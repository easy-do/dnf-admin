package plus.easydo.dnf.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import plus.easydo.dnf.qo.DaRoleQo;
import plus.easydo.dnf.service.IDaRoleService;
import plus.easydo.dnf.entity.DaRole;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.dnf.vo.DataResult;
import plus.easydo.dnf.vo.R;

import java.io.Serializable;
import java.util.List;

/**
 * 角色信息表 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/role")
public class DaRoleController {

    private final IDaRoleService daRoleService;



    /**
     * 分页查询角色信息表
     *
     * @param daRoleQo daRoleQo
     * @return 分页对象
     */
    @SaCheckPermission("role")
    @PostMapping("/page")
    public R<List<DaRole>> pageRole(@RequestBody DaRoleQo daRoleQo) {
        return DataResult.ok(daRoleService.pageRole(daRoleQo));
    }

    /**
     * 根据角色信息表主键获取详细信息。
     *
     * @param id daRole主键
     * @return 角色信息表详情
     */
    @SaCheckPermission("role")
    @GetMapping("/info/{id}")
    public R<DaRole> getRoleInfo(@PathVariable Serializable id) {
        return DataResult.ok(daRoleService.getById(id));
    }

    /**
     * 根据主键更新角色信息表
     *
     * @param daRole 角色信息表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @SaCheckPermission("role.update")
    @PostMapping("/update")
    public R<Boolean> updateRole(@RequestBody DaRole daRole) {
        return DataResult.ok(daRoleService.updateById(daRole));
    }

    /**
     * 添加 角色信息表
     *
     * @param daRole 角色信息表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @SaCheckPermission("role.save")
    @PostMapping("/save")
    public R<Boolean> saveRole(@RequestBody DaRole daRole) {
        return DataResult.ok(daRoleService.save(daRole));
    }

}
