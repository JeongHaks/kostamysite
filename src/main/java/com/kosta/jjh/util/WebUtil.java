package com.kosta.jjh.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebUtil {
	
	public static String replace(String str, String pattern, String replace) {
		int s = 0, e = 0;
		StringBuffer result = new StringBuffer();

		while ((e = str.indexOf(pattern, s)) >= 0) {
			result.append(str.substring(s, e));
			result.append(replace);
			s = e + pattern.length();
		}
		result.append(str.substring(s));
		return result.toString();
	}

	public static void delete(String s) {
		File file = new File(s);
		if (file.isFile()) {
			file.delete();
		}
	}

	public static String con(String s) {
		String str = null;
		try {
			str = new String(s.getBytes("8859_1"), "ksc5601");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public static void forward(HttpServletRequest request, HttpServletResponse response, String path) 
			throws ServletException, IOException {
		
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}
	
	public static void redirect(HttpServletRequest request, HttpServletResponse response,String url)
			throws IOException {
		response.sendRedirect(url);
	}
}


