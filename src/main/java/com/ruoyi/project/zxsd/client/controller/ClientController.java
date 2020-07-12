package com.ruoyi.project.zxsd.client.controller;

import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.zxsd.client.domain.ClientEntity;
import com.ruoyi.project.zxsd.client.service.IClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 后台端用户Controller
 *
 * @author wangdong
 * @date 2020.04.30
 */
@RestController
@RequestMapping("/systemClient")
@Api(tags = {"【后台端】6.1 客户管理"}, description = "客户管理")
public class ClientController extends BaseController {
    private final IClientService clientService;
    @Autowired
    public ClientController(IClientService clientService) {

        this.clientService = clientService;
    }

    /**
     * 查询客户
     */
    @Log(title = "6.1.1 查询客户", businessType = BusinessType.OTHER)
    @PostMapping("/getClient")
    @ApiOperation(value = "6.1.1 查询客户", notes = "查询客户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", dataType = "int", value = "当前页数", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", value = "每页显示记录数", defaultValue = "10")
    })
    public TableDataInfo getClient(@RequestBody ClientEntity clientEntity) {
        startPage();
        try{
            //查询司机
            List<ClientEntity> menuList = clientService.getUserByUserRole(clientEntity);
            return getDataTable(menuList);
        }catch (Exception e){
            logger.error("用户信息查询失败",e);
            TableDataInfo rspData = new TableDataInfo();
            rspData.setCode(HttpStatus.ERROR);
            rspData.setTotal(0L);
            rspData.setMsg("用户信息查询失败");
            return rspData;
        }
    }
}
