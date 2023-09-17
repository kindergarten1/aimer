package com.cilcil.modules.login.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cilcil.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 郑为中
 * CSDN: Designer 小郑
 */

@Data
@Entity
@Table(name = "permission")
@EqualsAndHashCode(callSuper = true)
@Schema(name = "Permission",description = "菜单授权")
public class Permission extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(name = "parentId",description = "父节点ID")
    private String parentId;

    @Schema(name = "name",description = "菜单名称")
    private String name;

    @Schema(name = "title",description = "菜单标题")
    private String title;

    @Schema(name = "level",description = "菜单层级")
    private Integer level;

    @Schema(name = "status",description = "启用状态")
    private Integer status = 1;

    @Schema(name = "type",description = "菜单类型")
    private Integer type;

    @Schema(name = "icon",description = "PC端图标")
    private String icon;

    @Schema(name = "component",description = "前端组件名称")
    private String component;

    @Schema(name = "path",description = "路径")
    private String path;

    @Schema(name = "description",description = "备注")
    private String description;

    @Schema(name = "sort",description = "菜单排序值")
    private BigDecimal sort;

    @Transient
    @TableField(exist=false)
    @Schema(name = "expand",description = "节点展开状态")
    private Boolean expand = true;

    @Transient
    @TableField(exist=false)
    @Schema(name = "selected",description = "结点选中状态")
    private Boolean selected = false;

    @Transient
    @TableField(exist=false)
    @Schema(name = "checked",description = "结点勾选状态")
    private Boolean checked = false;

    @Transient
    @TableField(exist=false)
    @Schema(name = "children",description = "子菜单列表")
    private List<Permission> children;

    @Transient
    @TableField(exist=false)
    @Schema(name = "permissionButton",description = "菜单权限授权按钮")
    private List<String> permissionButton;
}