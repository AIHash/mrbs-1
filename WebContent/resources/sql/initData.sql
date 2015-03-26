SET FOREIGN_KEY_CHECKS=0;

BEGIN;
INSERT INTO `accessories_type` VALUES ('1', '通用', '1');
INSERT INTO `accessories_type` VALUES ('2', '检验', '2');
INSERT INTO `accessories_type` VALUES ('3', '检查', '3');

INSERT INTO `department` VALUES ('1', '001', null,'人民医院', '-1', null, '北京大学人民医院', null);
INSERT INTO `department` VALUES ('2', '001001', null, '院内', '001', '', null, '1');
INSERT INTO `department` VALUES ('3', '001002', null, '院际', '001', '', null, '1');
INSERT INTO `department` VALUES ('4', '001003', null, '共同体组织', '001', '', null, '1');

INSERT INTO `language` VALUES ('en_US', '英文', 'Resources_en_US.prop');
INSERT INTO `language` VALUES ('zh_CN', '中文', 'Resources_zh_CN.prop');

INSERT INTO `meeting_level` VALUES ('1', '紧急', '紧急', '1');
INSERT INTO `meeting_level` VALUES ('2', '一般', '一般', '2');
INSERT INTO `meeting_level` VALUES ('3', '指定专家', '指定专家', '3');
INSERT INTO `meeting_level` VALUES ('4', '普通医生', '普通医生', '4');

INSERT INTO `meeting_satisfaction_evaluation` VALUES ('5', '非常满意', '90分以上', '5');
INSERT INTO `meeting_satisfaction_evaluation` VALUES ('4', '满意', '75-90分', '4');
INSERT INTO `meeting_satisfaction_evaluation` VALUES ('3', '一般', '60-75分', '3');
INSERT INTO `meeting_satisfaction_evaluation` VALUES ('2', '不满意', '40-60分', '2');
INSERT INTO `meeting_satisfaction_evaluation` VALUES ('1', '很差', '0-40分', '1');

INSERT INTO `meeting_type` VALUES ('1', '远程会诊', '1');
INSERT INTO `meeting_type` VALUES ('2', '病历讨论', '2');
INSERT INTO `meeting_type` VALUES ('3', '讲座', '3');

INSERT INTO `system_function` VALUES ('1', 'admin.title_system_config', '001', '-1', '分类', '');
/*
 * INSERT INTO `system_function` VALUES ('2', 'admin.system_service_center', '001001', '0010', '功能', '');
 * INSERT INTO `system_function` VALUES ('7', 'admin.role_manage', '002003', '0020', '功能', '');
 * INSERT INTO `system_function` VALUES ('14', 'admin.usergroup_manage', '002005', '0020', '功能', '/group/init');
 */
INSERT INTO `system_function` VALUES ('3', 'admin.system_param_config', '001002', '001', '功能', '/system/param/edit');
INSERT INTO `system_function` VALUES ('4', 'admin.title_system_manage', '002', '-1', '分类', '');
INSERT INTO `system_function` VALUES ('5', 'admin.department_manage', '002001', '002', '功能', '/dept/main');
INSERT INTO `system_function` VALUES ('6', 'admin.user_manage', '002002', '002', '功能', '/user/main');
INSERT INTO `system_function` VALUES ('8', 'admin.meetingroom_manage', '002004', '002', '功能', '/meeting/room/list');
INSERT INTO `system_function` VALUES ('9', 'admin.title_log_manage', '003', '-1', '分类', '');
INSERT INTO `system_function` VALUES ('10', 'admin.system_operation_log', '003001', '003', '功能', '/log/operation/init');
INSERT INTO `system_function` VALUES ('11', 'admin.system_service_log', '003002', '003', '功能', '/log/service/init');
INSERT INTO `system_function` VALUES ('12', 'admin.title_db_manage', '004', '-1', '分类', '');
INSERT INTO `system_function` VALUES ('13', 'admin.db_backup', '004001', '004', '功能', '/db/index');
INSERT INTO `system_function` VALUES ('15', 'admin.title_self_manage', '005', '-1', '分类', '');
INSERT INTO `system_function` VALUES ('16', 'admin.changepassword', '005001', '005', '功能', '/user/self');

INSERT INTO `system_role` VALUES ('1', '管理权限', '超级管理员权限', null);

