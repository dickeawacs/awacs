package com.cdk.ats.udp.operating;

import java.net.DatagramPacket;

import org.apache.log4j.Logger;

import com.cdk.ats.udp.cache.AtsEquipmentCache;
import com.cdk.ats.udp.cache.EquipmentCacheVO;
import com.cdk.ats.udp.exception.AtsException;
import com.cdk.ats.udp.process.ActionContext;
import com.cdk.ats.udp.process.ActionParams;
import com.cdk.ats.udp.receiver.ReceiverContext;
import com.cdk.ats.udp.reset.ResetContext;
import com.cdk.ats.udp.transmitter.TransmitterContext;
import com.cdk.ats.udp.utils.CommandTools;
import com.cdk.ats.udp.utils.Constant;
import com.cdk.ats.udp.webplug.dao.DaoFactory;
import com.cdk.ats.web.dao.BaseDao;
import com.cdk.ats.web.dao.SystemDao;
import com.cdk.ats.web.dao.Table10DAO;
import com.cdk.ats.web.dao.Table1DAO;
import com.cdk.ats.web.pojo.hbm.Table1;
import com.cdk.ats.web.pojo.hbm.Table10;
import common.cdk.config.files.msgconfig.MsgConfig;
import common.cdk.config.files.sqlconfig.SqlConfig;

/****
 * 系统设置
 * 
 * @author cdk
 * 
 */
public class Operating_0x10 {
	
	private static Logger logger = Logger.getLogger(Operating_0x10.class);
	private Table1DAO t1dao;
	private Table10DAO t10DAO;
	private BaseDao baseDao;
	private SystemDao sysdao;
	public void closeDao(){
		if(this.t10DAO!=null)this.t10DAO.closeSession();
		if(this.t1dao!=null)this.t1dao.closeSession();
		if(this.baseDao!=null)this.baseDao.closeSession();
		if(this.sysdao!=null)this.sysdao.closeSession();
	}
	private SystemDao getSysdao(){
		if(sysdao==null){
			sysdao=DaoFactory.getSysDao();
		}
		return sysdao;
	}
	private BaseDao getBaseDao(){
		if(baseDao==null){
			baseDao=DaoFactory.getBaseDao();
		}
		return baseDao;
	}
	private Table10DAO getT10DAO() {
		if (t10DAO == null) {
			t10DAO = DaoFactory.getTable10Dao();
		}
		return t10DAO;
	}
	public void CloseDao(){
		if(t1dao!=null){
			t1dao.closeSession();
			
		}
		
	}	
	public Table1DAO getT1dao() {
		if (t1dao == null) {
			t1dao = DaoFactory.getTable1Dao();
		}
		return t1dao;
	}
 

	private DatagramPacket dp;

	public DatagramPacket getDp() {
		return dp;
	}

	public void setDp(DatagramPacket dp) {
		this.dp = dp;
	}

	/****
	 *  
	 *  7 上报交叉设置信息
	 *  **/
	public Object process_0x10_0x01() {
		boolean end=false;
		byte[] data = dp.getData();
		int key=0;
		try {
			 int  one,two,the_port,to_one,to_two,action;
			 
			 /*** 一层设备网络地址 ***/
			 one=data[9]&0xff;
			 //二层设备地址
			 two=data[12]&0xff;
			 //输入选择（当前的输入端口）
			 the_port=data[14]&0xff;
			 /**交叉输入所在的1层设备地址。	**/
			 to_one=data[15]&0xff;
			 /**交叉输入所在的2层设备地址。**/
			 to_two=data[16]&0xff;
			 /** 输入选择 ( 通过数字的8个位来标记对应的输入交叉端口)
			  * 1,2,4,8,16,32,64,128
			  * **/
			 action=data[17]&0xff;
			 
			key= ReceiverContext.putReceiveSequnce(CommandTools.getSequnces(data));
					{
						String queryString=null;
						switch (the_port) {
						case 1:
							queryString=SqlConfig.SQL("ats.table10.update.0x10.0x02-1");
							break;
						case 2:
							queryString=SqlConfig.SQL("ats.table10.update.0x10.0x02-2");				
							break;
						case 4:
							queryString=SqlConfig.SQL("ats.table10.update.0x10.0x02-4");
							break;
						case 8:
							queryString=SqlConfig.SQL("ats.table10.update.0x10.0x02-8");
							break;
						case 16:
							queryString=SqlConfig.SQL("ats.table10.update.0x10.0x02-16");
							break;
						case 32:
							queryString=SqlConfig.SQL("ats.table10.update.0x10.0x02-32");
							break;
						case 64:
							queryString=SqlConfig.SQL("ats.table10.update.0x10.0x02-64");
							break;
						case 128:
							queryString=SqlConfig.SQL("ats.table10.update.0x10.0x02-128");
							break;
						default:
							throw new AtsException("ats-1010");
						}

				/*
				 * switch (action) { case 1: action=1; break; case 2: action=2;
				 * break; case 4: action=3; break; case 8: action=4; break; case
				 * 16: action=5; break; case 32: action=6; break; case 64:
				 * action=7; break; case 128: action=8; break; default:
				 * action=0; }
				 */
						StringBuffer sb=new StringBuffer();
						//交叉一层设备地块                                                          交叉二层设备地址                                                          //交叉设备禁用标记
						//sb.append(to_one).append(",").append(to_two).append(",").append(action).append(",").append((action==0?1:0));
						sb.append(to_one).append(",").append(to_two).append(",").append(action);
						int count=this.getBaseDao().updateByHql(queryString, new Object[]{sb.toString(),one,two});
						if(count>0)end=true;
					}
			   
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			//无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);
			 /*TransmitterContext.pushDatagram(ResponseCommandTools.responseCmdBase12((byte)0x10,(byte)0x01,data[4], data[5], data[6], data[7],
					data[9], data[10], (byte)(end?0x00:0xff)),false,key,dp.getAddress().toString(),dp.getPort());*/		
		}
		return end;
	}
	
