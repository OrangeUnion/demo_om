package com.example.demo;

import com.example.demo.clients.BZLMClient;
import com.example.demo.clients.OMClient;
import com.example.demo.data.IntStatus;
import com.example.demo.data.tagState.LoseStatus;
import com.example.demo.data.tagState.WinStatus;
import com.example.demo.utils.WinOrLose;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

@RestController
public class IntController {
    @GetMapping("getWin")
    public IntStatus win(@RequestParam("our") String our,
                         @RequestParam("you") String you) {
        String state;
        WinStatus winStatus = new WinStatus();
        LoseStatus loseStatus = new LoseStatus();
        IntStatus intStatus = new IntStatus();
        //判断空值
        if (StringUtils.isEmpty(our) || StringUtils.isEmpty(you)) {
            intStatus.setState("标签空");
            return intStatus;
        }
        //格式化标签
        our = WinOrLose.urlTag(our);
        you = WinOrLose.urlTag(you);

        if (our.equals(you)) {
            intStatus.setState("同标签");
            return intStatus;
        }

        //判断标签大于三位
        if (our.toCharArray().length < 4 || you.toCharArray().length < 4) {
            intStatus.setState("标签过短");
            return intStatus;
        }

        //判断O盟
        String omOur = OMClient.getOmState(our).get(0);
        String omYou = OMClient.getOmState(you).get(0);

        //判断黑白
        String bzOur = BZLMClient.getBzStatus(you);
        String bzYou = BZLMClient.getBzStatus(you);

        //失败判断
        String wol;
        if (WinOrLose.loseState(you)) {
            wol = "匹配失败";
        } else {
            wol = "匹配成功";
        }

        int statWin = WinOrLose.win(our, you);
        boolean statNewWin = WinOrLose.newWin(our, you);
        //计算标签比对结果
        if (statWin == 1) {
            state = "win";
            intStatus.setState(state);

            winStatus.setTag(our);
            winStatus.setOmState(omOur);
            winStatus.setBzState(bzOur);
            loseStatus.setTag(you);
            loseStatus.setOmState(omYou);
            loseStatus.setBzState(bzYou);
        } else if (statWin == 2) {
            state = "lose";
            intStatus.setState(state);

            winStatus.setTag(you);
            winStatus.setOmState(omYou);
            winStatus.setBzState(bzYou);
            loseStatus.setTag(our);
            loseStatus.setOmState(omOur);
            loseStatus.setBzState(bzOur);
        } else {
            state = "无法判断";
            intStatus.setState(state);
        }

        intStatus.setLoseState(wol);
        intStatus.setWinTag(winStatus);
        intStatus.setLoseTag(loseStatus);

        return intStatus;
    }
}
