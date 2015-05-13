package com.cdk.ats.udp.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;

import com.cdk.ats.udp.utils.CommandTools;
import com.cdk.ats.udp.utils.Constant;
import com.cdk.ats.web.pojo.hbm.Table1;
import com.cdk.ats.web.pojo.hbm.Table10;

/***
 * 设备信息缓存
 * 
 * @author dingkai
 * 
 */
public class AtsEquipmentCache {
	private static Logger logger=Logger.getLogger(AtsEquipmentCache.class);
	/**
	 * 缓存设置信息
	 */
	private static Vector<EquipmentCacheVO> EquipmentCache;
	/**
	 * 设置信息快速查询
	 */
	private static ConcurrentMap<String, EquipmentCacheVO> QueryEquipmentCache;

	/***
	 * 用户初始化缓存对象
	 */
	//public static List<Table1> users = null;
	/***
	 * 设备用户快速查找 
	 */
	//private  static ConcurrentMap<String, Table1> userIndex = null;
	/***
	 * 设备初始化缓存对象
	 */
	private static Vector<Table10> equis = null;
	
	private static ConcurrentMap<String, Table10> equisIndex = null;

	/***
	 * 初始化，当接收到初始化命令后调用
	 */
	public static void equisInit() {
		//users = new ArrayList<Table1>();
		logger.info("初始化缓存");
		//userIndex = new ConcurrentHashMap<String, Table1>();
		equis = new Vector<Table10>();
		equisIndex = new ConcurrentHashMap<String, Table10>();
	}
	/***
	 * 如果一层设备掉线 了，则移除初始化信息中的所有与之相关的设备与用户
	 * @param one
	 */
	public static void unUseEqui(int one,int two){
		String key = CommandTools.handKeyFormat(one,two);
		Table10 t10 = AtsEquipmentCache.getEquiReadOnly(one, two);
		if(t10!=null)
		{
			//if (equisIndex.containsKey(key))			 EQUIPMENT_STATUS_LOSE_VAL
			t10.setField130(Constant.EQUIPMENT_STATUS_LOSE_VAL);
			//System.out.println("掉线-"+t10.getField3()+"-"+t10.getField4());
		}
		if(QueryEquipmentCache!=null&&!QueryEquipmentCache.isEmpty() ){
			if (QueryEquipmentCache.containsKey(key)) 
				QueryEquipmentCache.get(key).setStatus(Constant.EQUIPMENT_STATUS_LOSE);
		}
		 
	}
	/***
	 * 如果一层设备掉线 了，则移除初始化信息中的所有与之相关的设备与用户
	 * @param one
	 */
	public static void removeEquiIndex(int one){
		if(equisIndex!=null&&!equisIndex.isEmpty())
			for(int i=1;i<=128;i++){
				removeEquiIndex(one,i);
				/*String key = CommandTools.handKeyFormat(one,i);
				if (equisIndex.containsKey(key)) {
					synchronized (equisIndex) {
						equisIndex.remove(key);
					}
				}*/
			}
		//移除设备下的所有用户
		String key2 = CommandTools.handKeyFormat(one,0);
		if (equisIndex.containsKey(key2)) 
			{	
					synchronized (equisIndex.get(key2)) {
						
						equisIndex.get(key2).getUsers().clear();
						equisIndex.remove(key2);
					}
			}
		 
			 /*if(userIndex!=null&&!userIndex.isEmpty())
			for(int i=1;i<=16;i++){
				String key = CommandTools.handKeyFormat(one,i);
				if (userIndex.containsKey(key)) 
					userIndex.remove(key);
			}*/
		}
	/***
	 * 
	 * 描述：删除索引中的缓存设备信息
	 * @createBy dingkai
	 * @createDate 2014-3-13
	 * @lastUpdate 2014-3-13
	 * @param one
	 * @param two
	 */
	public static void removeEquiIndex(int one,int two){
			String key = CommandTools.handKeyFormat(one,two);
			if (equisIndex.containsKey(key)) 
				synchronized (equisIndex) {
					equisIndex.remove(key);
				}
		 
	}
	/**
	 * 删除缓存设备树的指定一层设备以及其它下的所有子设备
	 * @param one
	 */
	public static void removeEquipmentCacheVO(int one){
		
		String key = CommandTools.handKeyFormat(one,0);
		if(QueryEquipmentCache.containsKey(key)){ 
			EquipmentCacheVO  et=QueryEquipmentCache.get(key);
			if(et!=null){
				synchronized (QueryEquipmentCache) {
					
				// 从快速检索器中移除二层设备。因为二层设备只存在 于，一层设备的子节点，以及快速查询中
				if(et.getChildren()!=null&&et.getChildren().size()>0){
					for (int i = 0; i < et.getChildren().size(); i++) {
						QueryEquipmentCache.remove(CommandTools.handKeyFormat(one,et.getChildren().get(i).getAddress()));
					}
					
				}
				//移除一层设备在 检查器与缓存设备树中的对象
				QueryEquipmentCache.remove(key);
				}
				synchronized (EquipmentCache) {
					EquipmentCache.remove(et);
				}
			}
		}
	}
	 
		
		/*users = null;
		userIndex = null;
		equis = null;
		equisIndex = null; 
		
		users = new ArrayList<Table1>();
		userIndex = new HashMap<String, Table1>();
		equis = new ArrayList<Table10>();
		equisIndex = new HashMap<String, Table10>();*/
	/***
	 * 刷新缓存的设备树信息
	 * 接收到初始化结束命令，并执行完保存后调用。根据一层设备编号 
	 * 注意，因为这个只会在初始化成功后才执行，因此。 要将一层设备的原始数据以及期
	 * 下的所有二层设备全删除，再重新建立，方为有效
	 */
	public static void refreshEquipmentCacheVO(int one) {
		if(logger.isDebugEnabled()){
			logger.debug("保存并写入缓存数据");
		}
		//在创建设备树缓存信息前，要清除已经存在的此设备的数据，以保证显示最新的数据
		removeEquipmentCacheVO(one);		
		/**
		 * 
		 * 在初始化信息销毁在移除之前，将设备的初始信息缓存起来，
		 * 
		 **/
		EquipmentCacheVO equiOne= creatEquipmentCacheVO(one, 0);
		//从初始化的快速检索窗口中找到对应的一层设备
		Table10 t10=equisIndex.get(CommandTools.handKeyFormat(one,0));
		//不 为空就进行以下操作
		if(t10!=null){
			//一层设备
			equiOne.setId(t10.getField1());
			 
			equiOne.setStatus(t10.getField130().toString());//!=null?t10.getField130().toString():Constant.EQUIPMENT_STATUS_LOSE);
			equiOne.setText(t10.getField121());
			equiOne.setParentid("0");
			//如果二层设备不是空，则将二层设备做为一个子节点加入
			if(t10.getSecond()!=null&&!t10.getSecond().isEmpty()){
				System.out.println(t10.getSecond().size()+"==============");
				equiOne.setLeaf(false);
				for (int i = 0; i < t10.getSecond().size(); i++) {
					//if(t10.getSecond().get(i).getField8()==null)continue;
					//创建二层设备的缓存 
					EquipmentCacheVO equiTwo= creatEquipmentCacheVO(one, t10.getSecond().get(i).getField4());
					equiTwo.setId(t10.getSecond().get(i).getField1());
					equiTwo.setParentid(equiOne.getId()+"");
					//equiTwo.setId(t10.getSecond().get(i).getField1());
					//equiTwo.setStatus(Constant.EQUIPMENT_STATUS_NORMAL);
					equiTwo.setStatus(t10.getSecond().get(i).getField130().toString());//!=null?t10.getSecond().get(i).getField130().toString():Constant.EQUIPMENT_STATUS_LOSE);
					equiTwo.setText(t10.getSecond().get(i).getField121());
					equiTwo.setLeaf(true);
					//不用手动填充，因为在调用 creatEquipmentCacheVO时，已经维护好了父子关系 。只需要修改其中的值就行了
					//equiOne.getChildrens().add(equiTwo);
				}
			}		
			
			/**
			 * 删除此设备的所有一二层设备的索引
			 */
			//equisIndex.remove(CommandTools.handKeyFormat(one,0));
			/**
			 * 删除此设备的所有一层设备储存
			 * */
		/*	if(equis!=null&&!equis.isEmpty())
			for (int i = 0; i < equis.size(); i++) {
				if(equis.get(i).getField3().equals(one)){
					equis.remove(equis.get(i));
					break;
				}
			}*/
			/***
			 * 删除此设备的相关用户
			 */
			/*if(userIndex!=null&&!userIndex.isEmpty()){
				Set<String> keys=userIndex.keySet();
				for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					if(userIndex.get(key).getField11().equals(one)){
						userIndex.remove(key);
					}
				}
				 
			}*/
		}
	}
	/***
	 * 
	 * 描述： 获取一个设备缓存信息  只读
	 * @createBy dingkai
	 * @createDate 2013-12-28
	 * @lastUpdate 2013-12-28
	 * @param one
	 * @return
	 */
	public static EquipmentCacheVO readEqui(int one){
		
		return readEqui(one,0);
	}
	/***
	 * 
	 * 描述： 获取一个设备缓存信息  只读
	 * @createBy dingkai
	 * @createDate 2013-12-28
	 * @lastUpdate 2013-12-28
	 * @param one
	 * @param two
	 * @return
	 */
	public static EquipmentCacheVO readEqui(int one,int two){
			
		return QueryEquipmentCache.get(CommandTools.handKeyFormat(one,two));
	}
	
	
	/***
	 * 
	 * 描述： 获取用户信息，根据设备ID与用户ID查询，若没有则返回 空
	 * @createBy dingkai
	 * @createDate 2014-1-1
	 * @lastUpdate 2014-1-1
	 * @param one
	 * @param userNum
	 * @return
	 */
	public static Table1 getUserReadOnly(int one, int userNum){
		String key2 = CommandTools.handKeyFormat(one, 0);
		if(equisIndex.containsKey(key2))
		{
			if(equisIndex.get(key2).getUsers()!=null){
				for (Table1 t1 : equisIndex.get(key2).getUsers()) {
					if(t1.getField12().equals(Integer.valueOf(userNum))){
						return t1;
					}
				}
			}
		}
			return null;
	}
/**
 * 获取一个用户
 * 没有则创建一个只包含设备号与用户号的空用户
 * @param one
 * @param userNum
 * @return
 */
	public synchronized static Table1 getUser(int one, int userNum) {
		//String key = CommandTools.handKeyFormat(one, userNum);
		Table1 tempTable1=getUserReadOnly(one, userNum);
		if (tempTable1!=null) {
			return tempTable1;
		} else {
			String parentKey= CommandTools.handKeyFormat(one,0);
			if(!equisIndex.containsKey(parentKey)){
				//如果一层设备都不存在 ，则我必须要先创建 一个一层设备信息
				//则创建一个一层设备
				Table10 tempParent = new Table10();
				tempParent.setField3(one);
				tempParent.setField4(0);
				synchronized (equisIndex) {
					equisIndex.put(parentKey, tempParent);
				}
				
			}
			Table1 user = new Table1();
			user.setField11(one);
			user.setField12(userNum);
			user.setField6(0);//0-启用用户
			if(userNum==0){
				user.setField5(2);
				user.setField3(CommandTools.createAdminName(one));
			}
			else{
				user.setField5(3);
			}
			//userIndex.put(key, user);
			synchronized (equisIndex.get(parentKey)) {
				equisIndex.get(parentKey).getUsers().add(user);
			}
			return user;
		}

	}

