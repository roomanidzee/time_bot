CREATE TABLE IF NOT EXISTS time_info(
   id SERIAL CONSTRAINT time_info_pk PRIMARY KEY,
   user_id INTEGER,
   date_info DATE,
   time_info INTERVAL
);