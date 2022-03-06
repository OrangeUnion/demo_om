package com.example.demo.data;

import com.example.demo.data.tagState.LoseStatus;
import com.example.demo.data.tagState.WinStatus;

public class IntStatus {
    private String state;
    private WinStatus winTag;
    private LoseStatus loseTag;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public WinStatus getWinTag() {
        return winTag;
    }

    public void setWinTag(WinStatus winTag) {
        this.winTag = winTag;
    }

    public LoseStatus getLoseTag() {
        return loseTag;
    }

    public void setLoseTag(LoseStatus loseTag) {
        this.loseTag = loseTag;
    }
}
