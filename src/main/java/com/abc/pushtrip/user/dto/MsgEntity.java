package com.abc.pushtrip.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MsgEntity {
    private String message;
    private Object data;
}
