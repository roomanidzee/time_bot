CREATE OR REPLACE VIEW time_analytics
AS
SELECT
  user_id,
  date_info,
  to_char(date_info, 'YYYY-MM-DD') AS date_str,
  to_char(sum(time_info), 'HH24:MI:SS') AS time_sum
FROM time_info
GROUP BY user_id, date_info;