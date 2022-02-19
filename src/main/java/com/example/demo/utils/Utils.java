package com.example.demo.utils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static String getFormalizedCLanTag(String unprocessedTag) {
        if (unprocessedTag == null) return null;
        return formalizeMemberTag(unprocessedTag).replace("O", "0");
    }

    public static boolean compareMemberTags(String memberTag1, String memberTag2) {
        String memberTag1Formalized = formalizeMemberTag(memberTag1);
        String memberTag2Formalized = formalizeMemberTag(memberTag2);
        return memberTag1Formalized.equals(memberTag2Formalized);
    }

    public static boolean compareClanTags(String clanTag1, String clanTag2) {
        String clanTag1Formalized = getFormalizedCLanTag(clanTag1);
        String clanTag2Formalized = getFormalizedCLanTag(clanTag2);
        return clanTag1Formalized.equals(clanTag2Formalized);
    }

    public static String formalizeMemberTag(String unprocessedTag) {
        if (unprocessedTag == null) return null;
        String reg = "[^a-zA-Z0-9]";
        String result = unprocessedTag.replaceAll(reg, "");
        result = result.replace("O", "0");
        return result.trim().replace("#", "").toUpperCase();
    }

    public static String onlyKeepLettersAndNums(String unprocessedTag) {
        String formalizedClanTag = Utils.getFormalizedCLanTag(unprocessedTag);
        String reg = "[^a-zA-Z0-9]";
        return formalizedClanTag.replaceAll(reg, "");
    }

    public static String processCard(String unprocessedcard) {
        return unprocessedcard.replaceAll("'", "");
    }

    public static Float roundUp(float input, String decimal) {
        DecimalFormat df = new DecimalFormat(decimal);
        return Float.valueOf(df.format(input));
    }

    public static String onlyKeepNums(String unprocessedTag) {
        if (unprocessedTag == null)
            return "";
        String formalizedClanTag = Utils.getFormalizedCLanTag(unprocessedTag);
        String reg = "[^0-9]";
        return formalizedClanTag.replaceAll(reg, "");
    }

    public static String deleteAt(String qqwithat) {
        qqwithat = qqwithat.replaceAll("[^\\d.]", "");
        return qqwithat;
    }

    public static String getAtMsg(String qq) {
        return String.format("[CQ:at,qq=%s]", qq);
    }

    public static String replyCQCode(String messageid) {
        return String.format("[CQ:reply,id=%s]", messageid);
    }

    public static void copyToClipboard(String msg) {
        StringSelection stringSelection = new StringSelection(msg);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    public static String deleteErrorChars(String unprocessedString) {
        if (unprocessedString == null)
            return "";
        String utf8 = "[\\x01-\\x7f][^\\w]|[\\xc2-\\xdf][\\x80-\\xbf]|[\\xe0-\\xef][\\x80-\\xbf]{2}[^\\w]|[\\xf0-\\xff][\\x80-\\xbf]{3}[^\\w]";
        String gb2312 = "[\\x01-\\x7f][^\\w]|[\\x81-\\xfe][\\x40-\\xfe][^\\w]";
        String gbk = "[\\x01-\\x7f][^\\w]|[\\x81-\\xfe][\\x40-\\xfe][^\\w]";
        String processedString = unprocessedString.replaceAll(gbk, "");
        String regex = "[^\\p{L}\\p{N}\\p{P}\\p{Z}]";
        Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(unprocessedString);
        String result = matcher.replaceAll("");
        processedString = result;
        if (processedString.equals(""))
            processedString = "->一堆表情的名字<-";
        return processedString;
    }

    public static String convertToChinaTime(String str, int randomMin) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));
        Date date = null;
        try {
            date = format.parse(str);
            date = new Date(date.getTime() + randomMin * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
            return str;
        }

        return format.format(date);
    }

    public static String shorterName(String str) {
        String result = "";
        result = str.replaceAll("不打部落战只做捐兵狂", "不战");
        result = result.replaceAll("不打部落战", "不战");
        return result;
    }

    public static Map<String, String> retrieveValueFromObject(String objectString) {
        Map<String, String> result = new HashMap<String, String>();
        String[] values = objectString.split("[{,}]");
        for (int i = 1; i < values.length; i++) {
            String currentStatement = values[i].trim();
            if (currentStatement.contains("=")) {
                String[] statement = currentStatement.split("=");
                String key = statement[0].trim();
                String value = statement[1].trim();
                result.put(key, value);
            }
        }
        return result;
    }

    public static int convertLanNum(String lan) {
        int result = 0;
        lan = Utils.onlyKeepNums(lan);
        if (lan.contains("负")) {
            lan = lan.replaceAll("负", "");
            result = 0 - Integer.valueOf(lan);
        } else {
            lan = lan.replaceAll("正", "");
            result = Integer.valueOf(lan);
        }
        return result;
    }
}
