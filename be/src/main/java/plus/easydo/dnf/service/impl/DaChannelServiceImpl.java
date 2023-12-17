package plus.easydo.dnf.service.impl;


import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.dto.DebugFridaDto;
import plus.easydo.dnf.dto.UpdateScriptDto;
import plus.easydo.dnf.entity.DaChannel;
import plus.easydo.dnf.enums.FridaMessageTypeEnum;
import plus.easydo.dnf.exception.BaseException;
import plus.easydo.dnf.mapper.DaChannelMapper;
import plus.easydo.dnf.qo.ChannelQo;
import plus.easydo.dnf.service.IDaChannelService;
import plus.easydo.dnf.util.DockerUtil;
import plus.easydo.dnf.vo.FcResult;
import plus.easydo.dnf.websocket.FcWebSocketHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static plus.easydo.dnf.entity.table.DaChannelTableDef.DA_CHANNEL;

/**
 * 频道信息 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Slf4j
@Service
public class DaChannelServiceImpl extends ServiceImpl<DaChannelMapper, DaChannel> implements IDaChannelService {

    @Override
    public Page<DaChannel> pageChannel(ChannelQo channelQo) {
        QueryWrapper query = query()
                .select(DA_CHANNEL.ID, DA_CHANNEL.PID, DA_CHANNEL.CHANNEL_NAME, DA_CHANNEL.CHANNEL_STATUS, DA_CHANNEL.FRIDA_STATUS)
                .and(DA_CHANNEL.CHANNEL_NAME.like(channelQo.getChannelName()))
                .and(DA_CHANNEL.PID.like(channelQo.getPid()))
                .and(DA_CHANNEL.CHANNEL_STATUS.eq(channelQo.getChannelStatus()))
                .and(DA_CHANNEL.FRIDA_STATUS.eq(channelQo.getFridaStatus()));
        return page(new Page<>(channelQo.getCurrent(), channelQo.getPageSize()), query);
    }

    @Override
    public List<String> updateJs(UpdateScriptDto updateScriptDto) {
        DaChannel channel = getById(updateScriptDto.getChannelId());
        if (Objects.isNull(channel)) {
            throw new BaseException("频道不存在");
        }
        DaChannel entity = new DaChannel();
        entity.setId(updateScriptDto.getChannelId());
        entity.setScriptContext(updateScriptDto.getContext());
        boolean updateResult = updateById(entity);

        if (updateResult && updateScriptDto.getRestartFrida()) {
            List<String> result = new ArrayList<>();
            channel = getById(updateScriptDto.getChannelId());
            List<String> removeRes = stopFrida(channel);
            result.addAll(removeRes);
            List<String> startResult = restartFrida(channel);
            result.addAll(startResult);
            return result;
        }
        return ListUtil.empty();
    }

    @Override
    public List<String> updatePythonScript(UpdateScriptDto updateScriptDto) {
        DaChannel channel = getById(updateScriptDto.getChannelId());
        if (Objects.isNull(channel)) {
            throw new BaseException("频道不存在");
        }
        DaChannel entity = new DaChannel();
        entity.setId(updateScriptDto.getChannelId());
        entity.setMainPython(updateScriptDto.getContext());
        boolean updateResult = updateById(entity);
        if (updateResult && updateScriptDto.getRestartFrida()) {
            List<String> result = new ArrayList<>();
            channel = getById(updateScriptDto.getChannelId());
            List<String> removeRes = stopFrida(channel);
            result.addAll(removeRes);
            List<String> startResult = restartFrida(channel);
            result.addAll(startResult);
            return result;
        }
        return ListUtil.empty();
    }

    @Override
    public List<String> getFridaLog(Long id) {
        DaChannel channel = getById(id);
        if (Objects.isNull(channel)) {
            throw new BaseException("频道不存在");
        }
        return DockerUtil.getFridaClientLogs(channel.getChannelName());
    }

    @Override
    public List<DaChannel> onlineList() {
        QueryWrapper queryWrapper = query().and(DA_CHANNEL.CHANNEL_STATUS.eq(true)).and(DA_CHANNEL.FRIDA_STATUS.eq(true));
        return list(queryWrapper);
    }

    @Override
    public List<String> restartFrida(Long id) {
        DaChannel channel = getById(id);
        if (Objects.isNull(channel)) {
            throw new BaseException("频道不存在");
        }
        stopFrida(channel);
        return restartFrida(channel);
    }

    @Override
    public List<String> stopFrida(Long id) {
        DaChannel channel = getById(id);
        if (Objects.isNull(channel)) {
            throw new BaseException("频道不存在");
        }
        return stopFrida(channel);
    }

    @Override
    public void debugFrida(DebugFridaDto debugFridaDto) {
        DaChannel channel = getById(debugFridaDto.getChannelId());
        if (Objects.isNull(channel)) {
            throw new BaseException("频道不存在");
        }
        FcResult r = FcResult.builder().type(FridaMessageTypeEnum.DEBUG.getCode()).data(debugFridaDto.getDebugData()).build();
        FcWebSocketHandler.sendMessage(channel.getChannelName(), JSONUtil.toJsonStr(r));
    }

    @Override
    public List<String> getDebugLog(Long id) {
        DaChannel channel = getById(id);
        if (Objects.isNull(channel)) {
            throw new BaseException("频道不存在");
        }
        return FcWebSocketHandler.getLog(channel.getChannelName());
    }


    /**
     * 停止频道的frida
     *
     * @param channel channel
     * @return java.util.List<java.lang.String>
     * @author laoyu
     * @date 2023/12/3
     */
    private List<String> stopFrida(DaChannel channel) {
        String fridaClient = channel.getFridaClient();
        if (CharSequenceUtil.isNotBlank(fridaClient)) {
            List<String> removeRes = DockerUtil.stopFridaClient(fridaClient);
            log.info("remove frida client {}, resul:{}", fridaClient, removeRes);
            DaChannel entity = new DaChannel();
            entity.setId(channel.getId());
            entity.setFridaClient("");
            entity.setFridaStatus(false);
            updateById(entity);
            return removeRes;
        }
        return ListUtil.empty();
    }

    private List<String> restartFrida(DaChannel channel) {
        boolean scriptIsBlank = CharSequenceUtil.isBlank(channel.getScriptContext());
        boolean mainIsBlank = CharSequenceUtil.isBlank(channel.getMainPython());
        List<String> startResult = DockerUtil.startChannelFrida(channel, scriptIsBlank, mainIsBlank);
        DaChannel entity = new DaChannel();
        entity.setId(channel.getId());
        entity.setFridaClient(startResult.get(0));
        entity.setFridaStatus(true);
        if (scriptIsBlank) {
            entity.setScriptContext(channel.getScriptContext());
        }
        if (mainIsBlank) {
            entity.setMainPython(channel.getMainPython());
        }
        updateById(entity);
        return startResult;
    }
}
