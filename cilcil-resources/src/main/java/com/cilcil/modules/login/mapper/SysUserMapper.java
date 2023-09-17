package com.cilcil.modules.login.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cilcil.modules.login.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Xy
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2023-08-31 00:12:13
* @Entity com.cilcilresources.modules.generator.domain.SysUser
*/
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}




