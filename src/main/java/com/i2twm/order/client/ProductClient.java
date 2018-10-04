package com.i2twm.order.client;

import com.i2twm.order.dataobject.ProductInfo;
import com.i2twm.order.dto.CartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@FeignClient(name = "product")
public interface ProductClient {
    @GetMapping("/msg")
    String Pmsg();

    @PostMapping("/product/listfororder")
    List<ProductInfo> listfororder(@RequestBody List<String> productIdList);

    @PostMapping("/product/descStock")
    void descStock(@RequestBody List<CartDTO> cartDTOList);
}
