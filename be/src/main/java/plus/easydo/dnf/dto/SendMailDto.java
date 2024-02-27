package plus.easydo.dnf.dto;

import lombok.Data;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 发送邮件参数封装
 * @date 2023/10/29
 */
@Data
public class SendMailDto {

    private Long characNo;

    private String title;

    private String content;

    private Long gold = 0L;

    private List<MailItemDto> itemList;
}
