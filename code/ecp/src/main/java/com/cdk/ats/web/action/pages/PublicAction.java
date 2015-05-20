package com.cdk.ats.web.action.pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cdk.ats.udp.cache.AtsEquipmentCache;
import com.cdk.ats.udp.exception.AtsException;
import com.cdk.ats.udp.heart.HeartContext;
import com.cdk.ats.udp.process.ActionContext;
import com.cdk.ats.udp.process.ActionParams;
import com.cdk.ats.udp.process.ActionProcess;
import com.cdk.ats.udp.process.ActionReady;
import com.cdk.ats.udp.reset.ResetContext;
import com.cdk.ats.udp.transmitter.TransmitterContext;
import com.cdk.ats.udp.utils.CommandTools;
import com.cdk.ats.udp.utils.Constant;
import com.cdk.ats.web.action.login.UserInfo;
import com.cdk.ats.web.action.login.UserLoginAction;
import com.cdk.ats.web.dao.BaseDao;
import com.cdk.ats.web.pojo.hbm.Map;
import com.cdk.ats.web.pojo.hbm.Mappoint;
import com.cdk.ats.web.pojo.hbm.Table10;
import com.cdk.ats.web.utils.AjaxResult;

public class PublicAction {
	private static Logger logger = Logger.getLogger(PublicAction.class);

	private BaseDao baseDao;

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	/***
	 * 打开设备状态说明界面
	 * 
	 * @return
	 */
	public String stateHelp() {
		return "stateHelp";
	}

	private Integer baseAction(Table10 vo,UserInfo userinfo,int action ) throws AtsException{
		
		ActionParams params = new ActionParams();
		params.setCode(300);
		params.setOneP(userinfo.getAddres());
		params.setOnePT(1);
		params.setUserCode(userinfo.getUserCode());
		params.setUserID(userinfo.getId());
		params.setComand0(action);// 0x01布防
		ActionProcess ap = new ActionProcess();
		ap.setParams(params);// 设置参数
		ActionReady aReady = null;
		if(userinfo.getLevel().equals(Constant.ROLE_ADMIN)){
			aReady=ap.ox20_0x04();
		}else if(userinfo.getLevel().equals(Constant.ROLE_USER)){
			aReady=ap.ox20_0x02();
		}else throw new AtsException("失败！当前用户无权进行此操作.");
		Constant.getInstance().bindTarget(vo, aReady);
		 
		List<ActionReady> alist=new ArrayList<ActionReady>();
		alist.add(aReady);
		TransmitterContext.pushGroupDatagram(alist);
		ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
		return aReady.getKey();
	} 
	
	private void baseActions(AjaxResult ar,int action) throws AtsException{
		UserInfo userinfo = UserLoginAction.getLoginUser();
		if(userinfo.getAddres()==null)throw new AtsException("系统管理员无法执行此操作!");
		Table10 vo=AtsEquipmentCache.getEquiReadOnly(userinfo.getAddres(),0);
		if( !HeartContext.hasHeart(CommandTools.handKeyFormat(vo.getField3(),0)))throw new AtsException("设备可能已经掉线,无法操作！");
		if(ResetContext.isReset(vo.getField3())&&ResetContext.connectioning(vo.getField3())){
			throw new AtsException("用户所属设备处在编程状态，无法操作！");
		}
		if (vo== null)
			throw new AtsException("用户所属设备不存在，无法操作！");
		if(vo.getField130().equals(Constant.EQUIPMENT_STATUS_LOSE_VAL))
			throw new AtsException("设备已掉线，无法操作！");
		if(!vo.getField130().equals(Constant.EQUIPMENT_STATUS_NORMAL_VAL))
			throw new AtsException("用户所属设备状态异常，无法操作！");
		//判断是设备是否已经被屏蔽或是已经掉线
		Constant.checkUsed(vo.getField3(),0);
		ar.setReturnVal(this.baseAction(vo, userinfo, action));
		ar.isSuccess();
		ar.stateWaiting();
		
	}
	/***
	 * 布防操作
	 * 
	 * @return
	 * @throws IOException
	 */
	public String bufang() throws IOException {
		AjaxResult ar = new AjaxResult();
		try {
			/*
			UserInfo userinfo = UserLoginAction.getLoginUser();
			Table10 vo=AtsEquipmentCache.getEquiReadOnly(userinfo.getAddres(),0);
			if (vo== null)
				throw new AtsException("用户所属设备不存在，无法操作！");
			if(!vo.getField130().equals(Constant.EQUIPMENT_STATUS_NORMAL_VAL))
				throw new AtsException("用户所属设备暂时不可用，无法操作！");
			this.baseAction(vo, userinfo, 1);
			ar.isSuccess();
			ar.stateWaiting();
			*/
			
			baseActions(ar,1);
		} catch (AtsException e) {
			logger.error(e.getMessage(), e);
			ar.isFailed(e.getMessage());
		} catch (Exception e) {
			logger.error("布防操作失败！", e);
			ar.isFailed("布防操作失败！");
		} finally {
			ar.writeToJsonString();
		}
		return null;
	}

