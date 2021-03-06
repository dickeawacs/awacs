DELIMITER $$

USE `ats`$$

DROP PROCEDURE IF EXISTS `buildData`$$

CREATE  PROCEDURE `buildData`( )
BEGIN
DECLARE onenum INT  DEFAULT 1;
DECLARE onemax INT DEFAULT 128;
DECLARE twonum INT  DEFAULT 0;  
DECLARE twomax INT DEFAULT 255;
DECLARE userIndex INT DEFAULT 0;
 
-- 一层设备循环
WHILE onenum<=onemax DO 
SET twonum=0; 
-- 二层设备循环
WHILE twonum<=twomax DO 
INSERT INTO `table_10_bak` (
  /**`field1`,*//***序号 ***/
  `field2`,/*** [废弃] ***/
  `field3`,/*** 一层设备网络地址 ***/
  `field4`,/*** 二层设备网络地址 ***/
  `field5`,/*** 设备启用状态位 ***/
  `field6`,/*** 输入交叉状态位 ***/
  `field7`,/*** 联动输出状态位 ***/
  `field8`,/*** 设备唯一ID ***/
  `field9`,/*** 设备生产序列号 ***/
  `field10`,/*** 设备种类 ***/
  `field11`,/*** 设备通讯正/异常 ***/
  `field12`,/*** 电压检测 ***/
  `field13`,/*** 设备被撬 ***/
  `field14`,/*** 输入状态 ***/
  `field15`,/*** 输出状态 ***/
  `field16`,/*** 用户防区布撤防状态 ***/	
  /****1.屏蔽输入。	1.0x00
	2.普通输入。	2.0x01
	3.立即防区。	3.0x02
	4.24小时防区。4.0x03 */	 
	 
  `field17`,/*** 1输入（防区）属性 ***/
  `field18`,/*** 2输入（防区）属性 ***/
  `field19`,/*** 3输入（防区）属性 ***/
  `field20`,/*** 4输入（防区）属性 ***/
  `field21`,/*** 5输入（防区）属性 ***/
  `field22`,/*** 6输入（防区）属性 ***/
  `field23`,/*** 7输入（防区）属性 ***/
  `field24`,/*** 8输入（防区）属性 ***/
  `field25`,/*** 1输入（防区）隶属属性 ***/
  `field26`,/*** 2输入（防区）隶属属性 ***/
  `field27`,/*** 3输入（防区）隶属属性 ***/
  `field28`,/*** 4输入（防区）隶属属性 ***/
  `field29`,/*** 5输入（防区）隶属属性 ***/
  `field30`,/*** 6输入（防区）隶属属性 ***/
  `field31`,/*** 7输入（防区）隶属属性 ***/
  `field32`,/*** 8输入（防区）隶属属性 ***/
  `field33`,/*** 输入1输入交叉信息 ***/
  `field34`,/*** 输入2输入交叉信息 ***/
  `field35`,/*** 输入3输入交叉信息 ***/
  `field36`,/*** 输入4输入交叉信息 ***/
  `field37`,/*** 输入5输入交叉信息 ***/
  `field38`,/*** 输入6输入交叉信息 ***/
  `field39`,/*** 输入7输入交叉信息 ***/
  `field40`,/*** 输入8输入交叉信息 ***/
  /****输入1联动///***/
  `field41`,/*** 输入1联动输出信息1 ***/
  `field42`,/*** 输入1联动输出信息2 ***/
  `field43`,/*** 输入1联动输出信息3 ***/
  `field44`,/*** 输入1联动输出信息4 ***/
  `field45`,/*** 输入1联动输出信息5 ***/
  `field46`,/*** 输入1联动输出信息6 ***/
  `field47`,/*** 输入1联动输出信息7 ***/
  `field48`,/*** 输入1联动输出信息8 ***/
  /**输入2联动/////////////**/
	
  `field49`,/*** 输入2联动输出信息1 ***/
  `field50`,/*** 输入2联动输出信息2 ***/
  `field51`,/*** 输入2联动输出信息3 ***/
  `field52`,/*** 输入2联动输出信息4 ***/
  `field53`,/*** 输入2联动输出信息5 ***/
  `field54`,/*** 输入2联动输出信息6 ***/
  `field55`,/*** 输入2联动输出信息7 ***/
  `field56`,/*** 输入2联动输出信息8 ***/
  /*输入3联动//////****/
  
  `field57`,/*** 输入3联动输出信息1 ***/
  `field58`,/*** 输入3联动输出信息2 ***/
  `field59`,/*** 输入3联动输出信息3 ***/
  `field60`,/*** 输入3联动输出信息4 ***/
  `field61`,/*** 输入3联动输出信息5 ***/
  `field62`,/*** 输入3联动输出信息6 ***/
  `field63`,/*** 输入3联动输出信息7 ***/
  `field64`,/*** 输入3联动输出信息8 ***/
  /*输入4联动*/
  `field65`,/*** 输入4联动输出信息1 ***/
  `field66`,/*** 输入4联动输出信息2 ***/
  `field67`,/*** 输入4联动输出信息3 ***/
  `field68`,/*** 输入4联动输出信息4 ***/
  `field69`,/*** 输入4联动输出信息5 ***/
  `field70`,/*** 输入4联动输出信息6 ***/
  `field71`,/*** 输入4联动输出信息7 ***/
  `field72`,/*** 输入4联动输出信息8 ***/
  /*输入5联动*/
  `field73`,/*** 输入5联动输出信息1 ***/
  `field74`,/*** 输入5联动输出信息2 ***/
  `field75`,/*** 输入5联动输出信息3 ***/
  `field76`,/*** 输入5联动输出信息4 ***/
  `field77`,/*** 输入5联动输出信息5 ***/
  `field78`,/*** 输入5联动输出信息6 ***/
  `field79`,/*** 输入5联动输出信息7 ***/
  `field80`,/*** 输入5联动输出信息8 ***/
  /*输入6联动*/
  `field81`,/*** 输入6联动输出信息1 ***/
  `field82`,/*** 输入6联动输出信息2 ***/
  `field83`,/*** 输入6联动输出信息3 ***/
  `field84`,/*** 输入6联动输出信息4 ***/
  `field85`,/*** 输入6联动输出信息5 ***/
  `field86`,/*** 输入6联动输出信息6 ***/
  `field87`,/*** 输入6联动输出信息7 ***/
  `field88`,/*** 输入6联动输出信息8 ***/
  /*输入7联动*/
  `field89`,/*** 输入7联动输出信息1 ***/
  `field90`,/*** 输入7联动输出信息2 ***/
  `field91`,/*** 输入7联动输出信息3 ***/
  `field92`,/*** 输入7联动输出信息4 ***/
  `field93`,/*** 输入7联动输出信息5 ***/
  `field94`,/*** 输入7联动输出信息6 ***/
  `field95`,/*** 输入7联动输出信息7 ***/
  `field96`,/*** 输入7联动输出信息8 ***/
  /*输入8联动*/
  `field97`,/*** 输入8联动输出信息1 ***/
  `field98`,/*** 输入8联动输出信息2 ***/
  `field99`,/*** 输入8联动输出信息3 ***/
  `field100`,/*** 输入8联动输出信息4 ***/
  `field101`,/*** 输入8联动输出信息5 ***/
  `field102`,/*** 输入8联动输出信息6 ***/
  `field103`,/*** 输入8联动输出信息7 ***/
  `field104`,/*** 输入8联动输出信息8 ***/
  
  `field105`,/*** 输入1名称 ***/
  `field106`,/*** 输入2名称 ***/
  `field107`,/*** 输入3名称 ***/
  `field108`,/*** 输入4名称 ***/
  `field109`,/*** 输入5名称 ***/
  `field110`,/*** 输入6名称 ***/
  `field111`,/*** 输入7名称 ***/
  `field112`,/*** 输入8名称 ***/
  
  `field113`,/*** 输出1名称 ***/
  `field114`,/*** 输出2名称 ***/
  `field115`,/*** 输出3名称 ***/
  `field116`,/*** 输出4名称 ***/
  `field117`,/*** 输出5名称 ***/
  `field118`,/*** 输出6名称 ***/
  `field119`,/*** 输出7名称 ***/
  `field120`,/*** 输出8名称 ***/
  
  `field121`,/*** 二层设备名称 ***/
  
  `field122`,/**输入1触发提示音**/
  `field123`,/**输入2触发提示音**/
  `field124`,/**输入3触发提示音**/
  `field125`,/**输入4触发提示音**/
  `field126`,/**输入5触发提示音**/
  `field127`,/**输入6触发提示音**/
  `field128`,/**输入7触发提示音**/
  `field129`,/**输入8触发提示音**/
  
  `field130`,/**屏蔽状态（1为屏蔽禁用）**/
  
  `field131`,/**输入1恢复提示音*/
  `field132`,/**输入2恢复提示音*/
  `field133`,/**输入3恢复提示音*/
  `field134`,/**输入4恢复提示音*/
  `field135`,/**输入5恢复提示音*/
  `field136`,/**输入6恢复提示音*/
  `field137`,/**输入7恢复提示音*/
  `field138`,/**输入8恢复提示音*/
  
  `field139`,/**输出1闭合提示音**/
  `field140`,/**输出2闭合提示音**/
  `field141`,/**输出3闭合提示音**/
  `field142`,/**输出4闭合提示音**/
  `field143`,/**输出5闭合提示音**/
  `field144`,/**输出6闭合提示音**/
  `field145`,/**输出7闭合提示音**/
  `field146`,/**输出8闭合提示音**/
  
  `field147`,/**输出1断开提示音**/
  `field148`,/**输出2断开提示音**/
  `field149`,/**输出3断开提示音**/
  `field150`,/**输出4断开提示音**/
  `field151`,/**输出5断开提示音**/
  `field152`,/**输出6断开提示音**/
  `field153`,/**输出7断开提示音**/
  `field154`,/**输出8断开提示音**/
  
  `field155`,/*输出1状态**/
  `field156`,/*输出2状态**/
  `field157`,/*输出3状态**/
  `field158`,/*输出4状态**/
  `field159`,/*输出5状态**/
  `field160`,/*输出6状态**/
  `field161`,/*输出7状态**/
  `field162`,/*输出8状态**/
  
  `oneType`,/**一层设备类型**/
  `fip`,/**设备的IP地址**/
  `fport`,/**设备的访问端口	**/
  `disabletel`,/**禁用电话端口  禁用为1，启用为0，默认为0**/
  `disablemessage`,/**禁用短信端口 禁用为1，启用为0，默认为0**/
  `telnum1`,/**接警中心号码一**/
  `telnum2`,/**接警中心号码2**/
  `telspace`,/**电话通迅间隔(分钟):**/
  `messagespace`,/**短信通迅间隔(分钟):**/
  `printset`,/**打印设置（禁止时为“”，不禁止时为"报警事件,普通输入事件,用户操作事件"，其中0表示未选中，1表示选中**/
  `dtype`/**设置类型(1:一层设备，2：二层设备）**/
  
) 
VALUES
  (
  
  /**`field1`,*//***序号 ***/
  NULL ,/*** [废弃] ***/
   onenum,/*** 一层设备网络地址 ***/
   twonum,/*** 二层设备网络地址 ***/
  0,/*** 设备启用状态位 ***/
  0,/*** 输入交叉状态位 ***/
  0,/*** 联动输出状态位 ***/
  '0',/*** 设备唯一ID ***/
  '0',/*** 设备生产序列号 ***/
  0,/*** 设备种类 ***/
  0,/*** 设备通讯正/异常 ***/
  0,/*** 电压检测 ***/
  0,/*** 设备被撬 ***/
  0,/*** 输入状态 ***/
  0,/*** 输出状态 ***/
  0,/*** 用户防区布撤防状态 ***/	
  /****1.屏蔽输入。	1.0x00
	2.普通输入。	2.0x01
	3.立即防区。	3.0x02
	4.24小时防区。4.0x03 */	 
	 
  IF(TWONUM=0,1,1),/*** 1输入（防区）属性 ***/
  IF(TWONUM=0,2,1),/*** 2输入（防区）属性 ***/
  IF(TWONUM=0,3,1),/*** 3输入（防区）属性 ***/
  IF(TWONUM=0,4,1),/*** 4输入（防区）属性 ***/
  IF(TWONUM=0,5,1),/*** 5输入（防区）属性 ***/
  IF(TWONUM=0,6,1),/*** 6输入（防区）属性 ***/
  IF(TWONUM=0,7,1),/*** 7输入（防区）属性 ***/
  IF(TWONUM=0,8,1),/*** 8输入（防区）属性 ***/
 
  0,/*** 1输入（防区）隶属属性 ***/
  0,/*** 2输入（防区）隶属属性 ***/
  0,/*** 3输入（防区）隶属属性 ***/
  0,/*** 4输入（防区）隶属属性 ***/
  0,/*** 5输入（防区）隶属属性 ***/
  0,/*** 6输入（防区）隶属属性 ***/
  0,/*** 7输入（防区）隶属属性 ***/
  0,/*** 8输入（防区）隶属属性 ***/ 
  /**输入选择(输入1-8)--,交叉信息1字节,交叉信息2字节,交叉信息3字节输入选择**/
   '0,0,0',/*** 输入1输入交叉信息 ***/
   '0,0,0',/*** 输入2输入交叉信息 ***/
   '0,0,0',/*** 输入3输入交叉信息 ***/
   '0,0,0',/*** 输入4输入交叉信息 ***/
   '0,0,0',/*** 输入5输入交叉信息 ***/
   '0,0,0',/*** 输入6输入交叉信息 ***/
   '0,0,0',/*** 输入7输入交叉信息 ***/
   '0,0,0',/*** 输入8输入交叉信息 ***/
   
   /***输入选择(输入1-8，默认1),输出联动输出（1-8）--,联动输出的1层地址,联动输出的2层地址，联动的输出端口号，联动属性，**/   
  '0,0,0,0,1',/*** 输入1联动输出信息1 ***/
  '0,0,0,0,1',/*** 输入1联动输出信息2 ***/
  '0,0,0,0,1',/*** 输入1联动输出信息3 ***/
  '0,0,0,0,1',/*** 输入1联动输出信息4 ***/
  '0,0,0,0,1',/*** 输入1联动输出信息5 ***/
  '0,0,0,0,1',/*** 输入1联动输出信息6 ***/
  '0,0,0,0,1',/*** 输入1联动输出信息7 ***/
  '0,0,0,0,1',/*** 输入1联动输出信息8 ***/
  /**输入2联动/////////////**/
	
  '0,0,0,0,1',/*** 输入2联动输出信息1 ***/
  '0,0,0,0,1',/*** 输入2联动输出信息2 ***/
  '0,0,0,0,1',/*** 输入2联动输出信息3 ***/
  '0,0,0,0,1',/*** 输入2联动输出信息4 ***/
  '0,0,0,0,1',/*** 输入2联动输出信息5 ***/
  '0,0,0,0,1',/*** 输入2联动输出信息6 ***/
  '0,0,0,0,1',/*** 输入2联动输出信息7 ***/
  '0,0,0,0,1',/*** 输入2联动输出信息8 ***/
  /*输入3联动//////****/
  
   '0,0,0,0,1',/*** 输入3联动输出信息1 ***/
  '0,0,0,0,1',/*** 输入3联动输出信息2 ***/
  '0,0,0,0,1',/*** 输入3联动输出信息3 ***/
  '0,0,0,0,1',/*** 输入3联动输出信息4 ***/
  '0,0,0,0,1',/*** 输入3联动输出信息5 ***/
  '0,0,0,0,1',/*** 输入3联动输出信息6 ***/
  '0,0,0,0,1',/*** 输入3联动输出信息7 ***/
  '0,0,0,0,1',/*** 输入3联动输出信息8 ***/
  /*输入4联动*/
     '0,0,0,0,1',/*** 输入4联动输出信息1 ***/
  '0,0,0,0,1',/*** 输入4联动输出信息2 ***/
  '0,0,0,0,1',/*** 输入4联动输出信息3 ***/
  '0,0,0,0,1',/*** 输入4联动输出信息4 ***/
  '0,0,0,0,1',/*** 输入4联动输出信息5 ***/
  '0,0,0,0,1',/*** 输入4联动输出信息6 ***/
  '0,0,0,0,1',/*** 输入4联动输出信息7 ***/
  '0,0,0,0,1',/*** 输入4联动输出信息8 ***/
  /*输入5联动*/
    '0,0,0,0,1',/*** 输入5联动输出信息1 ***/
  '0,0,0,0,1',/*** 输入5联动输出信息2 ***/
  '0,0,0,0,1',/*** 输入5联动输出信息3 ***/
  '0,0,0,0,1',/*** 输入5联动输出信息4 ***/
  '0,0,0,0,1',/*** 输入5联动输出信息5 ***/
  '0,0,0,0,1',/*** 输入5联动输出信息6 ***/
  '0,0,0,0,1',/*** 输入5联动输出信息7 ***/
  '0,0,0,0,1',/*** 输入5联动输出信息8 ***/
  /*输入6联动*/
   '0,0,0,0,1',/*** 输入6联动输出信息1 ***/
  '0,0,0,0,1',/*** 输入6联动输出信息2 ***/
  '0,0,0,0,1',/*** 输入6联动输出信息3 ***/
  '0,0,0,0,1',/*** 输入6联动输出信息4 ***/
  '0,0,0,0,1',/*** 输入6联动输出信息5 ***/
  '0,0,0,0,1',/*** 输入6联动输出信息6 ***/
  '0,0,0,0,1',/*** 输入6联动输出信息7 ***/
  '0,0,0,0,1',/*** 输入6联动输出信息8 ***/
  /*输入7联动*/
  '0,0,0,0,1',/*** 输入7联动输出信息1 ***/
  '0,0,0,0,1',/*** 输入7联动输出信息2 ***/
  '0,0,0,0,1',/*** 输入7联动输出信息3 ***/
  '0,0,0,0,1',/*** 输入7联动输出信息4 ***/
  '0,0,0,0,1',/*** 输入7联动输出信息5 ***/
  '0,0,0,0,1',/*** 输入7联动输出信息6 ***/
  '0,0,0,0,1',/*** 输入7联动输出信息7 ***/
  '0,0,0,0,1',/*** 输入7联动输出信息8 ***/
  /*输入8联动*/
  '0,0,0,0,1',/*** 输入8联动输出信息1 ***/
  '0,0,0,0,1',/*** 输入8联动输出信息2 ***/
  '0,0,0,0,1',/*** 输入8联动输出信息3 ***/
  '0,0,0,0,1',/*** 输入8联动输出信息4 ***/
  '0,0,0,0,1',/*** 输入8联动输出信息5 ***/
  '0,0,0,0,1',/*** 输入8联动输出信息6 ***/
  '0,0,0,0,1',/*** 输入8联动输出信息7 ***/
  '0,0,0,0,1',/*** 输入8联动输出信息8 ***/
  
  CONCAT('设备',LPAD(twonum,3,'0'),1,'输入'),/*** 输入1名称 ***/
  CONCAT('设备',LPAD(twonum,3,'0'),2,'输入'),/*** 输入2名称 ***/
  CONCAT('设备',LPAD(twonum,3,'0'),3,'输入'),/*** 输入3名称 ***/
  CONCAT('设备',LPAD(twonum,3,'0'),4,'输入'),/*** 输入4名称 ***/
  CONCAT('设备',LPAD(twonum,3,'0'),5,'输入'),/*** 输入5名称 ***/
  CONCAT('设备',LPAD(twonum,3,'0'),6,'输入'),/*** 输入6名称 ***/
  CONCAT('设备',LPAD(twonum,3,'0'),7,'输入'),/*** 输入7名称 ***/
  CONCAT('设备',LPAD(twonum,3,'0'),8,'输入'),/*** 输入8名称 ***/
  
  CONCAT('设备',LPAD(twonum,3,'0'),1,'输出'),/*** 输出1名称 ***/
  CONCAT('设备',LPAD(twonum,3,'0'),2,'输出'),/*** 输出2名称 ***/
  CONCAT('设备',LPAD(twonum,3,'0'),3,'输出'),/*** 输出3名称 ***/
  CONCAT('设备',LPAD(twonum,3,'0'),4,'输出'),/*** 输出4名称 ***/
  CONCAT('设备',LPAD(twonum,3,'0'),5,'输出'),/*** 输出5名称 ***/
  CONCAT('设备',LPAD(twonum,3,'0'),6,'输出'),/*** 输出6名称 ***/
  CONCAT('设备',LPAD(twonum,3,'0'),7,'输出'),/*** 输出7名称 ***/
  CONCAT('设备',LPAD(twonum,3,'0'),8,'输出'),/*** 输出8名称 ***/  
  ( CASE WHEN twonum=0 THEN CONCAT( '一层设备',LPAD(onenum,3,'0')) ELSE CONCAT( '二层设备',LPAD(twonum,3,'0')) END ),/*** 二层设备名称 ***/
  
  1,/**输入1触发提示音**/
  1,/**输入2触发提示音**/
  1,/**输入3触发提示音**/
  1,/**输入4触发提示音**/
  1,/**输入5触发提示音**/
  1,/**输入6触发提示音**/
  1,/**输入7触发提示音**/
  1,/**输入8触发提示音**/
  
  0,/**屏蔽状态（1为屏蔽禁用）**/
  
  2,/**输入1恢复提示音*/
  2,/**输入2恢复提示音*/
  2,/**输入3恢复提示音*/
  2,/**输入4恢复提示音*/
  2,/**输入5恢复提示音*/
  2,/**输入6恢复提示音*/
  2,/**输入7恢复提示音*/
  2,/**输入8恢复提示音*/
  
  3,/**输出1闭合提示音**/
  3,/**输出2闭合提示音**/
  3,/**输出3闭合提示音**/
  3,/**输出4闭合提示音**/
  3,/**输出5闭合提示音**/
  3,/**输出6闭合提示音**/
  3,/**输出7闭合提示音**/
  3,/**输出8闭合提示音**/
  
  4,/**输出1断开提示音**/
  4,/**输出2断开提示音**/
  4,/**输出3断开提示音**/
  4,/**输出4断开提示音**/
  4,/**输出5断开提示音**/
  4,/**输出6断开提示音**/
  4,/**输出7断开提示音**/
  4,/**输出8断开提示音**/
  
  0,/*输出1状态**/
  0,/*输出2状态**/
  0,/*输出3状态**/
  0,/*输出4状态**/
  0,/*输出5状态**/
  0,/*输出6状态**/
  0,/*输出7状态**/
  0,/*输出8状态**/
  
  0,/**一层设备类型**/
  NULL,/**设备的IP地址**/
  NULL,/**设备的访问端口	**/
  '0',/**禁用电话端口  禁用为1，启用为0，默认为0**/
  '0',/**禁用短信端口 禁用为1，启用为0，默认为0**/
  '',/**接警中心号码一**/
  '',/**接警中心号码2**/
  0,/**电话通迅间隔(分钟):**/
  0,/**短信通迅间隔(分钟):**/
  '1,1,1,1',/**打印设置（禁止时为“”，不禁止时为"报警事件,普通输入事件,用户操作事件"，其中0表示未选中，1表示选中**/
  IF(TWONUM=0,1,2)/**设置类型(1:一层设备，2：二层设备）**/
  ) ;
  
