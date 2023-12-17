package plus.easydo.dnf.dto;

import lombok.Data;

/**
 * @author laoyu
 * @version 1.0
 * @description 编辑脚本参数封装
 * @date 2023/12/2
 */
@Data
public class UpdateScriptDto {

    private Long channelId;

    private String context;

    private Boolean restartFrida;
}
