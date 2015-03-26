package com.wafersystems.mrbs.service;

public interface DataBackupService {

	/**
	 * 得到当前硬盘可用空间
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public long getFreeSpace(String fileName) throws Exception;

	/**
	 * 获取备份数据库大小
	 * @return
	 * @throws Exception
	 */
	public int getDbSize() throws Exception;

	/**
	 * 获取备份文件大小(包括附件和数据库文件)
	 * @param accFilePath 附件目录
	 * @return 文件大小(单位MB)
	 * @throws Exception
	 */
	public long getBackupDataSize(String accFilePath) throws Exception;

	/**
	 * 备份
	 * @param backupCatelog
	 * @param fileName
	 * @param installDir
	 * @return
	 * @throws Exception
	 */
	public boolean backup(String backupCatelog, String fileName, String installDir) throws Exception;
}
