package com.scott.testproject.nio;

import java.io.IOException;
import java.nio.channels.Selector;

/**
 * Created by zouzhiyi on 18/04/17.
 */

public class SelectorTest {
    public static void test() throws IOException {
        Selector selector = Selector.open();
    }
}
