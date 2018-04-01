/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.hengyi.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Array;
import java.util.*;

/**
 *
 * 字符串实用类。
 * @author liuyuan
 *
 */
public class StringUtil extends StringUtils {

	private static final Log log = LogFactory.getLog(StringUtil.class);

	private StringUtil() {
	}

	/**
	 * 剪掉字符串左边的空格。
	 * 
	 * @param value
	 *            String
	 * 
	 * @return String
	 */
	public static String trimLeft(String value) {
		if (value == null) {
			return "";
		}
		String result = value;
		char ch[] = result.toCharArray();
		int index = -1;
		for (int i = 0; i < ch.length && Character.isWhitespace(ch[i]); i++) {
			index = i;
		}

		if (index != -1) {
			result = result.substring(index + 1);
		}
		return result;
	}

	/**
	 * 剪掉字符串右边的空格。
	 * 
	 * @param value
	 *            String
	 * 
	 * @return String
	 */
	public static String trimRight(String value) {
		if (value == null) {
			return "";
		}
		String result = value;
		char ch[] = result.toCharArray();
		int endIndex = -1;
		for (int i = ch.length - 1; i > -1 && Character.isWhitespace(ch[i]); i--) {
			endIndex = i;
		}

		if (endIndex != -1) {
			result = result.substring(0, endIndex);
		}
		return result;
	}
	
	public static String fillHeadCharsLen(String strOri, int len) {
		return fillHeadCharsLen(strOri, "0", len);
	}

	public static String fillBackCharsLen(String strOri, int len) {
		return fillBackCharsLen(strOri, "0", len);
	}

	public static String fillHeadCharsLen(String strOri, String subStr, int len) {
		if ((strOri == null) || (strOri.trim().length() == 0)) {
			strOri = "";
		}
		if (subStr == null) {
			subStr = " ";
		}
		StringBuffer fileStrBuf = new StringBuffer();
		for (int i = 0; i < len; i++) {
			fileStrBuf.append(subStr);
		}

		subStr = fileStrBuf.toString() + strOri;

		return subStr.substring(subStr.length() - len, subStr.length());
	}

	public static String fillBackCharsLen(String strOri, String subStr, int len) {
		if ((strOri == null) || (strOri.trim().length() == 0)) {
			strOri = "";
		}
		if (subStr == null) {
			subStr = " ";
		}
		StringBuffer fillStrBuf = new StringBuffer();
		for (int i = 0; i < len; i++) {
			fillStrBuf.append(subStr);
		}

		subStr = strOri + fillStrBuf.toString();

		return subStr.substring(0, len);
	}

	public static String fillHeadChars(String strOri, int counter) {
		return fillHeadChars(strOri, "0", counter);
	}

	public static String fillBackChars(String strOri, int counter) {
		return fillBackChars(strOri, "0", counter);
	}

	public static String fillHeadChars(String strOri, String subStr, int counter) {
		if ((strOri == null) || (strOri.trim().length() == 0)) {
			strOri = "";
		}
		if ((counter <= 0) || (subStr == null)) {
			return strOri;
		}
		StringBuffer fillStrBuf = new StringBuffer("");
		for (int i = 0; i < counter; i++) {
			fillStrBuf.append(subStr);
		}

		return fillStrBuf.toString() + strOri;
	}

	public static String fillBackChars(String strOri, String subStr, int counter) {
		if ((strOri == null) || (strOri.trim().length() == 0)) {
			strOri = "";
		}
		if ((counter <= 0) || (subStr == null)) {
			return strOri;
		}
		StringBuffer fillStrBuf = new StringBuffer("");
		for (int i = 0; i < counter; i++) {
			fillStrBuf.append(subStr);
		}

		return strOri + fillStrBuf.toString();
	}

	/**
	 * 判断某对象是否为空。
	 *  
	 * @param strObj
	 *            Object
	 * 
	 * @return boolean
	 */
	public static boolean isEmpty(Object strObj) {
		return strObj == null || strObj.toString().trim().length() < 1;
	}

