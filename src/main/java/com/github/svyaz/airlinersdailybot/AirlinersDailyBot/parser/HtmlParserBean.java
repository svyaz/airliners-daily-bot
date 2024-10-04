package com.github.svyaz.airlinersdailybot.AirlinersDailyBot.parser;

import com.github.svyaz.airlinersdailybot.AirlinersDailyBot.errors.ParseException;
import com.github.svyaz.airlinersdailybot.AirlinersDailyBot.model.db.PictureEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HtmlParserBean implements HtmlParser {

    private static final String TOP_PHOTO_LINK_SELECTOR = "div.hp-t5-row-photo a";
    private static final String ID_SELECTOR = "h1.photo-details-page-title span";
    private static final String PHOTO_LINK_SELECTOR = "div.pdp-image-wrapper img";


    @Override
    public String getLargePicturePageUri(String html) {
        return Optional.ofNullable(Jsoup.parse(html)
                        .select(TOP_PHOTO_LINK_SELECTOR)
                        .first())
                .map(e -> e.attr("href"))
                .orElseThrow(() -> new ParseException("Top photo link not found"));
    }

    @Override   //todo get rest fields
    public PictureEntity getTarget(String html) {
        var document = Jsoup.parse(html);

        return PictureEntity.builder()
                .title(getTitle(document))
                .photoLink(getPhotoLink(document))
                .build();
    }

    private String getTitle(Document document) {
        return Optional.of(document)
                .map(d -> d.select(ID_SELECTOR))
                .map(Elements::text)
                .orElse(null);
    }

    private String getPhotoLink(Document document) {
        return Optional.of(document)
                .map(d -> d.select(PHOTO_LINK_SELECTOR))
                .map(e -> e.attr("src"))
                .orElse(null);
    }
}
