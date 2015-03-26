package com.wafersystems.mrbs.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.DataBackupDao;
import com.wafersystems.mrbs.service.DataBackupService;
import com.wafersystems.mrbs.tag.MessageTag;
import com.wafersystems.util.DateUtil;
import com.wafersystems.util.ZipUtils;

@Service
public class DataBackupServiceImpl implements DataBackupService {

	private static final Logger logger = LoggerFactory.getLogger(DataBackupServiceImpl.class);
	private String dbName;
	private String dbUser;
	private String dbPassword;
	private DataBackupDao dataBacupDao;

	@Override
	public long getBackupDataSize(String accFilePath) throws Exception {
		return getFileSize(accFilePath) + getDbSize();
	}

	@Override
	public boolean backup(String backupPath, String dataBackupFileName, String softwareInstallDir) throws Exception{
		try{
			//备份数据库文件
			String databaseFileName = this.backupDatabase(backupPath, softwareInstallDir);
			//附件目录
			String attachmentDirectory = softwareInstallDir + "download/";
			//附件压缩后的名称
			String attachmentFileName = backupPath + "/" + dataBackupFileName;
			//压缩附件
			this.backupAttachmentFile(attachmentDirectory, attachmentFileName);
			//删除备份到附件目录的数据库文件
			new File(databaseFileName).delete();
		}catch(Exception e){
			logger.debug("DataBackupServiceImpl.backup failed.",e);
			return false;
		}
		return true;
	}

	/**
	 * 获取磁盘剩余空间
	 * @param file File（备份路径）
	 * @return long(单位：MB)
	 */
	public long getFreeSpace(String fileName) throws Exception {
		logger.debug("------------------ Begin getFreeSpace -------------------");
		long freeSpace = -1;
		try {
			File file = new File(fileName);

			//当备份路径不存在时，创建该路径
			if (!file.exists()) {
				// 检查备份路径是否合法
				if (!file.mkdirs()){
					logger.debug(fileName + " does not exist");
					return freeSpace;
				}
			}

			freeSpace = file.getUsableSpace() / (1024 * 1024);
			logger.debug(fileName + " has " + freeSpace + " MB avaiable space.");

			logger.debug("------------------ End getFreeSpace -------------------");
			return freeSpace;
		} catch (Exception e) {
			logger.error("Get a disk the remaining space error,", e);
			throw new Exception(MessageTag.getMessage("backupDirError"));
		}
	}

	@Override
	public int getDbSize() throws Exception{
		return dataBacupDao.getDatabaseSize();
	}

	@Value("${dbname}")
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	@Value("${username}")
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	@Value("${password}")
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	@Resource
	public void setDataBacupDao(DataBackupDao dataBacupDao) {
		this.dataBacupDao = dataBacupDao;
	}

	/**
	 * 获取文件大小
	 * @param fileName
	 * @return
	 */
	private static Long getFileSize(String fileName) {
		Long totalSize = 0L;
		File file = new File(fileName);
		if (file.isFile())
			return file.length();
		else {
			if (file.isDirectory()) {
				File[] contents = file.listFiles();
				for (int i = 0; i < contents.length; i++) {
					if (contents[i].isFile())
						totalSize += contents[i].length();
					else {
						if (contents[i].isDirectory())
							totalSize += getFileSize(contents[i].getPath());
					}
				}
			}
		}
		return totalSize / (1024 * 1024);
	}

	/**
	 * 实现数据库备份方法
	 * 
	 * @param backupCatelog 绝对路径
	 * @param type 备份类型(0-自动,1-手动)
	 */
	private String backupDatabase(String backupPath, String softwareInstallDir) throws Exception {
		logger.debug("------------------ Begin execute database Backup -------------------");

		softwareInstallDir = softwareInstallDir.toLowerCase();
		// src\Tomcat\webapps\ROOT,src\mysql\bin，E:\javawork\UC1220\WebRoot\
		// 自动备份服务中的mysqldump.exe文件改为绝对路径形式
		String mysqlInstallDir = softwareInstallDir.substring(0, softwareInstallDir.indexOf("tomcat")) + "mysql/bin/";
//		String mysqlInstallDir = "E:/mysql-5.5.19-win32/bin/";
		//mysql备份文件放到备份目录
		String dbTempFileName = backupPath + "/" + DateUtil.getCurrentDate() + ".sql";
		//存放到附件目录
		String dbFileName = softwareInstallDir + "download/" + DateUtil.getCurrentDate() + ".sql";

		try {
			int exitValue = -1;
			/**
			 * 根据输入备份参数组装数据库备份命令 Mysql数据库备份命令参数为 mysqldump -u ipt -psystem ipt >
			 * c:\voicemate_backup_2006-8-16_12-00-00.sql
			 */
			// 添加\"是为了防止mysqlInstallDir路径中有空格,--skip-lock-tables处理when using LOCK TABLES问题
			String backupCommand = "cmd.exe /c \"" + mysqlInstallDir + "mysqldump.exe\" -u" + dbUser
					+ " -p" + dbPassword + " " + dbName + " --skip-lock-tables > " + dbTempFileName;
			logger.debug("Mysql备份命令" + backupCommand);

			int result = -1;
			String comment = "";

			//取得当前JVM的运行时环境，产生一个本地进程
			Process proc = Runtime.getRuntime().exec(backupCommand);

			// 通过获取数据流的方式判断备份命令是否执行成功
			InputStream stream = proc.getErrorStream();
			InputStreamReader isr = new InputStreamReader(stream);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			StringBuffer buffer = new StringBuffer();
			while ((line = br.readLine()) != null) {
				buffer.append(line);
			}

			// 等待命令执行完毕，然后返回外部命令执行的结果
			proc.waitFor();

			//如果返回值为0，则备份成功，否则备份失败
			exitValue = (proc.exitValue());

			// 给出备注信息，成功提示成功及备份路径信息，错误则给出错误原因，错误原因从错误输出流获取
			result = (exitValue == 0) ? 0 : 1;
			if (result == 0) {
				this.copyFile(dbTempFileName, dbFileName);
				new File(dbTempFileName).delete();
				logger.debug("Database backup success");
			} else {
				comment = buffer.toString();
				logger.error("Database backup failed, reason:" + comment);
				return null;
			}
		}catch (Exception e) {
			logger.error("Database backup failed", e);
			return null;
		}
		logger.debug("------------------ End executeBackup -------------------");
		return dbFileName;
	}

	/**
	 * 备份附件
	 * @param srcFile
	 * @param destFile
	 * @throws Exception
	 */
	private void backupAttachmentFile(String srcFile, String destFile) throws Exception{
		ZipUtils.archive(srcFile, destFile);
	}

	/**
	 * 复制文件
	 * @param sourceFile
	 * @param targetFile
	 * @throws Exception
	 */
	private void copyFile(String sourceFile,String targetFile) throws Exception {
		FileInputStream fin = null;
		FileOutputStream fou = null;
		try{
			// 第一步：获取通道
			fin = new FileInputStream(sourceFile);  //输入
			fou = new FileOutputStream(targetFile); //输出

			FileChannel fc = fin.getChannel();
			FileChannel fo = fou.getChannel();
			// 第二步：创建缓冲区
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			while(fc.read(buffer)!=-1){
                buffer.flip();
                while(buffer.hasRemaining()){
                	fo.write(buffer);
                }
                buffer.clear();  
            }  
		}finally{
			fin.close();
			fou.close();
		}
	}
}