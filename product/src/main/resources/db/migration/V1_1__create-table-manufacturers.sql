DROP TABLE IF EXISTS users_token;
DROP TABLE IF EXISTS solutions;
DROP TABLE IF EXISTS problems;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS test1;

CREATE TABLE IF NOT EXISTS "manufacturers" (
	"uuid"              uuid NOT NULL PRIMARY KEY,
	"name"              varchar NOT NULL UNIQUE,
	"origin_country"    varchar NOT NULL,
	"description"       varchar
);