	/****
	 * 下传交叉设置信息
	 * 设备中心响应0x10-0x02的数据包处理方法  
	 * return 是否处理成功
	 *  **/
	public void process_0x10_0x02() {
		byte[] data = dp.getData();
		int key=CommandTools.formateSequnceToInt(data);
		/***
		 * 验证流水号是否存在 
		 */
		if(key<0||!TransmitterContext.checek_Sequnce(key))
			{return;}
		ActionParams ap=null;
		//取出缓存的数据
		ap= ActionContext.getValues(key);
		if(ap==null||!CommandTools.isValidDP(data, ap))return;
		
		try {
			//判断命令是否成功！
			if(CommandTools.isSuccess(data)){
				String queryString=null;
				
				int count=0;
				switch (ap.getComand0()) {
				case 1:
					queryString=SqlConfig.SQL("ats.table10.update.0x10.0x02-1");
					break;
				case 2:
					queryString=SqlConfig.SQL("ats.table10.update.0x10.0x02-2");				
					break;
				case 4:
					queryString=SqlConfig.SQL("ats.table10.update.0x10.0x02-4");
					break;
				case 8:
					queryString=SqlConfig.SQL("ats.table10.update.0x10.0x02-8");
					break;
				case 16:
					queryString=SqlConfig.SQL("ats.table10.update.0x10.0x02-16");
					break;
				case 32:
					queryString=SqlConfig.SQL("ats.table10.update.0x10.0x02-32");
					break;
				case 64:
					queryString=SqlConfig.SQL("ats.table10.update.0x10.0x02-64");
					break;
				case 128:
					queryString=SqlConfig.SQL("ats.table10.update.0x10.0x02-128");
					break;
				default:
					throw new AtsException("ats-1010");
				}
				StringBuffer sb=new StringBuffer();
				//交叉一层设备地块                                                          交叉二层设备地址                                                          //交叉设备禁用标记
				//sb.append(ap.getComand1()).append(",").append(ap.getComand2()).append(",").append(ap.getComand3()).append(",").append((ap.getComand3()==0?1:0));
				sb.append(ap.getComand1()).append(",").append(ap.getComand2()).append(",").append(ap.getComand3());
				count=this.getBaseDao().updateByHql(queryString, new Object[]{sb.toString(),ap.getOneP(),ap.getTwoP()});
				if(count>0)ActionContext.setState(key, true, "操作成功！");
				else	ActionContext.setState(key,false,MsgConfig.msg("ats-1024"));
			}else	ActionContext.setState(key,false,MsgConfig.msg("ats-1024"));	 
		} catch (Exception e) {
			ActionContext.setState(key,false,MsgConfig.msg("ats-1024"));
			logger.error(e.getMessage(),e);
		}
	
	}
	/****
	 *  9 上报输入联动信息
	 *  @return  成功与否 
	 * **/
	public Object process_0x10_0x03() {
		boolean end=false;
		byte[] data = dp.getData();
		int key=0;
		try {
			 int  one,two,thePort,seq,to_one,to_two,output,action;
			 
			 /*** 一层设备网络地址 ***/
			 one=data[9]&0xff;
			 two=data[12]&0xff;
			 //当前的输入 端口
			 thePort=data[14]&0xff;
			 //当前输入端口的第几个联动
			 seq=data[15]&0xff;
			 /**联动输出所在的1层设备地址。	**/
			 to_one=data[16]&0xff;
			 /**联动输出所在的2层设备地址。**/
			 to_two=data[17]&0xff;
			 
			 /** 联动输出所在的联出端口（即联动输出端口）**/
			 output=data[18]&0xff;
			 /**联动属性	**/
			 action=data[19]&0xff;
			 
			key= ReceiverContext.putReceiveSequnce(CommandTools.getSequnces(data));

			int count=0;
			String hqlString=null;
				switch (thePort) {
				//输入端口1的8个联动输出
				case 1:{
						switch (seq) {
						case 1:
							hqlString=SqlConfig.SQL("ats.table10.update.field41");
							break;
						case 2:
							hqlString=SqlConfig.SQL("ats.table10.update.field42");
							break;
						case 3:
							hqlString=SqlConfig.SQL("ats.table10.update.field43");
							break;
						case 4:
							hqlString=SqlConfig.SQL("ats.table10.update.field44");
							break;
						case 5:
							hqlString=SqlConfig.SQL("ats.table10.update.field45");
							break;
						case 6:
							hqlString=SqlConfig.SQL("ats.table10.update.field46");
							break;
						case 7:
							hqlString=SqlConfig.SQL("ats.table10.update.field47");
							break;
						case 8:
							hqlString=SqlConfig.SQL("ats.table10.update.field48");
							break;
						}
					}
					break;
				case 2:
				{
					switch (seq) {
					case 1:
						hqlString=SqlConfig.SQL("ats.table10.update.field49");
						break;
					case 2:
						hqlString=SqlConfig.SQL("ats.table10.update.field50");
						break;
					case 3:
						hqlString=SqlConfig.SQL("ats.table10.update.field51");
						break;
					case 4:
						hqlString=SqlConfig.SQL("ats.table10.update.field52");
						break;
					case 5:
						hqlString=SqlConfig.SQL("ats.table10.update.field53");
						break;
					case 6:
						hqlString=SqlConfig.SQL("ats.table10.update.field54");
						break;
					case 7:
						hqlString=SqlConfig.SQL("ats.table10.update.field55");
						break;
					case 8:
						hqlString=SqlConfig.SQL("ats.table10.update.field56");
						break;
					}
				}break;
				case 4:
				{
					switch (seq) {
					case 1:
						hqlString=SqlConfig.SQL("ats.table10.update.field57");
						break;
					case 2:
						hqlString=SqlConfig.SQL("ats.table10.update.field58");
						break;
					case 3:
						hqlString=SqlConfig.SQL("ats.table10.update.field59");
						break;
					case 4:
						hqlString=SqlConfig.SQL("ats.table10.update.field60");
						break;
					case 5:
						hqlString=SqlConfig.SQL("ats.table10.update.field61");
						break;
					case 6:
						hqlString=SqlConfig.SQL("ats.table10.update.field62");
						break;
					case 7:
						hqlString=SqlConfig.SQL("ats.table10.update.field63");
						break;
					case 8:
						hqlString=SqlConfig.SQL("ats.table10.update.field64");
						break;
					}
				}break;
				case 8:
				{
					switch (seq) {
					case 1:
						hqlString=SqlConfig.SQL("ats.table10.update.field65");
						break;
					case 2:
						hqlString=SqlConfig.SQL("ats.table10.update.field66");
						break;
					case 3:
						hqlString=SqlConfig.SQL("ats.table10.update.field67");
						break;
					case 4:
						hqlString=SqlConfig.SQL("ats.table10.update.field68");
						break;
					case 5:
						hqlString=SqlConfig.SQL("ats.table10.update.field69");
						break;
					case 6:
						hqlString=SqlConfig.SQL("ats.table10.update.field70");
						break;
					case 7:
						hqlString=SqlConfig.SQL("ats.table10.update.field71");
						break;
					case 8:
						hqlString=SqlConfig.SQL("ats.table10.update.field72");
						break;
					}
				}break;
				case 16:
				{
					switch (seq) {
					case 1:
						hqlString=SqlConfig.SQL("ats.table10.update.field73");
						break;
					case 2:
						hqlString=SqlConfig.SQL("ats.table10.update.field74");
						break;
					case 3:
						hqlString=SqlConfig.SQL("ats.table10.update.field75");
						break;
					case 4:
						hqlString=SqlConfig.SQL("ats.table10.update.field76");
						break;
					case 5:
						hqlString=SqlConfig.SQL("ats.table10.update.field77");
						break;
					case 6:
						hqlString=SqlConfig.SQL("ats.table10.update.field78");
						break;
					case 7:
						hqlString=SqlConfig.SQL("ats.table10.update.field79");
						break;
					case 8:
						hqlString=SqlConfig.SQL("ats.table10.update.field80");
						break;
					}
				}break;
				case 32:
				{
					switch (seq) {
					case 1:
						hqlString=SqlConfig.SQL("ats.table10.update.field81");
						break;
					case 2:
						hqlString=SqlConfig.SQL("ats.table10.update.field82");
						break;
					case 3:
						hqlString=SqlConfig.SQL("ats.table10.update.field83");
						break;
					case 4:
						hqlString=SqlConfig.SQL("ats.table10.update.field84");
						break;
					case 5:
						hqlString=SqlConfig.SQL("ats.table10.update.field85");
						break;
					case 6:
						hqlString=SqlConfig.SQL("ats.table10.update.field86");
						break;
					case 7:
						hqlString=SqlConfig.SQL("ats.table10.update.field87");
						break;
					case 8:
						hqlString=SqlConfig.SQL("ats.table10.update.field88");
						break;
					}
				}break;
				case 64:
				{
					switch (seq) {
					case 1:
						hqlString=SqlConfig.SQL("ats.table10.update.field89");
						break;
					case 2:
						hqlString=SqlConfig.SQL("ats.table10.update.field90");
						break;
					case 3:
						hqlString=SqlConfig.SQL("ats.table10.update.field91");
						break;
					case 4:
						hqlString=SqlConfig.SQL("ats.table10.update.field92");
						break;
					case 5:
						hqlString=SqlConfig.SQL("ats.table10.update.field93");
						break;
					case 6:
						hqlString=SqlConfig.SQL("ats.table10.update.field94");
						break;
					case 7:
						hqlString=SqlConfig.SQL("ats.table10.update.field95");
						break;
					case 8:
						hqlString=SqlConfig.SQL("ats.table10.update.field96");
						break;
					}
				}break;
				case 128:
				{
					switch (seq) {
					case 1:
						hqlString=SqlConfig.SQL("ats.table10.update.field97");
						break;
					case 2:
						hqlString=SqlConfig.SQL("ats.table10.update.field98");
						break;
					case 3:
						hqlString=SqlConfig.SQL("ats.table10.update.field99");
						break;
					case 4:
						hqlString=SqlConfig.SQL("ats.table10.update.field100");
						break;
					case 5:
						hqlString=SqlConfig.SQL("ats.table10.update.field101");
						break;
					case 6:
						hqlString=SqlConfig.SQL("ats.table10.update.field102");
						break;
					case 7:
						hqlString=SqlConfig.SQL("ats.table10.update.field103");
						break;
					case 8:
						hqlString=SqlConfig.SQL("ats.table10.update.field104");
						break;
					}
				}break;
				}
			
			if(hqlString!=null&&!hqlString.equals("")){
				StringBuffer sb=new StringBuffer();
				sb.append(to_one).append(",").append(to_two).append(",").append(output)
				.append(",").append(action).append(",").append((output==0?1:0));
				count=getBaseDao().updateByHql(hqlString, new Object[]{sb.toString(),one,two});
			}
				
			if(count>0)end=true;
		
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			//无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);
			/*TransmitterContext.pushDatagram(ResponseCommandTools.responseCmdBase12((byte)0x10,(byte)0x03,data[4], data[5], data[6], data[7],
					data[9], data[10], (byte)(end?0x00:0xff)),false,key,dp.getAddress().toString(),dp.getPort());*/			
		}
		return end;
	}
 
