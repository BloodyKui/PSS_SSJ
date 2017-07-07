package cn.lkk.pss.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.lkk.pss.page.PageList;
import cn.lkk.pss.query.BaseQuery;
import cn.lkk.pss.repository.BaseRepository;
import cn.lkk.pss.service.IBaseService;

@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
// 不让父类被创建对象,所以使用抽象类
public abstract class BaseServiceImpl<T, ID extends Serializable> implements IBaseService<T, ID> {

	// spring4.x开始直接注入BaseRepository，自动获取对应的子接口
	@Autowired
	protected BaseRepository<T, ID> baseRepository;

	@Override
	@Transactional
	public void save(T t) {
		baseRepository.save(t);
	}

	@Override
	@Transactional
	public void delete(ID id) {
		baseRepository.delete(id);
	}

	@Override
	public T get(ID id) {
		// getOne是通过代理对象获取的，所以需要使用懒加载，需要配置事务过滤器，否则出现懒加载异常
		return baseRepository.getOne(id);
		// findOne没有使用代代理对象，所以不存在懒加载的问题
		// return baseRepository.findOne(id);
	}

	@Override
	public List<T> getAll() {
		return baseRepository.findAll();
	}

	@Override
	public List findByJpql(String jpql, Object... values) {
		return baseRepository.findByJpql(jpql, values);
	}

	@Override
	public List findCacheByJpql(String jpql, Object... values) {
		return baseRepository.findCacheByJpql(jpql, values);
	}

	@Override
	public PageList<T> findPageByQuery(BaseQuery baseQuery) {
		return baseRepository.findPageByQuery(baseQuery);
	}

	@Override
	public InputStream download(String[] heads, List<String[]> rows) throws Exception {
		// 创建一个Excel文件
		SXSSFWorkbook wb = new SXSSFWorkbook();
		// 创建一个表，并设置表名
		Sheet sheet = wb.createSheet();
		// 创建表头行
		Row head = sheet.createRow(0);
		// 将表头数据放在第一行中
		for (int i = 0; i < heads.length; i++) {
			Cell headCell = head.createCell(i);
			headCell.setCellValue(heads[i]);
		}
		// 创建表中数据的行
		for (int i = 0; i < rows.size(); i++) {
			// 因为0已经被表头
			Row contentRow = sheet.createRow(i + 1);
			// 拿到一行数据
			String[] datas = rows.get(i);
			// 创建每一行中的单元格，并将数据放入里面
			for (int j = 0; j < heads.length; j++) {
				Cell contentCell = contentRow.createCell(j);
				contentCell.setCellValue(datas[j]);
			}
		}
		// 创建一个字节输出流
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		// 将创建好的表格写入到流中
		wb.write(outputStream);
		// 关闭流，先开后关
		wb.dispose();
		// 将流返回回去
		return new ByteArrayInputStream(outputStream.toByteArray());
	}

	@Override
	public List<String[]> importXlsx(File upload) throws Exception {
		// 创建一个输入流
		FileInputStream fs = new FileInputStream(upload);
		// 可以直接加载字符串路径，但是方法过时了
		XSSFWorkbook workbook = new XSSFWorkbook(fs);
		// 通过索引获取该excel文件的表
		XSSFSheet sheet = workbook.getSheetAt(0);
		// 定义数据集合用于存放读取文件后的数据
		List<String[]> rows = new ArrayList<>();
		// 通过表遍历获取所有行,不需要读取表头行，sheet.getLastRowNum()获取最后一行的标号
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			// 依次得到每一行
			XSSFRow row = sheet.getRow(i);
			// 遍历该行获取每一个单元格的数据，row.getLastCellNum()获取该行最后一个单元格
			short lastCellNum = row.getLastCellNum();
			// 定义一个字符串数组用于存放一行数据
			String[] datas = new String[lastCellNum];
			for (int j = 0; j < lastCellNum; j++) {
				// 获取每一个单元格
				XSSFCell cell = row.getCell(j);
				// 获取该单元格数据
				String cellValue = cell.getStringCellValue();
				// 将数据存放到String数组中
				datas[j] = cellValue;
			}
			// 将读取完的一行数据放入list集合中
			rows.add(datas);
		}
		return rows;
	}

}
