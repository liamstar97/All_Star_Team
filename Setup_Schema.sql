-- -----------------------------------------------------------------------------------------------------------
-- This script creates the required schemas and and tables for the Allstar_Team DB if the do not already exist
-- -----------------------------------------------------------------------------------------------------------

# Create and use schema if it does not already exist
CREATE SCHEMA IF NOT EXISTS Allstar_Team;
USE Allstar_Team;

# Create table Championship_Teams if it does not already exist
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
    PRIMARY KEY (Id)
);

# Create table Players if it does not already exist
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
    FOREIGN KEY (Team_name)
        REFERENCES CHAMPIONSHIP_TEAM (Team_name),
    PRIMARY KEY (Ssn)
);

# Create table Coach if it does not already exist
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
    PRIMARY KEY (Ssn),
    FOREIGN KEY (Team_name)
        REFERENCES CHAMPIONSHIP_TEAM (Team_name)
);

# Add Coach_name as FK in table CHAMPIONSHIP_TEAM referencing COACH table
ALTER TABLE CHAMPIONSHIP_TEAM
    ADD FOREIGN KEY (Coach_name)
        REFERENCES COACH (Name);

# Create table Assistant_Coach if it does not already exist
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
    PRIMARY KEY (Ssn)
);

# Create table Allstar_Game if it does not already exist
CREATE TABLE IF NOT EXISTS ALLSTAR_GAME
(
    Date     DATE        NOT NULL,
    Location VARCHAR(60) NOT NULL,
    Team     VARCHAR(20) NOT NULL,
    Coach    VARCHAR(45) NOT NULL,
    Score    INT         NOT NULL,
    PRIMARY KEY (Date, Location),
    FOREIGN KEY (Team)
        REFERENCES PLAYERS (Team_name),
    FOREIGN KEY (Coach)
        REFERENCES COACH (Name)
);

# Create table Allstar_Team_Nominees if it does not already exist
CREATE TABLE IF NOT EXISTS ALLSTAR_NOMINEES
(
    Ssn          INT         NOT NULL,
    Date         DATE        NOT NULL,
    Location     VARCHAR(60) NOT NULL,
    Name         VARCHAR(45) NOT NULL,
    Address      VARCHAR(60) NOT NULL,
    Birth_date   DATE        NOT NULL,
    Position     INT         NOT NULL,
    University   VARCHAR(20) NOT NULL,
    Years_team   INT         NOT NULL,
    Class        VARCHAR(20) NOT NULL,
    Time_allstar INT         NOT NULL,
    FOREIGN KEY (Date, Location)
        REFERENCES ALLSTAR_GAME (Date, Location),
    FOREIGN KEY (Ssn, Name, Address, Birth_date, Position, University, Years_team, Class, Time_allstar)
        REFERENCES PLAYERS (Ssn, Name, Address, Birth_date, Position, University, Years_team, Class, Time_allstar),
    PRIMARY KEY (Ssn, Date, Location)
);