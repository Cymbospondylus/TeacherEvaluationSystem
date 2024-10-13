package site.bzyl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import site.bzyl.entity.StudentEvaluationDO;
import site.bzyl.entity.IndicatorDO;
import site.bzyl.entity.TeacherDO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
public interface EvaluationMapper extends BaseMapper<StudentEvaluationDO> {
    
    /**
     * 查询需要评价的教师列表
     */
    List<TeacherDO> listTeachers();

    /**
     * 查询所有评价指标
     */
    List<IndicatorDO> listIndicators();

    @MapKey("indicator_name")
    Map<String, BigDecimal> getEvaluationResult(Long teacherId);
}
