package com.cdk.ats.udp.operating;

import java.net.DatagramPacket;

import org.apache.log4j.Logger;

import com.cdk.ats.udp.cache.AtsEquipmentCache;
import com.cdk.ats.udp.event.EventContext;
import com.cdk.ats.udp.process.ActionContext;
import com.cdk.ats.udp.process.ActionParams;
import com.cdk.ats.udp.receiver.ReceiverContext;
import com.cdk.ats.udp.reset.ResetContext;
import com.cdk.ats.udp.transmitter.TransmitterContext;
import com.cdk.ats.udp.utils.CommandTools;
import com.cdk.ats.udp.webplug.dao.DaoFactory;
import com.cdk.ats.web.dao.BaseDao;
import com.cdk.ats.web.pojo.hbm.Table10;
import common.cdk.config.files.msgconfig.MsgConfig;

/***
 * 
 * 针对命令总分类 0x20 上报用户操作信息 0x20 0x01 
 * 下传用户操作信息 0x20 0x02 上报管理员操作信息 0x20 0x03
 * 下传管理员操作信息 0x20 0x04 下传输出信息 0x20 0x05
 * 
 * @author cdk
 * 
 */
public class Operating_0x20 {

	private static Logger logger = Logger.getLogger(Operating_0x20.class);
	private DatagramPacket dp;

	private BaseDao baseDao;

