package com.github.svyaz.airlinersbot.app.service;

import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.domain.request.Request;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.InlineButton;
import com.github.svyaz.airlinersbot.app.domain.response.TextResponse;
import com.github.svyaz.airlinersbot.datastore.service.UserStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.github.svyaz.airlinersbot.conf.properties.Constants.SHOW_TOP_CB_DATA;

@Service
@RequiredArgsConstructor
public class StartHandlerBean extends AbstractRequestHandler<TextResponse> {

    private final UserStorageService userStorageService;

    @Override
    public RequestType myType() {
        return RequestType.START;
    }

    @Override
    public TextResponse handle(Request request) {
        //todo : UserService in app.
        var user = userStorageService.findUser(request.user().getId())
                .orElseGet(() ->
                        User.builder()
                                .id(request.user().getId())
                                .firstName(request.user().getFirstName())
                                .lastName(request.user().getLastName())
                                .userName(request.user().getUserName())
                                .languageCode(request.user().getLanguageCode())
                                .registerTime(LocalDateTime.now())
                                .build()
                );

        userStorageService.save(user);

        return new TextResponse(
                user.getId(),
                Optional.ofNullable(user.getFirstName())
                        .map(name -> getLocalizedMessage("start.text-with-name", name))
                        .orElseGet(() -> getLocalizedMessage("start.text")),
                List.of(
                        List.of(
                                new InlineButton(
                                        SHOW_TOP_CB_DATA,
                                        getLocalizedMessage("button.show-top")
                                )
                        )
                ));
    }
}
