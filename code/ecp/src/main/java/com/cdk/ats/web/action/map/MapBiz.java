package com.cdk.ats.web.action.map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cdk.ats.udp.cache.AtsEquipmentCache;
import com.cdk.ats.udp.exception.AtsException;
import com.cdk.ats.web.dao.BaseDao;
import com.cdk.ats.web.pojo.hbm.Map;
import com.cdk.ats.web.pojo.hbm.Mappoint;
import com.cdk.ats.web.pojo.hbm.Table10;
import com.cdk.ats.web.utils.TreeNode;
import common.cdk.config.files.sqlconfig.SqlConfig;

public class MapBiz {

	
	private Logger logger=Logger.getLogger(MapBiz.class);
	private BaseDao mapDao;
	
	/***
	 * 
	 * 描述： 根据ID查出对应的MAP信息，若没有ID，则查找默认的
	 * @createBy dingkai
	 * @createDate 2013-12-2
	 * @lastUpdate 2013-12-2
	 * @param map
	 * @return
	 */
	public Map viewOnlyMap(Map map){
		if(map!=null&&map.getMapid()!=null&&map.getMapid()>0){
			map=(Map) mapDao.findById("com.cdk.ats.web.pojo.hbm.Map", map.getMapid());
			
		}else{			
			map=(Map) mapDao.findOnlyByHql("from Map t where t.isuse=1 order by mapid");
		}
		if(map!=null&&map.getMapid()!=null&&map.getMapid()>0){
			
			map.setPoints(mapDao.findObjectsByHql("from Mappoint t where t.mapid=? order by pointid ", new Object[]{map.getMapid()}));
		}
		return map;
		
	}
	/**
	 * 
	 * 描述：查询当前地图。带状态 
	 * @createBy dingkai
	 * @createDate 2014-4-5
	 * @lastUpdate 2014-4-5
	 * @param map
	 * @return
	 */
	public Map viewCurrentOnlyMap(Map map){
		if(map!=null&&map.getMapid()!=null&&map.getMapid()>0){
			map=(Map) mapDao.findById("com.cdk.ats.web.pojo.hbm.Map", map.getMapid());
			
		}else{			
			map=(Map) mapDao.findOnlyByHql("from Map t where t.isuse=1 order by mapid");
		}
		if(map!=null&&map.getMapid()!=null&&map.getMapid()>0){
			
			map.setPoints(mapDao.findObjectsByHql("from Mappoint t where t.mapid=? order by pointid ", new Object[]{map.getMapid()}));
			loadPointStatus(map);
		}
		return map;
		
	}
	/***
	 * 
	 * 描述： 获取每个地图点的状态，若不存在 或是异常数据，默认为-1，-1是指设备不存在 
	 * @createBy dingkai
	 * @createDate 2014-4-5
	 * @lastUpdate 2014-4-5
	 */
	private void loadPointStatus(Map map){
		Integer type1=Integer.valueOf(1);
		Integer type2=Integer.valueOf(2);
		for (Mappoint point :map.getPoints()) {
			 Table10 t10= AtsEquipmentCache.getEquiReadOnly(point.getEquiparent(),point.getEquiid());
			 if(t10!=null){
				 if(point.getPtype()==null||point.getPort()==null){
					 point.setStatus(-1);continue;
				 }
			 	point.setStatus(t10.getPortState(point.getPtype().intValue(),point.getPort().intValue()));
			 	/*if(type1.equals(point.getPtype())){
			 		point.setName(t10.findPortInputName(point.getPort()));
			 	}else if(type2.equals(point.getPtype())){
			 		point.setName(t10.loadPortOutputName(point.getPort()));
			 	}*/
			 }
		}
	}
 
	/***
	 * 
	 * 描述：查出所有的地图
	 * @createBy dingkai
	 * @createDate 2013-12-15
	 * @lastUpdate 2013-12-15
	 * @return
	 */
	public List<Map> findAllMap(){
		 
	    List<Map> maps= mapDao.findObjectsByHql("from com.cdk.ats.web.pojo.hbm.Map t order by t.isuse DESC,t.mapid");
	    return maps;
		
	}
	
	/***
	 * 
	 * 描述：  保存 地图上的点
	 * @createBy dingkai
	 * @createDate 2013-12-11
	 * @lastUpdate 2013-12-11
	 * @param point
	 * @return
	 */
	public Integer savePoint(Mappoint point){
		Integer  pointID=0;
		if(point.getPointid()!=null&&point.getPointid()>0){
			mapDao.updateObject("com.cdk.ats.web.pojo.hbm.Mappoint", point);
			pointID=point.getPointid();
		}else{
			pointID=(Integer) mapDao.save(point);
		}
		return pointID;
	}
	
