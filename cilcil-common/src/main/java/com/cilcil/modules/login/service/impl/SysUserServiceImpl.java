package com.cilcil.modules.login.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cilcil.modules.login.entity.SysUser;
import com.cilcil.modules.login.service.SysUserService;
import com.cilcil.modules.login.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

/**
 * @Author SlyAimer
 * @Date 2023/8/31 0:47
 * @Version 1.0
 */
@Service
public class SysUserServiceImpl  extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService{
}
