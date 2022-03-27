package com.pearadmin.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pearadmin.modules.sys.domain.SysPower;
import com.pearadmin.modules.sys.mapper.SysPowerMapper;
import com.pearadmin.modules.sys.mapper.SysRolePowerMapper;
import com.pearadmin.modules.sys.service.SysPowerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Describe: 权限服务实现类
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@Service
public class SysPowerServiceImpl implements SysPowerService {

    /**
     * 引入服务
     */
    @Resource
    private SysPowerMapper sysPowerMapper;

    /**
     * 引入角色权限服务
     */
    @Resource
    private SysRolePowerMapper sysRolePowerMapper;

    /**
     * Describe: 查询权限列表
     * Param: SysPower
     * Return: 执行结果
     */
    @Override
    public List<SysPower> list(SysPower sysPower) {
        return sysPowerMapper.selectList(new QueryWrapper<>(sysPower));
    }

    /**
     * Describe: 保存权限
     * Param: SysPower
     * Return: 执行结果
     */
    @Override
    public boolean save(SysPower sysPower) {
        int result = sysPowerMapper.insert(sysPower);
        return result > 0;
    }

    /**
     * Describe: 根据 ID 查询权限
     * Param: id
     * Return: 返回前线信息
     */
    @Override
    public SysPower getById(String id) {
        return sysPowerMapper.selectById(id);
    }

    /**
     * Describe: 修改权限数据
     * Param: SysPower
     * Return: 执行结果
     */
    @Override
    public boolean update(SysPower sysPower) {
        int result = sysPowerMapper.updateById(sysPower);
        return result > 0;
    }

    /**
     * Describe: 根据 ID 删除权限信息
     * Param: id
     * Return: 执行结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean remove(String id) {
        sysPowerMapper.deleteById(id);
        sysRolePowerMapper.deleteByPowerId(id);
        return true;
    }

    /**
     * Describe: 根据 parentId 查询权限
     * Param: parentId
     * Return: 操作结果
     */
    @Override
    public List<SysPower> selectByParentId(String parentId) {
        LambdaQueryWrapper<SysPower> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysPower::getParentId,parentId);
        return sysPowerMapper.selectList(wrapper);
    }
}
