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
        if (!checkData(data)) {
            return;
        }
        cacheBuffer.add(data);
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
        } finally {
            cacheBuffer.clear();
            end();
        }
    }

    /**
     * 校验数据
     *
     * @param data Excel 的单行数据, 导出转为 data
     * @return 校验通过则返回 true, 并将 data 添加到缓冲队列 cacheBuffer 中, 如果校验不通过则返回 false
     * @throws RuntimeException 允许抛出异常
     */
    protected boolean checkData(T data) throws RuntimeException {
        return true;
    }

    /**
     * 保存数据
     * <p>
     * 每当缓冲队列到达阈值或 Excel 数据解析完成时会调用此方法
     *
     * @throws InterruptedException 允许抛出异常
     */
    protected abstract void saveData() throws InterruptedException;

    /**
     * 结束方法
     * <p>
     * 当 Excel 数据解析完成并调用完 saveData() 方法后会调用此方法.
     * <p>
     * 一般用于释放资源.
     */
    protected void end() {
    }

}
