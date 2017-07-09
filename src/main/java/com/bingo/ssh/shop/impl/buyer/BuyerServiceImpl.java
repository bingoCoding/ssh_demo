package com.bingo.ssh.shop.impl.buyer;

import com.bingo.ssh.shop.base.DaoImpl;
import com.bingo.ssh.shop.bean.buyer.Buyer;
import com.bingo.ssh.shop.service.buyer.BuyerService;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

/**
 * Created by 26685 on 2017/7/4.
 */
@Service
public class BuyerServiceImpl extends DaoImpl<Buyer,String> implements BuyerService {


    /**
     *  
     *     * 构造函数 
     *     * @param domainClass 
     *     * @param em 
     *   
     *
     * @param entityInformation
     * @param em
     */
    public BuyerServiceImpl(JpaEntityInformation<Buyer, String> entityInformation, EntityManager em) {
        super(entityInformation, em);
    }
}
