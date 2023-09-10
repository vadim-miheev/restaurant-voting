package ru.topjava.restaurant_voting.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.topjava.restaurant_voting.util.JsonUtil;

import java.sql.SQLException;

@Configuration
@Slf4j
public class AppConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    Server h2Server() throws SQLException {
        log.info("Start H2 TCP server");
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }

    @Autowired
    private void setMapper(ObjectMapper objectMapper) {
        JsonUtil.setMapper(objectMapper);
    }
}
