package com.bingo.ssh.shop.impl.buyer; 

import com.bingo.ssh.shop.bean.Page;
import com.bingo.ssh.shop.bean.buyer.Buyer;
import com.bingo.ssh.shop.service.buyer.BuyerService;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/** 
* BuyerServiceImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>ÆßÔÂ 4, 2017</pre> 
* @version 1.0 
*/ 
public class BuyerServiceImplTest {
    static BuyerService buyerService;
@Before
public void before() throws Exception {
    ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
    buyerService =(BuyerService) context.getBean("buyerServiceImpl");
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: add(Buyer buyer) 
* 
*/ 
@Test
public void testAdd() throws Exception {
    for(int i=0;i<10;i++){
        Buyer buyer=new Buyer("kaka1"+i,"123456","email@qq.com");
        buyerService.add(buyer);
    }
}
    @Test
    public void testPagefind() throws Exception {

        Page<Buyer> buyerPage = buyerService.find(0,5,"where username like ?1",new Object[]{"kaka%"});
        System.out.println(buyerPage.getResult().size());
    }

    @Test
    public void testGetCount() throws Exception {

        System.out.println(buyerService.getCount());
    }
    /**
* 
* Method: delete(String username) 
* 
*/ 
@Test
public void testDelete() throws Exception {
    buyerService.delete("kaka19");
} 

/** 
* 
* Method: update(Buyer buyer) 
* 
*/ 
@Test
public void testUpdate() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: findOne(String username) 
* 
*/ 
@Test
public void testFindOne() throws Exception { 
//TODO: Test goes here... 
} 


} 
