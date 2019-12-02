package org.wltea.analyzer.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 汉字转换成拼音方法
 * @author 蔡龙
 */

public class Pinyin4j {

    public static String getFullSpellWithSpace(String chinese) {

        StringBuffer pybf = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        List<Character> list = new ArrayList<Character>();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 128) {
                if(!list.isEmpty()){
                    pybf.append(  StringUtils.join(list,"")+" ");
                    list.clear();
                }
                try {
                    pybf.append(PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat)[0]+" ");
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                list.add(arr[i]);
            }
        }
        if(!list.isEmpty()){
            pybf.append(  StringUtils.join(list,"")+" ");
            list=null; //help GC
        }
        return pybf.toString();
    }
    public static void main(String[] args) throws BadHanyuPinyinOutputFormatCombination {
        String str="中国天安门mi检索12名00";

        String result=Pinyin4j.getFullSpellWithSpace(str);
        System.out.println(result);

    }

    /**
     * 获取汉字串拼音首字母，英文字符不变
     *
     * @param chinese 汉字串
     * @return 汉语拼音首字母
     */
    public static String cn2FirstSpell(String chinese) {
        StringBuffer pybf = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 128) {
                try {
                    String[] _t = PinyinHelper.toHanyuPinyinStringArray(arr[i],
                            defaultFormat);
                    if (_t != null) {
                        pybf.append(_t[0].charAt(0));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pybf.append(arr[i]);
            }
        }
        return pybf.toString().replaceAll("\\W", "").trim();
    }

    /**
     * 获取汉字串拼音，英文字符不变
     *
     * @param chinese
     *            汉字串
     * @return 汉语拼音
     */
    public static String cn2Spell(String chinese) {
        StringBuffer pybf = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 128) {
                try {
                    pybf.append(PinyinHelper.toHanyuPinyinStringArray(arr[i],
                            defaultFormat)[0]);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pybf.append(arr[i]);
            }
        }
        return pybf.toString();
    }

    HanyuPinyinOutputFormat format = null;
    public static enum Type {
        UPPERCASE,			  //全部大写
        LOWERCASE,			  //全部小写
        FIRSTUPPER            //首字母大写
    }
    /**
     * @Title: 获取中文串拼音首字母，英文字符不变
     * @methodName:  getFirstSpell
     * @param chinese 汉字串
     * @return java.lang.String 中文拼音首字母
     * @Description:
     *
     * @author: FLY
     * @date:  2017-11-13 17:13
     */
    public static String getFirstSpell(String chinese) {


        StringBuffer pybf = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 128) {
                try {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
                    if (temp != null) {
                        pybf.append(temp[0].charAt(0));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pybf.append(arr[i]);
            }
        }
        return pybf.toString().replaceAll("\\W", "").trim();
    }

    /**
     * @Title:  获取中文串拼音，英文字符不变
     * @methodName:  getFullSpell
     * @param chinese 中文字符串
     * @return java.lang.String  中文拼音
     * @Description:
     *
     * @author: FLY
     * @date:  2017-11-13 17:15
     */
    public static String getFullSpell(String chinese) {

        StringBuffer pybf = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 128) {
                try {
                    pybf.append(PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat)[0]);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pybf.append(arr[i]);
            }
        }
        return pybf.toString();
    }

    public Pinyin4j(){
        format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    }

    private static boolean isChinese(char a) {
        int v = a;
        return (v >= 19968) && (v <= 171941);
    }
    /**
     * 转换全部大写
     * @param str 字符串
     * @return str为颐和园 ,return获取到的是YHY
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public String toPinYinUppercase(String str) throws BadHanyuPinyinOutputFormatCombination{
        return toPinYin(str, "", Type.UPPERCASE);
    }

    /**
     * 转换全部大写
     * @param str 字符串
     * @param spera 转换字母间隔加的字符串,如果不需要为""
     * @return str为颐和园 ,spera为** return获取到的是Y**H**Y
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public String toPinYinUppercase(String str,String spera) throws BadHanyuPinyinOutputFormatCombination{
        return toPinYin(str, spera, Type.UPPERCASE);
    }

    /**
     * 转换全部小写
     * @param str 字符串
     * @return	str为颐和园 ,return获取到的是yhy
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public String toPinYinLowercase(String str) throws BadHanyuPinyinOutputFormatCombination{
        return toPinYin(str, "", Type.LOWERCASE);
    }

    /**
     * 转换全部小写
     * @param str 字符串
     * @param spera 转换字母间隔加的字符串,如果不需要为""
     * @return	str为颐和园 ,spera为** return获取到的是y**h**y
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public String toPinYinLowercase(String str,String spera) throws BadHanyuPinyinOutputFormatCombination{
        return toPinYin(str, spera, Type.LOWERCASE);
    }

    /**
     * 获取拼音首字母(大写)
     * @param str 字符串
     * @return str为颐和园 ,return获取到的是Y
     * @throws BadHanyuPinyinOutputFormatCombination 异常信息
     */
    public String toPinYinUppercaseInitials(String str) throws BadHanyuPinyinOutputFormatCombination {
        String initials = null;
        String py = toPinYinUppercase(str);
        if(py.length()>1){
            initials = py.substring(0, 1);
        }
        if(py.length()<=1){
            initials = py;
        }
        return initials.trim();
    }

    /**
     * 获取拼音首字母(小写)
     * @param str 字符串
     * @return str为颐和园 ,return获取到的是y
     * @throws BadHanyuPinyinOutputFormatCombination 异常信息
     */
    public String toPinYinLowercaseInitials(String str) throws BadHanyuPinyinOutputFormatCombination {
        String initials = null;
        String py = toPinYinLowercase(str);
        if(py.length()>1){
            initials = py.substring(0, 1);
        }
        if(py.length()<=1){
            initials = py;
        }
        return initials.trim();
    }

    /**
     * 将str转换成拼音，如果不是汉字或者没有对应的拼音，则不作转换
     * @param str    字符串
     * @param spera  默认,可为""
     * @param type   转换格式
     * @return 按照转换格式转换成字符串
     * @throws BadHanyuPinyinOutputFormatCombination 异常信息
     */
    public String toPinYin(String str, String spera, Type type) throws BadHanyuPinyinOutputFormatCombination {
        if(str == null || str.trim().length()==0) {
            return "";
        }
        if(type == Type.UPPERCASE) {
            format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        } else{
            format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        }
        String py = "";
        String temp = "";
        String[] t;
        for(int i=0;i<str.length();i++){
            char c = str.charAt(i);
            if((int)c <= 128)  {
                py += c;
            }else{
                t = PinyinHelper.toHanyuPinyinStringArray(c, format);
                if(t == null) {
                    py += c;
                }else{
                    temp = t[0];
                    if(type == Type.FIRSTUPPER) {
                        temp = t[0].toUpperCase().charAt(0)+temp.substring(1);
                    }
                    if(temp.length()>=1){
                        temp = temp.substring(0, 1);
                    }
                    py += temp+(i==str.length()-1?"":spera);
                }
            }
        }
        return py.trim();
    }
}
