# Resources schema

# --- !Ups
CREATE TABLE IF NOT EXISTS `archiveconfig` (
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


CREATE TABLE IF NOT EXISTS `authors` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `email` varchar(255) NOT NULL DEFAULT '',
  `imported` tinyint(1) NOT NULL DEFAULT '0',
  `doNotImport` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `bookmarks` (
  `id` int(11) NOT NULL DEFAULT '0',
  `title` varchar(255) NOT NULL DEFAULT '',
  `summary` text,
  `notes` text,
  `authorId` int(11) DEFAULT '0',
  `rating` varchar(255) NOT NULL DEFAULT '',
  `date` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `categories` varchar(45) DEFAULT NULL,
  `tags` varchar(1024) NOT NULL DEFAULT '',
  `warnings` varchar(255) DEFAULT '',
  `fandoms` varchar(255) DEFAULT NULL,
  `characters` varchar(1024) DEFAULT NULL,
  `relationships` varchar(1024) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `imported` tinyint(1) NOT NULL DEFAULT '0',
  `donotimport` tinyint(1) NOT NULL DEFAULT '0',
  `ao3url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `authorId` (`authorId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `chapters` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Position` bigint(22) DEFAULT NULL,
  `Title` varchar(255) NOT NULL DEFAULT '',
  `AuthorID` int(11) NOT NULL DEFAULT '0',
  `Text` mediumtext,
  `Date` datetime DEFAULT NULL,
  `StoryID` int(11) NOT NULL DEFAULT '0',
  `Notes` text,
  `Url` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `id_UNIQUE` (`ID`),
  KEY `storyid` (`StoryID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `stories` (
  `id` int(11) NOT NULL DEFAULT '0',
  `title` varchar(255) NOT NULL DEFAULT '',
  `summary` text,
  `notes` text,
  `authorId` int(11) DEFAULT '0',
  `rating` varchar(255) NOT NULL DEFAULT '',
  `date` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `categories` varchar(45) DEFAULT NULL,
  `tags` varchar(1024) NOT NULL DEFAULT '',
  `warnings` varchar(255) DEFAULT '',
  `fandoms` varchar(255) DEFAULT NULL,
  `characters` varchar(1024) DEFAULT NULL,
  `relationships` varchar(1024) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `imported` tinyint(1) NOT NULL DEFAULT '0',
  `donotimport` tinyint(1) NOT NULL DEFAULT '0',
  `ao3url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `authorId` (`authorId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


# --- !Downs



