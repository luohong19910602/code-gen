package com.skg.luohong.code.gen.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * XML工具类
 * 
 * @author 骆宏
 * @since 2015-08-11 22:49
 */
public class XMlUtils {

	/**
	 * 获取根节点
	 * 
	 * @param doc
	 * @return
	 */
	public static Element getRootElement(Document doc) {
		if (Objects.isNull(doc)) {
			return null;
		}
		return doc.getRootElement();
	}

	/**
	 * 获取节点eleName下的文本值，若eleName不存在则返回默认值defaultValue
	 * 
	 * @param eleName
	 * @param defaultValue
	 * @return
	 */
	public static String getElementValue(Element eleName, String defaultValue) {
		if (Objects.isNull(eleName)) {
			return defaultValue == null ? "" : defaultValue;
		} else {
			return eleName.getTextTrim();
		}
	}

	public static String getElementValue(String eleName, Element parentElement) {
		if (Objects.isNull(parentElement)) {
			return null;
		} else {
			Element element = parentElement.element(eleName);
			if (Objects.isNotNull(element)) {
				return element.getTextTrim();
			} else {
				try {
					throw new Exception("找不到节点" + eleName);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		}
	}

	/**
	 * 获取节点eleName下的文本值
	 * 
	 * @param eleName
	 * @return
	 */
	public static String getElementValue(Element eleName) {
		return getElementValue(eleName, null);
	}

	public static Document read(File file) {
		return read(file, null);
	}

	public static Document read(String filename){
		return read(new File(filename), null);
	}

	public static Document findCDATA(Document body, String path) {
		return XMlUtils.stringToXml(XMlUtils.getElementValue(path,
				body.getRootElement()));
	}

	/**
	 * 
	 * @param file
	 * @param charset
	 * @return
	 * @throws DocumentException
	 */
	public static Document read(File file, String charset) {
		if (Objects.isNull(file)) {
			return null;
		}
		SAXReader reader = new SAXReader();
		if (Objects.isNotNull(charset)) {
			reader.setEncoding(charset);
		}
		Document document = null;
		try {
			document = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return document;
	}

	public static Document read(URL url) {
		return read(url, null);
	}

	/**
	 * 
	 * @param url
	 * @param charset
	 * @return
	 * @throws DocumentException
	 */
	public static Document read(URL url, String charset) {
		if (Objects.isNull(url)) {
			return null;
		}
		SAXReader reader = new SAXReader();
		if (Objects.isNotNull(charset)) {
			reader.setEncoding(charset);
		}
		Document document = null;
		try {
			document = reader.read(url);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return document;
	}

	/**
	 * 将文档树转换成字符串
	 * 
	 * @param doc
	 * @return
	 */
	public static String xmltoString(Document doc) {
		return xmltoString(doc, null);
	}

	/**
	 * 
	 * @param doc
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String xmltoString(Document doc, String charset) {
		if (Objects.isNull(doc)) {
			return "";
		}
		if (Objects.isNull(charset)) {
			return doc.asXML();
		}
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(charset);
		StringWriter strWriter = new StringWriter();
		XMLWriter xmlWriter = new XMLWriter(strWriter, format);
		try {
			xmlWriter.write(doc);
			xmlWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strWriter.toString();
	}

	/**
	 * 持久化Document
	 * @param doc
	 * @param charset
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	public static void xmltoFile(Document doc, File file, String charset)
			throws Exception {
		if (Objects.isNull(doc)) {
			throw new NullPointerException("doc cant not null");
		}
		if (Objects.isNull(charset)) {
			throw new NullPointerException("charset cant not null");
		}
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(charset);
		FileOutputStream os = new FileOutputStream(file);
		OutputStreamWriter osw = new OutputStreamWriter(os, charset);
		XMLWriter xmlWriter = new XMLWriter(osw, format);
		try {
			xmlWriter.write(doc);
			xmlWriter.close();
			if (osw != null) {
				osw.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param doc
	 * @param charset
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	public static void xmltoFile(Document doc, String filePath, String charset)
			throws Exception {
		xmltoFile(doc, new File(filePath), charset);
	}


	/**
	 * 
	 * @param doc
	 * @param filePath
	 * @param charset
	 * @throws Exception
	 */
	public static void writDocumentToFile(Document doc, String filePath, String charset)
			throws Exception {
		xmltoFile(doc, new File(filePath), charset);
	}

	public static Document stringToXml(String text) {
		try {
			return DocumentHelper.parseText(text);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Document createDocument() {
		return DocumentHelper.createDocument();
	}


	public static class Objects
	{

		public static boolean isNull(Object obj)
		{
			return obj == null;
		}

		public static boolean isNotNull(Object obj)
		{
			return !isNull(obj);
		}

		@SuppressWarnings("rawtypes")
		public static boolean isEmpty(Object obj)
		{
			if (obj == null)
			{
				return true;
			}
			if (obj instanceof CharSequence)
			{
				return ((CharSequence) obj).length() == 0;
			}
			if (obj instanceof Collection)
			{
				return ((Collection) obj).isEmpty();
			}
			if (obj instanceof Map)
			{
				return ((Map) obj).isEmpty();
			}
			if (obj.getClass().isArray())
			{
				return Array.getLength(obj) == 0;
			}
			return false;
		}

		public static boolean isNotEmpty(Object obj)
		{
			return !isEmpty(obj);
		}
	}

	/**
	 * 获取工作空间
	 * */
	public static String getWorkSpace(){
		Document doc = read("src/main/resources/conf/codegen.xml");
		Element root = getRootElement(doc);
		//workspace
	    Element workspace = root.element("workspace");
		return workspace.getTextTrim();
	}
	
	/**
	 * 获取module
	 * */
	public static String getModule(){
		Document doc = read("src/main/resources/conf/codegen.xml");
		Element root = getRootElement(doc);
		Element module = root.element("module");
		return module.getTextTrim();
	}
	
	public static String getSystem(){
		Document doc = read("src/main/resources/conf/codegen.xml");
		Element root = getRootElement(doc);
		Element system = root.element("system");
		return system.getTextTrim();
	}
	
	public static String getSystemKey(){
		Document doc = read("src/main/resources/conf/codegen.xml");
		Element root = getRootElement(doc);
		Element system = root.element("system");
		return system.attributeValue("key");
	}
	
	public static String getPrefix(){
		Document doc = read("src/main/resources/conf/codegen.xml");
		Element root = getRootElement(doc);
		Element system = root.element("prefix");
		return system.getTextTrim();
	}
	
	/**
	 * 获取要生成表的信息
	 * */
	public static List<GenFile> genFiles(){
		Document doc = read("src/main/resources/conf/codegen.xml");
		Element root = getRootElement(doc);
		
		//gen file
		List<GenFile> genFiles = new ArrayList<GenFile>();
		Element files = root.element("files");
		int size = files.elements().size();
		for(int i=0; i<size; i++){
			Element file = (Element) files.elements().get(i);
			String key = file.attributeValue("key");
            boolean override = Boolean.valueOf((file.attributeValue("override")));
            String prefix = file.attributeValue("prefix");
            List<String> options = Arrays.asList(file.attributeValue("value").split(","));
            
            GenFile genFile = new GenFile(key, override, options);
            
            if(prefix != null && prefix.trim().length() > 0){
            	genFile.setPrefix(prefix);
            }
            
            genFiles.add(genFile);
		}

		return genFiles;
	}
	
	/**
	 * 信使对象
	 * */
	public static class GenFile{
		
		public final String key;
        public final boolean override;
        private String prefix;
		public final List<String> genOptions;
		
		public GenFile(String key, boolean override, List<String> genOptions) {
			this.key = key;
			this.override = override;
			this.genOptions = genOptions;
		}
        
		public String getPrefix() {
			return prefix;
		}

		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}

		@Override
		public String toString() {
			return "GenFile [key=" + key  + ", override=" + override  + ", prefix=" + prefix 
					+ ", genOptions=" + genOptions + "]";
		}
	}
	
    
	public static void main(String[] args) {
		System.out.println(getWorkSpace());
		System.out.println(getModule());
		System.out.println(getSystem());
		System.out.println(getSystemKey());
		System.out.println(getPrefix());
		System.out.println(genFiles());
	}

}