	/***
	 * 撤防操作
	 * 
	 * @return
	 * @throws IOException
	 */
	public String cefang() throws IOException {
		AjaxResult ar = new AjaxResult();
		try {
			baseActions(ar,2);
/*
			UserInfo userinfo = UserLoginAction.getLoginUser();
			Table10 vo=AtsEquipmentCache.getEquiReadOnly(userinfo.getAddres(),0);
			if(ResetContext.ResetOpen&&ResetContext.connectioning(vo.getField3())){
				
				throw new AtsException("用户所属设备处在编程状态，无法操作！");
			}
			if (vo== null)
				throw new AtsException("用户所属设备不存在，无法操作！");
			if(!vo.getField130().equals(Constant.EQUIPMENT_STATUS_NORMAL_VAL))
				throw new AtsException("用户所属设备暂时不可用，无法操作！");
			this.baseAction(vo, userinfo, 2);
			ar.isSuccess();
			ar.stateWaiting();*/
			
			/*
			UserInfo userinfo = UserLoginAction.getLoginUser();
			Table9 t9 = (Table9) baseDao.findOnlyByHql(SqlConfig
					.SQL("ats.table9.query.saveBeforQuery"),
					new Object[] { userinfo.getAddres() });
			if (t9 == null)
				throw new AtsException("用户布防所属设备无效，无法操作！");
			ActionParams params = new ActionParams();
			params.setCode(300);
			params.setOneP(userinfo.getAddres());
			params.setOnePT(userinfo.getPtype());
			params.setUserCode(userinfo.getUserCode());
			params.setUserID(userinfo.getId());
			params.setComand0(2);// 0x02撤防
			ActionProcess ap = new ActionProcess();
			ap.setParams(params);// 设置参数
			ActionReady aReady = ap.ox20_0x02();
			aReady.setTargetIp(t9.getField128());
			aReady.setTargetPort(t9.getField129());
			TransmitterContext.pushDatagram(aReady);
			ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
			ar.isSuccess();
			ar.stateWaiting();
		*/} catch (Exception e) {
			ar.isFailed(e.getMessage());
		} finally {
			ar.writeToJsonString();
		}
		return null;
	}

