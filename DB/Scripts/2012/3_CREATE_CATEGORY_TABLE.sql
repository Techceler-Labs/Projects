
CREATE TABLE `CMS`.`CATEGORY` (
  `CATEGORY_ID` int(11) NOT NULL,
  `CATEGORY_NAME` varchar(45) DEFAULT NULL,
  `PARENT_CATEGORY_ID` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`CATEGORY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1

