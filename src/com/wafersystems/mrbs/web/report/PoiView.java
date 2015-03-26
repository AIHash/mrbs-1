package com.wafersystems.mrbs.web.report;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class PoiView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HSSFSheet sheet = workbook.createSheet("list");
		HSSFCellStyle style = workbook.createCellStyle(); //样式对象
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中

		HSSFRow titleRow = sheet.createRow(0);
		titleRow.setHeightInPoints(40);

		HSSFRow dateRow =  sheet.createRow(1);
		dateRow.setHeightInPoints(40);

		HSSFRow blankRow =  sheet.createRow(1);
		blankRow.setHeightInPoints(10);

		sheet.setDefaultColumnWidth(12);

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
		HSSFCell cell = getCell(sheet, 0, 0);
		style.setWrapText(true);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.WHITE.index);
		
		cell.setCellStyle(style);
		cell.setCellValue("填报日期：2011-5-18\n填报人：会议管理员\n报表统计时间：2011-4-1 至2011-4-30");

	}
}
