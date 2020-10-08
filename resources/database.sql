DROP DATABASE simpletodo;

CREATE DATABASE IF NOT EXISTS simpletodo;
USE simpletodo;

DELIMITER //

CREATE FUNCTION UuidToBin(_uuid BINARY(36))
    RETURNS BINARY(16)
    LANGUAGE SQL  DETERMINISTIC  CONTAINS SQL  SQL SECURITY INVOKER
RETURN
    UNHEX(CONCAT(
        SUBSTR(_uuid, 15, 4),
        SUBSTR(_uuid, 10, 4),
        SUBSTR(_uuid,  1, 8),
        SUBSTR(_uuid, 20, 4),
        SUBSTR(_uuid, 25) ));
//
CREATE FUNCTION UuidFromBin(_bin BINARY(16))
    RETURNS BINARY(36)
    LANGUAGE SQL  DETERMINISTIC  CONTAINS SQL  SQL SECURITY INVOKER
RETURN
    LCASE(CONCAT_WS('-',
        HEX(SUBSTR(_bin,  5, 4)),
        HEX(SUBSTR(_bin,  3, 2)),
        HEX(SUBSTR(_bin,  1, 2)),
        HEX(SUBSTR(_bin,  9, 2)),
        HEX(SUBSTR(_bin, 11))
             ));

//
DELIMITER ;

CREATE TABLE IF NOT EXISTS roles (
	NR_ID BINARY(16) NOT NULL DEFAULT UUID(),
	STR_NAME VARCHAR(20),
	CONSTRAINT pk_roles
		PRIMARY KEY ( NR_ID )
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS workspaces (
	NR_ID BINARY(16) NOT NULL DEFAULT UUID(),
	DATE_CREATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	STR_NAME VARCHAR(100),
	JSON_DATA JSON CHECK (JSON_VALID(json_data)),
	CONSTRAINT pk_roles
		PRIMARY KEY ( NR_ID )
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS lists (
	NR_ID BINARY(16) NOT NULL DEFAULT UUID(),
	DATE_CREATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	STR_NAME VARCHAR(100),
	NR_WORKSPACE_ID BINARY(16) NOT NULL,
	JSON_DATA JSON CHECK (JSON_VALID(json_data)),
	CONSTRAINT pk_roles
		PRIMARY KEY ( NR_ID )
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS users (
	NR_ID BINARY(16) NOT NULL DEFAULT UUID(),
	STR_USERNAME VARCHAR(100),
	STR_EMAIL VARCHAR(100),
	STR_PASSWORD VARCHAR(100),
	STATUS_ACTIVATED TINYINT(1) NOT NULL DEFAULT 0,
	JSON_DATA JSON CHECK (JSON_VALID(json_data)),
	CONSTRAINT pk_users
		PRIMARY KEY ( NR_ID ),
	UNIQUE KEY uk_username ( STR_USERNAME ),
	UNIQUE KEY uk_email ( STR_EMAIL )
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS todos (
	NR_ID BINARY(16) NOT NULL DEFAULT UUID(),
	DATE_CREATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	STR_MSG VARCHAR(1024),
	DATE_DUE TIMESTAMP NULL,
	STATUS_DONE TINYINT(1) NOT NULL DEFAULT 0,
	NR_LIST_ID BINARY(16) NOT NULL,
	JSON_DATA JSON CHECK (JSON_VALID(json_data)),
	CONSTRAINT pk_todo
		PRIMARY KEY (NR_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS user_roles (
	NR_USER_ID BINARY(16) NOT NULL,
	NR_ROLE_ID BINARY(16) NOT NULL,
	CONSTRAINT pk_user_role_id
		PRIMARY KEY ( NR_USER_ID, NR_ROLE_ID )
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS workspace_users (
	NR_WORKSPACE_ID BINARY(16) NOT NULL,
	NR_USER_ID BINARY(16) NOT NULL,
	CONSTRAINT pk_workspace_users_id
		PRIMARY KEY ( NR_WORKSPACE_ID, NR_USER_ID )
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS workspace_lists (
	NR_WORKSPACE_ID BINARY(16) NOT NULL,
	NR_LIST_ID BINARY(16) NOT NULL,
	CONSTRAINT pk_workspace_lists_id
		PRIMARY KEY ( NR_WORKSPACE_ID, NR_LIST_ID )
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS list_todos (
	NR_LIST_ID BINARY(16) NOT NULL,
	NR_TODO_ID BINARY(16) NOT NULL,
	CONSTRAINT pk_list_todos_id
		PRIMARY KEY ( NR_LIST_ID, NR_TODO_ID )
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS forgotpasswordtokens (
	NR_ID BINARY(16) NOT NULL DEFAULT UUID(),
	NR_USER_ID BINARY(16) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE IF NOT EXISTS useractivationtokens (
	NR_ID BINARY(16) NOT NULL DEFAULT UUID(),
	NR_USER_ID BINARY(16) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
