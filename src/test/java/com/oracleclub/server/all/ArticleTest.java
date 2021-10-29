package com.oracleclub.server.all;

import com.alibaba.fastjson.JSON;
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
 * @date :2021/9/20 9:08
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ArticleTest {

    @Autowired
    MockMvc mock;

    final String URL = "api/admin/articles";

    @Test
    void ping() throws Exception {
        String respString = mock.perform(MockMvcRequestBuilders.get(URL+"/1426171475689345024"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        R result = JSON.parseObject(respString, R.class);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getResult(),"ok");
    }
}
