package org.zhuzi.excel;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ExcelDao {

    int batchSave(List<SysStaff> list);

}
