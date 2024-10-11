package site.bzyl.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import site.bzyl.dto.req.PageUserReqDTO;
import site.bzyl.entity.UserDO;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
    List<UserDO> selectPageByRoleId(@Param("requestParam") PageUserReqDTO requestParam);
}
