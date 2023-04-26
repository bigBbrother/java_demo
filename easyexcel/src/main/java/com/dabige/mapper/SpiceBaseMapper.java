package com.dabige.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public interface SpiceBaseMapper<T> extends BaseMapper<T> {
    int insertBatchSomeColumn(List<T> entityList);
}
