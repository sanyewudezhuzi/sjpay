package org.zhuzi.excel;

import com.alibaba.excel.EasyExcel;
import com.sun.deploy.net.HttpResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
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
    public String importExcel(@RequestParam MultipartFile file) {
        if (file == null) {
            return "error";
        }
        try {
            service.importExcel(file);
        } catch (Exception e) {
            log.error("error: " + e.getMessage());
            return e.getMessage();
        }
        return "success";
    }

    @GetMapping
    public void exportExcel(HttpServletResponse response) throws IOException {
        List<SysStaff> data = service.exportExcel();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = "data";
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), SysStaff.class).sheet().doWrite(data);
    }

}
