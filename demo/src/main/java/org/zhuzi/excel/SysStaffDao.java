package org.zhuzi.excel;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysStaffDao {

    int batchSave(List<SysStaff> list);

}
