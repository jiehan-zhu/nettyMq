package com.pearadmin.modules.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pearadmin.common.web.domain.request.PageDomain;
import com.pearadmin.modules.sys.domain.SysNotice;
import com.pearadmin.modules.sys.mapper.SysNoticeMapper;
import com.pearadmin.modules.sys.service.SysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * noticeService业务层处理
 *
 * @author jmys
 * @date 2021-03-13
 */
@Service
public class SysNoticeServiceImpl implements SysNoticeService {
    @Autowired
    private SysNoticeMapper sysNoticeMapper;

    /**
     * 查询notice
     *
     * @param id noticeID
     * @return notice
     */
    @Override
    public SysNotice selectSysNoticeById(String id) {
        return sysNoticeMapper.selectById(id);
    }

    /**
     * 查询notice列表
     *
     * @param sysNotice notice
     * @return notice
     */
    @Override
    public List<SysNotice> selectSysNoticeList(SysNotice sysNotice) {
        return sysNoticeMapper.selectSysNoticeList(sysNotice);
    }

    /**
     * 查询notice
     *
     * @param sysNotice  notice
     * @param pageDomain
     * @return notice 分页集合
     */
    @Override
    public PageInfo<SysNotice> selectSysNoticePage(SysNotice sysNotice, PageDomain pageDomain) {
        PageHelper.startPage(pageDomain.getPage(), pageDomain.getLimit());
        List<SysNotice> data = sysNoticeMapper.selectSysNoticeList(sysNotice);
        return new PageInfo<>(data);
    }

    /**
     * 新增notice
     *
     * @param sysNotice notice
     * @return 结果
     */

    @Override
    public int insertSysNotice(SysNotice sysNotice) {
        sysNotice.setCreateTime(LocalDateTime.now());
        return sysNoticeMapper.insert(sysNotice);
    }

    /**
     * 修改notice
     *
     * @param sysNotice notice
     * @return 结果
     */
    @Override
    public int updateSysNotice(SysNotice sysNotice) {
        return sysNoticeMapper.updateById(sysNotice);
    }

    /**
     * 删除notice对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysNoticeByIds(String[] ids) {
        List<String> idList = Arrays.asList(ids);
        return sysNoticeMapper.deleteBatchIds(idList);
    }

    /**
     * 删除notice信息
     *
     * @param id noticeID
     * @return 结果
     */
    @Override
    public int deleteSysNoticeById(String id) {
        return sysNoticeMapper.deleteById(id);
    }
}
