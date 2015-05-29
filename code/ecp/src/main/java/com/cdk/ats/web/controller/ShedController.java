package com.cdk.ats.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/***
 * 
 * @Title: ShedController.java
 * @Package com.cdk.ats.plugs.controller
 * @Description: 棚
 * @author 陈定凯
 * @date 2015年5月27日 上午12:17:47
 * @version V1.0
 */

@Controller
public class ShedController extends BaseController {

	/**
	 * 
	 * 
	 * @Description: 获取一个指定棚的所有相关信息，这里需要配置棚关系 表。棚与设备关系
	 * @author 陈定凯
	 * @date 2015年5月27日 上午1:20:00
	 * @param shedId
	 * @return
	 */
	@RequestMapping("/show/{shedId}")
	public Object queryShed(@RequestParam(defaultValue = "shedId") String shedId) {
		return null;
	}

	/***
	 * 
	 * 
	 * @Description: 获取指定的设备模拟量信息 ; 需要在设备信息中添加模拟量信息的缓存 。
	 * @author 陈定凯
	 * @date 2015年5月27日 上午1:22:41
	 */
	public void queryAQ() {
	}
}
