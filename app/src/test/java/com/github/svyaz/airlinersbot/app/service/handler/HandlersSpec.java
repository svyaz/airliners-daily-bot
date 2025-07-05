package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.SpringSpec;
import com.github.svyaz.airlinersbot.app.service.button.ButtonsService;
import com.github.svyaz.airlinersbot.app.service.message.MessageService;
import com.github.svyaz.airlinersbot.app.service.translate.PictureTranslateService;
import com.github.svyaz.airlinersbot.app.service.user.UserService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

public abstract class HandlersSpec extends SpringSpec {

    @MockitoBean
    MessageService messageService;

    @MockitoBean
    ButtonsService buttonsService;

    @MockitoBean
    PictureTranslateService translateService;

    @MockitoBean
    UserService userService;
}
