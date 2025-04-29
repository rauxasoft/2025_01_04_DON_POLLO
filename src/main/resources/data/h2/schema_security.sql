-- ******************************************************
-- 
--                  SPRING SECURITY 
-- 
-- ******************************************************

CREATE TABLE ROLES (
    ID 					       		BIGINT			    NOT NULL,
    NAME                       		VARCHAR(50) 		NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE USERS (
    ID 								BIGINT		        NOT NULL,
    USERNAME 						VARCHAR(50) 		NOT NULL UNIQUE,
    PASSWORD 						VARCHAR(120) 		NOT NULL,
    EMAIL 							VARCHAR(150) 		,
    TELEFONO						VARCHAR(50)			,
    ENABLED 						BOOLEAN 			NOT NULL,
    FIRST_NAME 						VARCHAR(50) 		NOT NULL,
    LAST_NAME 						VARCHAR(50) 		NOT NULL,
    LAST_PASSWORD_RESET_DATE 		DATETIME 			NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE USER_ROLES (
    ID_USER                     	BIGINT              NOT NULL,
    ID_ROL                      	BIGINT              NOT NULL,
    FOREIGN KEY (ID_USER) REFERENCES USERS (ID),
    FOREIGN KEY (ID_ROL) REFERENCES ROLES (ID)
);