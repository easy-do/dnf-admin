package plus.easydo.dnf.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.hutool.core.lang.tree.Tree;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import plus.easydo.dnf.dto.AuthRoleResourceDto;
import plus.easydo.dnf.service.IDaResourceService;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.dnf.vo.DataResult;
import plus.easydo.dnf.vo.R;

import java.util.List;

/**
 * 系统资源 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/resource")
public class DaResourceController {

    
    private final IDaResourceService resourceService;


    /**
     * 授权角色资源
     */
    @SaCheckRole("admin")
    @PostMapping("/authRoleResource")
    public R<Object> authRoleResource(@RequestBody AuthRoleResourceDto authRoleResourceDto) {
        return DataResult.ok(resourceService.authRoleResource(authRoleResourceDto));
    }


    /**
     * 获取所有资源下拉树
     */
    @SaCheckRole("admin")
    @GetMapping("/resourceTree")
    public R<List<Tree<Long>>> resourceTree() {
        return DataResult.ok(resourceService.resourceTree());
    }



    /**
     * 加载对应角色资源列表
     */
    @SaCheckRole("admin")
    @GetMapping(value = "/roleResource/{roleId}")
    public R<List<Tree<Long>>> roleResource(@PathVariable("roleId") Long roleId) {
        return DataResult.ok(resourceService.roleResource(roleId));
    }

    /**
     * 加载对应角色资源id集合
     */
    @SaCheckRole("admin")
    @GetMapping(value = "/roleResourceIds/{roleId}")
    public R<List<Long>> roleResourceIds(@PathVariable("roleId") Long roleId) {
        return DataResult.ok(resourceService.roleResourceIds(roleId));
    }

}