	public Operating_0x20( ) {
	}
	public Operating_0x20(DatagramPacket dp) {
		this.dp = dp;
	}
	public void closeDao(){
		if(this.baseDao!=null)this.baseDao.closeSession();
		
	}
	private BaseDao getBaseDao(){
		if(baseDao==null){
			baseDao=DaoFactory.getBaseDao();
		}
		return baseDao;
	}
 
 
	/***
	 * 上报用户操作信息
	 * @return 执行成功与否
	 */
	public void process_0x20_0x01() {
		boolean end=false;
		byte[] data = dp.getData();
		int key=0;
		try {
		 int  one,uid,action;
		 end=true;
		 key= ReceiverContext.putReceiveSequnce(CommandTools.getSequnces(data));
		 /*** 一层设备网络地址 ***/
		 one=data[9]&0xff;
		 /**隶属用户编号（1-16）**/
		 uid=data[14]&0xff;
		 /**操作**/
		 action=data[15]&0xff;
		 /**0x01为布防，0x02为撤防，0x03为确认*/
		 EventContext.logUserAction(one, uid, action);
		
		 //(one,0,uid,1, "用户布防");
		 //else if(action==2)
		// EventContext.logUser(one,0,uid,2, "用户撤防");
		 
		/*int count=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table1.update.0x20.0x01"), new Object[]{action,one,uid});
		count+=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x20.0x01-1"),new Object[]{action,one,uid});
		count+=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x20.0x01-2"),new Object[]{action,one,uid});
		count+=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x20.0x01-3"),new Object[]{action,one,uid});
		count+=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x20.0x01-4"),new Object[]{action,one,uid});
		count+=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x20.0x01-5"),new Object[]{action,one,uid});
		count+=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x20.0x01-6"),new Object[]{action,one,uid});
		count+=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x20.0x01-7"),new Object[]{action,one,uid});
		count+=this.getBaseDao().updateByHql(SqlConfig.SQL("ats.table10.update.0x20.0x01-8"),new Object[]{action,one,uid});
		if(count>0)end=true;*/
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			//无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);
			/*TransmitterContext.pushDatagram(ResponseCommandTools.responseCmdBase12((byte)0x20,(byte)0x01,data[4], data[5], data[6], data[7],
					data[9], data[10], (byte)(end?0x00:0xff)),false,key,dp.getAddress().toString(),dp.getPort());*/		
		}
	}
	/***
	 * 下传用户操作信息 0x20 0x02 处理方法 ，执行数据的校验、存储与数据包响应
	 * 找出对应一层设备下的所有二层设备中，此用户对应的端口，将输入属性修改为对应的值（0x01为布防，0x02为撤防，0x03为查看）
	 * 同时将用户的布防状态设置为布防 （1）。
	 * @return 执行成功与否
	 */
	public void process_0x20_0x02() {
		boolean end=false;
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
				end=true;
			//判断命令是否成功！
			if(CommandTools.isSuccess(data)){
				int action=ap.getComand0();
				int one=ap.getOneP();
				int uid=ap.getUserCode();
				
				 /**0x01为布防，0x02为撤防，0x03为确认*/
				 EventContext.logUserAction(one, uid, action);
				if (end) {
					ActionContext.setState(key, true, "操作成功！");
				}else{
					ActionContext.setState(key, false, "操作失败！");
				}
			}	else	ActionContext.setState(key,false,MsgConfig.msg("ats-1032"));
		} catch (Exception e) {
			ActionContext.setState(key,false,MsgConfig.msg("ats-1032"));
			logger.error(e.getMessage(),e);
		}
	}
	/***
	 *设备中心响应0x20-0x03的数据包处理方法 
	 *上报管理员操作信息
	 * 找出对应一层设备下的所有二层设备中，此用户对应的端口，将输入属性修改为对应的值（0x01为布防，0x02为撤防，0x03为处理，0x04为编程）
	 * @return 执行成功与否
	 */
	public Object process_0x20_0x03(){
		boolean end=false;
		byte[] data = dp.getData();
		int key=0;
		try {
			key= ReceiverContext.putReceiveSequnce(CommandTools.getSequnces(data));
			 int  one,uid,action;
			 /*** 一层设备网络地址 ***/
			 one=data[9]&0xff;
			 
			 uid=0;
			 /**操作**/
			 action=data[14]&0xff;
			 /**0x01为布防，0x02为撤防，0x03为确认,0x04编程*/
			 EventContext.logUserAction(one, uid, action);
			} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			//无论本地的处理是否成功。都必须给对方发送一个响应包
			TransmitterContext.sendResponseDatagram(end, key, dp);
		}
		return end;
	}
	
	/***
	 *设备中心响应0x20-0x04的数据包处理方法 
	 *下传管理员操作信息
	 * 
	 * @return 执行成功与否
	 */
	public void process_0x20_0x04(){
//System.out.println("管理员命令响应处理！....");
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
				//System.out.println("管理员操作响应了：成功");
					int action=ap.getComand0();
					int one=ap.getOneP();
					int uid=ap.getUserCode();
				 
					/**0x01为布防，0x02为撤防，0x03为处理,0x04编程*/
					EventContext.logAdminAction(one,uid,action);					
					if(action==4){
						//System.out.println("响应命令4 编程启动成功");
						TransmitterContext.delSequnce(key);
						openDevModule(one);					
						return;
					}else{
						ActionContext.setState(key, true, "操作成功！");
					}
			
			}else{ 
				//System.out.println("管理员操作响应了：失败");
				//当此命令为编程状态时
				if(ap.getComand0()==4){
					//System.out.println("管理员操作 编程 失败");
					TransmitterContext.delSequnce(key);
					ResetContext.getConnection().remove(key);
					ActionContext.setState(key, false, "操作失败！");
				}
				else{
					ActionContext.setState(key, false, "操作失败！");
				}
			}
			//else	ActionContext.setState(key,false,MsgConfig.msg("ats-1031"));	 
		} catch (Exception e) {
			ActionContext.setState(key,false,MsgConfig.msg("ats-1031"));
			logger.error(e.getMessage(),e);
		}
	}
	
	/****
	 * 
	 * 描述：  打开编辑模式 
	 * @createBy dingkai
	 * @createDate 2014-1-28
	 * @lastUpdate 2014-1-28
	 * @param key
	 */
	private void openDevModule(int key){
		ResetContext.connectionDevSuccess(key);
	}
	
	/***
	 *设备中心响应0x20-0x05的数据包处理方法 
	 *下传输出信息
	 * 
	 * @return 执行成功与否
	 */
	public void process_0x20_0x05(){
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
		//System.out.println("处理");
		if(ap==null||!CommandTools.isValidDP(data, ap))return;
		
		try {
			//判断命令是否成功！
			if(CommandTools.isSuccess(data)){
				//修改用户布防状态
				 
				//EquipmentCacheVO ccVO=AtsEquipmentCache.readEqui(ap.getOneP(), ap.getTwoP());
				/*Table10 t10=(Table10) this.getBaseDao().findOnlyByHql("ats.system.query.Table10.by",new Object[]{ap.getOneP(),ap.getTwoP()});
				if(t10!=null){
					 switch (ap.getComand3()) {
					case 1:	t10.setField155(ap.getComand4());	break;
					case 2:	t10.setField156(ap.getComand4());	break;
					case 3:	t10.setField157(ap.getComand4());	break;
					case 4:	t10.setField158(ap.getComand4());	break;
					case 5:	t10.setField159(ap.getComand4());	break;
					case 6:	t10.setField160(ap.getComand4());	break;
					case 7:	t10.setField161(ap.getComand4());	break;
					case 8:	t10.setField162(ap.getComand4());	break;
					} 
					// AtsEquipmentCache.refreshEquipment(t10);
					getBaseDao().saveOrUpdate(t10);
					ActionContext.setState(key, true, "操作成功！");
					return ;
				}*/
				Table10   t10=AtsEquipmentCache.getEquiReadOnly(ap.getOneP(),ap.getTwoP());
				if(t10!=null){
					switch (ap.getComand3()) {
					case 1:	t10.setField155(ap.getComand4());	break;
					case 2:	t10.setField156(ap.getComand4());	break;
					case 3:	t10.setField157(ap.getComand4());	break;
					case 4:	t10.setField158(ap.getComand4());	break;
					case 5:	t10.setField159(ap.getComand4());	break;
					case 6:	t10.setField160(ap.getComand4());	break;
					case 7:	t10.setField161(ap.getComand4());	break;
					case 8:	t10.setField162(ap.getComand4());	break;
					} 
					ActionContext.setState(key, true, "操作成功！");
					EventContext.logOutputClose(ap.getOneP(), ap.getTwoP(), ap.getComand3(), ap.getComand4());
					if(t10!=null){
						t10.setPortState(2, CommandTools.switchPortIndex128To8(ap.getComand3()), ap.getComand4()==0?-1:1);//1绿色，3黄色
					}
				}else{
					ActionContext.setState(key,false,MsgConfig.msg("ats-1031"));
				}
			} else{
				ActionContext.setState(key,false,MsgConfig.msg("ats-1031"));
				}
				 
		} catch (Exception e) {
			ActionContext.setState(key,false,MsgConfig.msg("ats-1031"));
			logger.error(e.getMessage(),e);
		}
	}
	
	/**
	 {byte[] data = dp.getData();
		int key=CommandTools.formateSequnceToInt(data);
	 
		if(key<0||!TransmitterContext.checek_Sequnce(key))
			{return;}
		ActionParams ap=null;
		//取出缓存的数据
		ap= ActionContext.getValues(key);
		if(ap==null||!CommandTools.isValidDP(data, ap))return;
		
		try {
			//判断命令是否成功！
			if(CommandTools.isSuccess(data)){
				EventContext.logOutputClose(ap.getOneP(), ap.getTwoP(), ap.getComand0(), ap.getComand1());
				ActionContext.setState(key, true, "操作成功！");
			}else	ActionContext.setState(key,false,MsgConfig.msg("ats-1031"));	 
		} catch (Exception e) {
			ActionContext.setState(key,false,MsgConfig.msg("ats-1031"));
			logger.error(e.getMessage(),e);
		}
	}}
	 * **/
}
