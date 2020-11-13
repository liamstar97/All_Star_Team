-- -----------------------------------------------------------------------------------------------------------
-- This script creates the required schemas and and tables for the Allstar_Team DB if the do not already exist
-- -----------------------------------------------------------------------------------------------------------

# Create and use schema if it does not already exist
CREATE SCHEMA IF NOT EXISTS Allstar_Team;
USE Allstar_Team;

# Create table CHAMPIONSHIP_TEAM if it does not already exist
CREATE TABLE IF NOT EXISTS CHAMPIONSHIP_TEAM
(
  Id                  INT UNIQUE    NOT NULL,
  Team_name           VARCHAR(45)   NOT NULL,
  Coach_ssn           INT UNIQUE    NOT NULL,
  Assistant_Coach_Ssn INT UNIQUE,
  University          VARCHAR(45)   NOT NULL,
  Team_rank           INT UNIQUE    NOT NULL,
  Win                 INT DEFAULT 0 NOT NULL,
  Lost                INT DEFAULT 0 NOT NULL,
  Tie                 INT DEFAULT 0 NOT NULL,
  CONSTRAINT PK_Team_Id
    PRIMARY KEY (Id)
);

# Create table PLAYERS if it does not already exist
CREATE TABLE IF NOT EXISTS PLAYERS
(
  Ssn          INT UNIQUE    NOT NULL,
  Team_id      INT UNIQUE    NOT NULL,
  Name         VARCHAR(45)   NOT NULL,
  Address      VARCHAR(60)   NOT NULL,
  Birth_date   DATE          NOT NULL,
  Position     VARCHAR(45)   NOT NULL,
  University   VARCHAR(45)   NOT NULL,
  Years_team   INT DEFAULT 0 NOT NULL,
  Class        VARCHAR(45)   NOT NULL,
  Players_rank INT DEFAULT 0 NOT NULL,
  Time_allstar INT DEFAULT 0 NOT NULL,
  CONSTRAINT CHK_Players_Rank
    CHECK (Players_rank BETWEEN 0 AND 10),
  CONSTRAINT PK_Players_Ssn
    PRIMARY KEY (Ssn),
  CONSTRAINT FK_Players_Team_Id
    FOREIGN KEY (Team_id)
      REFERENCES CHAMPIONSHIP_TEAM (Id)
      ON DELETE RESTRICT
      ON UPDATE CASCADE
);

# Create table COACH if it does not already exist
CREATE TABLE IF NOT EXISTS COACH
(
  Ssn         INT UNIQUE    NOT NULL,
  Name        VARCHAR(45)   NOT NULL,
  Address     VARCHAR(60),
  Birth_date  DATE          NOT NULL,
  University  VARCHAR(45)   NOT NULL,
  Years_uni   INT DEFAULT 0 NOT NULL,
  Years_total INT DEFAULT 0 NOT NULL,
  Champs_wins INT DEFAULT 0 NOT NULL,
  Semifinals  INT DEFAULT 0 NOT NULL,
  CONSTRAINT PK_Coach_Ssn
    PRIMARY KEY (Ssn)
);

# Create table ASSISTANT_COACH if it does not already exist
CREATE TABLE IF NOT EXISTS ASSISTANT_COACH
(
  Ssn            INT UNIQUE    NOT NULL,
  Name           VARCHAR(45)   NOT NULL,
  Address        VARCHAR(60),
  Birth_date     DATE          NOT NULL,
  Team_id        INT UNIQUE    NOT NULL,
  University     VARCHAR(45)   NOT NULL,
  Years_uni      INT DEFAULT 0 NOT NULL,
  Years_total    INT DEFAULT 0 NOT NULL,
  Specialization VARCHAR(45)   NOT NULL,
  CONSTRAINT PK_Ass_Coach_Ssn
    PRIMARY KEY (Ssn)
);

# Add Coach_name as FK in table CHAMPIONSHIP_TEAM referencing COACH table
ALTER TABLE CHAMPIONSHIP_TEAM
  ADD CONSTRAINT FK_Team_Coach_Ssn
    FOREIGN KEY (Coach_ssn)
      REFERENCES COACH (Ssn)
      ON DELETE RESTRICT
      ON UPDATE CASCADE,
  Add CONSTRAINT FK_Team_Ass_Coach_Ssn
    FOREIGN KEY (Assistant_Coach_Ssn)
      REFERENCES ASSISTANT_COACH (Ssn)
      ON DELETE RESTRICT
      ON UPDATE CASCADE;

# Create table ALLSTAR_GAME if it does not already exist
CREATE TABLE IF NOT EXISTS ALLSTAR_GAME
(
  Date                DATE          NOT NULL,
  Location            VARCHAR(60)   NOT NULL,
  Team_id             INT UNIQUE    NOT NULL,
  Coach_ssn           INT UNIQUE    NOT NULL,
  Assistant_coach_ssn INT UNIQUE    NOT NULL,
  Score               INT DEFAULT 0 NOT NULL,
  CONSTRAINT PK_Game_Date_Location
    PRIMARY KEY (Date, Location),
  CONSTRAINT FK_Game_Players_Team_Id
    FOREIGN KEY (Team_id)
      REFERENCES CHAMPIONSHIP_TEAM (Id)
      ON DELETE RESTRICT
      ON UPDATE CASCADE,
  CONSTRAINT FK_Game_Coach_Ssn
    FOREIGN KEY (Coach_ssn)
      REFERENCES COACH (Ssn)
      ON DELETE RESTRICT
      ON UPDATE CASCADE,
  CONSTRAINT FK_Game_Ass_Coach_Ssn
    FOREIGN KEY (Assistant_coach_ssn)
      REFERENCES ASSISTANT_COACH (Ssn)
      ON DELETE RESTRICT
      ON UPDATE CASCADE
);

# Create table ALLSTAR_NOMINEES if it does not already exist
CREATE TABLE IF NOT EXISTS ALLSTAR_NOMINEES
(
  Ssn      INT UNIQUE  NOT NULL,
  Date     DATE        NOT NULL,
  Location VARCHAR(60) NOT NULL,
  CONSTRAINT PK_Nominees_Ssn_Date_Location
    PRIMARY KEY (Ssn, Date, Location),
  CONSTRAINT FK_Nominees_Date_Location
    FOREIGN KEY (Date, Location)
      REFERENCES ALLSTAR_GAME (Date, Location)
      ON DELETE RESTRICT
      ON UPDATE CASCADE,
  CONSTRAINT FK_Nominees_Players_Ssn
    FOREIGN KEY (Ssn)
      REFERENCES PLAYERS (Ssn)
      ON DELETE RESTRICT
      ON UPDATE CASCADE
);

# Drop statements for tables and schema
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE ALLSTAR_NOMINEES;
DROP TABLE ALLSTAR_GAME;
DROP TABLE ASSISTANT_COACH;
DROP TABLE COACH;
DROP TABLE CHAMPIONSHIP_TEAM;
DROP SCHEMA Allstar_Team;
SET FOREIGN_KEY_CHECKS = 1;