package plus.easydo.dnf.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.json.JSONObject;
import com.alibaba.excel.EasyExcelFactory;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import plus.easydo.dnf.entity.DaItemEntity;
import plus.easydo.dnf.listener.ItemDataListener;
import plus.easydo.dnf.qo.DaItemQo;
import plus.easydo.dnf.service.IDaItemService;
import plus.easydo.dnf.util.ItemReaderUtil;
import plus.easydo.dnf.util.ResponseUtil;
import plus.easydo.dnf.vo.DataResult;
import plus.easydo.dnf.vo.R;

import java.io.IOException;
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
    @Operation(summary = "导入")
    @SaCheckPermission("item.importItem")
    @PostMapping("/importItem")
    public void importItem(@RequestParam("file") MultipartFile file) throws IOException {
        EasyExcelFactory.read(file.getInputStream(), DaItemEntity.class, new ItemDataListener(daItemService)).sheet().doRead();
    }

    /**
     * 导入物品缓存 从pvf导出的7z文件导入
     * @param file file
     * @throws IOException
     */
    @Operation(summary = "从pvf导出的7z文件导入")
    @SaCheckPermission("item.importItemFor7z")
    @PostMapping("/importItemFor7z")
    public void importItemFor7z(@RequestParam("file") MultipartFile file) throws IOException {
        List<JSONObject> res = ItemReaderUtil.reader(file);
        daItemService.importItemForJson(res);
    }

    /**
     * 下载导入模板
     *
     * @param response response
     * @author laoyu
     * @date 2023/10/29
     */
    @Operation(summary = "下载导入模板")
    @SaCheckPermission("item")
    @GetMapping("/downloadTemplate")
    public void downloadTemplate(HttpServletResponse response) throws IOException {
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
    @Operation(summary = "添加")
    @SaCheckPermission("item.save")
    @PostMapping("/save")
    public boolean saveItem(@RequestBody DaItemEntity daItem) {
        return daItemService.save(daItem);
    }


    /**
     * 根据主键删除物品缓存
     *
     * @param ids 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @Operation(summary = "删除")
    @SaCheckPermission("item.remove")
    @PostMapping("/remove")
    public boolean removeItem(@RequestBody List<String> ids) {
        return daItemService.removeByIds(ids);
    }


    /**
     * 根据主键更新物品缓存
     *
     * @param daItem 物品缓存
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @Operation(summary = "更新")
    @SaCheckPermission("item.update")
    @PostMapping("/update")
    public boolean updateItem(@RequestBody DaItemEntity daItem) {
        return daItemService.updateById(daItem);
    }


    /**
     * 查询所有物品缓存
     *
     * @return 所有数据
     */
    @Operation(summary = "查询所有物品")
    @SaCheckLogin
    @GetMapping("/list")
    public R<List<DaItemEntity>> listItem(@RequestParam(value = "name", required = false)String name) {
        return DataResult.ok(daItemService.listByName(name));
    }



    /**
     * 分页查询物品缓存
     *
     * @param daItemQo 分页对象
     * @return 分页对象
     */
    @Operation(summary = "分页")
    @SaCheckPermission("item")
    @PostMapping("/page")
    public R<List<DaItemEntity>> pageItem(@RequestBody DaItemQo daItemQo) {
        return DataResult.ok(daItemService.itemPage(daItemQo));
    }
}
