package com.xxx.blue;

import org.junit.Test;

import utility.ChineseToPinyin;
import utility.QueryLocation;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void yinpin() throws Exception {
        assertEquals("shanghai", ChineseToPinyin.getPingYin("shanghai"));
        assertEquals("shanghai", ChineseToPinyin.getPingYin("Shanghai"));
        assertEquals("shanghai", ChineseToPinyin.getPingYin("上海"));
        assertEquals("2shanghai", ChineseToPinyin.getPingYin("2上海"));
        assertEquals("shang4abhai", ChineseToPinyin.getPingYin("上4ab海"));
    }

    @Test
    public void location() throws Exception {
        assertNotEquals(null, QueryLocation.query("上海"));
    }
}