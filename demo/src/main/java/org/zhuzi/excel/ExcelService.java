package org.zhuzi.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
@Slf4j
public class ExcelService {

    private final ExcelDao dao;

    private final TransactionTemplate transactionTemplate;

    public void importExcel(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), SysStaff.class, new ImportExcelService(dao, transactionTemplate)).sheet().doRead();
    }

    public List<SysStaff> exportExcel() {
        r.setSeed(System.currentTimeMillis());
        List<SysStaff> res = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            SysStaff staff = new SysStaff();
            staff.setUsername(randomStr(11));
            staff.setPassword("123456");
            staff.setName(randomStr(4));
            res.add(staff);
        }
        return res;
    }


    Random r = new Random();

    private String randomStr(int size) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < size; i++) {
            str.append((char) ('a' + r.nextInt(26)));
        }
        return str.toString();
    }

}
