/**
 * @Title: TemplateController.java
 * @Package com.wafersystems.controller
 * @Description: TODO(用一句话描述该文件做什么)
 * @author harvoo
 * @date 2013-4-15 下午3:29:56
 */
package com.wafersystems.mrbs.web.media;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.service.media.MediaService;
import com.wafersystems.mrbs.vo.meeting.Media;
import com.wafersystems.mrbs.vo.meeting.MediaRole;
import com.wafersystems.mrbs.vo.user.UserInfo;

 

/** 
* @ClassName:  MediaController
* @Description: 数字资源共享
* @author yangs
*/

@Controller
@RequestMapping("/media")
public class MediaController {
  
	private static final Logger logger = LoggerFactory.getLogger(MediaController.class);
    private Map<String, String> fileInfo = null;  

	private MediaService mediaService;
	@Resource
	public void setMediaService(MediaService mediaService) {
		this.mediaService = mediaService;
	}
	@InitBinder
	protected void initBinder(WebDataBinder dataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@RequestMapping(value="/mediaQuery")
	public String mediaQuery(HttpServletRequest request,Model model)
	{

		return "media/mediaQuery";
	}
	@RequestMapping(value="/mediaListQuery")
	public String mediaListQuery(Model model,HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			
			return "media/mediaListQuery";
		} catch (Exception e) {
			e.printStackTrace();		
			response.getWriter().write("fail");
			logger.error("Error icumonitor.adminIcuMonitorEvaluationList",e);
		}
		return null;
	}
	
	
	@RequestMapping(value="/mediaList")
	public String mediaList(HttpServletRequest request, Model model) throws Throwable{
		try{
			UserInfo user = (UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
			logger.debug("Enter MediaController.mediaList");
			Media media = new Media();
			media.setMedianame(request.getParameter("medianame"));
			String mediaType = request.getParameter("mediaType");
			if(mediaType != null &&!"".equals(mediaType)){
				media.setMediaType(Integer.parseInt(mediaType));
			}
			 
		    List<Media> medialist = mediaService.getMediaList(new PageSortModel(request), media,user).getResultlist();
	        request.setAttribute("report_data", medialist);
	        request.setAttribute("userId", user.getUserId());
	        String requestParameter = "medianame="+request.getParameter("medianame")+"&mediaType="+mediaType;
	        model.addAttribute("requestParameter",requestParameter);
		     // model.addAttribute("report_data", medialist);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "media/mediaListQuery";
	}
	 
		@RequestMapping("delMedia/{mediaId}")
		public String delMedia(HttpServletRequest request,
				HttpServletResponse response, @PathVariable Integer mediaId)
				throws Throwable {
			try {
				mediaService.delMedia(mediaId);
				response.getWriter().print("succ");
			} catch (Throwable e) {
				logger.debug("Execute delMedia Error ", e);
				response.getWriter().print("error");
			}
			logger.debug("Over execute delMedia, id:" + mediaId);
			return null;
		}
		@RequestMapping(value="/mediauploadView")
		public String mediauploadView(Model model,HttpServletRequest request,HttpServletResponse response) throws IOException{
			try {
				
				return "media/mediaupload";
			} catch (Exception e) {
				e.printStackTrace();		
				response.getWriter().write("fail");
				logger.error("Error icumonitor.adminIcuMonitorEvaluationList",e);
			}
			return null;
		}
		@RequestMapping("/saveMediaLib")
		public String saveMediaLib( HttpServletRequest request,
				HttpServletResponse response)  {
			
			try {
				//保存上传文件
	            ArrayList<String> rowIdList= new ArrayList<String>();
				String  returnFlag = this.upLoadMedia(request, response,rowIdList);
				Media returnMedia = new Media();
				if(returnFlag.equals("0")){
					if( String.valueOf(GlobalConstent.MEDIA_PATH_TYPE_LOCAL).equals(fileInfo.get("upType"))){//上传的是本地文件

					 	returnMedia.setFilename(fileInfo.get("filename"));
		                returnMedia.setFilepath(fileInfo.get("filepath"));	                      
		                returnMedia.setSize(Integer.valueOf(fileInfo.get("size")));               
						//获取界面相关属性并保存
						
					}else if(String.valueOf(GlobalConstent.MEDIA_PATH_TYPE_NETWORK).equals(fileInfo.get("upType"))){//说明是远程连接
						String urlp = fileInfo.get("urlpath");
						if(urlp.indexOf("http:") < 0){
							urlp = "http://"+urlp;
						} 
						returnMedia.setFilepath(urlp);
					}
					UserInfo user = (UserInfo) request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
	                returnMedia.setAuditUser(user.getUserId());
					returnMedia.setAuditTime(new Date());
					String mediaName = fileInfo.get("medianame");
	                returnMedia.setMedianame(mediaName);//fileInfo.get("");
					int mediaLength = Integer.valueOf(fileInfo.get("length"));//播放时间
					String description = fileInfo.get("description");  
			        int uptype= Integer.valueOf(fileInfo.get("upType")); 
			        int mediaTypeId=Integer.valueOf( fileInfo.get("mediaTypeId"));
					returnMedia.setMediaUrl("");            
	                returnMedia.setMediaLength(mediaLength);
	                returnMedia.setMediaType(mediaTypeId);
	                returnMedia.setPathType(uptype);
	                returnMedia.setDescription(description);
	                mediaService.saveMedia(returnMedia);
	                if(rowIdList != null && rowIdList.size() >0){
	                	for(String rowId:rowIdList){
	                		MediaRole mediaRole = new MediaRole();
	                		mediaRole.setMediaId(returnMedia.getId());
	                		mediaRole.setRoleId(Integer.parseInt(rowId));
	                		mediaService.saveMediaRole(mediaRole);
	                		
	                	}
	                }
					request.getSession().setAttribute("returnmessage", "文件上传成功!");
				}else if(returnFlag.equals("-1")){
					request.getSession().setAttribute("returnmessage", "上传的文本必须为UTF-8格式");
				}
			} catch (Exception e) {
				logger.error("添加共享资源失败", e);
				request.getSession().setAttribute("returnmessage", "erroe");
			} catch (Throwable e) {
				logger.error("添加共享资源失败,Dao操作异常", e);
				request.getSession().setAttribute("returnmessage", "error");
				e.printStackTrace();
			}
			 return "redirect:/media/mediauploadView";
		
			
		}
		/**
		 * add by wangzhenglin 重命名共享资源在服务器的存储文件名
		 * @param request
		 * @return
		 */
		private String createID(HttpServletRequest request) {
			UserInfo userinfo = (UserInfo) request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
			String id = userinfo.getUserId() + sdf.format(new Date());
			return id;
		}
		/**
		 * add by wangzhenglin 上传共享资源
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		private String  upLoadMedia( HttpServletRequest request,
				HttpServletResponse response,ArrayList<String> rowIdList)throws Exception{
			String returnFlag = "0";
			String aa = request.getParameter("uploadType");
			File file = new File(request.getSession().getServletContext().getRealPath("/"));
			String path = file.getAbsolutePath(); 

			DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
			diskFileItemFactory.setSizeThreshold(4096);// 设置上传文件时用于临时存放文件的内存大小,这里是4K.多于的部分将临时存在硬盘

			File tempfile = new File(System.getProperty("java.io.tmpdir"));
			diskFileItemFactory.setRepository(tempfile);

	        ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
	        fileUpload.setHeaderEncoding("UTF-8");
	        response.setContentType("text/html;charset=utf-8");
	        List<FileItem> fileList = null;
	        fileList = fileUpload.parseRequest(request);	        
	        /** 
	         * 没有上传文件
	         */
	        if (fileList == null || fileList.size() == 0) {
	            request.setAttribute("message", "您上传的文件为空，请重新上传");
	            throw new Exception("您上传的文件为空，请重新上传");
	        }
	        /** 
	         * 得到所有上传的文件 
	         * 对文件域操作 
	         * 并保存每个文件的详细信息 
	         */  
	        Iterator<FileItem> fileItr = fileList.iterator();  
            fileInfo = new HashMap<String, String>();
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
	                userPath = fileItem.getName();  
	                size = fileItem.getSize();  
	                if ("".equals(userPath) || size == 0) {  
	                	
	                	continue;
	                }  
	                fileName = userPath.substring(userPath.lastIndexOf("\\") + 1);  
	                fileExt = fileName.substring(fileName.lastIndexOf(".") + 1); 
	                fileExt = fileExt.toLowerCase();
	                /**
	                 * 文件存储时的相对于/的路径
	                 */
	                fileStoreName = "resources/download/" + createID(request) + "." + fileExt;
	                /**
	                 * 文件存储时的全路径
	                 */
	                serverPath = path + "/" + fileStoreName;

                    File diskPath = new File(path+ "/resources/download");
                    if(!diskPath.exists()) {
                        diskPath.mkdirs();
                    }
                    File diskFile = new File(serverPath);
                     
                	if(!diskFile.exists()) {
                        diskFile.createNewFile();
                    }
                    fileItem.write(diskFile);
                    fileItem.write(diskFile);
                    fileInfo.put("size", String.valueOf(size));
                    fileInfo.put("userpath", userPath);
                    fileInfo.put("filename",fileName);
                    fileInfo.put("ext", fileExt);
                    fileInfo.put("filepath", fileStoreName);
                    boolean flag = true;
                    if(fileExt.equals("txt")){
                    	flag = this.checkTXTEncod(diskFile);
                    }
                    if(flag){
                    	 returnFlag = "0";
                    }else{
                    	returnFlag ="-1";
                    }
                    
	            } else {
					try {
						/**
		                 * 在取字段值的时候，用FileItem.getString(”UTF-8″)，这项设置可以解决获取的表单字段为乱码的问题。 
		                 */
						String value = fileItem.getString("UTF-8");
						if(fileItem.getFieldName() != null)
							if("roleid".equals(fileItem.getFieldName())){
								rowIdList.add(value);
							}else{
								fileInfo.put(fileItem.getFieldName(), value);
							}
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
	            }
	        }
	        return returnFlag;
	
		}
		
