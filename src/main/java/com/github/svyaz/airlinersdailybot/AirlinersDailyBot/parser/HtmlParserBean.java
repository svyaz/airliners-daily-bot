package com.github.svyaz.airlinersdailybot.AirlinersDailyBot.parser;

import com.github.svyaz.airlinersdailybot.AirlinersDailyBot.errors.ParseException;
import com.github.svyaz.airlinersdailybot.AirlinersDailyBot.model.PictureData;
import com.github.svyaz.airlinersdailybot.AirlinersDailyBot.model.PictureEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HtmlParserBean implements HtmlParser {

    private static final String TOP_PHOTO_LINK_SELECTOR = "div.hp-t5-row-photo a";
    private static final String TITLE_SELECTOR = "h1.photo-details-page-title span";
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
    public PictureEntity getPictureData(String html) {
        var document = Jsoup.parse(html);

        var pictureData = PictureData.builder()
                .title(getTitle(document))
                //.photoPageUri()
                //.airline()
                //.aircraft()
                //.registration()
                //.location()
                //.date()
                //.content()
                //.author()
                //.authorCountry()
                .build();

        return PictureEntity.builder()
                .photoFileUri(getPhotoFileUri(document))
                .pictureData(pictureData)
                .build();
    }

    private String getTitle(Document document) {
        return Optional.of(document)
                .map(d -> d.select(TITLE_SELECTOR))
                .map(Elements::text)
                .orElse(null);
    }

    private String getPhotoFileUri(Document document) {
        return Optional.of(document)
                .map(d -> d.select(PHOTO_LINK_SELECTOR))
                .map(e -> e.attr("src"))
                .orElse(null);
    }
}
