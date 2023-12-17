package plus.easydo.dnf.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.dnf.entity.DaFridaFunction;
import plus.easydo.dnf.qo.FridaFunctionQo;
import plus.easydo.dnf.service.IDaFridaFunctionService;
import plus.easydo.dnf.vo.DataResult;
import plus.easydo.dnf.vo.R;

import java.io.Serializable;
import java.util.List;

/**
 * frida函数信息 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fridaFunction")
public class DaFridaFunctionController {

    private final IDaFridaFunctionService daFridaFunctionService;

    /**
     * 添加 frida函数信息
     *
     * @param daFridaFunction frida函数信息
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @SaCheckPermission("function.save")
    @PostMapping("/save")
    public R<Boolean> saveFridaFunction(@RequestBody DaFridaFunction daFridaFunction) {
        return DataResult.ok(daFridaFunctionService.saveFridaFunction(daFridaFunction));
    }



    /**
     * 根据主键更新frida函数信息
     *
     * @param daFridaFunction frida函数信息
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @SaCheckPermission("function.update")
    @PostMapping("/update")
    public R<Boolean> updateFridaFunction(@RequestBody DaFridaFunction daFridaFunction) {
        return DataResult.ok(daFridaFunctionService.updateFridaFunction(daFridaFunction));
    }


    /**
     * 查询所有frida函数信息
     *
     * @return 所有数据
     */
    @SaCheckLogin
    @GetMapping("/list")
    public R<List<DaFridaFunction>> listFridaFunction() {
        return DataResult.ok(daFridaFunctionService.listFridaFunction());
    }


    /**
     * 根据frida函数信息主键获取详细信息。
     *
     * @param id daFridaFunction主键
     * @return frida函数信息详情
     */
    @SaCheckPermission("function")
    @GetMapping("/getInfo/{id}")
    public R<DaFridaFunction> getFridaFunctionInfo(@PathVariable Long id) {
        return DataResult.ok(daFridaFunctionService.getInfo(id));
    }

    /**
     * 根据frida函数信息主键获取详细信息。
     *
     * @param id daFridaFunction主键
     * @return frida函数信息详情
     */
    @SaCheckPermission("function")
    @GetMapping("/getChildrenFunction/{id}")
    public R<List<DaFridaFunction>> getChildrenFunction(@PathVariable Long id) {
        return DataResult.ok(daFridaFunctionService.getChildrenFunction(id));
    }


    /**
     * 分页查询frida函数信息
     *
     * @param fridaFunctionQo 分页对象
     * @return 分页对象
     */
    @SaCheckPermission("function")
    @PostMapping("/page")
    public R<List<DaFridaFunction>> pageFridaFunction(@RequestBody FridaFunctionQo fridaFunctionQo) {
        return DataResult.ok(daFridaFunctionService.pageFridaFunction(fridaFunctionQo));
    }
}
