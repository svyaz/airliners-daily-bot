package com.github.svyaz.airlinersbot.adapter.client;

import com.github.svyaz.airlinersbot.app.domain.Picture;

public interface AirlinersClient {

    Picture getTopPicture();

    Picture search(String keywords);
}
