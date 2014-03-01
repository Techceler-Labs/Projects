CREATE DATABASE `modules` /*!40100 DEFAULT CHARACTER SET latin1 */;

CREATE TABLE `modules`.`modules` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(250) NOT NULL,
  `DESCRIPTION` varchar(5000) DEFAULT NULL,
  `UUID` varchar(150) NOT NULL,
  `IS_ENABLED` tinyint(1) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UUID` (`UUID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;