		/**
		 *  查看文本
		 */
		@RequestMapping("readtxt")
		public String readtxt(HttpServletRequest request,HttpServletResponse response)throws Exception{
			String path = request.getSession().getServletContext().getRealPath("");
			StringBuffer buffer = new StringBuffer();
			String filepath = request.getParameter("filepath");
			try{
				String afPath = path+"\\"+filepath;
				BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(afPath),"UTF-8"));  
		        String line; // 用来保存每行读取的内容
		        line = reader.readLine(); // 读取第一行
		        int i=0;
		        while (line != null && i<100) { // 如果 line 为空说明读完了
		            buffer.append(line); // 将读到的内容添加到 buffer 中
		            buffer.append("\n"); // 添加换行符
		            line = reader.readLine(); // 读取下一行
		            i++;
		        }
		        reader.close();
		        buffer.append("\n\n");
		        if(i >= 99){
		        	buffer.append("      需要查看更多请下载后查看…… ……");
		        }
		        buffer.append("\n\n");
		        String txt = buffer.toString();
		        //response.setCharacterEncoding("utf-8");
				response.getWriter().write(txt);
				response.getWriter().flush();
			}catch(Exception e){
				e.printStackTrace();
				response.getWriter().write("error");
				response.getWriter().flush();
			}
			return null;
		}
		
		@RequestMapping("readImg")
		public String readImg(HttpServletRequest request,HttpServletResponse response) throws Exception{
			 String ft = "F:/BaiduMusic/Images/0.jpg";
			 File file = new File(ft);
			 JSONArray json = JSONArray.fromObject(file);
			  
			 response.setContentType("text/Xml;charset=UTF-8");
			 PrintWriter out = null;
			 out = response.getWriter();
			 out.print(json);
			return null;
		}
		@RequestMapping(value="/mediaView")
		public String mediaView(HttpServletRequest request,Model model)
		{

			return "media/media_view";
		}
		
		
		public boolean checkTXTEncod(File file){
			boolean flag = false;
			try {
				InputStream in= new java.io.FileInputStream(file);  
				byte[] b = new byte[3];  
				in.read(b);  
				in.close();  
				if (b[0] == -17 && b[1] == -69 && b[2] == -65) {
					flag = true;
				}   
			} catch (Exception e) {
				e.printStackTrace();
			} 
			 return flag;
		}
}