	/***
	 * 获取一个缓存设备对象,如果不存在 则先创建，若一层设备不存在 也先创建 
	 * @param one
	 * @param two
	 * @return
	 */
	public synchronized static Table10 getEqui(int one, int two) {
		if(logger.isDebugEnabled()){
			logger.debug("获取一个设备"+one+"-"+two);
		}
		String key = CommandTools.handKeyFormat(one, two);
		if(one==0&&two==0)return null;
		if (equisIndex.containsKey(key)&&equisIndex.get(key)!=null) {
			return equisIndex.get(key);
		} else {
			String parentKey= CommandTools.handKeyFormat(one,0);
			if(!equisIndex.containsKey(parentKey)){
				//如果一层设备都不存在 ，则我必须要先创建 一个一层设备信息
				//则创建一个一层设备
				Table10 tempParent = new Table10();
				tempParent.setField3(one);
				tempParent.setField4(0);
				tempParent.setDtype(0);
				equis.add(tempParent);
				equisIndex.put(parentKey, tempParent);
			}
			if(two!=0){
				Table10 equi = new Table10();
				equi.setField3(one);
				equi.setField4(two);
				equisIndex.put(key, equi);
				synchronized (equisIndex.get(parentKey)) {
					equisIndex.get(parentKey).getSecond().add(equi);
				}
			}
			return equisIndex.get(key);
		}

	}
	/***
	 * 获取 一个设备 缓存 信息，这个时候 ，只取不创建 
	 * @param one
	 * @param two
	 * @return
	 */
	public static Table10 getEquiReadOnly(int one, int two) {
		if(logger.isDebugEnabled()){
			logger.debug(" only read " + one + "-" + two);}
		String key = CommandTools.handKeyFormat(one, two);
		return equisIndex.get(key);

	}

