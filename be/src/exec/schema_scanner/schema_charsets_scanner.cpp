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

#include "exec/schema_scanner/schema_charsets_scanner.h"

#include "vec/common/string_ref.h"

namespace doris {

SchemaScanner::ColumnDesc SchemaCharsetsScanner::_s_css_columns[] = {
        //   name,       type,          size
        {"CHARACTER_SET_NAME", TYPE_VARCHAR, sizeof(StringRef), false},
        {"DEFAULT_COLLATE_NAME", TYPE_VARCHAR, sizeof(StringRef), false},
        {"DESCRIPTION", TYPE_VARCHAR, sizeof(StringRef), false},
        {"MAXLEN", TYPE_BIGINT, sizeof(int64_t), false},
};

SchemaCharsetsScanner::CharsetStruct SchemaCharsetsScanner::_s_charsets[] = {
        {"utf8", "utf8_general_ci", "UTF-8 Unicode", 3},
        {nullptr, nullptr, nullptr, 0},
};

SchemaCharsetsScanner::SchemaCharsetsScanner()
        : SchemaScanner(_s_css_columns, sizeof(_s_css_columns) / sizeof(SchemaScanner::ColumnDesc)),
          _index(0) {}

SchemaCharsetsScanner::~SchemaCharsetsScanner() {}

Status SchemaCharsetsScanner::fill_one_row(Tuple* tuple, MemPool* pool) {
    // variables names
    {
        void* slot = tuple->get_slot(_tuple_desc->slots()[0]->tuple_offset());
        StringRef* str_slot = reinterpret_cast<StringRef*>(slot);
        int len = strlen(_s_charsets[_index].charset);
        str_slot->data = (char*)pool->allocate(len + 1);
        if (nullptr == str_slot->data) {
            return Status::InternalError("No Memory.");
        }
        memcpy(const_cast<char*>(str_slot->data), _s_charsets[_index].charset, len + 1);
        str_slot->size = len;
    }
    // DEFAULT_COLLATE_NAME
    {
        void* slot = tuple->get_slot(_tuple_desc->slots()[1]->tuple_offset());
        StringRef* str_slot = reinterpret_cast<StringRef*>(slot);
        int len = strlen(_s_charsets[_index].default_collation);
        str_slot->data = (char*)pool->allocate(len + 1);
        if (nullptr == str_slot->data) {
            return Status::InternalError("No Memory.");
        }
        memcpy(const_cast<char*>(str_slot->data), _s_charsets[_index].default_collation, len + 1);
        str_slot->size = len;
    }
    // DESCRIPTION
    {
        void* slot = tuple->get_slot(_tuple_desc->slots()[2]->tuple_offset());
        StringRef* str_slot = reinterpret_cast<StringRef*>(slot);
        int len = strlen(_s_charsets[_index].description);
        str_slot->data = (char*)pool->allocate(len + 1);
        if (nullptr == str_slot->data) {
            return Status::InternalError("No Memory.");
        }
        memcpy(const_cast<char*>(str_slot->data), _s_charsets[_index].description, len + 1);
        str_slot->size = len;
    }
    // maxlen
    {
        void* slot = tuple->get_slot(_tuple_desc->slots()[3]->tuple_offset());
        *(int64_t*)slot = _s_charsets[_index].maxlen;
    }
    _index++;
    return Status::OK();
}

Status SchemaCharsetsScanner::get_next_row(Tuple* tuple, MemPool* pool, bool* eos) {
    if (!_is_init) {
        return Status::InternalError("call this before initial.");
    }
    if (nullptr == tuple || nullptr == pool || nullptr == eos) {
        return Status::InternalError("invalid parameter.");
    }
    if (nullptr == _s_charsets[_index].charset) {
        *eos = true;
        return Status::OK();
    }
    *eos = false;
    return fill_one_row(tuple, pool);
}

} // namespace doris
