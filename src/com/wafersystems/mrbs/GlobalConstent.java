package com.wafersystems.mrbs;

/**
 * MRBS 系统全局常量
 * 
 * @author Moon
 */
public interface GlobalConstent {

	/**
	 * 用户登录Session
	 */
	String USER_LOGIN_SESSION = "USER_LOGIN_SESSION";

	/**
	 * ec数据常量
	 */
	String REPORT_DATA = "report_data";

	/**
	 * 系统常量 Yes =1 No=0
	 */
	short SYSTEM_VALUE_YES = 1;
	short SYSTEM_VALUE_NO = 0;

	/**
	 * 用户状态
	 */
	short USER_STATE_ON = 1;
	short USER_STATE_OFF = 0;
	short USER_STATE_DELETED = -1;

	/**
	 * 部门状态
	 */
	int DEPT_STATE_ON = 1;// 启用
	int DEPT_STATE_OFF = 0;// 不可用

	/**
	 * 超级管理员id
	 */
	String USER_ADMIN_ID = "admin";

	/**
	 * 用户类型
	 */
	short USER_TYPE_SUPER_ADMIN = 1;
	short USER_TYPE_MEETING_ADMIN = 2;
	short USER_TYPE_DEPT_ADMIN = 3;
	short USER_TYPE_PROFESSIONAL = 4;
	short USER_TYPE_UNION = 5;

	/**
	 * 用户角色
	 */
	int USER_ROLE_SUPER_ADMIN = 1;
	int USER_ROLE_MEETING_ADMIN = 4;
	int USER_ROLE_DEPT_ADMIN = 5;
	int USER_ROLE_PROFESSIONAL = 3;
	int USER_ROLE_UNION = 2;

	/**
	 * 用户性别
	 */
	short USER_GENDER_MALE = 1;
	short USER_GENDER_FEMALE = 0;

	/**
	 * 会议室状态
	 */
	short MEETING_ROOM_STATE_ON = 1;
	short MEETING_ROOM_STATE_OFF = 0;

	/**
	 * 日志状态
	 */
	short SYSTEM_LOG_SUCCESS = 1;
	short SYSTEM_LOG_FAILED = 0;
	short SYSTEM_LOG_ALL = 2;

	/**
	 * 通知状态
	 */
	short NOTICE_NOT_SEND = 0;
	short NOTICE_SEND = 1;

	/**
	 * 通知类型
	 */
	short NOTICE_TYPE_INNER = 1;
	short NOTICE_TYPE_MAIL = 2;
	short NOTICE_TYPE_SMS = 3;

	/**
	 * 服务TAG
	 */
	String SERVICE_ALL = "ALL";
	// 邮件发送
	String SERVICE_MAIL_TAG = "MAIL";
	/** 短信通知 */
	String SERVICE_SMS_TAG = "SMS";
	// 会议状态
	String SERVICE_MEETING_STATUS_TAG = "STATUS";
	//MCU
	String SERVICE_MCU_STATUS_TAG = "MCU";

	/**
	 * 会议申请状态
	 */
	short MEETING_APPLICATION_STATE_PENDING = 1; // 待审核
	short MEETING_APPLICATION_STATE_PASS = 2; // 审核通过
	short MEETING_APPLICATION_STATE_REFUSED = 3; // 审核拒绝

	/**
	 * 会议状态
	 */
	short MEETING_STATE_NONE = 0; // 无效状态
	short MEETING_STATE_PENDING = 1; // 未开时
	short MEETING_STATE_START = 2; // 已开始
	short MEETING_STATE_END = 3; // 已结束
	short MEETING_STATE_ABSENCE = 4; // 缺席

	/** 邀请状态 */
	short APPLICATION_STATE_NONE = 1; // 未接受
	short APPLICATION_STATE_ACCEPT = 2; // 已接受
	short APPLICATION_STATE_REFUSED = 3; // 已拒绝
	short APPLICATION_STATE_ABSENCE = 4;// 缺席

	/** 申请的会诊类型 */
	short APPLICATION_TYPE_TELECONSULTATION = 1; // 远程会诊
	short APPLICATION_TYPE_VIDEOLECTURES = 2; // 视频讲座
	short APPLICATION_TYPE_ICUMONITOR = 3; //重症监护
	short APPLICATION_TYPE_ICUVISIT = 4;    // 远程探视
	short MEETING_LEVEL_EMERGENCY = 1; // 紧急
	short MEETING_LEVEL_COMMON = 2; // 普通

