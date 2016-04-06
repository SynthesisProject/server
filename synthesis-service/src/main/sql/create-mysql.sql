CREATE TABLE `content_version` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MODULE_REGISTRATION_ID` bigint(20) NOT NULL,
  `TOOL_NAME` varchar(64) NOT NULL,
  `CONTENT_VERSION` varchar(24) NOT NULL,
  `ACTIVE` bit(1) NOT NULL DEFAULT b'1',
  `LAST_UPDATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `CONTENT_VERSION_U` (`MODULE_REGISTRATION_ID`,`TOOL_NAME`,`CONTENT_VERSION`)
);

CREATE TABLE `managed_module` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MODULE_ID` varchar(64) NOT NULL,
  `MODULE_NAME` varchar(255),
  `LAST_SYNC` timestamp NULL DEFAULT NULL,
  `ACTIVE` bit(1) NOT NULL DEFAULT b'1',
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `REPOSITORY` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `MANAGED_MODULE_U` (`MODULE_ID`)
);

CREATE TABLE `tool` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `on_menu` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
);

CREATE TABLE `module_tools` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `MODULE_ID` bigint(20) NOT NULL,
  `TOOL_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `module_tools_U` (`MODULE_ID`,`TOOL_ID`),
  KEY `FK_MODULE_TOOLS_TOOL` (`TOOL_ID`),
  CONSTRAINT `FK_MODULE_TOOLS_MANAGED_MODULE` FOREIGN KEY (`MODULE_ID`) REFERENCES `managed_module` (`ID`),
  CONSTRAINT `FK_MODULE_TOOLS_TOOL` FOREIGN KEY (`TOOL_ID`) REFERENCES `tool` (`id`)
);

CREATE TABLE `push_devices` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `model` varchar(255) DEFAULT NULL,
  `platform` varchar(255) DEFAULT NULL,
  `pst_ts` datetime DEFAULT NULL,
  `regid` varchar(255) DEFAULT NULL,
  `usr` varchar(255) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `ver_nbr` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uuid` (`uuid`)
);


CREATE TABLE `push_msg` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `emr` bit(1) DEFAULT NULL,
  `msg` varchar(255) DEFAULT NULL,
  `pst_ts` datetime DEFAULT NULL,
  `sndr` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `ver_nbr` bigint(20) DEFAULT NULL,
  `module_id` varchar(255) DEFAULT NULL,
  `tool_name` varchar(255) DEFAULT NULL,
  `msg_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE `push_pref` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `enb` bit(1) DEFAULT NULL,
  `pst_ts` datetime DEFAULT NULL,
  `sender` varchar(255) DEFAULT NULL,
  `ver_nbr` bigint(20) DEFAULT NULL,
  `device_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
   KEY `FK_DEVICE_ID` (`device_id`),
  CONSTRAINT `FK_DEVICE_ID` FOREIGN KEY (`device_id`) REFERENCES `push_devices` (`id`)
);

CREATE TABLE `push_tuple` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `next_attempt` datetime DEFAULT NULL,
  `pst_ts` datetime DEFAULT NULL,
  `retries` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `ver_nbr` bigint(20) DEFAULT NULL,
  `device_id` bigint(20) NOT NULL,
  `push_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_T_DEVICE_ID` (`device_id`),
  KEY `FK_T_PUSH_ID` (`push_id`),
  CONSTRAINT `FK_T_DEVICE_ID` FOREIGN KEY (`device_id`) REFERENCES `push_devices` (`id`),
  CONSTRAINT `FK_T_PUSH_ID` FOREIGN KEY (`push_id`) REFERENCES `push_msg` (`id`)
);


