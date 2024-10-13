package site.bzyl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import site.bzyl.dto.req.PageUserReqDTO;
import site.bzyl.dto.resp.StudentResponseDTO;
import site.bzyl.entity.StudentDO;

import java.util.List;

@Mapper
public interface StudentMapper extends BaseMapper<StudentDO> {
    List<StudentResponseDTO> pageStudent(@Param("requestParam") PageUserReqDTO requestParam);
}