	/****
	 * 10 下传输入联动信息 
	 * **/
	public void process_0x10_0x04() {
		byte[] data = dp.getData();
		
		int key=CommandTools.formateSequnceToInt(data);
		/***
		 * 验证流水号是否存在 
		 */
		if(key<0||!TransmitterContext.checek_Sequnce(key))
			{return;}
		ActionParams ap=null;
		//取出缓存的数据
		ap= ActionContext.getValues(key);
		if(ap==null||!CommandTools.isValidDP(data, ap))return;
		
		try {
			//判断命令是否成功！
			if(CommandTools.isSuccess(data)){
				int count=0;
				String hqlString=null;
					switch (ap.getComand0()) {
					//输入端口1的8个联动输出
					case 1:{
							switch (ap.getComand1()) {
							case 1:
								hqlString=SqlConfig.SQL("ats.table10.update.field41");
								break;
							case 2:
								hqlString=SqlConfig.SQL("ats.table10.update.field42");
								break;
							case 3:
								hqlString=SqlConfig.SQL("ats.table10.update.field43");
								break;
							case 4:
								hqlString=SqlConfig.SQL("ats.table10.update.field44");
								break;
							case 5:
								hqlString=SqlConfig.SQL("ats.table10.update.field45");
								break;
							case 6:
								hqlString=SqlConfig.SQL("ats.table10.update.field46");
								break;
							case 7:
								hqlString=SqlConfig.SQL("ats.table10.update.field47");
								break;
							case 8:
								hqlString=SqlConfig.SQL("ats.table10.update.field48");
								break;
							}
						}
						break;
					case 2:
					{
						switch (ap.getComand1()) {
						case 1:
							hqlString=SqlConfig.SQL("ats.table10.update.field49");
							break;
						case 2:
							hqlString=SqlConfig.SQL("ats.table10.update.field50");
							break;
						case 3:
							hqlString=SqlConfig.SQL("ats.table10.update.field51");
							break;
						case 4:
							hqlString=SqlConfig.SQL("ats.table10.update.field52");
							break;
						case 5:
							hqlString=SqlConfig.SQL("ats.table10.update.field53");
							break;
						case 6:
							hqlString=SqlConfig.SQL("ats.table10.update.field54");
							break;
						case 7:
							hqlString=SqlConfig.SQL("ats.table10.update.field55");
							break;
						case 8:
							hqlString=SqlConfig.SQL("ats.table10.update.field56");
							break;
						}
					}break;
					case 4:
					{
						switch (ap.getComand1()) {
						case 1:
							hqlString=SqlConfig.SQL("ats.table10.update.field57");
							break;
						case 2:
							hqlString=SqlConfig.SQL("ats.table10.update.field58");
							break;
						case 3:
							hqlString=SqlConfig.SQL("ats.table10.update.field59");
							break;
						case 4:
							hqlString=SqlConfig.SQL("ats.table10.update.field60");
							break;
						case 5:
							hqlString=SqlConfig.SQL("ats.table10.update.field61");
							break;
						case 6:
							hqlString=SqlConfig.SQL("ats.table10.update.field62");
							break;
						case 7:
							hqlString=SqlConfig.SQL("ats.table10.update.field63");
							break;
						case 8:
							hqlString=SqlConfig.SQL("ats.table10.update.field64");
							break;
						}
					}break;
					case 8:
					{
						switch (ap.getComand1()) {
						case 1:
							hqlString=SqlConfig.SQL("ats.table10.update.field65");
							break;
						case 2:
							hqlString=SqlConfig.SQL("ats.table10.update.field66");
							break;
						case 3:
							hqlString=SqlConfig.SQL("ats.table10.update.field67");
							break;
						case 4:
							hqlString=SqlConfig.SQL("ats.table10.update.field68");
							break;
						case 5:
							hqlString=SqlConfig.SQL("ats.table10.update.field69");
							break;
						case 6:
							hqlString=SqlConfig.SQL("ats.table10.update.field70");
							break;
						case 7:
							hqlString=SqlConfig.SQL("ats.table10.update.field71");
							break;
						case 8:
							hqlString=SqlConfig.SQL("ats.table10.update.field72");
							break;
						}
					}break;
					case 16:
					{
						switch (ap.getComand1()) {
						case 1:
							hqlString=SqlConfig.SQL("ats.table10.update.field73");
							break;
						case 2:
							hqlString=SqlConfig.SQL("ats.table10.update.field74");
							break;
						case 3:
							hqlString=SqlConfig.SQL("ats.table10.update.field75");
							break;
						case 4:
							hqlString=SqlConfig.SQL("ats.table10.update.field76");
							break;
						case 5:
							hqlString=SqlConfig.SQL("ats.table10.update.field77");
							break;
						case 6:
							hqlString=SqlConfig.SQL("ats.table10.update.field78");
							break;
						case 7:
							hqlString=SqlConfig.SQL("ats.table10.update.field79");
							break;
						case 8:
							hqlString=SqlConfig.SQL("ats.table10.update.field80");
							break;
						}
					}break;
					case 32:
					{
						switch (ap.getComand1()) {
						case 1:
							hqlString=SqlConfig.SQL("ats.table10.update.field81");
							break;
						case 2:
							hqlString=SqlConfig.SQL("ats.table10.update.field82");
							break;
						case 3:
							hqlString=SqlConfig.SQL("ats.table10.update.field83");
							break;
						case 4:
							hqlString=SqlConfig.SQL("ats.table10.update.field84");
							break;
						case 5:
							hqlString=SqlConfig.SQL("ats.table10.update.field85");
							break;
						case 6:
							hqlString=SqlConfig.SQL("ats.table10.update.field86");
							break;
						case 7:
							hqlString=SqlConfig.SQL("ats.table10.update.field87");
							break;
						case 8:
							hqlString=SqlConfig.SQL("ats.table10.update.field88");
							break;
						}
					}break;
					case 64:
					{
						switch (ap.getComand1()) {
						case 1:
							hqlString=SqlConfig.SQL("ats.table10.update.field89");
							break;
						case 2:
							hqlString=SqlConfig.SQL("ats.table10.update.field90");
							break;
						case 3:
							hqlString=SqlConfig.SQL("ats.table10.update.field91");
							break;
						case 4:
							hqlString=SqlConfig.SQL("ats.table10.update.field92");
							break;
						case 5:
							hqlString=SqlConfig.SQL("ats.table10.update.field93");
							break;
						case 6:
							hqlString=SqlConfig.SQL("ats.table10.update.field94");
							break;
						case 7:
							hqlString=SqlConfig.SQL("ats.table10.update.field95");
							break;
						case 8:
							hqlString=SqlConfig.SQL("ats.table10.update.field96");
							break;
						}
					}break;
					case 128:
					{
						switch (ap.getComand1()) {
						case 1:
							hqlString=SqlConfig.SQL("ats.table10.update.field97");
							break;
						case 2:
							hqlString=SqlConfig.SQL("ats.table10.update.field98");
							break;
						case 3:
							hqlString=SqlConfig.SQL("ats.table10.update.field99");
							break;
						case 4:
							hqlString=SqlConfig.SQL("ats.table10.update.field100");
							break;
						case 5:
							hqlString=SqlConfig.SQL("ats.table10.update.field101");
							break;
						case 6:
							hqlString=SqlConfig.SQL("ats.table10.update.field102");
							break;
						case 7:
							hqlString=SqlConfig.SQL("ats.table10.update.field103");
							break;
						case 8:
							hqlString=SqlConfig.SQL("ats.table10.update.field104");
							break;
						}
					}break;
					}
				
				if(hqlString!=null&&!hqlString.equals("")){
					StringBuffer sb=new StringBuffer();
					sb.append(ap.getComand2()).append(",").append(ap.getComand3()).append(",").append(ap.getComand4())
					.append(",").append(ap.getComand6()).append(",").append((ap.getComand4()==0?1:0));
					count=getBaseDao().updateByHql(hqlString, new Object[]{sb.toString(),ap.getOneP(),ap.getTwoP()});
				}
					
				if(count>0){
					ActionContext.setState(key, true, "操作成功！");
					}else{
						ActionContext.setState(key, false, "操作失败！");
					}
			}else	ActionContext.setState(key,false,MsgConfig.msg("ats-1025"));	 
		} catch (Exception e) {
			ActionContext.setState(key,false,MsgConfig.msg("ats-1025"));
			logger.error(e.getMessage(),e);
		}
	
	
		
	}
	
	
	/**** 11 上报用户密码信息 **/
	public void process_0x10_0x05() {
		boolean end = false;
		byte[] data = dp.getData();
		int key = 0;
		try {
			key = ReceiverContext.putReceiveSequnce(CommandTools.getSequnces(data));			 
				// 此数据包为二层设备上报信息
				Table1 t1 = new Table1();
				//一层设备地址
				t1.setField11(data[9] & 0xff);
				//一层设备类型
				t1.setField13(data[10] & 0xff);
				//设备用户（0x01--0x10)
				t1.setField12(data[14] & 0xff);
				//用户密码（6位）
				t1.setField4(CommandTools.byteToString(data, 15, 6));
				
				end=this.getT1dao().saveOrUpdate(t1);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} finally {
			// 无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);/*
			TransmitterContext.pushDatagram(ResponseCommandTools
					.responseCmdBase12((byte) 0x10, (byte) 0x05, data[4],
							data[5], data[6], data[7], data[9], data[10],
							(byte) (end ? 0x00 : 0xff)), false, key,dp.getAddress().toString(),dp.getPort());*/
		}

	}

	 
	/**** 
	 * 12 下传用户密码信息 ***
	 * 
	 * @return 处理成功与否
	 */
	public void process_0x10_0x06() {
		byte[] data = dp.getData();
		
		int key=CommandTools.formateSequnceToInt(data);
		/***
		 * 验证流水号是否存在 
		 */
		if(key<0||!TransmitterContext.checek_Sequnce(key))
			{return;}
		ActionParams ap=null;
		//取出缓存的数据
		ap= ActionContext.getValues(key);
		if(ap==null||!CommandTools.isValidDP(data, ap))return;
		
		try {
			//判断命令是否成功！
			if(CommandTools.isSuccess(data)){
				int count = this.getBaseDao().updateByHql(
						SqlConfig.SQL("ats.table1.update.0x10.0x06"),
						new Object[] {
								//AtsMD5util.getMD5_16(ap.getData0().toString()),
							ap.getData0().toString(),
								ap.getUserID() });
				if(count>0)ActionContext.setState(key, true, "操作成功！");
				else	ActionContext.setState(key,false,MsgConfig.msg("ats-1013"));
			}else	ActionContext.setState(key,false,MsgConfig.msg("ats-1013"));	 
		} catch (Exception e) {
			ActionContext.setState(key,false,MsgConfig.msg("ats-1013"));
			logger.error(e.getMessage(),e);
		}
	}

