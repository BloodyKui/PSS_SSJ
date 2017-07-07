package cn.lkk.pss.velocity;

import java.io.File;
import java.io.FileWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.junit.Test;

public class GenerateCodeTest {
	/**
	 * 生成文件后的存放路径 1、src/main/java/cn/lkk/pss/action/EmployeeAction.java
	 * 2、src/main/java/cn/lkk/pss/query/EmployeeQuery.java
	 * 3、src/main/java/cn/lkk/pss/repository/EmployeeRepository.java
	 * 4、src/main/java/cn/lkk/pss/service/impl/EmployeeServiceImpl.java
	 * 5、src/main/java/cn/lkk/pss/service/IEmployeeService.java
	 * 6、src/test/java/cn/lkk/pss/service/EmployeeServiceTest.java
	 * 7、src/main/webapp/js/model/employee.js
	 * 8、src/main/webapp/WEB-INF/views/employee/employee_input.jsp
	 * 9、src/main/webapp/WEB-INF/views/employee/employee.jsp
	 */

	/*******************************************************************
	 * 准备拼接上面的路径： 分为src路径，包路径，和文件名，还有需要创建的domain名
	 ********************************************************************/
	private static final String SRCADRESS = "src/main/java/";
	private static final String TESTADRESS = "src/test/java/";
	private static final String PACKAGEADRESS = "cn/lkk/pss/";
	private static final String WEBAPPADRESS = "src/main/webapp/";

	// 模版文件名
	private String[] templates = { "DomainAction.java", "DomainQuery.java", "DomainRepository.java",
			"DomainServiceImpl.java", "IDomainService.java", "DomainServiceTest.java", "domain.js", "domain_input.jsp",
			"domain.jsp" };

	// 执行初始化操作，将文件名拼接完成,注意顺序要和templates中的顺序一一对应
	private String[] paths = { SRCADRESS + PACKAGEADRESS + "web/action/", SRCADRESS + PACKAGEADRESS + "query/",
			SRCADRESS + PACKAGEADRESS + "repository/", SRCADRESS + PACKAGEADRESS + "service/impl/",
			SRCADRESS + PACKAGEADRESS + "service/", TESTADRESS + PACKAGEADRESS + "service/", WEBAPPADRESS + "js/model/",
			WEBAPPADRESS + "WEB-INF/views/domain/", WEBAPPADRESS + "WEB-INF/views/domain/" };

	// 要创建的domain的名字
	private String[] domains = {"Department"};

	@Test
	public void testCreate() throws Exception {
		init();
	}

	// 根据相对应的domain生成对应的路径和文件名，并拼接在一起
	public void init() throws Exception{
		//设置标记，表示每次生成的时候是否替换已存在文件，默认为false，不替换
		Boolean isReplaceAll = false;
		
		for (int i = 0; i < domains.length; i++) {
			// 拿到首字母大写的domain
			String entity = domains[i];
			// 拿到首字母小写的domain
			String lowerEntity = entity.substring(0, 1).toLowerCase() + entity.substring(1);
			
			for (int j = 0; j < templates.length; j++) {
				String fileName = paths[j] + templates[j];
				//拼接全路径，并将其中的domain字段替换为指定的类型名字
				fileName = fileName.replaceAll("Domain", entity).replaceAll("domain", lowerEntity);
				//拿到velocity模版的上下文对象
				VelocityContext context = new VelocityContext();
				//放入值
				context.put("entity", entity);
				context.put("lowerEntity", lowerEntity);
				//加载模版文件。拿到模版对象(注意设置编码格式。否则中文乱码)
				Template template = Velocity.getTemplate("template/"+templates[j],"UTF-8"); 
				//创建一个文件对象
				File file = new File(fileName);
				
				//为了防止误操作导致重新生成文件,设置是否要替换文件的开关
				//如果该开关关闭同时该文件存在，就直接结束本次循环
				if (!isReplaceAll && file.exists()) {
					continue;
				}
				
				//如果文件所在目录不存在，则创建该目录
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				//创建一个文件输出流
				FileWriter fw = new FileWriter(file);
				//将流和模版结合起来
				template.merge( context, fw );
				
				//文件流必须要关闭，否则不能从内存输出到文件中
				fw.close();
			}
		}
	}
}
