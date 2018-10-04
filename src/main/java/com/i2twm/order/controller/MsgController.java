package com.i2twm.order.controller;

import com.i2twm.order.client.ProductClient;
import com.i2twm.order.config.RestTemplateConfig;
import com.i2twm.order.dataobject.ProductInfo;
import com.i2twm.order.dto.CartDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.OutputKeys;
import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
public class MsgController {

//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private ProductClient productClient;
    @GetMapping("/getMsg")
    public String getProductMsg(){
//        String response = restTemplate.getForObject("http://PRODUCT/msg", String.class);
        String response = productClient.Pmsg();
        log.info("response={}",response);
        return response;
    }
    @GetMapping("/getlistfororder")
    public String getListfororder(){
        List<ProductInfo> list = productClient.listfororder(Arrays.asList("157875196678160022"));
        log.info("response={}" ,list);
        return "获取成功";
    }
    @GetMapping("/descStock")
    public String descStock(){
        productClient.descStock(Arrays.asList(new CartDTO("157875196678160022",4)));
        return "扣除成功";
    }
}
