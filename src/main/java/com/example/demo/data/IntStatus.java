package com.example.demo.data;

import com.example.demo.data.tagState.LoseStatus;
import com.example.demo.data.tagState.WinStatus;

public class IntStatus {
    private String state;
    private String loseState;
    private WinStatus winTag;
    private LoseStatus loseTag;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLoseState() {
        return loseState;
    }

    public void setLoseState(String loseState) {
        this.loseState = loseState;
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
