insert into user(id, join_date, name, password, ssn) values(90001, NOW(), 'User1','pwd111', '701010-1111111');
insert into user(id, join_date, name, password, ssn) values(90002, NOW(), 'User2','pwd222', '801111-1111111');
insert into user(id, join_date, name, password, ssn) values(90003, NOW(), 'User3','pwd333', '901212-2222222');

insert into post(description, user_id) values('My first post', 90001);
insert into post(description, user_id) values('My second post', 90001);