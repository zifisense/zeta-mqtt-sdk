package com.zifisense.zeta.mqtt.api.util;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Date;
import org.junit.Test;

public class CommonUtilsTest {

	@Test
	public void testBytesToHexStringSucc1() {
		byte[] beforeByte = { 77, 84, 73, 122, 78, 68, 85, 50, 78, 122, 103, 53, 89, 87, 74, 106, 90, 71, 86, 109, 77,69, 113, 118, 77, 67, 73, 76, 51, 101, 110, 115, 107, 78, 106, 83, 112, 104, 79, 102, 89, 106, 104,73 };
		String afterHex = "4d54497a4e4455324e7a673559574a6a5a47566d4d4571764d43494c33656e736b4e6a5370684f66596a6849";
		String hexString = CommonUtils.bytesToHexString(beforeByte);
		System.out.printf("%s,before:%s,after:%s\n", Thread.currentThread().getStackTrace()[1].getMethodName(),Arrays.toString(beforeByte), hexString);

		assertEquals(afterHex, hexString);
	}
	@Test
	public void testBytesToHexStringSucc2() {
		byte[] beforeByte = { 1 };
		String afterHex = "01";
		String hexString = CommonUtils.bytesToHexString(beforeByte);
		System.out.printf("%s,before:%s,after:%s\n", Thread.currentThread().getStackTrace()[1].getMethodName(),Arrays.toString(beforeByte), hexString);

		assertEquals(afterHex, hexString);
	}
	@Test
	public void testBytesToHexStringFail() {
		byte[] beforeByte = null;
		String afterHex = null;
		String hexString = CommonUtils.bytesToHexString(beforeByte);
		System.out.printf("%s,before:%s,after:%s\n", Thread.currentThread().getStackTrace()[1].getMethodName(),Arrays.toString(beforeByte), hexString);

		assertEquals(afterHex, hexString);
	}
	@Test
	public void testDateString2LongSucc() {
		String stringDate = "2020-08-27 14:26:00";
		long longDate = 1598509560;
		long resultLong = 0L;
		try {
			resultLong = CommonUtils.dateString2Long(stringDate);
			System.out.printf("%s,before:%s,after:%s\n", Thread.currentThread().getStackTrace()[1].getMethodName(),stringDate, resultLong);
			assertEquals(longDate, resultLong);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testDateString2LongFail() {
		String stringDate = "2020-08-27 14:26";
		long longDate = 1598509560;
		long resultLong = 0L;
		try {
			resultLong = CommonUtils.dateString2Long(stringDate);
			System.out.printf("%s,before:%s,after:%s\n", Thread.currentThread().getStackTrace()[1].getMethodName(),stringDate, resultLong);
			assertEquals(longDate, resultLong);
		} catch (Exception e) {
			System.out.printf("%s,before:%s,after:%s\n", Thread.currentThread().getStackTrace()[1].getMethodName(),stringDate, e.getMessage());
			assertEquals(true, e.getMessage().contains("yyyy-MM-dd HH:mm:ss"));
		}
	}

	@Test
	public void convertDateToLong() {
		Date date = new Date();
		long longDate = System.currentTimeMillis() / 1000;
		long resultLong = CommonUtils.convertDateToLong(date);
		System.out.printf("%s,before:%s,after:%s\n", Thread.currentThread().getStackTrace()[1].getMethodName(), date, resultLong);

		assertEquals(longDate, resultLong);
	}

	@Test
	public void convertStringToDateSucc() {
		String stringDate = "2020-08-27 14:26:00";
		long longDate = 1598509560;
		Date resultDate = null;
		try {
			resultDate = CommonUtils.convertStringToDate(stringDate);
			System.out.printf("%s,before:%s,after:%s\n", Thread.currentThread().getStackTrace()[1].getMethodName(),stringDate, resultDate);
			assertEquals(longDate, resultDate.getTime() / 1000);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void convertStringToDateFail() {
		String stringDate = "2020/08/27 14:6:00";
		try {
			CommonUtils.convertStringToDate(stringDate);
		} catch (Exception e) {
			System.out.printf("%s,before:%s,after:%s\n", Thread.currentThread().getStackTrace()[1].getMethodName(),stringDate, e.getMessage());
			assertEquals(true, e.getMessage().contains("yyyy-MM-dd HH:mm:ss"));
		}
	}

	@Test
	public void getRandomString() {
		int lengthInt = 16;
		String resultString = CommonUtils.getRandomString(lengthInt);
		System.out.printf("%s,before:%s,after:%s\n", Thread.currentThread().getStackTrace()[1].getMethodName(),lengthInt, resultString);

		assertEquals(lengthInt, resultString.length());
	}
	@Test
	public void string2byteSucc() {
		String beforeString = "abcd";
		byte[] afterArr = {97, 98, 99, 100};
		byte[] resultArr = {};
		try {
			resultArr = CommonUtils.string2byte(beforeString,"utf8");
			System.out.printf("%s,before:%s,after:%s\n", Thread.currentThread().getStackTrace()[1].getMethodName(),beforeString, Arrays.toString(resultArr));
			assertArrayEquals(afterArr, resultArr);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void string2byteFail() {
		String beforeString = "abcd";
		String chartSet = "ukf";
		try {
			CommonUtils.string2byte(beforeString,chartSet);
		} catch (Exception e) {
			System.out.printf("%s,before:%s,after:%s\n", Thread.currentThread().getStackTrace()[1].getMethodName(),chartSet, e.getMessage());
			assertEquals("The Character Encoding is not supported:"+chartSet, e.getMessage());
			return;
		}
		fail("Simulated exception, no exception reported！");
	}
	
}