	/****
	 *  13 上报管理员密码信息 ****
	 * 
	 * @return 与否
	 */
	public void  process_0x10_0x07() {
		boolean end = false;
		byte[] data = dp.getData();
		int key = 0;
		try {
			key = ReceiverContext.putReceiveSequnce(CommandTools.getSequnces(data));			 
				// 此数据包为二层设备上报信息
				Table1 t1 = new Table1();
				//一层设备地址
				t1.setField11(data[9] & 0xff);
				//一层设备类型
				t1.setField13(data[10] & 0xff);
				//设备用户（管理员默认为0x00)
				t1.setField12(0x00& 0xff);
				//用户密码（8位）
				t1.setField4(CommandTools.byteToString(data, 14, 8));
				
				end=this.getT1dao().saveOrUpdate(t1);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} finally {
			// 无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);
			/*TransmitterContext.pushDatagram(ResponseCommandTools
					.responseCmdBase12((byte) 0x10, (byte) 0x05, data[4],
							data[5], data[6], data[7], data[9], data[10],
							(byte) (end ? 0x00 : 0xff)), false, key,dp.getAddress().toString(),dp.getPort());*/
		}

	}

	/***
	 * (未处理)
	 * 	下传管理员密码信息 命令响应的处理方法 
	 *  @return   成功与否
	 */
	public void  process_0x10_0x08() {
		byte[] data = dp.getData();
		
		int key=CommandTools.formateSequnceToInt(data);
		/***
		 * 验证流水号是否存在 
		 */
		if(key<0||!TransmitterContext.checek_Sequnce(key))
			{return;}
		ActionParams ap=null;
		//取出缓存的数据
		ap= ActionContext.getValues(key);
		if(ap==null||!CommandTools.isValidDP(data, ap))return;
		
		try {
			//判断命令是否成功！
			if(CommandTools.isSuccess(data)){
				int count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table1.update.0x10.0x06"), new Object[]{ap.getData0().toString(),ap.getUserID()});
				if(count>0)ActionContext.setState(key, true, "操作成功！");
				else	ActionContext.setState(key,false,MsgConfig.msg("ats-1013"));
			}else	ActionContext.setState(key,false,MsgConfig.msg("ats-1013"));	 
		} catch (Exception e) {
			ActionContext.setState(key,false,MsgConfig.msg("ats-1013"));
			logger.error(e.getMessage(),e);
		}
	}

	/**** 
	 * 15 上报输入属性信息 *****
	 * 
	 * @return 成功与否
	 * 
	 */
	public void process_0x10_0x09() {
		boolean end=false;
		byte[] data = dp.getData();
		int key=0;
		try {
			 int  one,two,input_port,input_p;
			 
			 /*** 一层设备网络地址 ***/
			 one=data[9]&0xff;
			 // 二层设备地址
			 two=data[12]&0xff;		 
			 
			 /**输入端口**/
			 input_port=data[14]&0xff;
			 /**输入属性**/
			 input_p=data[15]&0xff;//屏蔽输入 [0x00] 普通输入 [0x01] ; 立即防区[0x02]; 小时防区[0x03];火警[0x05]
			 key= ReceiverContext.putReceiveSequnce(CommandTools.getSequnces(data));
			 Table10 equipment=AtsEquipmentCache.getEquiReadOnly(one,two);

				String queryString=null;
				
				int count=0;
				switch (input_port) {
				case 1:
					if(equipment!=null){
						equipment.setField17(input_p);
					}
					queryString=SqlConfig.SQL("ats.table10.update.0x10.0x0a-1");
					break;
				case 2:
					queryString=SqlConfig.SQL("ats.table10.update.0x10.0x0a-2");
					if(equipment!=null){
						equipment.setField18(input_p);
					}
					break;
				case 4:
					queryString=SqlConfig.SQL("ats.table10.update.0x10.0x0a-4");
					if(equipment!=null){
						equipment.setField19(input_p);
					}
					break;
				case 8:
					queryString=SqlConfig.SQL("ats.table10.update.0x10.0x0a-8");
					if(equipment!=null){
						equipment.setField20(input_p);
					}
					break;
				case 16:
					queryString=SqlConfig.SQL("ats.table10.update.0x10.0x0a-16");
					if(equipment!=null){
						equipment.setField21(input_p);
					}
					break;
				case 32:
					queryString=SqlConfig.SQL("ats.table10.update.0x10.0x0a-32");
					if(equipment!=null){
						equipment.setField22(input_p);
					}
					break;
				case 64:
					queryString=SqlConfig.SQL("ats.table10.update.0x10.0x0a-64");
					if(equipment!=null){
						equipment.setField23(input_p);
					}
					break;
				case 128:
					queryString=SqlConfig.SQL("ats.table10.update.0x10.0x0a-128");
					if(equipment!=null){
						equipment.setField24(input_p);
					}
					break;
				default:
					throw new AtsException("ats-1010");
				}
				this.getBaseDao().updateByHql(queryString, new Object[]{input_p,one,two});
				if(count>0)end=true;
		 
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			//无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp); 
			/*TransmitterContext.pushDatagram(ResponseCommandTools.responseCmdBase12((byte)0x10,(byte)0x09,data[4], data[5], data[6], data[7],
					data[9], data[10], (byte)(end?0x00:0xff)),false,key,dp.getAddress().toString(),dp.getPort());*/ 			
		}
	}

	
	
	 
	/****
	 * 下传输入属性信息 命令发送成功后的处理方法 
	 * 
	 * 
	 */
	public void process_0x10_0x0a() {
		byte[] data = dp.getData();
		
		int key=CommandTools.formateSequnceToInt(data);
		/***
		 * 验证流水号是否存在 
		 */
		if(key<0||!TransmitterContext.checek_Sequnce(key))
			{return;}
		ActionParams ap=null;
		//取出缓存的数据
		ap= ActionContext.getValues(key);
		if(ap==null||!CommandTools.isValidDP(data, ap))return;
		
		try {
			//判断命令是否成功！
			if(CommandTools.isSuccess(data)){
				String queryString=null;
				Table10 equipment=AtsEquipmentCache.getEquiReadOnly(ap.getOneP(),ap.getTwoP());
				int input_p=ap.getComand1();
				int count=0;
				switch (ap.getComand0()) {
				case 1:
					if(equipment!=null){
						equipment.setField17(input_p);
					}
					queryString=SqlConfig.SQL("ats.table10.update.0x10.0x0a-1");
					break;
				case 2:
					queryString=SqlConfig.SQL("ats.table10.update.0x10.0x0a-2");
					if(equipment!=null){
						equipment.setField18(input_p);
					}
					break;
				case 4:
					queryString=SqlConfig.SQL("ats.table10.update.0x10.0x0a-4");
					if(equipment!=null){
						equipment.setField19(input_p);
					}
					break;
				case 8:
					queryString=SqlConfig.SQL("ats.table10.update.0x10.0x0a-8");
					if(equipment!=null){
						equipment.setField20(input_p);
					}
					break;
				case 16:
					queryString=SqlConfig.SQL("ats.table10.update.0x10.0x0a-16");
					if(equipment!=null){
						equipment.setField21(input_p);
					}
					break;
				case 32:
					queryString=SqlConfig.SQL("ats.table10.update.0x10.0x0a-32");
					if(equipment!=null){
						equipment.setField22(input_p);
					}
					break;
				case 64:
					queryString=SqlConfig.SQL("ats.table10.update.0x10.0x0a-64");
					if(equipment!=null){
						equipment.setField23(input_p);
					}
					break;
				case 128:
					queryString=SqlConfig.SQL("ats.table10.update.0x10.0x0a-128");
					if(equipment!=null){
						equipment.setField24(input_p);
					}
					break;
				default:
					throw new AtsException("ats-1010");
				}
				this.getBaseDao().updateByHql(queryString, new Object[]{ap.getComand1(),ap.getOneP(),ap.getTwoP()});
				if(count>0)ActionContext.setState(key, true, "操作成功！");
				else	ActionContext.setState(key,false,MsgConfig.msg("ats-1024"));
			}else	ActionContext.setState(key,false,MsgConfig.msg("ats-1024"));	 
		} catch (Exception e) {
			ActionContext.setState(key,false,MsgConfig.msg("ats-1024"));
			logger.error(e.getMessage(),e);
		}
	
	}
	
	
	/****
	 *  17 上报用户电话号码信息
	 * 
	 *  
	 *  **/
	public void process_0x10_0x0b() {
		boolean end=false;
		byte[] data = dp.getData();
		int key=0;
		try {
			 int  one,uid;
			 /*** 一层设备网络地址 ***/
			 one=data[9]&0xff; 
			 /** 用户**/
			 uid=data[14]&0xff;
			 /**电话号码，15-31  16个长度**/
			 String telNum=CommandTools.byteToString(data, 15, 16);
			 key= ReceiverContext.putReceiveSequnce(CommandTools.getSequnces(data));
			 int count=this.getT10DAO().updateColumn(SqlConfig.SQL("ats.table1.update.0x10.0x0b"), new Object[]{telNum,one,uid});
			 if(count>0)end=true;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			//无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);
			 /*TransmitterContext.pushDatagram(ResponseCommandTools.responseCmdBase12((byte)0x10,(byte)0x0b,data[4], data[5], data[6], data[7],
					data[9], data[10], (byte)(end?0x00:0xff)),false,key,dp.getAddress().toString(),dp.getPort()); 				*/
		}
	}
	
 

