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
    public int numberOfPairs(int[] nums1, int[] nums2, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums1) {
            if (num % k != 0) {
                continue;
            }
            num /= k;
            for (int i = 1; i * i <= num; i++) {
                if (num % i == 0) {
                    map.merge(i, 1, Integer::sum);
                    if (i * i < num) {
                        map.merge(num / i, 1, Integer::sum);
                    }
                }
            }
        }
        int res = 0;
        for (int num : nums2) {
            res += map.getOrDefault(num, 0);
        }
        return res;
    }
}