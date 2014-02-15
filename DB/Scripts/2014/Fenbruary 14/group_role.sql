
CREATE TABLE IF NOT EXISTS `group_role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `GROUP_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_GROUP_ROLE` (`GROUP_ID`,`ROLE_ID`),
  KEY `FK_ROLE_GRPROLE` (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

ALTER TABLE `group_role`
  ADD CONSTRAINT `FK_GRP_GRPROLE` FOREIGN KEY (`GROUP_ID`) REFERENCES `group` (`GROUP_ID`),
  ADD CONSTRAINT `FK_ROLE_GRPROLE` FOREIGN KEY (`ROLE_ID`) REFERENCES `role` (`ROLE_ID`);