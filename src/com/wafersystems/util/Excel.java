package com.wafersystems.util;

/*
 * Title: Voice Mate System
 * Copyright: Copyright (c) 2004
 * Company: Wafer Systems Ltd
 * All rights reserved.
 * 
 * Created on 2005-8-29 by AnDong
 * JDK version used : 1.4.1_02
 */

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Excel格式文档操作类
 * 
 * @author AnDong
 */
public class Excel {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(Excel.class);

	public static final String SPLIT_STR = "#@#";
	/**
	 * 上边框
	 */
	public static final int BORDER_TOP = 0;
	/**
	 * 下边框
	 */
	public static final int BORDER_BOTTOM = 1;
	/**
	 * 左边框
	 */
	public static final int BORDER_LEFT = 2;
	/**
	 * 右边框
	 */
	public static final int BORDER_RIGHT = 3;

	private POIFSFileSystem fileSystem = null;

	private HSSFWorkbook workBook = null;

	private HSSFSheet sheet = null;

	private int currentSheet = -1;

	/**
	 * 构造函数
	 * 
	 * @param filePath
	 * 
	 * @throws IOException
	 */
	public Excel(String filePath) throws IOException {
		InputStream is = Excel.class.getResourceAsStream(filePath);
		fileSystem = new POIFSFileSystem(is);
		workBook = new HSSFWorkbook(fileSystem);
	}

	/**
	 * 设置当前工作使用的sheet页。
	 */
	public void setCurrentSheet(int sheetNumber) {
		currentSheet = sheetNumber - 1;
		sheet = workBook.getSheetAt(currentSheet);
	}
	
	/**
	 * 设置当前工作使用的sheet页。
	 */
	public void setCurrentSheet(String sheetString) {
		sheet = workBook.getSheet(sheetString);
	}
	
	/**
	 * 在指定行插入多行数据
	 */
	public void insert(int rowNumber, String[] data) {
		if (null == data) {
			logger.warn("要保存的数据为Null，无法保存！");
			return;
		}

		if (0 == data.length)
			return;
		else {
			// 处理第一行记录。
			HSSFRow row = sheet.getRow(rowNumber - 1);			
			int columNumber = row.getPhysicalNumberOfCells();
			String[] cellData = data[0].split(SPLIT_STR);

			// 如果插入数据的列与最大的列数不相符，则使用2者中较小的一个。
			if (columNumber > cellData.length)
				columNumber = cellData.length;

			HSSFCell cell = null;
			for (int i = 0; i < columNumber; i++) {
				cell = row.getCell(i);
				// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				// 插入单元格数据
				setCellValue(cell, cellData[i]);
			}

			// 获取模板中第一行设置的样式列表。
			HSSFCellStyle[] cellStyle = new HSSFCellStyle[columNumber];
			for (int i = 0; i < columNumber; i++) {
				cellStyle[i] = row.getCell(i).getCellStyle();
				if(cellStyle[i].getLocked() == true){
					cellStyle[i].setLocked(true);
					cell.setCellStyle(cellStyle[i]);
				}else{
					cellStyle[i].setLocked(false);
					cell.setCellStyle(cellStyle[i]);
				}
				// logger.debug("column:" + (i + 1) + "；align:" +
				// cellStyle[i].getAlignment());
			}

			for (int i = 1; i < data.length; i++) {
				// logger.debug("row:" + (rowNumber + i));
				if(data[i]!=null&&data[i].length()>0){
					row = sheet.createRow(rowNumber + i - 1);
					cellData = data[i].split(SPLIT_STR);

					for (int j = 0; j < columNumber; j++) {
						// logger.debug("cell:" + (rowNumber + i) + "," + (j + 1));
						// logger.debug("sample column:" + (j + 1) + "；align:" +
						// cellStyle[j].getAlignment());

						cell = row.createCell(j);
						// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						// 同步列格式
						setCellStyle(cell, cellStyle[j]);

						// 插入单元格数据
						setCellValue(cell, cellData[j]);
					}
				}
			}

			// //建立最后一行，将顶部的线条设为粗线条
			// row = sheet.createRow(rowNumber + data.length - 1);
			// row.setHeight((short)20);
			// for (int j = 0; j < columNumber; j++)
			// {
			// cell = row.createCell((short)j);
			// cell.getCellStyle().setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
			// }

			// 设置打印区域
			// TODO [待完善...] 暂时只处理列数<=Z的情况，其它情况后面处理。
			char columInLetter = (char) (columNumber + 64);
			logger.debug("Last ColumInLetter:" + columInLetter);
			workBook.setPrintArea(currentSheet, "$A$1:$" + columInLetter + "$"
					+ sheet.getPhysicalNumberOfRows());
			for(int i=0;i<data.length;i++){
				sheet.setColumnWidth(i, 5000);
			}
			
		}
	}

