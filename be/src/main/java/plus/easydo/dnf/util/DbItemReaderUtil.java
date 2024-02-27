package plus.easydo.dnf.util;

import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import plus.easydo.dnf.vo.GameItemVo;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 数据库物品格子读取工具类
 * @date 2023/12/28
 */
@Slf4j
public class DbItemReaderUtil {

    private DbItemReaderUtil() {
    }

    /**
     * 解压读取byte数据为装备信息
     *
     * @param data data
     * @return java.util.List<plus.easydo.dnf.vo.GameItemVo>
     * @author laoyu
     * @date 2023/12/30
     */
    public static List<GameItemVo> readerItem(byte[] data){
        if (data != null && data.length > 4) {
            try {
                // 创建一个新的数组，长度比原数组少4
                byte[] result = new byte[data.length - 4];
                // 将除了前四个元素之外的所有元素复制到新数组中
                for (int i = 4; i < data.length; i++) {
                    result[i - 4] = data[i];
                }
                byte[] decompress = ZLibUtils.decompress(result);
                ByteBuffer byteBuffer = ByteBuffer.wrap(decompress);
                byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
                List<GameItemVo> resultList = new ArrayList<>();
                int i = 0;
                while (byteBuffer.hasRemaining()) {
                    byte[] itemBytes = new byte[61]; // 创建一个大小为61的字节数组
                    byteBuffer.get(itemBytes); // 从缓冲区读取61个字节到数组中
                    //解析61位数据
                    GameItemVo gameItemVo = new GameItemVo();
                    gameItemVo.setIndex(i);
                    ByteBuffer itemByteBuffer = ByteBuffer.wrap(itemBytes);
                    itemByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
                    // 0 封装/魔法封印
                    gameItemVo.setSealType(itemByteBuffer.get());
                    //1 类型 1装备 8时装 5 6
                    gameItemVo.setItemType(itemByteBuffer.get());
                    //2-5 装备id
                    gameItemVo.setItemId(itemByteBuffer.getInt());
                    //6 强化等级
                    gameItemVo.setUpgrade(itemByteBuffer.get());
                    //7-10 装备品级 消耗品代表数量
                    gameItemVo.setQuality(itemByteBuffer.getInt());
                    //11-12 耐久度
                    gameItemVo.setDurability(itemByteBuffer.getShort());
                    //13-16 宝珠
                    gameItemVo.setEnchantment(itemByteBuffer.getInt());
                    //17 增幅 1体力 2精神 3力量 4智力
                    gameItemVo.setIncreaseType(itemByteBuffer.get());
                    //18-19 增幅附加值 最大65536
                    gameItemVo.setIncreaseLevel(itemByteBuffer.getShort());
                    //20-30 暂时不知道干啥的
                    byte[] bytes20to30 = new byte[10];
                    itemByteBuffer.get(bytes20to30);
                    gameItemVo.setExt20to30(bytes20to30);
                    //31-32 异界气息
                    gameItemVo.setOtherworldly(itemByteBuffer.getShort());
                    //33-36
                    byte[] bytes33to36 = new byte[4];
                    itemByteBuffer.get(bytes33to36);
                    gameItemVo.setExt33to36(bytes33to36);
                    //37-50 魔法封印，具体可以看看神牛的教程，或者插件相关的代码
                    byte[] bytes37to50 = new byte[14];
                    itemByteBuffer.get(bytes37to50);
                    gameItemVo.setExt37to50(bytes37to50);
                    //51 锻造等级
                    gameItemVo.setForgeLevel(itemBytes[51]);
                    //一直到结尾的一段
                    byte[] bytes52toEnd= new byte[10];
                    itemByteBuffer.get(bytes52toEnd);
                    gameItemVo.setExt52toEnd(bytes52toEnd);
                    resultList.add(gameItemVo);
                    i++;
                }
                return resultList;
            }catch (Exception e){
                log.warn("读取blob字段失败:{}", ExceptionUtil.getMessage(e));
            }
        }
        return Collections.emptyList();
    }


    /**
     * 写入装备信息为byte并压缩
     *
     * @param itemVoList itemVoList
     * @return byte[]
     * @author laoyu
     * @date 2023/12/30
     */
    public static byte[] writeItem(List<GameItemVo> itemVoList, byte[] startByte){
        byte[] decompress = new byte[itemVoList.size()*61];
        int currentIndex = 0;
        for (GameItemVo gameItemVo : itemVoList) {
            byte[] itemBytes = new byte[61];
            ByteBuffer itemByteBuffer = ByteBuffer.wrap(itemBytes);
            itemByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            // 0 封装/魔法封印
            itemByteBuffer.put((byte) gameItemVo.getSealType());
            //1 类型 1装备 8时装 5 6
            itemByteBuffer.put((byte) gameItemVo.getItemType());
            //2-5 装备id
            itemByteBuffer.putInt(gameItemVo.getItemId());
            //6 强化等级
            itemByteBuffer.put((byte) gameItemVo.getUpgrade());
            //7-10 装备品级 消耗品代表数量
            itemByteBuffer.putInt(gameItemVo.getQuality());
            //11-12 耐久度
            itemByteBuffer.putShort((short) gameItemVo.getDurability());
            //13-16 宝珠
            itemByteBuffer.putInt(gameItemVo.getEnchantment());
            //17 增幅 1体力 2精神 3力量 4智力
            itemByteBuffer.put((byte) gameItemVo.getIncreaseType());
            //18-19 增幅附加值 最大65536
            itemByteBuffer.putShort((short) gameItemVo.getIncreaseLevel());
            //20-30 暂时不知道干啥的
            itemByteBuffer.put(gameItemVo.getExt20to30());
            //31-32 异界气息
            itemByteBuffer.putShort((short) gameItemVo.getOtherworldly());
            //33-36
            itemByteBuffer.put(gameItemVo.getExt33to36());
            //37-50 魔法封印，具体可以看看神牛的教程，或者插件相关的代码
            itemByteBuffer.put(gameItemVo.getExt37to50());
            //51 锻造等级
            itemByteBuffer.put((byte) gameItemVo.getForgeLevel());
            //一直到结尾的一段
            itemByteBuffer.put(gameItemVo.getExt52toEnd());
            for (int i = 0; i < itemByteBuffer.array().length; i++) {
                decompress[i+currentIndex] = itemByteBuffer.array()[i];
            }
            currentIndex+=itemByteBuffer.array().length;
        }
        byte[] compress = ZLibUtils.compress(decompress);
        byte[] resultByte = new byte[compress.length+4];
        for (int i = 0; i < startByte.length; i++) {
            resultByte[i] = startByte[i];
        }
        for (int i = 0; i < compress.length; i++) {
            resultByte[i+4] = compress[i];
        }
        return  resultByte;
    }


}
