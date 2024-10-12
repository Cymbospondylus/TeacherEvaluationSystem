package site.bzyl.service;

import site.bzyl.common.ResponseResult;
import site.bzyl.dto.req.indicators.AddIndicatorReqDTO;
import site.bzyl.dto.req.indicators.UpdateIndicatorReqDTO;

public interface IndicatorService {
    ResponseResult listIndicators();

    ResponseResult addIndicator(AddIndicatorReqDTO requestParam);

    ResponseResult updateIndicator(Long indicatorId, UpdateIndicatorReqDTO requestParam);

    ResponseResult deleteIndicator(Long indicatorId);
}
