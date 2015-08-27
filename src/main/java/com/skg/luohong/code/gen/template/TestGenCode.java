package com.skg.luohong.code.gen.template;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.skg.luohong.code.gen.utils.FreemarkUtils;
import com.skg.luohong.code.gen.utils.JdbcUtils;
import com.skg.luohong.code.gen.utils.StringUtils;

/**
 * 单元测试类代码生成器
 * 这里面只生成service层的代码，dao层不需要生成
 * 
 * @author 骆宏 15013336884 846705189@qq.com
 * @date 2015-08-27 19:29
 * */
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

		String testName = temp.substring(0, 1).toUpperCase() + temp.substring(1) + "Test"; 

		/**
		 * 将E:\workspace中的'\'替换为'/'
		 * */
		if(workspace.contains("\\")){
			workspace = workspace.replaceAll("\\\\", "/");
		}

		//根据配置信息，得到要生成Entity实体类的所在路径
		String outputPath = workspace + "/biz-root/" + system + "/src/test/java/com/skg/luohong/biz/" + systemKey + "/" + module + "/";

		File outputPathDirectory = new File(outputPath);
		String outputFileName = null;

		//文件夹已经存在
		if(!outputPathDirectory.exists()){
			System.out.println("mk dir " + outputPath);
			outputPathDirectory.mkdirs();
		}

		outputFileName = outputPath + testName + ".java";

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
		datas.put("testName", testName);
		String serviceName = "I" + testName.substring(0, testName.length() - 4) + "Service";
		datas.put("serviceName", serviceName);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		datas.put("date", sdf.format(new Date()));

		//生成代码
		FreemarkUtils.createFile(datas, 
				"src/main/resources/template/test.ftl", outputFileName);

	}
}
