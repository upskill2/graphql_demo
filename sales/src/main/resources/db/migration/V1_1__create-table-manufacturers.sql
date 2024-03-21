CREATE TABLE IF NOT EXISTS "manufacturersEntity" (
	"uuid"              uuid NOT NULL PRIMARY KEY,
	"name"              varchar NOT NULL UNIQUE,
	"origin_country"    varchar NOT NULL,
	"description"       varchar
);