INSERT INTO `system_role_functions` VALUES ('1', '1');
/*
INSERT INTO `system_role_functions` VALUES ('1', '2');
INSERT INTO `system_role_functions` VALUES ('1', '7');
INSERT INTO `system_role_functions` VALUES ('1', '14');
*/
INSERT INTO `system_role_functions` VALUES ('1', '3');
INSERT INTO `system_role_functions` VALUES ('1', '4');
INSERT INTO `system_role_functions` VALUES ('1', '5');
INSERT INTO `system_role_functions` VALUES ('1', '6');
INSERT INTO `system_role_functions` VALUES ('1', '8');
INSERT INTO `system_role_functions` VALUES ('1', '9');
INSERT INTO `system_role_functions` VALUES ('1', '10');
INSERT INTO `system_role_functions` VALUES ('1', '11');
INSERT INTO `system_role_functions` VALUES ('1', '12');
INSERT INTO `system_role_functions` VALUES ('1', '13');
INSERT INTO `system_role_functions` VALUES ('1', '15');
INSERT INTO `system_role_functions` VALUES ('1', '16');

INSERT INTO `unified_notice_type` VALUES ('1', '站内通知', '1');
INSERT INTO `unified_notice_type` VALUES ('2', '邮件通知', '2');
INSERT INTO `unified_notice_type` VALUES ('3', '短信通知', '3');

INSERT INTO `unified_parameter_package` VALUES ('SERVICE', '', '服务参数');
INSERT INTO `unified_parameter_package` VALUES ('SMS', '', '短信发送参数');
INSERT INTO `unified_parameter_package` VALUES ('SYNC', '', '同步数据参数');
INSERT INTO `unified_parameter_package` VALUES ('SYSTEM', '', '系统级参数');

INSERT INTO `unified_parameter` VALUES ('BEFORE-MINUTE', '会议通知提前(分钟)', '5', 'SERVICE');
INSERT INTO `unified_parameter` VALUES ('OUTTER-ACCESS-IP', 'Internet访问URL', 'http://192.168.0.230:8080/mrbs/login/notice', 'SERVICE');
INSERT INTO `unified_parameter` VALUES ('TMS-ADDRESS', 'TMS访问URL', 'http://192.168.0.96/tms', 'SERVICE');
INSERT INTO `unified_parameter` VALUES ('SEND-SMS-DB-NAME', '数据库名', 'chisdb_bjrm_jk', 'SMS');
INSERT INTO `unified_parameter` VALUES ('SEND-SMS-DB-USR', '数据库用户名', 'sa', 'SMS');
INSERT INTO `unified_parameter` VALUES ('SEND-SMS-IP', '数据库名IP地址', '172.16.2.50', 'SMS');
INSERT INTO `unified_parameter` VALUES ('SEND-SMS-PWD', '数据库密码', '', 'SMS');
INSERT INTO `unified_parameter` VALUES ('SYNC-DATA-DB-NAME', '数据库名', 'chisdb_bjrm_mz', 'SYNC');
INSERT INTO `unified_parameter` VALUES ('SYNC-DATA-DB-PWD', '数据库密码', '', 'SYNC');
INSERT INTO `unified_parameter` VALUES ('SYNC-DATA-DB-USER', '数据库用户名', 'llh', 'SYNC');
INSERT INTO `unified_parameter` VALUES ('SYNC-DATA-INNER-IP', '数据库名IP地址', '172.16.0.214', 'SYNC');
INSERT INTO `unified_parameter` VALUES ('LANGUAGE', '默认语言', 'zh_CN', 'SYSTEM');
INSERT INTO `unified_parameter` VALUES ('RMYY-URL-1', '院务信箱', 'http://www.pkuph.cn:81/', 'SYSTEM');
INSERT INTO `unified_parameter` VALUES ('RMYY-URL-2', '寻医问药', 'http://www.phbjmu.edu.cn/mass', 'SYSTEM');


INSERT INTO `unified_user` VALUES ('admin', '2011-07-28 15:38:09', null, null, null, null, '', 'ISMvKXpXpadDiUoOSoAfww', null, '1', null, null, null, null, '1', '1', '1');

INSERT INTO `unified_user_type` VALUES ('1', '超级管理员', '1');
INSERT INTO `unified_user_type` VALUES ('2', '会议管理员', '2');
INSERT INTO `unified_user_type` VALUES ('3', '科室管理员', '3');
INSERT INTO `unified_user_type` VALUES ('4', '专家用户', '4');
INSERT INTO `unified_user_type` VALUES ('5', '共同体用户', '5');

COMMIT;
