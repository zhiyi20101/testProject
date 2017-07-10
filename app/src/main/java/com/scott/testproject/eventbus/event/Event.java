package com.scott.testproject.eventbus.event;

/**
 * Created by zouzhiyi on 10/07/17.
 */

public class Event {
    protected int mId;
    protected String mMessage;

    public Event(int id, String message) {
        mId = id;
        mMessage = message;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }
}
