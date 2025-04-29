-- ******************************************************
-- 
--                  SPRING SECURITY 
-- 
-- ******************************************************

INSERT INTO ROLES (ID, NAME) VALUES (1, 'SUPER_ADMIN');
INSERT INTO ROLES (ID, NAME) VALUES (2, 'ADMIN');
INSERT INTO ROLES (ID, NAME) VALUES (3, 'CLIENTE');

/*
  	role						        username	password        enabled
 	========================================================================================================
 	SUPER_ADMIN, ADMIN, CLIENTE	    	u1		    password
 	SUPER_ADMIN, ADMIN	    	        u2		    password
 	SUPER_ADMIN			                u3		    password
 	ADMIN			                    u4		    password
 	CLIENTE                        		u5   	    password
 	CLIENTE		                        u6		    password		(this user is disabled)
 */

INSERT INTO USERS (ID, USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, EMAIL, TELEFONO, ENABLED, LAST_PASSWORD_RESET_DATE) VALUES
(1, 'u1', '$2a$10$0CDIaJi.j4HWgba1uwkNa.pwgxFzmiQATMXVWnj5Epi4HZCZlhM7C', 'Pepín', 'Gálvez Ridruejuela', 'u1@gmail.com', '+34 636598871', 1, PARSEDATETIME('01-01-2016', 'dd-MM-yyyy')),
(2, 'u2', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'Montse', 'Queralt Conejero', 'pingo@pingo.com','687253062', 1, PARSEDATETIME('01-01-2016', 'dd-MM-yyyy')),
(3, 'u3', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'Carlota', 'Fernández Olaz', 'pingo@pingo.com','962207855', 1, PARSEDATETIME('01-01-2016', 'dd-MM-yyyy')),
(4, 'u4', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'Honorio', 'Martín Salvador', 'disabled@user.com', '607253012', 1, PARSEDATETIME('01-01-2016', 'dd-MM-yyyy')),
(5, 'u5', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'Marta', 'Olmos Medina', 'marta@admin.com','93220909872', 1, PARSEDATETIME('01-01-2016', 'dd-MM-yyyy')),
(6, 'u6', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'José Ramón', 'Fernández Grimal', 'admin@admin.com','629457021', 0, PARSEDATETIME('01-01-2016', 'dd-MM-yyyy'));

INSERT INTO USER_ROLES (ID_USER, ID_ROL) VALUES
(1,1),
(1,2),
(1,3),
(2,1),
(2,2),
(3,1),
(4,2),
(5,3),
(6,3);