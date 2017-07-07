package cn.lkk.pss.velocity;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.junit.Test;

public class VelocityTest {
	@Test
	public void testHello() throws Exception {
		//拿到velocity模版的上下文对象
		VelocityContext context = new VelocityContext();
		//放入值
		context.put("name", "霜霜");
		//加载模版文件。拿到模版对象(注意设置编码格式。否则中文乱码)
		Template template = Velocity.getTemplate("template0/Hello.html","UTF-8"); 
		//创建一个字符串输出流
		StringWriter sw = new StringWriter(); 
		//将流和模版结合起来
		template.merge( context, sw );
		
		System.out.println(sw);
	}
	
	@Test
	public void testHelloFile() throws Exception {
		//拿到velocity模版的上下文对象
		VelocityContext context = new VelocityContext();
		//放入值
		context.put("name", "霜霜");
		//加载模版文件。拿到模版对象(注意设置编码格式。否则中文乱码)
		Template template = Velocity.getTemplate("template0/Hello.html","UTF-8"); 
		//创建一个文件对象
		File file = new File("template0/xxx.html");
		//创建一个文件输出流
		FileWriter fw = new FileWriter(file);
		//将流和模版结合起来
		template.merge( context, fw );
		
//		System.out.println(fw);
		//文件流必须要关闭，否则不能从内存输出到文件中
		fw.close();
	}
}