	/***
	 * 确认操作
	 * 
	 * @return
	 * @throws IOException
	 */
	public String chakan() throws IOException {
		AjaxResult ar = new AjaxResult();
		try {
			baseActions(ar,3);
			/*UserInfo userinfo = UserLoginAction.getLoginUser();
			Table10 vo=AtsEquipmentCache.getEquiReadOnly(userinfo.getAddres(),0);
			if (vo== null)
				throw new AtsException("用户所属设备不存在，无法操作！");
			if(!vo.getField130().equals(Constant.EQUIPMENT_STATUS_NORMAL_VAL))
				throw new AtsException("用户所属设备暂时不可用，无法操作！");
			this.baseAction(vo, userinfo, 2);
			ar.isSuccess();
			ar.stateWaiting();
			baseActions(ar,1);*/
			/*
			UserInfo userinfo = UserLoginAction.getLoginUser();
			Table9 t9 = (Table9) baseDao.findOnlyByHql(SqlConfig
					.SQL("ats.table9.query.saveBeforQuery"),
					new Object[] { userinfo.getAddres() });
			if (t9 == null)
				throw new AtsException("用户布防所属设备无效，无法操作！");

			ActionParams params = new ActionParams();
			params.setCode(300);
			params.setOneP(userinfo.getAddres());
			params.setOnePT(userinfo.getPtype());
			params.setUserCode(userinfo.getUserCode());
			params.setUserID(userinfo.getId());
			params.setComand0(3);// 0x03查看
			ActionProcess ap = new ActionProcess();
			ap.setParams(params);// 设置参数
			ActionReady aReady = ap.ox20_0x02();
			aReady.setTargetIp(t9.getField128());
			aReady.setTargetPort(t9.getField129());

			TransmitterContext.pushDatagram(aReady);
			ActionContext.putValues(aReady.getKey(), params);// 将数据保存至下传的缓存区
			ar.isSuccess();
			ar.stateWaiting();
		*/} catch (Exception e) {
			ar.isFailed(e.getMessage());
		} finally {
			ar.writeToJsonString();
		}
		return null;
	}

	/***
	 * 打开地图
	 * 
	 * @return
	 */
	public String openMap() {

		return "map";
	}

	/***
	 * 获取地图，以及地图上的点
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getMapPoint() throws IOException {
		AjaxResult ar = new AjaxResult();
		try {

			/**
			 * 查出所有可用地图
			 */
			List<Map> atsMaps = baseDao
					.findObjectsByHql("from com.cdk.ats.web.pojo.hbm.Map where isuse=1  ");
			if (atsMaps != null && atsMaps.size() > 0) {
				// atsMap.setPoints(baseDao.findObjectsByHql("from  com.cdk.ats.web.pojo.hbm.Mappoint where  mapid=?  and isshow=1 ",new
				// Object[]{atsMap.getMapid()}));
				ar.setArray(atsMaps);
				ar.isSuccess();
			}
		} catch (Exception e) {
			ar.isFailed(e.getMessage());
		} finally {
			ar.writeToJsonArrayString();
		}
		return null;
	}

	/***
	 * 根据 地图获取 地图的所有点
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getPoint() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		AjaxResult ar = new AjaxResult();
		try {
			String mapId = request.getParameter("mapId");
			if (mapId != null && NumberUtils.isNumber(mapId)) {
				List<Mappoint> points = baseDao
						.findObjectsByHql(
								"from  com.cdk.ats.web.pojo.hbm.Mappoint where  mapid=?  and isshow=1 ",
								new Object[] { NumberUtils.createInteger(mapId) });
				ar.setArray(points);
			} else {
				ar.isFailed("失败");
			}
		} catch (Exception e) {
			ar.isFailed(e.getMessage());

		} finally {
			ar.writeToJsonArrayString();
		}
		return null;
	}

	private List<Mappoint> mapPoints;

	public List<Mappoint> getMapPoints() {
		return mapPoints;
	}

	public void setMapPoints(List<Mappoint> mapPoints) {
		this.mapPoints = mapPoints;
	}

	/***
	 * 编辑地图
	 * 
	 * @return
	 * @throws IOException 
	 */
	public String editMap() throws IOException {
		AjaxResult ar = new AjaxResult();
		try {
			
			if (mapPoints != null && mapPoints.size() > 0) {
				baseDao.saveOrUpdateAll(mapPoints);
			}else{
				ar.isFailed();
			}
		} catch (Exception e) {
			ar.isFailed(e.getMessage());
		}finally{
			ar.writeToJsonArrayString();
		}
		return null;
	}
}
