DROP DATABASE db;
CREATE DATABASE db;
Use db;

CREATE TABLE product(
	id int not null,
	name varchar(20) not null,
	des varchar(50),
	bname varchar(20),
	PRIMARY KEY(id));

CREATE TABLE productKeyword(
	id int not null,
	typeKeyword varchar(20),
	PRIMARY KEY(id,typeKeyword),
	FOREIGN KEY(id) references product(id));
    
CREATE TABLE zip(
	country varchar(30) not null,
	city varchar(30) not null,
	zipcode char(20) not null,
	PRIMARY KEY(zipcode));    

CREATE TABLE website(
	url varchar(30) not null,
	zipcode char(20) not null,
	street varchar(30) not null,
	email varchar(30) not null,
	PRIMARY KEY(url),
    	FOREIGN KEY(zipcode) references zip(zipcode));


	
CREATE TABLE websitephone(
	url varchar(30) not null,
	phonenumber varchar(20) not null,
	PRIMARY KEY(url,phonenumber),
	FOREIGN KEY(url) references website(url));

CREATE TABLE externalsupplier(
	url varchar(30) not null,
	name varchar(30),
	phonenumber varchar(30),
	email varchar(30),
	PRIMARY KEY(url),
	FOREIGN KEY(url) references website(url));

CREATE TABLE sell(
	id int not null,
	url varchar(30) not null,
	date varchar(10) not null,
	initial int,
	discounted int,
	PRIMARY KEY(id,url,date),
	FOREIGN KEY(url) references website(url),
	FOREIGN KEY(id) references product(id),
	CONSTRAINT initial_discount_check CHECK(initial > discounted));