	/**
	 * 设置单元格数据（区分单元格数据类型）
	 * 
	 * @param cell 单元格
	 * @param cellData 单元格值
	 */
	private void setCellValue(HSSFCell cell, String cellData) {
		try {
			// 整数,小数，货币（带格式）
			if (189 == cell.getCellStyle().getDataFormat()
					|| 190 == cell.getCellStyle().getDataFormat()
					|| 188 == cell.getCellStyle().getDataFormat()
					|| 7 == cell.getCellStyle().getDataFormat()) {
				if (!StrUtil.isEmptyStr(cellData))
					cell.setCellValue(Double.parseDouble(cellData));
			} else {
				logger.debug("普通文本格式：" + cell.getCellStyle().getDataFormat());
				cell.setCellValue(new HSSFRichTextString(cellData)); // 普通字符形式
			}
		} catch (Exception e) {
			logger.error(
					"报表单元格数据格式转换错误：" + cell.getCellStyle().getDataFormat(), e);
			// 默认为普通的字符形式
			cell.setCellValue(new HSSFRichTextString(cellData));
		}
	}

	/**
	 * 设置单元格格式，解除每一列的样式对象引用，为单个单元格的样式修改准备条件。
	 * 
	 * @param cell
	 *            单元格
	 * @param style
	 *            模板单元格格式
	 */
	private void setCellStyle(HSSFCell cell, HSSFCellStyle cellStyle) {
		// 暂时使用样式单元格的样式对象来格式化新的单元格信息，避免大数据量环境下产生的“字体个数超出限制”错误。
		cell.setCellStyle(cellStyle);
	}

	/**
	 * 在指定的cell中插入文本字符
	 * 
	 * @param row
	 *            行
	 * @param column
	 *            列
	 * @param text
	 *            文本内容
	 */
	public void insert(int row, int column, String text) {
		if (null == text || "".equals(text.trim()))
			return;
		logger.info("往" + row + "行，" + column + "列，添加数据:" + text);

		HSSFCell cell = sheet.getRow(row - 1).getCell((column - 1));
		// 插入单元格数据
		setCellValue(cell, text);
	}

	/**
	 * 使用已有行的格式建立新行
	 * @param row 建立新行的行号
	 * @param styleRow 格式行行号
	 */
	public void createRow(int row, int styleRowNumber) {
		// 获取格式行信息
		HSSFRow styleRow = sheet.getRow(styleRowNumber - 1);
		int columNumber = styleRow.getPhysicalNumberOfCells();
		HSSFCellStyle[] cellStyle = new HSSFCellStyle[columNumber];
		for (int i = 0; i < columNumber; i++){
			cellStyle[i] = styleRow.getCell(i).getCellStyle();
		}

		HSSFCell cell = null;
		HSSFRow createRow = sheet.createRow(row - 1);
		createRow.setHeight(styleRow.getHeight());
		for (int j = 0; j < columNumber; j++) {
			cell = createRow.createCell(j);
			cell.setCellStyle(cellStyle[j]);
		}
	}

	/**
	 * 使用已有行的格式建立新行
	 * 
	 * @param column 新的列号
	 * @param styleColumnNumber 格式列列号
	 * @param width 列宽度
	 */
	public void createColumn(int column, int styleColumnNumber) {
		// TODO [待完善...] 暂时不考虑插入一列的需求，只考虑在最后一列后面新建列。
		HSSFRow row = null;
		HSSFCell cell = null;
		HSSFCellStyle cellStyle = null;
		for (Iterator<Row> itr = sheet.rowIterator(); itr.hasNext();) {
			row = (HSSFRow) itr.next();
			cellStyle = row.getCell((styleColumnNumber - 1)).getCellStyle();
			cell = row.createCell((column - 1));
			cell.setCellStyle(cellStyle);
		}

		sheet.setColumnWidth((column - 1), sheet.getColumnWidth((column - 2)));
	}

	/**
	 * 合并指定行的多个单元格
	 * 
	 * @param row  行号
	 * @param startColumn 开始列号
	 * @param endColumn 结束列号
	 */
	public void mergeCell(int fromRow, int fromColumn, int toRow, int toColumn) {
		sheet.addMergedRegion(
			new CellRangeAddress(fromRow - 1, toRow - 1, fromColumn - 1, toColumn - 1));
	}

