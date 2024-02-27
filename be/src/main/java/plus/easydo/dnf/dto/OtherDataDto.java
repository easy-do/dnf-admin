package plus.easydo.dnf.dto;

import lombok.Data;

/**
 * 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
public class OtherDataDto {

    private Long characNo;

    /**胜利场次*/
    private Integer win;

    /**胜点*/
    private Integer pvpPoint;

    /**段位*/
    private Integer pvpGrade;

    /**金币*/
    private Long money;

    /**sp点数*/
    private Integer sp;

    /**tp点数*/
    private Integer tp;

    /**qp点数*/
    private Integer qp;

    /**时装币*/
    private Integer avatarCoin;

    private OtherDataDto oldData;

}
