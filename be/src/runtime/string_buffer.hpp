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

#pragma once

#include "runtime/mem_pool.h"
#include "vec/common/string_ref.h"

namespace doris {

// Dynamic-sizable string (similar to std::string) but without as many
// copies and allocations.
// StringBuffer wraps a StringRef object with a pool and memory buffer length.
// It supports a subset of the std::string functionality but will only allocate
// bigger string buffers as necessary.  std::string tries to be immutable and will
// reallocate very often.  std::string should be avoided in all hot paths.
class StringBuffer {
public:
    // C'tor for StringBuffer.  Memory backing the string will be allocated from
    // the pool as necessary.  Can optionally be initialized from a StringRef.
    StringBuffer(MemPool* pool, StringRef* str) : _pool(pool), _buffer_size(0) {
        if (str != NULL) {
            _string_value = *str;
            _buffer_size = str->size;
        }
    }

    StringBuffer(MemPool* pool) : _pool(pool), _buffer_size(0) {}

    virtual ~StringBuffer() {}

    // append 'str' to the current string, allocating a new buffer as necessary.
    void append(const char* str, int len) {
        int new_len = len + _string_value.size;

        if (new_len > _buffer_size) {
            grow_buffer(new_len);
        }

        memcpy(const_cast<char*>(_string_value.data) + _string_value.size, str, len);
        _string_value.size = new_len;
    }

    // TODO: switch everything to uint8_t?
    void append(const uint8_t* str, int len) { append(reinterpret_cast<const char*>(str), len); }

    // Assigns contents to StringBuffer
    void assign(const char* str, int len) {
        clear();
        append(str, len);
    }

    // clear the underlying StringRef.  The allocated buffer can be reused.
    void clear() { _string_value.size = 0; }

    // Clears the underlying buffer and StringRef
    void reset() {
        _string_value.size = 0;
        _buffer_size = 0;
    }

    // Returns whether the current string is empty
    bool empty() const { return _string_value.size == 0; }

    // Returns the length of the current string
    int size() const { return _string_value.size; }

    // Returns the underlying StringRef
    const StringRef& str() const { return _string_value; }

    // Returns the buffer size
    int buffer_size() const { return _buffer_size; }

private:
    // Grows the buffer backing the string to be at least new_size, copying
    // over the previous string data into the new buffer.
    // TODO: some kind of doubling strategy?
    void grow_buffer(int new_len) {
        char* new_buffer = reinterpret_cast<char*>(_pool->allocate(new_len));

        if (_string_value.size > 0) {
            memcpy(new_buffer, _string_value.data, _string_value.size);
        }

        _string_value.data = new_buffer;
        _buffer_size = new_len;
    }

    MemPool* _pool;
    StringRef _string_value;
    int _buffer_size;
};

} // namespace doris