	/***
	 * 初化信息
	 */
	public static void init() {
		QueryEquipmentCache = new ConcurrentHashMap<String, EquipmentCacheVO>();
		EquipmentCache = new Vector<EquipmentCacheVO>();
		equisInit();
	}

	/***
	 * 根据 地址 获取某个设备缓存信息
	 * 
	 * @param key
	 *            标准键（一层地址与二层地址的标准格式化）
	 * @return
	 */
	public static EquipmentCacheVO queryByAddress(String key) {
		return QueryEquipmentCache.get(key);
	}

	/***
	 * 根据 地址 获取某个设备缓存信息
	 * 
	 * @param one
	 *            一层设备地址
	 * @param two
	 *            二层设备地址
	 * @return
	 */
	public static EquipmentCacheVO queryByAddress(int one, int two) {
		return queryByAddress(CommandTools.handKeyFormat(one, two));
	}
	
	/**
	 * 
	 * 描述： 刷新缓存的设备详细信息
	 * @createBy dingkai
	 * @createDate 2014-1-3
	 * @lastUpdate 2014-1-3
	 * @param t10
	 */
	public static void refreshEquipment(Table10 t10){
		
		Table10 temp=getEquiReadOnly(t10.getField3(), t10.getField4());
		if(temp!=null){
			temp.copyFrom(t10);
		}
		 
		
	}
	
