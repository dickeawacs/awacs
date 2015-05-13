-- 删除原数据
DELETE FROM table_10_bak ;
DELETE FROM table_1_bak ;
-- 执行 
CALL ats.`buildData`();
-- 查看
-- SELECT * FROM table_10_bak;
SELECT COUNT(1) FROM table_10_bak;
-- SELECT * FROM table_1_bak;
SELECT COUNT(1) FROM table_1_bak;