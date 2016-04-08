package org.syndrome.compiler.util;

public class UrlUtil {

	public static String trim(String url) {
		return url.replaceAll("/+$", "").replaceAll("^/+", "");
	}

}