	/**
	 * 自动合并指定列的单元格内容（根据名称相同原则）
	 * 
	 * @param fromRow 起始行号
	 * @param column 要合并的列号
	 */
	public void autoMergeColumn(int fromRow, int column) {
		try {
			int from = 0;
			String preValue = "", currValue = "";
			for (int i = fromRow - 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				if (i == (fromRow - 1)) // 第1行
				{
					currValue = getCellValue(i + 1, (short)column);
					preValue = currValue;
					from = i;
					// logger.info("第1行");
				} else // 中间行
				{
					currValue = getCellValue(i + 1, (short)column);
					if (currValue.equals(preValue)) // 后移合并截至点
					{
						logger.debug("相同-from:" + from + ",to:" + i);
					} else {
						logger.debug("要合并了-from:" + from + ",to:" + (i - 1));
						// 开始单元格合并
						if (i - from > 0) // 当前分组值只有1行数据，不合并
							sheet.addMergedRegion(
								new CellRangeAddress(from, i - 1, column - 1, column - 1));

						// 标志位后移。
						preValue = currValue;
						from = i;
					}
				}
			}

			// 处理最后几行相同的情况。
			if (from != sheet.getPhysicalNumberOfRows() - 1) // 最后1段的相同情况没有处理
				sheet.addMergedRegion(
						new CellRangeAddress(from, sheet.getPhysicalNumberOfRows() - 1, column - 1, column - 1));
		} catch (Exception e) {
			logger.error("自动合并单元格错误：", e);
		}
	}

	/**
	 * 自动合并指定列的单元格内容（根据名称相同原则）
	 * 
	 * @param fromRow  起始行号
	 * @param column  要合并的列号
	 * @param exampleColumn 合并样例列号
	 */
	public void autoMergeColumn(int fromRow, int column, int exampleColumn) {
		try {
			int from = 0;
			String preValue = "", currValue = "";
			for (int i = fromRow - 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				if (i == (fromRow - 1)) // 第1行
				{
					currValue = getCellValue(i + 1, exampleColumn);
					preValue = currValue;
					from = i;
				} else // 中间行
				{
					currValue = getCellValue(i + 1, exampleColumn);
					if (currValue.equals(preValue)) // 后移合并截至点
					{
						logger.debug("相同-from:" + from + ",to:" + i);
					} else {
						logger.debug("要合并了-from:" + from + ",to:" + (i - 1));
						// 开始单元格合并
						if (i - from > 0) // 当前分组值只有1行数据，不合并
							sheet.addMergedRegion(
								new CellRangeAddress(from, i - 1, column - 1, column - 1));

						// 标志位后移。
						preValue = currValue;
						from = i;
					}
				}
			}

			// 处理最后几行相同的情况。
			if (from != sheet.getPhysicalNumberOfRows() - 1) // 最后1段的相同情况没有处理
				sheet.addMergedRegion(
					new CellRangeAddress(from, sheet.getPhysicalNumberOfRows() - 1, column - 1, column - 1));
		} catch (Exception e) {
			logger.error("自动合并单元格错误：", e);
		}
	}
	
	/**
	 * 自动合并指定列的单元格内容（根据名称相同原则,为空时不合并）
	 * 
	 * @param fromRow			起始行号
	 * @param column			要合并的列号
	 */
	public void autoMergeColumnNotNull(int fromRow, short column) 
	{
		try
		{
			int from = 0;
			String preValue = "", currValue = "";
			for (int i = fromRow - 1; i < sheet.getPhysicalNumberOfRows(); i ++)
			{
				//cell = sheet.getRow(i).getCell((short)(column - 1));
			
	//			logger.info("row:" + i + ",preValue:" + preValue + ",currValue:" + cell.getStringCellValue().trim());
				if (i == (fromRow - 1))	//第1行
				{
					currValue = getCellValue(i + 1, column);
					preValue = currValue;
					from = i;
	//				logger.info("第1行");
				}
				else	//中间行
				{
					currValue = getCellValue(i + 1, column);
					if (currValue.equals(preValue) && !StrUtil.isEmptyStr(currValue))	//后移合并截至点
					{
						logger.debug("相同-from:" + from + ",to:" + i);
					}
					else
					{
						logger.debug("要合并了-from:" + from + ",to:" + (i - 1));
						//开始单元格合并
						if (i - from > 0)	//当前分组值只有1行数据，不合并
						//	sheet.addMergedRegion(new Region(from, (short)(column - 1), i - 1, (short)(column - 1)));
							sheet.addMergedRegion(new CellRangeAddress(from, i - 1, column - 1 ,column - 1));
						
						//标志位后移。
						preValue = currValue;
						from = i;
					}
				}
			}
	
			//处理最后几行相同的情况。
			if (from != sheet.getPhysicalNumberOfRows() - 1)	//最后1段的相同情况没有处理
				//sheet.addMergedRegion(new Region(from, (short)(column - 1), sheet.getPhysicalNumberOfRows() - 1, (short)(column - 1)));
				sheet.addMergedRegion(new CellRangeAddress(from, sheet.getPhysicalNumberOfRows() - 1, (column - 1) ,(column - 1)));
		}
		catch (Exception e)
		{
			logger.error("自动合并单元格错误：", e);
		}
	}

