package plus.easydo.dnf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName accounts
 */
@TableName(value ="accounts")
@Data
public class Accounts implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer uid;

    /**
     * 
     */
    private String accountname;

    /**
     * 
     */
    private String password;

    /**
     * 
     */
    private String qq;

    /**
     * 
     */
    private Integer dzuid;

    /**
     * 
     */
    private Integer billing;

    /**
     * 
     */
    private String vip;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}