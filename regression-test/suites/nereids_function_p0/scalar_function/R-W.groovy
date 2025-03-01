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

suite("nereids_scalar_fn_4") {
    sql "use regression_test_nereids_function_p0"
    sql "set enable_nereids_planner=true"
    sql "set enable_fallback_to_original_planner=false"

    qt_sql "select radians(kdbl) from fn_test order by kdbl"
    sql "select random() from fn_test"
    sql "select random(1000) from fn_test order by kbint"
    qt_sql "select regexp_extract(kvchrs1, kvchrs1, kbint) from fn_test order by kvchrs1, kvchrs1, kbint"
    qt_sql "select regexp_extract(kstr, kstr, kbint) from fn_test order by kstr, kstr, kbint"
    // core
    // qt_sql "select regexp_extract_all(kvchrs1, kvchrs1) from fn_test order by kvchrs1, kvchrs1"
    // qt_sql "select regexp_extract_all(kstr, kstr) from fn_test order by kstr, kstr"
    qt_sql "select regexp_replace(kvchrs1, kvchrs1, kvchrs1) from fn_test order by kvchrs1, kvchrs1, kvchrs1"
    qt_sql "select regexp_replace(kstr, kstr, kstr) from fn_test order by kstr, kstr, kstr"
    qt_sql "select regexp_replace_one(kvchrs1, kvchrs1, kvchrs1) from fn_test order by kvchrs1, kvchrs1, kvchrs1"
    qt_sql "select regexp_replace_one(kstr, kstr, kstr) from fn_test order by kstr, kstr, kstr"
    qt_sql "select repeat(kvchrs1, kint) from fn_test order by kvchrs1, kint"
    qt_sql "select repeat(kstr, kint) from fn_test order by kstr, kint"
    qt_sql "select replace(kvchrs1, kvchrs1, kvchrs1) from fn_test order by kvchrs1, kvchrs1, kvchrs1"
    qt_sql "select replace(kstr, kstr, kstr) from fn_test order by kstr, kstr, kstr"
    qt_sql "select right(kvchrs1, kint) from fn_test order by kvchrs1, kint"
    qt_sql "select right(kstr, kint) from fn_test order by kstr, kint"
    qt_sql "select round(kdbl) from fn_test order by kdbl"
    qt_sql "select round(kdbl, 2) from fn_test order by kdbl, kint"
    qt_sql "select round_bankers(kdbl) from fn_test order by kdbl"
    qt_sql "select round_bankers(kdbl, 2) from fn_test order by kdbl, kint"
    // timeout
    // qt_sql "select rpad(kvchrs1, kint, kvchrs1) from fn_test order by kvchrs1, kint, kvchrs1"
    // qt_sql "select rpad(kstr, kint, kstr) from fn_test order by kstr, kint, kstr"
    qt_sql "select rtrim(kvchrs1) from fn_test order by kvchrs1"
    qt_sql "select rtrim(kstr) from fn_test order by kstr"
    sql "select running_difference(ktint) from fn_test order by ktint"
    sql "select running_difference(ksint) from fn_test order by ksint"
    sql "select running_difference(kint) from fn_test order by kint"
    // core
    // qt_sql "select running_difference(kbint) from fn_test order by kbint"
    sql "select running_difference(klint) from fn_test order by klint"
    sql "select running_difference(kfloat) from fn_test order by kfloat"
    sql "select running_difference(kdbl) from fn_test order by kdbl"
    sql "select running_difference(kdcmls1) from fn_test order by kdcmls1"
    sql "select running_difference(kdt) from fn_test order by kdt"
    sql "select running_difference(kdtv2) from fn_test order by kdtv2"
    // core
    // qt_sql "select running_difference(kdtm) from fn_test order by kdtm"
    // qt_sql "select running_difference(kdtmv2s1) from fn_test order by kdtmv2s1"
    qt_sql "select second(kdtm) from fn_test order by kdtm"
    qt_sql "select second(kdtmv2s1) from fn_test order by kdtmv2s1"
    qt_sql "select second(kdtv2) from fn_test order by kdtv2"
    qt_sql "select second_ceil(kdtm) from fn_test order by kdtm"
    qt_sql "select second_ceil(kdtmv2s1) from fn_test order by kdtmv2s1"
    // core
    // qt_sql "select second_ceil(kdtv2) from fn_test order by kdtv2"
    qt_sql "select second_ceil(kdtm, kdtm) from fn_test order by kdtm, kdtm"
    qt_sql "select second_ceil(kdtm, kint) from fn_test order by kdtm, kint"
    qt_sql "select second_ceil(kdtmv2s1, kdtmv2s1) from fn_test order by kdtmv2s1, kdtmv2s1"
    qt_sql "select second_ceil(kdtmv2s1, kint) from fn_test order by kdtmv2s1, kint"
    // core
    // qt_sql "select second_ceil(kdtv2, kdtv2) from fn_test order by kdtv2, kdtv2"
    // qt_sql "select second_ceil(kdtv2, kint) from fn_test order by kdtv2, kint"
    qt_sql "select second_ceil(kdtm, kint, kdtm) from fn_test order by kdtm, kint, kdtm"
    qt_sql "select second_ceil(kdtmv2s1, kint, kdtmv2s1) from fn_test order by kdtmv2s1, kint, kdtmv2s1"
    // core
    // qt_sql "select second_ceil(kdtv2, kint, kdtv2) from fn_test order by kdtv2, kint, kdtv2"
    qt_sql "select second_floor(kdtm) from fn_test order by kdtm"
    qt_sql "select second_floor(kdtmv2s1) from fn_test order by kdtmv2s1"
    // core
    // qt_sql "select second_floor(kdtv2) from fn_test order by kdtv2"
    qt_sql "select second_floor(kdtm, kdtm) from fn_test order by kdtm, kdtm"
    qt_sql "select second_floor(kdtm, kint) from fn_test order by kdtm, kint"
    qt_sql "select second_floor(kdtmv2s1, kdtmv2s1) from fn_test order by kdtmv2s1, kdtmv2s1"
    qt_sql "select second_floor(kdtmv2s1, kint) from fn_test order by kdtmv2s1, kint"
    // core
    // qt_sql "select second_floor(kdtv2, kdtv2) from fn_test order by kdtv2, kdtv2"
    // qt_sql "select second_floor(kdtv2, kint) from fn_test order by kdtv2, kint"
    qt_sql "select second_floor(kdtm, kint, kdtm) from fn_test order by kdtm, kint, kdtm"
    qt_sql "select second_floor(kdtmv2s1, kint, kdtmv2s1) from fn_test order by kdtmv2s1, kint, kdtmv2s1"
    // core
    // qt_sql "select second_floor(kdtv2, kint, kdtv2) from fn_test order by kdtv2, kint, kdtv2"
    // cannot find function
    // qt_sql "select seconds_add(kdtm, kint) from fn_test order by kdtm, kint"
    // qt_sql "select seconds_add(kdtmv2s1, kint) from fn_test order by kdtmv2s1, kint"
    // qt_sql "select seconds_add(kdt, kint) from fn_test order by kdt, kint"
    // qt_sql "select seconds_add(kdtv2, kint) from fn_test order by kdtv2, kint"
    qt_sql "select seconds_diff(kdtm, kdtm) from fn_test order by kdtm, kdtm"
    qt_sql "select seconds_diff(kdtmv2s1, kdtmv2s1) from fn_test order by kdtmv2s1, kdtmv2s1"
    qt_sql "select seconds_diff(kdtv2, kdtmv2s1) from fn_test order by kdtv2, kdtmv2s1"
    qt_sql "select seconds_diff(kdtmv2s1, kdtv2) from fn_test order by kdtmv2s1, kdtv2"
    qt_sql "select seconds_diff(kdtv2, kdtv2) from fn_test order by kdtv2, kdtv2"
    qt_sql "select seconds_diff(kdtv2, kdtm) from fn_test order by kdtv2, kdtm"
    qt_sql "select seconds_diff(kdtm, kdtv2) from fn_test order by kdtm, kdtv2"
    qt_sql "select seconds_diff(kdtmv2s1, kdtm) from fn_test order by kdtmv2s1, kdtm"
    qt_sql "select seconds_diff(kdtm, kdtmv2s1) from fn_test order by kdtm, kdtmv2s1"
    // cannot find function
    // qt_sql "select seconds_sub(kdtm, kint) from fn_test order by kdtm, kint"
    // qt_sql "select seconds_sub(kdtmv2s1, kint) from fn_test order by kdtmv2s1, kint"
    // qt_sql "select seconds_sub(kdt, kint) from fn_test order by kdt, kint"
    // qt_sql "select seconds_sub(kdtv2, kint) from fn_test order by kdtv2, kint"
    qt_sql "select sign(kdbl) from fn_test order by kdbl"
    qt_sql "select sin(kdbl) from fn_test order by kdbl"
    qt_sql "select sleep(0.1) from fn_test order by kint"
    qt_sql "select sm3(kvchrs1) from fn_test order by kvchrs1"
    qt_sql "select sm3(kstr) from fn_test order by kstr"
    qt_sql "select sm3sum(kvchrs1) from fn_test order by kvchrs1"
    qt_sql "select sm3sum(kstr) from fn_test order by kstr"
    sql "select sm4_decrypt(kvchrs1, kvchrs1) from fn_test order by kvchrs1, kvchrs1"
    sql "select sm4_decrypt(kstr, kstr) from fn_test order by kstr, kstr"
    // core
    // sql "select sm4_decrypt(kvchrs1, kvchrs1, kvchrs1) from fn_test order by kvchrs1, kvchrs1, kvchrs1"
    sql "select sm4_decrypt(kstr, kstr, kstr) from fn_test order by kstr, kstr, kstr"
    // cannot find function
    // qt_sql "select sm4_decrypt(kvchrs1, kvchrs1, kvchrs1, kvchrs1) from fn_test order by kvchrs1, kvchrs1, kvchrs1, kvchrs1"
    // qt_sql "select sm4_decrypt(kstr, kstr, kstr, kstr) from fn_test order by kstr, kstr, kstr, kstr"
    sql "select sm4_encrypt(kvchrs1, kvchrs1) from fn_test order by kvchrs1, kvchrs1"
    sql "select sm4_encrypt(kstr, kstr) from fn_test order by kstr, kstr"
    sql "select sm4_encrypt(kvchrs1, kvchrs1, kvchrs1) from fn_test order by kvchrs1, kvchrs1, kvchrs1"
    sql "select sm4_encrypt(kstr, kstr, kstr) from fn_test order by kstr, kstr, kstr"
    // cannot find function
    // qt_sql "select sm4_encrypt(kvchrs1, kvchrs1, kvchrs1, kvchrs1) from fn_test order by kvchrs1, kvchrs1, kvchrs1, kvchrs1"
    // qt_sql "select sm4_encrypt(kstr, kstr, kstr, kstr) from fn_test order by kstr, kstr, kstr, kstr"
    sql "select space(10) from fn_test order by kint"
    // argument in index 2 must be constant but it is
    // qt_sql "select split_part(kvchrs1, kvchrs1, 3) from fn_test order by kvchrs1, kvchrs1, kint"
    // qt_sql "select split_part(kstr, kstr, 3) from fn_test order by kstr, kstr, kint"
    // data out of double range
    // qt_sql "select sqrt(kdbl) from fn_test order by kdbl"
    // data out of geometry-style range
    // qt_sql "select st_astext(kvchrs1) from fn_test order by kvchrs1"
    // qt_sql "select st_astext(kstr) from fn_test order by kstr"
    // qt_sql "select st_aswkt(kvchrs1) from fn_test order by kvchrs1"
    // qt_sql "select st_aswkt(kstr) from fn_test order by kstr"
    // qt_sql "select st_circle(kdbl, kdbl, kdbl) from fn_test order by kdbl, kdbl, kdbl"
    // qt_sql "select st_contains(kvchrs1, kvchrs1) from fn_test order by kvchrs1, kvchrs1"
    // qt_sql "select st_distance_sphere(kdbl, kdbl, kdbl, kdbl) from fn_test order by kdbl, kdbl, kdbl, kdbl"
    // qt_sql "select st_geometryfromtext(kvchrs1) from fn_test order by kvchrs1"
    // qt_sql "select st_geometryfromtext(kstr) from fn_test order by kstr"
    // qt_sql "select st_geomfromtext(kvchrs1) from fn_test order by kvchrs1"
    // qt_sql "select st_geomfromtext(kstr) from fn_test order by kstr"
    // qt_sql "select st_linefromtext(kvchrs1) from fn_test order by kvchrs1"
    // qt_sql "select st_linefromtext(kstr) from fn_test order by kstr"
    // qt_sql "select st_linestringfromtext(kvchrs1) from fn_test order by kvchrs1"
    // qt_sql "select st_linestringfromtext(kstr) from fn_test order by kstr"
    // qt_sql "select st_point(kdbl, kdbl) from fn_test order by kdbl, kdbl"
    // qt_sql "select st_polyfromtext(kvchrs1) from fn_test order by kvchrs1"
    // qt_sql "select st_polyfromtext(kstr) from fn_test order by kstr"
    // qt_sql "select st_polygon(kvchrs1) from fn_test order by kvchrs1"
    // qt_sql "select st_polygon(kstr) from fn_test order by kstr"
    // qt_sql "select st_polygonfromtext(kvchrs1) from fn_test order by kvchrs1"
    // qt_sql "select st_polygonfromtext(kstr) from fn_test order by kstr"
    // qt_sql "select st_x(kvchrs1) from fn_test order by kvchrs1"
    // qt_sql "select st_x(kstr) from fn_test order by kstr"
    // qt_sql "select st_y(kvchrs1) from fn_test order by kvchrs1"
    // qt_sql "select st_y(kstr) from fn_test order by kstr"
    qt_sql "select starts_with(kvchrs1, kvchrs1) from fn_test order by kvchrs1, kvchrs1"
    // core
    // qt_sql "select starts_with(kstr, kstr) from fn_test order by kstr, kstr"
    // core
    // qt_sql "select str_left(kvchrs1, kint) from fn_test order by kvchrs1, kint"
    // qt_sql "select str_left(kstr, kint) from fn_test order by kstr, kint"
    // qt_sql "select str_right(kvchrs1, kint) from fn_test order by kvchrs1, kint"
    // qt_sql "select str_right(kstr, kint) from fn_test order by kstr, kint"
    // qt_sql "select str_to_date(kvchrs1, kvchrs1) from fn_test order by kvchrs1, kvchrs1"
    // qt_sql "select str_to_date(kstr, kstr) from fn_test order by kstr, kstr"
// function sub_bitmap(bitmap, bigint, bigint) is unsupported for the test suite.
    qt_sql "select sub_replace(kvchrs1, kvchrs1, kint) from fn_test order by kvchrs1, kvchrs1, kint"
    qt_sql "select sub_replace(kstr, kstr, kint) from fn_test order by kstr, kstr, kint"
    qt_sql "select sub_replace(kvchrs1, kvchrs1, kint, kint) from fn_test order by kvchrs1, kvchrs1, kint, kint"
    qt_sql "select sub_replace(kstr, kstr, kint, kint) from fn_test order by kstr, kstr, kint, kint"
    qt_sql "select substring(kvchrs1, kint) from fn_test order by kvchrs1, kint"
    qt_sql "select substring(kstr, kint) from fn_test order by kstr, kint"
    qt_sql "select substring(kvchrs1, kint, kint) from fn_test order by kvchrs1, kint, kint"
    qt_sql "select substring(kstr, kint, kint) from fn_test order by kstr, kint, kint"
    // argument index 2 must be constant but it is
    // qt_sql "select substring_index(kvchrs1, kvchrs1, 2) from fn_test order by kvchrs1, kvchrs1, kint"
    // qt_sql "select substring_index(kstr, kstr, 2) from fn_test order by kstr, kstr, kint"
    qt_sql "select tan(kdbl) from fn_test order by kdbl"
    // cannot find function
    // qt_sql "select time_diff(kdtm, kdtm) from fn_test order by kdtm, kdtm"
    // qt_sql "select time_diff(kdtmv2s1, kdtmv2s1) from fn_test order by kdtmv2s1, kdtmv2s1"
    // qt_sql "select time_diff(kdtmv2s1, kdtv2) from fn_test order by kdtmv2s1, kdtv2"
    // qt_sql "select time_diff(kdtv2, kdtmv2s1) from fn_test order by kdtv2, kdtmv2s1"
    // qt_sql "select time_diff(kdtv2, kdtv2) from fn_test order by kdtv2, kdtv2"
    // qt_sql "select time_diff(kdtmv2s1, kdtm) from fn_test order by kdtmv2s1, kdtm"
    // qt_sql "select time_diff(kdtv2, kdtm) from fn_test order by kdtv2, kdtm"
    qt_sql "select timestamp(kdtm) from fn_test order by kdtm"
    // core
    // qt_sql "select timestamp(kdtmv2s1) from fn_test order by kdtmv2s1"
    qt_sql "select to_base64(kstr) from fn_test order by kstr"
    qt_sql "select to_bitmap(kvchrs1) from fn_test order by kvchrs1"
    qt_sql "select to_bitmap(kstr) from fn_test order by kstr"
    // core
    // qt_sql "select to_bitmap_with_check(kvchrs1) from fn_test where kvchrs1 is not null order by kvchrs1"
    // qt_sql "select to_bitmap_with_check(kstr) from fn_test where kstr is not null order by kstr"
    qt_sql "select to_date(kdtm) from fn_test order by kdtm"
    qt_sql "select to_date(kdtmv2s1) from fn_test order by kdtmv2s1"
    // core
    // qt_sql "select to_date_v2(kdtmv2s1) from fn_test order by kdtmv2s1"
    qt_sql "select to_days(kdt) from fn_test order by kdt"
    qt_sql "select to_days(kdtv2) from fn_test order by kdtv2"
    qt_sql "select to_monday(kdtmv2s1) from fn_test order by kdtmv2s1"
    qt_sql "select to_monday(kdtv2) from fn_test order by kdtv2"
    qt_sql "select to_monday(kdtm) from fn_test order by kdtm"
    qt_sql "select to_monday(kdt) from fn_test order by kdt"
    // core
    // qt_sql "select to_quantile_state(kvchrs1, 0.05) from fn_test order by kvchrs1, kfloat"
    qt_sql "select trim(kvchrs1) from fn_test order by kvchrs1"
    qt_sql "select trim(kstr) from fn_test order by kstr"
    qt_sql "select truncate(kdbl, 2) from fn_test order by kdbl, kint"
    qt_sql "select unhex(kvchrs1) from fn_test order by kvchrs1"
    qt_sql "select unhex(kstr) from fn_test order by kstr"
    sql "select unix_timestamp() from fn_test"
    qt_sql "select unix_timestamp(kdtm) from fn_test order by kdtm"
    qt_sql "select unix_timestamp(kdt) from fn_test order by kdt"
    qt_sql "select unix_timestamp(kdtmv2s1) from fn_test order by kdtmv2s1"
    qt_sql "select unix_timestamp(kdtv2) from fn_test order by kdtv2"
    qt_sql "select unix_timestamp(kvchrs1, kvchrs1) from fn_test order by kvchrs1, kvchrs1"
    qt_sql "select unix_timestamp(kstr, kstr) from fn_test order by kstr, kstr"
    qt_sql "select upper(kvchrs1) from fn_test order by kvchrs1"
    qt_sql "select upper(kstr) from fn_test order by kstr"
    sql "select user() from fn_test"
    sql "select utc_timestamp() from fn_test"
    sql "select uuid() from fn_test"
    sql "select version() from fn_test"
    qt_sql "select week(kdtm) from fn_test order by kdtm"
    qt_sql "select week(kdtmv2s1) from fn_test order by kdtmv2s1"
    qt_sql "select week(kdtv2) from fn_test order by kdtv2"
    qt_sql "select week(kdtm, kint) from fn_test order by kdtm, kint"
    qt_sql "select week(kdtmv2s1, kint) from fn_test order by kdtmv2s1, kint"
    qt_sql "select week(kdtv2, kint) from fn_test order by kdtv2, kint"
    qt_sql "select week_ceil(kdtm) from fn_test order by kdtm"
    qt_sql "select week_ceil(kdtmv2s1) from fn_test order by kdtmv2s1"
    qt_sql "select week_ceil(kdtv2) from fn_test order by kdtv2"
    qt_sql "select week_ceil(kdtm, kdtm) from fn_test order by kdtm, kdtm"
    qt_sql "select week_ceil(kdtm, kint) from fn_test order by kdtm, kint"
    qt_sql "select week_ceil(kdtmv2s1, kdtmv2s1) from fn_test order by kdtmv2s1, kdtmv2s1"
    qt_sql "select week_ceil(kdtmv2s1, kint) from fn_test order by kdtmv2s1, kint"
    qt_sql "select week_ceil(kdtv2, kdtv2) from fn_test order by kdtv2, kdtv2"
    qt_sql "select week_ceil(kdtv2, kint) from fn_test order by kdtv2, kint"
    qt_sql "select week_ceil(kdtm, kint, kdtm) from fn_test order by kdtm, kint, kdtm"
    qt_sql "select week_ceil(kdtmv2s1, kint, kdtmv2s1) from fn_test order by kdtmv2s1, kint, kdtmv2s1"
    qt_sql "select week_ceil(kdtv2, kint, kdtv2) from fn_test order by kdtv2, kint, kdtv2"
    qt_sql "select week_floor(kdtm) from fn_test order by kdtm"
    qt_sql "select week_floor(kdtmv2s1) from fn_test order by kdtmv2s1"
    qt_sql "select week_floor(kdtv2) from fn_test order by kdtv2"
    qt_sql "select week_floor(kdtm, kdtm) from fn_test order by kdtm, kdtm"
    qt_sql "select week_floor(kdtm, kint) from fn_test order by kdtm, kint"
    qt_sql "select week_floor(kdtmv2s1, kdtmv2s1) from fn_test order by kdtmv2s1, kdtmv2s1"
    qt_sql "select week_floor(kdtmv2s1, kint) from fn_test order by kdtmv2s1, kint"
    qt_sql "select week_floor(kdtv2, kdtv2) from fn_test order by kdtv2, kdtv2"
    qt_sql "select week_floor(kdtv2, kint) from fn_test order by kdtv2, kint"
    qt_sql "select week_floor(kdtm, kint, kdtm) from fn_test order by kdtm, kint, kdtm"
    qt_sql "select week_floor(kdtmv2s1, kint, kdtmv2s1) from fn_test order by kdtmv2s1, kint, kdtmv2s1"
    qt_sql "select week_floor(kdtv2, kint, kdtv2) from fn_test order by kdtv2, kint, kdtv2"
    // cannot find function
    // qt_sql "select week_of_year(kdtm) from fn_test order by kdtm"
    // qt_sql "select week_of_year(kdtmv2s1) from fn_test order by kdtmv2s1"
    // qt_sql "select week_of_year(kdtv2) from fn_test order by kdtv2"
    qt_sql "select weekday(kdtm) from fn_test order by kdtm"
    qt_sql "select weekday(kdtmv2s1) from fn_test order by kdtmv2s1"
    qt_sql "select weekday(kdtv2) from fn_test order by kdtv2"
    qt_sql "select weeks_diff(kdtm, kdtm) from fn_test order by kdtm, kdtm"
    qt_sql "select weeks_diff(kdtmv2s1, kdtmv2s1) from fn_test order by kdtmv2s1, kdtmv2s1"
    qt_sql "select weeks_diff(kdtv2, kdtmv2s1) from fn_test order by kdtv2, kdtmv2s1"
    qt_sql "select weeks_diff(kdtmv2s1, kdtv2) from fn_test order by kdtmv2s1, kdtv2"
    qt_sql "select weeks_diff(kdtv2, kdtv2) from fn_test order by kdtv2, kdtv2"
    qt_sql "select weeks_diff(kdtv2, kdtm) from fn_test order by kdtv2, kdtm"
    qt_sql "select weeks_diff(kdtm, kdtv2) from fn_test order by kdtm, kdtv2"
    qt_sql "select weeks_diff(kdtmv2s1, kdtm) from fn_test order by kdtmv2s1, kdtm"
    qt_sql "select weeks_diff(kdtm, kdtmv2s1) from fn_test order by kdtm, kdtmv2s1"
}