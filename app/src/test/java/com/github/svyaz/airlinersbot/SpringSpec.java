package com.github.svyaz.airlinersbot;

import com.github.svyaz.airlinersbot.adapter.bot.AirlinersBot;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(properties = {
        "spring.config.location=file:config/application-test.yaml",
        "spring.profiles.active=test",
        "bot.token=XYZ"
})
public abstract class SpringSpec {

    @MockitoBean
    AirlinersBot airlinersBot;

}
