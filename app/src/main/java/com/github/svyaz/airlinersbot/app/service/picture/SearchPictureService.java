package com.github.svyaz.airlinersbot.app.service.picture;

import com.github.svyaz.airlinersbot.app.domain.Picture;
import com.github.svyaz.airlinersbot.app.domain.User;

public interface SearchPictureService {

    Picture search(User user, String keywords);

    Picture next(User user);
}
