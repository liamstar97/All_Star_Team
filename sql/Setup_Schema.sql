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
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

# Create table PLAYERS if it does not already exist
CREATE TABLE IF NOT EXISTS PLAYERS
(
  SSN                   INT UNIQUE    NOT NULL,
  CHAMPIONSHIP_TEAMS_ID INT           NOT NULL,
  Name                  VARCHAR(45)   NOT NULL,
  Address               VARCHAR(60)   NOT NULL,
  Birth_date            DATE          NOT NULL,
  Position              VARCHAR(45)   NOT NULL,
  University            VARCHAR(45)   NOT NULL,
  Years_team            INT DEFAULT 0 NOT NULL,
  Class                 VARCHAR(45)   NOT NULL,
  Time_allstar          INT DEFAULT 0 NOT NULL,
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
  Date                  		DATE                        NOT NULL,
  Location              		VARCHAR(60)                 NOT NULL,
  CHAMPIONSHIP_TEAMS_ID_WINNER 	INT                         NOT NULL,
  CHAMPIONSHIP_TEAMS_ID_LOSER	INT							NOT NULL,
  COACH_SSN             		INT                         NOT NULL,
  ASSISTANT_COACH_SSN   		INT,
  Score                 		VARCHAR(10) DEFAULT '0 - 0' NOT NULL,
  CONSTRAINT PK_GAME_Date_Location
    PRIMARY KEY (Date, Location),
  CONSTRAINT FK_GAME_TEAM_ID_WINNER
    FOREIGN KEY (CHAMPIONSHIP_TEAMS_ID_WINNER)
      REFERENCES CHAMPIONSHIP_TEAM (ID)
      ON DELETE RESTRICT
      ON UPDATE CASCADE,
  CONSTRAINT FK_GAME_TEAM_ID_LOSER
    FOREIGN KEY (CHAMPIONSHIP_TEAMS_ID_LOSER)
      REFERENCES CHAMPIONSHIP_TEAM (ID)
      ON DELETE RESTRICT
      ON UPDATE CASCADE,
  CONSTRAINT FK_GAME_COACH_SSN
    FOREIGN KEY (COACH_SSN)
      REFERENCES COACH (SSN)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT,
  CONSTRAINT FK_GAME_ASSISTANT_COACH_SSN
    FOREIGN KEY (ASSISTANT_COACH_SSN)
      REFERENCES ASSISTANT_COACH (SSN)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT
);

# Create table ALLSTAR_NOMINEES if it does not already exist
CREATE TABLE IF NOT EXISTS ALLSTAR_NOMINEES
(
  PLAYERS_SSN           INT UNIQUE  NOT NULL,
  ALLSTAR_GAME_Date     DATE        NOT NULL,
  ALLSTAR_GAME_Location VARCHAR(60) NOT NULL,
  PLAYER_Rank           INT         NOT NULL,
  CONSTRAINT CHK_PLAYER_Rank
    CHECK (PLAYER_rank BETWEEN 1 AND 10),
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
      ON DELETE CASCADE
      ON UPDATE RESTRICT
);

-- Adding Triggers for wins/losses/ties

DELIMITER $$

CREATE TRIGGER Winning_Team
AFTER INSERT ON ALLSTAR_GAME FOR EACH ROW
BEGIN
	UPDATE CHAMPIONSHIP_TEAM
    SET Win = Win + 1
	WHERE
		CHAMPIONSHIP_TEAM.ID = NEW.CHAMPIONSHIP_TEAMS_ID_WINNER;
END$$

CREATE TRIGGER Losing_Team
AFTER INSERT ON ALLSTAR_GAME FOR EACH ROW
BEGIN
	UPDATE CHAMPIONSHIP_TEAM
    SET Lost = Lost + 1
	WHERE
		CHAMPIONSHIP_TEAM.ID = NEW.CHAMPIONSHIP_TEAMS_ID_LOSER;
END$$

CREATE TRIGGER Tie_Game
AFTER INSERT ON ALLSTAR_GAME FOR EACH ROW
BEGIN
	UPDATE CHAMPIONSHIP_TEAM
    SET Tie = Tie + 1
	WHERE
		NEW.Score = '0 - 0' AND
		(ID = NEW.CHAMPIONSHIP_TEAMS_ID_LOSER OR ID = NEW.CHAMPIONSHIP_TEAMS_ID_LOSER);
END$$

DELIMITER ;

-- -----------------------------------------------------------------------------------------------------------
-- The following inserts mock data into the previously created schema
-- -----------------------------------------------------------------------------------------------------------

