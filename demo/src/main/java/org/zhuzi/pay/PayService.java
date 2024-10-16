package org.zhuzi.pay;

import com.alibaba.fastjson2.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class PayService {

    private final AlipayResource alipayResource;

    private final AlipayClient alipayClient;

    public String face2face() {
        // 获取订单信息
        JSONObject json = new JSONObject();
        json.put("id", "114514");
        json.put("goods", "Lumix S5");
        json.put("money", 7999);

        // 封装请求
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setReturnUrl(alipayResource.getReturnUrl());
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(json.getString("id"));
        model.setTotalAmount(json.getString("money"));
        model.setSubject(json.getString("goods"));
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        request.setBizModel(model);

        // 发起请求, 调用支付宝
        AlipayTradePagePayResponse response = null;
        try {
            response = alipayClient.pageExecute(request);
            if (response.isSuccess()) {
                log.info("success: " + response.getBody());
                return response.getBody();
            } else {
                log.info("error: " + response.getMsg());
            }
        } catch (AlipayApiException e) {
            log.error("Alipay exception: " + e.getMessage());
        }
        return null;
    }

}
