CREATE  TABLE `modules`.`ACCOUNT_TYPE_MODULE` (
  `ACCOUNT_TYPE_MODULE_ID` INT(11) NOT NULL AUTO_INCREMENT ,
  `ACCOUNT_TYPE_ID` INT(11) NOT NULL ,
  `MODULE_ID` INT(11) NOT NULL ,
  PRIMARY KEY (`ACCOUNT_TYPE_MODULE_ID`) ,
  INDEX `FK_ATM_ACCOUNT_TYPE_idx` (`ACCOUNT_TYPE_ID` ASC) ,
  INDEX `FK_ATM_MODULE_idx` (`MODULE_ID` ASC) ,
  CONSTRAINT `FK_ATM_ACCOUNT_TYPE`
    FOREIGN KEY (`ACCOUNT_TYPE_ID` )
    REFERENCES `modules`.`account_type` (`ACCOUNT_TYPE_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_ATM_MODULE`
    FOREIGN KEY (`MODULE_ID` )
    REFERENCES `modules`.`module` (`MODULE_ID` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
