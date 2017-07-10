package com.scott.testproject.eventbus;

import com.scott.testproject.eventbus.event.Event;

/**
 * Created by zouzhiyi on 10/07/17.
 */

public class TestEvent extends Event {
    public TestEvent(int id, String message) {
        super(id, message);
    }
}
