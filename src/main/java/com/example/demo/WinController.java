package com.example.demo;

import com.example.demo.clients.BZLMClient;
import com.example.demo.utils.PrettyPrint;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

@Controller
public class WinController {
    @RequestMapping("/index")
    public String getWin() {
        return "index";
    }


    @RequestMapping("/win")
    public String win(@RequestParam("tagOur") String tagOur,
                      @RequestParam("tagYou") String tagYou,
                      Model model) {
        int clanFight;
        //判断空值
        if (StringUtils.isEmpty(tagOur) || StringUtils.isEmpty(tagYou)) {
            model.addAttribute("msg", "标签为空~");
            model.addAttribute("color", "color: orange");
            model.addAttribute("size", "display-3");
            return "index";
        }
        //显示的名字
        String displayOur = "";
        String displayYou = "";
        //小写字母转大写
        tagOur = tagOur.toUpperCase();
        tagYou = tagYou.toUpperCase();
        //0替换成O
        tagOur = tagOur.replaceAll("0", "O");
        tagYou = tagYou.replaceAll("0", "O");
        //删除多余#
        tagOur = "#" + tagOur.replaceAll("#", "");
        tagYou = "#" + tagYou.replaceAll("#", "");

        //判断O盟
        String omDataOur = Api.getOmApi(tagOur.replaceAll("#",""));
        if (omDataOur.contains("\"state\":\"正常\"")) {
            model.addAttribute("omOur","正常O盟部落");
            displayOur = tagOur;
        }
        String omDataYou = Api.getOmApi(tagYou.replaceAll("#",""));
        if (omDataYou.contains("\"state\":\"正常\"")) {
            model.addAttribute("omYou","正常O盟部落");
            displayYou = tagYou;
        }

        //判断黑白
        String bzlmDataOurString = BZLMClient.getBZLMAccountInfo(tagOur);
        JSONObject bzlmOurResponse = new JSONObject(bzlmDataOurString);
        if(bzlmOurResponse.getBoolean("exist") && !bzlmOurResponse.getBoolean("lock")){
            model.addAttribute("bzlmOur","正常黑白部落");
            displayOur = String.format("%s<%s>", bzlmOurResponse.getString("fullName"), tagOur);
        }

        String bzlmDataYouString = BZLMClient.getBZLMAccountInfo(tagYou);
        JSONObject bzlmYouResponse = new JSONObject(bzlmDataYouString);
        if(bzlmYouResponse.getBoolean("exist") && !bzlmYouResponse.getBoolean("lock")){
            model.addAttribute("bzlmYou","正常黑白部落");
            displayYou = String.format("%s<%s>", bzlmOurResponse.getString("fullName"), tagYou);
        }

        if (tagOur.equals(tagYou)) {
            model.addAttribute("msg", "同标签？");
            model.addAttribute("color", "color: blue");
            model.addAttribute("size", "display-3");
            return "index";
        }
        //#符号判断
        if (tagOur.charAt(0) != '#') {
            tagOur = "#" + tagOur;
        }
        if (tagYou.charAt(0) != '#') {
            tagYou = "#" + tagYou;
        }
        //判断标签大于三位
        if (tagOur.toCharArray().length < 4 || tagYou.toCharArray().length < 4) {
            model.addAttribute("msg", "标签过短？");
            model.addAttribute("color", "color: darkmagenta");
            model.addAttribute("size", "display-3");
            return "index";
        }
        int th = tagOur.charAt(3) + tagYou.charAt(3);
        if (th % 2 == 0) {
            clanFight = 1;
        } else {
            clanFight = 2;
        }
        //比前三位标签大小
        //比标签第一位大小
        int fightFst = 0;
        if (tagOur.charAt(1) > tagYou.charAt(1)) {
            fightFst = 1;
        } else if (tagOur.charAt(1) < tagYou.charAt(1)) {
            fightFst = -1;
        }
        //比标签第二位大小
        int fightSnd = 0;
        if (tagOur.charAt(2) > tagYou.charAt(2)) {
            fightSnd = 1;
        } else if (tagOur.charAt(2) < tagYou.charAt(2)) {
            fightSnd = -1;
        }
        //比标签第三位大小
        int fightTrd = 0;
        if (tagOur.charAt(3) > tagYou.charAt(3)) {
            fightTrd = 1;
        } else if (tagOur.charAt(3) < tagYou.charAt(3)) {
            fightTrd = -1;
        }
        //计算前三位比对结果
        int he = fightFst + fightSnd + fightTrd;
        int fightBegin = 0;
        int tagLong = Math.max(tagOur.length(), tagYou.length());
        int tagMin = Math.min(tagOur.length(), tagYou.length());
        if (he > 0) {
            fightBegin = 1;
        } else if (he < 0) {
            fightBegin = -1;
        } else if (tagMin == 4) {
            fightBegin = tagLong == tagOur.length() ? 1 : -1;
        } else {
            //前三位相同从第四位开始推
            for (int i = 4; i < tagLong - 1; i++) {
                if (tagOur.charAt(i) > tagYou.charAt(i)) {
                    fightBegin = 1;
                    break;
                } else if (tagOur.charAt(i) < tagYou.charAt(i)) {
                    fightBegin = -1;
                    break;
                } else {
                    if (tagOur.length() < tagYou.length()) {
                        fightBegin = -1;
                        break;
                    } else if (tagYou.length() < tagOur.length()) {
                        fightBegin = 1;
                        break;
                    }
                }
            }
        }
        //计算标签比对结果
        if (fightBegin == 1 && clanFight == 1) {
            model.addAttribute("msg", "比大：赢");
            model.addAttribute("color", "color: green");
            model.addAttribute("win", displayOur);
            model.addAttribute("lose",displayYou);
        } else if (fightBegin == -1 && clanFight == 1) {
            model.addAttribute("msg", "比大：输");
            model.addAttribute("color", "color: red");
            model.addAttribute("win", displayYou);
            model.addAttribute("lose",displayOur);
        } else if (fightBegin == -1 && clanFight == 2) {
            model.addAttribute("msg", "比小：赢");
            model.addAttribute("color", "color: green");
            model.addAttribute("win", displayOur);
            model.addAttribute("lose",displayYou);
        } else if (fightBegin == 1 && clanFight == 2) {
            model.addAttribute("msg", "比小：输");
            model.addAttribute("color", "color: red");
            model.addAttribute("win", displayYou);
            model.addAttribute("lose",displayOur);
        } else {
            model.addAttribute("msg", "找迈向嗨！");
            model.addAttribute("color", "color: black");
        }
        //结果输出
        model.addAttribute("our", displayOur);
        model.addAttribute("you", displayYou);
        model.addAttribute("fight", clanFight);
        model.addAttribute("size", "display-3");
        return "index";
    }
}
