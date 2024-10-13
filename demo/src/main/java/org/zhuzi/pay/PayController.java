package org.zhuzi.pay;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequestMapping("/pay")
@RequiredArgsConstructor
@RestController
@Slf4j
public class PayController {

    @GetMapping("/qrcode")
    public void qrcode() {
        QrConfig config = new QrConfig();
        config.setHeight(200);
        config.setWidth(200);
        config.setMargin(3);
        config.setImg("F:/Art/Photo/zhuzi.jpg");
        QrCodeUtil.generate("https://space.bilibili.com/479724929?spm_id_from=333.1007.0.0", config, FileUtil.file("F:/qrcode.jpg"));
    }

}