	/**** 
	 *  
	 * 18 下传用户电话号码信息    命令响应后的处理办法 **/
	public void process_0x10_0x0c() {

		byte[] data = dp.getData();
		
		int key=CommandTools.formateSequnceToInt(data);
		/***
		 * 验证流水号是否存在 
		 */
		if(key<0||!TransmitterContext.checek_Sequnce(key))
			{return;}
		ActionParams ap=null;
		//取出缓存的数据
		ap= ActionContext.getValues(key);
		if(ap==null||!CommandTools.isValidDP(data, ap))return;
		
		try {
			//判断命令是否成功！
			if(CommandTools.isSuccess(data)){
				int count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table1.update.0x10.0x0c"), new Object[]{ap.getData0().toString(),ap.getUserID()});
				if(count>0)ActionContext.setState(key, true, "操作成功！");
				else	ActionContext.setState(key,false,MsgConfig.msg("ats-1015"));
			}else	ActionContext.setState(key,false,MsgConfig.msg("ats-1015"));	 
		} catch (Exception e) {
			ActionContext.setState(key,false,MsgConfig.msg("ats-1015"));
			logger.error(e.getMessage(),e);
		}
	
		
		
		
	}
	
	
	/**** 19 上报用户短信号码信息 **/
	public void process_0x10_0x0d() {
		boolean end=false;
		byte[] data = dp.getData();
		int key=0;
		try {
			 int  one,uid;
			 /*** 一层设备网络地址 ***/
			 one=data[9]&0xff; 
			 /** 用户**/
			 uid=data[14]&0xff;
			 /**短信号码，15-31  16个长度**/
			 String telNum=CommandTools.byteToString(data, 15, 16);
			 key= ReceiverContext.putReceiveSequnce(CommandTools.getSequnces(data));
			 int count=this.getT10DAO().updateColumn(SqlConfig.SQL("ats.table1.update.0x10.0x0d"), new Object[]{telNum,one,uid});
			 if(count>0)end=true;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			//无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);
			 /*TransmitterContext.pushDatagram(ResponseCommandTools.responseCmdBase12((byte)0x10,(byte)0x0d,data[4], data[5], data[6], data[7],
					data[9], data[10], (byte)(end?0x00:0xff)),false,key,dp.getAddress().toString(),dp.getPort());*/ 				
		}
	}

	/**** 20 下传用户短信号码信息   命令响应后的处理方法  **/
	public void process_0x10_0x0e() {

		byte[] data = dp.getData();
		
		int key=CommandTools.formateSequnceToInt(data);
		/***
		 * 验证流水号是否存在 
		 */
		if(key<0||!TransmitterContext.checek_Sequnce(key))
			{return;}
		ActionParams ap=null;
		//取出缓存的数据
		ap= ActionContext.getValues(key);
		if(ap==null||!CommandTools.isValidDP(data, ap))return;
		
		try {
			//判断命令是否成功！
			if(CommandTools.isSuccess(data)){
				int count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table1.update.0x10.0x0e"), new Object[]{ap.getData0().toString(),ap.getUserID()});
				if(count>0)ActionContext.setState(key, true, "操作成功！");
				else	ActionContext.setState(key,false,MsgConfig.msg("ats-1017"));
			}else	ActionContext.setState(key,false,MsgConfig.msg("ats-1017"));	 
		} catch (Exception e) {
			ActionContext.setState(key,false,MsgConfig.msg("ats-1017"));
			logger.error(e.getMessage(),e);
		}
	
		
		
		
	}
	
	

	/**** 21 上报开启/屏蔽短信端口信息 
	 * 
	 * @return 成功与否
	 */
	public void process_0x10_0x0f() {
		boolean end=false;
		byte[] data = dp.getData();
		int key=0;
		try {
			 int  one,two,disableMessage;
			 /*** 一层设备网络地址 ***/
			 one=data[9]&0xff; 
			 /*** 二层设备网络地址   （默认为0x00)***/
			 two=0; 
			 /** 短信端口开关  {0x80代表开启，0xff代表关闭。}**/
			 disableMessage=data[14]&0xff;
			 key= ReceiverContext.putReceiveSequnce(CommandTools.getSequnces(data));
			 int count=this.getT10DAO().updateColumn(SqlConfig.SQL("ats.table10.update.0x10.0x0f"), new Object[]{disableMessage,one,two});
			 if(count>0)end=true;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			//无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);
			/* TransmitterContext.pushDatagram(ResponseCommandTools.responseCmdBase12((byte)0x10,(byte)0x0f,data[4], data[5], data[6], data[7],
					data[9], data[10], (byte)(end?0x00:0xff)),false,key,dp.getAddress().toString(),dp.getPort());*/ 					
		}
	}


 
	/****
	 * 22 下传开启/屏蔽短信端口信息 的命令响应 处理方法 
	 * 无论开启或是屏蔽，都是在操作一层设备 ，所以 只需要获取一层设备就好，同时，将二层设备默认为0 
	 */
	public void process_0x10_0x10() {
		byte[] data = dp.getData();
		
		int key=CommandTools.formateSequnceToInt(data);
		/***
		 * 验证流水号是否存在 
		 */
		if(key<0||!TransmitterContext.checek_Sequnce(key))
			{return;}
		ActionParams ap=null;
		//取出缓存的数据
		ap= ActionContext.getValues(key);
		if(ap==null||!CommandTools.isValidDP(data, ap))return;
		
		try {
			//判断命令是否成功！
			if(CommandTools.isSuccess(data)){
				int count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x0f"), new Object[]{ap.getComand0(),ap.getOneP(),0});
				if(count>0)ActionContext.setState(key, true, "操作成功！");
				else	ActionContext.setState(key,false,MsgConfig.msg("ats-1018"));
			}else	ActionContext.setState(key,false,MsgConfig.msg("ats-1018"));	 
		} catch (Exception e) {
			ActionContext.setState(key,false,MsgConfig.msg("ats-1018"));
			logger.error(e.getMessage(),e);
		}
	
	}

	/**** 23 上报开启/屏蔽电话端口信息 **/
	public void process_0x10_0x11() {
		boolean end=false;
		byte[] data = dp.getData();
		int key=0;
		try {
			 int  one,two,disableTel;
			 /*** 一层设备网络地址 ***/
			 one=data[9]&0xff; 
			 /*** 二层设备网络地址   （默认为0x00)***/
			 two=0; 
			 /** 电话端口开关  {0x80代表开启，0xff代表关闭。}**/
			 disableTel=data[14]&0xff;
			 key= ReceiverContext.putReceiveSequnce(CommandTools.getSequnces(data));
			 int count=this.getT10DAO().updateColumn(SqlConfig.SQL("ats.table10.update.0x10.0x11"), new Object[]{disableTel,one,two});
			 if(count>0)end=true;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			//无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);
			 /*TransmitterContext.pushDatagram(ResponseCommandTools.responseCmdBase12((byte)0x10,(byte)0x11,data[4], data[5], data[6], data[7],
					data[9], data[10], (byte)(end?0x00:0xff)),false,key,dp.getAddress().toString(),dp.getPort());*/ 					
		}
	}
 
	/**** 24  下传开启/屏蔽电话端口信息 *  的命令响应 处理方法 
	 * 
	 */
	public void process_0x10_0x12() {
		byte[] data = dp.getData();
		
		int key=CommandTools.formateSequnceToInt(data);
		/***
		 * 验证流水号是否存在 
		 */
		if(key<0||!TransmitterContext.checek_Sequnce(key))
			{return;}
		ActionParams ap=null;
		//取出缓存的数据
		ap= ActionContext.getValues(key);
		if(ap==null||!CommandTools.isValidDP(data, ap))return;
		
		try {
			//判断命令是否成功！
			if(CommandTools.isSuccess(data)){
				int count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x11"), new Object[]{ap.getComand0(),ap.getOneP(),0});
				if(count>0)ActionContext.setState(key, true, "操作成功！");
				else	ActionContext.setState(key,false,MsgConfig.msg("ats-1016"));
			}else	ActionContext.setState(key,false,MsgConfig.msg("ats-1016"));	 
		} catch (Exception e) {
			ActionContext.setState(key,false,MsgConfig.msg("ats-1016"));
			logger.error(e.getMessage(),e);
		}
	
	}
/**** 25 上报打印机设置信息 ****
	 * 
	 * @return
	 */
	public boolean process_0x10_0x13() {
		boolean end=false;
		byte[] data = dp.getData();
		int key=0;
		try {
			 int  one,cmd1,cmd2,cmd3,cmd4;
			 /*** 一层设备网络地址 ***/
			 one=data[9]&0xff;
			 cmd1=data[14]&0xff;//打印机设置命令1	0x01代表禁止打印机，0x02代表使用打印机	
			 cmd2=data[15]&0xff;//打印机设置命令2	0x01代表禁止打印报警事件，0x02代表打印报警事件	
			 cmd3=data[16]&0xff;//打印机设置命令3	0x01代表禁止打印普通输入事件，0x02代表打印普通输入事件	
			 cmd4=data[17]&0xff;//打印机设置命令4	0x01代表禁止打印用户操作事件，0x02代表打印用户操作事件	
			 cmd1=(cmd1==1?1:0);
			 cmd2=(cmd2==2?1:0);
			 cmd3=(cmd3==2?1:0);
			 cmd4=(cmd4==2?1:0);
			 
			 key= ReceiverContext.putReceiveSequnce(CommandTools.getSequnces(data));
			 StringBuffer sb=new StringBuffer();
			 sb.append(cmd1).append(",").append(cmd2).append(",").append(cmd3).append(",").append(cmd4);
			 int count=this.getT10DAO().updateColumn(SqlConfig.SQL("ats.table10.update.0x10.0x13"),
						new Object[]{sb,one,0});
				 if(count>0)end=true;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			//无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);
			/* TransmitterContext.pushDatagram(ResponseCommandTools.responseCmdBase12((byte)0x10,(byte)0x13,data[4], data[5], data[6], data[7],
					data[9], data[10], (byte)(end?0x00:0xff)),false,key,dp.getAddress().toString(),dp.getPort());*/ 			
		}
		return end;
	}
	 
