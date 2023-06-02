package com.qtone.util;

import org.apache.commons.lang3.text.StrBuilder;
import org.springframework.cglib.beans.BeanMap;

import java.io.UnsupportedEncodingException;
import java.text.CharacterIterator;
import java.text.DecimalFormat;
import java.text.StringCharacterIterator;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author <a href="mailto:wangxuhui@ssreader.cn">wangxuhui</a>
 * @version @date
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

	static Pattern regEx_cssFont = Pattern.compile("font\\-((size)|(weight))\\s*:\\s*[0-9]+((px)|(pt)|(em)(%))*\\s*;*");// css
																														// font

	static final String checkEmail = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

	public static boolean isEmail(String str) {
		Pattern regex = Pattern.compile(checkEmail);
		Matcher matcher = regex.matcher(str);
		boolean isMatched = matcher.matches();
		return isMatched;
	}

	public static boolean isPhone(String str) {
		if (isEmpty(str)) {
			return false;
		}
		String reg = "1\\d{10}";
		return str.matches(reg);
	}

	/**
	 * @author:zhangpk
	 * @Description:判断字符串是否是中文字符
	 * @param null:
	 * @return:
	 * @creed: Talk is cheap,show me the code
	 * @date: 2021/12/6 11:27
	 */

	public static  boolean isChinese(String str,int length){

		if(StringUtils.isBlank(str) ||  str.length()>length) {
			return false;
		}
		if(str.matches("[\u4E00-\u9FA5]+")){
			return true;
		}
		return false;
	}

	public static String replace(String text, String searchString, String replacement) {
		return replace(text, searchString, replacement, -1);
	}

	public static String Html2Text(String inputString) {

		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		Pattern p_script;
		Matcher m_script;
		Pattern p_style;
		Matcher m_style;
		Pattern p_html;
		Matcher m_html;
		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
			// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
			// }
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			htmlStr = htmlStr.replaceAll("&nbsp;", " ");// &nbsp;

			textStr = htmlStr;
		}
		catch (Exception e) {
			// System.err.println("Html2Text: " + e.getMessage());
		}
		return textStr;// 返回文本字符串
	}

	public static String html2Text(String inputString, int length) {
		if (inputString == null) {
			return "";
		}
		// String regExp_IMG =
		// "<[\\s]*?img[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?img[\\s]*?>";
		String regExp_IMG = "<[img]{3}[^>]+[\\/]?>(<\\/[img]{3}>)?";
		Pattern pattern = Pattern.compile(regExp_IMG, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputString);
		return part(Html2Text(matcher.replaceAll("[图片]")), length);

	}

	public static String encode(String src) {
		String str = "";
		try {
			str = java.net.URLEncoder.encode(src, "UTF-8").toString();
		}
		catch (UnsupportedEncodingException e) {
			str = "";
		}
		return str;
	}

	/**
	 * 截取部分字符串，这里一个汉字的长度认为是2
	 *
	 * @param str
	 * @param len
	 * @return
	 */
	public static String part(String str, int maxLength) {
		/*
		 * try { str = str.replaceAll("\\&[a-zA-Z]{1,10};",
		 * "").replaceAll("<[^>]*>", ""); byte b[]; int counterOfDoubleByte = 0;
		 * b = str.getBytes("UTF-8"); if (b.length <= len) { return str; } if
		 * (len >= 2) { len += 2; } for (int i = 0; i < len; i++) { if (b[i] <
		 * 0) { counterOfDoubleByte++; } } if (counterOfDoubleByte % 2 == 0) {
		 * return new String(b, 0, len, "UTF-8") + "…"; } else { return new
		 * String(b, 0, len - 1, "UTF-8") + "…"; } } catch (Exception e) {
		 * return str; }
		 */
		try {
			if (str == null) {
				return str;
			}
			String suffix = "...";
			int suffixLen = suffix.length();

			final StringBuffer sbuffer = new StringBuffer();
			final char[] chr = str.trim().toCharArray();
			int len = 0;
			for (int i = 0; i < chr.length; i++) {

				if (chr[i] >= 0xa1) {
					len += 2;
				}
				else {
					len++;
				}
			}

			if (len <= maxLength) {
				return new String(str.getBytes("UTF-8"), "UTF-8");
			}

			len = 0;
			for (int i = 0; i < chr.length; i++) {

				if (chr[i] >= 0xa1) {
					len += 2;
					if (len + suffixLen > maxLength) {
						break;
					}
					else {
						sbuffer.append(chr[i]);
					}
				}
				else {
					len++;
					if (len + suffixLen > maxLength) {
						break;
					}
					else {
						sbuffer.append(chr[i]);
					}
				}
			}
			sbuffer.append(suffix);

			return new String(sbuffer.toString().getBytes("UTF-8"), "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			return str;
		}
	}

	// 检索词需要过滤的特殊字符
	public static String filterSw(String sw) {
		String islv = "\\||\\!|\\@|\\#|\\$|\\%|\\^|\\&|\\*|\\(|\\)|\\_|\\+|\\-|\\}|\\{|\\/|\\.|\\,|\\?|\\>|\\<|\\~|\\、|\\】|\\【|\\‘|\\；|\\’|\\、|\\。|\\，|\\￥|\\……|\\）|\\（|:";
		sw = sw.replaceAll(islv, " ");
		return sw;
	}

	// 过滤不可见字符
	public static String filterHideSw(String sw) {

		String filter = "[\u007f-\u009f]|\u00ad|[\u0483-\u0489]|[\u0559-\u055a]|\u058a|[\u0591-\u05bd]|\u05bf|[\u05c1-\u05c2]|[\u05c4-\u05c7]|[\u0606-\u060a]|[\u063b-\u063f]|\u0674|[\u06e5-\u06e6]|\u070f|[\u076e-\u077f]|\u0a51|\u0a75|\u0b44|[\u0b62-\u0b63]|[\u0c62-\u0c63]|[\u0ce2-\u0ce3]|[\u0d62-\u0d63]|\u135f|[\u200b-\u200f]|[\u2028-\u202e]|\u2044|\u2071|[\uf701-\uf70e]|[\uf710-\uf71a]|\ufb1e|[\ufc5e-\ufc62]|\ufeff|\ufffc";
		if (StringUtils.isNotBlank(sw)) {
			sw = sw.replaceAll(filter, "");
		}
		if (StringUtils.isNotBlank(sw)) {
			sw = trimWhitspace(sw);
		}
		return sw;
	}
	/***
	 * @Description:检索词需要过滤的特殊字符
	 * @Author: huangguangxi
	 * @Date: 2021/11/10 18:04
	 * * @param null
	 *return
	 **/
	public static String filterSpecialChar(String sw) {
		if(StringUtils.isNullToObj(sw)) {
			return "无";
		}
		//特殊字符串
		String islv = "[\\s\\p{P}\\n\\r=+$￥<>^`~|]";
		sw = sw.replaceAll(islv, "").trim();
		if(StringUtils.isNullToObj(sw)){
			return "无";
		}
		return sw;
	}
	/**
	 * 将双引号 前面加上下划线
	 */
	public static String switchShuang(String s) {

		if (StringUtils.isBlank(s)) {
			return s;
		}
		else {
			return s.replaceAll("\"", "&quot;");
		}
	}

	public static String merge(List<String> src, String seperator) {
		if (src == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder(16);
		for (int i = 0; i < src.size(); i++) {
			if (i == 0) {
				sb.append(src.get(i));
			}
			else {
				sb.append(seperator + src.get(i));
			}
		}
		return sb.toString();
	}

	// 判断某个字符串是否是整数字符串，若是数字字符串返回true，若不是则返回false
	private static boolean isNumberString(String testString) {
		String numAllString = "0123456789";
		if (testString.length() <= 0)
			return false;
		for (int i = 0; i < testString.length(); i++) {
			String charInString = testString.substring(i, i + 1);
			if (!numAllString.contains(charInString))
				return false;
		}
		return true;
	}

	// 判断某个字符串是否是float字符串，若是返回true，若不是则返回false
	public static boolean isFloathString(String testString) {
		if (testString == null)
			return false;
		testString = testString.replaceAll(" ", "");
		if (!testString.contains(".")) {
			return isNumberString(testString);
		}
		else {
			String[] floatStringPartArray = testString.split("\\.");
			if (floatStringPartArray.length == 2) {
				if (true == isNumberString(floatStringPartArray[0]) && true == isNumberString(floatStringPartArray[1]))
					return true;
				else
					return false;
			}
			else
				return false;
		}
	}

	/*
	 * 返回长度为【strLength】的随机数，strLength<=6
	 */
	public static String getFixLenthString(int strLength) {
		Random rm = new Random();
		// 获得随机数
		double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);
		// 将获得的获得随机数转化为字符串
		String fixLenthString = String.valueOf(pross);
		// 返回固定的长度的随机数
		return fixLenthString.substring(1, strLength + 1);
	}

	public static String trimAll(String str) {
		if (str == null) {
			return str;
		}
		return str.replaceAll(" ", "");
	}

	public static String trim(String str) {
		if (str == null) {
			return "";
		}
		// return str.trim();
		return str.replace("　", "").trim();
	}

	// 过滤文章中的html
	public static String safeHTML(String content) {
		if (content != null && !"".equals(content)) {
			content = content.replaceAll("</?[^<]+>", "").replaceAll("&nbsp;", "");
		}
		return content;
	}

	/**
	 * 判断给定的字符串长度是否 > 指定长度，这里汉子算两个字节
	 */
	public boolean more(String str, int len) {
		str = str.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");
		boolean more = false;
		try {
			byte[] b = str.getBytes("GBK");
			if (b.length > len) {
				more = true;
			}
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return more;
	}

	public static String replaceSpecial(String str) {
		String dest = "";
		if (str != null) {
			str = str.contains("\\") ? str.replace("\\", "?") : str;
			Pattern p = Pattern.compile("[\\s]|[\t]|[\r]|[\n]|[?]|[/]|[:]|[*]|[|]|[\"]|[<]|[>]");
			Matcher m = p.matcher(str);
			dest = m.replaceAll(" ");

		}
		return dest;
	}

	// 作业考试字符串特殊处理("[xxxxx]"这种字符串)
	public static String replaceString(String content) {
		if (content.contains("\"[")) {
			content = content.replaceAll("\"", " ");
		}
		if (content.contains("]\"")) {
			content = content.replaceAll("\"", " ");
		}
		if (content.contains("\"{")) {
			content = content.replaceAll("\"", " ");
		}
		if (content.contains("}\"")) {
			content = content.replaceAll("\"", " ");
		}
		return content;
	}

	// 手机端作业去掉编辑器的字体样式
	public static String disableCssFont(String content) {
		if (content != null) {
			Matcher matcher = regEx_cssFont.matcher(content);

			return matcher.replaceAll("");
		}

		return content;
	}

	/**
	 * 小写的数字转换成大写的数字
	 */
	public static String upperCase(int src) {
		Map<Integer, String> mapping = new HashMap<Integer, String>();
		mapping.put(1, "一");
		mapping.put(2, "二");
		mapping.put(3, "三");
		mapping.put(4, "四");
		mapping.put(5, "五");
		mapping.put(6, "六");
		mapping.put(7, "七");
		mapping.put(8, "八");
		mapping.put(9, "九");
		mapping.put(10, "十");
		mapping.put(11, "十一");
		mapping.put(12, "十二");
		mapping.put(13, "十三");
		mapping.put(14, "十四");
		mapping.put(15, "十五");
		mapping.put(16, "十六");
		mapping.put(17, "十七");
		mapping.put(18, "十八");
		mapping.put(19, "十九");
		mapping.put(20, "二十");
		mapping.put(21, "二十一");
		mapping.put(22, "二十二");
		mapping.put(23, "二十三");
		mapping.put(24, "二十四");
		mapping.put(25, "二十五");
		mapping.put(26, "二十六");
		mapping.put(27, "二十七");
		mapping.put(28, "二十八");
		mapping.put(29, "二十九");
		mapping.put(30, "三十");
		mapping.put(30, "三十一");
		if (src <= 30) {
			return mapping.get(src);
		}
		else {
			return String.valueOf(src);
		}
	}

	public static String replaceHtml(String input) {
		if (input == null || input.trim().equals("")) {
			return "";
		}
		// 去掉所有html元素,
		String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");
		// str = str.replaceAll("[(/>)<]", "");
		str = str.replaceAll("&nbsp;", " ");
		return str;
	}

	// 将数字转为ABCD
	public static String convertToNum(int num) {
		String[] strArr = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
		return strArr[num - 1];
	}

	/**
	 * 判断str1中是否包含str2
	 */
	public static boolean isContain(String str1, String str2) {
		if (StringUtils.isNotBlank(str1)) {
			if (str1.contains(str2)) {
				return true;
			}
		}
		return false;
	}

	public static String addNumBeforeP(String index, String content) {
		if (content == null) {
			content = "";
		}
		if (content != null) {
			// content = removeBr(content);
		}
		Pattern pattern = Pattern.compile("^<[^>]{1,}>");
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			return content.replaceFirst(matcher.group(), matcher.group() + index);
		}
		else {
			return index + content;
		}
	}

	public static String removeBr(String inputString) {
		inputString = inputString.replace("<br>", "");
		inputString = inputString.replace("</br>", "");
		inputString = inputString.replace("<br/>", "");
		return inputString;
	}

	/**
	 * 如果double类型小数点后面为0，则返回整数，否则返回原有double的字符串
	 *
	 * @param d
	 * @return
	 */
	public static String subDouble(double d) {
		String str = "";
		if (String.valueOf(d).substring(String.valueOf(d).indexOf(".") + 1).equals("0")) {
			str = (int) d + "";
		}
		else {
			str = String.valueOf(d);
		}
		return str;
	}

	public static String getZeroString(int count) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < count; i++) {
			sb.append("0");
		}
		return sb.toString();
	}

	public static String format(String str, Object... args) {
		String result = str;
		Pattern p = Pattern.compile("\\{(\\d+)\\}");
		Matcher m = p.matcher(str);
		while (m.find()) {
			int index = Integer.parseInt(m.group(1));
			if (index < args.length) {
				result = result.replace(m.group(), notNull(args[index], "").toString());
			}
		}
		return result;
	}

	public static Object notNull(Object obj, Object obj1) {
		return (obj == null || "".equals(obj)) ? obj1 : obj;
	}

	public static double getScoreDouble(double f1, double f2) {
		double result = f1 / f2;
		DecimalFormat df = new DecimalFormat(".##");
		result = Double.parseDouble(df.format(result));
		return result;
	}

	/**
	 * 获取有效验证码
	 * @param identifyCode
	 * @return
	 */
	public static String getValidateIdentifyCode(String identifyCode) {
		if (identifyCode.contains("-"))
			return identifyCode.split("-")[0];
		else
			return identifyCode;

	}

	/**
	 * 设置验证码为无效
	 * @param identifyCode
	 * @return
	 */
	public static String setValidateIdentifyCode(String identifyCode) {
		if (identifyCode.contains("-"))
			return identifyCode;
		else
			return identifyCode + "-";

	}

	/**
	 * 无效验证码-设置验证码为空
	 * @param identifyCode
	 * @return
	 */
	public static String setNullIdentifyCode(String identifyCode) {
		if (identifyCode == null)
			return null;
		if (identifyCode.contains("-"))
			return null;
		return identifyCode;

	}

	/**
	 * 字符串转整型
	 * @param
	 * @return
	 */
	public static int setStrtoInt(String str) {

		return Integer.parseInt(str);

	}

	/*
	 * 
	 * 根据编号获取题型名字 public static String getQueName(int num){ String str="其它";
	 * switch(num){
	 * 
	 * case 0: str= "单选题"; break; case 1: str= "多选题"; break; case 2: str= "填空题";
	 * break; case 3: str= "判断题"; break; case 4: str= "简答题"; break; case 5: str=
	 * "名词解释"; break; case 6: str= "论述题"; break; case 7: str= "计算题"; break; case
	 * 8: str= "其它"; break; case 9: str= "完型填空"; break; case 10: str= "阅读理解";
	 * break; case 11: str= "连线题"; break; case 12: str= "投票题"; break;
	 * 
	 * } return str; }
	 */

	public static boolean isWithIn(String sourceStr, String withInStr) {
		boolean result = false;
		if (StringUtils.isNotBlank(sourceStr)) {
			if (sourceStr.indexOf(withInStr) != -1) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * 
	 * 按空格分割字符串
	 */

	public static String getFormatString(String str) {
		if (StringUtils.isBlank(str))
			return "";
		String[] techerName = str.split(" ");
		if (techerName.length > 0) {
			String teacherNameStr = techerName[0];
			if (teacherNameStr.length() > 9)
				teacherNameStr = teacherNameStr.substring(0, 9) + "...";
			if (techerName.length > 1)
				return teacherNameStr + "等";
			else
				return teacherNameStr;
		}
		else
			return "";

	}

	public static String genIndex(int offSet, int pageNum, int pageSize) {
		return ((pageNum - 1) * pageSize + offSet) + "";
	}

	public boolean isNotNull(String str) {
		if (StringUtils.isBlank(safeHTML(str)))
			return false;
		return true;
	}

	public int stringToInt(String str) {
		if (StringUtils.isBlank(str))
			return 1;
		try {
			return Integer.parseInt(str);
		}
		catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * 判断字符串是否按生效排序 区分大小写
	 * @param abc
	 * @return
	 */
	public static boolean isAsc(String abc) {
		char[] table = abc.toCharArray();

		for (int i = 0; i < table.length - 1; i++) {
			int a = table[i];
			int b = table[i + 1];
			if (a > b) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param source
	 * @param arrayStr [1,2,3]
	 * @return
	 */
	public static boolean isStrInArrayStr(String source, String arrayStr) {
		try {
			if (source == null || arrayStr == null || arrayStr.length() == 0) {
				return false;
			}
			String[] strArray = arrayStr.substring(1, arrayStr.length() - 1).split(",");
			List<String> array = Arrays.asList(strArray);
			if (array.contains(source)) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public static String trimWhitspace(String str) {
		int len = str.length();
		int st = 0;
		while ((st < len) && Character.isWhitespace(str.charAt(st))) {
			st++;
		}
		while ((st < len) && Character.isWhitespace(str.charAt(len - 1))) {
			len--;
		}
		return ((st > 0) || (len < str.length())) ? str.substring(st, len) : str;
	}

	public static String join(double[] array, char separator) {
		if (array == null) {
			return null;
		}
		int bufSize = array.length;
		if (bufSize <= 0) {
			return EMPTY;
		}

		bufSize *= ((array[0] == 0 ? 16 : (array[0] + "".length())) + 1);
		StrBuilder buf = new StrBuilder(bufSize);

		for (int i = 0; i < array.length; i++) {
			if (i > 0) {
				buf.append(separator);
			}

			buf.append(array[i]);
		}
		return buf.toString();
	}

	public static int parseInt(String str) {
		int i = 0;
		if (StringUtils.isNotEmpty(str)) {
			if (isNum(str)) {
				i = Integer.parseInt(str);
			}
		}
		return i;
	}

	public static boolean isNum(String str) {
		if (isEmpty(str)) {
			return false;
		}
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	/*
	 * 获取文件扩展名
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	public static String getFullWebName(StringBuffer dm) {
		StringBuffer domain = new StringBuffer(dm);
		if (domain.indexOf("http://") >= 0) { // 去掉 http://
			domain.delete(0, 7);
			if (domain.indexOf("/") >= 0) {// 去掉 域名后面的 地址
				domain.delete(domain.indexOf("/"), domain.length());
			}
			if (domain.indexOf(":") >= 0) { // 去掉端口
				domain.delete(domain.indexOf(":"), domain.length());
			}
		}
		return domain.toString();

	}

	public static String array2string(Object[] oArray) {
		String str = "";
		if (oArray != null && oArray.length > 0) {
			str = Arrays.toString(oArray).replaceAll(", ", ",");
			str = str.substring(1, (str.length() - 1));
		}
		return str;
	}
	
	/**
	 * 防止xss 跨站漏洞 框架注入漏洞 链接注入漏洞
	 * @param s
	 * @return
	 */
	public static String xssEncode(String s) {
		if (s == null || s.isEmpty()) {
			return s;
		}
		String result = s;
		try {
			result = stripXSS(s);
			if (null != result) {
				result = HTMLEncode(result);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String HTMLEncode(String aText) {
		StringBuilder result = new StringBuilder();
		StringCharacterIterator iterator = new StringCharacterIterator(aText);
		char character = iterator.current();
		while (character != CharacterIterator.DONE) {
			if (character == '<') {
				result.append("&lt;");
			}
			else if (character == '>') {
				result.append("&gt;");
			}
			else if (character == '&') {
				result.append("&amp;");
			}
			else if (character == '\"') {
				result.append("&quot;");
			}
			else {
				// the char is not a special one
				// add it to the result as is
				result.append(character);
			}
			character = iterator.next();
		}
		return result.toString();
	}

	public static String stripXSS(String value) {
		if (value != null) {
			// NOTE: It's highly recommended to use the ESAPI library and
			// uncomment the following line to
			// avoid encoded attacks.
			// value = ESAPI.encoder().canonicalize(value);
			// Avoid null characters
			value = value.replaceAll("", "");
			// Avoid anything between script tags
			Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			Pattern aPattern = Pattern.compile("<a>(.*?)</a>", Pattern.CASE_INSENSITIVE);
			value = aPattern.matcher(value).replaceAll("");
			// Avoid anything in a src='...' type of expression
			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			// Remove any lonesome </script> tag
			scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			// Remove any lonesome <script ...> tag
			scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid eval(...) expressions
			scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid expression(...) expressions
			scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid javascript:... expressions
			scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid vbscript:... expressions
			scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid onload= expressions
			scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");

			scriptPattern = Pattern.compile("<iframe>(.*?)</iframe>", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");

			scriptPattern = Pattern.compile("</iframe>", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			// Remove any lonesome <script ...> tag
			scriptPattern = Pattern.compile("<iframe(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
		}
		return value;
	}

	
	/**
	 * 格式化double为int类型
	 * @param d
	 * @param format
	 * @return
	 */
	public static int formatDouble2Int(double d, String format) {
		return Integer.valueOf(formatDouble2String(d, format));
	}

	/**
	 * 格式化double为字符串
	 * @param d
	 * @param format
	 * @return
	 */
	public static String formatDouble2String(double d, String format) {
		DecimalFormat decimalFormat = new DecimalFormat(format);
		return decimalFormat.format(d);
	}
	
	/**
	 * 对象为空，返回空字符串
	 * @author :huangguangxi
	 * @date :2017-9-25 下午3:08:54
	 * @param obj 对象
	 * @return
	 */
	public static String objToStr(Object obj){
		if (null == obj) {
			return "";
		}
		if("null".equals(obj.toString().trim())){
			return "";
		}
		return obj.toString().trim();
	}
	/**
	 * 对象转为Int
	 * @author huangguangxi
	 * @date:   2017年9月27日 下午4:06:45    
	 * @param obj 对象
	 * @return          
	 * @throws
	 */
	public static Integer objToInt(Object obj){
		if (null == obj) {
			return -100;
		}
		if("null".equals(obj.toString().trim())){
			return -100;
		}
		if (!isNum(obj.toString().trim())){
			return -100;
		}
		return Integer.parseInt(obj.toString().trim());
	}

	//判断能否转为int
	public static boolean isInt(Object obj){
		try {
			Integer.parseInt(obj.toString().trim());
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 对象转为Long
	 *  
	 * @author qichenghao
	 * @date:   2017年12月5日 下午2:04:36   
	 * @param obj 对象
	 * @return
	 */
	public static long objToLong(Object obj){
		if (null == obj) {
			return -100;
		}
		if("null".equals(obj.toString().trim())){
			return -100;
		}
		if (!isNum(obj.toString().trim())){
			return -100;
		}
		return Long.parseLong(obj.toString().trim());
	}
	
	/**
	 * 分页参数验证
	 * @author renfei
	 * @date 2017年10月19日 下午5:08:12
	 * @param obj
	 * @return
	 */
	public static boolean pageParamValidate(Object obj) {
		return objToInt(obj) > 0;
	}
	/**
	 * 将阿拉伯数字转成汉字
	 *  
	 * @author qichenghao
	 * @date:   2017年10月26日 下午5:48:28   
	 * @param string
	 * @return
	 */
	public static String toChinese(String string) {
        String[] s1 = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        String[] s2 = { "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千" };

        String result = "";

        int n = string.trim().length();
        for (int i = 0; i < n; i++) {

            int num = string.charAt(i) - '0';

            if (i != n - 1 && num != 0) {
                result += s1[num] + s2[n - 2 - i];
            } else {
                result += s1[num];
            }
        }

        return result;
    }
	/**
	 *将字符串中的html标签替换为_ 
	 *  
	 * @author qichenghao
	 * @date:   2017年10月27日 上午9:23:47   
	 * @param str
	 * @return
	 */
	public static String toReplace(String str){
		
		return str.replaceAll("\\&[a-zA-Z]{0,9};", "").replaceAll("<[^>]*>", "_");
	}
	/**
	 *将字符串中的html标签替换为""
	 *  
	 * @author qichenghao
	 * @date:   2017年10月27日 上午9:23:47   
	 * @param str
	 * @return
	 */
	public static String toReplace2(String str){
		
		return str.replaceAll("\\&[a-zA-Z]{0,9};", "").replaceAll("<[^>]*>", "");
	}
	
	/**
	 * 对象转为Double
	 * @author renfei
	 * @date 2017年11月2日 下午4:23:51
	 * @param obj
	 * @return
	 */
	public static Double objToDouble(Object obj){
		if (null == obj) {
			return -100.0;
		}
		if("null".equals(obj.toString().trim())){
			return -100.0;
		}
		if (!isNum(obj.toString().trim())){
			return -100.0;
		}
		return Double.parseDouble(obj.toString().trim());
	}
	/**
	 * 判断对象是否为空 为空返回true
	 * @author huangguangxi
	 * @date:   2017年11月3日 上午9:36:45    
	 * @param obj
	 * @return          
	 * @throws
	 */
	public static boolean isNullToObj(Object obj){
		if (null == obj) {
			return true;
		}
		if("null".equals(obj.toString().trim())){
			return true;
		}
		if ("".equals(obj.toString().trim())) {
			return true;
		}
		return false;
	}
	/**
	 * 判断对象是否不为空 为空返回false
	 * @author huangguangxi
	 * @date:   2017年11月3日 上午9:36:45    
	 * @param obj
	 * @return          
	 * @throws
	 */
	public static boolean isNotNullToObj(Object obj){
		if (null == obj) {
			return false;
		}
		if("null".equals(obj.toString().trim())){
			return false;
		}
		if ("".equals(obj.toString().trim())) {
			return false;
		}
		return true;
	}
	/**
	 * 把字符串对象转为List<Map<String, Object>>
	 * @author huangguangxi
	 * @date:   2017年11月8日 上午9:20:37    
	 * @param jsonStr [{name=1212, url=/uplaod/123.jpg}, {name=1212, url=/uplaod/123.jpg}]
	 * List<Map<String,Object>> 调用toString()方法之后形成的字符串
	 * @return 
	 * @throws
	 */
	public static List<Map<String, Object>> jsonStrToList(String jsonStr) {
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		// 校验格式
		if (StringUtils.isNotBlank(jsonStr) && jsonStr.startsWith("[{")
				&& jsonStr.endsWith("}]")) {
			jsonStr = jsonStr.substring(1, jsonStr.length() - 1);
			// 第一次分割
			String[] firstArr = jsonStr.split("},");
			Map<String, Object> dataMap = null;
			for (int i = 0; i < firstArr.length; i++) {
				String secondStr = firstArr[i];
				// 替换大括号和空格
				secondStr = secondStr.replace("{", "").replace("}", "")
						.replace(" ", "");
				// 第二次分割
				String[] secondArr = secondStr.split(",");

				dataMap = new HashMap<String, Object>();
				for (int j = 0; j < secondArr.length; j++) {
					// 第三次分割
					String[] third = secondArr[j].split("=");
					if (third.length == 1) {
						continue;
					}
					dataMap.put(third[0], third[1]);
				}
				dataList.add(dataMap);
			}
		}
		return dataList;
	}
	/**
	 * 把字符串对象转为List<Map<String, Object>> 全课云教师专用
	 * @author yuanjinxin
	 * @date:   2018年7月4日 上午11:11:37    
	 * @param jsonStr [{name=1212, url=/uplaod/123.jpg}, {name=1212, url=/uplaod/123.jpg}]
	 * List<Map<String,Object>> 调用toString()方法之后形成的字符串
	 * @return 
	 * @throws
	 */
	public static List<Map<String, Object>> jsonStrToList2(String jsonStr) {
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		// 校验格式
		if (StringUtils.isNotBlank(jsonStr) && jsonStr.startsWith("[{")
				&& jsonStr.endsWith("}]")) {
			jsonStr = jsonStr.substring(1, jsonStr.length() - 1);
			// 第一次分割
			String[] firstArr = jsonStr.split("},");
			Map<String, Object> dataMap = null;
			for (int i = 0; i < firstArr.length; i++) {
				String secondStr = firstArr[i];
				System.out.println(i+"------"+secondStr);
				secondStr = secondStr.replace("{", "").replace("}", "")
						.replace(" ", "");
				// 第二次分割
				if(secondStr.contains("Job=") && secondStr.contains(",,")){
					int end = secondStr.indexOf(",,");
					int beg = secondStr.indexOf("Job=");
					String substring = secondStr.substring(beg,end);
					String replace = substring.replace(",", ":");
					secondStr = secondStr.replace(substring, replace);	
				}
				String[] secondArr = secondStr.split(",");
				dataMap = new HashMap<String, Object>();
				for (int j = 0; j < secondArr.length; j++) {
					// 第三次分割
					String[] third = secondArr[j].split("=");
					if (third.length == 1) {
						continue;
					}
					dataMap.put(third[0], third[1]);
				}
				dataList.add(dataMap);
			}
		}
		return dataList;
	}

	
	/**
	 * 将字符串转为List<Integer>
	 * @author yuanjinxin
	 * @date:   2018年6月6日 下午3:30:45
	 * @param 	String  1,2,3
	 * @return: List<Integer> 
	 */
	public static List<Integer> strToList(String str){
		List<Integer> list = new ArrayList<Integer>();
		for (String string : str.split(",")) {
			list.add(StringUtils.objToInt(string));
		}
		return list;
	}


	/**
	 * 取y与x的差集。。即确定x在y中肯定存在 ,y是多的一方
	 * @author huangguangxi
	 * @date:   2020年3月7日 下午9:50:26
	 * @param x
	 * @param y
	 * @return
	 * @throws
	 */
	public static Set<Integer> getDifSet(Set<Integer> x, Set<Integer> y) {
		//将多转换为set fid 肯定不是小的
		//Set<Integer> set = new HashSet<Integer>(Arrays.asList(y));
		for (Integer p : x) {
			// 如果集合里有相同的就删掉，如果没有就将值添加到集合
			if (y.contains(p)) {
				y.remove(p);
			} else {
				y.add(p);
			}
		}
		return y;
	}

	/**
	 * 去除换行符/空格
	 * @author liuzhichao
	 * @date  2020/9/15 16:26
	 * @param [str]
	 * @return java.lang.String
	 */
	public static String replace(String str) {
		String destination = "";
		if (str!=null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			destination = m.replaceAll("");
		}
		return destination;
	}

	//生成虚拟的手机号码
	public static String creatTel(String userId,Integer userType){
		StringBuffer sb = new StringBuffer("199");
		if(isNullToObj(userType)){
			userType = 0;
		}
		sb.append(userType).append("0000000"); //19910000000
		if(isNotNullToObj(userId)){
			Integer userLength = userId.length();
			if(userLength < 11){
				sb.replace(11 - userLength,11,userId);
			}else {
				return userId;
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		//System.out.println(creatTel(null,null));
		System.out.println(getRandom());
	}

	
	/**
	* @Author huangguangxi
	* @Date 2021年3月18日下午3:43:57
	* @Description: 字符串数组对比第三方id
	* @param exitsUserIds
	* @param userIdList
	* @return    参数
	* Set<String>    返回类型
	* @throws
	*/
	public static Set<String> getStrDifSet(Set<String> x,Set<String> y) {
		//将多转换为set fid 肯定不是小的
		for (String p : x) {
			// 如果集合里有相同的就删掉，如果没有就将值添加到集合
			if (y.contains(p)) {
				y.remove(p);
			} else {
				y.add(p);
			}
		}
		return y;
	}
	/**
	 * @Description:将Map转为对象
	 * @Author: huangguangxi
	 * @Date: 2021/5/7 15:17
	 * * @param map
	 * @param bean
	 *return T
	 **/
	public static <T> T mapToBean(Map<String, Object> map, T bean) {
		BeanMap beanMap = BeanMap.create(bean);
		beanMap.putAll(map);
		return bean;
	}

	public static String getRandom(){
		Random random = new Random();
		int ends = random.nextInt(99);
		return String.format("%02d",ends);//如果不足两位，前面补0
	}

}
