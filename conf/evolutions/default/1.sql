# Resources schema

# --- !Ups

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
  `rating` varchar(255) NOT NULL DEFAULT '',
  `date` date NOT NULL,
  `categories` varchar(45) DEFAULT NULL,
  `tags` varchar(1024) DEFAULT NULL,
  `warnings` varchar(255) DEFAULT NULL,
  `fandoms` varchar(255) DEFAULT NULL,
  `characters` varchar(255) DEFAULT NULL,
  `relationships` varchar(255) DEFAULT NULL,
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
  `date` date NOT NULL,
  `categories` varchar(45) DEFAULT NULL,
  `tags` varchar(1024) DEFAULT NULL,
  `warnings` varchar(255) DEFAULT NULL,
  `fandoms` varchar(255) DEFAULT NULL,
  `characters` varchar(255) DEFAULT NULL,
  `relationships` varchar(255) DEFAULT NULL,
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
  `Position` bigint(22) DEFAULT NULL,
  `Title` varchar(255) NOT NULL DEFAULT '',
  `AuthorID` int(11) NOT NULL DEFAULT '0',
  `Text` mediumtext,
  `Date` date NOT NULL,
  `StoryID` int(11) NOT NULL DEFAULT '0',
  `Notes` text,
  `Url` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `storyid` (`StoryID`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# --- !Downs



