DROP TABLE  IF EXISTS SITE_IMAGE ;
CREATE TABLE  SITE_IMAGE  (
    SITE_ID INT(11) NOT NULL,
    IMAGE_ID INT(11)  NOT NULL,
    PRIMARY KEY (SITE_ID,IMAGE_ID)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;