	/***
	 * 
	 * 描述：刷新缓存（ 功能不全）
	 * @createBy dingkai
	 * @createDate 2014-1-3
	 * @lastUpdate 2014-1-3
	 * @param t10
	 */
	public static void refreshEquipmentCacheVO(Table10 t10) {
		String key = CommandTools.handKeyFormat(t10.getField3(), t10.getField4());
		if (QueryEquipmentCache.containsKey(key)) {
			EquipmentCacheVO temp=QueryEquipmentCache.get(key);
			if(temp!=null){
				temp.copyfrom(t10);
			}
		}
	}
	/***
	 * 创建一个缓存实体，并将创建的实体返回，并将此实体保存至缓存
	 * 此缓存主要用于页面的树查询 
	 * @param one
	 * @param two
	 *            * @return 返回一个有一层设备与二层设备地址值 的缓存 实体
	 */
	public static EquipmentCacheVO creatEquipmentCacheVO(int one, int two) {
		if(logger.isDebugEnabled()){
			logger.debug("缓存一个设备"+one+"-"+two);
		}
		String key = CommandTools.handKeyFormat(one, two);
		if (QueryEquipmentCache.containsKey(key)) {
			return QueryEquipmentCache.get(key);
		} else {
			EquipmentCacheVO vo = new EquipmentCacheVO();
			vo.setParentAddress(one);
			vo.setAddress(two);
			if(two==0)
			{
				AtsEquipmentCache.EquipmentCache.add(vo);
				AtsEquipmentCache.QueryEquipmentCache.put(key, vo);
			}
			else {
				String parentKey = CommandTools.handKeyFormat(one, 0);
				
				//如果这时候没有一层设备
				if(!QueryEquipmentCache.containsKey(parentKey)){
					//则创建一个一层设备
					EquipmentCacheVO tempParent = new EquipmentCacheVO();
					tempParent.setParentAddress(one);
					tempParent.setAddress(0);
					//并将这个一层设备加入调协检查器与设备对缓存中
					AtsEquipmentCache.EquipmentCache.add(tempParent);
					AtsEquipmentCache.QueryEquipmentCache.put(parentKey, tempParent);
				}
				//如果是二层设备，则将二层设备放入对应的一层设备的子节点中。
				AtsEquipmentCache.QueryEquipmentCache.get(parentKey).getChildren().add(vo);
				//放入快速检索器
				AtsEquipmentCache.QueryEquipmentCache.put(key,vo);
			}
			//返回刚刚创建的对象
			return vo;//为提高效率，直接返回、、AtsEquipmentCache.QueryEquipmentCache.get(key);
		}
	}

