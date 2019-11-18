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

import io.renren.modules.sys.entity.SysConfigEntity;
import io.renren.modules.sys.service.SysConfigService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 系统配置信息表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-11-18 22:41:56
 */
@RestController
@RequestMapping("sys/sysconfig")
public class SysConfigController {
    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:sysconfig:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysConfigService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:sysconfig:info")
    public R info(@PathVariable("id") Long id){
        SysConfigEntity sysConfig = sysConfigService.getById(id);

        return R.ok().put("sysConfig", sysConfig);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:sysconfig:save")
    public R save(@RequestBody SysConfigEntity sysConfig){
        sysConfigService.save(sysConfig);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:sysconfig:update")
    public R update(@RequestBody SysConfigEntity sysConfig){
        ValidatorUtils.validateEntity(sysConfig);
        sysConfigService.updateById(sysConfig);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:sysconfig:delete")
    public R delete(@RequestBody Long[] ids){
        sysConfigService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
