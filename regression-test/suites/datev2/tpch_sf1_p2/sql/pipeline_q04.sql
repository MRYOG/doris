-- tables: orders,lineitem
SELECT /*+SET_VAR(enable_pipeline_engine=true) */
  o_orderpriority,
  count(*) AS order_count
FROM orders
WHERE
  o_orderdate >= DATEV2 '1993-07-01'
  AND o_orderdate < DATEV2 '1993-07-01' + INTERVAL '3' MONTH
AND EXISTS (
SELECT *
FROM lineitem
WHERE
l_orderkey = o_orderkey
AND l_commitdate < l_receiptdate
)
GROUP BY
o_orderpriority
ORDER BY
o_orderpriority