	/**
	 * 查询当前的有效缓存设备树
	 * @return
	 */
	public static List<EquipmentCacheVO>  getTree(){
		return EquipmentCache;
	}
	
	/***
	 * 
	 * 描述：  获得一个树节点的缓存数据，若没有则返回 为空
	 * @createBy dingkai
	 * @createDate 2014-2-16
	 * @lastUpdate 2014-2-16
	 * @param one
	 * @param two
	 * @return
	 */
	public static EquipmentCacheVO getTreeNode(int one,int two){
		String key = CommandTools.handKeyFormat(one, two);
		return QueryEquipmentCache.get(key);
		
	}
	
	/***
	 * 重置设备信息
	 * @param t10s
	 */
	public static void reset(Table10 t10){
		if(logger.isDebugEnabled()){
			logger.debug("重置刷新缓存数据");
		}
		if(t10!=null){
			int one=t10.getField3(),two=t10.getField4();
			if(queryByAddress(one,two)!=null){
				EquipmentCacheVO equiOne= creatEquipmentCacheVO(one,two);
				synchronized (equiOne) {
				if(t10.getSecond()!=null&&!t10.getSecond().isEmpty()){
					for (int i = 0; i < equiOne.getChildren().size(); i++) {
						synchronized (QueryEquipmentCache) {
							
							QueryEquipmentCache.remove(CommandTools.handKeyFormat(one, two));
						}
					}	
					
					equiOne.getChildren().clear();
					equiOne.setId(t10.getField1());
					equiOne.setText(t10.getField121());
					for (int i = 0; i < t10.getSecond().size(); i++) {
						//创建二层设备的缓存 
						EquipmentCacheVO equiTwo= creatEquipmentCacheVO(one, t10.getSecond().get(i).getField4());
						equiTwo.setId(t10.getSecond().get(i).getField1());
						equiTwo.setParentid(equiOne.getId()+"");
						 //!=2?Constant.EQUIPMENT_STATUS_NORMAL:Constant.EQUIPMENT_STATUS_DISABLE
						equiTwo.setStatus(t10.getSecond().get(i).getField130()!=null?t10.getSecond().get(i).getField130().toString():Constant.EQUIPMENT_STATUS_LOSE);
						equiTwo.setText(t10.getSecond().get(i).getField121());
						equiTwo.setLeaf(true);
					}
					
				}else{ 
					equiOne.setId(t10.getField1());
					equiOne.setText(t10.getField121());
					if(equiOne.getChildren()!=null&&equiOne.getChildren().size()>0){
						for (int i = 0; i < equiOne.getChildren().size(); i++) {
							equiOne.getChildren().get(i).setParentid(""+equiOne.getId());
						}	
					}
				}
			}
			}
		}
	     
			
		 
	}
	
	
	public static List<Table10> getEquipmentFirst(){
		return equis;
	}
	
