-- Run this on database problems

-- table definitions
DROP TABLE IF EXISTS users_token;
DROP TABLE IF EXISTS solutions;
DROP TABLE IF EXISTS problems;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS test1;

CREATE TABLE test1 (
	id uuid NOT NULL
);

CREATE TABLE users (
	id uuid NOT NULL,
	active bool NOT NULL default true,
	avatar varchar(255) NULL,
	creation_timestamp timestamp NOT NULL,
	display_name varchar(100) NULL,
	email varchar(100) NOT NULL,
	hashed_password varchar(255) NOT NULL,
	username varchar(50) NOT NULL,
	UNIQUE (username),
	UNIQUE (email),
	PRIMARY KEY (id)
);

CREATE TABLE problems (
	id uuid NOT NULL,
	content text NOT NULL,
	creation_timestamp timestamp NOT NULL,
	tags varchar(255) NOT NULL,
	title varchar(255) NOT NULL,
	created_by uuid NOT NULL,
	PRIMARY KEY (id)
);

ALTER TABLE problems ADD CONSTRAINT problems_users_fk FOREIGN KEY (created_by) REFERENCES users(id);

CREATE TABLE solutions (
	id uuid NOT NULL,
	category varchar(50) NOT NULL,
	"content" text NOT NULL,
	creation_timestamp timestamp NOT NULL,
	vote_bad_count int4 NOT NULL,
	vote_good_count int4 NOT NULL,
	created_by uuid NOT NULL,
	problems_id uuid NOT NULL,
	PRIMARY KEY (id)
);

ALTER TABLE solutions ADD CONSTRAINT solutions_problems_fk FOREIGN KEY (problems_id) REFERENCES problems(id);
ALTER TABLE solutions ADD CONSTRAINT solutions_users_fk FOREIGN KEY (created_by) REFERENCES users(id);

CREATE TABLE users_token (
	user_id uuid NOT NULL,
	auth_token varchar(255) NOT NULL,
	creation_timestamp timestamp NOT NULL,
	expiry_timestamp timestamp NOT NULL,
	PRIMARY KEY (user_id)
);

-- data samples