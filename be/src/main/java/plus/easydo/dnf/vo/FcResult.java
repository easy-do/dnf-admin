package plus.easydo.dnf.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author laoyu
 * @version 1.0
 * @description FcResult
 * @date 2023/12/2
 */
@Data
@Builder
public class FcResult {
    private String type;
    private String secret;
    private String channel;
    private Object data;
}
