package com.oracleclub.server.entity.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class VerifyCodeLoginParam {

    @NotBlank(message = "邮箱不能为空")
    @Size(max = 255, message = "邮箱的字符长度不能超过 {max}")
    private String email;

    @NotBlank(message = "验证码不能为空")
    @Size(min = 6, max = 6, message = "验证码必须是{max}位")
    private String verifyCode;
}
