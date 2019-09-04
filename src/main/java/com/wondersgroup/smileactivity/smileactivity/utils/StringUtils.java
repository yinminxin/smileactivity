package com.wondersgroup.smileactivity.smileactivity.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Random;

public class StringUtils {
	
	public static int getRandomNumber(){
		
		return (int)((Math.random()*9+1)*100000);
	} 

	/**
	 * 生成订单号
	 * @param appId
	 * @return
	 */
	public static String getOrderNo(String appId){
		StringBuilder buff = new StringBuilder(appId);
		Calendar cal = Calendar.getInstance();
		int number = getRandomNumber();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		buff.append(year);
		if(month<10){
			buff.append("0"+month);
		}else{
			buff.append(month);
		}
		if(day<10){
			buff.append("0"+day);
		}else{
			buff.append(day);
		}
		if(hour<10){
			buff.append("0"+hour);
		}else{
			buff.append(hour);
		}
		if(min<10){
			buff.append("0"+min);
		}else{
			buff.append(min);
		}
		buff.append(getRandomNumber());
		return buff.toString();
	}
	
	/**
	 * 
	* @description: 对象为空转为空字符串
	* @param obj
	* @return String   
	* @author HANBO 
	* @date 2016年5月17日上午9:19:41
	 */
	public static String  convertStr(Object obj){
		if(obj == null){
			return "";
		}else{
			return obj.toString();
		}
	}
	
	/**
	 * 判断对象是否是null
	 *
	 * @param obj
	 * @return true false
	 */
	public static boolean isNull(Object obj){
		return obj == null ? true : false;
	}
	/**
	 * 检查字符串是否为空
	 *
	 * @param checkStr
	 *            检查的字符串
	 * @return true false
	 */
	public static boolean isBlank(Object checkStr){
		if ("".equals(checkStr)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 检查字符串 是否为空字符串 或者是null
	 *
	 * @param checkStr
	 *            检查的字符串
	 * @return true false
	 */
	public static boolean isBlankOrNull(Object checkStr){
		if (isNull(checkStr) || isBlank(checkStr)) {
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * 在前补0
	 */
	public static String pad0Front(String src,int totalLength){
		StringBuffer sb = new StringBuffer();
		int count = totalLength - src.length();
		for (int i = 0; i < count; i++) {
			sb.append("0");
		}
		sb.append(src);
		return sb.toString();
	}
	
	
	/**  
	* @description: 产生6位随机码
	* @return String   
	* @author HANBO 
	* @date 2016年5月20日上午10:40:39
	*/ 
	public static String randomOf6Str(){

		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i <6; i++) {
			buffer.append(random.nextInt(9));
		}
		return buffer.toString();
	}

    public static String byteToString(byte[] data){
        String dataString=null;
        try {
            dataString=new String(data,"ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return dataString;
    }

	/**
	 * /?:@&=+$,#特殊字符,进行URL编码处理
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String URLEncoderStr(String str) throws UnsupportedEncodingException{
		String content = URLEncoder.encode(str, "utf-8");
		return content;
	}

	public static void main(String[] args) {
		System.out.println(getOrderNo(""));
	}
	
}