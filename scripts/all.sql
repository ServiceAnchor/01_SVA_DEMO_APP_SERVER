-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.5.29 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win32
-- HeidiSQL 版本:                  8.3.0.4833
--

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `menukey` varchar(2000) DEFAULT NULL,
  `menus` varchar(255) DEFAULT NULL,
  `storesid` varchar(255) DEFAULT NULL,
  `stores` varchar(255) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index 2` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
INSERT INTO `role` (`id`, `name`, `menukey`, `menus`, `storesid`, `stores`, `updateTime`) VALUES
	(1, '1', '1', '1', '1', '1', '2016-03-02 17:41:19'); 


-- 导出  表 sva.account 结构
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleid` int(11) NOT NULL,
  `username` varchar(50) COLLATE utf8_bin NOT NULL,
  `password` varchar(50) COLLATE utf8_bin NOT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index 3` (`username`),
  KEY `fk_account_role` (`roleid`) USING BTREE,
  CONSTRAINT `fk_roleid` FOREIGN KEY (`roleid`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
INSERT INTO `account` (`id`, `roleid`, `username`, `password`, `updateTime`) VALUES
	(1, 1, 'admin', 'admin', '2016-03-02 17:41:40');

-- 数据导出被取消选择。


-- 导出  表 sva.store 结构
DROP TABLE IF EXISTS `store`;
CREATE TABLE IF NOT EXISTS `store` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 数据导出被取消选择。

-- 导出  表 sva.locationphone 结构
DROP TABLE IF EXISTS `locationphone`;
CREATE TABLE IF NOT EXISTS `locationphone` (
  `idType` varchar(50) DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL,
  `dataType` varchar(50) DEFAULT NULL,
  `x` decimal(10,0) DEFAULT NULL,
  `y` decimal(10,0) DEFAULT NULL,
  `z` decimal(10,0) DEFAULT NULL,
  `userID` varchar(50) NOT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 数据导出被取消选择。


-- 导出  表 sva.maps 结构
DROP TABLE IF EXISTS `maps`;
CREATE TABLE `maps` (
	`floor` VARCHAR(50) NOT NULL COLLATE 'utf8_general_ci',
	`xo` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
	`yo` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
	`scale` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
	`coordinate` VARCHAR(50) NOT NULL COLLATE 'utf8_general_ci',
	`angle` FLOAT NOT NULL DEFAULT '0',
	`path` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
	`placeId` INT(11) NULL DEFAULT NULL,
	`mapId` INT(11) NULL DEFAULT NULL,
	`floorNo` DECIMAL(10,2) NULL DEFAULT NULL,
	`imgWidth` INT(11) NULL DEFAULT NULL,
	`imgHeight` INT(11) NULL DEFAULT NULL,
	`floorid` DECIMAL(10,2) NULL DEFAULT NULL,
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`svg` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
	`route` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
	`pathfile` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
	`updateTime` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `floorNo` (`floorNo`),
	INDEX `FK_maps_store` (`placeId`),
	CONSTRAINT `FK_maps_store` FOREIGN KEY (`placeId`) REFERENCES `store` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
COMMENT='地图信息'
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB;





-- 导出  表 sva.accuracy 结构
DROP TABLE IF EXISTS `accuracy`;
CREATE TABLE `accuracy` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`placeId` INT(11) NOT NULL,
	`floorNo` DECIMAL(10,2) NOT NULL,
	`origin` VARCHAR(50) NOT NULL COLLATE 'utf8_unicode_ci',
	`destination` VARCHAR(50) NOT NULL COLLATE 'utf8_unicode_ci',
	`start_date` DATETIME NOT NULL,
	`end_date` DATETIME NOT NULL,
	`type` VARCHAR(50) NOT NULL COLLATE 'utf8_unicode_ci',
	`triggerIp` VARCHAR(50) NOT NULL COLLATE 'utf8_general_ci',
	`offset` DECIMAL(10,2) NOT NULL,
	`variance` DECIMAL(10,2) NOT NULL,
	`averDevi` DECIMAL(10,2) NOT NULL,
	`count_3` INT(11) NOT NULL,
	`count_5` INT(11) NOT NULL,
	`count_10` INT(11) NOT NULL,
	`count_10p` INT(11) NOT NULL,
	`detail` TEXT NULL COLLATE 'utf8_unicode_ci',
	PRIMARY KEY (`id`)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB;


DROP TABLE IF EXISTS `staticaccuracy`;
CREATE TABLE `staticaccuracy` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`placeId` INT(11) NOT NULL,
	`floorNo` DECIMAL(10,2) NOT NULL,
	`origin` VARCHAR(50) NOT NULL COLLATE 'utf8_unicode_ci',
	`destination` VARCHAR(50) NOT NULL COLLATE 'utf8_unicode_ci',
	`start_date` DATETIME NOT NULL,
	`end_date` DATETIME NOT NULL,
	`avgeOffset` DECIMAL(10,0) NOT NULL,
	`maxOffset` DECIMAL(10,2) NOT NULL,
	`staicAccuracy` DECIMAL(10,2) NOT NULL,
	`offsetCenter` DECIMAL(10,2) NOT NULL,
	`offsetNumber` DECIMAL(10,2) NOT NULL,
	`stability` DECIMAL(10,0) NOT NULL,
	`count_3` INT(11) NOT NULL,
	`count_5` INT(11) NOT NULL,
	`count_10` INT(11) NOT NULL,
	`count_10p` INT(11) NOT NULL,
	`detail` TEXT NULL COLLATE 'utf8_unicode_ci',
	`triggerIp` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
	PRIMARY KEY (`id`)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB;




-- 导出  表 sva.dynamicaccuracy 结构
DROP TABLE IF EXISTS `dynamicaccuracy`;
CREATE TABLE `dynamicaccuracy` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`placeId` INT(11) NOT NULL,
	`floorNo` DECIMAL(10,2) NOT NULL,
	`origin` VARCHAR(50) NOT NULL COLLATE 'utf8_unicode_ci',
	`destination` VARCHAR(50) NOT NULL COLLATE 'utf8_unicode_ci',
	`start_date` DATETIME NOT NULL,
	`end_date` DATETIME NOT NULL,
	`avgeOffset` DECIMAL(10,2) NOT NULL,
	`maxOffset` DECIMAL(10,2) NOT NULL,
	`Offset` DECIMAL(10,2) NOT NULL,
	`count_3` INT(11) NOT NULL,
	`count_5` INT(11) NOT NULL,
	`count_10` INT(11) NOT NULL,
	`count_10p` INT(11) NOT NULL,
	`detail` TEXT NULL COLLATE 'utf8_unicode_ci',
	`triggerIp` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
	PRIMARY KEY (`id`)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB;






-- 导出  表 sva.bluemix 结构
DROP TABLE IF EXISTS `bluemix`;
CREATE TABLE `bluemix` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `svaUser` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `svaPassword` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `url` varchar(300) COLLATE utf8_unicode_ci DEFAULT NULL,
  `site` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ibmUser` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ibmPassword` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `tokenPort` int(11) NOT NULL,
  `brokerPort` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



DROP TABLE IF EXISTS `code`;
CREATE TABLE `code` (
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
-- 数据导出被取消选择。


-- 数据导出被取消选择。
DROP TABLE IF EXISTS `locationtemp`;
CREATE TABLE `locationtemp` (
	`idType` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
	`timestamp` BIGINT(20) NULL DEFAULT NULL,
	`dataType` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
	`x` DECIMAL(10,0) NULL DEFAULT NULL,
	`y` DECIMAL(10,0) NULL DEFAULT NULL,
	`z` DECIMAL(10,0) NULL DEFAULT NULL,
	`userID` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
	INDEX `Index 1` (`userID`),
	INDEX `Index 2` (`timestamp`)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB;


-- 导出  表 sva.estimatedev 结构
DROP TABLE IF EXISTS `estimatedev`;
CREATE TABLE IF NOT EXISTS `estimatedev` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `placeId` int(11) DEFAULT NULL,
  `floorNo` decimal(10,2) DEFAULT NULL,
  `a` decimal(10,2) DEFAULT NULL,
  `b` decimal(10,2) DEFAULT NULL,
  `n` int(11) DEFAULT NULL,
  `type` decimal(10,3) DEFAULT NULL,
  `d` decimal(10,1) DEFAULT NULL,
  `deviation` decimal(10,1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`),
  KEY `FK_estimatedev_store` (`placeId`),
  KEY `FK_estimatedev_maps` (`floorNo`),
  CONSTRAINT `FK_estimatedev_store` FOREIGN KEY (`placeId`) REFERENCES `store` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_estimatedev_maps` FOREIGN KEY (`floorNo`) REFERENCES `maps` (`floorNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='预估偏差';

