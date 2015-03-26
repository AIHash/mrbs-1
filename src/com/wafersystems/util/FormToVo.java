package com.wafersystems.util;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;

public class FormToVo {
	public static void pushVo(Object vo, HttpServletRequest request) {
		Class<?> voclass = vo.getClass();
		Field[] fields = voclass.getDeclaredFields();
		if (fields != null && fields.length > 0) {
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].getType().getName().equals("java.lang.String")) {
					String value = request.getParameter(fields[i].getName());
					fields[i].setAccessible(true);
					try {
						fields[i].set(vo, value);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

}
