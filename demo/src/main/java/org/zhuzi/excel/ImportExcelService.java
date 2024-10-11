package org.zhuzi.excel;

public class ImportExcelService extends EasyExcelListener<SysStaff, ExcelDao> {

    public ImportExcelService(ExcelDao dao) {
        super(dao);
    }

    @Override
    public boolean checkData() throws RuntimeException {
        return true;
    }

    @Override
    public void saveData() {
        // super.dao.batchSave(super.cacheBuffer);
    }

}
