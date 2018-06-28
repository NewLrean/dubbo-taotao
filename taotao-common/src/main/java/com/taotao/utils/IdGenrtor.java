package com.taotao.utils;

import java.math.BigInteger;
import java.util.Random;

public class IdGenrtor {
	public static String getGUID(){
		String upperCase = new BigInteger(165,new Random()).toString(36).toUpperCase();
		String str=upperCase+"_";
		return str;
	}
	
	
	public static Long getItemID(){
		//取当前事件的长整形包括毫秒
		long millis = System.currentTimeMillis();
		Random random=new Random();
		//2位随机数
		int end3=random.nextInt(22);
		//不足3位补0
		String itemID=millis+String.format("%03d", end3);
		return new Long(itemID);
		
	}
}
