package com.shop.test;


import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by 26685 on 2017/7/1.
 */
public class TestMain {
    @Test
    public void testApplication(){
        new ClassPathXmlApplicationContext("classpath:beans.xml");
    }
}
