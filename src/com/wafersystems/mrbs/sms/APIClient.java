package com.wafersystems.mrbs.sms;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class APIClient {
	
	private static Logger logger = LoggerFactory.getLogger(APIClient.class);
	
	public static final int IMAPI_SUCC = 0;
	public static final int IMAPI_CONN_ERR = -1;
	public static final int IMAPI_CONN_CLOSE_ERR = -2;
	public static final int IMAPI_INS_ERR = -3;
	public static final int IMAPI_DEL_ERR = -4;
	public static final int IMAPI_QUERY_ERR = -5;
	public static final int IMAPI_DATA_ERR = -6;
	public static final int IMAPI_API_ERR = -7;
	public static final int IMAPI_DATA_TOOLONG = -8;
	public static final int IMAPI_INIT_ERR = -9;
	public static final int IMAPI_IFSTATUS_INVALID = -10;
	public static final int IMAPI_GATEWAY_CONN_ERR = -11;
	private String dbUser = null;

	private String dbPwd = null;

	private String apiCode_ = null;

	private String dbUrl = null;

	private Connection conn = null;

	private RPTItem[] arrayOfRPTItem1 = null;

	public int init(String dbIP, String dbUser, String dbPwd, String apiCode) {
		release();

		this.dbUser = dbUser;
		this.dbPwd = dbPwd;
		this.apiCode_ = apiCode;
		this.dbUrl = ("jdbc:mysql://" + dbIP + "/im");

		return testConnect();
	}

	public int init(String dbIP, String dbUser, String dbPwd, String apiCode, String dbName) {
		release();

		this.dbUser = dbUser;
		this.dbPwd = dbPwd;
		this.apiCode_ = apiCode;
		this.dbUrl = ("jdbc:mysql://" + dbIP + "/" + dbName);

		return testConnect();
	}

	public int sendSM(String mobile, String content, long smID) {
		return sendSM(new String[] { mobile }, content, smID, smID);
	}

	public int sendSM(String[] mobiles, String content, long smID) {
		return sendSM(mobiles, content, smID, smID);
	}

	public int sendSM(String[] mobiles, String content, long smID, long srcID) {
		return sendSM(mobiles, content, smID, srcID, "");
	}

	public int sendSM(String[] mobiles, String content, String sendTime,
			long smID, long srcID) {
		return sendSM(mobiles, content, smID, srcID, "", sendTime);
	}

	public int sendSM(String mobile, String content, long smID, String url) {
		return sendSM(new String[] { mobile }, content, smID, url);
	}

	public int sendSM(String[] mobiles, String content, long smID, String url) {
		return sendSM(mobiles, content, smID, smID, url);
	}

	public int sendSM(String[] mobiles, String content, long smID, long srcID,
			String url) {
		return sendSM(mobiles, content, smID, smID, url, "");
	}

	public int sendSM(String[] mobiles, String content, long smID, long srcID,
			String url, String sendTime) {
		if (this.dbUrl == null) {
			return -9;
		}
		if ((mobiles == null) || (mobiles.length == 0)) {
			return -6;
		}

		StringBuffer mobileBuf = new StringBuffer();
		for (int i = 0; i < mobiles.length; i++) {
			mobileBuf.append(",").append(mobiles[i]);
		}
		if (mobileBuf.length() > 1)
			mobileBuf.delete(0, 1);
		else {
			return -6;
		}

		String contenttmp = replaceSpecilAlhpa(content);
		if (contenttmp.length() < 1) {
			return -6;
		}

		if (contenttmp.length() > 2000) {
			contenttmp = contenttmp.substring(0, 2000);
		}

		if ((!checkSmID(smID)) || (!checkSmID(srcID))) {
			return -6;
		}

		if ((url != null) && (url.length() > 0)) {
			if (url.length() > 110) {
				return -8;
			}
			if ((url + contenttmp).length() > 120) {
				return -8;
			}

		}

		int ret = checkGatConn();
		if (ret != 1) {
			return ret;
		}

		if ((!"".equals(sendTime)) && (isDateTime(sendTime) == null)) {
			return -6;
		}

		return mTInsert(mobileBuf.toString(), contenttmp, smID, srcID, url,
				sendTime);
	}

	public MOItem[] receiveSM() {
		if (this.dbUrl == null) {
			return null;
		}
		if (this.conn == null) {
			int state = initConnect();
			if (state != 0) {
				return null;
			}
		}
		Statement st = null;
		ResultSet rs = null;

		String getMoSql = "select * from api_mo_" + this.apiCode_
				+ " limit 200";
		String delMoSql = "delete from api_mo_" + this.apiCode_
				+ " where AUTO_SN in (";

		ArrayList moList = new ArrayList();
		StringBuffer snBuf = new StringBuffer("-1");
		try {
			st = this.conn.createStatement();
			rs = st.executeQuery(getMoSql);
			while (rs.next()) {
				MOItem mItemtmp = new MOItem();
				mItemtmp.setSmID(rs.getLong("SM_ID"));
				mItemtmp.setContent(rs.getString("CONTENT"));
				mItemtmp.setMobile(rs.getString("MOBILE"));
				mItemtmp.setMoTime(rs.getString("MO_TIME"));

				snBuf.append(",").append(rs.getString("AUTO_SN"));
				moList.add(mItemtmp);
			}
			rs.close();
			st.executeUpdate(delMoSql + snBuf.toString() + ")");
		} catch (Exception e) {
			releaseConn();
			logger.error("Error APIClient.receiveSM",e);
			return null;
		} finally {
			closeStatment(st);
		}

		MOItem[] moItem = new MOItem[0];
		return (MOItem[]) moList.toArray(moItem);
	}

	public MOItem[] receiveSM(long srcID, int amount) {
		if (this.dbUrl == null) {
			return null;
		}
		if (this.conn == null) {
			int state = initConnect();
			if (state != 0) {
				return null;
			}
		}
		Statement st = null;
		ResultSet rs = null;

		String getMoSql = "select * from api_mo_" + this.apiCode_;
		if (srcID != -1L) {
			getMoSql = getMoSql + " where SM_ID=" + srcID;
		}
		if (amount != -1) {
			getMoSql = getMoSql + " limit " + amount;
		}
		String delMoSql = "delete from api_mo_" + this.apiCode_
				+ " where AUTO_SN in (";

		ArrayList moList = new ArrayList();
		StringBuffer snBuf = new StringBuffer("-1");
		try {
			st = this.conn.createStatement();
			rs = st.executeQuery(getMoSql);
			while (rs.next()) {
				MOItem mItemtmp = new MOItem();
				mItemtmp.setSmID(rs.getLong("SM_ID"));
				mItemtmp.setContent(rs.getString("CONTENT"));
				mItemtmp.setMobile(rs.getString("MOBILE"));
				mItemtmp.setMoTime(rs.getString("MO_TIME"));

				snBuf.append(",").append(rs.getString("AUTO_SN"));
				moList.add(mItemtmp);
			}
			rs.close();
			st.executeUpdate(delMoSql + snBuf.toString() + ")");
		} catch (Exception e) {
			releaseConn();
			logger.error("Error APIClient.receiveSM by srcID ,amount",e);
			return null;
		} finally {
			closeStatment(st);
		}

		MOItem[] moItem = new MOItem[0];
		return (MOItem[]) moList.toArray(moItem);
	}

	public RPTItem[] receiveRPT() {
		if (this.dbUrl == null) {
			return null;
		}
		ResultSet rs = null;
		Statement st = null;
		if (this.conn == null) {
			int state = initConnect();
			if (state != 0) {
				return null;
			}
		}
		String getRPTSql = "select * from api_rpt_" + this.apiCode_
				+ " limit 5000";
		String delRPTSql = "delete from api_rpt_" + this.apiCode_
				+ " where AUTO_SN in (";

		RPTItem[] rptItem = null;
		ArrayList rptList = new ArrayList();

		StringBuffer snBuf = new StringBuffer("-1");
		try {
			st = this.conn.createStatement();
			rs = st.executeQuery(getRPTSql);
			while (rs.next()) {
				RPTItem rptItemtmp = new RPTItem();
				rptItemtmp.setSmID(rs.getLong("SM_ID"));
				rptItemtmp.setCode(rs.getInt("RPT_CODE"));
				rptItemtmp.setMobile(rs.getString("MOBILE"));
				rptItemtmp.setDesc(rs.getString("RPT_DESC"));
				rptItemtmp.setRptTime(rs.getString("RPT_TIME"));

				snBuf.append(",").append(rs.getString("AUTO_SN"));

				rptList.add(rptItemtmp);
			}
			rs.close();

			st.executeUpdate(delRPTSql + snBuf.toString() + ")");
		} catch (SQLException e) {
			logger.error("Error APIClient.receiveRPT",e);
			releaseConn();
			if ((e.getErrorCode() == 1146) || (e.getErrorCode() == 1142)) {
				arrayOfRPTItem1 = new RPTItem[0];
				return arrayOfRPTItem1;
			}
			arrayOfRPTItem1 = null;
			return arrayOfRPTItem1;
		} catch (Exception ex) {
			logger.error("Error APIClient.receiveRPT",ex);
			RPTItem[] arrayOfRPTItem1 = null;
			return arrayOfRPTItem1;
		} finally {
			closeStatment(st);
		}
		closeStatment(st);

		rptItem = new RPTItem[0];
		return (RPTItem[]) rptList.toArray(rptItem);
	}

	public RPTItem[] receiveRPT(long smID, int amount) {
		if (this.dbUrl == null) {
			return null;
		}
		ResultSet rs = null;
		Statement st = null;
		if (this.conn == null) {
			int state = initConnect();
			if (state != 0) {
				return null;
			}
		}
		String getRPTSql = "select * from api_rpt_" + this.apiCode_;
		if (smID != -1L) {
			getRPTSql = getRPTSql + " where SM_ID=" + smID;
		}
		if (amount != -1) {
			getRPTSql = getRPTSql + " limit " + amount;
		}
		String delRPTSql = "delete from api_rpt_" + this.apiCode_
				+ " where AUTO_SN in (";

		RPTItem[] rptItem = null;
		ArrayList rptList = new ArrayList();

		StringBuffer snBuf = new StringBuffer("-1");
		try {
			st = this.conn.createStatement();
			rs = st.executeQuery(getRPTSql);
			while (rs.next()) {
				RPTItem rptItemtmp = new RPTItem();
				rptItemtmp.setSmID(rs.getLong("SM_ID"));
				rptItemtmp.setCode(rs.getInt("RPT_CODE"));
				rptItemtmp.setMobile(rs.getString("MOBILE"));
				rptItemtmp.setDesc(rs.getString("RPT_DESC"));
				rptItemtmp.setRptTime(rs.getString("RPT_TIME"));

				snBuf.append(",").append(rs.getString("AUTO_SN"));

				rptList.add(rptItemtmp);
			}
			rs.close();

			st.executeUpdate(delRPTSql + snBuf.toString() + ")");
		} catch (SQLException e) {
			logger.error("Error APIClient.receiveRPT by smID amount",e);
			releaseConn();
			if ((e.getErrorCode() == 1146) || (e.getErrorCode() == 1142)) {
				arrayOfRPTItem1 = new RPTItem[0];
				return arrayOfRPTItem1;
			}
			arrayOfRPTItem1 = null;
			return arrayOfRPTItem1;
		} catch (Exception ex) {
			logger.error("Error APIClient.receiveRPT by smID amount",ex);
			RPTItem[] arrayOfRPTItem1 = null;
			return arrayOfRPTItem1;
		} finally {
			closeStatment(st);
		}
		closeStatment(st);

		rptItem = new RPTItem[0];
		return (RPTItem[]) rptList.toArray(rptItem);
	}

	public void release() {
		this.dbUser = null;
		this.dbPwd = null;
		this.apiCode_ = null;
		this.dbUrl = null;
		releaseConn();
	}

	private int testConnect() {
		Statement st = null;
		ResultSet rs = null;
		try {
			if (this.conn != null) {
				releaseConn();
			}

			getConn();

			st = this.conn.createStatement();
		} catch (ClassNotFoundException e) {
			logger.error("Error APIClient.testConnect",e);
			e.printStackTrace();
			return -1;
		} catch (SQLException e) {
			logger.error("Error APIClient.testConnect",e);
			e.printStackTrace();
			return -1;
		}catch (Throwable e) {
			logger.error("Error APIClient.testConnect",e);
			e.printStackTrace();
			return -1;
		}
		try {
			String tableName = "api_mo_" + this.apiCode_;
			rs = st.executeQuery("select * from " + tableName + " limit 1");
			rs.close();
		} catch (SQLException e) {
			logger.error("Error APIClient.testConnect executeQuery select * from api_mo_" + this.apiCode_,e);
			return -7;
		} finally {
			try {
				st.close();
			} catch (Exception localException1) {
			}
		}
		return 0;
	}

	private int initConnect() {
		try {
			getConn();
		} catch (Exception e) {
			logger.error("Error APIClient.initConnect",e);
			e.printStackTrace();
			return -1;
		}

		return 0;
	}

	private void getConn() throws ClassNotFoundException, SQLException {
		Class.forName("org.gjt.mm.mysql.Driver");
		this.conn = DriverManager.getConnection(this.dbUrl, this.dbUser,
				this.dbPwd);
	}

	private void releaseConn() {
		if (this.conn != null) {
			try {
				this.conn.close();
			} catch (SQLException localSQLException) {
			}
		}
		this.conn = null;
	}

	private int mTInsert(String mobile, String content, long smID, long srcID,
			String url, String sendTime) {
		if (this.conn == null) {
			int state = initConnect();
			if (state != 0) {
				return -1;
			}
		}
		if ((sendTime == null) || ("".equals(sendTime))) {
			sendTime = getCurDateTime();
		}
		String sendMTSql = "";
		if ((url == null) || (url.trim().length() == 0)) {
			sendMTSql = "insert into api_mt_" + this.apiCode_
					+ " (SM_ID,SRC_ID,MOBILES,CONTENT,SEND_TIME) values ("
					+ smID + "," + srcID + ",'" + mobile + "','" + content
					+ "','" + sendTime + "')";
		} else {
			sendMTSql = "insert into api_mt_"
					+ this.apiCode_
					+ " (SM_ID,SRC_ID,MOBILES,CONTENT,IS_WAP,URL,SEND_TIME) values ("
					+ smID + "," + srcID + ",'" + mobile + "','" + content
					+ "'," + 1 + ",'" + url + "','" + sendTime + "')";
		}
		Statement st = null;
		try {
			st = this.conn.createStatement();
			st.executeUpdate(gb2Iso(sendMTSql));
		} catch (SQLException e) {
			logger.error("Error APIClient.mTInsert executeUpdate sendMTSql="+sendMTSql,e);
			releaseConn();
			return -3;
		} finally {
			closeStatment(st);
		}
		return 0;
	}

	private void closeStatment(Statement st) {
		try {
			st.close();
		} catch (Exception localException) {
		}
	}

	private String replaceSpecilAlhpa(String content) {
		if ((content == null) || (content.trim().length() == 0)) {
			return "";
		}
		String spec_char = "\\'";
		String retStr = "";
		for (int i = 0; i < content.length(); i++) {
			if (spec_char.indexOf(content.charAt(i)) >= 0) {
				retStr = retStr + "\\";
			}
			retStr = retStr + content.charAt(i);
		}
		return retStr;
	}

	private boolean checkSmID(long smID) {
		return (smID >= 0L) && (smID <= 99999999L);
	}

	private String gb2Iso(String str) {
		if (str == null) {
			return "";
		}
		String temp = "";
		try {
			byte[] buf = str.trim().getBytes("GBK");
			temp = new String(buf, "iso8859-1");
		} catch (UnsupportedEncodingException e) {
			temp = str;
		}
		return temp;
	}

	private int checkGatConn() {
		int ret = 1;
		ResultSet rs = null;
		Statement st = null;
		if (this.conn == null) {
			initConnect();
		}

		String sql = "select if_status,conn_succ_status from tbl_api_info as api where api.if_code='"
				+ this.apiCode_ + "' limit 1";
		try {
			st = this.conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				if ("2".equals(rs.getString("if_status"))) {
					ret = -10;
				}
				if ("0".equals(rs.getString("conn_succ_status"))) {
					ret = -11;
				}
			}
			rs.close();
		} catch (SQLException e) {
			logger.error("Error APIClient.testConnect executeQuery select if_status,conn_succ_status from tbl_api_info as api where api.if_code=" + this.apiCode_,e);
			return -7;
		} finally {
			try {
				st.close();
			} catch (Exception localException1) {
			}
		}
		return ret;
	}

	public static String getCurDateTime() {
		SimpleDateFormat nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return nowDate.format(new Date());
	}

	public static String isDateTime(String str) {
		if (str == null)
			return null;
		if (str.length() != 19)
			return null;
		if (str.split(" ")[0].length() != 10)
			return null;
		if (str.split(" ")[1].length() != 8)
			return null;
		int temp = Integer.parseInt(str.substring(5, 7));
		if ((12 < temp) || (temp < 1)) {
			return null;
		}
		temp = Integer.parseInt(str.substring(8, 10));
		if ((31 < temp) || (temp < 1)) {
			return null;
		}
		temp = Integer.parseInt(str.substring(11, 13));
		if ((23 < temp) || (temp < 0))
			return null;
		temp = Integer.parseInt(str.substring(14, 16));
		if ((59 < temp) || (temp < 0))
			return null;
		temp = Integer.parseInt(str.substring(17, 19));
		if ((59 < temp) || (temp < 0))
			return null;
		Date returnDate = null;
		DateFormat df = DateFormat.getDateInstance();
		try {
			returnDate = df.parse(str);
			return returnDate.toString();
		} catch (Exception e) {
		}
		return null;
	}
}