	/**** 26 下传打印机设置信息   命令响应处理办法**/
	public void process_0x10_0x14() {
		byte[] data = dp.getData();
		
		int key=CommandTools.formateSequnceToInt(data);
		/***
		 * 验证流水号是否存在 
		 */
		if(key<0||!TransmitterContext.checek_Sequnce(key))
			{return;}
		ActionParams ap=null;
		//取出缓存的数据
		ap= ActionContext.getValues(key);
		if(ap==null||!CommandTools.isValidDP(data, ap))return;		
		try {
			//判断命令是否成功！
			if(CommandTools.isSuccess(data)){
				 StringBuffer sb=new StringBuffer();
				 sb.append(ap.getComand0()).append(",").append(ap.getComand1()).append(",").append(ap.getComand2()).append(",").append(ap.getComand3());
				int count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x13"), new Object[]{sb.toString(),ap.getOneP(),0});
				if(count>0)ActionContext.setState(key, true, "操作成功！");
				else	ActionContext.setState(key,false,MsgConfig.msg("ats-1016"));
			}else	ActionContext.setState(key,false,MsgConfig.msg("ats-1016"));	 
		} catch (Exception e) {
			ActionContext.setState(key,false,MsgConfig.msg("ats-1016"));
			logger.error(e.getMessage(),e);
		}
	
	}

	/**** 27 上报接警中心1号码信息 **/
	public boolean process_0x10_0x15() {
		boolean end=false;
		byte[] data = dp.getData();
		int key=0;
		try {
			 int  one;
			 /*** 一层设备网络地址 ***/
			 one=data[9]&0xff; 
			 /**电话号码，14-29  16个长度**/
			 String telNum=CommandTools.byteToString(data, 14, 16);
			 
			 key= ReceiverContext.putReceiveSequnce(CommandTools.getSequnces(data));
			//判断是否包含二层设备信息
			if (CommandTools.hasSecondDevice(data)) {
			} else {
				int count=this.getT10DAO().updateColumn(SqlConfig.SQL("ats.table10.update.0x10.0x15"),new Object[]{telNum,one,0});
				 if(count>0)end=true;
			}  
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			//无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);
			/*TransmitterContext.pushDatagram(ResponseCommandTools.responseCmdBase12((byte)0x10,(byte)0x15,data[4], data[5], data[6], data[7],
					data[9], data[10], (byte)(end?0x00:0xff)),false,key,dp.getAddress().toString(),dp.getPort());*/			
		}
		return end;
	}

	/**** 28 下传接警中心1号码信息**  命令响应处理办法**/
	public void process_0x10_0x16() {

		byte[] data = dp.getData();
		
		int key=CommandTools.formateSequnceToInt(data);
		/***
		 * 验证流水号是否存在 
		 */
		if(key<0||!TransmitterContext.checek_Sequnce(key))
			{return;}
		ActionParams ap=null;
		//取出缓存的数据
		ap= ActionContext.getValues(key);
		if(ap==null||!CommandTools.isValidDP(data, ap))return;
		
		try {
			//判断命令是否成功！
			if(CommandTools.isSuccess(data)){
				int count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x15"), new Object[]{ap.getData0().toString(),ap.getOneP(),0});
				if(count>0)ActionContext.setState(key, true, "操作成功！");
				else	ActionContext.setState(key,false,MsgConfig.msg("ats-1027"));
			}else	ActionContext.setState(key,false,MsgConfig.msg("ats-1027"));	 
		} catch (Exception e) {
			ActionContext.setState(key,false,MsgConfig.msg("ats-1027"));
			logger.error(e.getMessage(),e);
		}	
	}
	
	
	

	/****
	 * 
	 *  29 上报接警中心2号码信息
	 * 
	 *  **/
	public boolean process_0x10_0x17() {
		boolean end=false;
		byte[] data = dp.getData();
		int key=0;
		try {
			 int  one;
			 /*** 一层设备网络地址 ***/
			 one=data[9]&0xff; 
			 /**电话号码，14-29  16个长度**/
			 String telNum=CommandTools.byteToString(data, 14, 16);
			 
			 key= ReceiverContext.putReceiveSequnce(CommandTools.getSequnces(data));
			//判断是否包含二层设备信息
			if (CommandTools.hasSecondDevice(data)) {
			} else {
				int count=this.getT10DAO().updateColumn(SqlConfig.SQL("ats.table10.update.0x10.0x17"),new Object[]{telNum,one,0});
				 if(count>0)end=true;
			}  
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			//无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);
			/*TransmitterContext.pushDatagram(ResponseCommandTools.responseCmdBase12((byte)0x10,(byte)0x17,data[4], data[5], data[6], data[7],
					data[9], data[10], (byte)(end?0x00:0xff)),false,key,dp.getAddress().toString(),dp.getPort());*/			
		}
		return end;
	}

	
	/**** 30 下传接警中心2号码信息   命令响应处理办法**/
	public void process_0x10_0x18() {

		byte[] data = dp.getData();
		
		int key=CommandTools.formateSequnceToInt(data);
		/***
		 * 验证流水号是否存在 
		 */
		if(key<0||!TransmitterContext.checek_Sequnce(key))
			{return;}
		ActionParams ap=null;
		//取出缓存的数据
		ap= ActionContext.getValues(key);
		if(ap==null||!CommandTools.isValidDP(data, ap))return;
		
		try {
			//判断命令是否成功！
			if(CommandTools.isSuccess(data)){
				int count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x17"), new Object[]{ap.getData0().toString(),ap.getOneP(),0});
				if(count>0)ActionContext.setState(key, true, "操作成功！");
				else	ActionContext.setState(key,false,MsgConfig.msg("ats-1028"));
			}else	ActionContext.setState(key,false,MsgConfig.msg("ats-1028"));	 
		} catch (Exception e) {
			ActionContext.setState(key,false,MsgConfig.msg("ats-1028"));
			logger.error(e.getMessage(),e);
		}	
	}
	
	
	

	/**** 31 上报开启/屏蔽用户信息 **/
	public boolean process_0x10_0x19() {
		boolean end=false;
		byte[] data = dp.getData();
		int key=0;
		try {
			 int  one,uid,sw;
			 
			 /*** 一层设备网络地址 ***/
			 one=data[9]&0xff;
			 /** 用户**/
			 uid=data[14]&0xff;
			 /**开启/屏蔽用户命令  {0x01代表开启。0x02代表屏蔽	。}**/
			 sw=data[15]&0xff;
			 
			 key= ReceiverContext.putReceiveSequnce(CommandTools.getSequnces(data));
			 int count=this.getT10DAO().updateColumn(SqlConfig.SQL("ats.table1.update.0x10.0x19"),
					new Object[]{sw,one,uid});
			 if(count>0)end=true;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			//无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);
			/*TransmitterContext.pushDatagram(ResponseCommandTools.responseCmdBase12((byte)0x10,(byte)0x19,data[4], data[5], data[6], data[7],
					data[9], data[10], (byte)(end?0x00:0xff)),false,key,dp.getAddress().toString(),dp.getPort());*/	
		}
		return end;
	
	}
 	/**** 32 下传开启/屏蔽用户信息  命令响应处理办法**/
	public void process_0x10_0x1a() {
		byte[] data = dp.getData();
		
		int key=CommandTools.formateSequnceToInt(data);
		/***
		 * 验证流水号是否存在 
		 */
		if(key<0||!TransmitterContext.checek_Sequnce(key))
			{return;}
		ActionParams ap=null;
		//取出缓存的数据
		ap= ActionContext.getValues(key);
		if(ap==null||!CommandTools.isValidDP(data, ap))return;
		
		try {
			//判断命令是否成功！
			if(CommandTools.isSuccess(data)){
				int count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table1.update.0x10.0x1a"), new Object[]{ap.getComand0(),ap.getUserID()});
				if(count>0)ActionContext.setState(key, true, "操作成功！");
				else	ActionContext.setState(key,false,MsgConfig.msg("ats-1014"));
			}else	ActionContext.setState(key,false,MsgConfig.msg("ats-1014"));	 
		} catch (Exception e) {
			ActionContext.setState(key,false,MsgConfig.msg("ats-1014"));
			logger.error(e.getMessage(),e);
		}
	
	}

	
	
	
	/**** 
	 * 33 下传用户名信息    ---响应处理**
	 * @return
	 */

	public void process_0x10_0x1b() {
		byte[] data = dp.getData();
		
		int key=CommandTools.formateSequnceToInt(data);
		/***
		 * 验证流水号是否存在 
		 */
		if(key<0||!TransmitterContext.checek_Sequnce(key))
			{return;}
		ActionParams ap=null;
		//取出缓存的数据
		ap= ActionContext.getValues(key);
		if(ap==null||!CommandTools.isValidDP(data, ap))return;
		
		try {
			//判断命令是否成功！
			if(CommandTools.isSuccess(data)){
				int count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table1.update.0x10.0x1b"), new Object[]{ap.getData0(),ap.getUserID()});
				if(count>0)ActionContext.setState(key, true, "操作成功！");
				else ActionContext.setState(key,false,MsgConfig.msg("ats-1022"));
			}else	ActionContext.setState(key,false,MsgConfig.msg("ats-1022"));	 
		} catch (Exception e) {
			ActionContext.setState(key,false,MsgConfig.msg("ats-1022"));
			logger.error(e.getMessage(),e);
		}
	}
	 
