package org.zhuzi.excel;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ImportExcelService extends EasyExcelListener<SysStaff> {

    // 数据交互层
    private final ExcelDao dao;
    // 事务
    private final TransactionTemplate transactionTemplate;
    // 线程池
    private final ThreadPoolExecutor executor;
    private static final int CORE_POOL_SIZE = 3;
    private static final int MAXIMUM_POOL_SIZE = 5;
    private static final int KEEP_ALIVE_TIME = 60;
    private static final int QUEUE_SIZE = 1000;

    // 导入功能最大时间(单位: 秒)
    private static final long MAX_TIME = 60 * 2;

    public ImportExcelService(ExcelDao dao, TransactionTemplate transactionTemplate) {
        super();
        this.dao = dao;
        this.transactionTemplate = transactionTemplate;
        executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.MINUTES, new LinkedBlockingDeque<>(QUEUE_SIZE)
        );
    }

    @Override
    public boolean checkData(SysStaff data) throws RuntimeException {
        return !StringUtils.isAnyBlank(data.getUsername(), data.getName(), data.getPassword());
    }

    @Override
    public void saveData() throws InterruptedException {
        ArrayList<List<SysStaff>> lists = new ArrayList<>(ListUtil.split(super.cacheBuffer, 1000));
        // List<List<SysStaff>> lists = ListUtil.split(super.cacheBuffer, 1000);
        CountDownLatch cdt = new CountDownLatch(lists.size());
        for (List<SysStaff> list : lists) {
            try {
                executor.execute(() -> runFunc(list));
            } catch (Exception e) {
                log.error("import excel error: " + e.getMessage());
            } finally {
                cdt.countDown();
            }
        }
        if (!cdt.await(MAX_TIME, TimeUnit.SECONDS)) {
            log.error("import excel error: timeout. please check this flow...");
        }
        lists.clear();
    }

    private void runFunc(List<SysStaff> list) {
        transactionTemplate.executeWithoutResult(status -> {
            for (SysStaff item : list) {
                item.setPassword(DigestUtils.md5DigestAsHex(item.getPassword().getBytes()));
                item.setStatus(1);
                item.setAuth(1);
                item.setCreateTime(LocalDateTime.now());
                item.setUpdateTime(LocalDateTime.now());
                try {
                    dao.insert(item);
                } catch (DuplicateKeyException e) {
                    log.debug("update: username[" + item.getUsername() + "] has been insert.");
                    dao.update(item, new LambdaUpdateWrapper<SysStaff>().eq(SysStaff::getUsername, item.getUsername()));
                } catch (Exception e) {
                    log.error("unknow error: " + e.getMessage());
                    throw new RuntimeException(e.getMessage());
                }
            }
        });
    }

}
