package com.cilcilcommon.modules.sysuser.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cilcilcommon.modules.sysuser.service.SysUserService;
import com.cilcilresources.modules.sysuser.entity.SysUser;
import com.cilcilresources.modules.sysuser.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

/**
 * @Author SlyAimer
 * @Date 2023/8/31 0:47
 * @Version 1.0
 */
@Service
public class SysUserServiceImpl  extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService{
}