# Insert Coaches
INSERT INTO `COACH`
VALUES (666666666, 'Tudor Saunders', '123 Peachtree, Atlanta, GA', '1990-01-01', 'WWU', 3, 8, 3, 2),
       (666666667, 'Larry Bowers', '111 Allgood, Atlanta, GA', '1990-01-01', 'UW', 3, 8, 3, 2),
       (666666668, 'Jeremiah Reyna', '2342 May, Atlanta, GA', '1990-01-01', 'CWU', 1, 6, 2, 1),
       (666666669, 'Charly Stark', '134 Pelham, Milwaukee, WI', '1990-01-01', 'UBC', 1, 6, 4, 3),
       (666666670, 'Maliha Marriott', '266 McGrady, Milwaukee, WI', '1990-01-01', 'SPU', 4, 9, 3, 2),
       (666666671, 'Honey Boone', '112 Third St, Milwaukee, WI', '1990-01-01', 'EWU', 2, 7, 2, 1),
       (666666672, 'Ronaldo Hayden', '263 Mayberry, Milwaukee, WI', '1990-01-01', 'Gonzaga', 6, 11, 2, 1);

# Insert Assistant Coaches
INSERT INTO `ASSISTANT_COACH`
VALUES (15309766, 'Warren Cervantes', '492 Railroad Dr. Westmont, IL 60559', '2016-02-17', 'UBC', 2, 3, 'Defence'),
       (24449374, 'Dustin Ponce', '92 Rose St. Thomasville, NC 27360', '2004-07-16', 'SPU', 1, 2, 'Defence'),
       (118072692, 'Honor Munro', '376 East Rosewood Road Fort Walton Beach, FL 32547', '2014-01-11', 'UW', 4, 6,
        'Offence'),
       (406210010, 'Lorna Vang', '32 Smoky Hollow Drive Cheshire, CT 06410', '2006-04-08', 'CWU', 3, 5, 'Offence'),
       (534615656, 'Freddie Holman', '525 Hall Street Circle Pines, MN 55014', '2006-06-07', 'WWU', 5, 8, 'Defence'),
       (617043149, 'Miguel Rowland', '52 Newcastle Road Lancaster, NY 14086', '2010-09-05', 'Gonzaga', 2, 3, 'Offence'),
       (765040826, 'Massimo Rojas', '18 Smith Store Ave. Hope Mills, NC 28348', '2010-02-14', 'EWU', 8, 10, 'Defence');

# Insert Championship Teams
INSERT INTO `CHAMPIONSHIP_TEAM`
VALUES (1, 'Blitzburg Steelheads', 666666666, 534615656, 'WWU', 0, 0, 0, 0),
       (2, 'Brawltimore Razors', 666666667, 118072692, 'UW', 2, 0, 0, 0),
       (3, 'Cracksumskull Jugulars', 666666668, 406210010, 'CWU', 3, 0, 0, 0),
       (4, 'Full Metal Mayhem', 666666669, 15309766, 'UBC', 4, 0, 0, 0),
       (5, 'Galaxy Chaos', 666666670, 24449374, 'SPU', 5, 0, 0, 0),
       (6, 'Insane Cults', 666666671, 765040826, 'EWU', 6, 0, 0, 0),
       (7, 'Hexxon Oilers', 666666672, 617043149, 'Gonzaga', 7, 0, 0, 0);

