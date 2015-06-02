CREATE TABLE `ats`.`ats_AnalogQuantityCfg`(  
  `cfgId` INT NOT NULL AUTO_INCREMENT,
  `aqTypeName` VARCHAR(50) COMMENT '类型名称',
  `aqType` INT COMMENT '类型值',
  `mark` INT COMMENT '模拟量特殊标识，0位：0代表正，1代表负。1位：0代表变大，1代表表小。',
  `precision` INT COMMENT '精度，字节为125时小数点在个位右边1位，每加1小数点向左移一位，每减1小数点向右移一位。',
  `limit` INT COMMENT '限制（0，下限，1上限）',
  `limitValue` INT COMMENT '模拟量数值',
  PRIMARY KEY (`cfgId`)
);

SELECT * FROM ats_AnalogQuantityCfg;