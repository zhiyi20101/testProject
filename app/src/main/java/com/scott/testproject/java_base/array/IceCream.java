package com.scott.testproject.java_base.array;

import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

/**
 * Created by Administrator on 2017-07-23.
 */

public class IceCream {
    static String[] flav = {
            "Chocolate", "Strawberry",
            "Vanilla Fudge Swirl", "Mint Chip",
            "Mocha Almond Fudge", "Rum Raisin",
            "Praline Cream", "Mud Pie"
    };

    static String[] flavorSet(int n) {
        n = Math.abs(n) % (flav.length + 1);
        //System.out.println("n=" + n);
        String[] results = new String[n];
        int[] picks = new int[n];
        for (int i = 0; i < picks.length; i++)
            picks[i] = -1;
        for (int i = 0; i < results.length; i++) {
            retry:
            while (true) {
                int t = (int) (Math.random() * flav.length);
                for (int j = 0; j < i; j++) {
                    if (picks[j] == t) continue retry;
                }
                picks[i] = t;
                results[i] = flav[t];
                break;
            }
        }
        return results;
    }

    @Test
    public  void main() {
        for(int i = 0; i < 20; i++) {
            System.out.println(
                    "flavorSet(" + i + ") = ");
            String[] fl = flavorSet(flav.length);
            for(int j = 0; j < fl.length; j++)
                System.out.println("\t" + fl[j]);
        }
    }

}