	/** 发送短信提醒时，相对操作完成而推迟的时间 */
	short TO_MANAGER_SMS_DELAY = 5;

	/** 最上层主目录人民医院的deptcode */
	String PARENT_DEPARTMENT_CODE = "001";

	/**
	 * 院内的dept_code
	 */
	String HIS_DEPT_CODE = "001001";

	/**
	 * 院际的dept_code
	 */
	String OUT_DEPT_CODE = "001002";

	/**
	 * 共同体组织的dept_code
	 */
	String COMMUNITY_DEPT_CODE = "001003";

	/**
	 * 从本系统录入的用户
	 */
	Short USER_SOURCE_LOCAL = 0;

	/**
	 * 从医院HIS系统同步的用户
	 */
	Short USER_SOURCE_HIS = 1;
	
	
	 /*短信常用参数*/
	
	/**
	 * 数据是否过期标记
	 * 0 未过期
	 * 1 已过期
	 */
	Integer DATE_EXPIRED_FLAG_N = 0;
	Integer DATE_EXPIRED_FLAG_Y = 1;
	
	/**
	 * 短信业务代码
	 * 001 通知管理员有需要审核的病历讨论申请
	 * 002 通知申请人，其申请病历讨论已被审核通过
	 * 003 通知申请人，其申请病历讨论审核时被拒绝
	 * 004 通知管理员有需要审核的视频讲座申请
	 * 005 通知申请人，其申请视频讲座已被审核通过
	 * 006 通知申请人，其申请视频讲座审核时被拒绝 
	 * 007 通知受邀人，有需要其接受的病历讨论邀请
	 * 008 通知受邀人，有需要其接受的视频讲座邀请
	 * 009 通知受邀人，有需要其接受的病历讨论邀请(专家默认接受)
	 * 010 通知受邀人，有需要其接受的视频讲座邀请(专家默认接受)
	 * 011 申请人默认接受
	 * 012 通知受邀人，会议马上开始，请准时参加（会前提醒）
	 */
	String SMS_SERVICE_CODE_001 = "001";
	String SMS_SERVICE_CODE_002 = "002";
	String SMS_SERVICE_CODE_003 = "003";
	String SMS_SERVICE_CODE_004 = "004";
	String SMS_SERVICE_CODE_005 = "005";
	String SMS_SERVICE_CODE_006 = "006";
	String SMS_SERVICE_CODE_007 = "007";
	String SMS_SERVICE_CODE_008 = "008";
	String SMS_SERVICE_CODE_009 = "009";
	String SMS_SERVICE_CODE_010 = "010";
	String SMS_SERVICE_CODE_011 = "011";
	String SMS_SERVICE_CODE_012 = "012";
	
	/**
	 * 数据是否处理标记
	 * 0 未处理
	 * 1 已处理
	 */
	Integer DATE_OPERATED_FALG_N = 0;
	Integer DATE_OPERATED_FALG_Y = 1;
	
	/**
	 * 数据是否处理标记
	 * 0 未处理
	 * 1 已处理
	 */
	String ACCEPT_YES = "Y";
	String ACCEPT_NO = "N";
	String ACCEPT_ZH_YES = "是";
	String ACCEPT_ZH_NO = "否";
	
	/**
	 * ICD显示
	 */
	short ICD_RADIO_DEFAULT = 1;
	short ICD_RADIO_USERDEFINED = 0;
	
	
    /**
     * 资源类型
     */ 
	int MEDIA_TYPE_HTML = 1;   //网页
	int MEDIA_TYPE_MEDIA = 2;  //视频
	int MEDIA_TYPE_TEXT = 3;   //文本
	int MEDIA_TYPE_IMG = 4;    //图片
	
	/**
	 * 资源来源
	 */
	int MEDIA_PATH_TYPE_LOCAL=1;      //本地上传
	int MEDIA_PATH_TYPE_NETWORK=2;    //网络连接
	
	/**
	 * 系统外网IP
	 */
	String SYSTEM_OUT_NET = "211.160.74.12";
	
}
