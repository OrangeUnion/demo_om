package com.example.demo;

import com.example.demo.clients.BZLMClient;
import com.example.demo.clients.OMClient;
import com.example.demo.utils.WinOrLose;
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
        //判断空值
        if (StringUtils.isEmpty(tagOur) || StringUtils.isEmpty(tagYou)) {
            model.addAttribute("msg", "标签为空~");
            model.addAttribute("color", "color: orange");
            model.addAttribute("size", "display-3");
            return "index";
        }
        //小写字母转大写
        tagOur = tagOur.toUpperCase();
        tagYou = tagYou.toUpperCase();
        //0替换成O
        tagOur = tagOur.replaceAll("O", "0");
        tagYou = tagYou.replaceAll("O", "0");
        //删除多余#
        tagOur = "#" + tagOur.replaceAll("#", "");
        tagYou = "#" + tagYou.replaceAll("#", "");

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

        //比前三位标签大小
        int statWin = WinOrLose.win(tagOur,tagYou);

        //计算标签比对结果
        String winTag = "";
        String loseTag = "";
        if (statWin == 1) {
            winTag = tagOur;
            loseTag = tagYou;
            model.addAttribute("msg", "赢");
            model.addAttribute("color", "color: green");
        } else if (statWin == 2) {
            winTag = tagYou;
            loseTag = tagOur;
            model.addAttribute("msg", "输");
            model.addAttribute("color", "color: red");
        } else {
            model.addAttribute("msg", "无法判断！");
            model.addAttribute("color", "color: black");
        }

        //判断O盟
        String omWin = OMClient.getOmStatus(winTag);
        String omColWin = OMClient.getStatCol(winTag);

        String omLose = OMClient.getOmStatus(loseTag);
        String omColLose = OMClient.getStatCol(loseTag);

        //判断黑白
        String bzWin = null;
        String bzLose = null;
        String bzlmDataOurString = BZLMClient.getBZLMAccountInfo(winTag);
        JSONObject bzlmOurResponse = new JSONObject(bzlmDataOurString);
        if (bzlmOurResponse.getBoolean("exist") && !bzlmOurResponse.getBoolean("lock")) {
            bzWin = "正常黑白部落";
            model.addAttribute("bzlmOur", "正常黑白部落");
        }

        String bzlmDataYouString = BZLMClient.getBZLMAccountInfo(loseTag);
        JSONObject bzlmYouResponse = new JSONObject(bzlmDataYouString);
        if (bzlmYouResponse.getBoolean("exist") && !bzlmYouResponse.getBoolean("lock")) {
            bzLose = "正常黑白部落";
            model.addAttribute("bzlmYou", "正常黑白部落");
        }

        //结果输出
        model.addAttribute("win", winTag);
        model.addAttribute("lose", loseTag);

        model.addAttribute("winOm", omWin);
        model.addAttribute("loseOm", omLose);
        model.addAttribute("winColOm",omColWin);
        model.addAttribute("loseColOm",omColLose);

        model.addAttribute("winBz", bzWin);
        model.addAttribute("loseBz", bzLose);

        model.addAttribute("our", tagOur);
        model.addAttribute("you", tagYou);
        model.addAttribute("size", "display-3");
        return "index";
    }
}
