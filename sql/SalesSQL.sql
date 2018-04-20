--일별 상위 2개 품목
SELECT order_date_1, menu_1, sum_1
  FROM (SELECT row_number() over(partition BY order_date ORDER BY order_date) rnum, order_date order_date_1, menu menu_1, sum(count) sum_1
          FROM menusales
         WHERE to_char(order_date,'yyyy-MM-dd') >= '2017-04-01'
           AND to_char(order_date,'yyyy-MM-dd') <= '2017-04-30'
         GROUP BY order_date, menu
         ORDER BY order_date asc, sum(count) desc
        )
 WHERE rnum <= 2;

--일별 총 판매 수
SELECT menu, sum(count) sum
FROM menusales
WHERE to_char(order_date,'yyyy-MM-dd') >= '2017-04-01'
 AND to_char(order_date,'yyyy-MM-dd') <= '2017-04-30'
GROUP BY menu
ORDER BY sum(count) desc;

--월별 최대 판매 품목
select menu, month, sum
from(
    SELECT row_number() over(partition by to_char(order_date, 'yyyy-MM') order by to_char(order_date, 'yyyy-MM')) rnum, menu, to_char(order_date,'yyyy-MM') month, sum(count) sum
    FROM menusales
    WHERE to_char(order_date,'yyyy-MM-dd') >= '2017-01-01'
     AND to_char(order_date,'yyyy-MM-dd') <= '2017-04-30'
    GROUP BY menu, to_char(order_date,'yyyy-MM')
    ORDER BY to_char(order_date, 'yyyy-MM') asc, sum(count) desc
)
where rnum = 1;
 
 --월별 최대 2개 품목
SELECT order_date_1, menu, sum
  FROM (SELECT row_number() over(partition BY to_char(order_date,'yyyy-MM') ORDER BY to_char(order_date,'yyyy-MM')) rnum, to_char(order_date,'yyyy-MM') order_date_1, menu, sum(count) sum
          FROM menusales
         WHERE to_char(order_date,'yyyy-MM-dd') >= '2017-01-01'
           AND to_char(order_date,'yyyy-MM-dd') <= '2017-04-30'
         GROUP BY to_char(order_date,'yyyy-MM'), menu
         ORDER BY to_char(order_date,'yyyy-MM') asc, sum(count) desc
        )
WHERE rnum <= 2;