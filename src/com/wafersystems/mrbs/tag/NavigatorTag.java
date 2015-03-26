package com.wafersystems.mrbs.tag;

import javax.servlet.jsp.JspException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 系统导航条自定义标签
 */
public class NavigatorTag extends BaseTag {
	private static final long serialVersionUID = -9035528102498267664L;
	private static final Logger logger = LoggerFactory
			.getLogger(NavigatorTag.class);
	/**
	 * 当前位置
	 */
	private String position = "";

	@Override
	public int doEndTag() throws JspException {
		try {
			String[] datas = position.split(">>");
			position = "";
			for (int i = 0; i < datas.length; i++) {
				//判断是否含有中文字符
				if (datas[i].getBytes().length != datas[i].length())
					position += ">>" + datas[i];
				else
					position += ">>" + MessageTag.getMessage(session, datas[i]);
			}
			datas = position.substring(2).split(">>");

			page.print("<div id='daohang'></div>");
			page.print("<div id='breadcrumb'>"
					+ MessageTag.getMessage(session, "comm.position.atNow")
					+ MessageTag.getMessage(session, datas[0])
					+ ">>"
					+ MessageTag.getMessage(session, datas[1]) + "</div>");
		} catch (Exception e) {
			logger.error("系统导航条Tag错误:", e);
		}

		return EVAL_PAGE;
	}

	/**
	 * @param position
	 *            The position to set.
	 */
	public void setPosition(String position) {
		this.position = position;
	}

}
