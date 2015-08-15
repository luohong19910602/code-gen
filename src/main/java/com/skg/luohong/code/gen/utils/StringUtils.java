package com.skg.luohong.code.gen.utils;

/**
 * 将数据库字段变成java属性
 * 这里面将数据库字段转化为java 属性
 * 这里面默认数据库字段使用_分割单词，末尾为_
 * 比如：user_tel_ --> userTel, ship_address_ shipAddress
 * 
 * @author 骆宏
 * @date 2015-08-12 23:28
 * */
public class StringUtils {

	/**
	 * 将数据库字段变成java属性
	 * 这里面将数据库字段转化为java 属性
	 * 这里面默认数据库字段使用_分割单词，末尾为_
	 * 比如：user_tel_ --> userTel, ship_address_ shipAddress
	 * 
	 * */
	public static String toJavaProperties(String columnName){
		String javaName = null;
		
		//去掉末尾的_
		if(columnName.endsWith("_")){
			javaName = columnName.substring(0, columnName.length()-1);
		}else{
			javaName = columnName;
		}
		
		//去掉_，并且将相邻的下一个字符换成大写
		while(javaName.contains("_")){				
			int index = javaName.indexOf("_");
			String t = javaName.charAt(index+1) + "";
			t = t.toUpperCase();
			javaName = javaName.substring(0, index) + t + javaName.substring(index+2);
		}

		return javaName;
	}
}
