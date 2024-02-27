package plus.easydo.dnf.qo;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


/**
 *  角色信息。
 *
 * @author yuzhanfeng
 * @since 2023-10-14
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class CharacInfoQo extends PageQo{

    private Integer mId;

    private Integer characNo;

    private String characName;

    private Boolean online;

}
