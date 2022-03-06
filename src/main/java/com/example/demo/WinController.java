package com.example.demo;

import com.example.demo.clients.BZLMClient;
import com.example.demo.clients.OMClient;
import com.example.demo.dataUtils.DataClient;
import com.example.demo.utils.WinOrLose;
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
        //格式化标签
        tagOur = WinOrLose.ozTag(tagOur);
        tagYou = WinOrLose.ozTag(tagYou);

        if (tagOur.equals(tagYou)) {
            model.addAttribute("msg", "同标签？");
            model.addAttribute("color", "color: blue");
            model.addAttribute("size", "display-3");
            return "index";
        }

        //判断标签大于三位
        if (tagOur.toCharArray().length < 4 || tagYou.toCharArray().length < 4) {
            model.addAttribute("msg", "标签过短？");
            model.addAttribute("color", "color: darkmagenta");
            model.addAttribute("size", "display-3");
            return "index";
        }

        //比前三位标签大小
        int statWin = WinOrLose.win(tagOur, tagYou);

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

        //O盟统计
        boolean state = "null".equals(OMClient.getOmName(tagOur).get(0));
        boolean dataStatus = DataClient.searchData(tagOur) < 1;
        if (dataStatus && !state) {
            DataClient.insertData(tagOur, OMClient.getOmName(tagOur).get(1), OMClient.getOmName(tagOur).get(2));
        }

        //失败判断
        String youWin = null;
        String youLose = null;
        String youCol = null;
        String wol = null;
        if (WinOrLose.loseState(tagYou)) {
            if (tagYou.equals(winTag)) {
                youWin = "外部";
            } else {
                youLose = "外部";
            }
            youCol = "badge bg-warning";
            wol = "匹配失败";
        }

        //判断O盟
        String omWin = OMClient.getOmState(winTag).get(0);
        String omColWin = OMClient.getOmState(winTag).get(1);

        String omLose = OMClient.getOmState(loseTag).get(0);
        String omColLose = OMClient.getOmState(loseTag).get(1);

        //判断黑白
        String bzWin = BZLMClient.getBzStatus(winTag);
        String bzColWin = BZLMClient.getStatCol(winTag);

        String bzLose = BZLMClient.getBzStatus(loseTag);
        String bzColLose = BZLMClient.getStatCol(loseTag);

        //结果输出
        model.addAttribute("win", winTag);
        model.addAttribute("lose", loseTag);

        model.addAttribute("winOm", omWin);
        model.addAttribute("loseOm", omLose);
        model.addAttribute("winColOm", omColWin);
        model.addAttribute("loseColOm", omColLose);

        model.addAttribute("winBz", bzWin);
        model.addAttribute("loseBz", bzLose);
        model.addAttribute("winColBz", bzColWin);
        model.addAttribute("loseColBz", bzColLose);

        model.addAttribute("youStatWin", youWin);
        model.addAttribute("youStatLose", youLose);
        model.addAttribute("youCol", youCol);
        model.addAttribute("wol", wol);

        model.addAttribute("our", tagOur);
        model.addAttribute("you", tagYou);
        model.addAttribute("size", "display-3");
        return "index";
    }
}
