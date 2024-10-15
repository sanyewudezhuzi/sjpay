package org.zhuzi.pay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "alipay")
@Data
@PropertySource("classpath:application.yml")
public class AlipayResource {

    private String appId;
    private String pid;
    private String merchantPrivateKey;
    private String alipayPublicKey;
    private String contentKey;
    private String notifyUrl;
    private String returnUrl;
    private String gatewayUrl;

}
