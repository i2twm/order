package com.i2twm.order.repository;

import com.i2twm.order.OrderApplication;
import com.i2twm.order.OrderApplicationTests;
import com.i2twm.order.dataobject.OrderMaster;
import com.i2twm.order.enums.OrderStatusEnum;
import com.i2twm.order.enums.PayStatusEnum;
import org.hibernate.criterion.Order;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@Component
public class OrderMasterRepositoryTest extends OrderApplicationTests {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Test
    public void save(){

        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123456");
        orderMaster.setBuyerName("张三");
        orderMaster.setBuyerAddress("曙光新城2105");
        orderMaster.setBuyerOpenid("112112");
        orderMaster.setBuyerPhone("15745658546");
        orderMaster.setOrderAmount(new BigDecimal(2.5));
        orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderMaster.setPayStatus(PayStatusEnum.SUCCESS.getCode());

        OrderMaster result = orderMasterRepository.save(orderMaster);
        Assert.assertTrue(result!=null);

    }

}