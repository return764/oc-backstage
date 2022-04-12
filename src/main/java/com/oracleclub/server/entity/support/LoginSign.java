package com.oracleclub.server.entity.support;

import com.oracleclub.server.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginSign {
    User user;
}
