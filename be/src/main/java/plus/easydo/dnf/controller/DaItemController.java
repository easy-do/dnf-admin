package plus.easydo.dnf.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.mybatisflex.core.paginate.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import plus.easydo.dnf.service.IDaItemService;
import plus.easydo.dnf.entity.DaItemEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

/**
 * 物品缓存 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
public class DaItemController {

    private final IDaItemService daItemService;

    /**
     * 添加 物品缓存
     *
     * @param daItem 物品缓存
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @SaCheckRole("admin")
    @PostMapping("/save")
    public boolean save(@RequestBody DaItemEntity daItem) {
        return daItemService.save(daItem);
    }


    /**
     * 根据主键删除物品缓存
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @SaCheckRole("admin")
    @DeleteMapping("/remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return daItemService.removeById(id);
    }


    /**
     * 根据主键更新物品缓存
     *
     * @param daItem 物品缓存
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @SaCheckRole("admin")
    @PutMapping("/update")
    public boolean update(@RequestBody DaItemEntity daItem) {
        return daItemService.updateById(daItem);
    }


    /**
     * 查询所有物品缓存
     *
     * @return 所有数据
     */
    @SaCheckLogin
    @GetMapping("/list")
    public List<DaItemEntity> list() {
        return daItemService.list();
    }


    /**
     * 根据物品缓存主键获取详细信息。
     *
     * @param id daItem主键
     * @return 物品缓存详情
     */
    @SaCheckLogin
    @GetMapping("/getInfo/{id}")
    public DaItemEntity getInfo(@PathVariable Serializable id) {
        return daItemService.getById(id);
    }


    /**
     * 分页查询物品缓存
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @SaCheckRole("admin")
    @GetMapping("/page")
    public Page<DaItemEntity> page(Page<DaItemEntity> page) {
        return daItemService.page(page);
    }
}
