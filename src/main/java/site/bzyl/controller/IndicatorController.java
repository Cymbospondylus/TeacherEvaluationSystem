package site.bzyl.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import site.bzyl.common.ResponseResult;
import site.bzyl.dto.req.indicators.AddIndicatorReqDTO;
import site.bzyl.dto.req.indicators.UpdateIndicatorReqDTO;
import site.bzyl.service.IndicatorService;

import javax.annotation.Resource;

/**
 * 评价指标管理接口
 */
@RestController
@RequestMapping("/indicator")
public class IndicatorController {

    @Resource
    private IndicatorService indicatorService;

    /**
     * 获取评价指标列表
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('management:indicator')")
    public ResponseResult listIndicators() {
        return indicatorService.listIndicators();
    }

    /**
     * 添加评价指标
     */
    @PreAuthorize("hasAuthority('management:indicator')")
    @PostMapping
    public ResponseResult addIndicator(@RequestBody AddIndicatorReqDTO requestParam) {
        return indicatorService.addIndicator(requestParam);
    }

    /**
     * 修改评价指标
     */
    @PutMapping("/{indicatorId}")
    @PreAuthorize("hasAuthority('management:indicator')")
    public ResponseResult updateIndicator(@PathVariable Long indicatorId, @RequestBody UpdateIndicatorReqDTO requestParam) {
        return indicatorService.updateIndicator(indicatorId, requestParam);
    }

    /**
     * 删除评价指标
     */
    @DeleteMapping("/{indicatorId}")
    @PreAuthorize("hasAuthority('management:indicator')")
    public ResponseResult deleteIndicator(@PathVariable Long indicatorId) {
        return indicatorService.deleteIndicator(indicatorId);
    }
}
