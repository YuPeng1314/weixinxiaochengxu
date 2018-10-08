package com.huayu.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 作者 fzh:
 * @version 创建时间：2016年11月25日 上午11:05:31 类说明
 */
public class RegularValidateUtil {

	/***** 手机号码验证 ****/
	public static final String REGULAR_MOBLIE = "^(1\\d{10}$)";
	/****** 邮箱验证 ******/
	public static final String REGULAR_EMAIL = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";
	public static final String EXG_EMAIL = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

	/**
	 * 正则表达式：验证身份证
	 */
	public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

	/**
	 * 加权因子
	 */
	private final static int[] DIVISOR = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

	/**
	 * 校验码
	 */
	private final static char[] VALIDATECODE = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
	/**
	 * 验证码字符表
	 */
	private final static char[] CHECKNO = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y' };

	/**
	 * 验证手机号码
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean checkMobileNumber(String mobileNumber) {
		boolean flag = false;
		try {
			Pattern regex = Pattern
					.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
			Matcher matcher = regex.matcher(mobileNumber);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 身份证验证
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isIDCard(String idCard) {
		return Pattern.matches(REGEX_ID_CARD, idCard);
	}

	/**
	 * 匹配中国邮政编码
	 * 
	 * @param postcode
	 *            邮政编码
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean isPostCode(String postCode) {
		String reg = "[1-9]\\d{5}";
		return Pattern.matches(reg, postCode);
	}

	public static boolean isNewIdCard(final String newIdCard) {
		if (!newIdCard.matches("^\\d{17}(\\d|x|X)$")) {
			return false;
		}
		String birthday = newIdCard.substring(6, 14);
		if (!DateTimeUtils.isDateStr(birthday, "yyyyMMdd")) {
			return false;
		}
		if (DateTimeUtils.parseStringToDate(birthday, "yyyyMMdd").after(new Date())) {
			return false;
		}
		// 检验最后以为
		if (!checkLastLetter(newIdCard)) {
			return false;
		}
		return true;
	}

	/**
	 * 检验新身份证最后一个校验位的合法性
	 * 
	 * @param newIdCard
	 * @return
	 */
	private static boolean checkLastLetter(final String newIdCard) {
		String str = newIdCard.substring(0, 17);
		String last = newIdCard.substring(17);
		// 加权因子校验
		char[] numChar = str.toCharArray();
		Long result = 0L;
		for (int i = 0; i < numChar.length; i++) {
			result += Integer.parseInt(Character.toString(numChar[i])) * DIVISOR[i];
		}
		int checkIndex = (int) (result % 11);
		if (Character.toString(last.toCharArray()[0]).equalsIgnoreCase(Character.toString(VALIDATECODE[checkIndex]))) {
			return true;
		}
		return false;
	}

	public static String getSex(String idCard) {
		try {
			String sex = "1";
			if (Integer.parseInt(idCard.substring(16).substring(0, 1)) % 2 == 0) {// 判断性别
				sex = "0";
			} else {
				sex = "1";
			}
			return sex;
		} catch (Exception e) {
			return "1";
		}
	}

	public static Date getBirthday(String idCard) {
		String birthday = idCard.substring(6, 14);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		try {
			return format.parse(birthday);
		} catch (ParseException e) {
			return null;
		}
	}

	public static Integer getAge(String idCard) {
		Date birthday = getBirthday(idCard);
		if (null == birthday) {
			return -1;
		}
		Calendar calendar = Calendar.getInstance();
		if (calendar.before(birthday)) {
			// 生日在当前如期的后面
			return -1;
		}
		// 获得当前的时间
		int currentYear = calendar.get(Calendar.YEAR);
		int currentMonth = calendar.get(Calendar.MONTH) + 1;
		int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.setTime(birthday);
		// 获得生日的时间
		int birthdayYear = calendar.get(Calendar.YEAR);
		int birthdayMonth = calendar.get(Calendar.MONTH) + 1;
		int birthdayDay = calendar.get(Calendar.DAY_OF_MONTH);

		int age = currentYear - birthdayYear;
		if (currentMonth <= birthdayMonth) {
			if (currentMonth == birthdayMonth) {
				if (currentDay < birthdayDay) {
					age--;
				}
			}
		}
		return age;
	}

	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

}
