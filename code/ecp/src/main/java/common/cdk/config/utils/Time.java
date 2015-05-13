package common.cdk.config.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
 
public class Time {
public static 	Logger logger=Logger.getLogger(Time.class);

	/**
	 * 
	 * @param datestr
	 * @return yyyy-MM-dd hh:mm:ss  
	 * @throws ParseException
	 */
	public static Date switchToyMdhms(String datestr) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		java.util.Date nowtime=null;
		try {
			if(datestr.trim().length()<11)
			{
				datestr=datestr.trim()+" 00:00:00";
				
			}
			nowtime = df.parse(datestr);
			nowtime = new java.sql.Timestamp(nowtime.getTime());
		} catch (ParseException e) {
			logger.info(e);
		}
		
		return nowtime;
	}

	/**
	 * 
	 * @param datestr
	 * @return yyyy-MM-dd 
	 * @throws ParseException
	 */
	public static Date switchToyMd(String datestr) throws ParseException {
		Date nowtime=null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		nowtime = df.parse(datestr);
		nowtime = new java.sql.Date(nowtime.getTime());
		return nowtime;
	}

	/**
	 * 
	 * @return yyyy-MM-dd
	 * @throws ParseException 
	 */
	public static Date getsystimeyMd() throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date2 = df.format(new Date());
		java.util.Date nowtime =null;
		nowtime= df.parse(date2);
		nowtime= new java.sql.Date(nowtime.getTime());
		return nowtime;
	}

	/**
	 * 
	 * @return yyyy-MM-dd hh:mm:ss
	 * @throws ParseException 
	 */
	public static Date getsystimeyMdhms()   {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date2 = df.format(new Date());
		java.util.Date nowtime =null;
		try {
			nowtime= df.parse(date2);
		} catch (ParseException e) {
			logger.info(e);
		}
		nowtime= new java.sql.Timestamp(nowtime.getTime());
		return nowtime;
	}
	
	/**
	 *  
	 * @param date
	 * @return Stringyyyy-MM-dd
	 */
	public static String getFormatYYMMDD(Date date){
		if(date==null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	
	public static void main(String[] args) throws ParseException {
		Time.getsystimeyMd();
	Time.getsystimeyMdhms();
	Time.switchToyMd("2009-12-10");
	Time.switchToyMdhms("2008-02-02 13:25:03");
	}
}
