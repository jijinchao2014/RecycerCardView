package com.jijc.recyclercardview;

import com.jijc.recyclercardview.bean.NewsRequest;
import com.jijc.recyclercardview.utils.DataUtil;

import org.junit.Test;

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
    public void test() throws Exception{
        NewsRequest newsList = DataUtil.getNewsList();
//        Log.i("jijinchao","+aaaaaa")
        System.out.print("newsList:"+newsList.data.list.get(0).title);
    }
}