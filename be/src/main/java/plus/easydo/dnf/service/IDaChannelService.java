package plus.easydo.dnf.service;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import plus.easydo.dnf.dto.DebugFridaDto;
import plus.easydo.dnf.dto.UpdateScriptDto;
import plus.easydo.dnf.entity.DaChannel;
import plus.easydo.dnf.qo.ChannelQo;

import java.util.List;

/**
 * 频道信息 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface IDaChannelService extends IService<DaChannel> {

    Page<DaChannel> pageChannel(ChannelQo channelQo);

    List<String> updateJs(UpdateScriptDto updateScriptDto);

    List<String> restartFrida(Long id);

    List<String> stopFrida(Long id);

    void debugFrida(DebugFridaDto debugFridaDto);

    List<String> getDebugLog(Long id);

    List<String> updatePythonScript(UpdateScriptDto updateScriptDto);

    List<String> getFridaLog(Long id);

    List<DaChannel> onlineList();
}
