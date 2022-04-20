create table customer
(
    id  SERIAL PRIMARY KEY,
    name varchar(255) not null
);
insert into customer (id, name) values (1, 'John Doe');
select * from customer;