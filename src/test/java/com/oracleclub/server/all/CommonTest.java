package com.oracleclub.server.all;

import cn.hutool.json.JSONUtil;
import com.oracleclub.server.entity.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author :RETURN
 * @date :2021/9/20 9:18
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CommonTest {

    @Autowired
    private MockMvc mock;

    @Test
    void ping() throws Exception {
        String respString = mock.perform(MockMvcRequestBuilders.get("/ping"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        R result = JSONUtil.toBean(respString, R.class);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getMsg(),"pong");
    }
}
