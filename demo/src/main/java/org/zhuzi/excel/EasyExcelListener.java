package org.zhuzi.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class EasyExcelListener<T, V> implements ReadListener<T> {

    // 默认缓冲队列大小
    private static final int DEFAULT_BATCH_COUNT = 1000;
    // 缓冲队列大小
    private final Integer batchCount;
    // 缓冲队列
    protected List<T> cacheBuffer;
    // 数据交互层
    protected final V dao;

    public EasyExcelListener(V dao) {
        this(DEFAULT_BATCH_COUNT, dao);
    }

    public EasyExcelListener(Integer batchCount, V dao) {
        this.batchCount = batchCount;
        this.dao = dao;
        cacheBuffer = new ArrayList<>(this.batchCount);
    }

    @Override
    public void invoke(T data, AnalysisContext context) throws RuntimeException {
        log.info("invoke: data = " + data + ", context = " + context);
        if (checkData()) {
            cacheBuffer.add(data);
        }
        if (cacheBuffer.size() >= batchCount) {
            saveData();
            cacheBuffer = new ArrayList<>(batchCount);
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
