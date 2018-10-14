package com.wbhz.flightsys.component.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换器
 * @author Administrator
 *
 */
public class DateUtil {
	/**
	 * 给定日期和格式，转换成相应的字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2Str(Date date,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return  sdf.format(date);
	}
	/**
	 * 给定一个日期格式的字符串和格式，转换成对应的Date对象
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date str2Date(String dateStr,String format) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException("日期格式字符串错误"+dateStr);
		}
		return date ;
	}
}