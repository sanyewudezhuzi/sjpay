package org.zhuzi.excel;

import com.alibaba.excel.EasyExcel;
import com.sun.deploy.net.HttpResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequestMapping("/excel")
@RequiredArgsConstructor
@RestController
@Slf4j
public class ExcelController {

    private final ExcelService service;

    @PostMapping
    public String importExcel(MultipartFile file) {
        try {
            service.importExcel(file);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "success";
    }

    @GetMapping
    public void exportExcel(HttpServletResponse response) throws IOException {
        List<SysStaff> data = service.exportExcel();
        EasyExcel.write(response.getOutputStream(), SysStaff.class).sheet().doWrite(data);
    }


}
