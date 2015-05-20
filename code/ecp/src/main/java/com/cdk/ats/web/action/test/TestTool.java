package com.cdk.ats.web.action.test;

import java.io.IOException;

import com.cdk.ats.udp.transmitter.TransmitterContext;
import com.cdk.ats.web.utils.AjaxResult;

public class TestTool {

	public String page(){
		
		return "page";
	}
	public String clearSequnce() throws IOException{
		AjaxResult ar=new AjaxResult();
		try{
			TransmitterContext.clearSequnce();
			ar.isSuccess().setMessage("重置成功！");
		}catch (Exception e) {
			ar.isFailed("重置失败");
		}finally{
			ar.writeToJsonString();
		}
		return null;
	}
	
	
}
