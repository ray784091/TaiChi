package com.fbm.escape.busmessage;

public class EventMsg {

    public static final int EVENT_CODE_EARTHQUAKE=0X01;

    private int eventCode;

    public int getEventCode() {
        return eventCode;
    }

    public void setEventCode(int eventCode) {
        this.eventCode = eventCode;
    }
}