IF TWONUM=0 THEN 
SET userIndex=0;
WHILE userIndex<=16 DO 
INSERT INTO `ats`.`table_1_bak`
            ( 
             `field2`,
             `field3`,
             `field4`,
             `field5`,
             `field6`,
             `field7`,
             `field8`,
             `field9`,
             `field10`,
             `field11`,
             `field12`,
             `field13`,
             `field14`,
             `field100`)
VALUES ( 
	-- 冗余的名字
        IF(userIndex=0,CONCAT(LPAD(onenum,4,'0'),'管理员'),CONCAT(LPAD(onenum,4,'0'),'用户',LPAD(userIndex,2,'0'))),
        -- 用户名
         IF(userIndex=0,CONCAT(LPAD(onenum,4,'0'),'管理员'),CONCAT(LPAD(onenum,4,'0'),'用户',LPAD(userIndex,2,'0'))),
        -- 密码
       IF(userIndex=0,'12345678','123456'),
       -- 用户类型 2设备管理员，3用户
        IF(userIndex=0,2,3),
        0,
        '',
        0,
        '',
        0,
        onenum,
        userIndex,
        0,
        0,
        '');
        SET userIndex=userIndex+1;
END WHILE ;

END IF ;
-- 2层设备编号加1
SET twonum=twonum+1; 

END WHILE;
-- 1层设备编号加1
SET onenum=onenum+1; 

END WHILE;
 COMMIT ;
 
 
    END$$

DELIMITER ;