	/**** 34 下传设备名信息 ** 命令响应处理办法**/
	public void process_0x10_0x1c() {
		byte[] data = dp.getData();
		
		int key=CommandTools.formateSequnceToInt(data);
		/***
		 * 验证流水号是否存在 
		 */
		if(key<0||!TransmitterContext.checek_Sequnce(key))
			{return;}
		ActionParams ap=null;
		//取出缓存的数据
		ap= ActionContext.getValues(key);
		if(ap==null||!CommandTools.isValidDP(data, ap))return;
		
		try {
			//判断命令是否成功！
			if(CommandTools.isSuccess(data)){
				int count=0;
				/*if(ap.getComand1()==1)
				count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table9.update.0x10.0x1c"), new Object[]{ap.getData0(),ap.getOneP()});
				else if (ap.getComand1()==2)*/
					count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x1c"), new Object[]{ap.getData0(),ap.getOneP(),ap.getTwoP()});	
				if(count>0){
					ActionContext.setState(key, true, "操作成功！");
					Table10 temp10=AtsEquipmentCache.getEquiReadOnly(ap.getOneP(), ap.getTwoP());
					if(temp10!=null)
					temp10.setField121(ap.getData0().toString());
					
					EquipmentCacheVO tempNode=AtsEquipmentCache.getTreeNode(ap.getOneP(), ap.getTwoP());
					if(tempNode!=null)
						tempNode.setText(ap.getData0().toString());
				}
				else	ActionContext.setState(key,false,MsgConfig.msg("ats-1012"));
			}else	ActionContext.setState(key,false,MsgConfig.msg("ats-1012"));	 
		} catch (Exception e) {
			ActionContext.setState(key,false,MsgConfig.msg("ats-1012"));
			logger.error(e.getMessage(),e);
		}
	}

	
	
	
	
	
	/**** 35 上报开启/屏蔽设备信息 **/
	public boolean process_0x10_0x1d() {
		boolean end=false;
		byte[] data = dp.getData();
		int key=0;
		try {
			 int  one,two,sw;
			 
			 /*** 一层设备网络地址 ***/
			 one=data[9]&0xff;
			 two=data[12]&0xff;
			 /** 开启/屏蔽设备命令	0x01为开启，0x02为屏蔽	**/
			 sw=data[14]&0xff;
			 key= ReceiverContext.putReceiveSequnce(CommandTools.getSequnces(data));
			int count=this.getT10DAO().updateColumn(SqlConfig.SQL("ats.table10.update.0x10.0x1e"),new Object[]{sw,one,two});
			Table10 t10=AtsEquipmentCache.getEquiReadOnly(one, two);
			if(t10!=null){
				if (sw == 1) {
					t10.setField130(Constant.EQUIPMENT_STATUS_NORMAL_VAL);
				} else {
					t10.setField130(Constant.EQUIPMENT_STATUS_DISABLE_VAL);
				}
			}
			EquipmentCacheVO vo=AtsEquipmentCache.readEqui(one, two);
			if(vo!=null){
				if (sw == 1) {
					vo.setStatus(Constant.EQUIPMENT_STATUS_NORMAL);
				} else {
					vo.setStatus(Constant.EQUIPMENT_STATUS_DISABLE);
				}
			}
			 if(count>0)end=true;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			//无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);
			/*TransmitterContext.pushDatagram(ResponseCommandTools.responseCmdBase12((byte)0x10,(byte)0x1d,data[4], data[5], data[6], data[7],
					data[9], data[10], (byte)(end?0x00:0xff)),false,key,dp.getAddress().toString(),dp.getPort());*/	
		}
		return end;
	
		
	}

	
 
