package org.zhuzi.excel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@TableName("sys_staff")
@ToString
public class SysStaff implements Serializable {

    @ExcelIgnore
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ExcelProperty("用户名")
    private String username;

    @ExcelProperty("密码")
    private String password;

    @ExcelProperty("昵称")
    private String name;

    @ExcelIgnore
    private Integer auth;

    @ExcelIgnore
    private Integer status;

    @ExcelIgnore
    private LocalDateTime createTime;

    @ExcelIgnore
    private LocalDateTime updateTime;

    @ExcelIgnore
    @TableLogic(value = "0", delval = "1")
    private Boolean deleted;

}
