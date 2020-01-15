package io.renren.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.sys.entity.SysLogEntity;
import io.renren.modules.sys.service.SysLogService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 系统日志
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-11-18 22:41:56
 */
@RestController
@RequestMapping("sys/log")
public class SysLogController {
    @Autowired
    private SysLogService sysLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:syslog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysLogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:syslog:info")
    public R info(@PathVariable("id") Long id){
        SysLogEntity sysLog = sysLogService.getById(id);

        return R.ok().put("sysLog", sysLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:syslog:save")
    public R save(@RequestBody SysLogEntity sysLog){
        sysLogService.save(sysLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:syslog:update")
    public R update(@RequestBody SysLogEntity sysLog){
        ValidatorUtils.validateEntity(sysLog);
        sysLogService.updateById(sysLog);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:syslog:delete")
    public R delete(@RequestBody Long[] ids){
        sysLogService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
