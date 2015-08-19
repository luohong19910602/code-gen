package com.skg.luohong.code.gen.utils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
  
/** 
 * 访问FreeMarker的工具类 
 * 
 * @author 骆宏
 * @date 2015-08-12 22:11 
 * 
 */  
public class FreemarkUtils {  
    /** 编码格式 UTF-8 */  
    private static final String ENCODING = "UTF-8";  
  
    /** FreeMarker配置 */  
    private static Configuration config = new Configuration();  
    /** 
     * 据数据及模板生成文件 
     *  
     * @param data 一个Map的数据结果集 
     * @param templateFileName ftl模版路径 
     * @param file 生成的文件 
     *           
     */  
    public static void createFile(Map<String, Object> data, 
    		String templateFileName, String outFileName) {
        
        Writer out = null;  
        try {  
            // 获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致  
            Template template = config.getTemplate(templateFileName, ENCODING);  
            template.setEncoding(ENCODING);  
            File outFile = new File(outFileName);  
            out = new OutputStreamWriter(new FileOutputStream(outFile), ENCODING);  
            // 处理模版  
            template.process(data, out);  
            out.flush();  
            
            System.out.println("create file " + outFileName);
        } catch (Exception e) {  
        	e.printStackTrace();
        } finally{  
            try {  
                if(out != null){  
                    out.close();  
                }  
            } catch (IOException e) {  
            	e.printStackTrace();
            }  
        }  
    }  
  
    
    public static void main(String[] args) {
		
	}
}  