# Insert Players
INSERT INTO `PLAYERS`
VALUES (111111100, 1, 'Jared James', '123 Peachtree, Atlanta, GA', '1966-10-10', 'forward', 'WWU', 3, 'freshman', 3),
       (111111101, 1, 'Jon Jones', '111 Allgood, Atlanta, GA', '1967-11-14', 'center', 'WWU', 3, 'sophomore', 3),
       (111111102, 1, 'Justin Mark', '2342 May, Atlanta, GA', '1966-01-12', 'quarterback', 'WWU', 1, 'junior', 2),
       (222222200, 1, 'Evan Wallis', '134 Pelham, Milwaukee, WI', '1958-01-16', 'offensive guard', 'WWU', 1, 'senior',
        4),
       (222222201, 1, 'Josh Zell', '266 McGrady, Milwaukee, WI', '1954-05-22', 'defense', 'WWU', 4, 'freshman', 3),
       (222222203, 1, 'Tom Brand', '112 Third St, Milwaukee, WI', '1966-12-16', 'running back', 'WWU', 2, 'sophomore',
        2),
       (222222204, 1, 'Jenny Vos', '263 Mayberry, Milwaukee, WI', '1967-11-11', 'wide receiver', 'WWU', 6, 'junior', 2),
       (222222205, 2, 'Chris Carter', '565 Jordan, Milwaukee, WI', '1960-03-21', 'forward', 'UW', 2, 'senior', 4),
       (333333300, 2, 'Kim Grace', '6677 Mills Ave, Sacramento, CA', '1970-10-23', 'center', 'UW', 6, 'freshman', 2),
       (333333301, 2, 'Jeff Chase', '145 Bradbury, Sacramento, CA', '1970-01-07', 'quarterback', 'UW', 6, 'sophomore',
        3),
       (444444400, 2, 'Alex Freed', '4333 Pillsbury, Milwaukee, WI', '1950-10-09', 'offensive guard', 'UW', 4, 'junior',
        1),
       (444444401, 2, 'Bonnie Bays', '111 Hollow, Milwaukee, WI', '1956-06-19', 'defense', 'UW', 4, 'senior', 4),
       (444444402, 2, 'Alec Best', '233 Solid, Milwaukee, WI', '1966-06-18', 'running back', 'UW', 3, 'freshman', 4),
       (444444403, 2, 'Sam Snedden', '987 Windy St, Milwaukee, WI', '1977-07-31', 'wide receiver', 'UW', 3, 'sophomore',
        2),
       (555555500, 3, 'John James', '7676 Bloomington, Sacramento, CA', '1975-06-30', 'forward', 'CWU', 4, 'junior', 1),
       (555555501, 3, 'Nandita Ball', '222 Howard, Sacramento, CA', '1969-04-16', 'center', 'CWU', 3, 'senior', 3),
       (666666600, 3, 'Bob Bender', '8794 Garfield, Chicago, IL', '1968-04-17', 'quarterback', 'CWU', 3, 'freshman', 3),
       (666666601, 3, 'Jill Jarvis', '6234 Lincoln, Chicago, IL', '1966-01-14', 'offensive guard', 'CWU', 5,
        'sophomore', 2),
       (666666602, 3, 'Kate King', '1976 Boone Trace, Chicago, IL', '1966-04-16', 'defense', 'CWU', 2, 'junior', 1),
       (666666603, 3, 'Lyle Leslie', '417 Hancock Ave, Chicago, IL', '1963-06-09', 'running back', 'CWU', 4, 'senior',
        4),
       (666666604, 3, 'Billie King', '556 Washington, Chicago, IL', '1960-01-01', 'wide receiver', 'CWU', 3, 'freshman',
        2),
       (666666605, 4, 'Jon Kramer', '1988 Windy Creek, Seattle, WA', '1964-08-22', 'forward', 'UBC', 4, 'sophomore', 2),
       (666666606, 4, 'Ray King', '213 Delk Road, Seattle, WA', '1949-08-16', 'center', 'UBC', 2, 'junior', 2),
       (666666607, 4, 'Gerald Small', '122 Ball Street, Dallas, TX', '1962-05-15', 'quarterback', 'UBC', 2, 'senior',
        3),
       (666666608, 4, 'Arnold Head', '233 Spring St, Dallas, TX', '1967-05-19', 'offensive guard', 'UBC', 4, 'freshman',
        2),
       (666666609, 4, 'Helga Pataki', '101 Holyoke St, Dallas, TX', '1969-03-11', 'defense', 'UBC', 4, 'sophomore', 2),
       (666666610, 4, 'Naveen Drew', '198 Elm St, Philadelphia, PA', '1970-05-23', 'running back', 'UBC', 2, 'junior',
        2),
       (666666611, 4, 'Carl Reedy', '213 Ball St, Philadelphia, PA', '1977-06-21', 'wide receiver', 'UBC', 5, 'senior',
        3),
       (666666612, 5, 'Sammy Hall', '433 Main Street, Miami, FL', '1970-01-11', 'forward', 'SPU', 5, 'freshman', 2),
       (666666613, 5, 'Red Bacher', '196 Elm Street, Miami, FL', '1980-05-21', 'center', 'SPU', 5, 'sophomore', 1),
       (666666614, 5, 'James Borg', '450 Stone, Houston, TX', '1927-11-10', 'quarterback', 'SPU', 6, 'junior', 2),
       (666666615, 5, 'Jeffrey Geoffrey', '451 Stone, Houston, TX', '1927-11-11', 'offensive guard', 'SPU', 4, 'senior',
        1),
       (666666616, 5, 'VIKKI SCHEUERLEIN', '452 Stone, Houston, TX', '1927-11-12', 'defense', 'SPU', 6, 'freshman', 1),
       (666666617, 5, 'VERLA SCHEXNYDER', '453 Stone, Houston, TX', '1927-11-13', 'running back', 'SPU', 1, 'sophomore',
        1),
       (666666618, 5, 'ROSELYN SCHIEFERDECKER', '454 Stone, Houston, TX', '1927-11-14', 'wide receiver', 'SPU', 5,
        'junior', 2),
       (666666619, 6, 'MELVINA SCHINTZIUS', '455 Stone, Houston, TX', '1927-11-15', 'forward', 'EWU', 1, 'senior', 3),
       (666666620, 6, 'JANNETTE SCHLENVOGT', '456 Stone, Houston, TX', '1927-11-16', 'center', 'EWU', 1, 'freshman', 4),
       (666666621, 6, 'GINNY SCHNEIR', '457 Stone, Houston, TX', '1927-11-17', 'quarterback', 'EWU', 3, 'sophomore', 1),
       (666666622, 6, 'ANISSA SCUTCHFIELD', '458 Stone, Houston, TX', '1927-11-18', 'offensive guard', 'EWU', 6,
        'junior', 2),
       (666666623, 6, 'VIVIANA SEADORF', '459 Stone, Houston, TX', '1927-11-19', 'defense', 'EWU', 1, 'senior', 3),
       (666666624, 6, 'TWYLA SEARE', '460 Stone, Houston, TX', '1927-11-20', 'running back', 'EWU', 1, 'freshman', 1),
       (666666625, 6, 'SHARYN SETCHEL', '461 Stone, Houston, TX', '1927-11-21', 'wide receiver', 'EWU', 4, 'sophomore',
        1),
       (666666626, 7, 'CHANTAL SETKA', '462 Stone, Houston, TX', '1927-11-22', 'forward', 'Gonzaga', 2, 'junior', 4),
       (666666627, 7, 'NIKI SGARLATO', '463 Stone, Houston, TX', '1927-11-23', 'center', 'Gonzaga', 6, 'senior', 4),
       (666666628, 7, 'MAUD SHAFRON', '464 Stone, Houston, TX', '1927-11-24', 'quarterback', 'Gonzaga', 2, 'freshman',
        1),
       (666666629, 7, 'LIZETTE SHAMLOO', '465 Stone, Houston, TX', '1927-11-25', 'offensive guard', 'Gonzaga', 4,
        'sophomore', 4),
       (666666630, 7, 'LINDY SHCHERBAKOV', '466 Stone, Houston, TX', '1927-11-26', 'defense', 'Gonzaga', 3, 'junior',
        1),
       (666666631, 7, 'PATRICIA PATRICIA', '467 Stone, Houston, TX', '1927-11-27', 'running back', 'Gonzaga', 1,
        'senior', 4),
       (666666632, 7, 'PATRICIA JOHNSON', '468 Stone, Houston, TX', '1927-11-28', 'wide receiver', 'Gonzaga', 6,
        'freshman', 3);

