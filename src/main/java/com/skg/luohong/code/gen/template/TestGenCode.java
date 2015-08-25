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

public class TestGenCode implements IGenCode {

	private String workspace;  //工作目录
	private String system;  //系统
	private String systemKey;  //系统的简称
	private String module;  //模块
	private String table;
	private boolean override;

	@Override
	public void init(GenCodeInitParam param) {
		this.workspace = param.getWorkspace();
		this.system = param.getSystem();
		this.module = param.getModule();
		this.systemKey = param.getSystemKey();
		this.table = param.getTable();
		this.override = param.override();
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
		if(this.table == null){
			throw new IllegalArgumentException("table can't be null");
		}

		//为了确保代码生成的路劲正确，这里面workspace必须存在
		if(!new File(workspace).exists()){
			throw new IllegalArgumentException("workspace must be exists");
		}

	}

	@Override
	public void genCode() {
		genTest(table, override);
	}

	/**
	 * 1.生成测试类
	 * 2.生成test mapper.xml，并且放在test/resources下面
	 * */
	private void genTest(String table, boolean override) {
		//实体名
		String temp = StringUtils.toJavaProperties(table);
		String entityName = temp.substring(0, 1).toUpperCase() + temp.substring(1) + "Tbl"; //先不做细节处理
		/**
		 * 将E:\workspace中的'\'替换为'/'
		 * */
		if(workspace.contains("\\")){
			workspace = workspace.replaceAll("\\\\", "/");
		}

		//根据配置信息，得到要生成Entity实体类的所在路径
		String outputPath = workspace + "/biz-root/" + system + "/src/test/java/com/skg/luohong/biz/" + systemKey + "/" + module + "/mapper/";
		File outputPathDirectory = new File(outputPath);
		String outputFileName = null;

		//文件夹已经存在
//		if(!outputPathDirectory.exists()){
//			System.out.println("mk dir " + outputPath);
//			outputPathDirectory.mkdirs();
//		}
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
		List<Field> fields = new ArrayList<Field>();
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
			
			//po类名，去掉Tbl
			String poName = entityName.substring(0, entityName.length() - 3) + "Po";
			
			datas.put("poName", poName);
			
			//已经完成了Entity的生成，接下来把mapper文件也生成
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * copy测试文件
	 * @param datas
	 * */
	private void genMapper(Map<String, Object> datas) {
		String outputPath = workspace + "/biz-root/" + system + "/src/test/resources/com/skg/luohong/biz/" + systemKey + "/" + module + "/mapper/";
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
        File outputFile = new File(outputFileName);
        if(outputFile.exists() && override){
        	System.out.println("delete file " + outputFileName);
        	outputFile.delete();
        }
        
        //生成代码
		FreemarkUtils.createFile(datas, 
				"src/main/resources/template/mapper.ftl", outputFileName);
	}
}
