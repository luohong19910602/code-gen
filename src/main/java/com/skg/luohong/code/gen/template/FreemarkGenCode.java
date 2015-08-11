package com.skg.luohong.code.gen.template;

import java.sql.SQLException;
import java.util.List;

import com.skg.luohong.code.gen.utils.JdbcUtils;
import com.skg.luohong.code.gen.utils.XMlUtils;
import com.skg.luohong.code.gen.utils.XMlUtils.GenFile;

/**
 * 代码生成器的实现类，这里面使用freemarker来充当代码生成器
 * @author 骆宏
 * @date 2015-08-11 21:49
 * */
public class FreemarkGenCode implements IGenCode {

	public void genCode() {
		String workspace = XMlUtils.getWorkSpace();
        String module = XMlUtils.getModule();
        List<XMlUtils.GenFile> genFiles = XMlUtils.genFiles();
        
        for(GenFile genFile: genFiles){
        	String table = genFile.key;
        	boolean override = genFile.override;
        	List<String> options = genFile.genOptions;
        	
        	try {
				List<String> tableInfos = JdbcUtils.tableInfo(table);
				for(String column: tableInfos){
					System.out.println(column);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
	}
	
	public static void main(String[] args) throws SQLException {
		new FreemarkGenCode().genCode();
	}
}
