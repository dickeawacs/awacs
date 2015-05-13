package com.cdk.ats.web.action.result;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;

import com.cdk.ats.udp.exception.AtsException;
import com.cdk.ats.udp.process.ActionContext;
import com.cdk.ats.udp.process.ActionParams;
import com.cdk.ats.udp.reset.ResetContext;
import com.cdk.ats.web.utils.AjaxResult;
/****
 * 用于 查询处理结果
 * @author cdk
 *
 */
public class ResultAction {

	private String key;
	

	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	/***
	 * 获取结果，根据 流水号 
	 * @return
	 * @throws IOException
	 */
	public String result() throws IOException{
		ActionParams ar=new ActionParams();
		try{
		if(key!=null&&NumberUtils.isNumber(key)){			
			ActionParams apm=	ActionContext.getValues(NumberUtils.createInteger(key));
			if(apm!=null){
				if(apm.getChildrens()!=null&&!apm.getChildrens().isEmpty()){
					///数据组的处理方式 
						boolean end=true;
						StringBuffer sb=new StringBuffer();
						Map<Integer,Boolean> keys=apm.getChildrens();				
						int ks=keys.size(),count=0,trueCount=0;
						//判断父级的所有子级是否都已经完成响应
						for (Iterator<Integer> child = keys.keySet().iterator() ; child.hasNext();) {
							Integer tempkey = (Integer) child.next();
							ActionParams tempParam=ActionContext.getValues(tempkey);
							if(tempParam!=null){
								count++;
								if(tempParam.getCode().equals(900)){
									trueCount++;
									sb.append("|").append(tempParam.getMessage());
								}else if(tempParam.getCode().equals(901)){
									sb.append("|").append(tempParam.getMessage());
								}else {
									//如果在命令包参数包中只要有一个code==300（等待状态），则跳出，视为命令没有处理完成，需要前台继续等
									end=false;
									break;
								}							
							}						
						}
						 //如果总命令数减去 有效命令数等于 总命令数，则说明所有命令已经失败了
						  if(ks-count==ks){
							sb.append(" 丢失所有命令包，共 "+(ks)+"条");
							ar.setCode(901);
							ar.setMessage(sb.toString());
						  }else if(end){//如果命令包判断全部通过，则说明 命令已经处理完成
							     //如果总的命令处理数 不等于 命令包组的 总的子命令数，则说明命令有丢失 
								if(count!=ks) sb.append("丢失命令包 "+(ks-count)+"条");
								//n个中，只要有一个是成功的，我们认成功。因为一个成功了，也会执行数据库修改，同时还可能需要刷新界面数据
								if(trueCount>0)ar.setCode(900);
								else ar.setCode(901);
								
								ar.setMessage(sb.toString());
								
						}else 
						{
							ar.setCode(300);
						}
						sb=null;
					// 经过上面的命令处理，如果发现code不为300,则说明 命令处理完成了，这里需要清楚缓存的命令数据
					if(ar.getCode()!=300){
						for (Iterator<Integer> child = keys.keySet().iterator() ; child.hasNext();) {
							Integer tempkey = (Integer) child.next();
							ActionContext.distoryParam(tempkey);
						}
						ActionContext.distoryParam(NumberUtils.createInteger(key));
					}
					
				}else {
					//单数据的处理方式 
					ar.setCode(apm.getCode());					 
					ar.setMessage(apm.getMessage());
					if(ar.getCode()!=300){
						ActionContext.distoryParam(NumberUtils.createInteger(key));
					}
				}
			}else ar.isFailed("操作失败！数据缓存异常,缓存数据不存在,key="+key); 
		}else {
			ar.isFailed("操作失败！参数异常,key="+key);
		}
		}catch (Exception e) {
			e.printStackTrace();
			ar.setCode(901);
			ar.isFailed(e.getMessage());
		}finally{
			ar.writeToJsonString();
			//若是成功响应了，就要销毁缓存区的数据
			//if(ar.getCode()==900)ActionContext.distoryParam(NumberUtils.createInteger(key));
		}
		return null;
	}
	
	
	
	
	/***
	 * 读取重置信息
	 * @return
	 * @throws IOException
	 */
	public String readResetInfo()throws IOException{
		AjaxResult ar=new AjaxResult();
		try{
		if(key!=null&&NumberUtils.isNumber(key)){	
			ar=ResetContext.getResetInfos(NumberUtils.createInteger(key));		
			ar.isSuccess();
			ar.stateWaiting();
		}else {
			throw new AtsException("请求数据异常,key="+key);
		}
		}catch (AtsException e) {
			ar.isFailed(e.getMessage());
		}catch (Exception e) {
			ar.isFailed(e.getMessage());
		}finally{
			ar.writeToJsonString(); 
		}
		return null;
	
	}
}
