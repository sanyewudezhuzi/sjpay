package org.zhuzi.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@Slf4j
public abstract class EasyExcelListener<T, V> implements ReadListener<T> {

    private static final int BATCH_COUNT = 100;
    private List<T> cacheBuffer = new ArrayList<>(BATCH_COUNT);

    private final V dao;

    public EasyExcelListener(V dao) {
        this.dao = dao;
    }

    @Override
    public void invoke(T data, AnalysisContext context) throws RuntimeException {
        log.info("invoke: data = " + data + ", context = " + context);
        if (checkData()) {
            cacheBuffer.add(data);
        }
        if (cacheBuffer.size() >= BATCH_COUNT) {
            saveData();
            cacheBuffer = new ArrayList<>(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        cacheBuffer.clear();
    }

    public boolean checkData() throws RuntimeException {
        return true;
    }

    public abstract void saveData();

}
