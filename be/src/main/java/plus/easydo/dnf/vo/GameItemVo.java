package plus.easydo.dnf.vo;

import lombok.Data;

/**
 * @author laoyu
 * @version 1.0
 * @description 格子装物品解析参数对象
 * @date 2023/12/27
 */
@Data
public class GameItemVo {

    private int index;

    private int sealType;

    private int itemType;

    private int itemId;

    private String itemName;

    private int upgrade;

    private int quality;

    private int durability;

    private int enchantment;

    private int increaseType;

    private int increaseLevel;

    private int otherworldly;

    private int forgeLevel;

    private byte[] ext20to30;
    private byte[] ext33to36;
    private byte[] ext37to50;
    private byte[] ext52toEnd;


}
