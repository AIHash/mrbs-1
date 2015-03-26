package com.wafersystems.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class pubDownLoad extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(pubDownLoad.class);

	public void service(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		download(request, response);
	}

	 /** 
     * 支持中文,文件名长度无限制 
     * 不支持国际化 
     */  
    public void download(HttpServletRequest request, HttpServletResponse response){
    	response.setContentType("text/html;charset=utf-8");
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        String filepath = request.getParameter("filepath");//文件下载的位置
        String filename = "";
        try {
        	filename = java.net.URLDecoder.decode(request.getParameter("filename"),"utf-8"); //文件名
        	File file = new File(request.getSession().getServletContext() .getRealPath("/") + filepath);
  
            response.setContentType("application/x-msdownload;");
            response.setHeader("Content-disposition", "attachment; filename=" + new String(filename.getBytes("GBK"),"ISO8859-1")); 
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.setHeader("Connection", "close");

            bis = new BufferedInputStream(new FileInputStream(file));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2 * 1024];
            int bytesRead;
            while((bytesRead = bis.read(buff, 0, buff.length)) != -1){
                bos.write(buff, 0, bytesRead);
            }
            /**
             * 此处不要关闭response中的outstream，否则会造成下载文件不可用
             */
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
			try {
				if (bis != null)
					bis.close();
				if (bos != null)
					bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        logger.debug(filename + "下载完成");
    }
}