package com.github.svyaz.airlinersbot.adapter.htmlselector;

import com.github.svyaz.airlinersbot.app.domain.Picture;

public interface HtmlSelector {

    String getTopPicturePageUri(String html);

    Picture getTopPicture(String html);
}
