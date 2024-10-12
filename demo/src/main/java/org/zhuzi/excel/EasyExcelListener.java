package org.zhuzi.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class EasyExcelListener<T> implements ReadListener<T> {

    // 默认缓冲队列大小
    private static final int DEFAULT_BATCH_COUNT = 10000;
    // 缓冲队列大小
    private final Integer batchCount;
    // 缓冲队列
    protected List<T> cacheBuffer;

    public EasyExcelListener() {
        this(DEFAULT_BATCH_COUNT);
    }

    public EasyExcelListener(Integer batchCount) {
        this.batchCount = batchCount;
        cacheBuffer = new ArrayList<>(this.batchCount);
    }

    @Override
    public void invoke(T data, AnalysisContext context) {
        if (checkData(data)) {
            cacheBuffer.add(data);
        }
        if (cacheBuffer.size() >= batchCount) {
            try {
                saveData();
            } catch (Exception e) {
                log.error("EasyExcelListener invoke error: " + e.getMessage());
                throw new RuntimeException(e);
            }
            cacheBuffer = new ArrayList<>(batchCount);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        try {
            saveData();
        } catch (Exception e) {
            log.error("EasyExcelListener doAfterAllAnalysed error: " + e.getMessage());
            throw new RuntimeException(e);
        }
        cacheBuffer.clear();
    }

    protected boolean checkData(T data) throws RuntimeException {
        return true;
    }

    protected abstract void saveData() throws InterruptedException;

}