	/**** 36 下传开启/屏蔽设备信息   命令响应处理办法**/
	public void process_0x10_0x1e() {
		byte[] data = dp.getData();
		
		int key=CommandTools.formateSequnceToInt(data);
		/***
		 * 验证流水号是否存在 
		 */
		if(key<0||!TransmitterContext.checek_Sequnce(key))
			{return;}
		ActionParams ap=null;
		//取出缓存的数据
		ap= ActionContext.getValues(key);
		if(ap==null||!CommandTools.isValidDP(data, ap))return;
		
		try {
			//判断命令是否成功！
			if(CommandTools.isSuccess(data)){
				int count=0;
				/*if(ap.getComand1()==1)
				count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table9.update.0x10.0x1e"), new Object[]{ap.getComand0(),ap.getOneP()});
				else if (ap.getComand1()==2)*/
					count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x1e"), new Object[]{ap.getComand0(),ap.getOneP(),ap.getTwoP()});	
				if(count>0){
					ActionContext.setState(key, true, "操作成功！");
					Table10 temp10=AtsEquipmentCache.getEquiReadOnly(ap.getOneP(), ap.getTwoP());
				if(temp10!=null)
				{	if(ap.getComand0()==2)
					{
						temp10.setField130(Constant.EQUIPMENT_STATUS_DISABLE_VAL);
					}
					else{
						temp10.setField130(Constant.EQUIPMENT_STATUS_NORMAL_VAL);
					}
				
				}
				
				EquipmentCacheVO tempNode=AtsEquipmentCache.getTreeNode(ap.getOneP(), ap.getTwoP());
				if(tempNode!=null)
					tempNode.setStatus(ap.getComand0()==2?Constant.EQUIPMENT_STATUS_DISABLE:Constant.EQUIPMENT_STATUS_NORMAL);
				}
				else	ActionContext.setState(key,false,MsgConfig.msg("ats-1023"));
			}else	ActionContext.setState(key,false,MsgConfig.msg("ats-1023"));	 
		} catch (Exception e) {
			ActionContext.setState(key,false,MsgConfig.msg("ats-1023"));
			logger.error(e.getMessage(),e);
		}
	}

	
	
	 
	
	
	/**** 37 下传输入所对应2级用户信息   命令响应处理办法**/
	public void process_0x10_0x1f() {
		byte[] data = dp.getData();
		
		int key=CommandTools.formateSequnceToInt(data);
		/***
		 * 验证流水号是否存在 
		 */
		if(key<0||!TransmitterContext.checek_Sequnce(key))
			{return;}
		ActionParams ap=null;
		//取出缓存的数据
		ap= ActionContext.getValues(key);
		if(ap==null||!CommandTools.isValidDP(data, ap))return;
		
		try {
			//判断命令是否成功！
			if(CommandTools.isSuccess(data)){
				int count=0;
				if(ap.getComand5()==1)//一层设备
				switch (ap.getComand0()) {
				case 1:
					 	count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table9.update.0x10.0x1f.field23"), new Object[]{ap.getUserCode(),ap.getOneP()});	
				break;
				case 2:
				 	count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table9.update.0x10.0x1f.field24"), new Object[]{ap.getUserCode(),ap.getOneP()});	
				 	break;
				case 4:
				 	count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table9.update.0x10.0x1f.field25"), new Object[]{ap.getUserCode(),ap.getOneP()});	
				 	break;
				case 8:
				 	count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table9.update.0x10.0x1f.field26"), new Object[]{ap.getUserCode(),ap.getOneP()});	
				 	break;
				case 16:
				 	count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table9.update.0x10.0x1f.field27"), new Object[]{ap.getUserCode(),ap.getOneP()});	
				 	break;
				case 32:
				 	count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table9.update.0x10.0x1f.field28"), new Object[]{ap.getUserCode(),ap.getOneP()});	
				 	break;
				case 64:
				 	count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table9.update.0x10.0x1f.field29"), new Object[]{ap.getUserCode(),ap.getOneP()});	
				 	break;
				case 128:
				 	count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table9.update.0x10.0x1f.field30"), new Object[]{ap.getUserCode(),ap.getOneP()});	
				 	break;
				default:
					break;
				}	
				
				else if(ap.getComand5()==2)//二层设备 
					switch (ap.getComand0()) {
					case 1:
						 	count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x1f.field25"), new Object[]{ap.getUserCode(),ap.getOneP(),ap.getTwoP()});	
					break;
					case 2:
					 	count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x1f.field26"), new Object[]{ap.getUserCode(),ap.getOneP(),ap.getTwoP()});	
					 	break;
					case 4:
					 	count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x1f.field27"), new Object[]{ap.getUserCode(),ap.getOneP(),ap.getTwoP()});	
					 	break;
					case 8:
					 	count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x1f.field28"), new Object[]{ap.getUserCode(),ap.getOneP(),ap.getTwoP()});	
					 	break;
					case 16:
					 	count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x1f.field29"), new Object[]{ap.getUserCode(),ap.getOneP(),ap.getTwoP()});	
					 	break;
					case 32:
					 	count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x1f.field30"), new Object[]{ap.getUserCode(),ap.getOneP(),ap.getTwoP()});	
					 	break;
					case 64:
					 	count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x1f.field31"), new Object[]{ap.getUserCode(),ap.getOneP(),ap.getTwoP()});	
					 	break;
					case 128:
					 	count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x1f.field32"), new Object[]{ap.getUserCode(),ap.getOneP(),ap.getTwoP()});	
					 	break;
					default:
						break;
					}
					
				if(count>0){
					ActionContext.setState(key, true, "操作成功！");
					}else{
						ActionContext.setState(key, false, "操作失败！");
					}
			}else	ActionContext.setState(key,false,MsgConfig.msg("ats-1020"));	 
		} catch (Exception e) {
			ActionContext.setState(key,false,MsgConfig.msg("ats-1020"));
			logger.error(e.getMessage(),e);
		}
	
	}
	
	
	
/***
 * 修改输入端口的名称
 */
	public void process_0x10_0x20() {
		byte[] data = dp.getData();
		
		int key=CommandTools.formateSequnceToInt(data);
		if(key<0)return;
		ActionParams ap=null;
		//取出缓存的数据
		ap= ActionContext.getValues(key);
		if(ap==null||!CommandTools.isValidDP(data, ap))return;
		
		try {
			//判断命令是否成功！
			if(CommandTools.isSuccess(data)){
				int count=0;
				Table10 temp10=AtsEquipmentCache.getEquiReadOnly(ap.getOneP(), ap.getTwoP());
				if(temp10!=null){
					switch (ap.getComand0()) {
					case 1:
						count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x20-1"), new Object[]{ap.getData0(),ap.getOneP(),ap.getTwoP()});
						temp10.setField105(ap.getData0().toString());
					break;
					case 2:
					 	count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x20-2"), new Object[]{ap.getData0(),ap.getOneP(),ap.getTwoP()});
					 	temp10.setField106(ap.getData0().toString());
					 	break;
					case 4:
					 	count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x20-4"), new Object[]{ap.getData0(),ap.getOneP(),ap.getTwoP()});
					 	temp10.setField107(ap.getData0().toString());
					 	break;
					case 8:
					 	count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x20-8"), new Object[]{ap.getData0(),ap.getOneP(),ap.getTwoP()});
					 	temp10.setField108(ap.getData0().toString());
					 	break;
					case 16:
					 	count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x20-16"), new Object[]{ap.getData0(),ap.getOneP(),ap.getTwoP()});
					 	temp10.setField109(ap.getData0().toString());
					 	break;
					case 32:
					 	count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x20-32"), new Object[]{ap.getData0(),ap.getOneP(),ap.getTwoP()});
					 	temp10.setField110(ap.getData0().toString());
					 	break;
					case 64:
					 	count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x20-64"), new Object[]{ap.getData0(),ap.getOneP(),ap.getTwoP()});
					 	temp10.setField111(ap.getData0().toString());
					 	break;
					case 128:
					 	count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x20-128"), new Object[]{ap.getData0(),ap.getOneP(),ap.getTwoP()});
					 	temp10.setField112(ap.getData0().toString());
					 	break;
					default:
						break;
					}
					if(count>0){
						ActionContext.setState(key, true, "操作成功！");
						}else{
							ActionContext.setState(key, false, "操作失败！");
						}
				}else{
					ActionContext.setState(key, false, "设备丢失，操作失败！");
				}
			}else	ActionContext.setState(key,false,MsgConfig.msg("ats-1025"));	 
		} catch (Exception e) {
			ActionContext.setState(key,false,MsgConfig.msg("ats-1025"));
			logger.error(e.getMessage(),e);
		}
	
	
		
	}

 
	/***
	 * 修改输出端口的名称
	 */
	public void process_0x10_0x21() {
		byte[] data = dp.getData();
		
		int key=CommandTools.formateSequnceToInt(data);
		if(key<0)return;
		ActionParams ap=null;
		//取出缓存的数据
		ap= ActionContext.getValues(key);
		if(ap==null||!CommandTools.isValidDP(data, ap))return;
		
		try {
			//判断命令是否成功！
			if(CommandTools.isSuccess(data)){
				int count=0;
				Table10 temp10=AtsEquipmentCache.getEquiReadOnly(ap.getOneP(), ap.getTwoP());
				if(temp10!=null){
					switch (ap.getComand0()) {
					case 1:
						count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x21-1"), new Object[]{ap.getData0(),ap.getOneP(),ap.getTwoP()});
						temp10.setField113(ap.getData0().toString());
					break;
					case 2:
					 	count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x21-2"), new Object[]{ap.getData0(),ap.getOneP(),ap.getTwoP()});
					 	temp10.setField114(ap.getData0().toString());
					 	break;
					case 4:
					 	count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x21-4"), new Object[]{ap.getData0(),ap.getOneP(),ap.getTwoP()});
					 	temp10.setField115(ap.getData0().toString());
					 	break;
					case 8:
					 	count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x21-8"), new Object[]{ap.getData0(),ap.getOneP(),ap.getTwoP()});
					 	temp10.setField116(ap.getData0().toString());
					 	break;
					case 16:
					 	count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x21-16"), new Object[]{ap.getData0(),ap.getOneP(),ap.getTwoP()});
					 	temp10.setField117(ap.getData0().toString());
					 	break;
					case 32:
					 	count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x21-32"), new Object[]{ap.getData0(),ap.getOneP(),ap.getTwoP()});
					 	temp10.setField118(ap.getData0().toString());
					 	break;
					case 64:
					 	count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x21-64"), new Object[]{ap.getData0(),ap.getOneP(),ap.getTwoP()});
					 	temp10.setField119(ap.getData0().toString());
					 	break;
					case 128:
					 	count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x21-128"), new Object[]{ap.getData0(),ap.getOneP(),ap.getTwoP()});
					 	temp10.setField120(ap.getData0().toString());
					 	break;
					default:
						break;
					}
					
					if(count>0){
						ActionContext.setState(key, true, "操作成功！");
						}else{
							ActionContext.setState(key, false, "操作失败！");
						}
				}else{
					ActionContext.setState(key, false, "设备丢失，操作失败！");
				}
			}else	ActionContext.setState(key,false,MsgConfig.msg("ats-1026"));	 
		} catch (Exception e) {
			ActionContext.setState(key,false,MsgConfig.msg("ats-1026"));
			logger.error(e.getMessage(),e);
		}
	
	
		
	}
	/****
	 *  40 下传电话通讯上报时间间隔信息 ****
	 * 
	**/
	public void process_0x10_0x22() {
		byte[] data = dp.getData();
		
		int key=CommandTools.formateSequnceToInt(data);
		/***
		 * 验证流水号是否存在 
		 */
		if(key<0||!TransmitterContext.checek_Sequnce(key))
			{return;}
		ActionParams ap=null;
		//取出缓存的数据
		ap= ActionContext.getValues(key);
		if(ap==null||!CommandTools.isValidDP(data, ap))return;
		
		try {
			//判断命令是否成功！
			if(CommandTools.isSuccess(data)){
				int count=0;
				count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x22"), new Object[]{ap.getComand0(),ap.getOneP(),0});	
				if(count>0){
					ActionContext.setState(key, true, "操作成功！");
					}else{
						ActionContext.setState(key, false, "操作失败！");
					}
			}else	ActionContext.setState(key,false,MsgConfig.msg("ats-1029"));	 
		} catch (Exception e) {
			ActionContext.setState(key,false,MsgConfig.msg("ats-1029"));
			logger.error(e.getMessage(),e);
		}
	
	
		
	}
	/**** 41 下传短信通讯上报时间间隔信息 ***
	 */
	public void process_0x10_0x23() {

		byte[] data = dp.getData();
		
		int key=CommandTools.formateSequnceToInt(data);
		/***
		 * 验证流水号是否存在 
		 */
		if(key<0||!TransmitterContext.checek_Sequnce(key))
			{return;}
		ActionParams ap=null;
		//取出缓存的数据
		ap= ActionContext.getValues(key);
		if(ap==null||!CommandTools.isValidDP(data, ap))return;
		
		try {
			//判断命令是否成功！
			if(CommandTools.isSuccess(data)){
				int count=0;
				count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x10.0x23"), new Object[]{ap.getComand0(),ap.getOneP(),0});	
				 
				if(count>0){
				ActionContext.setState(key, true, "操作成功！");
				}else{
					ActionContext.setState(key, false, "操作失败！");
				}
		 
			}else	ActionContext.setState(key,false,MsgConfig.msg("ats-1030"));	 
		} catch (Exception e) {
			ActionContext.setState(key,false,MsgConfig.msg("ats-1030"));
			logger.error(e.getMessage(),e);
		}
	
	
		
	
	}
	/**** 重置启动 ***
	 */
	public void process_0x10_0x24() {
		int key=CommandTools.formateSequnceToInt(dp.getData());
		if(ActionContext.resetTempKeys.containsKey(key)){
			if(ResetContext.connectionHas(key)){
				//System.out.println("开始："+System.currentTimeMillis());
				ResetContext.connectionSuccess(key); 
			}
		}else{
			return;
		} 
	 
	}
	/**** 重置结束 ***
	 */
	public void process_0x10_0x25() {
		//System.out.println("结束："+System.currentTimeMillis());
		byte[] data = dp.getData();
		
		int key=CommandTools.formateSequnceToInt(data);
		ActionParams ap=null;
		//取出缓存的数据
		ap= ActionContext.getValues(key);
		if(ap!=null&&CommandTools.isValidDP(data, ap)){
		if(ActionContext.resetTempKeys.containsKey(key)){
		try {
			//判断命令是否成功！
			if(CommandTools.isSuccess(data)){
				getSysdao().updateResetOne(new Object[] { ap.getOneP()});
				//System.out.println("更新结束："+System.currentTimeMillis());
				//记录操作成功  
				ResetContext.over(ap.getGroupID(),ap.getOneP(), 0, ResetContext.Result_True);
			} 	else{
				ResetContext.over(ap.getGroupID(),ap.getOneP(), 0, ResetContext.Result_False);
			}
			/**
			 * 关闭对应容器中的空间占用
			 */
			ResetContext.close(ap.getOneP(),key);
		} catch (Exception e) {
			ActionContext.setState(key,false,MsgConfig.msg("ats-1030"));
			logger.error(e.getMessage(),e);
		}
		}
		}
	}

}