-- 数据导出被取消选择。


-- 导出  表 sva.menu 结构
DROP TABLE IF EXISTS `menu`;
CREATE TABLE IF NOT EXISTS `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '0',
  `url` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '0',
  `parent` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '0',
  `type` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '0',
  `sort` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 数据导出被取消选择。


-- 导出  表 sva.message 结构
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
	`placeId` INT(11) NOT NULL,
	`shopName` VARCHAR(100) NOT NULL COLLATE 'utf8_general_ci',
	`xSpot` DECIMAL(10,2) NOT NULL,
	`ySpot` DECIMAL(10,2) NOT NULL,
	`rangeSpot` DECIMAL(10,2) NULL DEFAULT NULL,
	`timeInterval` INT(10) NULL DEFAULT NULL,
	`message` MEDIUMTEXT NULL COLLATE 'utf8_general_ci',
	`isEnable` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
	`pictruePath` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
	`moviePath` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	`floorNo` DECIMAL(10,2) NULL DEFAULT NULL,
	`x1Spot` DECIMAL(10,2) NULL DEFAULT NULL,
	`y1Spot` DECIMAL(10,2) NULL DEFAULT NULL,
	`shopId` INT(11) NOT NULL,
	PRIMARY KEY (`id`),
	INDEX `FK_message_maps` (`floorNo`),
	INDEX `FK_message_store` (`placeId`),
	CONSTRAINT `FK_message_maps` FOREIGN KEY (`floorNo`) REFERENCES `maps` (`floorNo`) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT `FK_message_store` FOREIGN KEY (`placeId`) REFERENCES `store` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB
AUTO_INCREMENT=3;



-- 数据导出被取消选择。


-- 导出  表 sva.prru 结构
DROP TABLE IF EXISTS `prru`;
CREATE TABLE `prru` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`pRRUid` VARCHAR(100) NULL DEFAULT NULL,
	`name` VARCHAR(100) NULL DEFAULT NULL,
	`x` VARCHAR(50) NULL DEFAULT NULL,
	`y` VARCHAR(50) NULL DEFAULT NULL,
	`floorNo` DECIMAL(10,2) NULL DEFAULT NULL,
	`placeId` INT(11) NULL DEFAULT NULL,
        `neCode` varchar(50) DEFAULT NULL,
	PRIMARY KEY (`id`),
	INDEX `FK_prru_maps` (`floorNo`),
	INDEX `FK_prru_store` (`placeId`),
	CONSTRAINT `FK_prru_store` FOREIGN KEY (`placeId`) REFERENCES `store` (`id`) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT `FK_prru_maps` FOREIGN KEY (`floorNo`) REFERENCES `maps` (`floorNo`) ON UPDATE CASCADE ON DELETE CASCADE
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
AUTO_INCREMENT=103;

-- 数据导出被取消选择。

DROP TABLE IF EXISTS `prrusignal`;
CREATE TABLE `prrusignal` (
	`id` INT(11) NOT NULL,
	`gpp` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
	`rsrp` DECIMAL(10,2) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB;


-- 导出  表 sva.seller 结构
DROP TABLE IF EXISTS `seller`;
CREATE TABLE IF NOT EXISTS `seller` (
  `placeId` int(11) NOT NULL,
  `xSpot` decimal(10,2) NOT NULL,
  `ySpot` decimal(10,2) NOT NULL,
  `info` mediumtext CHARACTER SET utf8,
  `isEnable` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `pictruePath` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `moviePath` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isVip` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `floorNo` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_seller_store` (`placeId`),
  KEY `FK_seller_maps` (`floorNo`),
  CONSTRAINT `FK_seller_store` FOREIGN KEY (`placeId`) REFERENCES `store` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_seller_maps` FOREIGN KEY (`floorNo`) REFERENCES `maps` (`floorNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 数据导出被取消选择。
-- ----------------------------
-- Table structure for statisticarea
-- ----------------------------
DROP TABLE IF EXISTS `statisticarea`;
CREATE TABLE `statisticarea` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` datetime NOT NULL,
  `areaId` int(11) NOT NULL,
  `number` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 导出  表 sva.statisticday 结构
DROP TABLE IF EXISTS `statisticday`;
CREATE TABLE IF NOT EXISTS `statisticday` (
  `placeId` int(11) NOT NULL,
  `time` datetime NOT NULL,
  `number` int(11) DEFAULT NULL,
  PRIMARY KEY (`placeId`,`time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 数据导出被取消选择。


-- 导出  表 sva.statisticfloor 结构
DROP TABLE IF EXISTS `statisticfloor`;
CREATE TABLE `statisticfloor` (
	`userID` VARCHAR(50) NOT NULL COLLATE 'utf8_general_ci',
	`time` DATETIME NOT NULL,
	`z` DECIMAL(10,0) NOT NULL,
	PRIMARY KEY (`userID`, `time`, `z`),
	INDEX `index` (`z`)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB;


-- 数据导出被取消选择。


-- 导出  表 sva.statistichour 结构
DROP TABLE IF EXISTS `statistichour`;
CREATE TABLE IF NOT EXISTS `statistichour` (
  `placeId` int(11) NOT NULL,
  `time` datetime NOT NULL,
  `number` int(11) DEFAULT NULL,
  PRIMARY KEY (`placeId`,`time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- 数据导出被取消选择。
DROP TABLE IF EXISTS `statisticlinetemp`;
CREATE TABLE `statisticlinetemp` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`placeId` INT(11) NOT NULL,
	`time` DATETIME NULL DEFAULT NULL,
	`number` INT(11) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;


DROP TABLE IF EXISTS `statistictemp`;
CREATE TABLE `statistictemp` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(50) NULL DEFAULT '0',
	`number` INT(11) NULL DEFAULT '0',
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;



-- 导出  表 sva.svalist 结构
DROP TABLE IF EXISTS `svalist`;
CREATE TABLE `svalist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `ip` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `username` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `status` int(1) NOT NULL DEFAULT '0',
  `type` int(1) NOT NULL,
  `tokenPort` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `brokerPort` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='sva列表';

-- 数据导出被取消选择。


-- 导出  表 sva.svastoremap 结构
DROP TABLE IF EXISTS `svastoremap`;
CREATE TABLE IF NOT EXISTS `svastoremap` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `svaId` int(11) DEFAULT '0',
  `storeId` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_svastoremap_svalist` (`svaId`),
  KEY `FK_svastoremap_store` (`storeId`),
  CONSTRAINT `FK_svastoremap_svalist` FOREIGN KEY (`svaId`) REFERENCES `svalist` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_svastoremap_store` FOREIGN KEY (`storeId`) REFERENCES `store` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `updateTime` datetime DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for input
-- ----------------------------
DROP TABLE IF EXISTS `area`;
CREATE TABLE `area` (
	`areaName` VARCHAR(255) NOT NULL,
	`placeId` INT(11) NOT NULL,
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	`floorNo` DECIMAL(10,2) NOT NULL,
	`xSpot` DECIMAL(10,2) NOT NULL,
	`ySpot` DECIMAL(10,2) NOT NULL,
	`status` INT(11) NOT NULL DEFAULT '0',
	`zoneId` VARCHAR(50) NULL DEFAULT NULL,
	`x1Spot` DECIMAL(10,2) NOT NULL,
	`y1Spot` DECIMAL(10,2) NOT NULL,
	`categoryid` INT(10) NOT NULL,
	PRIMARY KEY (`id`),
	INDEX `FK_message_maps` (`placeId`) USING BTREE,
	INDEX `FK_message_store` (`floorNo`) USING BTREE,
	INDEX `FK_message_category` (`categoryid`) USING BTREE,
	CONSTRAINT `area_ibfk_1` FOREIGN KEY (`placeId`) REFERENCES `store` (`id`) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT `area_ibfk_2` FOREIGN KEY (`floorNo`) REFERENCES `maps` (`floorNo`) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT `area_ibfk_3` FOREIGN KEY (`categoryid`) REFERENCES `category` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;




DROP TABLE IF EXISTS `param`;
CREATE TABLE IF NOT EXISTS `param` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `banThreshold` double(20,2) NOT NULL,
  `filterLength` double(20,2) NOT NULL,
  `horizontalWeight` double(20,2) unsigned NOT NULL,
  `ongitudinalWeight` double(20,2) NOT NULL,
  `maxDeviation` double(20,2) NOT NULL,
  `exceedMaxDeviation` double(20,2) NOT NULL,
  `updateTime` bigint(20) NOT NULL,
  `correctMapDirection` double(20,2) NOT NULL,
  `restTimes` double(20,2) NOT NULL,
  `step` double(20,2) NOT NULL,
  `filterPeakError` double(20,2) NOT NULL,
  `peakWidth` double(20,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
INSERT INTO `param` (`id`, `banThreshold`, `filterLength`, `horizontalWeight`, `ongitudinalWeight`, `maxDeviation`, `exceedMaxDeviation`, `updateTime`, `correctMapDirection`, `restTimes`, `step`, `filterPeakError`, `peakWidth`) VALUES
	(1, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1458640424691, 1.00, 1.00, 1.00, 1.00, 1.00);








-- ----------------------------
-- Table structure for `staticvisit`
-- ----------------------------
DROP TABLE IF EXISTS `staticvisit`;
CREATE TABLE `staticvisit` (
	`areaId` INT(11) NOT NULL,
	`time` VARCHAR(50) NOT NULL,
	`allTime` BIGINT(20) NOT NULL,
	`number` INT(11) NOT NULL,
	`averageTime` BIGINT(20) NOT NULL,
	PRIMARY KEY (`areaId`, `time`),
	CONSTRAINT `areaId` FOREIGN KEY (`areaId`) REFERENCES `area` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB;



-- ----------------------------
-- Table structure for `statisticareaday`
-- ----------------------------
DROP TABLE IF EXISTS `statisticareaday`;
CREATE TABLE `statisticareaday` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` datetime NOT NULL,
  `areaId` int(11) NOT NULL,
  `number` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=272 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------





DROP TABLE IF EXISTS `bzprames`;
CREATE TABLE `bzprames` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `placeId` int(11) NOT NULL,
  `floorNo` decimal(10,2) NOT NULL,
  `periodSel` int(10) DEFAULT NULL,
  `startTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_store` (`placeId`) USING BTREE,
  KEY `FK_maps` (`floorNo`) USING BTREE,
  CONSTRAINT `fk_floorId` FOREIGN KEY (`floorNo`) REFERENCES `maps` (`floorNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_store` FOREIGN KEY (`placeId`) REFERENCES `store` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `menuname`;
CREATE TABLE `menuname` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `keyZH` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
INSERT INTO `menuname` (`keyZH`, `name`) VALUES
	('key_storeManage', '商场管理'),
	('key_svaManage', 'SVA管理'),
	('key_mapManage', '地图管理'),
	('key_messagePush', '消息推送管理'),
	('key_sellerInfo', '商户信息管理'),
	('key_areaCategory', '区域类别管理'),
	('key_areaInfo', '区域信息录入'),
	('key_customerHeamap', '客流实时热力图'),
	('key_customerPeriodHeamap', '时间段客流热力图'),
	('key_customerScattermap', '客流实时散点图'),
	('key_historicalTrack', '历史轨迹'),
	('key_CustomerFlowLinemap', '历史客流分析图'),
	('key_code', '口令管理'),
	('key_estimateDeviation', '预估偏差设定'),
	('key_summaryDisplay', '汇总结果'),
	('key_bluemixConnection', 'bluemix对接'),
	('key_ping', 'ping'),
	('key_pRRUConfig', 'pRRU配置'),
	('key_versionDownload', '版本下载'),
	('key_APKDownload', 'APK下载'),
	('key_role', '角色管理'),
	('key_account', '权限管理'),
	('key_paramConfig', '参数配置'),
	('key_allShow', '全局概览'),
	('key_dynamicAccuyacy', '动态精度测试'),
	('key_staticAccuyacy', '静态精度测试'),
	('key_positionlatency', '定位延时'),
	('key_MessagePush', '消息推送准确率');
DROP TABLE IF EXISTS `menuenglish`;
CREATE TABLE `menuenglish` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `keyEN` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `menuenglish` ( `keyEN`, `name`) VALUES
	('key_storeManage', 'Store Management'),
	('key_svaManage', 'SVA Management'),
	('key_mapManage', 'Map Management'),
	('key_messagePush', 'Message Management'),
	('key_sellerInfo', 'Seller Management'),
	('key_areaCategory', ' Regional category management '),
	('key_areaInfo', 'Regional information input'),
	('key_customerHeamap', 'Customer Heatmap'),
	('key_customerPeriodHeamap', 'Customer Heatmap in Period'),
	('key_customerScattermap', 'Customer Scattermap'),
	('key_historicalTrack', 'Historical Track'),
	('key_CustomerFlowLinemap', 'Customer Flow Range Linemap'),
	('key_code', 'Code Management'),
	('key_estimateDeviation', 'Set Estimate Deviation'),
	('key_summaryDisplay', 'Summary Display'),
	('key_bluemixConnection', 'Bluemix Connection'),
	('key_ping', 'ping'),
	('key_pRRUConfig', 'pRRU Config'),
	('key_versionDownload', 'Version Downloads'),
	('key_APKDownload', 'APKDownload'),
	('key_role', 'Role management'),
	('key_paramConfig', 'Parameter configuration '),
	('key_account', 'Rights management'),
	('key_allShow', 'Global overview'),
	('key_dynamicAccuyacy', 'Dynamic accuracy test'),
	('key_staticAccuyacy', 'Static accuracy test'),
	('key_positionlatency', ' Position latency'),
	('key_MessagePush', 'Message push accuracy');


DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `areaData`(IN `allTimes` VARCHAR(50), IN `numbers` INT, IN `nowTimes` BIGINT, IN `floorNos` decimal, IN `allTableName` varchar(50), IN `xSpot` int, IN `xSpot1` int, IN `ySpot` int, IN `ySpot1` int, IN `areaIds` int, IN `visitDay` varchar(50))
	LANGUAGE SQL
	NOT DETERMINISTIC
	CONTAINS SQL
	SQL SECURITY DEFINER
	COMMENT ''
BEGIN

DECLARE sql_data VARCHAR(200);
DECLARE done1 INT DEFAULT 0;
DECLARE areaUser VARCHAR(50);
DECLARE areaTimestamp BIGINT;
DECLARE areaTempUser VARCHAR(50);
DECLARE areaTempTimestamp BIGINT;
DECLARE sql_area VARCHAR(200);
DECLARE areaStayTime BIGINT DEFAULT 0;
DECLARE areaCount int DEFAULT 1;
DECLARE areaMinTime INT DEFAULT 0; 
DECLARE sizeNumber INT ;
DECLARE sizes INT DEFAULT 0 ;
DECLARE cur_areaData CURSOR FOR SELECT userID,timestamp FROM v_area;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done1=1;


DROP VIEW IF EXISTS v_area;
set @sql_area = CONCAT("CREATE view v_area as SELECT userID,timestamp FROM ",allTableName," WHERE z = ",floorNos," AND x >",xSpot," AND x <",xSpot1," AND y >",ySpot," AND y < ",ySpot1," AND timestamp > ",nowTimes," ORDER BY userID,timestamp");
PREPARE stmt1 FROM @sql_area;
EXECUTE stmt1; 
DEALLOCATE PREPARE stmt1;



OPEN cur_areaData;
SET done1 = 0;
FETCH cur_areaData INTO areaTempUser,areaTempTimestamp;
WHILE done1 < 1 DO
FETCH cur_areaData INTO areaUser,areaTimestamp;
IF areaTempUser = areaUser THEN
IF areaTempTimestamp + 30000 > areaTimestamp THEN
SET areaStayTime = areaStayTime + areaTimestamp - areaTempTimestamp;
END IF;
ELSE 
SET areaTempUser = areaUser;
SET areaCount = areaCount +1;
END IF;
SET areaTempTimestamp = areaTimestamp;
END WHILE;
CLOSE cur_areaData; 



REPLACE INTO staticvisit(areaId,time,allTime,number,averageTime) VALUES(areaIds,visitDay,areaStayTime+allTimes,numbers,areaStayTime+allTimes);
set sizes = sizes+1;
END //

DROP TABLE IF EXISTS `allpeople`;
CREATE TABLE `allpeople` (
	`time` VARCHAR(50) NOT NULL,
	`nowNumber` INT(11) NOT NULL,
	`yesterNumber` INT(11) NOT NULL,
	`placeId` INT(11) NOT NULL,
	`number` INT(11) NOT NULL,
	PRIMARY KEY (`time`, `placeId`)
)
COMMENT='每分钟更新今日人数和昨日人数'
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB;



DROP TABLE IF EXISTS `nowpeople`;
CREATE TABLE `nowpeople` (
	`areaName` VARCHAR(50) NOT NULL,
	`areaId` INT(11) NOT NULL,
	`placeId` INT(11) NOT NULL,
	`number` INT(11) NOT NULL,
	PRIMARY KEY (`areaId`, `placeId`),
	CONSTRAINT `area_name` FOREIGN KEY (`areaId`) REFERENCES `area` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

DROP TABLE IF EXISTS `messagepush`;
CREATE TABLE `messagepush` (
	`id` INT(11) NOT NULL DEFAULT '0',
	`placeId` INT(11) NULL DEFAULT NULL,
	`floorNo` DECIMAL(10,2) NULL DEFAULT NULL,
	`pushRight` DOUBLE NULL DEFAULT NULL,
	`pushWrong` DOUBLE NULL DEFAULT NULL,
	`notPush` DOUBLE NULL DEFAULT NULL,
	`centerRadius` VARCHAR(100) NULL DEFAULT NULL,
	`centerReality` VARCHAR(100) NULL DEFAULT NULL,
	`isRigth` INT(11) NULL DEFAULT NULL,
	`updateTime` BIGINT(20) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB;



DROP TABLE IF EXISTS `locationdelay`;
CREATE TABLE `locationdelay` (
	`id` INT(11) NOT NULL DEFAULT '0',
	`placeId` INT(11) NULL DEFAULT NULL,
	`floorNo` DECIMAL(10,2) NULL DEFAULT NULL,
	`dataDelay` DOUBLE NULL DEFAULT NULL,
	`positionDelay` DOUBLE NULL DEFAULT NULL,
	`updateTime` BIGINT(20) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB;


DROP TABLE IF EXISTS `district_during`;
CREATE TABLE `district_during` (
	`idType` VARCHAR(50) NULL DEFAULT NULL,
	`timestamp` BIGINT(20) NULL DEFAULT NULL,
	`time_begin` BIGINT(20) NULL DEFAULT NULL,
	`time_local` BIGINT(20) NULL DEFAULT NULL,
	`during` BIGINT(20) NULL DEFAULT NULL,
	`dataType` VARCHAR(50) NULL DEFAULT NULL,
	`district_id` INT(11) NOT NULL,
	`userID` VARCHAR(50) NOT NULL,
	`loc_count` BIGINT(20) NULL DEFAULT NULL,
	`departure_time` BIGINT(20) NULL DEFAULT '0'
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB;

DROP TABLE IF EXISTS `locationphone`;
CREATE TABLE `locationphone` (
	`idType` VARCHAR(50) NULL DEFAULT NULL,
	`timestamp` BIGINT(20) NULL DEFAULT NULL,
	`time_begin` BIGINT(20) NULL DEFAULT NULL,
	`time_local` BIGINT(20) NULL DEFAULT NULL,
	`during` BIGINT(20) NOT NULL DEFAULT '0',
	`dataType` VARCHAR(50) NULL DEFAULT NULL,
	`x` DECIMAL(10,0) NULL DEFAULT NULL,
	`y` DECIMAL(10,0) NULL DEFAULT NULL,
	`z` DECIMAL(10,0) NULL DEFAULT NULL,
	`userID` VARCHAR(50) NOT NULL,
	`loc_count` BIGINT(20) NOT NULL DEFAULT '1',
	PRIMARY KEY (`userID`)
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB;

DROP TABLE IF EXISTS `phonenumber`;
CREATE TABLE `phonenumber` (
	`ip` VARCHAR(50) NOT NULL,
	`phoneNumber` VARCHAR(50) NOT NULL,
	`timestamp` BIGINT(20) NOT NULL,
	UNIQUE INDEX `Index 1` (`ip`, `phoneNumber`)
)
ENGINE=InnoDB;

CREATE TABLE `geofencing` (
	`id` INT(11) NULL DEFAULT NULL,
	`IdType` VARCHAR(50) NOT NULL,
	`userid` VARCHAR(50) NOT NULL,
	`mapid` BIGINT(20) NOT NULL,
	`zoneid` BIGINT(20) NOT NULL,
	`enter` VARCHAR(50) NOT NULL,
	`Timestamp` BIGINT(20) NOT NULL
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB;

CREATE TABLE `mwcprames` (
	`floorNo1` DECIMAL(10,2) NULL DEFAULT NULL,
	`floorNo2` DECIMAL(10,2) NULL DEFAULT NULL,
	`floorNo3` DECIMAL(10,2) NULL DEFAULT NULL,
	`floorNo4` DECIMAL(10,2) NULL DEFAULT NULL,
	`floorNo5` DECIMAL(10,2) NULL DEFAULT NULL,
	`floorNo6` DECIMAL(10,2) NULL DEFAULT NULL,
	`floorNo7` DECIMAL(10,2) NULL DEFAULT NULL,
	`floorNo8` DECIMAL(10,2) NULL DEFAULT NULL,
	`densitySel1` INT(11) NULL DEFAULT NULL,
	`densitySel2` INT(11) NULL DEFAULT NULL,
	`densitySel3` INT(11) NULL DEFAULT NULL,
	`densitySel4` INT(11) NULL DEFAULT NULL,
	`densitySel5` INT(11) NULL DEFAULT NULL,
	`startTime` DATETIME NULL DEFAULT NULL,
	`densitySel6` INT(11) NULL DEFAULT NULL,
	`densitySel7` INT(11) NULL DEFAULT NULL,
	`densitySel8` INT(11) NULL DEFAULT NULL,
	`radiusSel1` INT(11) NULL DEFAULT NULL,
	`radiusSel2` INT(11) NULL DEFAULT NULL,
	`radiusSel3` INT(11) NULL DEFAULT NULL,
	`coefficient` INT(11) NULL DEFAULT NULL,
	`radiusSel4` INT(11) NULL DEFAULT NULL,
	`radiusSel5` INT(11) NULL DEFAULT NULL,
	`radiusSel6` INT(11) NULL DEFAULT NULL,
	`radiusSel7` INT(11) NULL DEFAULT NULL,
	`radiusSel8` INT(11) NULL DEFAULT NULL,
	`id` INT(11) NOT NULL DEFAULT '0',
	`periodSel` INT(11) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB;

CREATE TABLE `register` (
	`ip` VARCHAR(50) NULL DEFAULT NULL,
	`userName` VARCHAR(50) NULL DEFAULT NULL,
	`timestamp` BIGINT(20) NULL DEFAULT NULL,
	`status` INT(11) NULL DEFAULT '0',
	`password` VARCHAR(50) NULL DEFAULT NULL,
	`phoneNumber` BIGINT(20) NULL DEFAULT NULL,
	`role` INT(11) NULL DEFAULT NULL COMMENT '0:  1:  2:',
	`isTrue` INT(11) NULL DEFAULT '0',
	`otherPhone` BIGINT(20) NULL DEFAULT NULL,
	UNIQUE INDEX `55555` (`phoneNumber`)
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB;


CREATE TABLE `electronic` (
	`placeId` INT(11) NOT NULL,
	`electronicName` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
	`message` MEDIUMTEXT NULL COLLATE 'utf8_general_ci',
	`shopId` INT(11) NULL DEFAULT NULL,
	`pictruePath` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
	`moviePath` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_unicode_ci',
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	`floorNo` DECIMAL(10,2) NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	INDEX `FK_electronic_store` (`placeId`),
	INDEX `FK_electronic_maps` (`floorNo`),
	INDEX `FK_electronic_area` (`shopId`),
	CONSTRAINT `FK_electronic_area` FOREIGN KEY (`shopId`) REFERENCES `area` (`id`),
	CONSTRAINT `FK_electronic_maps` FOREIGN KEY (`floorNo`) REFERENCES `maps` (`floorNo`),
	CONSTRAINT `FK_electronic_store` FOREIGN KEY (`placeId`) REFERENCES `store` (`id`)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB
AUTO_INCREMENT=3;

