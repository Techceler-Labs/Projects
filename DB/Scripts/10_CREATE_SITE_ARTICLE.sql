CREATE TABLE SITE_ARTICLE
(
    ARTICLE_ID int(11)  NOT NULL,
    SITE_ID int(11)  NOT NULL,
    PRIMARY KEY (ARTICLE_ID, SITE_ID)
);