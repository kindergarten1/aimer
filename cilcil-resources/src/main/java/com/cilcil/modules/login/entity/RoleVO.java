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
@Schema(name ="RoleDTO" ,description = "角色VO类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVO {

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色ID")
    private String id;

    @Schema(description = "角色备注")
    private String description;
}
