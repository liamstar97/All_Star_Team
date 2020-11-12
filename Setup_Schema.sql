-- -----------------------------------------------------------------------------------------------------------
-- This script creates the required schemas and and tables for the Allstar_Team DB if the do not already exist
-- -----------------------------------------------------------------------------------------------------------

# Create and use schema if it does not already exist
CREATE SCHEMA IF NOT EXISTS Allstar_Team;
USE Allstar_Team;

# Create table CHAMPIONSHIP_TEAM if it does not already exist
CREATE TABLE IF NOT EXISTS CHAMPIONSHIP_TEAM
(
  Id         INT                NOT NULL,
  Team_name  VARCHAR(20) UNIQUE NOT NULL,
  Coach_name VARCHAR(45)        NOT NULL,
  University VARCHAR(20)        NOT NULL,
  `Rank`     INT                NOT NULL,
  Win        INT                NOT NULL,
  Lost       INT                NOT NULL,
  Tie        INT                NOT NULL,
  CONSTRAINT PK_Team_Id
    PRIMARY KEY (Id)
);

# Create table PLAYERS if it does not already exist
CREATE TABLE IF NOT EXISTS PLAYERS
(
  Ssn          INT         NOT NULL,
  Team_name    VARCHAR(20) NOT NULL,
  Name         VARCHAR(45) NOT NULL,
  Address      VARCHAR(60) NOT NULL,
  Birth_date   DATE        NOT NULL,
  Position     VARCHAR(20) NOT NULL,
  University   VARCHAR(20) NOT NULL,
  Years_team   INT         NOT NULL,
  Class        VARCHAR(20) NOT NULL,
  Time_allstar INT         NOT NULL,
  CONSTRAINT PK_Players_Ssn
    PRIMARY KEY (Ssn),
  CONSTRAINT FK_Players_Team_Name
    FOREIGN KEY (Team_name)
      REFERENCES CHAMPIONSHIP_TEAM (Team_name)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

# Creates Players_Info index to more easily retrieve player info
CREATE INDEX Players_Info
  ON PLAYERS (Ssn, Name, Address, Birth_date, Position, University, Years_team, Class, Time_allstar);

# Create table COACH if it does not already exist
CREATE TABLE IF NOT EXISTS COACH
(
  Ssn         INT                NOT NULL,
  Team_name   VARCHAR(20)        NOT NULL,
  Name        VARCHAR(45) UNIQUE NOT NULL,
  Address     VARCHAR(60),
  Birth_date  DATE               NOT NULL,
  University  VARCHAR(20)        NOT NULL,
  Years_uni   INT                NOT NULL,
  Years_total INT                NOT NULL,
  Champs_wins INT                NOT NULL,
  Semifinals  INT                NOT NULL,
  CONSTRAINT PK_Coach_Ssn
    PRIMARY KEY (Ssn),
  CONSTRAINT FK_Coach_Team_Name
    FOREIGN KEY (Team_name)
      REFERENCES CHAMPIONSHIP_TEAM (Team_name)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

# Add Coach_name as FK in table CHAMPIONSHIP_TEAM referencing COACH table
ALTER TABLE CHAMPIONSHIP_TEAM
  ADD CONSTRAINT FK_Team_Coach_Name
    FOREIGN KEY (Coach_name)
      REFERENCES COACH (Name)
      ON DELETE CASCADE
      ON UPDATE CASCADE;

# Create table ASSISTANT_COACH if it does not already exist
CREATE TABLE IF NOT EXISTS ASSISTANT_COACH
(
  Ssn            INT         NOT NULL,
  Name           VARCHAR(45) NOT NULL,
  Address        VARCHAR(60) NOT NULL,
  Birth_date     DATE        NOT NULL,
  University     VARCHAR(20) NOT NULL,
  Years_uni      INT         NOT NULL,
  Years_total    INT         NOT NULL,
  Specialization INT         NOT NULL,
  CONSTRAINT FK_Ass_Coach_Ssn
    PRIMARY KEY (Ssn)
);

# Create table ALLSTAR_GAME if it does not already exist
CREATE TABLE IF NOT EXISTS ALLSTAR_GAME
(
  Date     DATE        NOT NULL,
  Location VARCHAR(60) NOT NULL,
  Team     VARCHAR(20) NOT NULL,
  Coach    VARCHAR(45) NOT NULL,
  Score    INT         NOT NULL,
  CONSTRAINT PK_Game_Date_Location
    PRIMARY KEY (Date, Location),
  CONSTRAINT FK_Game_Players_Team
    FOREIGN KEY (Team)
      REFERENCES PLAYERS (Team_name),
  CONSTRAINT FK_Game_Coach_Name
    FOREIGN KEY (Coach)
      REFERENCES COACH (Name)
);

# Create table ALLSTAR_NOMINEES if it does not already exist
CREATE TABLE IF NOT EXISTS ALLSTAR_NOMINEES
(
  Ssn          INT         NOT NULL,
  Date         DATE        NOT NULL,
  Location     VARCHAR(60) NOT NULL,
  Name         VARCHAR(45) NOT NULL,
  Address      VARCHAR(60) NOT NULL,
  Birth_date   DATE        NOT NULL,
  Position     VARCHAR(20) NOT NULL,
  University   VARCHAR(20) NOT NULL,
  Years_team   INT         NOT NULL,
  Class        VARCHAR(20) NOT NULL,
  Time_allstar INT         NOT NULL,
  CONSTRAINT PK_Nominees_Ssn_Date_Location
    PRIMARY KEY (Ssn, Date, Location),
  CONSTRAINT FK_Nominees_Date_Location
    FOREIGN KEY (Date, Location)
      REFERENCES ALLSTAR_GAME (Date, Location)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  CONSTRAINT FK_Nominees_Players_Info
    FOREIGN KEY (Ssn, Name, Address, Birth_date, Position, University, Years_team, Class, Time_allstar)
      REFERENCES PLAYERS (Ssn, Name, Address, Birth_date, Position, University, Years_team, Class, Time_allstar)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);