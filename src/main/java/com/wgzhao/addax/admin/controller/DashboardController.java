package com.wgzhao.addax.admin.controller;

import com.wgzhao.addax.admin.repository.oracle.TbImpFlagRepo;
import com.wgzhao.addax.admin.repository.oracle.ViewPseudoRepo;
import com.wgzhao.addax.admin.repository.pg.AddaxStaRepo;
import com.wgzhao.addax.admin.utils.CacheUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 监控主页面，主要是汇总信息和图表展示
 */
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private ViewPseudoRepo viewPseudoRepo;

    @Autowired
    private TbImpFlagRepo tbImpFlagRepo;

    @Autowired
    private AddaxStaRepo addaxStaRepo;

    @Resource
    CacheUtil cacheUtil;

    // 各数据源采集完成率，用于图表展示
    @RequestMapping("/accomplishRatio")
    public List<Map<String, Float>> accompListRatio() {
        return viewPseudoRepo.accompListRatio();
    }

    //  最近5天采集耗时对比
    @RequestMapping("/last5DaysEtlTime")
    public List<Map<String, Object>> last5DaysEtlTime() {
        int l5td = Integer.parseInt(cacheUtil.get("param.L5TD"));
        return tbImpFlagRepo.findLast5DaysEtlTime(l5td);
    }

    // 最近交易日采集的数据量， 以 GB 为单位
    @RequestMapping("/lastEtlData")
    public double lastEtlData() {
        String td = cacheUtil.get("param.TD");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            Date d = sdf.parse(td);
            long btime = d.getTime() / 1000;
            long etime = btime + 86400;
            return addaxStaRepo.findLastEtlData(btime, etime);
        } catch (ParseException e) {
            return 0.0;
        }
    }

    // 获取最近12个月的采集累计数据量，单位为 GiB
    @RequestMapping("/last12MonthsEtlData")
    public List<Map<String, Object>> last12MonthsEtlData() {
        Date etime = new Date();
        Date btime = new Date(etime.getTime() - 86400 * 365 * 1000L);
        return addaxStaRepo.findEtlDataByMonth(btime, etime);
    }
}
