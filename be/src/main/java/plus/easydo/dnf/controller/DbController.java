package plus.easydo.dnf.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RuntimeUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.dnf.constant.SystemConstant;
import plus.easydo.dnf.service.DbService;
import plus.easydo.dnf.util.ResponseUtil;
import plus.easydo.dnf.vo.DataResult;
import plus.easydo.dnf.vo.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * @author yuzhanfeng
 * @Date 2024-01-24 14:34
 * @Description 数据库相关
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/db")
public class DbController {

    private final DbService dbService;

    @Operation(summary = "导出数据库备份")
    @SaCheckPermission("db.export")
    @GetMapping("/export")
    public void exportDb(HttpServletResponse response) throws IOException {
        //创建脚本
        File file = FileUtil.file(SystemConstant.MYSQL_DUMP_SH_PATH);
        FileUtil.writeString(SystemConstant.MYSQL_DUMP_SH_TEXT, file, CharsetUtil.defaultCharset());
        //执行脚本
        RuntimeUtil.execForStr(SystemConstant.MYSQL_DUMP_CMD);
        //获取备份文件
        File backFile = FileUtil.file(SystemConstant.MYSQL_DUMP_PATH);
        //下载备份文件
        ResponseUtil.setFileResponse(response,backFile.getName());
        BufferedInputStream ip = FileUtil.getInputStream(backFile);
        ServletOutputStream op = response.getOutputStream();
        IoUtil.copy(ip,op);
        IoUtil.close(ip);
        IoUtil.close(op);
    }

//    @SaCheckPermission("db.databases")
    @Operation(summary = "数据库列表")
    @GetMapping("/databases")
    public R<List<String>> databases() {
        return DataResult.ok(dbService.databases());
    }

}
