package com.qtone.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 
 * 日期处理工具类.
 */

public class DateUtils {

	private static final transient Log log = LogFactory.getLog(DateUtils.class);

	
	public static final String DATE_FORMAT = "yyyy-M-d";

	/**
	 * 缺省的日期格式
	 */
	public static final String DAFAULT_DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * 默认日期类型格试.
	 */
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(DAFAULT_DATE_FORMAT);

	/**
	 * 缺省的日期时间格式
	 */

	public static final String DAFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 时间格式
	 */

	private static String DATETIME_FORMAT = DAFAULT_DATETIME_FORMAT;

	private static SimpleDateFormat datetimeFormat = new SimpleDateFormat(DATETIME_FORMAT);

	public static final SimpleDateFormat dateTimeFormater = new SimpleDateFormat(DATETIME_FORMAT);
	public static final SimpleDateFormat dateFormater = new SimpleDateFormat(DAFAULT_DATE_FORMAT);

	
	/**
	 * 缺省的时间格式
	 */

	public static final String DAFAULT_TIME_FORMAT = "HH:mm:ss";

	/**
	 * 时间格式
	 */

	private static String TIME_FORMAT = DAFAULT_TIME_FORMAT;

	private static SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);

	private DateUtils()

	{

		// 私用构造主法.因为此类是工具类.

	}

	/**
	 * 获取格式化实例.
	 * 
	 * @param pattern
	 *            如果为空使用DAFAULT_DATE_FORMAT
	 * @return
	 */
	public static SimpleDateFormat getFormatInstance(String pattern) {
		if (StringUtils.isBlank(pattern)) {
			pattern = DAFAULT_DATE_FORMAT;
		}
		return new SimpleDateFormat(pattern);
	}

	/**
	 * 格式化Calendar
	 * 
	 * @param calendar
	 * @return
	 */

	public static String formatCalendar(Calendar calendar) {

		if (calendar == null){
			return "";
		}
		return dateFormat.format(calendar.getTime());

	}

	public static String formatDateTime(Date d) {
		if (d == null) {
			return "";
		}
		return datetimeFormat.format(d);
	}

	public static String formatDate(Date d) {

		if (d == null) {
			return "";
		}
		return dateFormat.format(d);
	}

	/**
	 * 格式化时间
	 * @return
	 */

	public static String formatTime(Date d) {
		if (d == null){
			return "";
		}
		return timeFormat.format(d);
	}

	/**
	 * 格式化整数型日期
	 * @param intDate
	 * @return
	 */
	public static String formatIntDate(Integer intDate) {
		if (intDate == null){
			return "";
		}
		Calendar c = newCalendar(intDate);
		return formatCalendar(c);

	}

	/**
	 * 根据指定格式化来格式日期.
	 * @param date  待格式化的日期.
	 * @param pattern 格式化样式或分格,如yyMMddHHmmss
	 * @return 字符串型日期.
	 */
	public static String formatDate(Date date, String pattern) {
		if (date == null){
			return "";
		}
		if (StringUtils.isBlank(pattern)){
			return formatDate(date);
		}
		SimpleDateFormat simpleDateFormat = null;

		try{
			simpleDateFormat = new SimpleDateFormat(pattern);
		} catch (Exception e){
			e.printStackTrace();
			return formatDate(date);
		}
		return simpleDateFormat.format(date);
	}

	/**
	 * 取得Integer型的当前日期
	 * @return
	 */
	public static Integer getIntNow() {
		return getIntDate(getNow());
	}

	/**
	 * 取得Integer型的当前日期
	 * @return
	 */
	public static Integer getIntToday() {
		return getIntDate(getNow());
	}

	/**
	 * 取得Integer型的当前年份
	 * @return
	 */
	public static Integer getIntYearNow() {
		Calendar c = Calendar.getInstance();

		int year = c.get(Calendar.YEAR);

		return year;
	}

	/**
	 * 取得Integer型的当前月份
	 * @return
	 */
	public static Integer getIntMonthNow() {
		Calendar c = Calendar.getInstance();

		int month = c.get(Calendar.MONTH) + 1;

		return month;
	}

	public static String getStringToday() {
		return getIntDate(getNow()) + "";
	}

	/**
	 * 根据年月日获取整型日期
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Integer getIntDate(int year, int month, int day) {
		return getIntDate(newCalendar(year, month, day));
	}

	/**
	 * 某年月的第一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static Integer getFirstDayOfMonth(int year, int month) {
		return getIntDate(newCalendar(year, month, 1));
	}

	/**
	 * 某年月的第一天
	 * @return
	 */
	public static Integer getFirstDayOfThisMonth() {
		Integer year = DateUtils.getIntYearNow();

		Integer month = DateUtils.getIntMonthNow();

		return getIntDate(newCalendar(year, month, 1));
	}

	/**
	 * 某年月的第一天
	 * @param date
	 * @return
	 * @time:-- 上午::
	 */
	public static Integer getFistDayOfMonth(Date date) {

		Integer intDate = getIntDate(date);

		int year = intDate / 10000;

		int month = intDate % 10000 / 100;

		return getIntDate(newCalendar(year, month, 1));
	}

	/**
	 * 某年月的最后一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static Integer getLastDayOfMonth(int year, int month) {
		return intDateSub(getIntDate(newCalendar(year, month + 1, 1)), 1);
	}

	/**
	 * 根据Calendar获取整型年份
	 * @param c
	 * @return
	 */
	public static Integer getIntYear(Calendar c) {
		int year = c.get(Calendar.YEAR);

		return year;
	}

	/**
	 * 根据Calendar获取整型日期
	 * @param c
	 * @return
	 */
	public static Integer getIntDate(Calendar c) {

		int year = c.get(Calendar.YEAR);

		int month = c.get(Calendar.MONTH) + 1;

		int day = c.get(Calendar.DAY_OF_MONTH);

		return year * 10000 + month * 100 + day;
	}

	public static Integer getIntHour(Date d){
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		return hour;
	}

	/**
	 * 根据Date获取整型年份
	 * @param d
	 * @return
	 */
	public static Integer getIntYear(Date d) {

		if (d == null){
			return null;
		}

		Calendar c = Calendar.getInstance();

		c.setTime(d);

		return getIntYear(c);
	}

	/**
	 * 根据Date获取整型日期
	 * @param d
	 * @return
	 */
	public static Integer getIntDate(Date d) {

		if (d == null){
			return null;
		}

		Calendar c = Calendar.getInstance();

		c.setTime(d);

		return getIntDate(c);
	}

	/**
	 * 根据Integer获取Date日期
	 * @param n
	 * @return
	 */
	public static Date getDate(Integer n) {

		if (n == null){
			return null;
		}

		Calendar c = Calendar.getInstance();

		c.set(n / 10000, n / 100 % 100 - 1, n % 100);

		return c.getTime();
	}

	public static Date getDate(String date) {
		return getDate(date, null);
	}
	
	/**
	 * 格式化成指定格式
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date getDate(String date, String pattern) {
		
		if (StringUtils.isBlank(date)){
			return null;
		}
		try{
			if (date.contains("/")){
				date = date.replaceAll("/", "-");
			}
			if(StringUtils.isBlank(pattern)){
				pattern = DAFAULT_DATE_FORMAT;
			}
			return getFormatInstance(pattern).parse(date);
			
		} catch (ParseException e){
			log.error("解析[" + date + "]错误！", e);
			return null;
		}
	}
	
	

	/**
	 * 根据年份Integer获取Date日期
	 * @param year
	 * @return
	 */
	public static Date getFirstDayOfYear(Integer year) {

		if (year == null){
			return null;
		}
		Calendar c = Calendar.getInstance();

		c.set(year, 1, 1);

		return c.getTime();
	}

	/**
	 * 根据年月日生成Calendar
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Calendar newCalendar(int year, int month, int day) {

		Calendar ret = Calendar.getInstance();

		if (year < 100){
			year = 2000 + year;
		}

		ret.set(year, month - 1, day);

		return ret;
	}

	/**
	 * 根据整型日期生成Calendar
	 * @param date
	 * @return
	 */
	public static Calendar newCalendar(int date) {

		int year = date / 10000;

		int month = (date % 10000) / 100;

		int day = date % 100;

		Calendar ret = Calendar.getInstance();

		ret.set(year, month - 1, day);

		return ret;
	}

	/**
	 * 取得Date型的当前日期
	 * @return
	 */
	public static Date getNow() {
		return new Date();
	}
	
	/**
	 * 取得Date型的当前日期
	 * @return
	 */
	public static Date getToday() {
		return DateUtils.getDate(DateUtils.getIntToday());
	}

	/**
	 * 整数型日期的加法
	 * @param date
	 * @param days
	 * @return
	 */
	public static Integer intDateAdd(int date, int days) {

		int year = date / 10000;

		int month = (date % 10000) / 100;

		int day = date % 100;

		day += days;

		return getIntDate(year, month, day);
	}

	/**
	 * 整数型日期的减法
	 * @param date
	 * @param days
	 * @return
	 */
	public static Integer intDateSub(int date, int days) {
		return intDateAdd(date, -days);
	}

	/**
	 * 计算两个整型日期之间的天数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Integer daysBetweenDate(Integer startDate, Integer endDate) {

		if (startDate == null || endDate == null){
			return null;
		}

		Calendar c1 = newCalendar(startDate);

		Calendar c2 = newCalendar(endDate);

		Long lg = (c2.getTimeInMillis() - c1.getTimeInMillis()) / 1000 / 60 / 60 / 24;

		return lg.intValue();
	}

	/**
	 * 计算两个整型日期之间的天数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Integer daysBetweenDate(Date startDate, Date endDate) {

		if (startDate == null || endDate == null){
			return null;
		}

		Long interval = endDate.getTime() - startDate.getTime();

		interval = interval / (24 * 60 * 60 * 1000);

		return interval.intValue();
	}

	/**
	 * 取得当前日期.
	 * @return 当前日期,字符串类型.
	 */
	public static String getStringDate() {
		return getStringDate(DateUtils.getNow());
	}

	/**
	 * 根据calendar产生字符串型日期
	 * @param d
	 * @return eg:
	 */
	public static String getStringDate(Date d) {
		if (d == null){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(d);
	}

	public static String getFormatStringDate(Date d) {

		if (d == null){
			return "";
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");

		return sdf.format(d);
	}
 
	

	 public static String betweenDate(Date startDate, Date endDate) {

	        if (startDate == null || endDate == null){
	            return "";
	        }

	        Long interval = endDate.getTime() - startDate.getTime();

	        interval = interval / 1000;
	        long day1=interval/(24*3600);
	        long hour1=interval%(24*3600)/3600;
	        long minute1=interval%3600/60;
	        long second1=interval%60/60;
	        StringBuilder s = new StringBuilder();
	        if(day1>0){
	            s.append(day1).append(" 天 ");
	        }else{
	            if(hour1>0){
	                s.append(hour1).append(" 小时 ");
	            }
	            if(minute1>0){
	                s.append(minute1).append(" 分钟 ");
	            }
	            if(second1>0){
	                s.append(second1).append(" 秒 ");
	            }
	        }
	        return s.toString();
	    }

    public static Timestamp getCurrentTimestamp(){
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        return new Timestamp(now.getTime());
    }
    
    /**
     * 格式化日期为yy-mm-dd格式的日期
     * @param date 待格式化日期
     * @return
     */
    public static Date formateDate(Date date){ 	
    	if(date == null){
    		return null;
    	}
    	String dateStr = dateFormater.format(date);
    	Date result = DateUtils.getDate(dateStr);
    	return result;
    }
    public static String getFiveDateAfter(){
    	long intervalMilli = new Date().getTime()-(24 * 60 * 60 * 1000*5);
		return new SimpleDateFormat("yyyyMMdd").format(intervalMilli);
    }
  /**
     * 获取当前月第一天
     * @author huangguangxi
     * 2016-3-17下午1:42:09
     * @return
     */
    public static String getMonthFirstDay(){
    	Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 0);
    	c.set(Calendar.DAY_OF_MONTH,1);
    	return dateFormat.format(c.getTime());
    }
    /**
     * 获取当前月最后一天
     * @author huangguangxi
     * 2016-3-17下午1:42:09
     * @return
     */
    public static String getMonthLastDay(){
    	 Calendar c = Calendar.getInstance();    
    	 c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH)); 
    	 return dateFormat.format(c.getTime());
    }
    /**
     * 判断日期大小
     * @author huangguangxi
     * 2016-8-25下午3:46:46
     * @param DATE1 委托单审批时间
     * @param DATE2 默认时间2016-05-01
     * @return
     */
    public static int compareDate(String DATE1, String DATE2) {
        try {
            Date dt1 = dateFormat.parse(DATE1);
            Date dt2 = dateFormat.parse(DATE2);
            if (dt1.getTime() >= dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 0;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return 1;
            } 
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

/**
	 * 验证时间字符串格式输入是否正确(yyyy-MM-dd HH:mm:ss)
	 * huangguangxi
	 * Date : 2016-7-27 
	 * @param timeStr
	 * @return
	 */
	public static boolean valiDateTimeWithLongFormat(String timeStr) {
		String format = "((19|20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01]) "
				+ "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";
		Pattern pattern = Pattern.compile(format);
		Matcher matcher = pattern.matcher(timeStr);
		if (matcher.matches()) {
			pattern = Pattern.compile("(\\d{4})-(\\d+)-(\\d+).*");
			matcher = pattern.matcher(timeStr);
			if (matcher.matches()) {
				int y = Integer.valueOf(matcher.group(1));
				int m = Integer.valueOf(matcher.group(2));
				int d = Integer.valueOf(matcher.group(3));
				if (d > 28) {
					Calendar c = Calendar.getInstance();
					c.set(y, m-1, 1);
					int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					return (lastDay >= d);
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 验证日期字符串格式输入是否正确(yyyy-MM-dd)
	 * huangguangxi
	 * Date : 2016-7-27
	 * @param timeStr
	 * @return
	 */
	public static boolean valiDateTimeWithShortFormat(String timeStr) {
		String format = "((19|20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";
		Pattern pattern = Pattern.compile(format);
		Matcher matcher = pattern.matcher(timeStr);
		if (matcher.matches()) {
			pattern = Pattern.compile("(\\d{4})-(\\d+)-(\\d+).*");
			matcher = pattern.matcher(timeStr);
			if (matcher.matches()) {
				int y = Integer.valueOf(matcher.group(1));
				int m = Integer.valueOf(matcher.group(2));
				int d = Integer.valueOf(matcher.group(3));
				if (d > 28) {
					Calendar c = Calendar.getInstance();
					c.set(y, m-1, 1);
					int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					return (lastDay >= d);
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 获取与当前日期相差${dayNums}的天数的日期
	 * @param dayNums 天数 把日期往后增加N天 负数往前移动
	 * @return
	 * @author huangguangxi
	 * @date 2016-9-20
	 */
	public static Date addDate(int dayNums){
		Date date=new Date();//取时间 
		Calendar   calendar   =   new   GregorianCalendar(); 
		calendar.setTime(date); 
		calendar.add(calendar.DATE,dayNums);
		date=calendar.getTime();
		return date;
	}

	/**
	 * create by: zhangpk
	 * description: 获取与指定时间相差${dayNums}的天数的日期
	 * create time: 14:52 2021/6/1
	 * @Param: null
	 * @return
	 */
	public static Date addDate(Date date, int dayNums){
		Calendar   calendar   =   new   GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE,dayNums);
		date=calendar.getTime();
		return date;
	}
	public static Date addMonth(Date date, int monty){
		Calendar   calendar   =   new   GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.MONTH,monty);
		date=calendar.getTime();
		return date;
	}

	public static Date addYear(Date date, int yearNum){
		Calendar   calendar   =   new   GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.YEAR,yearNum);
		date=calendar.getTime();
		return date;
	}

	/**
	 * create by: zhangpk
	 * description: 获取与指定时间相差${minuteNum}的分钟的日期
	 * create time: 16:07 2021/6/22
	 * @Param: null
	 * @return
	 */
	public static Date addMinute(Date date, int minuteNum){
		Calendar   calendar   =   new   GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.MINUTE,minuteNum);
		date=calendar.getTime();
		return date;
	}

	/**
     * 判断开始，结束时间是否满足相差分钟数
     * 大于返回 true
     * @author mjzhang
     * 2016-9-21下午6:23:00
     * @param dateStart
     * @param dateEnd
     * @param nums 相差分钟数
     * @return
     */
    public static boolean compareDateMin(Date dateStart ,Date dateEnd,int nums){
    	if(dateStart==null){
    		dateStart = new Date();
    	}
    	int comDate = (int) ((dateEnd.getTime()-dateStart.getTime())/1000/60);
    	if(comDate>=nums){
    		return true;
    	} else {
    		return false;
    	}
    }

	/**
	 * 获得当天零时零分零秒
	 * @return
	 */

	public static Date getStartTimeByDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
    /**
     * 获取指定日期加减天数
     * @author huangguangxi
     * 2016-10-17下午3:17:35
     * @param date
     * @param dayNum
     * @return
     */
    public static String compareDateNums(Date date,int dayNums){
		Calendar   calendar   =   new   GregorianCalendar(); 
		calendar.setTime(date); 
		calendar.add(calendar.DATE,dayNums);
		date=calendar.getTime();
		return formatDateTime(date);
    }
    /**
     * 获取指定日期加减天数
     * @author huangguangxi
     * 2016-10-17下午3:17:35
     * @param date
     * @param dayNum
     * @return
     */
    public static Date compareDateNum(Date date,int dayNums){
		Calendar   calendar   =   new   GregorianCalendar(); 
		calendar.setTime(date); 
		calendar.add(calendar.DATE,dayNums);
		date=calendar.getTime();
		return date;
    }
    /**
     * 判断当前时间是否大于指定时间
     * @param date
     * @return
     */
    public static boolean compareDateMin(Date date){
    	if(date==null){
    		return false;
    	}
    	//指定日期小于于当前时间返回true
    	long comDate = (long) (System.currentTimeMillis() - date.getTime());
    	if(comDate>=0){
    		return true;
    	} else {
    		return false;
    	}
    }

	/**
	 * 获取指定日期的零点时间
	 * @param d
	 * @return
	 */
	public static Date getBeginDate(Date d) {

		if (d == null){
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date zero = calendar.getTime();
		return zero;
	}


    
    public static void main(String[] args) throws ParseException {
//    	Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-04-01 00:00:00");
//    	System.out.println(DateUtils.compareDateMin(date));
//		String time = "2021-11-17";
//		System.out.println(valiDateTimeWithShortFormat(time));
		System.out.println(getIntHour(new Date()));
	}
    /**
     * 判断结束时间是否大于开始时间
     * @param dateStart
     * @param dateEnd
     * @return 大于 返回 true 
     */
    public static boolean compareDateMin(Date dateStart ,Date dateEnd){
    	long comDate = dateEnd.getTime()-dateStart.getTime();
    	if(comDate >= 0){
    		return true;
    	} else {
    		return false;
    	}
    }

    /**
     * 取得Integer型的当前日
     * @author renfei
     * @date 2017年10月12日 上午10:42:36
     * @return
     */
	public static Integer getIntDayNow() {
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DAY_OF_MONTH);
		return day;
	}

	//当前时间的前一个月时间
	public static String getBeforeMonth(){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, -1);
		c.add(Calendar.DAY_OF_MONTH, +1);

		Date m = c.getTime();
		String mon = datetimeFormat.format(m);

		return mon;
	}

	/**
	 * 获取当前时间yyyy-mm-dd hh:mm:ss.ffff格式
	 * @author liuzhichao
	 * @date  2020/10/19 15:19
	 * @param []
	 * @return java.lang.String
	 */
	public static String getCurrentDateTime(){
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		System.out.println(ts.toString());
		return ts.toString();
	}

	public static String parseTimeMillis(Date date) throws Exception{
		if (date == null){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("ss");
		return sdf.format(date);
	}

}
