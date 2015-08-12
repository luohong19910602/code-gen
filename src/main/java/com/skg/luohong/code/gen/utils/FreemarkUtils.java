package com.skg.luohong.code.gen.utils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
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
    /** 是否已初始化 */  
    private static boolean isInit = false;  
      
    /** 应用所在路径 */  
    private static String appPath = null;  
      
    /** 编码格式 UTF-8 */  
    private static final String ENCODING = "UTF-8";  
  
    /** FreeMarker配置 */  
    private static Configuration config = new Configuration();  
      
    /** 
     * 初始化FreeMarker配置 
     * @param applicationPath 应用所在路径 
     */  
    public static void initFreeMarker(String applicationPath) {  
        if (!(isInit)) {  
            try {  
                appPath = applicationPath;  
                // 加载模版  
                File file = new File(new StringBuffer(appPath).append(File.separator).toString());  
                // 设置要解析的模板所在的目录，并加载模板文件  
                config.setDirectoryForTemplateLoading(file);  
                // 设置文件编码为UTF-8  
                config.setEncoding(Locale.getDefault(), ENCODING);  
                isInit = true;  
            } catch (IOException e) {  
            	e.printStackTrace();
            }  
        }  
    }  
  
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
    	
        if(!isInit){  
            System.out.println("FreeMarker模板引擎未初始化,请确认已经调用initFreeMarker()方法对其进行了初始化");  
        }  
        
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
