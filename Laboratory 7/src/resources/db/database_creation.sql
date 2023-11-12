
CREATE SCHEMA educational_department;

USE educational_department;

CREATE TABLE `group` (
                         id int not null auto_increment primary key,
                         name varchar(255) not null,
                         studying_program varchar (255) not null
);

CREATE TABLE student (
                         id int not null auto_increment primary key,
                         first_name varchar(255) not null,
                         last_name varchar(255) not null,
                         average_mark decimal(5,3) not null,
                         group_id int not null,
                         foreign key (group_id) references `group`(id) on delete cascade
);