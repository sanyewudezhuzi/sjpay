package org.zhuzi.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
        EasyExcel.read(file.getInputStream(), SysStaff.class, new ImportExcelService(100000, dao, transactionTemplate)).sheet().doRead();
    }

    public List<SysStaff> exportExcel() {
        return dao.selectList(new LambdaQueryWrapper<SysStaff>().gt(SysStaff::getId, 0));
    }

}
