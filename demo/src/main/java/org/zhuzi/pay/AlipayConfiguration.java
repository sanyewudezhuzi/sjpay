package org.zhuzi.pay;

import com.alipay.api.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AlipayConfiguration {

    private final AlipayResource resource;

    @Bean
    public AlipayClient alipayClient() throws AlipayApiException {
        AlipayConfig config = new AlipayConfig();
        config.setAppId(resource.getAppId());
        config.setPrivateKey(resource.getMerchantPrivateKey());
        config.setAlipayPublicKey(resource.getAlipayPublicKey());
        config.setServerUrl(resource.getGatewayUrl());
        config.setFormat(AlipayConstants.FORMAT_JSON);
        config.setCharset(AlipayConstants.CHARSET_UTF8);
        config.setSignType(AlipayConstants.SIGN_TYPE_RSA2);
        return new DefaultAlipayClient(config);
    }

}
