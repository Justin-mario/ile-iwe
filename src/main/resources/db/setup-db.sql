create database ileiwedb;

create user if not exists 'super_user'@'localhost' identified by 'super123';
grant all privileges on ileiwedb.* to 'super_user'@'localhost';
flush privileges;