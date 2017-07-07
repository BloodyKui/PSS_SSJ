package cn.lkk.pss.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
//上面两个注解可以被继承，所以提取一个抽象的父接口
public abstract class BaseServiceTest {
	
}
