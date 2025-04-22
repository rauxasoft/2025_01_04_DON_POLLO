-- ******************************************************
-- 
--                       MODEL 
-- 
-- ******************************************************

CREATE TABLE REQUEST_LOGS(

	ID				BIGINT				NOT NULL,
	METHOD			VARCHAR(20)			,
	URL				VARCHAR(250)		,
	IP				VARCHAR(250)		,
	ELAPSED_TIME	BIGINT				,
	STATUS_CODE		INTEGER				,
	
	PRIMARY KEY(ID)

);