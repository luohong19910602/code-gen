package com.skg.luohong.code.gen.template;

import java.io.File;
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
	private String systemKey;  //系统的简称
	private String module;  //模块

	private boolean isInit;
	/**
	 * 初始化信息
	 * */
	public void init() {
		FreemarkUtils.initFreeMarker("C:\\workspace_current\\code-gen");
		isInit = true;
	}


	/**
	 * 生成代码
	 * */
	public void genCode() {
		if(!isInit){
			init();
		}
		
		String workspace = XMlUtils.getWorkSpace();
		String system = XMlUtils.getSystem();
		String module = XMlUtils.getModule();
		String systemKey = XMlUtils.getSystemKey();

		this.workspace = workspace;
		this.system = system;
		this.module = module;
		this.systemKey = systemKey;

		if(this.workspace == null){
			throw new IllegalArgumentException("workspace can't be null");
		}
		if(this.system == null){
			throw new IllegalArgumentException("system can't be null");
		}
		if(this.systemKey == null){
			throw new IllegalArgumentException("system key can't be null");
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
		//实体名
		String temp = StringUtils.toJavaProperties(table);
		String entityName = temp.substring(0, 1).toUpperCase() + temp.substring(1) + "Entity"; //先不做细节处理
		/**
		 * 将E:\workspace中的'\'替换为'/'
		 * */
		if(workspace.contains("\\")){
			workspace = workspace.replaceAll("\\\\", "/");
		}

		//根据配置信息，得到要生成Entity实体类的所在路径
		String outputPath = workspace + "/biz-root/" + system + "/src/main/java/com/skg/luohong/biz/" + systemKey + "/" + module + "/entity/";
		File outputPathDirectory = new File(outputPath);
		String outputFileName = null;

		//文件夹已经存在
		if(!outputPathDirectory.exists()){
			System.out.println("mk dir " + outputPath);
			outputPathDirectory.mkdirs();
		}
		outputFileName = outputPath + entityName + ".java";

		File outputFile = new File(outputFileName);

		//删除文件
		if(override && outputFile.exists()){
			System.out.println("delete file " + outputFileName);
			outputFile.delete();
		}

		//系统，模块，实体名字等信息
		Map<String, Object> datas = new HashMap<String, Object>();
		datas.put("system", systemKey);
		datas.put("module", module);
		datas.put("author", "骆宏");
		datas.put("entity", entityName);
		datas.put("table", table);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		datas.put("date", sdf.format(new Date()));


		//属性
		List<Field> fields = new ArrayList<FreemarkGenCode.Field>();
		try {
			List<String> columns = JdbcUtils.columns(table);
			
			//构建input的变量，这里的内容就不放在freemarker处理了
			StringBuilder nameBuilder = new StringBuilder();
			StringBuilder columnBuilder = new StringBuilder();
			
			//构建update的变量
			StringBuilder updateBuilder = new StringBuilder();
			
			if(columns != null){
				for(int i=0; i<columns.size(); i++){
					String column = columns.get(i);
					
					String[] col = column.split(" ");  //将字段拆分
					String name = col[0];
					String type = col[1];
                    
					
					Field field = new Field(name, name, type);
					
					if(i != columns.size() - 1){
						nameBuilder.append("#{" + field.name + "}, ");
						columnBuilder.append(name + ", ");
						
						//update部分id值不需要更新
						if(!name.equalsIgnoreCase("id_")){
							updateBuilder.append(name = "#{" + field.name + "}, ");
						}
					}else{
						nameBuilder.append("#{" + field.name + "}");
						columnBuilder.append(name);
						
						if(!name.equalsIgnoreCase("id_")){
							updateBuilder.append(name = "#{" + field.name + "}");
						}
					}
					fields.add(field);
					//根据数据类型来构建实体
				}
				
			}
			
			//处理update table set xxx=xxx where id_=#{id}
			updateBuilder.append(" where id_ = #{id}");
			
			datas.put("fields", fields);
			datas.put("names", nameBuilder.toString());
			datas.put("columns", columnBuilder.toString());
			datas.put("update", updateBuilder.toString());

			//生成代码
			FreemarkUtils.createFile(datas, 
					"src/main/resources/template/Entity.ftl", outputFileName);

			//已经完成了Entity的生成，接下来把mapper文件也生成
			genMapper(datas);
			genMapperClass(datas);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成mapper接口
	 * 这个方法由genEntity()调用
	 * @param datas
	 * */
	private void genMapperClass(Map<String, Object> datas){
		String outputPath = workspace + "/biz-root/" + system + "/src/main/java/com/skg/luohong/biz/" + systemKey + "/" + module + "/entity/";
		File outputPathDirectory = new File(outputPath);
		String outputFileName = null;

		//文件夹已经存在
		if(!outputPathDirectory.exists()){
			System.out.println("mk dir " + outputPath);
			outputPathDirectory.mkdirs();
		}
		
		String entity = (String) datas.get("entity");
		String mapperName = entity.replace("Entity", "") + "Mapper";
		datas.put("mapperName", mapperName);
		
		datas.put("firstLowwerMapperName", (entity.charAt(0)+ "").toLowerCase() + entity.substring(1));
		
		outputFileName = outputPath + mapperName + ".java";
		
		//生成代码
		FreemarkUtils.createFile(datas, 
				"src/main/resources/template/MapperClass.ftl", outputFileName);
	}
	/**
	 * 生成实体对应的Mapper.xml文件
	 * 这个方法由genEntity()调用
	 * @param datas
	 * */
	private void genMapper(Map<String, Object> datas) {
		String outputPath = workspace + "/biz-root/" + system + "/src/main/resources/com/skg/luohong/biz/" + systemKey + "/" + module + "/entity/";
		File outputPathDirectory = new File(outputPath);
		String outputFileName = null;
        
		
		String entity = (String) datas.get("entity");
		String mapperName = entity.replace("Entity", "") + "Mapper";
		datas.put("mapperName", mapperName);
		
		//文件夹已经存在
		if(!outputPathDirectory.exists()){
			System.out.println("mk dir " + outputPath);
			outputPathDirectory.mkdirs();
		}
		outputFileName = outputPath + datas.get("entity") + ".mapper.xml";
		
		//生成代码
		FreemarkUtils.createFile(datas, 
				"src/main/resources/template/mapper.ftl", outputFileName);
	}

	private void genController(String table, boolean override) {
		// TODO Auto-generated method stub

	}

	/**
	 * 使用一个类来包装这些信息
	 * 数据库字段名，java属性名，字段对应的数据库类型
	 * 
	 * @author 骆宏
	 * @date 2015-08-15 10:25
	 * */
	public static class Field{
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
			this.firstUpperName = name.substring(0, 1).toUpperCase() + name.substring(1);

			this.column = column;
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
		public String getColumn(){
			return column;
		}
	}
}
