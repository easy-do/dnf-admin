package plus.easydo.dnf.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.alibaba.excel.EasyExcelFactory;
import com.mybatisflex.core.paginate.Page;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import plus.easydo.dnf.entity.DaItemEntity;
import plus.easydo.dnf.listener.ItemDataListener;
import plus.easydo.dnf.qo.PageQo;
import plus.easydo.dnf.service.IDaItemService;
import plus.easydo.dnf.util.ResponseUtil;
import plus.easydo.dnf.vo.DataResult;
import plus.easydo.dnf.vo.R;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
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
     * 导入物品缓存
     * @param file file
     * @throws IOException
     */
    @SaCheckRole("admin")
    @PostMapping("/importItem")
    public R<Long> importItem(@RequestParam("file") MultipartFile file) throws IOException {
        EasyExcelFactory.read(file.getInputStream(), DaItemEntity.class, new ItemDataListener(daItemService)).sheet().doRead();
        return DataResult.ok();
    }

    /**
     * 下载导入模板
     *
     * @param response response
     * @author laoyu
     * @date 2023/10/29
     */
    @SaCheckRole("admin")
    @GetMapping("/downloadTemplate")
    public void importItem(HttpServletResponse response) throws IOException {
        ServletOutputStream opt = response.getOutputStream();
        ResponseUtil.setFileResponse(response,"物品导入模板.xlsx");
        EasyExcelFactory.write(opt, DaItemEntity.class).sheet(1).doWrite(Collections.emptyList());
    }

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
    @GetMapping("/remove/{id}")
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
    @PostMapping("/update")
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
    public R<List<DaItemEntity>> list(@RequestParam(value = "name", required = false)String name) {
        return DataResult.ok(daItemService.listByName(name));
    }



    /**
     * 分页查询物品缓存
     *
     * @param pageQo 分页对象
     * @return 分页对象
     */
    @SaCheckRole("admin")
    @PostMapping("/page")
    public R<Page<DaItemEntity>> page(@RequestBody PageQo pageQo) {
        return DataResult.ok(daItemService.itemPage(pageQo));
    }
}
