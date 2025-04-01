package com.hb.api.dto;

import lombok.Data;

import java.util.List;

/**
 * 回调请求对象
 */
@Data
public class NotifyRequestDTO {

    /** 组队ID */
    private String teamId;
    /** 外部单号 */
    private List<String> outTradeNoList;

}
