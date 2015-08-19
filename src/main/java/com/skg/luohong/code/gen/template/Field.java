package com.skg.luohong.code.gen.template;

import com.skg.luohong.code.gen.utils.StringUtils;

/**
 * 使用一个类来包装这些信息
 * 数据库字段名，java属性名，字段对应的数据库类型
 * 
 * @author 骆宏
 * @date 2015-08-15 10:25
 * */
public class Field{
	public final String name;  //对应的java字段名
	public final String column;  //数据库字段名
	public final String type;  //数据库类型
	public final String firstUpperName;  //强name首字母大写

	/**
	 * 类型目前只是支持int,String,Date三种，后期完善了进行扩展
	 * 
	 * @param name 字段名
	 * @param type 字段类型
	 * 
	 * */
	public Field(String name, String column, String type){
		if(name == null){
			throw new IllegalArgumentException("name can't be null");
		}

		this.name = StringUtils.toJavaProperties(name);
		this.firstUpperName = this.name.substring(0, 1).toUpperCase() + this.name.substring(1);
        
		this.column = column;
		//数据类型
		if(type.equalsIgnoreCase("VARCHAR")){				
			this.type = "String";
		}else if(type.contains("INT")){
			this.type = "Integer";
		}else if(type.contains("DATE")){
			this.type = "Date";
		}else{
			this.type = "String";
		}
	}

	@Override
	public String toString(){
		return name + " " + type + " " + firstUpperName;
	}

	public String getName(){
		return name;
	}
	public String getType(){
		return type;
	}
	public String getFirstUpperName(){
		return firstUpperName;
	}
	public String getColumn(){
		return column;
	}
}
