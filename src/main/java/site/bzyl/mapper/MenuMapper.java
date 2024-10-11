package site.bzyl.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import site.bzyl.entity.MenuDO;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<MenuDO> {
    List<String> selectPermsByUserId(Long userId);
}