	/***
	 * 
	 * 描述：获取一个缓存区的复本
	 * @createBy dingkai
	 * @createDate 2014-1-12
	 * @lastUpdate 2014-1-12
	 * @return
	 */
	public static List<Table10> getEquipmentFirstClone(){
		ArrayList<Table10>  tempt10s=new ArrayList<Table10>();
		for (int i = 0; i < equis.size(); i++) {
				Table10 t10=new Table10();
				t10.copyAllFrom(equis.get(i));
				tempt10s.add(t10);
		}
		return tempt10s;
	}
	/***
	 * 
	 * 描述：获取一个缓存区的复本
	 * @createBy dingkai
	 * @createDate 2014-1-12
	 * @lastUpdate 2014-1-12
	 * @return
	 */
	public static List<Table10> getEquipmentFirstClone(int one){
		ArrayList<Table10>  tempt10s=new ArrayList<Table10>();
		/*for (int i = 0; i < equis.size(); i++) {
				Table10 t10=new Table10();
				t10.copyAllFrom(equis.get(i));
				tempt10s.add(t10);
		}*/
		String key=CommandTools.handKeyFormat(one,0);
		if(equisIndex.containsKey(key)){
			Table10 t10=new Table10();
			t10.copyAllFrom(equisIndex.get(key));
			tempt10s.add(t10);
		}
		return tempt10s;
	}
	/**
	 * 
	 * 描述： 获取一个一层设备的所有二层设备 
	 * @createBy dingkai
	 * @createDate 2014-1-12
	 * @lastUpdate 2014-1-12
	 * @return
	 */
	public static List<Table10> getEquipmentSecondClone(int one ){
		Table10 cacheT10=getEquiReadOnly(one, 0);
		ArrayList<Table10>  tempt10s=new ArrayList<Table10>();
		//首先添加一层设备对应的二层1-0设备
		Table10 t10First=new Table10();
		t10First.copyAllFrom(cacheT10); 
		tempt10s.add(t10First);
		if(cacheT10!=null&&cacheT10.getSecond()!=null){
			
		for (int i = 0; i < cacheT10.getSecond().size(); i++) {
				Table10 t10=new Table10();
				t10.copyAllFrom( cacheT10.getSecond().get(i));
				tempt10s.add(t10);
		}
		}
		return tempt10s;
	}
	/*
	 * public static void main(String[] args) { AtsDeviceCache.init();
	 * EquipmentCacheVO one= new EquipmentCacheVO(); one.setAddress(002);
	 * one.setParentAddress(0); one.setName("ni mei!");
	 * AtsDeviceCache.AtsEquipmentCache.add(one); String key=null;
	 * if(one.getParentAddress()==null||one.getParentAddress().equals(0)){
	 * key=CommandTools.handKeyFormat(one.getAddress(),0); }else {
	 * key=CommandTools.handKeyFormat(one.getParentAddress(),one.getAddress());
	 * } AtsDeviceCache.QueryEquipmentCache.put(key, one);
	 * System.out.println(AtsDeviceCache
	 * .QueryEquipmentCache.get(key).getName());
	 * System.out.println(AtsDeviceCache.AtsEquipmentCache.get(0).getName());
	 * System
	 * .out.println(AtsDeviceCache.QueryEquipmentCache.get(key).getName()==
	 * AtsDeviceCache.AtsEquipmentCache.get(0).getName());
	 * System.out.println("-----------------------------------------------------"
	 * );
	 * AtsDeviceCache.QueryEquipmentCache.get(key).setName("111111111111111！");
	 * System
	 * .out.println(AtsDeviceCache.QueryEquipmentCache.get(key).getName());
	 * System.out.println(AtsDeviceCache.AtsEquipmentCache.get(0).getName());
	 * System
	 * .out.println(AtsDeviceCache.QueryEquipmentCache.get(key).getName()==
	 * AtsDeviceCache.AtsEquipmentCache.get(0).getName()); }
	 */
	/*
	 * public static List<Table10> AtsCache=new ArrayList<Table10>();
	 * 
	 * public static void init() { reload(); } public static void reload() {
	 * BaseDao basedao = DaoFactory.getBaseDao(); try { List<Table10> devices =
	 * basedao.findObjectsByHql(SqlConfig.SQL("ats.atsCache.reload.query")); if
	 * (devices != null && !devices.isEmpty()) { AtsCache.clear();
	 * AtsCache.addAll(devices); } } catch (Exception e) {
	 * logger.error("载入设备缓存信息失败！", e); } finally { if (basedao != null)
	 * basedao.closeSession(); } }
	 */
	/***
	 * 没有 完成 TO DO
	 * 
	 * @param one
	 * @param two
	 * @param status
	 */
	/*
	 * public static void setDeviceStatus(int one,int two,String status){ for
	 * (int i = 0; i < AtsCache.size(); i++) {
	 * if(AtsCache.get(i).getField3().equals
	 * (one)&&AtsCache.get(i).getField4().equals(two)) {
	 * AtsCache.get(i).setField15(status); break; }
	 * 
	 * }
	 * 
	 * }
	 */
}
