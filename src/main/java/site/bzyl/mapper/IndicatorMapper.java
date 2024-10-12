package site.bzyl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import site.bzyl.entity.IndicatorDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IndicatorMapper extends BaseMapper<IndicatorDO> {
    // 继承了BaseMapper后，自动提供了增删改查的基本方法
}
