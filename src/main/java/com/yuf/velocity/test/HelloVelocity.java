package com.yuf.velocity.test;

import java.io.FileInputStream;
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
		
		// 获取模板文件
		Template t = ve.getTemplate("templates/hellovelocity.vm");
		// 设置变量
		VelocityContext ctx = new VelocityContext();
		ctx.put("name", "velocity"); 
		ctx.put("date", (new Date()).toString()); 
		 
		List temp = new ArrayList(); 
		temp.add("1"); 
		temp.add("2"); 
		ctx.put( "list", temp); 
		
		//输出
		StringWriter sw = new StringWriter();
		t.merge(ctx, sw);
		System.out.println(sw.toString());
		
		System.out.println("----------------");
		
		Properties p = new Properties();
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		try {
			FileInputStream in = new FileInputStream(path+"application.properties");
			p.load(in);
			in.close();
			System.out.println(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
