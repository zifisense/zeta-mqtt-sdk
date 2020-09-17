package com.zifisense.zeta.mqtt.api.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.zifisense.zeta.mqtt.api.model.ZiFiException;

/**
 * 数据类型转换工具类
 * 
 * @Title: com.zifisense.zeta.mqtt.api.util.CommonUtils.java
 * @Description:
 * @author huangdg
 * @date 2020年8月28日
 */
public class CommonUtils {
	private CommonUtils() {
		
	}
	/** 
	 * 随机产生的字符串
	 */
	private static final String RANDOM_STRS = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static Random random = new Random();

	/**
	 * 8 Convert byte[] to hex
	 * string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
	 * 
	 * @param src byte[] data
	 * @return hex string
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
	/**
	 * String 转 byte[]
	 * @param content
	 * @return
	 * @throws ZiFiException
	 */
	public static byte[] string2byte(String content, String chartSet) throws ZiFiException{
		byte[] contentArr = {};
		try {
			contentArr = content.getBytes(chartSet);
		} catch (UnsupportedEncodingException e) {
			throw new ZiFiException("The Character Encoding is not supported:" + chartSet, e);
		}
		return contentArr;
	}
	/**
	 * 时间字符串格式转换为整型秒级时间戳
	 * @param dateString
	 * @return
	 * @throws ZiFiException
	 */
	public static Long dateString2Long(String dateString) throws ZiFiException{
		Date date = convertStringToDate(dateString);
		return convertDateToLong(date);
	}

	/**
	 * 时间格式转换为整型秒级时间戳
	 * @param date
	 * @return 返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的秒数
	 */
	public static Long convertDateToLong(Date date) {
		return date.getTime() / 1000;
	}

	/**
	 * 时间字符串转换为时间格式
	 * 
	 * @param dateString 格式：yyyy-MM-dd HH:mm:ss  短横杠分割，到秒
	 * @return
	 * @throws ZiFiException
	 */
	public static Date convertStringToDate(String dateString) throws ZiFiException {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.parse(dateString);
		} catch (Exception e) {
			throw new ZiFiException("a malformed date: \"yyyy-MM-dd HH:mm:ss\"");
		}
	}

	/**
	 * 获取指定长度随机字符串
	 * 
	 * @param lengthNum
	 * @return
	 */
	public static String getRandomString(int lengthNum) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < lengthNum; i++) {
			int number = random.nextInt(RANDOM_STRS.length());
			sb.append(RANDOM_STRS.charAt(number));
		}
		return sb.toString();
	}
}
