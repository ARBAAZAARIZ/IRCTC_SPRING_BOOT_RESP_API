-- spring project
-- database for spring project  project name IRCTC  
create database irctc_database;
use irctc_database;
show tables;

desc user;

desc bank_account;
desc admin;
select * from admin;
select * from user;
SELECT * from bank_account;
select * from train;
select * from station;
select * from train_station; 
desc train_station;

truncate train;
desc train;
SELECT * FROM user inner join bank_account on user.bank_account_id = bank_account.id; 
select * from user where bank_account_id in (1,52);

select * from user where created_at between "2024-11-24" and "2024-11-27";

select b.bank_name from bank_account b inner join user u  on b.id = u.bank_account_id where u.user_name='umranmalik';

select * from admin where admin_user_name ="moeanali" and password ="MoeanAli786" and role ="admin";

-- SET FOREIGN_KEY_CHECKS = 0; 
-- TRUNCATE TABLE train;
-- truncate table train_station;
--  SET FOREIGN_KEY_CHECKS = 1;
 
 
 select * from train_station;
 select * from train;
 select * from station;
 
 select t.train_name, t.train_number , s.station_name, ts.arrival_time, ts.departure_time
 from train t inner join train_station ts on t.id = ts.train_id
 inner join station s on ts.station_id = s.id where t.id=152 ;

