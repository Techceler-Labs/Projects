ALTER TABLE TEMPLATE_DIRECTORY ADD ACCOUNT_ID INT(11) NOT NULL;
ALTER TABLE TEMPLATE_DIRECTORY ADD FOREIGN KEY (ACCOUNT_ID) REFERENCES ACCOUNT(ACCOUNT_ID);