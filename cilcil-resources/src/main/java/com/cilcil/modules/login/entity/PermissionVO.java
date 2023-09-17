package com.cilcil.modules.login.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 郑为中
 * CSDN: Designer 小郑
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "PermissionVO",description = "菜单VO类")
public class PermissionVO {

    @Schema(name = "",description = "页面路径")
    private String path;

    @Schema(name = "title",description = "菜单标题")
    private String title;
}
