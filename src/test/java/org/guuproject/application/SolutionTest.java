package org.guuproject.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



class SolutionTest {

    @Test
    public void testOne(){
        Solution solution1 = new Solution();
        Assertions.assertEquals(5,solution1.strStr("mississippi","issip"));

    }
}