CREATE TABLE `CMS`.`META_TAGS` (
    `META_TAG_ID` INT(11)  NOT NULL AUTO_INCREMENT,
    `ATTRIBUTE` VARCHAR(45)  NOT NULL,
   `ATTRIBUTE_VALUE` VARCHAR(500) NOT NULL,
    `ATTRIBUTE_CONTENT_VALUE` VARCHAR(500) NOT NULL,
    `LEVEL`  VARCHAR (45)  NOT NULL,
    `ASSOCIATED_ID` INT(11) NOT NULL,
    `SITE_ID` INT(11) NOT NULL,
    PRIMARY KEY (`META_TAG_ID`),
    CONSTRAINT FK_SITE_META_ASSOC
            FOREIGN KEY(`SITE_ID`)  
            REFERENCES SITE(`SITE_ID`)
);