package com.dabige.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dabige.enitiy.DemoData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ExcelWriteMapper extends BaseMapper<DemoData> {
    List<DemoData> findLimit(@Param("index") int index, @Param("pageSize") int pageSize);
}
