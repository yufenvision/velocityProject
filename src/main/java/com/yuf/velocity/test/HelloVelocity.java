package com.yuf.velocity.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class HelloVelocity {
	public static void main(String[] args) {
		// 初始化模板引擎
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER,"classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();
		
		// 获取模板文件
//		Template t = ve.getTemplate("templates/hellovelocity.vm");
		Template t = ve.getTemplate("templates/news.html");
		// 设置变量
		VelocityContext ctx = new VelocityContext();
		ctx.put("name", "velocity"); 
		ctx.put("date", (new Date()).toString()); 
		ctx.put("username", "自动生成html文件");
		
		List temp = new ArrayList(); 
		temp.add("1"); 
		temp.add("2"); 
		ctx.put( "list", temp); 
		
		//输出
		StringWriter sw = new StringWriter();
		t.merge(ctx, sw);
		System.out.println(sw.toString());
		
		//获取服务器存储路径
		String dirPath = "application-dev.properties";
		Properties p = getConfigProperties(dirPath);
		System.out.println(p.get("server.storage.path"));
		System.out.println(p.getProperty("server.storage.path"));
		
		try {
			File dir = new File(dirPath);
			if(!dir.exists()){
				dir.mkdirs();
			}
			File file = new File(dirPath + File.separator + "file.html");
			file.createNewFile();
			FileOutputStream out = new FileOutputStream(file);
			out.write(sw.toString().getBytes());;
			out.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("----------------");
		
		
	}
	
	public static Properties getConfigProperties(String propertiesName){
		Properties p = new Properties();
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		try {
			FileInputStream in = new FileInputStream(path+propertiesName);
			p.load(in);
			in.close();
//			System.out.println(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}
	
}
