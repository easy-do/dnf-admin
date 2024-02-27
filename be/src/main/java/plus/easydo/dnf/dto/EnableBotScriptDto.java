package plus.easydo.dnf.dto;

import lombok.Data;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 启用脚本参数封装
 * @date 2024/2/24
 */
@Data
public class EnableBotScriptDto {

    private Long botId;
    private List<Long> scriptIds;
}
