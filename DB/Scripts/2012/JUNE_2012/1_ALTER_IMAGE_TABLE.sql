ALTER TABLE IMAGE DROP COLUMN IMAGE_UUIID;
ALTER TABLE IMAGE ADD IMAGE_ID INT NOT NULL PRIMARY KEY  AUTO_INCREMENT;
ALTER TABLE IMAGE ADD IMAGE_UUID VARCHAR(75) NOT NULL UNIQUE AFTER IMAGE_ID;

