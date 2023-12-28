package com.wgzhao.fsbrowser.controller;

import com.wgzhao.fsbrowser.model.oracle.VwImpPlanAll;
import com.wgzhao.fsbrowser.repository.oracle.VwImpPlanAllRepo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 实时监控接口
 */
@Api(value = "实时监控接口", tags = {"实时监控接口"})
@RestController
@RequestMapping("/realtime")
public class RealtimeController {

    @Autowired
    private VwImpPlanAllRepo vwImpPlanAllRepo;

    // 计划任务执行情况
    @GetMapping("/planAll")
    public List<VwImpPlanAll> plan() {
        return vwImpPlanAllRepo.findAll();
    }
}
