package com.wafersystems.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.vo.user.UserInfo;

public class pubUpLoad extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(pubUpLoad.class);
	 /** 
     * 允许上传的文件大小 
     */  
    private final long MAX_SIZE = 30*1024*1024;

    /**
     * 文件域的详细信息 
     */  
    private Map<String, String> fileInfo = null;  

    /** 
     * 允许上传的文件类型 
     */  
    private String[] allowedExt = new String[] {"jpg", "jpeg", "gif", "txt", "DICOM", "ppt", "pptx", "xls","xlsx","doc", "docx", "pdf"}; 

	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//文件保存路径
		File path = new File(request.getSession().getServletContext().getRealPath("/"));

		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		boolean result = upLoad(request, response, path.getAbsolutePath());
		request.setAttribute("succ", result);

		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/page/unified/forword.jsp");
		try {
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Boolean upLoad(HttpServletRequest request, HttpServletResponse response, String path) throws IOException{
		/**
		 * 实例化一个硬盘文件工厂,用来配置上传组件ServletFileUpload
		 */
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		diskFileItemFactory.setSizeThreshold(4096);// 设置上传文件时用于临时存放文件的内存大小,这里是4K.多于的部分将临时存在硬盘
		/**
		 * 采用系统临时文件目录作为上传的临时目录
		 * */
		File tempfile = new File(System.getProperty("java.io.tmpdir"));
		diskFileItemFactory.setRepository(tempfile);

		 /** 
         * 用以上工厂实例化上传组件
         * 设置最大上传尺寸
         */
        ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
        fileUpload.setSizeMax(MAX_SIZE);

        /** 
         * 调用FileUpload.settingHeaderEncoding(”UTF-8″)，这项设置可以解决路径或者文件名为乱码的问题。
         * 设置输出字符集
         */
        fileUpload.setHeaderEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        /**
         * 从request得到 所有 上传域的列表 
         */
        List<FileItem> fileList = null;
        try {
            fileList = fileUpload.parseRequest(request);
        } catch (FileUploadException e) {
            if (e instanceof SizeLimitExceededException) {
                /**
                 * 文件大小超出限制
                 */
            	logger.error("文件尺寸超过规定大小:" + MAX_SIZE + "字节");
            	request.setAttribute("message", "文件尺寸超过规定大小超过30M");
                return false;
            }
        } 
        /** 
         * 没有上传文件
         */
        if (fileList == null || fileList.size() == 0) {
            request.setAttribute("message", "您上传的文件为空，请重新上传");
            return false;
        }
        /** 
         * 得到所有上传的文件 
         * 对文件域操作 
         * 并保存每个文件的详细信息 
         */  
        Iterator<FileItem> fileItr = fileList.iterator();  
        while (fileItr.hasNext()) {
            FileItem fileItem = null;
            long size = 0;
            String userPath = null;//上传的文件名（包括扩展）
            String serverPath = null;//保存的文件名
            String fileName = null;//文件名不包括扩展
            String fileExt = null;//文件后缀
            String fileStoreName = null;//文件保存路径
            fileItem = (FileItem) fileItr.next();
            /** 
             * 忽略简单form字段而不是上传域的文件域(<input type="text" />等) 
             */  
            if (!fileItem.isFormField()) {
                /** 
                 * 得到文件的详细信息 
                 * 客户端完整路径：userPath
                 * 服务器端完整路径：serverPath 
                 * 大小：size 
                 * 文件名：fileName 
                 * 扩展名：fileExt 
                 */
                userPath = fileItem.getName();  
                size = fileItem.getSize();  
                if ("".equals(userPath) || size == 0) {  
                	request.setAttribute("message", "您上传的文件为空，请重新上传");
                    return false;  
                }  
                fileName = userPath.substring(userPath.lastIndexOf("\\") + 1);  
                fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);  
  
                /**
                 * 文件类型是否合法 
                 */
                boolean isAllow = false;
                for (int allowFlag = 0; allowFlag < allowedExt.length; allowFlag++) {
                    if (allowedExt[allowFlag].toLowerCase().equals(fileExt.toLowerCase())){
                    	isAllow = true;
                    	break;
                    }
                }  
                if (!isAllow) {
                	request.setAttribute("message", "*."+ fileExt + "文件格式不允许");
                    return false;
                }

                /**
                 * 文件存储时的相对于/的路径
                 */
                fileStoreName = "download/" + createID(request) + "." + fileExt;
                /**
                 * 文件存储时的全路径
                 */
                serverPath = path + "/" + fileStoreName;
                try {
                    /**
                     * 保存文件
                     */
                    File diskPath = new File(path+ "/download");
                    if(!diskPath.exists()) {
                        diskPath.mkdirs();
                    }
                    File diskFile = new File(serverPath);
                    if(!diskFile.exists()) {
                        diskFile.createNewFile();
                    }
                    fileItem.write(diskFile);
                    fileInfo = new HashMap<String, String>();
                    fileInfo.put("size", String.valueOf(size));
                    fileInfo.put("userpath", userPath);
                    fileInfo.put("filename",fileName);
                    fileInfo.put("ext", fileExt);
                    fileInfo.put("filepath", fileStoreName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
				try {
					/**
	                 * 在取字段值的时候，用FileItem.getString(”UTF-8″)，这项设置可以解决获取的表单字段为乱码的问题。 
	                 */
					String value = fileItem.getString("UTF-8");
					if(fileItem.getFieldName() != null)
						fileInfo.put(fileItem.getFieldName(), value);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
            }
        }
        request.setAttribute("fileinfo", fileInfo);
        request.setAttribute("message", "文件上传成功");
        return true;
	}

	private String createID(HttpServletRequest request) {
		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String id = userinfo.getUserId() + sdf.format(new Date());
		return id;
	}
}
