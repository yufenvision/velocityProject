package com.yuf.velocity.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
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
		
		// 实体化velocity上下文对象
		VelocityContext ctx = new VelocityContext();
		// 添加变量
		ctx.put("name", "velocity"); 
		ctx.put("date", (new Date()).toString()); 
		ctx.put("username", "自动生成html文件");
		List temp = new ArrayList(); 
		temp.add("第一个：啊啊啊啊啊"); 
		temp.add("第二个：oooooo"); 
		temp.add("第三个：哦哦哦哦哦"); 
		ctx.put( "list", temp); 
		
		// 获取模板文件
//		Template t = ve.getTemplate("templates/hellovelocity.vm");
		Template t = ve.getTemplate("templates/news.html");
		
		//1、输出到控制台
		StringWriter sw = new StringWriter();
		t.merge(ctx, sw);
		System.out.println(sw.toString());
		
		//2、持久化到本地
		//获取服务器存储路径
		String porpertiesFile = "application-dev.properties";
		Properties p = getConfigProperties(porpertiesFile);
		String dirPath = p.getProperty("server.storage.path");
		System.out.println(p.get("server.storage.path"));
		System.out.println(p.getProperty("server.storage.path"));
		
		try {
			File dir = new File(dirPath);
			if(!dir.exists()){
				dir.mkdirs();
			}
			File file = new File(dirPath + File.separator + "file.html");
			//每次都覆盖生成
			file.createNewFile();
//			FileOutputStream out = new FileOutputStream(file);
//			out.write(sw.toString().getBytes());;
//			out.close();
			FileWriter w = new FileWriter(file);
			t.merge(ctx, w);		
			w.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			try {
				sw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}
	
}