	/***
	 * 
	 * 描述：  保存或修改map
	 * @createBy dingkai
	 * @createDate 2013-12-21
	 * @lastUpdate 2013-12-21
	 * @param map
	 * @return
	 */
	public Integer saveMap(Map map){
		Integer  mapId=0;
		if(map.getMapid()!=null&&map.getMapid()>0){
			mapDao.updateObject("com.cdk.ats.web.pojo.hbm.Map", map);
			mapId=map.getMapid();
		}else{
			mapId=(Integer) mapDao.save(map);
		}
		return mapId;
	}
	/***
	 * 
	 * 描述：  保存或修改map
	 * @createBy dingkai
	 * @createDate 2013-12-21
	 * @lastUpdate 2013-12-21
	 * @param map
	 * @return
	 */
	public Integer updateMapName(Map map){
		Integer  count=null;
		if(map.getMapid()!=null&&map.getMapid()>0&&!StringUtils.isBlank(map.getMapname())){
			count=mapDao.updateByHql("update com.cdk.ats.web.pojo.hbm.Map t set t.mapname=? where t.mapid=?",new Object[]{map.getMapname(),map.getMapid()});
		}
		return count;
	}
	
	/***
	 * 
	 * 描述：  删除 地图上的点
	 * @createBy dingkai
	 * @createDate 2013-12-11
	 * @lastUpdate 2013-12-11
	 * @param point
	 * @return
	 * @throws AtsException 
	 */
	public boolean  deletePoint(Mappoint point) throws AtsException{
		if(point.getPointid()!=null&&point.getPointid()>0){
			return mapDao.deleteByhql("delete from com.cdk.ats.web.pojo.hbm.Mappoint t where t.pointid="+ point.getPointid())>0;
		} else{
			throw new AtsException("删除失败，数据不存在。");
			
		}
	}
	/***
	 * 
	 * 描述：  保存默认设置
	 * @createBy dingkai
	 * @createDate 2013-12-11
	 * @lastUpdate 2013-12-11
	 * @param defaultMap
	 * @throws AtsException 
	 */
	public void saveDefaultShow(Map defaultMap) throws AtsException{
		if(defaultMap!=null&&defaultMap.getMapid()!=null&&defaultMap.getMapid()>0){
			mapDao.updateByHql("update com.cdk.ats.web.pojo.hbm.Map m set m.isuse=0 where m.isuse=1");
			int count= mapDao.updateByHql("update com.cdk.ats.web.pojo.hbm.Map m set m.isuse=1 where m.mapid=?", new Object[]{defaultMap.getMapid()});
			if(count<1){
				throw new AtsException("设置失败");
			}
		}else {
			throw new AtsException("设置失败");
			
		}
		
		
	}
 /***
  * 
  * 描述：  删除地图 
  * @createBy dingkai
  * @createDate 2013-12-15
  * @lastUpdate 2013-12-15
  * @param defaultMap
  * @throws AtsException
  */
	public void delMap(Map defaultMap) throws AtsException{

		if(defaultMap!=null&&defaultMap.getMapid()!=null&&defaultMap.getMapid()>0){
				mapDao.updateByHql("delete from com.cdk.ats.web.pojo.hbm.Mappoint t where t.mapid=?  ", new Object[]{defaultMap.getMapid()});
			int count= mapDao.updateByHql("delete from  com.cdk.ats.web.pojo.hbm.Map m  where m.mapid=?", new Object[]{defaultMap.getMapid()});
			if(count<1){
				throw new AtsException("设置失败，您需要删除的数据已经不存在了！");
			}
		}else {
			throw new AtsException("数据异常，无法删除，请重试！");
			
		}
		
		
	
		
	}
	
	
	/***
	 * 
	 * 描述：查询所有的设备以及他们 的设备输入端口
	 * @createBy dingkai
	 * @createDate 2014-1-5
	 * @lastUpdate 2014-1-5
	 * @return
	 * @throws IOException
	 */
	public List<TreeNode> findEquipmentAndPort() throws IOException {
			List<TreeNode> fristNodes=new ArrayList<TreeNode>();			
			List<Table10> first=getMapDao().findObjectsByHql(SqlConfig.SQL("ats.userset.first.all"));
			if(first!=null)
			for(int h=0;h<first.size();h++){
				TreeNode ftt=new TreeNode();
				int field3=first.get(h).getField3();
				ftt.setParentid("root_001");
				ftt.setId("first_"+field3);
				ftt.setText(first.get(h).getField121());	
				//设置二层设备
				List<Table10> t10s = getMapDao().findObjectsByHql(SqlConfig.SQL("ats.userset.second.by"), new Object[] {field3});
				
				List<TreeNode> seconds=new ArrayList<TreeNode>();
				if(t10s!=null)
				for (int i = 0; i < t10s.size(); i++) {
					TreeNode stt=new TreeNode();
					stt.setId("second_"+t10s.get(i).getField1());
					int field4=t10s.get(i).getField4();
					int field10=t10s.get(i).getField10();
					stt.setParentid(ftt.getId());
					stt.setText(t10s.get(i).getField121());	
					stt.setLeaf(false);
					/**
					 * 输入 
					 */
					List<TreeNode> ports=new ArrayList<TreeNode>();
					ports.add(new TreeNode(stt.getId(),"port_"+field3+"_"+field4+"_1_1", t10s.get(i).getField105()!=null?t10s.get(i).getField105():"输入端口1", true));
					ports.add(new TreeNode(stt.getId(),"port_"+field3+"_"+field4+"_1_2", t10s.get(i).getField106()!=null?t10s.get(i).getField106():"输入端口2", true));
					ports.add(new TreeNode(stt.getId(),"port_"+field3+"_"+field4+"_1_3", t10s.get(i).getField107()!=null?t10s.get(i).getField107():"输入端口3", true));
					ports.add(new TreeNode(stt.getId(),"port_"+field3+"_"+field4+"_1_4", t10s.get(i).getField108()!=null?t10s.get(i).getField108():"输入端口4", true));
					ports.add(new TreeNode(stt.getId(),"port_"+field3+"_"+field4+"_1_5", t10s.get(i).getField109()!=null?t10s.get(i).getField109():"输入端口5", true));
					ports.add(new TreeNode(stt.getId(),"port_"+field3+"_"+field4+"_1_6", t10s.get(i).getField110()!=null?t10s.get(i).getField110():"输入端口6", true));
					ports.add(new TreeNode(stt.getId(),"port_"+field3+"_"+field4+"_1_7", t10s.get(i).getField111()!=null?t10s.get(i).getField111():"输入端口7", true));
					ports.add(new TreeNode(stt.getId(),"port_"+field3+"_"+field4+"_1_8", t10s.get(i).getField112()!=null?t10s.get(i).getField112():"输入端口8", true));
					/**
					 * 输出
					 */
					ports.add(new TreeNode(stt.getId(),"port_"+field3+"_"+field4+"_2_1", t10s.get(i).getField113()!=null?t10s.get(i).getField113():"输出端口1", true));
					ports.add(new TreeNode(stt.getId(),"port_"+field3+"_"+field4+"_2_2", t10s.get(i).getField114()!=null?t10s.get(i).getField114():"输出端口2", true));
					ports.add(new TreeNode(stt.getId(),"port_"+field3+"_"+field4+"_2_3", t10s.get(i).getField115()!=null?t10s.get(i).getField115():"输出端口3", true));
					ports.add(new TreeNode(stt.getId(),"port_"+field3+"_"+field4+"_2_4", t10s.get(i).getField116()!=null?t10s.get(i).getField116():"输出端口4", true));
					ports.add(new TreeNode(stt.getId(),"port_"+field3+"_"+field4+"_2_5", t10s.get(i).getField117()!=null?t10s.get(i).getField117():"输出端口5", true));
					ports.add(new TreeNode(stt.getId(),"port_"+field3+"_"+field4+"_2_6", t10s.get(i).getField118()!=null?t10s.get(i).getField118():"输出端口6", true));
					ports.add(new TreeNode(stt.getId(),"port_"+field3+"_"+field4+"_2_7", t10s.get(i).getField119()!=null?t10s.get(i).getField119():"输出端口7", true));
					ports.add(new TreeNode(stt.getId(),"port_"+field3+"_"+field4+"_2_8", t10s.get(i).getField120()!=null?t10s.get(i).getField120():"输出端口8", true));
					
					stt.setChildren(ports);
					seconds.add(stt);					
				}	
				ftt.setChildren(seconds);
				fristNodes.add(ftt);				
			}
		return fristNodes;
	}
	public BaseDao getMapDao() {
		return mapDao;
	}

	public void setMapDao(BaseDao mapDao) {
		this.mapDao = mapDao;
	}
	
	
}
