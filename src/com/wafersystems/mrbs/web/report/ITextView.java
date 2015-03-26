package com.wafersystems.mrbs.web.report;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * 使用itext生成pdf 
 *
 */
public class ITextView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 打开文档，以写入内容
		document.open();
		// 设置中文字体
		BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font titleFont = new Font(bfChinese, 10, Font.BOLD);// 设置字体大小
		Font headFont = new Font(bfChinese, 8, Font.BOLD);// 设置字体大小
		Font textFont = new Font(bfChinese, 10, Font.NORMAL);// 设置字体大小

		float[] widths = { 100f, 100f, 200f, 100f, 120f, 110f };// 设置表格的列宽

		PdfPTable table = new PdfPTable(widths);// 建立一个pdf表格
		table.setTotalWidth(535);// 设置表格的宽度
		table.setLockedWidth(true);// 设置表格的宽度固定
		table.setSpacingBefore(3f);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);

		table.addCell(new PdfPCell(new Paragraph("日期", headFont)));
		table.addCell(new PdfPCell(new Paragraph("时间", headFont)));
		table.addCell(new PdfPCell(new Paragraph("地点", headFont)));
		table.addCell(new PdfPCell(new Paragraph("发起人", headFont)));
		table.addCell(new PdfPCell(new Paragraph("联系电话", headFont)));
		table.addCell(new PdfPCell(new Paragraph("记录人", headFont)));

		table.addCell(new PdfPCell(new Paragraph("2011-3-1", textFont)));
		table.addCell(new PdfPCell(new Paragraph("15:00-16:00", textFont)));
		table.addCell(new PdfPCell(new Paragraph("协管中心", textFont)));
		table.addCell(new PdfPCell(new Paragraph("郭丹杰", textFont)));
		table.addCell(new PdfPCell(new Paragraph("13701351782", textFont)));
		table.addCell(new PdfPCell(new Paragraph("侯丽如", textFont)));

		PdfPCell middle = new PdfPCell(new Paragraph("视频讲课情况", titleFont));
		middle.setColspan(6);
		middle.setRowspan(2);
		middle.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
		middle.setVerticalAlignment(Element.ALIGN_MIDDLE);

		PdfPCell cell = new PdfPCell(new Paragraph("人民医院", titleFont));
		cell.setColspan(1);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);// 设置内容水平居中显示
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

		table.addCell(cell);

		Paragraph title = new Paragraph("人民医院", titleFont);
		title.setAlignment("Center");
		document.add(title);
		document.add(table);

		document.close();
	}

}
