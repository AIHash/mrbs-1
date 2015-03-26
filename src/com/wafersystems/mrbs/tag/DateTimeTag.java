package com.wafersystems.mrbs.tag;

import java.util.Calendar;

import javax.servlet.jsp.JspException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wafersystems.util.DateUtil;


/**
 * 日期时间下拉列表Tag
 *
 * @author AnDong
 */
public class DateTimeTag extends BaseTag
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6712873262329645108L;
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(DateTimeTag.class);
	/**
	 * 下拉框名称
	 */
	private String name = "";
	/**
	 * 时间下拉列表标志
	 */
	private String timeFlag = "false";
	/**
	 * 其它属性
	 */
	private String others = "";
	/**
	 * 类型：YYYYMM－只生成年月下拉框,YYYY-只生成年份下拉列表
	 */
	private String type = "ALL";
	/**
	 * 默认值（多个值之间使用-符号进行分割）
	 */
	private String value = "";

	/**
	 * 标签结束事件
	 */
	public int doEndTag() throws JspException
	{
		try
		{
			StringBuffer html = new StringBuffer();
			html.append(this.getDate());
			if ("true".equals(this.timeFlag))
				html.append(this.getTime());

			page.write(html.toString());
		}
		catch (Exception e)
		{
			logger.error("日期时间Tag错误：", e);
		}

		return EVAL_PAGE;
	}

	/**
	 * @return
	 */
	private String getDate()
	{
		StringBuffer html = new StringBuffer();

		//年月日都有
		if ("ALL".equals(type))
		{
			html.append("<input type='text' inputType='date' name='")
			.append(name)
			.append("' ")
			.append(others)
			.append(" value=\"");

            if (( name.startsWith("to"))|(name.startsWith("end")))
           {
               value = DateUtil.getCurrentDate();
               html.append(value);
           }
           else if (( name.startsWith("from"))|(name.startsWith("start")))
           {
               value=DateUtil.getCurrentDate().substring(0,8)+"01";
               html.append(value);
           }
           else
           {
               html.append(value);
           }
           html.append("\" readOnly>");
       }


		if ("YYYY".equals(type))
		{
			//年下拉列表
			html.append("<Select name=\"")
				.append(this.name)
				.append("\">\n");

			int year = Calendar.getInstance().get(Calendar.YEAR);

			for (int i = 1990; i <= year; i++)
			{
				html.append("	<Option value='")
					.append(i)
					.append("'");

				if (!"".equals(value.trim()))
				{
					if (Integer.parseInt(value) == i)
						html.append(" selected ");
				}
				else
				{
					if (year == i)
						html.append(" selected ");
				}

				html.append(">")
					.append(i)
					.append("</Option>\n");
			}
			html.append("</Select>&nbsp;");
//				.append(MessageTag.getMessage("year"));
		}

		if ("YYYYMM".equals(type))
		{
			//年下拉列表
			html.append("<Select name=\"")
				.append(this.name)
				.append("_Year")
				.append("\">\n");

			int year = Calendar.getInstance().get(Calendar.YEAR);
			int month = Calendar.getInstance().get(Calendar.MONTH);

			for (int i = 1990; i <= year; i++)
			{
				html.append("	<Option value='")
					.append(i)
					.append("'");

				if (!"".equals(value.trim()))
				{
					if (-1 != value.split("-")[0].indexOf(i + ""))
						html.append(" selected ");
				}
				else
				{
					if (year == i)
						html.append(" selected ");
				}

				html.append(">")
					.append(i)
					.append("</Option>\n");
			}
			html.append("</Select>&nbsp;")
				.append(MessageTag.getMessage("year"));

			//月下拉列表
			html.append("<Select name=\"")
				.append(this.name)
				.append("_Month")
				.append("\">\n");
			for (int i = 1; i <= 12; i++)
			{
				html.append("	<Option value='");

				if (i < 10)
					html.append("0");

				html.append(i)
					.append("'");

				if (!"".equals(value.trim()))
				{
					if (-1 != value.split("-")[1].indexOf(i + ""))
						html.append(" selected ");
				}
				else
				{
					if ((month + 1) == i)
						html.append(" selected ");
				}

				html.append(">")
					.append(i)
					.append("</Option>\n");
			}
			html.append("</Select>&nbsp;")
				.append(MessageTag.getMessage("month"));
		}

		return html.toString();
	}

	/**
	 * @return
	 */
	private String getTime()
	{
		StringBuffer html = new StringBuffer();

		html.append("&nbsp;<input type='text' size='8' value='00:00:00' maxLength='8' name='")
			.append(name)
			.append("_Time'>&nbsp;[HH:MM:SS]");

//		//小时下拉列表
//		html.append("&nbsp;<Select name=\"")
//			.append(this.name)
//			.append("_Hour")
//			.append("\">\n");
//		for (int i = 0; i <= 23; i++)
//		{
//			html.append("	<Option value='");
//
//			if (i < 10)
//				html.append("0");
//
//			html.append(i)
//				.append("'>");
//
//			if (i < 10)
//				html.append("0");
//
//			html.append(i)
//				.append("</Option>\n");
//		}
//		html.append("</Select>时\n");
//
//		//分钟下拉列表
//		html.append("<Select name=\"")
//			.append(this.name)
//			.append("_Minute")
//			.append("\">\n");
//		for (int i = 1; i <= 59; i++)
//		{
//			html.append("	<Option value='");
//
//			if (i < 10)
//				html.append("0");
//
//			html.append(i)
//				.append("'>");
//
//			if (i < 10)
//				html.append("0");
//
//			html.append(i)
//				.append("</Option>\n");
//		}
//		html.append("</Select>分");
//
//		//秒下拉列表
//		html.append("<Select name=\"")
//			.append(this.name)
//			.append("_Second")
//			.append("\">\n");
//		for (int i = 1; i <= 59; i++)
//		{
//			html.append("	<Option value='");
//
//			if (i < 10)
//				html.append("0");
//
//			html.append(i)
//				.append("'>");
//
//			if (i < 10)
//				html.append("0");
//
//			html.append(i)
//				.append("</Option>\n");
//		}
//		html.append("</Select>秒\n");

		return html.toString();
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return Returns the timeFlag.
	 */
	public String getTimeFlag()
	{
		return timeFlag;
	}

	/**
	 * @param timeFlag The timeFlag to set.
	 */
	public void setTimeFlag(String timeFlag)
	{
		this.timeFlag = timeFlag;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param type The type to set.
	 */
	public void setType(String type)
	{
		this.type = type;
	}
	/**
	 * @param others The others to set.
	 */
	public void setOthers(String others)
	{
		this.others = others;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
}
