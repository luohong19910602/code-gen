package com.skg.luohong.code.gen.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 
 * jdbc工具类
 * 这个工具类主要在代码生成器中使用
 * 
 * @author 骆宏
 * @date 2015-08-11 21:57
 */
public class JdbcUtils {

	private static String url = null;
	private static String username = null;
	private static String password = null;

	private static String driverClassName = "com.mysql.jdbc.Driver";

	/**
	 * 初始化数据库连接
	 * */
	static{
		Properties pros = PropertiesUtils.loadPropertyInstance("src/main/resources/conf", "jdbc.properties");

		if(pros != null){
			url = pros.getProperty("url");
			username = pros.getProperty("username");
			password = pros.getProperty("password");
			driverClassName = pros.getProperty("driverClassName");

			try {
				Class.forName(driverClassName);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}else{
			throw new RuntimeException("Can't find src/main/resources/conf/jdbc.properties");
		}
	}

	/**
	 * 
	 * 获取连接 
	 * @author sen
	 * @version 1.0,2015年3月18日
	 * @return
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 创建数据库表
	 * @param createTableSql 创建表的sql
	 * */
	public static void create(String createTableSql) throws SQLException{
		Connection conn = getConnection();
		Statement stat = conn.createStatement();
		stat.executeUpdate(createTableSql);
		stat.close();
		conn.close();
	}

	/**
	 * 
	 * 判断表是否存在 
	 * @author sen
	 * @version 1.0,2015年3月18日
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public static boolean isExist(String tableName) {
		Connection conn = getConnection() ;
		DatabaseMetaData meta;
		try {
			meta = conn.getMetaData();
			ResultSet rs = meta.getTables(null, null, tableName, null) ;
			return rs.next() ;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false ;
	}

	/**
	 * 
	 * 执行更新的sql语句 
	 * 该方法一般用于创建表
	 * @author sen
	 * @version 1.1,2015年3月19日
	 * @param sql
	 * @return
	 */
	public static int executeUpdate(String sql) {
		Connection conn = getConnection() ;
		PreparedStatement pre = null;
		try {
			pre = conn.prepareStatement(sql);
			return pre.executeUpdate() ;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				pre.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0 ;
	}

	/**
	 * 根据反射，输出表的结构信息
	 * 数据库表的设计规则：实体属性对应数据库的字段，在后面加入下划线
	 * 比如：User.username,User.password，对应的数据库字段为：username_,password_
	 * 如果有是多个单词，那么使用下划线来代替，比如：emailAddress，对应的数据库字段为：email_address_
	 * 
	 * List里面的内容为table的字段信息，结构如下所示
	 * 字段名 + " " + 字段类型 + " " + 字段类型大小 + " " + 精确度 + " " + 是否为空（如果为空，那么值为0；否则为1）
	 * 
	 * @param tableName
	 * @throws SQLException 
	 * */
	public static List<String> columns(String tableName) throws SQLException{
		DatabaseMetaData dbmd = getConnection().getMetaData();
		ResultSet tableRet = dbmd.getTables(null, "%", tableName,new String[]{"TABLE"}); 
        		
		List<String> tableInfoList = new ArrayList<String>();
		while(tableRet.next()) {
			String columnName; 
			String columnType; 
			
			ResultSet colRet = dbmd.getColumns(null,"%", tableRet.getString("TABLE_NAME"),"%"); 
			while(colRet.next()) { 
				columnName = colRet.getString("COLUMN_NAME"); 
				columnType = colRet.getString("TYPE_NAME"); 
				
				int datasize = colRet.getInt("COLUMN_SIZE"); 
				int digits = colRet.getInt("DECIMAL_DIGITS"); 
				int nullable = colRet.getInt("NULLABLE"); 
				
				tableInfoList.add(columnName + " " + columnType + " " + datasize + " " + digits + " " + nullable); 
			}
		}
		
		return tableInfoList;
	}

	public static void main(String[] args) throws SQLException {
		List<String> cols = columns("crm_days_user");
		for(String col: cols){
			System.out.println(col);
		}
	}
}
