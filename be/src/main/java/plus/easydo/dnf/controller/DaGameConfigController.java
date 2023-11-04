package plus.easydo.dnf.controller;

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
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.dnf.entity.DaGameConfigEntity;
import plus.easydo.dnf.service.IDaGameConfigService;

import java.io.Serializable;
import java.util.List;

/**
 * 游戏配置 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/daGameConfig")
public class DaGameConfigController {

    private final IDaGameConfigService daGameConfigService;

    /**
     * 添加 游戏配置
     *
     * @param daGameConfig 游戏配置
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @SaCheckRole("admin")
    @PostMapping("/save")
    public boolean save(@RequestBody DaGameConfigEntity daGameConfig) {
        return daGameConfigService.save(daGameConfig);
    }


    /**
     * 根据主键删除游戏配置
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @SaCheckRole("admin")
    @DeleteMapping("/remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return daGameConfigService.removeById(id);
    }


    /**
     *
     *
     *
     *
     * @param daGameConfig 游戏配置
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @SaCheckRole("admin")
    @PutMapping("/update")
    public boolean update(@RequestBody DaGameConfigEntity daGameConfig) {
        return daGameConfigService.updateById(daGameConfig);
    }


    /**
     * 查询所有游戏配置
     *
     * @return 所有数据
     */
    @GetMapping("/list")
    public List<DaGameConfigEntity> list() {
        return daGameConfigService.list();
    }


    /**
     * 根据游戏配置主键获取详细信息。
     *
     * @param id daGameConfig主键
     * @return 游戏配置详情
     */
    @GetMapping("/getInfo/{id}")
    public DaGameConfigEntity getInfo(@PathVariable Serializable id) {
        return daGameConfigService.getById(id);
    }


    /**
     * 分页查询游戏配置
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    public Page<DaGameConfigEntity> page(Page<DaGameConfigEntity> page) {
        return daGameConfigService.page(page);
    }
}
