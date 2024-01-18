package com.pearadmin.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pearadmin.modules.sys.domain.SysNotice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * noticeMapper接口
 *
 * @author jmys
 * @date 2021-03-13
 */
@Mapper
public interface SysNoticeMapper extends BaseMapper<SysNotice> {

    /**
     * 查询notice列表
     *
     * @param sysNotice notice
     * @return notice集合
     */
    List<SysNotice> selectSysNoticeList(SysNotice sysNotice);


}
