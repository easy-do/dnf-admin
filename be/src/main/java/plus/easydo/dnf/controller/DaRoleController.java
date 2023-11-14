package plus.easydo.dnf.controller;

import com.mybatisflex.core.paginate.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import plus.easydo.dnf.service.IDaRoleService;
import plus.easydo.dnf.entity.DaRole;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

/**
 * 角色信息表 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@RestController
@RequestMapping("/daRole")
public class DaRoleController {

    @Autowired
    private IDaRoleService daRoleService;

    /**
     * 添加 角色信息表
     *
     * @param daRole 角色信息表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("/save")
    public boolean save(@RequestBody DaRole daRole) {
        return daRoleService.save(daRole);
    }


    /**
     * 根据主键删除角色信息表
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("/remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return daRoleService.removeById(id);
    }


    /**
     * 根据主键更新角色信息表
     *
     * @param daRole 角色信息表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("/update")
    public boolean update(@RequestBody DaRole daRole) {
        return daRoleService.updateById(daRole);
    }


    /**
     * 查询所有角色信息表
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    public List<DaRole> list() {
        return daRoleService.list();
    }


    /**
     * 根据角色信息表主键获取详细信息。
     *
     * @param id daRole主键
     * @return 角色信息表详情
     */
    @GetMapping("/getInfo/{id}")
    public DaRole getInfo(@PathVariable Serializable id) {
        return daRoleService.getById(id);
    }


    /**
     * 分页查询角色信息表
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    public Page<DaRole> page(Page<DaRole> page) {
        return daRoleService.page(page);
    }
}
