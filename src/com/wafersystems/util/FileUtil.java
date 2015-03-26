package com.wafersystems.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件操作
 * @author moon
 * @create_date 2008-10-7 下午04:49:21
 * @version V1.0
 *
 * @tag
 */
public class FileUtil {
	/**
	 * copy 单一文件
	 * @param in
	 * @param out
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @param 
	 * @throws
	 * @return
	 */
	public void CopyFile(File in, File out) throws  FileNotFoundException,IOException {
	     FileInputStream fis  = new FileInputStream(in);
	     FileOutputStream fos = new FileOutputStream(out);
	     byte[] buf = new byte[1024];
	     int i = 0;
	     while((i=fis.read(buf))!=-1) {
	       fos.write(buf, 0, i);
	     }
	     fos.flush();
	     fis.close();
	     fos.close();
	}
	
	/**
	 * copy目录下所有文件
	 * @param in
	 * @param out
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @param in, out 目录
	 * @throws
	 * @return
	 */
	public void CopyFileByPath(File in, File out) throws  FileNotFoundException,IOException {
	     if(in.exists()&&in.isDirectory()){
	    	 out.mkdir();
	    	 File[] list = in.listFiles();
				for(File i : list){
					CopyFileByPath(i, new File(out.getAbsolutePath() + "/" + i.getName()+(i.isDirectory()?"/":"")));
				}
	     }else if(in.exists()&& in.isFile()){
	    	 CopyFile(in, out);
	     }
	}
	
	/**
	 * 删除目录下所有文件
	 * @param path
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @param path目录
	 * @throws
	 * @return
	 */
	public void DeletePath(File path)throws  FileNotFoundException,IOException {
		if(path == null) return;
		if(path.exists() && path.isDirectory()){
			File[] list = path.listFiles();
			for(File i : list){
				DeletePath(i);
			}
			path.delete();
		}else if(path.exists()){
			path.delete();
		}
	}
}