# Insert Allstar Games
INSERT INTO `ALLSTAR_GAME`
VALUES ('2020-11-01', 'Blitzburg', 5, 1, 666666670, 24449374, '8000 - 7'),
       ('2020-11-02', 'Brawltimore', 4, 7,  666666669, 15309766, '90 - 43'),
       ('2020-11-03', 'Seattle', 6, 1, 666666671, 765040826, '59 - 30'),
       ('2020-11-04', 'Toronto', 7, 2, 666666672, 617043149, '800 - 799'),
       ('2020-11-05', 'Toronto', 5, 2, 666666670, 24449374, '0 - 1'),
       ('2020-11-06', 'Brawltimore', 3, 4, 666666668, 406210010, '90 - 78'),
       ('2020-11-07', 'Blitzburg', 2, 1, 666666667, 118072692, '9999 - 800');

# Insert Allstar Nominees
INSERT INTO `ALLSTAR_NOMINEES`
VALUES (111111100, '2020-11-01', 'Blitzburg', 1),
       (111111101, '2020-11-02', 'Brawltimore', 2),
       (111111102, '2020-11-03', 'Seattle', 3),
       (222222200, '2020-11-04', 'Toronto', 4),
       (222222201, '2020-11-05', 'Toronto', 5),
       (222222203, '2020-11-06', 'Brawltimore', 6),
       (222222204, '2020-11-07', 'Blitzburg', 9);

-- -----------------------------------------------------------------------------------------------------------
-- Drop statements
-- -----------------------------------------------------------------------------------------------------------
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE ALLSTAR_NOMINEES;
DROP TABLE ALLSTAR_GAME;
DROP TABLE ASSISTANT_COACH;
DROP TABLE COACH;
DROP TABLE CHAMPIONSHIP_TEAM;
DROP SCHEMA Allstar_Team;
SET FOREIGN_KEY_CHECKS = 1;