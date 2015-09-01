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
	 * 数据库字段与java属性对应关系
	 * JDBC Type           Java Type  
     * CHAR                String  
     * VARCHAR             String  
     * LONGVARCHAR         String  
     * NUMERIC             java.math.BigDecimal  
     * DECIMAL             java.math.BigDecimal  
     * BIT                 boolean  
     * BOOLEAN             boolean  
     * TINYINT             byte  
     * SMALLINT            short  
     * INTEGER             int  
     * BIGINT              long  
     * REAL                float  
     * FLOAT               double  
     * DOUBLE              double  
     * BINARY              byte[]  
     * VARBINARY           byte[]  
     * LONGVARBINARY       byte[]  
     * DATE                java.sql.Date  
     * TIME                java.sql.Time  
     * TIMESTAMP           java.sql.Timestamp  
     * CLOB                Clob  
     * BLOB                Blob  
     * ARRAY               Array  
     * DISTINCT            mapping of underlying type  
     * STRUCT              Struct  
     * REF                 Ref  
     * DATALINK            java.net.URL[color=red][/color] 
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
		if(type.contains("CHAR")){  //CHAR VARCHAR LONGVARCHAR				
			this.type = "String";
		}else if(type.contains("INT")){  //TINYINT SMALLINT INTEGER BIGINT
			this.type = "Integer";
		}else if(type.equals("REAL")){
			this.type = "Float";
		}else if(type.equals("FLOAT")){
			this.type = "Double";
		}else if(type.equals("DOUBLE")){
			this.type = "Double";
		}else if(type.equals("DATE")){
			this.type = "Date";
		}else if(type.equals("TIME")){
			this.type = "Date";
		}else if(type.equals("TIMESTAMP")){
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
