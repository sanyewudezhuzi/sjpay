package org.zhuzi.java;

import org.junit.jupiter.api.Test;

import java.util.*;

public class DemoTest {

    @Test
    public void test1() {
        System.out.println("hello java");
        Solution solution = new Solution();
    }

}

class Solution {
    public double minimumAverage(int[] nums) {
        Arrays.sort(nums);
        double res = Double.MAX_VALUE;
        for (int i = 0; i < nums.length / 2; i++) {
            res = Math.min((nums[i] + nums[nums.length - 1 - i]) / 2.0, res);
        }
        return res;
    }
}