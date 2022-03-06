package com.example.demo.utils;

public class WinOrLose {
    public static String ozTag(String tag) {
        //小写字母转大写 0替换成O 删除多余#
        tag = "#" + tag.toUpperCase().replaceAll("O", "0").replaceAll("#", "");
        //#符号判断
        if (tag.charAt(0) != '#') tag = "#" + tag;
        return tag;
    }
    public static String urlTag(String tag) {
        //小写字母转大写 0替换成O 删除多余#
        tag = "#" + tag.toUpperCase().replaceAll("O", "0").replaceAll("#", "");
        //#符号判断
        return tag;
    }
    public static int win(String tagOur, String tagYou) {
        int th = tagOur.charAt(3) + tagYou.charAt(3);

        //比前三位标签大小
        int sum = 0;
        for (int i = 1; i <= 3; i++) {
            if (tagOur.charAt(i) > tagYou.charAt(i)) sum++;
            if (tagOur.charAt(i) < tagYou.charAt(i)) sum--;
        }

        int tagLong = Math.max(tagOur.length(), tagYou.length());
        int tagMin = Math.min(tagOur.length(), tagYou.length());

        int fightBegin = 0;
        if (sum > 0) fightBegin = 1;
        else if (sum < 0) fightBegin = -1;
        else {
            //前三位相同从第四位开始推'
            for (int i = 4; i < tagLong; i++) {
                int currOur = (i >= tagOur.length()) ? -1 : (int) tagOur.charAt(i);
                int currYou = (i >= tagYou.length()) ? -1 : (int) tagYou.charAt(i);
                if (currOur > currYou) {
                    fightBegin = 1;
                    break;
                }
                if (currOur < currYou) {
                    fightBegin = -1;
                    break;
                }
            }
        }

        //计算标签比对结果
        int statWin;
        if ((fightBegin == 1 && th % 2 == 0) || (fightBegin == -1 && th % 2 != 0)) {
            statWin = 1;
        } else if ((fightBegin == -1 && th % 2 == 0) || (fightBegin == 1 && th % 2 != 0)) {
            statWin = 2;
        } else {
            statWin = 3;
        }
        return statWin;
    }
}
