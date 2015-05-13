package com.cdk.ats.web.utils;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

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
	 * @return yyyy-MM-dd hh:mm:ss
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
	 * @return yyyy-MM-dd 00:00:00
	 * @throws ParseException 
	 */
	public static Date getTodayStart()   {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00");
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
	/****
	 * 获取如下格式的时间
	 * yyyy/MM/dd/00:00
	 * 
	 * @return
	 */
	public static String getTimeStr()   {
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd/HH:mm");
		String date2 = df.format(new Date());
	/*	java.util.Date nowtime =null;
		try {
			nowtime= df.parse(date2);
		} catch (ParseException e) {
			logger.info(e);
		}
		//nowtime= new java.sql.Timestamp(nowtime.getTime());
		return nowtime;*/
		return date2;
	}
	/**
	 * 
	 * @return yyyy-MM-dd 23:59:59
	 * @throws ParseException 
	 */
	public static Date getTodayEnd() throws ParseException   {
		return switchToyMdhms(getsystimeyMd().toString()+" 23:59:59");
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
	public static String getFormatYYMMDD(){
		Date date=new Date();
		if(date==null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date);
	}
	/**
	 * 返回当前的年
	 * @return
	 */
	public static Integer year(){
		GregorianCalendar ggcalendar=new GregorianCalendar(Locale.CHINA);
		return ggcalendar.get(Calendar.YEAR);
	}
	/**
	 * 返回当前月份
	 * @return
	 */
	public static  Integer month(){
		GregorianCalendar ggcalendar=new GregorianCalendar(Locale.CHINA);
		return ggcalendar.get(Calendar.MONTH)+1;
	}
	
	/**
	 * 返回当前是几号
	 * @return
	 */
	public static  Integer date(){
		GregorianCalendar ggcalendar=new GregorianCalendar(Locale.CHINA);
		return ggcalendar.get(Calendar.DATE);
	}
	/***
	 * 获取时间 (24小时制)
	 * @return
	 */
	public static Integer hour(){
		GregorianCalendar ggcalendar=new GregorianCalendar(Locale.CHINA);
		System.out.println(ggcalendar.get(Calendar.HOUR_OF_DAY));
		return ggcalendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 返回当前是周几，从周日开始计数
	 * @return
	 */
	public static  Integer day(){
		GregorianCalendar ggcalendar=new GregorianCalendar(Locale.CHINA);
		return ggcalendar.get(Calendar.DAY_OF_WEEK);
	}
	public static  Timestamp[] getThisWeekDayForTimestamp(String format) throws ParseException {
		Calendar mc=Calendar.getInstance(Locale.CHINA);
		Timestamp [] weekday=new Timestamp[5];
		mc.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY );
		
		weekday[0]=new Timestamp(mc.getTimeInMillis());
		
 		mc.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY );
		weekday[1]=new Timestamp(mc.getTimeInMillis());
		
		mc.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY );
		weekday[2]=new Timestamp(mc.getTimeInMillis());
		
		mc.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY );
		weekday[3]=new Timestamp(mc.getTimeInMillis());
		
		mc.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY );
		weekday[4]=new Timestamp(mc.getTimeInMillis());
		 
		
		return weekday;
		
	}
	public static  Date[] getThisWeekDayForDate(String format) throws ParseException {
		Calendar mc=Calendar.getInstance(Locale.CHINA);
		Date [] weekday=new Date[5];
		SimpleDateFormat f=new SimpleDateFormat(format,Locale.CHINA);
		mc.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY );
		weekday[0]=f.parse(f.format(mc.getTime()));
		
		mc.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY );
		weekday[1]=f.parse(f.format(mc.getTime()));
		
		mc.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY );
		weekday[2]=f.parse(f.format(mc.getTime()));
		
		mc.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY );
		weekday[3]=f.parse(f.format(mc.getTime()));
		
		mc.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY );
		weekday[4]=f.parse(f.format(mc.getTime()));
		 
		
		return weekday;
		
	}
	
	public static  String[] getThisWeekDay(String format) {
		Calendar mc=Calendar.getInstance(Locale.CHINA);
		String [] weekday=new String[5];
		SimpleDateFormat f=new SimpleDateFormat(format,Locale.CHINA);
		mc.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY );
		weekday[0]=f.format(mc.getTime());
		
		mc.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY );
		weekday[1]=f.format(mc.getTime());
		
		mc.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY );
		weekday[2]=f.format(mc.getTime());
		
		mc.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY );
		weekday[3]=f.format(mc.getTime());
		
		mc.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY );
		weekday[4]=f.format(mc.getTime());
		 
		
		return weekday;
		
	}
	
	public static  String[] getThisWeekDay(String format,Date date) {
		Calendar mc=Calendar.getInstance(Locale.CHINA);
		mc.setTime(date);
		String [] weekday=new String[5];
		SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
		mc.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY );
		weekday[0]=f.format(mc.getTime());
		
		mc.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY );
		weekday[1]=f.format(mc.getTime());
		
		mc.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY );
		weekday[2]=f.format(mc.getTime());
		
		mc.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY );
		weekday[3]=f.format(mc.getTime());
		
		mc.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY );
		weekday[4]=f.format(mc.getTime());
 
		
		return weekday;
		
	}
	
	public static void main(String[] args) throws ParseException {
//		Time.getsystimeyMd();
//	Time.getsystimeyMdhms();
//	Time.switchToyMd("2009-12-10");
//	Time.switchToyMdhms("2008-02-02 13:25:03");
//		System.out.println(Time.year().toString()+Time.month().toString()+Time.date().toString());
//	System.out.println(Time.ggcalendar.get(Calendar.DAY_OF_WEEK));
		Timestamp a=new Timestamp(new Date().getTime());
		SimpleDateFormat df = new SimpleDateFormat("E MM月dd日");
		String date = df.format(a);
		System.out.println(date);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(a.getTime());
		cal.add(Calendar.DATE, 1);
		System.out.println(df.format(cal.getTime()));
		cal.add(Calendar.DATE, 1);
		System.out.println(df.format(cal.getTime()));
		cal.add(Calendar.DATE, 1);
		System.out.println(df.format(cal.getTime()));
		cal.add(Calendar.DATE, 1);
		System.out.println(df.format(cal.getTime()));
		cal.add(Calendar.DATE, 1);
		System.out.println(df.format(cal.getTime()));
		cal.add(Calendar.DATE, 1);
		System.out.println(df.format(cal.getTime()));
		System.out.println((new SimpleDateFormat("E")).format(cal.getTime()));
	}
	
	static public void maind(String x[]){   
		//Time.getThisWeekDay();
	//	System.out.println(Time.ggcalendar.get(Calendar.HOUR));
		//System.out.println(Time.day());
	}
	/*	SimpleDateFormat f=new SimpleDateFormat("M月d日 E",Locale.CHINA);
		Time.ggcalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY );
		System.out.println(f.format(Time.ggcalendar.getTime()));

 Calendar c=Calendar.getInstance(Locale.CHINA);  //当前时间，貌似多余，其实是为了所有可能的系统一致 
 c.setTimeInMillis(System.currentTimeMillis()); 
 System.out.println("当前时间:"+f.format(c.getTime()));
c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); 
System.out.println("周一时间:"+f.format(c.getTime())); } */
	//=========== 改到后天测了一下 输出 当前时间:2011年8月31日 星期三 12时32分40秒 周一时间:2011年8月29日 星期一 12时32分40秒
		
}
