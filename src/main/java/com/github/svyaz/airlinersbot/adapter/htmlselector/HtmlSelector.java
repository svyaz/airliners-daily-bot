package com.github.svyaz.airlinersbot.adapter.htmlselector;

import com.github.svyaz.airlinersbot.app.domain.Picture;

public interface HtmlSelector {

    String getTopPicturePageUri(String html);

    Picture getPicture(String html);

    String getFirstSearchResultUri(String html);
}
