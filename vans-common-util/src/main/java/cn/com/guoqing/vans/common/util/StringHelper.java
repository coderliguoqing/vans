package cn.com.guoqing.vans.common.util;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 *
 * @author mij
 */
public class StringHelper extends StringUtils {

    /**
     * 字符连接符
     */
    private static final char SEPARATOR = '_';
    /**
     * 默认编码
     */
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    /**
     * 转换为字节数组
     *
     * @param str 字符串
     * @return byte[] byte [ ]
     */
    public static byte[] getBytes(String str) {
        if (str == null) {
            throw new NullPointerException("str cannot be null");
        }
        return str.getBytes(DEFAULT_CHARSET);
    }

    /**
     * 转换为字节数组
     *
     * @param bytes 字节数组
     * @return String string
     */
    public static String toString(byte[] bytes) {
        return new String(bytes, DEFAULT_CHARSET);
    }

    /**
     * 是否包含字符串
     *
     * @param str  验证字符串
     * @param strs 字符串组
     * @return 包含返回true boolean
     */
    public static boolean inString(String str, String... strs) {
        if (str != null) {
            for (String s : strs) {
                if (str.equals(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 替换掉HTML标签方法
     *
     * @param html the html
     * @return the string
     */
    public static String replaceHtml(String html) {
        if (isBlank(html)) {
            return "";
        }
        String regEx = "<.+?>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        return m.replaceAll("");
    }

    /**
     * 驼峰命名法工具
     *
     * @param s the s
     * @return String  toCamelCase("hello_world") == "helloWorld" toCapitalizeCamelCase("hello_world") == "HelloWorld" toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }

        String ls = s.toLowerCase();

        StringBuilder sb = new StringBuilder(ls.length());
        boolean upperCase = false;
        for (int i = 0; i < ls.length(); i++) {
            char c = ls.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 驼峰命名法工具
     *
     * @param s the s
     * @return String  toCamelCase("hello_world") == "helloWorld" toCapitalizeCamelCase("hello_world") == "HelloWorld" toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        String cs = toCamelCase(s);
        return cs.substring(0, 1).toUpperCase() + cs.substring(1);
    }

    /**
     * 驼峰命名法工具
     *
     * @param s the s
     * @return String  toCamelCase("hello_world") == "helloWorld" toCapitalizeCamelCase("hello_world") == "HelloWorld" toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toUnderScoreCase(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i > 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * Trim to default string.
     *
     * @param str          字符串
     * @param defaultValue 默认值
     * @return String string
     */
    public static String trimToDefault(final String str, String defaultValue) {
        final String ts = trim(str);
        return isEmpty(ts) ? defaultValue : ts;
    }

    /**
     * 验证邮箱
     * @param email
     * @return
     */
    public static boolean checkEmail(String email){
        boolean flag = false;
        try{
            String check = "^\\w+@\\w+(\\.\\w+)+$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        }catch(Exception e){
            flag = false;
        }
        return flag;
    }

    /**
     * 验证手机号码
     * @param mobileNumber
     * @return
     */
    public static boolean checkMobileNumber(String mobileNumber){
        boolean flag = false;
        try{
            Pattern regex = Pattern.compile("^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        }catch(Exception e){
            flag = false;
        }
        return flag;
    }

    /**
     * 检验身份证号码有效性（copy from http://blog.csdn.net/gaohuanjie/article/details/26359151）
     * @param certificateNo 证件号码
     * @return TRUE：格式正确；  FALSE：格式错误
     */
    public static boolean CheckCertificateNo(String certificateNo) {
        if (certificateNo.length() == 15) {
            return false;
        } else if (certificateNo.length() == 18) {
            String address = certificateNo.substring(0, 6);//6位，地区代码
            String birthday = certificateNo.substring(6, 14);//8位，出生日期
            String sequenceCode = certificateNo.substring(14, 17);//3位，顺序码：奇为男，偶为女
            String checkCode = certificateNo.substring(17);//1位，校验码：检验位
            System.out.println("身份证号码:" + certificateNo + "、地区代码:" + address + "、出生日期:" + birthday + "、顺序码:" + sequenceCode + "、校验码:" + checkCode + "\n");
            String[] provinceArray = {"11:北京", "12:天津", "13:河北", "14:山西", "15:内蒙古", "21:辽宁", "22:吉林",
                "23:黑龙江", "31:上海", "32:江苏", "33:浙江", "34:安徽", "35:福建", "36:江西", "37:山东", "41:河南",
                "42:湖北 ", "43:湖南", "44:广东", "45:广西", "46:海南", "50:重庆", "51:四川", "52:贵州", "53:云南",
                "54:西藏 ", "61:陕西", "62:甘肃", "63:青海", "64:宁夏", "65:新疆", "71:台湾", "81:香港", "82:澳门",
                "91:国外"};
            boolean valideAddress = false;
            for (int i = 0; i < provinceArray.length; i++) {
                String provinceKey = provinceArray[i].split(":")[0];
                if (provinceKey.equals(address.substring(0, 2))) {
                    valideAddress = true;
                }
            }
            if (valideAddress) {
                String year = birthday.substring(0, 4);
                String month = birthday.substring(4, 6);
                String day = birthday.substring(6);
                Calendar cal = Calendar.getInstance();
                cal.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));
                Date tempDate = cal.getTime();
                if ((cal.get(Calendar.YEAR) != Integer.parseInt(year)
                    || cal.get(Calendar.MONTH) != Integer.parseInt(month) - 1
                    || cal.get(Calendar.DATE) != Integer.parseInt(day))) {//避免千年虫问题
                    return false;
                } else {
                    int[] weightedFactors = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};//加权因子
                    int[] valideCode = {1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2};//身份证验证位值，其中10代表X
                    int sum = 0;//声明加权求和变量
                    String[] certificateNoArray = new String[certificateNo.length()];
                    for (int i = 0; i < certificateNo.length(); i++) {
                        certificateNoArray[i] = String.valueOf(certificateNo.charAt(i));
                    }
                    if ("x".equals(certificateNoArray[17].toLowerCase())) {
                        certificateNoArray[17] = "10";//将最后位为x的验证码替换为10
                    }
                    for (int i = 0; i < 17; i++) {
                        sum += weightedFactors[i] * Integer.parseInt(certificateNoArray[i]);//加权求和
                    }
                    int valCodePosition = sum % 11;//得到验证码所在位置
                    if (Integer.parseInt(certificateNoArray[17]) != valideCode[valCodePosition]) {
                        return false;
                    } else {
                        return true;
                    }
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
