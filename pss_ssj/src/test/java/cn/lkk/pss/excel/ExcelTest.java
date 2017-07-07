package cn.lkk.pss.excel;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class ExcelTest {
	//操作时可能会报异常：java.io.FileNotFoundException: excel.xlsx (另一个程序正在使用此文件，进程无法访问。)
	@Test
	public void testCreateExcel() throws Exception {
		// 创建一个Excel文件
		SXSSFWorkbook wb = new SXSSFWorkbook();
		// 创建一个表，并设置表名
		Sheet sheet = wb.createSheet("9乘9");
		// 创建99乘法表
		for (int i = 1; i < 10; i++) {
			// 创建行,从0开始
			Row row = sheet.createRow(i - 1);
			for (int j = 1; j <= i; j++) {
				// 创建每一行中的单元格
				Cell cell = row.createCell(j-1);
				// 设置单元格中的值
				cell.setCellValue(i + "*" + j + "=" + i * j);
			}
		}

		// 创建一个文件输出流
		FileOutputStream fileOutputStream = new FileOutputStream("excel.xlsx");
		// 将创建好的表格写入到流中
		wb.write(fileOutputStream);
		//关闭流，先开后关
		fileOutputStream.close();
		wb.dispose();
	}
	
	//测试导入功能
	@Test
	public void testImportExcel() throws Exception {
		//创建一个输入流
		FileInputStream fs = new FileInputStream("excel.xlsx");
		//可以直接加载字符串路径，但是方法过时了
		XSSFWorkbook workbook = new XSSFWorkbook(fs);
		//通过索引获取该excel文件的表
		XSSFSheet sheet = workbook.getSheetAt(0);
		//定义数据集合用于存放读取文件后的数据
		List<String[]> rows = new ArrayList<>();
		//通过表遍历获取所有行,不需要读取表头行，sheet.getLastRowNum()获取最后一行的标号
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			//依次得到每一行
			XSSFRow row = sheet.getRow(i);
			//遍历该行获取每一个单元格的数据，row.getLastCellNum()获取该行最后一个单元格
			short lastCellNum = row.getLastCellNum();
			//定义一个字符串数组用于存放一行数据
			String[] datas = new String[lastCellNum];
			for (int j = 0; j < lastCellNum; j++) {
				//获取每一个单元格
				XSSFCell cell = row.getCell(j);
				//获取该单元格数据
				String cellValue = cell.getStringCellValue();
				//将数据存放到String数组中
				datas[j] = cellValue;
			}
			//将读取完的一行数据放入list集合中
			rows.add(datas);
		}
		//遍历打印测试
		for (String[] strings : rows) {
			for (String string : strings) {
				System.out.print(string+" ");
			}
			System.out.println();
		}
	}
}