	/**
	 * 设置指定行的边框样式
	 * 
	 * @param row
	 *            行号
	 * @param position
	 *            边框位置，详见BORDER_###属性
	 * @param style
	 *            边框样式详见HSSFCellStyle.BORDER_###属性
	 */
	public void setBorderStyle(int rowNumber, int position, int style) {
		HSSFRow row = sheet.getRow(rowNumber - 1);
		int columNumber = row.getPhysicalNumberOfCells();

		HSSFCell cell = null;
		for (int i = 0; i < columNumber; i++) {
			cell = row.getCell(i);

			if (position == BORDER_TOP)
				cell.getCellStyle().setBorderTop((short) style);

			if (position == BORDER_BOTTOM)
				cell.getCellStyle().setBorderBottom((short) style);

			if (position == BORDER_LEFT)
				cell.getCellStyle().setBorderLeft((short) style);

			if (position == BORDER_RIGHT)
				cell.getCellStyle().setBorderRight((short) style);
		}
	}

	/**
	 * 设置指定单元格超链接
	 * 
	 * @param row
	 *            行号
	 * @param column
	 *            列号
	 * @param href
	 *            链接路径
	 * @param title
	 *            单元格显示的title
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public void setCellHref(int row, int column, String href, String title)
			throws UnsupportedEncodingException {
		HSSFCell cell = sheet.getRow(row - 1).getCell((column - 1));

		// 临时处理过长的超链接 ngy 2007-04-23
		if (href.length() > 252)
			href = href.substring(0, 252);

		try {
			cell.setCellFormula("HYPERLINK(\"" + href + "\", \"" + title
					+ "\")");
		} catch (Exception e) {
			cell.setCellFormula("HYPERLINK(\"" + "" + "\", \"" + title + "\")");
		}

		// 设置链接样式
		HSSFCellStyle linkStyle = workBook.createCellStyle();
		linkStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);

		// 边框
		linkStyle.setBorderBottom((short) 1);
		linkStyle.setBorderLeft((short) 1);
		linkStyle.setBorderRight((short) 1);
		linkStyle.setBorderTop((short) 1);

		// 对齐方式
		linkStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		linkStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		// 字体
		HSSFFont font = workBook.createFont();
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setUnderline((byte) 1);
		font.setColor(HSSFColor.BLUE.index);
		font.setFontHeightInPoints((short) 9);
		linkStyle.setFont(font);

		cell.setCellStyle(linkStyle);
	}

	/**
	 * 获取字符串格式的单元格内容
	 * 
	 * @param row   行号
	 * @param column 列号
	 * 
	 * @return String（Null－如果单元格不存在）
	 */
	public String getCellValue(int row, int column) {
		String value = null;

		value = getCellContent(row, column);

		// 检查是否是合并单元格，调整获取内容的行列位置
		if ("".equals(value)) {
			CellRangeAddress address = null;
			for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
				address = sheet.getMergedRegion(i);
				// 区域范围判断
				if (address.getFirstColumn() <= (column - 1)
						&& (column - 1) <= address.getLastColumn()
						&& address.getFirstRow() <= (row - 1)
						&& (row - 1) <= address.getLastRow()) {
					value = getCellContent(address.getFirstRow() + 1, address.getFirstColumn() + 1);
					break;
				}
			}
		}
		return value;
	}

	/**
	 * 获取单元格内容
	 * 
	 * @param row
	 *            行号
	 * @param column
	 *            列号
	 * 
	 * @return String（Null－如果单元格不存在）
	 */
	private String getCellContent(int row, int column) {
		HSSFCell cell = sheet.getRow(row - 1).getCell((column - 1));
		if (HSSFCell.CELL_TYPE_NUMERIC == cell.getCellType())
			return NumberUtil.format(cell.getNumericCellValue()); // 数字格式
		else
			return cell.getRichStringCellValue().getString(); //字符串格式
	}

	/**
	 * 内容保存至指定路径的文件中
	 * 
	 * @throws IOException
	 */
	public void saveToFile(String filePath) throws IOException {
		if (null == filePath) {
			logger.warn("文件路径为Null，无法保存文件内容。");
			return;
		} else {
			java.io.File file = new java.io.File(filePath);
			FileOutputStream fileOut = new FileOutputStream(file);
			workBook.write(fileOut);
			fileOut.close();
		}
	}

	/**
	 * 获取当前文档的输出流信息
	 * 
	 * @return
	 */
	public HSSFWorkbook getContent() {
		return workBook;
	}
}
