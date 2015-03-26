package com.wafersystems.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporter;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

public class ReportUtil {
	private static final Logger logger = LoggerFactory.getLogger(ReportUtil.class);
	
	public static Map<String,String> getHtmlParameters(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		// 从表单获取Map参数集合
		Map<String, String[]> map = request.getParameterMap();
		// 传入报表Map参数集合
		Map<String,String> parameterMap = new HashMap<String,String>();
		Set<Entry<String, String[]>> set = map.entrySet();
		Iterator<Entry<String, String[]>> iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String[]> entry = iterator.next();
			String[] s = entry.getValue();
			parameterMap.put(entry.getKey(), new String(s[0].getBytes("ISO-8859-1"),"UTF-8"));
		}
		return parameterMap;
	}

	// .jasper格式文件路径
	public static String getJasperPath(HttpServletRequest request) {
		return "/page/report/jasper/reportFile/"
				+ request.getParameter("filename") + ".jasper";
	}
	
	public static void createHtmlView(HttpServletRequest request, HttpServletResponse response,ServletContext context,Map parameters,String exportURL,String searchURL) throws ServletException, IOException{
		PrintWriter out = response.getWriter();
		JasperPrint jasperPrint = (JasperPrint) request.getSession().getAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE);
		List list = null;
		java.sql.Connection conn = null;

		try {
			if (request.getParameter("reload") != null
					|| jasperPrint == null) {
				//compile jrxml to jasper
				//JasperCompileManager.compileReportToFile(context.getRealPath(this.getJrxmlPath(request)));
				String reportFileName = context.getRealPath(ReportUtil.getJasperPath(request));
				File reportFile = new File(reportFileName);
				JasperReport jasperReport = (JasperReport) JRLoader
						.loadObject(reportFile.getPath());
				
//				conn = MysqlToolsForReport.getConn();
				jasperPrint =JasperFillManager.fillReport(jasperReport,parameters,conn);
				request.getSession().setMaxInactiveInterval(240 * 60);
				request.getSession()
						.setAttribute(
								ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,
								jasperPrint);
			}
			
			int pageIndex = 0;
			int lastPageIndex = 1;
			if (jasperPrint != null) {
				list = jasperPrint.getPages();
			}
			if (list != null)
			{
				lastPageIndex = jasperPrint.getPages().size() - 1;
			}
			String pageStr = request.getParameter("page");

			if (pageStr != null) {
				pageIndex = Integer.parseInt(pageStr);
			}

			if (pageIndex < 0)
			{
				pageIndex = 0;
			}

			if (pageIndex > lastPageIndex)
			{
				pageIndex = lastPageIndex;
			}
			
			//exportURL=java.net.URLEncoder.encode(exportURL); 
			
			out.println("<html>");
			out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
			out.println("<head>");
			out.println(" <style type='text/css'>");
			out.println("*{margin:0 auto;padding:0;}");
			out.println("a {text-decoration: none}");
			out.println("</style>");
			out.println("</head>");
			out.println("<body  bgcolor='#efeeec'>");
			out.println("<div style='overflow-y: scroll;height:100%;text-align:center;'>");
			out.println("<table width='100%' border='0' style='padding:6px;margin-top:12px;margin-bottom:10px;'>");
			out.println("<tr>");
			out.println("<td width='50%'>&nbsp;</td>");
			out.println("<td align='left'>");
			out.println("<table width='100%' style='border:1px solid #000000;'>");
			out.println("<tr>");
			out.println("<td>&nbsp;</td>");
			out.println("<td><a STYLE='cursor:hand' onclick=javascript:window.location.href=encodeURI('"+request.getContextPath()+exportURL+"&type=HTML')><img src='"+request.getContextPath()+"/page/report/jasper/img/reload.gif' border='0'></a></td>");
			out.println("<td>&nbsp;&nbsp;&nbsp;</td>");

			out.println("<td><a STYLE='cursor:hand' onclick=javascript:window.location.href=encodeURI('"+request.getContextPath()+exportURL+"&type=XLS')><img src='"+request.getContextPath()+"/page/report/jasper/img/img_excel.gif' border='0'></a></td>");
			out.println("<td><a STYLE='cursor:hand' onclick=javascript:window.location.href=encodeURI('"+request.getContextPath()+exportURL+"&type=PDF')><img src='"+request.getContextPath()+"/page/report/jasper/img/img_pdf.gif' border='0'></a></td>");
			out.println("<td><a STYLE='cursor:hand' onclick=javascript:window.location.href=encodeURI('"+request.getContextPath()+exportURL+"&type=RTF')><img src='"+request.getContextPath()+"/page/report/jasper/img/img_word.gif' border='0'></a></td>");
			out.println("<td>&nbsp;&nbsp;&nbsp;</td>");
			
			if (pageIndex > 0)
			{
				out.println("<td><a STYLE='cursor:hand' onclick=javascript:window.location.href=encodeURI('"+request.getContextPath()+exportURL+"&type=HTML&page=0')><img src='"+request.getContextPath()+"/page/report/jasper/img/first.GIF' border='0'></a></td>");
				out.println("<td><a STYLE='cursor:hand' onclick=javascript:window.location.href=encodeURI('"+request.getContextPath()+exportURL+"&type=HTML&page="+(pageIndex - 1)+"')><img src='"+request.getContextPath()+"/page/report/jasper/img/previous.GIF' border='0'></a></td>");
			} else {
				out.println("<td><img src='"+request.getContextPath()+"/page/report/jasper/img/first_grey.GIF' border='0'></td>");
				out.println("<td><img src='"+request.getContextPath()+"/page/report/jasper/img/previous_grey.GIF' border='0'></td>");
			}

			String page = request.getParameter("page");

			if (page != null) {
				pageIndex = Integer.parseInt(page);
			}

			if (pageIndex < lastPageIndex) {
				out.println("<td><a STYLE='cursor:hand' onclick=javascript:window.location.href=encodeURI('"+request.getContextPath()+exportURL+"&type=HTML&page="+(pageIndex + 1)+"')><img src='"+request.getContextPath()+"/page/report/jasper/img/next.GIF' border='0'></a></td>");
				out.println("<td><a STYLE='cursor:hand' onclick=javascript:window.location.href=encodeURI('"+request.getContextPath()+exportURL+"&type=HTML&page="+lastPageIndex+"')><img src='"+request.getContextPath()+"/page/report/jasper/img/last.GIF' border='0'></a></td>");
			} else {
				out.println("<td><img src='"+request.getContextPath()+"/page/report/jasper/img/next_grey.GIF' border='0'></td>");
				out.println("<td><img src='"+request.getContextPath()+"/page/report/jasper/img/last_grey.GIF' border='0'></td>");
			}
			
			out.println("<td>&nbsp;&nbsp;&nbsp;</td>");
			out.println("<td><a href='"+searchURL+"'><img src='"+request.getContextPath()+"/page/report/jasper/img/back.gif' border='0'></a></td>");
			
			
			out.println("<td width='100%'>&nbsp;</td>");
			out.println("</tr>");
			out.println("</table>");
			out.println("</td>");
			out.println("<td width='50%'>&nbsp;</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td width='50%'>&nbsp;</td>");
			out.println("<td align='center'>");


			JRHtmlExporter exporter = new JRHtmlExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
			exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, request.getContextPath()+"/servlets/image?image=");
			
			exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR,Boolean.FALSE);
			exporter.setParameter(JRHtmlExporterParameter.SIZE_UNIT,"pt");
			
			exporter.setParameter(JRExporterParameter.PAGE_INDEX,new Integer(pageIndex));
			exporter.setParameter(JRHtmlExporterParameter.HTML_HEADER, "");
			exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML, "");
			exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER, "");
			exporter.exportReport();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		out.println("</td>");
		out.println("<td width='50%'>&nbsp;</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
		out.flush();
		out.close();
	}
	
	public static void createPDF(HttpServletRequest request, HttpServletResponse response,ServletContext context,Map parameters,String filename) throws ServletException, IOException{
		JasperPrint jasperPrint = (JasperPrint) request.getSession().getAttribute(
				ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE);
		java.sql.Connection conn = null;
		JRPdfExporter pdfexporter;
		try {
			//compile jrxml to jasper
			//JasperCompileManager.compileReportToFile(context.getRealPath(this.getJrxmlPath(request)));
			
			String reportFileName = context.getRealPath(ReportUtil.getJasperPath(request));
			File reportFile = new File(reportFileName);
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());
			
//			conn = MysqlToolsForReport.getConn();
			jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, conn);
			pdfexporter = new JRPdfExporter();
			pdfexporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			pdfexporter.setParameter(JRExporterParameter.OUTPUT_STREAM,baos);
			pdfexporter.exportReport();
			byte[] bytes = baos.toByteArray();

			if (bytes != null && bytes.length > 0)
			{
				response.setContentType("application/pdf");  
				response.setHeader("Content-Disposition", "attachment; filename=\""
						+ URLEncoder.encode(filename, "UTF-8") + ".pdf\"");
				response.setContentLength(bytes.length);
				ServletOutputStream ouputStream = response
						.getOutputStream();
				try
				{
					ouputStream.write(bytes, 0, bytes.length);
					ouputStream.flush();
				}finally{
					if (ouputStream != null)
					{
						try{
							ouputStream.close();
						}catch (IOException ex){
						}
					}
				}
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}finally{
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void createXLS(HttpServletRequest request, HttpServletResponse response,ServletContext context,Map parameters,String filename) throws ServletException, IOException{
		JasperPrint jasperPrint = (JasperPrint) request.getSession().getAttribute(
				ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE);
		java.sql.Connection conn = null;
		JRXlsAbstractExporter xlsexporter;
		try {
			//compile jrxml to jasper
			//JasperCompileManager.compileReportToFile(context.getRealPath(this.getJrxmlPath(request)));
			
			String reportFileName = context.getRealPath(ReportUtil.getJasperPath(request));
			File reportFile = new File(reportFileName);
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());
			
//			conn = MysqlToolsForReport.getConn();
			jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, conn);
			xlsexporter = new JRXlsExporter();
			xlsexporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			xlsexporter.setParameter(JRExporterParameter.OUTPUT_STREAM,baos);
			xlsexporter.setParameter(JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.FALSE);
			xlsexporter.setParameter(JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);
			xlsexporter.exportReport();

			byte[] bytes = baos.toByteArray();
			if ((bytes != null) && (bytes.length > 0))
			{
				response.setContentType("application/xls");
                response.setHeader("Content-Disposition", "inline; filename=\""  
                        + URLEncoder.encode(filename, "UTF-8") + ".xls\"");  
				response.setContentLength(bytes.length);
				ServletOutputStream ouputStream = response.getOutputStream();
				try{
					ouputStream.write(bytes, 0, bytes.length);
					ouputStream.flush();
				}finally{
					if (ouputStream != null){
						try{
							ouputStream.close();
						}catch (IOException ex)
						{
						}
					}
				}
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}finally{
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void createRTF(HttpServletRequest request, HttpServletResponse response,ServletContext context,Map parameters,String filename) throws ServletException, IOException{
		JasperPrint jasperPrint = (JasperPrint) request.getSession().getAttribute(
				ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE);
		java.sql.Connection conn = null;
		JRRtfExporter rtfexporter;
		
		try {
			//compile jrxml to jasper
			//JasperCompileManager.compileReportToFile(context.getRealPath(this.getJrxmlPath(request)));
			
			String reportFileName = context.getRealPath(ReportUtil.getJasperPath(request));
			File reportFile = new File(reportFileName);
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());
			
//			conn = MysqlToolsForReport.getConn();
			jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, conn);
			rtfexporter = new JRRtfExporter();
			rtfexporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			rtfexporter.setParameter(JRExporterParameter.OUTPUT_STREAM,baos);
			rtfexporter.exportReport();
			byte[] bytes = baos.toByteArray();
			if (bytes != null && bytes.length > 0)
			{
				response.setContentType("application/rtf");
                response.setHeader("Content-Disposition", "inline; filename=\""  
                        + URLEncoder.encode(filename, "UTF-8") + ".rtf\"");  
				response.setContentLength(bytes.length);
				ServletOutputStream ouputStream = response
						.getOutputStream();
				try{
					ouputStream.write(bytes, 0, bytes.length);
					ouputStream.flush();
				}finally{
					if (ouputStream != null)
					{
						try{
							ouputStream.close();
						}catch (IOException ex){
						}
					}
				}
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}finally{
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}