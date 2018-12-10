package com.yuf.velocity.test;

import java.io.File;
import java.io.FileWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.junit.Test;


public class AutoVelocity {
     // 15.如果已经存在的文件，默认是不生成的，防止覆盖,千万不要用Employee来测试
     // 规则flag = false，不覆盖，
     // 规则flag = true，覆盖，
     private boolean flag = false;
     
     
     // 1.定义路径固定值
     // *********************规则：路径前面不要写/，后面结尾必须写/
     private String SRC = "src/main/java/";
     // 容易写错的地方：不能包路径，必须是文件路径
     private String PACKAGE = "cn/itsource/pss/";
     private String RESOURCES = "src/main/resources/";
     private String TEST = "src/test/java/";
     private String WEBAPP = "src/main/webapp/";
     
     //2.定义那些domain需要生成代码
//   private String[] domains ={"Role","Permission"};
     private String[] domains ={"Depot","ProductStock","StockIncomeBill","StockIncomeBillItem"};
     
     //3.定义模版的名称
     private String[] templates = { "Service.java", "ServiceImpl.java", "Query.java", "Action.java", "ServiceTest.java",
                "hbm.xml", "Context.xml", "domain.js", "list.jsp", "input.jsp" };
     //4.定义模版对应生成文件的位置
     private String[] files = { SRC + PACKAGE + "service/", SRC + PACKAGE + "service/impl/",SRC + PACKAGE + "query/",SRC + PACKAGE + "web/action/",TEST + PACKAGE + "service/",
                RESOURCES + PACKAGE + "domain/",RESOURCES + "manager/",WEBAPP + "js/model/",WEBAPP + "WEB-INF/views/",WEBAPP + "WEB-INF/views/"};
     
     @Test
     public void create() throws Exception {
           if(templates.length != files.length){
                throw new RuntimeException("模版!=模版对应生成文件");
           }
           // 实例化Velocity上下文对象
           VelocityContext context = new VelocityContext();
           // 5.外循环
           for (int i = 0; i < domains.length; i++) {
                // 准备数据,类似于struts放入值栈:String getText(){ return "xxxx"}
                context.put("domainEntity", domains[i]);
                // 获取domain模型首字母小写
                String lowerDomainEntity = domains[i].substring(0, 1).toLowerCase() + domains[i].substring(1);
                context.put("lowerDomainEntity", lowerDomainEntity);
                // 6.内循环
                for (int j = 0; j < templates.length; j++) {
                     // src/main/java/cn/itsource/pss/DeptService.java
                     File file = new File(files[j] + domains[i] + templates[j]);
                     if ("Service.java".equals(templates[j])) {
                           file = new File(files[j] + "I" + domains[i] + templates[j]);
                     } else if ("hbm.xml".equals(templates[j])) {
                           file = new File(files[j] + domains[i] + "." + templates[j]);
                     } else if ("domain.js".equals(templates[j])) {
                           file = new File(files[j] + lowerDomainEntity + ".js");
                     } else if ("list.jsp".equals(templates[j])) {
                           file = new File(files[j] + lowerDomainEntity + "/" + lowerDomainEntity + ".jsp");
                     } else if ("input.jsp".equals(templates[j])) {
                           file = new File(files[j] + lowerDomainEntity + "/" + lowerDomainEntity + "_input.jsp");
                     }
                     
                     // 规则flag = false，不覆盖，
                     // 规则flag = true，覆盖，
                     // 如果不覆盖并且代码文件已经存在：不需要生成
                     if (!flag && file.exists()) {
                           // return;
                           // break;
                           continue; // 结束本次循环，进入下一次循环
                     }
                     // 打印必须在修改路径之后
                     System.out.println(file.getAbsolutePath());
                     
                     // 生成没有的路径
                     File parentFile = file.getParentFile();
                     if (!parentFile.exists()) {
                           parentFile.mkdirs();
                     }

                     FileWriter writer = new FileWriter(file);
                     Template template = Velocity.getTemplate("template/" + templates[j], "UTF-8");
                     template.merge(context, writer);
                     // 关闭流
                     writer.close();
                }
                
                System.out.println("先刷新工程,没有错误，修改映射文件，运行测试，启动tomcat");
           }
     }
}





