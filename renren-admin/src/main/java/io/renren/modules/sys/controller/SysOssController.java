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

import io.renren.modules.sys.entity.SysOssEntity;
import io.renren.modules.sys.service.SysOssService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 文件上传
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-11-18 22:41:56
 */
@RestController
@RequestMapping("sys/sysoss")
public class SysOssController {
    @Autowired
    private SysOssService sysOssService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:sysoss:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysOssService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:sysoss:info")
    public R info(@PathVariable("id") Long id){
        SysOssEntity sysOss = sysOssService.getById(id);

        return R.ok().put("sysOss", sysOss);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:sysoss:save")
    public R save(@RequestBody SysOssEntity sysOss){
        sysOssService.save(sysOss);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:sysoss:update")
    public R update(@RequestBody SysOssEntity sysOss){
        ValidatorUtils.validateEntity(sysOss);
        sysOssService.updateById(sysOss);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:sysoss:delete")
    public R delete(@RequestBody Long[] ids){
        sysOssService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
