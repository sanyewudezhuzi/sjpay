package org.zhuzi.excel;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ExcelDao extends BaseMapper<SysStaff> {

    int batchSave(@Param("list") List<SysStaff> list);

}
