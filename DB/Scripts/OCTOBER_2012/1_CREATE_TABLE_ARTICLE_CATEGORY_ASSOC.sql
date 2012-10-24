--CREATES TABLE TO MAP ARTICLE AND CATEGORY
--FIXED THE ERROR CAUSED BY ADDING THE SECOND FK CONSTRAINT DUE TO INCORRECT TABLE NAME (PAGE).
CREATE TABLE CMS.ARTICLE_CATEGORY_ASSOCIATION 
(
    CATEGORY_ID INT(11) NOT NULL,
    ARTICLE_ID INT(11) NOT NULL,
CONSTRAINT FK_CATEGORY_ARTLCATEGORYASSOC
FOREIGN KEY (CATEGORY_ID) 
REFERENCES CATEGORY(CATEGORY_ID),
    CONSTRAINT FK_ARTICLE_ARTLCATEGORYASSOC 
    FOREIGN KEY (ARTICLE_ID)  
    REFERENCES ARTICLE(ARTICLE_ID),
    PRIMARY KEY (CATEGORY_ID,ARTICLE_ID),
INDEX (CATEGORY_ID,ARTICLE_ID)
);
