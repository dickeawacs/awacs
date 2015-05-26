package com.cdk.ats.plugs.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdk.ats.plugs.biz.AnalogQuantityServices;

/***
 * 
* @Title: AnalogQuantityController.java 
* @Package com.cdk.ats.plugs.controller 
* @Description: 模拟量Controller
* @author 陈定凯 
* @date 2015年5月26日 下午11:36:10 
* @version V1.0
 */
@Service
@RequestMapping("/aqcfg")
public class AnalogQuantityController extends BaseController {

	@Resource
	private AnalogQuantityServices biz;
	
  /***
   * 
  * 
  * @Description: 获取所有可配置的模拟量信息集合。用于页面显示。
  * @author 陈定凯 
  * @date 2015年5月27日 上午1:16:50  
  * @return
   */
	@RequestMapping("/show")
	@ResponseBody
	public Object queryAnalogCfg(){
		
		return null;
	}
}
