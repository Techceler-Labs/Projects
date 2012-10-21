--CREATES TABLE TO MAP ARTICLE AND CATEGORY

CREATE TABLE CMS.ARTICLE_CATEGORY_ASSOCIATION 
(
    CATEGORY_ID INT(11) NOT NULL,
    ARTICLE_ID INT(11) NOT NULL,
	ADD CONSTRAINT FK_CATEGORY_ARTLCATEGORYASSOC
	FOREIGN KEY (CATEGORY_ID) 
	REFERENCES CATEGORY(CATEGORY_ID),
	ADD CONSTRAINT FK_ARTICLE_ARTLCATEGORYASSOC
	FOREIGN KEY (ARTICLE_ID) 
	REFERENCES PAGE(ARTICLE_ID),
    PRIMARY KEY (CATEGORY_ID,ARTICLE_ID),
	INDEX (CATEGORY_ID,ARTICLE_ID)
);