package com.cilcil.base;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author SlyAimer
 * @Date 2023/8/26 20:41
 * @Version 1.0
 */
@Schema(description = "通用实体类")
@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @TableId
    @Schema(description = "主键id")
    private Integer id ;

    @CreatedBy
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建人id-系统登登录人")
    private Integer createdId;

    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建人名称")
    private String createdName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT,updateStrategy = FieldStrategy.NOT_NULL)
    @Schema(description = "创建人名称")
    private LocalDateTime createdTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新人-系统登登录人")
    private Integer updateId;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新人名称")
    private String updateName;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE,updateStrategy = FieldStrategy.NOT_NULL)
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "逻辑删除")
    private Integer isDeleted;

}
