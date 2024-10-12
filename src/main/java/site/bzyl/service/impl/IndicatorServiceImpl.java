package site.bzyl.service.impl;

import org.springframework.stereotype.Service;
import site.bzyl.common.ResponseResult;
import site.bzyl.dto.req.indicators.AddIndicatorReqDTO;
import site.bzyl.dto.req.indicators.UpdateIndicatorReqDTO;
import site.bzyl.entity.IndicatorDO;
import site.bzyl.mapper.IndicatorMapper;
import site.bzyl.service.IndicatorService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IndicatorServiceImpl implements IndicatorService {

    @Resource
    private IndicatorMapper indicatorMapper;

    @Override
    public ResponseResult listIndicators() {
        // 获取所有评价指标
        List<IndicatorDO> indicators = indicatorMapper.selectList(null); // 查询所有数据
        return ResponseResult.success(indicators);
    }

    @Override
    public ResponseResult addIndicator(AddIndicatorReqDTO requestParam) {
        // 构建新评价指标
        IndicatorDO indicatorDO = IndicatorDO.builder()
                .name(requestParam.getName())
                .description(requestParam.getDescription())
                .build();

        // 插入数据库
        int result = indicatorMapper.insert(indicatorDO);
        if (result > 0) {
            return ResponseResult.success("评价指标添加成功");
        }
        return ResponseResult.error("评价指标添加失败");
    }

    @Override
    public ResponseResult updateIndicator(Long indicatorId, UpdateIndicatorReqDTO requestParam) {
        // 查找现有的指标
        IndicatorDO existingIndicator = indicatorMapper.selectById(indicatorId);
        if (existingIndicator == null) {
            return ResponseResult.error("评价指标不存在");
        }

        // 更新指标信息
        existingIndicator.setName(requestParam.getName());
        existingIndicator.setDescription(requestParam.getDescription());

        // 更新数据库
        int result = indicatorMapper.updateById(existingIndicator);
        if (result > 0) {
            return ResponseResult.success("评价指标更新成功");
        }
        return ResponseResult.error("评价指标更新失败");
    }

    @Override
    public ResponseResult deleteIndicator(Long indicatorId) {
        // 删除评价指标
        int result = indicatorMapper.deleteById(indicatorId);
        if (result > 0) {
            return ResponseResult.success("评价指标删除成功");
        }
        return ResponseResult.error("评价指标删除失败");
    }
}
