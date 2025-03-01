// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

#include "exec/schema_scanner/schema_schema_privileges_scanner.h"

#include "exec/schema_scanner/schema_helper.h"
#include "runtime/primitive_type.h"
#include "vec/common/string_ref.h"

namespace doris {

SchemaScanner::ColumnDesc SchemaSchemaPrivilegesScanner::_s_tbls_columns[] = {
        //   name,       type,          size,     is_null
        {"GRANTEE", TYPE_VARCHAR, sizeof(StringRef), true},
        {"TABLE_CATALOG", TYPE_VARCHAR, sizeof(StringRef), true},
        {"TABLE_SCHEMA", TYPE_VARCHAR, sizeof(StringRef), false},
        {"PRIVILEGE_TYPE", TYPE_VARCHAR, sizeof(StringRef), false},
        {"IS_GRANTABLE", TYPE_VARCHAR, sizeof(StringRef), true},
};

SchemaSchemaPrivilegesScanner::SchemaSchemaPrivilegesScanner()
        : SchemaScanner(_s_tbls_columns,
                        sizeof(_s_tbls_columns) / sizeof(SchemaScanner::ColumnDesc)),
          _priv_index(0) {}

SchemaSchemaPrivilegesScanner::~SchemaSchemaPrivilegesScanner() {}

Status SchemaSchemaPrivilegesScanner::start(RuntimeState* state) {
    if (!_is_init) {
        return Status::InternalError("used before initialized.");
    }
    RETURN_IF_ERROR(get_new_table());
    return Status::OK();
}

Status SchemaSchemaPrivilegesScanner::fill_one_row(Tuple* tuple, MemPool* pool) {
    // set all bit to not null
    memset((void*)tuple, 0, _tuple_desc->num_null_bytes());
    const TPrivilegeStatus& priv_status = _priv_result.privileges[_priv_index];
    // grantee
    {
        Status status = fill_one_col(&priv_status.grantee, pool,
                                     tuple->get_slot(_tuple_desc->slots()[0]->tuple_offset()));
        if (!status.ok()) {
            return status;
        }
    }
    // catalog
    // This value is always def.
    {
        std::string definer = "def";
        Status status = fill_one_col(&definer, pool,
                                     tuple->get_slot(_tuple_desc->slots()[1]->tuple_offset()));
        if (!status.ok()) {
            return status;
        }
    }
    // schema
    {
        Status status = fill_one_col(&priv_status.schema, pool,
                                     tuple->get_slot(_tuple_desc->slots()[2]->tuple_offset()));
        if (!status.ok()) {
            return status;
        }
    }
    // privilege type
    {
        Status status = fill_one_col(&priv_status.privilege_type, pool,
                                     tuple->get_slot(_tuple_desc->slots()[3]->tuple_offset()));
        if (!status.ok()) {
            return status;
        }
    }
    // is grantable
    {
        Status status = fill_one_col(&priv_status.is_grantable, pool,
                                     tuple->get_slot(_tuple_desc->slots()[4]->tuple_offset()));
        if (!status.ok()) {
            return status;
        }
    }
    _priv_index++;
    return Status::OK();
}

Status SchemaSchemaPrivilegesScanner::fill_one_col(const std::string* src, MemPool* pool,
                                                   void* slot) {
    if (nullptr == slot || nullptr == pool || nullptr == src) {
        return Status::InternalError("input pointer is nullptr.");
    }
    StringRef* str_slot = reinterpret_cast<StringRef*>(slot);
    str_slot->size = src->length();
    str_slot->data = (char*)pool->allocate(str_slot->size);
    if (nullptr == str_slot->data) {
        return Status::InternalError("Allocate memory failed.");
    }
    memcpy(const_cast<char*>(str_slot->data), src->c_str(), str_slot->size);
    return Status::OK();
}

Status SchemaSchemaPrivilegesScanner::get_new_table() {
    TGetTablesParams table_params;
    if (nullptr != _param->wild) {
        table_params.__set_pattern(*(_param->wild));
    }
    if (nullptr != _param->current_user_ident) {
        table_params.__set_current_user_ident(*(_param->current_user_ident));
    } else {
        if (nullptr != _param->user) {
            table_params.__set_user(*(_param->user));
        }
        if (nullptr != _param->user_ip) {
            table_params.__set_user_ip(*(_param->user_ip));
        }
    }

    if (nullptr != _param->ip && 0 != _param->port) {
        RETURN_IF_ERROR(SchemaHelper::list_schema_privilege_status(*(_param->ip), _param->port,
                                                                   table_params, &_priv_result));
    } else {
        return Status::InternalError("IP or port doesn't exists");
    }
    _priv_index = 0;
    return Status::OK();
}

Status SchemaSchemaPrivilegesScanner::get_next_row(Tuple* tuple, MemPool* pool, bool* eos) {
    if (!_is_init) {
        return Status::InternalError("Used before initialized.");
    }
    if (nullptr == tuple || nullptr == pool || nullptr == eos) {
        return Status::InternalError("input pointer is nullptr.");
    }
    if (_priv_index >= _priv_result.privileges.size()) {
        *eos = true;
        return Status::OK();
    }
    *eos = false;
    return fill_one_row(tuple, pool);
}

} // namespace doris