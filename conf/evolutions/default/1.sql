# Resources schema

# --- !Ups
set @today = DATE_FORMAT(NOW(), '%Y%m%d_%H%i');

SELECT Count(*)
INTO @exists
FROM information_schema.tables
WHERE table_type = 'BASE TABLE'
AND table_name = 'archiveconfig';

SET @sql_config = If(@exists>0,
                     CONCAT("RENAME TABLE archiveconfig TO ", @today, "_archiveconfig"),
                     'SELECT \'nothing to rename\' status');
PREPARE statemt FROM @sql_config;
EXECUTE statemt;
DEALLOCATE PREPARE statemt;

SELECT Count(*)
INTO @exists
FROM information_schema.tables
WHERE table_type = 'BASE TABLE'
      AND table_name = 'authors';
SET @sql_authors = If(@exists>0,
                      CONCAT("RENAME TABLE authors TO ", @today, "_authors"),
                      'SELECT \'nothing to rename\' status');
PREPARE statemt FROM @sql_authors;
EXECUTE statemt;
DEALLOCATE PREPARE statemt;

SELECT Count(*)
INTO @exists
FROM information_schema.tables
WHERE table_type = 'BASE TABLE'
      AND table_name = 'bookmarks';
SET @sql_bookmarks = If(@exists>0,
                        CONCAT("RENAME TABLE bookmarks TO ", @today, "_bookmarks"),
                        'SELECT \'nothing to rename\' status');
PREPARE statemt FROM @sql_bookmarks;
EXECUTE statemt;
DEALLOCATE PREPARE statemt;

SELECT Count(*)
INTO @exists
FROM information_schema.tables
WHERE table_type = 'BASE TABLE'
      AND table_name = 'chapters';
SET @sql_bookmarks = If(@exists>0,
                        CONCAT("RENAME TABLE chapters TO ", @today, "_chapters"),
                        'SELECT \'nothing to rename\' status');
PREPARE statemt FROM @sql_chapters;
EXECUTE statemt;
DEALLOCATE PREPARE statemt;

SELECT Count(*)
INTO @exists
FROM information_schema.tables
WHERE table_type = 'BASE TABLE'
      AND table_name = 'stories';
SET @sql_bookmarks = If(@exists>0,
                        CONCAT("RENAME TABLE stories TO ", @today, "_stories"),
                        'SELECT \'nothing to rename\' status');
PREPARE statemt FROM @sql_stories;
EXECUTE statemt;
DEALLOCATE PREPARE statemt;


CREATE TABLE `archiveconfig` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Key` varchar(45) NOT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `Fandom` varchar(255) DEFAULT NULL,
  `ODNote` text,
  `ArchiveHost` varchar(255) default 'ariana.ao3.org',
  `ArchiveToken` varchar(255),
  `SendEmail` tinyint(1) NOT NULL DEFAULT '0',
  `PostWorks` tinyint(1) NOT NULL DEFAULT '0',
  `ItemsPerPage` int(11) NOT NULL DEFAULT '30',
  `Archivist` varchar(100) NOT NULL,
  `CollectionName` varchar(255) NOT NULL,
  `Imported` tinyint(1) NOT NULL DEFAULT '0',
  `NotImported` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `Key_UNIQUE` (`Key`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


CREATE TABLE `authors` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `email` varchar(255) NOT NULL DEFAULT '',
  `imported` tinyint(1) NOT NULL DEFAULT '0',
  `doNotImport` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


CREATE TABLE `stories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL DEFAULT '',
  `summary` TEXT NULL DEFAULT NULL,
  `notes` text NULL DEFAULT NULL,
  `authorid` int(11) NOT NULL DEFAULT '0',
  `rating` varchar(25) NOT NULL DEFAULT '',
  `date` date,
  `updated` DATE,
  `categories` varchar(45) DEFAULT NULL,
  `tags` varchar(1024) DEFAULT NULL,
  `warnings` varchar(255) DEFAULT NULL,
  `fandoms` varchar(255) DEFAULT NULL,
  `characters` varchar(1024) DEFAULT NULL,
  `relationships` varchar(1024) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `imported` tinyint(1) NOT NULL DEFAULT '0',
  `doNotImport` tinyint(1) NOT NULL DEFAULT '0',
  `Ao3Url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `authorid` (`authorid`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- For sites where some stories are stored elsewhere (usually GeoCities /o\)
CREATE TABLE `bookmarks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL DEFAULT '',
  `summary` TEXT NULL DEFAULT NULL,
  `notes` text NULL DEFAULT NULL,
  `authorid` int(11) NOT NULL DEFAULT '0',
  `rating` varchar(255) NOT NULL DEFAULT '',
  `date` date,
  `categories` varchar(45) DEFAULT NULL,
  `tags` varchar(1024) DEFAULT NULL,
  `warnings` varchar(255) DEFAULT NULL,
  `fandoms` varchar(255) DEFAULT NULL,
  `characters` varchar(1024) DEFAULT NULL,
  `relationships` varchar(1024) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `imported` tinyint(1) NOT NULL DEFAULT '0',
  `doNotImport` tinyint(1) NOT NULL DEFAULT '0',
  `Ao3Url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `authorid` (`authorid`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- For sites where the stories are in the database
CREATE TABLE `chapters` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Position` int(11) DEFAULT NULL,
  `Title` varchar(255) NOT NULL DEFAULT '',
  `AuthorID` int(11) NOT NULL DEFAULT '0',
  `Text` LONGTEXT,
  `Date` date,
  `StoryID` int(11) NOT NULL DEFAULT '0',
  `Notes` text,
  `Url` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `storyid` (`StoryID`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# --- !Downs