	/**
	 * 判断某字符串是否为空。
	 * 
	 * @param str
	 *            String
	 * 
	 * @return boolean
	 */
	public static boolean isStrEmpty(String str) {
		return str == null || str.trim().length() < 1;
	}

	/**
	 * 检查某字符串长度是否小于指定长度。
	 * 
	 * @param text
	 *            字符串。
	 * @param len
	 *            指定长度。
	 *            
	 * @return boolean
	 */
	public static boolean chkTextLen(String text, int len) {
		return text != null && text.length() <= len;
	}

	/**
	 * 检查某（去空格后）字符串长度是否小于指定长度。
	 * 
	 * @param text
	 *            String
	 * @param len
	 *            int
	 * 
	 * @return boolean
	 */
	public static boolean chkTextTrimLen(String text, int len) {
		return text != null && text.trim().length() <= len;
	}

	/**
	 * 判断指定字符是否为数字。
	 *  
	 * @param ch
	 *            char
	 * 
	 * @return boolean
	 */
	public static boolean isCharNum(char ch) {
		return ch > '/' && ch < ':';
	}

	/**
	 * 判断某字符串是否由数字构成。
	 * 
	 * @param strSrc
	 *            String
	 * 
	 * @return boolean
	 */
	public static boolean isNum(String strSrc) {
		for (int i = 0; i < strSrc.length(); i++) {
			if (!isCharNum(strSrc.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 判断某字符串是否都是由数字构成。
	 * 
	 * @param str
	 *            String
	 * 
	 * @return boolean
	 */
	public static boolean isStrNum(String str) {
		if (isStrEmpty(str)) {
			return true;
		}
		boolean notNum = false;
		for (int i = 0; i < str.length(); i++) {
			if (!isCharNum(str.charAt(i))) {
				notNum = true;
			}
		}

		return !notNum;
	}

	/**
	 * 判断某字符是否是字母。
	 *  
	 * @param ch
	 *            char
	 * 
	 * @return boolean
	 */
	public static boolean isCharLetter(char ch) {
		return ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z';
	}

	/**
	 * 判断某字符串是否都由大些字母构成。
	 * 
	 * @param str
	 *            String
	 * 
	 * @return boolean
	 */
	public static boolean isStrLetter(String str) {
		if (isStrEmpty(str)) {
			return true;
		}
		boolean notLetter = false;
		for (int i = 0; i < str.length(); i++) {
			if (!isCharLetter(str.charAt(i))) {
				notLetter = true;
			}
		}

		return !notLetter;
	}

	/**
	 * 将null转换成""空串。
	 * 
	 * @param content
	 *            String
	 * 
	 * @return String
	 */
	public static String nullToSpace(String content) {
		if (content == null) {
			content = "";
		}
		return content;
	}

	/**
	 * 将某SQL语句进行16进制编码。
	 * 
	 * @param sql
	 *            String
	 * 
	 * @return String
	 */
	public static String encodeSQL(String sql) {
		StringBuffer tempBuff = new StringBuffer();
		for (int i = 0; i < sql.length(); i++) {
			tempBuff.append(Integer.toHexString(sql.charAt(i)));
		}
		return tempBuff.toString();
	}

	/**
	 * 还原被16进制编码的SQL语句。
	 * 
	 * @param encoded
	 *            String
	 * 
	 * @return String
	 */
	public static String decodeSQL(String encoded) {
		StringBuffer tempBuff = new StringBuffer();
		for (int i = 0; i < encoded.length(); i += 2) {
			tempBuff.append((char) Integer.parseInt(
					encoded.substring(i, i + 2), 16));
		}

		return tempBuff.toString();
	}

	/**
	 * 获得绝对路径。
	 * 
	 * @param path
	 *            String
	 * @param context
	 *            String
	 * 
	 * @return String
	 */
	public static String getAbsolutePath(String path, String context) {
		int i1 = path.indexOf(context);
		if (i1 < 0) {
			return path;
		} else {
			return path.substring(path.indexOf(context) + context.length());
		}
	}

	/**
	 * 获得某字符串的子串。
	 * 
	 * @param str
	 *            String
	 * @param sindex
	 *            int
	 * @param eindex
	 *            int
	 * 
	 * @return String
	 */
	public static String getSubString(String str, int sindex, int eindex) {
		if (str == null) {
			return "";
		}
		if (str.trim().length() <= 0) {
			return "";
		}
		if (str.length() > sindex) {
			if (eindex >= 0) {
				return str.substring(sindex, eindex);
			}
			if (eindex < 0) {
				return str.substring(sindex);
			}
		}
		return "";
	}

	/**
	 * 获得某字符串数组的部分内容。
	 * 
	 * @param strs
	 *            String
	 * @param size
	 *            int
	 * 
	 * @return String[]
	 */
	public static String[] getValues(String strs[], int size) {
		String strs1[] = new String[size];
		for (int i = 0; i < size; i++) {
			strs1[i] = "";
		}

		if (strs == null) {
			return strs1;
		}
		if (strs.length < size) {
			for (int i = 0; i < strs.length; i++) {
				strs1[i] = strs[i];
			}

			return strs1;
		} else {
			return strs;
		}
	}

	/**
	 * 将某字符串中的部分字符串替换成其他字符串。
	 * 
	 * @param strSource
	 *            String
	 * @param strFrom
	 *            String
	 * @param strTo
	 *            String
	 * 
	 * @return String
	 */
	public static String replaceStrAll(String strSource, String strFrom,
			String strTo) {
		StringBuffer strBufDest = new StringBuffer();
		int intFromLen = strFrom.length();
		int intPos;
		while ((intPos = strSource.indexOf(strFrom)) != -1) {
			strBufDest.append(strSource.substring(0, intPos)).append(strTo);
			strSource = strSource.substring(intPos + intFromLen);
		}
		strBufDest.append(strSource);
		return strBufDest.toString();
	}

	/**
	 * 判断某字符串中是否持有某字符串数组中指定的字符串。
	 * 
	 * @param str1
	 *            String
	 * @param strarray
	 *            String
	 * 
	 * @return boolean
	 */
	public static boolean includestr(String str1, String strarray[]) {
		if (strarray == null || strarray.length <= 0) {
			return false;
		}
		for (int i = 0; i < strarray.length; i++) {
			if (strarray[i] == null) {
				if (str1 == null) {
					return true;
				}
				continue;
			}
			if (strarray[i].trim().equals(str1)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 将某被“\n”、“\r”分隔开的字符串转换成字符串数组。
	 * 
	 * @param fvalue
	 *            String
	 * 
	 * @return String[]
	 */
	public static String[] getAreaValues(String fvalue) {
		String tmpstr = fvalue;
		int i = 0;
		if (tmpstr == null) {
			return null;
		}
		if ("".equals(tmpstr.trim())) {
			return null;
		}
		for (; tmpstr.indexOf("\n") >= 0; tmpstr = tmpstr.substring(tmpstr
				.indexOf("\n") + 1)) {
			i++;
		}

		if ("".equals(tmpstr.trim())) {
			i--;
		}
		String fvalues[] = new String[i + 1];
		tmpstr = fvalue;
		i = 0;
		for (; tmpstr.indexOf("\n") >= 0; tmpstr = tmpstr.substring(tmpstr
				.indexOf("\n") + 1)) {
			fvalues[i] = tmpstr.substring(0, tmpstr.indexOf("\n"));
			if (fvalues[i].indexOf("\r") >= 0) {
				fvalues[i] = fvalues[i].substring(0, fvalues[i].indexOf("\r"));
			}
			i++;
		}

		if (!"".equals(tmpstr.trim())) {
			fvalues[i] = tmpstr;
		}
		return fvalues;
	}

	/**
	 * 转换某字符串。
	 * 
	 * @param fvalue
	 *            String
	 * 
	 * @return String
	 */
	public static String getRealAreaValues(String fvalue) {
		String tmpstr = fvalue;
		StringBuffer returnStrBuf = new StringBuffer("");
		if (tmpstr == null) {
			return null;
		}
		if ("".equals(tmpstr.trim())) {
			return "";
		}
		for (; tmpstr.indexOf("|") > 0; tmpstr = tmpstr.substring(tmpstr
				.indexOf("|") + 1)) {
			returnStrBuf.append(tmpstr.substring(0, tmpstr.indexOf("|")))
					.append("\n");
		}

		returnStrBuf.append(tmpstr);
		return returnStrBuf.toString();
	}

	/**
	 * 计算字符串中某字符出现的次数。
	 * 
	 * @param strInput
	 *            String
	 * @param chr
	 *            char
	 * 
	 * @return int
	 */
	public static int countChar(String strInput, char chr) {
		int iCount = 0;
		char chrTmp = ' ';
		if (strInput.trim().length() == 0) {
			return 0;
		}
		for (int i = 0; i < strInput.length(); i++) {
			chrTmp = strInput.charAt(i);
			if (chrTmp == chr) {
				iCount++;
			}
		}

		return iCount;
	}

	/**
	 * 将字符串数组转换成字符串。
	 * 
	 * @param strs
	 *            字符串数组。
	 * 
	 * @return String
	 */
	public static String strArrayToStr(String strs[]) {
		return strArrayToStr(strs, null);
	}

	/**
	 * 打印出字符串数组的内容。
	 * 
	 * @param strs
	 *            String
	 */
	public static void printStrs(String strs[]) {
		for (int i = 0; i < strs.length; i++) {
			log.info(strs[i]);
		}
	}

	/**
	 * 打印出二维数组中各字符串的具体内容。
	 * 
	 * @param dualStr
	 *            String
	 */
	public static void printDualStr(String dualStr[][]) {
		for (int i = 0; i < dualStr.length; i++) {
			for (int j = 0; j < dualStr[i].length; j++) {
				log.info((new StringBuilder()).append(dualStr[i][j])
						.append(" ").toString());
			}
		}
	}

	/**
	 * 将二维数组的行列互换。
	 * 
	 * @param dualStr
	 *            String
	 * 
	 * @return String[][]
	 */
	public static String[][] rowToColumn(String dualStr[][]) {
		String returnDualStr[][] = (String[][]) null;
		if (dualStr != null) {
			returnDualStr = new String[dualStr[0].length][dualStr.length];
			for (int i = 0; i < dualStr.length; i++) {
				for (int j = 0; j < dualStr[0].length; j++) {
					returnDualStr[j][i] = dualStr[i][j];
				}
			}
		}
		return returnDualStr;
	}

	/**
	 * 对字符串中的“\”、“'”进行转义。
	 * 
	 * @param inStr
	 *            String
	 * @return String
	 */
	public static String latinString(String inStr) {
		String res = inStr;
		if (null == res) {
			return null;
		} else {
			res = replaceStrAll(res, "\"", "\\\"");
			res = replaceStrAll(res, "'", "\\'");
			return res;
		}
	}
	
	/**
	 * 将字符串中的空格替换成某字符串。 
	 * 
	 * @param strTarget
	 *            待替换的字符串。
	 * @param strNew
	 *            替换空格的字符串。
	 * 
	 * @return String
	 */
	public static String replaceWhiteSpace(String strTarget, String strNew) {
		int iIndex = -1;
		do {
			char cRep = ' ';
			iIndex = strTarget.indexOf(cRep);
			if (iIndex >= 0) {
				String strTemp = null;
				strTemp = strTarget.substring(0, iIndex);
				strTarget = (new StringBuilder()).append(strTemp)
						.append(strNew).append(strTarget.substring(iIndex + 1))
						.toString();
			} else {
				return strTarget;
			}
		} while (true);
	}

	/**
	 * 将double数据转换成字符串，但只保留小数点后面的几位。
	 * 
	 * @param amount
	 *            double
	 * @param length
	 *            精度控制
	 * 
	 * @return String
	 */
	public static String double2str(double amount, int length) {
		String strAmt = Double.toString(amount);
		int pos = strAmt.indexOf('.');
		if (pos != -1 && strAmt.length() > length + pos + 1) {
			strAmt = strAmt.substring(0, pos + length + 1);
		}
		return strAmt;
	}

	/**
	 * 根据某字符劈开字符串。
	 * 
	 * @param str
	 *            String
	 * @param chr
	 *            char
	 * 
	 * @return String[]
	 */
	public static String[] doSplit(String str, char chr) {
		int iCount = 0;
		char chrTmp = ' ';
		for (int i = 0; i < str.length(); i++) {
			chrTmp = str.charAt(i);
			if (chrTmp == chr) {
				iCount++;
			}
		}

		String strArray[] = new String[iCount];
		for (int i = 0; i < iCount; i++) {
			int iPos = str.indexOf(chr);
			if (iPos == 0) {
				strArray[i] = "";
			} else {
				strArray[i] = str.substring(0, iPos);
			}
			str = str.substring(iPos + 1);
		}

		return strArray;
	}

	/**
	 * 根据某字符劈开字符串。
	 * 
	 * @param src
	 *            String
	 * @param splitchar
	 *            String
	 * 
	 * @return String[]
	 */
	public static String[] strSplit(String src, String splitchar) {
		int resultSize = 0;
		int len = src.length();
		int idx = 0;
		String strTemp = "";
		for (int i = 0; i < len; i++) {
			if (src.substring(i, i + 1).equals(splitchar)) {
				resultSize++;
			}
		}

		if (len > 1 && !src.substring(len - 1, len).equals(splitchar)) {
			resultSize++;
		}
		String result[] = new String[resultSize];
		for (int i = 0; i < len; i++) {
			if (src.substring(i, i + 1).equals(splitchar)) {
				result[idx] = strTemp;
				idx++;
				strTemp = "";
			} else {
				strTemp = (new StringBuilder()).append(String.valueOf(strTemp))
						.append(String.valueOf(src.charAt(i))).toString();
			}
		}

		if (!"".equals(strTemp)) {
			result[idx] = strTemp;
		}
		return result;
	}

	/**
	 * 将被某特定分隔符分割的字符串拆分成字符串数组。
	 * 
	 * @param strToSplit
	 *            待拆分的字符串。
	 * @param strSeparator
	 *            特定分隔符。
	 * 
	 * @return String[]
	 */
	public static String[] split(String strToSplit, String strSeparator) {
		List tmpList = new ArrayList();
		int iFromIndex = 0;
		int iCurIndex = strToSplit.length();
		String strUnitInfo = "";
		for (int iCurCounts = 0; iCurIndex != -1
				&& iFromIndex < strToSplit.length(); iCurCounts++) {
			iCurIndex = strToSplit.indexOf(strSeparator, iFromIndex);
			if (iCurIndex == -1) {
				strUnitInfo = strToSplit.substring(iFromIndex,
						strToSplit.length());
			} else {
				strUnitInfo = strToSplit.substring(iFromIndex, iCurIndex);
				iFromIndex = iCurIndex + strSeparator.length();
				// 如果是二个空字符串的情况
				if (iFromIndex >= strToSplit.length()) {
					tmpList.add(strUnitInfo);
					tmpList.add("");
					break;
				}
			}
			tmpList.add(strUnitInfo);
		}

		int iCounts = tmpList.size();
		String tmpArray[] = new String[iCounts];
		for (int i = 0; i < iCounts; i++) {
			tmpArray[i] = (String) tmpList.get(i);
		}

		return tmpArray;
	}

	/**
	 * 将字符串数组转换成由特定分隔符隔开的字符串。
	 * 
	 * @param strs
	 *            字符串数组。
	 * @param separator
	 *            特定分隔符。
	 * 
	 * @return String
	 */
	public static String strArrayToStr(String strs[], String separator) {
		StringBuffer returnstr = new StringBuffer("");
		if (strs == null) {
			return "";
		}
		if (separator == null) {
			separator = "";
		}
		for (int i = 0; i < strs.length; i++) {
			returnstr.append(strs[i]);
			if (i < strs.length - 1) {
				returnstr.append(separator);
			}
		}

		return returnstr.toString();
	}

	/**
	 * 将某数组转换成由特定分隔符隔开的字符串。
	 * 
	 * @param objects
	 *            数组。
	 * @param separator
	 *            分隔符。
	 * 
	 * @return String
	 */
	public static String objectArrayToStr(Object objects[], String separator) {
		StringBuffer returnstr = new StringBuffer("");
		if (objects == null) {
			return "";
		}
		if (separator == null) {
			separator = "";
		}
		for (int i = 0; i < objects.length; i++) {
			returnstr.append(String.valueOf(objects[i]));
			if (i < objects.length - 1) {
				returnstr.append(separator);
			}
		}
		return returnstr.toString();
	}

	/**
	 * 将列表转换成由某分隔符隔开的字符串。
	 * 
	 * @param element
	 *            List
	 * @param separator
	 *            分隔符。
	 * 
	 * @return String
	 */
	public static String listToStr(List element, String separator) {
		StringBuffer returnstr = new StringBuffer("");
		if (element == null) {
			return "";
		}
		if (separator == null) {
			separator = "";
		}
		Iterator it = element.iterator();
		do {
			if (!it.hasNext()) {
				break;
			}
			returnstr.append(String.valueOf(it.next()));
			if (it.hasNext()) {
				returnstr.append(separator);
			}
		} while (true);
		return returnstr.toString();
	}

	/**
	 * 将列表转换成字符串数组。
	 * 
	 * @param element
	 *            List
	 * 
	 * @return String[]
	 */
	public static String[] listToStrArray(List element) {
		if (element == null || element.size() == 0) {
			return null;
		}
		Iterator it = element.iterator();
		String strArray[] = new String[element.size()];
		for (int i = 0; it.hasNext(); i++) {
			strArray[i] = String.valueOf(it.next());
		}

		return strArray;
	}

	/**
	 * 将被某分隔符分割开的字符串转换成列表。
	 * 
	 * @param str
	 *            字符串。
	 * @param separator
	 *            分隔符。
	 * 
	 * @return List 被转换成的列表。
	 */
	public static List strToList(String str, String separator) {
		if (str == null || "".equals(str)) {
			return null;
		}
		if (separator == null) {
			separator = "";
		}
		String strArr[] = str.split(separator);
		int size = strArr.length;
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < size; i++) {
			list.add(strArr[i]);
		}
		return list;
	}

	/**
	 * 判断某字符串中是否含有被某分隔符隔开的子字符串。
	 * 
	 * @param str
	 *            字符串
	 * @param substr
	 *            子字符串
	 * @param sepatator
	 *            分隔符
	 * 
	 * @return boolean
	 */
	public static boolean isExist(String str, String substr, String sepatator) {
		if (str == null || "".equals(str.trim())) {
			return false;
		}
		if (substr == null || "".equals(substr.trim())) {
			return false;
		}
		String strArr[] = str.split(sepatator);
		int size = strArr.length;
		for (int i = 0; i < size; i++) {
			if (strArr[i].equals(substr)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断字符串中是否包含某一子字符串。
	 * 
	 * @param str
	 *            String
	 * @param substr
	 *            String
	 * 
	 * @return boolean
	 */
	public static boolean isExist(String str, String substr) {
		return isExist(str, substr, ",");
	}

	/**
	 * 生成str%格式字符串。
	 * 
	 * @param str
	 *            String
	 * 
	 * @return String
	 */
	public static String leftInclude(String str) {
		if (str == null || "".equals(str)) {
			return str;
		} else {
			return (new StringBuilder()).append(str).append("%").toString();
		}
	}

	/**
	 * 生成%str格式字符串。
	 * 
	 * @param str
	 *            String
	 * 
	 * @return String
	 */
	public static String rightInclude(String str) {
		if (str == null || "".equals(str)) {
			return str;
		} else {
			return (new StringBuilder()).append("%").append(str).toString();
		}
	}

	/**
	 * 生成%str%格式字符串。
	 * 
	 * @param str
	 *            String
	 * 
	 * @return String
	 */
	public static String include(String str) {
		if (str == null || "".equals(str)) {
			return str;
		} else {
			return (new StringBuilder()).append("%").append(str).append("%")
					.toString();
		}
	}

	/**
	 * 将整数变成定长字符串，位数不够时左边补0。
	 * 
	 * @param i
	 *            long
	 * @param len
	 *            int
	 * 
	 * @return String
	 */
	public static String intToFixLenString(long i, int len) {
		String istr = String.valueOf(i);
		if (istr.length() >= len) {
			return istr;
		}
		StringBuffer sb = new StringBuffer("");
		for (int j = 0; j < len - istr.length(); j++) {
			sb.append("0");
		}
		sb.append(istr);
		return sb.toString();
	}

	/**
	 * 判断字符串是否为空
	 *
	 * @param s
	 * @return 是否为空
	 */
	public static boolean isNullStr(String s) {
		if (s == null || s.trim().length() <= 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isBlank(String str) {
		int strLen;
		if(str != null && (strLen = str.length()) != 0) {
			for(int i = 0; i < strLen; ++i) {
				if(!Character.isWhitespace(str.charAt(i))) {
					return false;
				}
			}

			return true;
		} else {
			return true;
		}
	}

	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	/**
	 * 如果不为空，则设置值
	 * @param target
	 * @param source
	 */
	public static void setValueIfNotBlank(String target, String source) {
		if (isNotBlank(source)){
			target = source;
		}
	}
	/**
	 * 判断某个对象是否为空 集合类、数组做特殊处理
	 *
	 * @param obj
	 * @return 如为空，返回true,否则false
	 * @author yehailong
	 */
	public static boolean booleanisEmpty(Object obj) {
		if (obj == null) {
            return true;
        }

		// 如果不为null，需要处理几种特殊对象类型
		if (obj instanceof String) {
			if("null".equals(((String) obj).toLowerCase())){
				return false;
			}else{
				return "".equals(((String) obj).trim());
			}
		} else if (obj instanceof Collection) {
			// 对象为集合
			Collection coll = (Collection) obj;
			return coll.size() == 0;
		} else if (obj instanceof Map) {
			// 对象为Map
			Map map = (Map) obj;
			return map.size() == 0;
		} else if (obj.getClass().isArray()) {
			// 对象为数组
			return Array.getLength(obj) == 0;
		} else{
			// 其他类型，只要不为null，即不为empty
			return false;
		}
	}
	/**
	 * 模糊查询字符串。
	 *
	 * @param value
	 *            String
	 *
	 * @return String
	 */
	public static String queryString(String value) {
		if(value==null||value==""){
			return null;
		}
		String result = "%";
		int length = value.length();
		for(int x = 0;x<length;x++){
			result += value.substring(x,x+1) + "%";
		}
		return result;
	}

	/***
	 *
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean equals(String str1, String str2){
		return  (str1+"").equals((str2+""));
	}

	/***
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		if (str == null || "".equals(str.trim())) {
			return false;
		}
		return true;
	}
}