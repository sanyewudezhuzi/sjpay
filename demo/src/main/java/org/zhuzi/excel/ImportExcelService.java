package org.zhuzi.excel;

public class ImportExcelService extends EasyExcelListener<SysStaff, SysStaffDao> {

    public ImportExcelService(SysStaffDao dao) {
        super(dao);
    }

    @Override
    public boolean checkData() throws RuntimeException {
        return true;
    }

    @Override
    public void saveData() {
        super.getDao().batchSave(super.getCacheBuffer());
    }

}
