package com.skg.luohong.code.gen.template;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.skg.luohong.code.gen.utils.FreemarkUtils;
import com.skg.luohong.code.gen.utils.JdbcUtils;
import com.skg.luohong.code.gen.utils.StringUtils;
import com.skg.luohong.code.gen.utils.XMlUtils;
import com.skg.luohong.code.gen.utils.XMlUtils.GenFile;

/**
 * 代码生成器的实现类，这里面使用freemarker来充当代码生成器
 * @author 骆宏
 * @date 2015-08-11 21:49
 * */
public class FreemarkGenCode implements IGenCode {

	private String workspace;  //工作目录
	private String system;  //系统
	private String module;  //模块
	
	public void genCode() {
		String workspace = XMlUtils.getWorkSpace();
		String system = XMlUtils.getSystem();
        String module = XMlUtils.getModule();
        
        this.workspace = workspace;
        this.system = system;
        this.module = module;
        
        
        if(this.workspace == null){
        	throw new IllegalArgumentException("workspace can't be null");
        }
        if(this.system == null){
        	throw new IllegalArgumentException("system can't be null");
        }
        if(this.module == null){
        	throw new IllegalArgumentException("module can't be null");
        }
        
        List<XMlUtils.GenFile> genFiles = XMlUtils.genFiles();
        
        for(GenFile genFile: genFiles){
        	String table = genFile.key;
        	boolean override = genFile.override;
        	
        	List<String> options = genFile.genOptions;
        	
        	if(options.contains("controller")){
        		genController(table, override);
        	}
        	if(options.contains("service")){
        		genService(table, override);
        	}
        	if(options.contains("dao")){
        		genDao(table, override);
        	}if(options.contains("entity")){
        		genEntity(table, override);
        	}
        	if(options.contains("view")){
        		genView(table, override);
        	}
        	if(options.contains("test")){
        		genTest(table, override);
        	}
        }
	}
	
	private void genView(String table, boolean override) {
		// TODO Auto-generated method stub
		
	}

	private void genTest(String table, boolean override) {
		// TODO Auto-generated method stub
		
	}

	private void genDao(String table, boolean override) {
		// TODO Auto-generated method stub
		
	}

	private void genService(String table, boolean override) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 生成实体
	 * 生成前，会判断是否需要覆盖
	 * 
	 * @param table 表名
	 * @param override 是否覆盖
	 * */
	private void genEntity(String table, boolean override) {
		//系统，模块，实体名字等信息
		Map<String, Object> datas = new HashMap<String, Object>();
		datas.put("system", system);
		datas.put("module", module);
		datas.put("author", "骆宏");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		datas.put("date", sdf.format(new Date()));
		
		//实体名
		String temp = StringUtils.toJavaProperties(table);
		String entityName = temp.substring(0, 1).toUpperCase() + temp.substring(1) + "Entity"; //先不做细节处理
		datas.put("entity", entityName);
		
		//属性
		List<Field> fields = new ArrayList<FreemarkGenCode.Field>();
		try {
			List<String> columns = JdbcUtils.columns(table);
		    if(columns != null){
		    	for(String column: columns){
		    		String[] col = column.split(" ");  //将字段拆分
		    		String name = col[0];
		    		String type = col[1];

		    		Field field = new Field(name, type);
		    		
		    		System.out.println(field);
		    		fields.add(field);
		    		//根据数据类型来构建实体
		    	}
		    }
		    
		    //生成代码
		    FreemarkUtils.initFreeMarker("C:\\workspace_current\\code-gen");
		    
		    datas.put("fields", fields);
		    FreemarkUtils.createFile(datas, 
		    		"src/main/resources/template/Entity.ftl", "E:\\" + entityName + ".java");
		    
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void genController(String table, boolean override) {
		// TODO Auto-generated method stub
		
	}

	public static class Field{
		public final String name;
		public final String type;
		public final String firstUpperName;  //强name首字母大写
		
		/**
		 * 类型目前只是支持int,String,Date三种，后期完善了进行扩展
		 * 
		 * @param name 字段名
		 * @param type 字段类型
		 * 
		 * */
		public Field(String name, String type){
			if(name == null){
				throw new IllegalArgumentException("name can't be null");
			}
			
			this.name = StringUtils.toJavaProperties(name);
			this.firstUpperName = name.substring(0, 1).toUpperCase() + name.substring(1);
			
			//数据类型
			if(type.equalsIgnoreCase("VARCHAR")){				
				this.type = "String";
			}else if(type.contains("INT")){
				this.type = "int";
			}else if(type.contains("DATE")){
				this.type = "Date";
			}else{
				this.type = "String";
			}
		}
		
		@Override
		public String toString(){
			return name + " " + type;
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
	}
}
