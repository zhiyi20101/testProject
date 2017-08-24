package com.scott.testproject.teststring;

import org.junit.Test;

/**
 * Created by zouzhiyi on 31/07/17.
 */

public class TestString {
    private int areaCode;
    private String prefix;
    private String lineNumber;

    @Test
    public void testString() {
        String s = "abdc";
        System.out.println("s=");

        System.out.println(24 >> 3);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestString that = (TestString) o;

        if (areaCode != that.areaCode) return false;
        if (prefix != null ? !prefix.equals(that.prefix) : that.prefix != null) return false;
        return lineNumber != null ? lineNumber.equals(that.lineNumber) : that.lineNumber == null;

    }

    @Override
    public int hashCode() {
        int result = areaCode;
        result = 31 * result + (prefix != null ? prefix.hashCode() : 0);
        result = 31 * result + (lineNumber != null ? lineNumber.hashCode() : 0);
        return result;
    }
}
