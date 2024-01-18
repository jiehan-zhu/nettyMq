package com.pearadmin.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pearadmin.common.web.domain.request.PageDomain;
import com.pearadmin.modules.sys.domain.SysDept;
import com.pearadmin.modules.sys.mapper.SysDeptMapper;
import com.pearadmin.modules.sys.service.SysDeptService;
import com.pearadmin.modules.sys.mapper.SysUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service
public class SysDeptServiceImpl implements SysDeptService {

    @Resource
    private SysDeptMapper sysDeptMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * Describe: 查询部门数据
     * Param: QueryRoleParam
     * Return: 操作结果
     */
    @Override
    public List<SysDept> list(SysDept param) {
        LambdaQueryWrapper<SysDept> wrapper = new LambdaQueryWrapper<>();
        if(null!=param.getDeptName())wrapper.like(SysDept::getDeptName,param.getDeptName());
        return sysDeptMapper.selectList(wrapper);
    }

    /**
     * Describe: 查询部门数据 分页
     * Param: QueryRoleParam
     * Return: 操作结果
     */
    @Override
    public PageInfo<SysDept> page(SysDept param, PageDomain pageDomain) {
        PageHelper.startPage(pageDomain.getPage(), pageDomain.getLimit());
        List<SysDept> list = list(param);
        return new PageInfo<>(list);
    }

    /**
     * Describe: 保存部门数据
     * Param: SysDept
     * Return: 操作结果
     */
    @Override
    public boolean save(SysDept sysDept) {
        if (null == sysDept.getParentId()) {
            sysDept.setParentId("0");
        }
        int result = sysDeptMapper.insert(sysDept);
        return result > 0;
    }

    /**
     * Describe: 根据 id 删除部门数据
     * Param: id
     * Return: Boolean
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean remove(String id) {
        sysDeptMapper.deleteById(id);
        sysUserMapper.resetDeptByDeptId(id);
        return true;
    }

    @Override
    public SysDept getById(String id) {
        return sysDeptMapper.selectById(id);
    }

    @Override
    public boolean update(SysDept sysDept) {
        sysDeptMapper.updateById(sysDept);
        return true;
    }

    /**
     * Describe: 根据 id 批量删除部门数据
     * Param: ids
     * Return: Boolean
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchRemove(String[] ids) {
        sysDeptMapper.deleteBatchIds(Arrays.asList(ids));
        sysUserMapper.resetDeptByDeptIds(ids);
        return true;
    }

    /**
     * Describe: 根据 parentId 查询部门数据
     * Param: parentId
     * Return: 操作结果
     */
    @Override
    public List<SysDept> selectByParentId(String tenantId) {
        LambdaQueryWrapper<SysDept> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDept::getParentId,tenantId);
        return sysDeptMapper.selectList(wrapper);
    }

    @Override
    public List<SysDept> treeAndChildren(String parent) {
        List<SysDept> ds = selectByParentId(parent);
        for (SysDept sd: ds) {
            treeAndChildren(sd,ds);
        }
        return ds;
    }

    private void treeAndChildren(SysDept sd, List<SysDept> ds) {
        List<SysDept> dss = selectByParentId(sd.getDeptId());
        if(dss.size() > 0) {
            for (SysDept sdd: dss) {
                ds.add(sdd);
                treeAndChildren(sdd,ds);
            }
        } else {
            return;
        }
    }
}
