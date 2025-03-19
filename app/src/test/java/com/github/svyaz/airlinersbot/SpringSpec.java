package com.github.svyaz.airlinersbot;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.config.location=file:config/application-test.yaml",
        "spring.profiles.active=test",
        "bot.token=XYZ"
})
public abstract class SpringSpec {
}
