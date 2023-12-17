package plus.easydo.dnf.enums;

import java.util.Objects;

/**
 * 副职业枚举
 *
 * @author laoyu
 * @date 2023/10/15
 */
public enum ExpertJobJobEnum {

    NO_JOB(0,"无职业"),
    ENCHANTER(1,"附魔师"),
    ALCHEMY(2,"炼金术师"),
    DECOMPOSE(3,"分解师"),
    DECOMPOSER(4,"控偶师");

    ExpertJobJobEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    private final Integer code;

    private final String name;

    public static String getNameByCode(Integer jobCode){
        for(ExpertJobJobEnum expertJobJobEnum: values()){
            if (Objects.equals(expertJobJobEnum.code, jobCode)){
                return expertJobJobEnum.name;
            }
        }
        return "";
    }

}
