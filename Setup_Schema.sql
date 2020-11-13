-- -----------------------------------------------------------------------------------------------------------
-- This script creates the required schemas and and tables for the Allstar_Team DB if the do not already exist
-- -----------------------------------------------------------------------------------------------------------

# Create and use schema if it does not already exist
CREATE SCHEMA IF NOT EXISTS Allstar_Team;
USE Allstar_Team;

# Create table COACH if it does not already exist
CREATE TABLE IF NOT EXISTS COACH
(
  SSN         INT UNIQUE    NOT NULL,
  Name        VARCHAR(45)   NOT NULL,
  Address     VARCHAR(60),
  Birth_date  DATE          NOT NULL,
  University  VARCHAR(45)   NOT NULL,
  Years_uni   INT DEFAULT 0 NOT NULL,
  Years_total INT DEFAULT 0 NOT NULL,
  Champs_wins INT DEFAULT 0 NOT NULL,
  Semifinals  INT DEFAULT 0 NOT NULL,
  CONSTRAINT PK_COACH_SSN
    PRIMARY KEY (SSN)
);

# Create table ASSISTANT_COACH if it does not already exist
CREATE TABLE IF NOT EXISTS ASSISTANT_COACH
(
  SSN            INT UNIQUE    NOT NULL,
  Name           VARCHAR(45)   NOT NULL,
  Address        VARCHAR(60),
  Birth_date     DATE          NOT NULL,
  University     VARCHAR(45)   NOT NULL,
  Years_uni      INT DEFAULT 0 NOT NULL,
  Years_total    INT DEFAULT 0 NOT NULL,
  Specialization VARCHAR(45)   NOT NULL,
  CONSTRAINT PK_ASSISTANT_COACH_SSN
    PRIMARY KEY (SSN)
);

# Create table CHAMPIONSHIP_TEAM if it does not already exist
CREATE TABLE IF NOT EXISTS CHAMPIONSHIP_TEAM
(
  ID                  INT UNIQUE    NOT NULL,
  Team_name           VARCHAR(45)   NOT NULL,
  COACH_SSN           INT UNIQUE    NOT NULL,
  ASSISTANT_COACH_SSN INT UNIQUE,
  University          VARCHAR(45)   NOT NULL,
  Team_rank           INT UNIQUE    NOT NULL,
  Win                 INT DEFAULT 0 NOT NULL,
  Lost                INT DEFAULT 0 NOT NULL,
  Tie                 INT DEFAULT 0 NOT NULL,
  CONSTRAINT PK_TEAM_ID
    PRIMARY KEY (ID),
  CONSTRAINT FK_TEAM_COACH_SSN
    FOREIGN KEY (COACH_SSN)
      REFERENCES COACH (SSN)
      ON DELETE RESTRICT
      ON UPDATE CASCADE,
  CONSTRAINT FK_TEAM_ASSISTANT_COACH_SSN
    FOREIGN KEY (ASSISTANT_COACH_SSN)
      REFERENCES ASSISTANT_COACH (SSN)
      ON DELETE RESTRICT
      ON UPDATE CASCADE
);

# Create table PLAYERS if it does not already exist
CREATE TABLE IF NOT EXISTS PLAYERS
(
  SSN                   INT UNIQUE    NOT NULL,
  CHAMPIONSHIP_TEAMS_ID INT UNIQUE    NOT NULL,
  Name                  VARCHAR(45)   NOT NULL,
  Address               VARCHAR(60)   NOT NULL,
  Birth_date            DATE          NOT NULL,
  Position              VARCHAR(45)   NOT NULL,
  University            VARCHAR(45)   NOT NULL,
  Years_team            INT DEFAULT 0 NOT NULL,
  Class                 VARCHAR(45)   NOT NULL,
  Time_allstar          INT DEFAULT 0 NOT NULL,
  Player_rank           INT DEFAULT 0 NOT NULL,
  CONSTRAINT CHK_Player_Rank
    CHECK (Player_rank BETWEEN 0 AND 10),
  CONSTRAINT PK_PLAYERS_SSN
    PRIMARY KEY (SSN),
  CONSTRAINT FK_PLAYERS_TEAM_ID
    FOREIGN KEY (CHAMPIONSHIP_TEAMS_ID)
      REFERENCES CHAMPIONSHIP_TEAM (ID)
      ON DELETE RESTRICT
      ON UPDATE CASCADE
);

# Create table ALLSTAR_GAME if it does not already exist
CREATE TABLE IF NOT EXISTS ALLSTAR_GAME
(
  Date                  DATE                        NOT NULL,
  Location              VARCHAR(60)                 NOT NULL,
  CHAMPIONSHIP_TEAMS_ID INT UNIQUE                  NOT NULL,
  COACH_SSN             INT UNIQUE                  NOT NULL,
  ASSISTANT_COACH_SSN   INT UNIQUE,
  Score                 VARCHAR(10) DEFAULT '0 - 0' NOT NULL,
  CONSTRAINT PK_GAME_Date_Location
    PRIMARY KEY (Date, Location),
  CONSTRAINT FK_GAME_TEAM_ID
    FOREIGN KEY (CHAMPIONSHIP_TEAMS_ID)
      REFERENCES CHAMPIONSHIP_TEAM (ID)
      ON DELETE RESTRICT
      ON UPDATE CASCADE,
  CONSTRAINT FK_GAME_COACH_SSN
    FOREIGN KEY (COACH_SSN)
      REFERENCES COACH (SSN)
      ON DELETE RESTRICT
      ON UPDATE CASCADE,
  CONSTRAINT FK_GAME_ASSISTANT_COACH_SSN
    FOREIGN KEY (ASSISTANT_COACH_SSN)
      REFERENCES ASSISTANT_COACH (SSN)
      ON DELETE RESTRICT
      ON UPDATE CASCADE
);

# Create table ALLSTAR_NOMINEES if it does not already exist
CREATE TABLE IF NOT EXISTS ALLSTAR_NOMINEES
(
  PLAYERS_SSN           INT UNIQUE  NOT NULL,
  ALLSTAR_GAME_Date     DATE        NOT NULL,
  ALLSTAR_GAME_Location VARCHAR(60) NOT NULL,
  CONSTRAINT PK_NOMINEES_SSN_Date_Location
    PRIMARY KEY (PLAYERS_SSN, ALLSTAR_GAME_Date, ALLSTAR_GAME_Location),
  CONSTRAINT FK_NOMINEES_GAME_Date_Location
    FOREIGN KEY (ALLSTAR_GAME_Date, ALLSTAR_GAME_Location)
      REFERENCES ALLSTAR_GAME (Date, Location)
      ON DELETE RESTRICT
      ON UPDATE CASCADE,
  CONSTRAINT FK_NOMINEES_PLAYERS_SSN
    FOREIGN KEY (PLAYERS_SSN)
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