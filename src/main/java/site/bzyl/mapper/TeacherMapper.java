package site.bzyl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import site.bzyl.entity.TeacherDO;

@Mapper
public interface TeacherMapper extends BaseMapper<TeacherDO> {
}
