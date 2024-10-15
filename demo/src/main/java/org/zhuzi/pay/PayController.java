package org.zhuzi.pay;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/pay")
@RequiredArgsConstructor
@RestController
@Slf4j
public class PayController {

    private final PayService service;

    @GetMapping("/face2face")
    public String face2face() {
        log.info("当面付接口: 需要用浏览器访问该接口");
        return service.face2face();
    }

}
