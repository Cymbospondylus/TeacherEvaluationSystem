package site.bzyl.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import site.bzyl.eneity.UserDO;

@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
}
