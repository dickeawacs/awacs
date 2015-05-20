package com.cdk.ats.web.action.query;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.cdk.ats.web.biz.QueryBiz;
import com.cdk.ats.web.dao.EventRecordDAO;
import com.cdk.ats.web.dao.Table10DAO;
import com.cdk.ats.web.pojo.hbm.Table10;
import com.cdk.ats.web.utils.AjaxResult;
import com.cdk.ats.web.utils.PageBean;
import com.cdk.ats.web.utils.WriteExcel;
import common.cdk.config.files.sqlconfig.SqlConfig;



/***
 * 数据查询接口，它主要为前台提供事件数据的查询等
 * @author happ
 *
 */
public class DataAction {
	private static Logger logger = Logger.getLogger(DataAction.class);
	private EventRecordDAO recordDao;
	private Table10DAO table10DAO;
	private Long eventMaxId;
	/***
	 * 查询事件table4的数据
	 * @return
	 * @throws IOException
	 */
	public String queryT4() throws IOException{
		AjaxResult ar = new AjaxResult();
		try {
			PageBean pagebean=new PageBean(ServletActionContext.getRequest(),ar);
			//PageBean pagebean=new PageBean(ServletActionContext.getRequest(),ar);
			ar= recordDao.findCurrentEvents(ar,eventMaxId);
			ar.isSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			ar.isFailed(e.getMessage());
			ar.setExceptionMessage(e.getMessage());
		}finally {
			ar.writeToJsonString();
		}
		return null;
	}
	/***
	 * 
	 * 描述：
	 * @createBy dingkai
	 * @createDate 2014-2-25
	 * @lastUpdate 2014-2-25
	 * @return
	 */
	private String  getCurrentEvent(PageBean pagebean){
		StringBuffer condition=new StringBuffer();
		if(!pagebean.getParams().isEmpty()&&pagebean.getParams().containsKey("eventMaxId".toUpperCase())){
			//pagebean.getRa().setLimit(100);
			condition.append(" where ").append("   eventId>").append(pagebean.getParams().get("eventMaxId".toUpperCase()));
		}else{			
			pagebean.getRa().setLimit(0);
		}
		return condition.toString();
	}
	/***
	 * 查询table10的数据
	 * @throws IOException
	 */
	public void queryT10() throws IOException{
		AjaxResult ar = new AjaxResult();
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String f2 = request.getParameter("field2");
			int field2;
			if(NumberUtils.isNumber(f2)){
				field2 = NumberUtils.createInteger(f2);
			}else{
				throw new Exception();
			}
			List<Table10> t10 = table10DAO.findAll(SqlConfig.SQL("ats.table10.query"),new Object[]{field2});
			ar.setArray(new QueryBiz().t10FormatTree(t10));
			ar.setTotal(t10!=null?t10.size():0);
			ar.isSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			ar.isFailed(e.getMessage());
			ar.setExceptionMessage(e.getMessage());
		}finally{
			ar.writeToJsonArrayString();
		}
	}
	
	 

	 

	public Table10DAO getTable10DAO() {
		return table10DAO;
	}

	public void setTable10DAO(Table10DAO table10dao) {
		table10DAO = table10dao;
	}

	public EventRecordDAO getRecordDao() {
		return recordDao;
	}

	public void setRecordDao(EventRecordDAO recordDao) {
		this.recordDao = recordDao;
	}
	
	private InputStream stream;
	private String fileName;
 
	/***
	 * 
	 * 描述：  导出事件 
	 * @createBy dingkai
	 * @createDate 2014-2-22
	 * @lastUpdate 2014-2-22
	 * @return
	 * @throws IOException
	 */
	public String exportExcel() throws IOException {
		fileName= "Event_Log_"+new Date(System.currentTimeMillis());
		WriteExcel writer = new WriteExcel();
		
		AjaxResult ar = new AjaxResult();
		PageBean pagebean=new PageBean(ServletActionContext.getRequest(),ar);
		//ar= recordDao.findEventView(ar,getCondition(pagebean));
		String[] heads=new String[]{"序号","事件分类","事件时间","事件描述","终端描述","处理人","处理时间 ","备注"};
		writer.writeContent("事件记录日志", recordDao.findExportData(ar, getCondition(pagebean)),heads);
		HSSFWorkbook workbook = writer.getBook();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		workbook.write(output);
		byte[] ba = output.toByteArray();
		stream = new ByteArrayInputStream(ba);
		output.flush();

		output.close();
		return "success";
	}
	/***
	 * 
	 * 描述： 查看事件的历史数据
	 * @createBy dingkai
	 * @createDate 2014-2-22
	 * @lastUpdate 2014-2-22
	 * @return
	 * @throws IOException
	 */
	public String openHistory() throws IOException{
		return "success";
	}
	
	/***
	 * 
	 * 描述： 查看事件的历史数据
	 * @createBy dingkai
	 * @createDate 2014-2-22
	 * @lastUpdate 2014-2-22
	 * @return
	 * @throws IOException
	 */
	public String history() throws IOException{
		AjaxResult ar = new AjaxResult();
		try {
			PageBean pagebean=new PageBean(ServletActionContext.getRequest(),ar);
			ar= recordDao.findEventView(ar,getCondition(pagebean));
			ar.isSuccess();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			ar.isFailed(e.getMessage());
			ar.setExceptionMessage(e.getMessage());
		}finally {
			ar.writeToJsonString();
		}
		return null;
	}
	private String getCondition(PageBean pagebean){
		StringBuffer  condition=new StringBuffer();
		Map<String, Object> params=pagebean.getParams();
		if(!params.isEmpty()){
			condition.append(" where 1=1 ");
			if(params.containsKey("eventTypeChange".toUpperCase())){
				String v=params.get("eventTypeChange".toUpperCase()).toString();
				if(StringUtils.isNotBlank(v)&&!v.equals("0")){
				condition.append(" and ( eventType=").append(v).append(")  ");
				}
				
			}
			//日期区间
			if(params.containsKey("event_begin".toUpperCase())&&params.containsKey("event_end".toUpperCase())){
				condition.append(" and  (eventTime between ").append(" DATE_FORMAT('").
				append(params.get("event_begin".toUpperCase())).append("','%Y-%m-%d') and DATE_FORMAT('")
				.append(params.get("event_end".toUpperCase())).append("','%Y-%m-%d'))");
				
				
			}else if(params.containsKey("event_begin".toUpperCase())){//开始日期
				condition.append(" and( eventTime >= ").append("DATE_FORMAT('").
				append(params.get("event_begin".toUpperCase())).append("','%Y-%m-%d') )");
			}else if(params.containsKey("event_end".toUpperCase())){//结束日期
				condition.append(" and ( eventTime <= ").append("DATE_FORMAT('")
				.append(params.get("event_end".toUpperCase())).append("','%Y-%m-%d'))");
			}
			if(params.containsKey("event_exec".toUpperCase())){
				condition.append(" and  eventTime like '%").append(params.get("event_exec".toUpperCase())).append("%' ");
			}
			 
			if(params.containsKey("exportAll".toUpperCase())){
				if(params.get("exportAll".toUpperCase()).equals("true"))
				pagebean.getRa().setStart(-1);
				
			}
			
			
		}
		
		return condition.toString();	
	}

	public InputStream getStream() {
		return stream;
	}

	public String getFileName() {
		return fileName;
	}
	public void setEventMaxId(Long eventMaxId) {
		this.eventMaxId = eventMaxId;
	}
	
}
