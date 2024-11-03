package com.github.svyaz.airlinersdailybot.parser;

import com.github.svyaz.airlinersdailybot.errors.ParseException;
import com.github.svyaz.airlinersdailybot.model.PictureData;
import com.github.svyaz.airlinersdailybot.model.PictureEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HtmlParserBean implements HtmlParser {

    private static final String TOP_PHOTO_LINK_SELECTOR = "div.hp-t5-row-photo a";

    private static final String SEARCH_FIRST_PHOTO_LINK_SELECTOR = "div.ps-v2-results-photo a";
    private static final String SEARCH_NEXT_PAGE_URI_SELECTOR = "div.pdcp-pager.pdcp-pager-next a";

    private static final String PHOTO_PAGE_URI_SELECTOR = "meta[property='og:url']";
    private static final String AIRLINE_SELECTOR = "div.pib-section-content-left a[href*=airline]";
    private static final String AIRCRAFT_SELECTOR = "div.pib-section-content-left a[href*=aircraft]";
    private static final String REGISTRATION_SELECTOR = "div.pib-section-content-right a[href*=registration]";
    private static final String LOCATION_SELECTOR = "div.pib-section-location-date a[href*=location]";
    private static final String DATE_SELECTOR = "div.pib-section-location-date a[href*=datePhotographed]";
    private static final String CONTENT_SELECTOR = "div.pib-section-caption div.pib-section-content";
    private static final String AUTHOR_SELECTOR = "div.pib-section-photographer a.ua-name-content";
    private static final String AUTHOR_COUNTRY_SELECTOR = "div.pib-section-photographer span.ua-country";
    private static final String PHOTO_FILE_URI_SELECTOR = "div.pdp-image-wrapper img";

    @Override
    public String getLargePicturePageUri(String html) {
        return Optional.ofNullable(Jsoup.parse(html)
                        .select(TOP_PHOTO_LINK_SELECTOR)
                        .first())
                .map(e -> e.attr("href"))
                .orElseThrow(() -> new ParseException("Top photo link not found"));
    }

    @Override
    public String getFirstSearchResultUri(String html) {
        return Optional.ofNullable(Jsoup.parse(html)
                        .select(SEARCH_FIRST_PHOTO_LINK_SELECTOR)
                        .first())
                .map(e -> e.attr("href"))
                .orElseThrow(() -> new ParseException("Pictures by keywords not found"));
    }

    @Override
    public PictureEntity getPictureData(String html) {
        var document = Jsoup.parse(html);

        var pictureData = PictureData.builder()
                .photoPageUri(selectFromAttribute(document, PHOTO_PAGE_URI_SELECTOR, "content"))
                .airline(selectFromElementValue(document, AIRLINE_SELECTOR))
                .aircraft(selectFromElementValue(document, AIRCRAFT_SELECTOR))
                .registration(selectFromElementValue(document, REGISTRATION_SELECTOR))
                .location(selectFromElementValue(document, LOCATION_SELECTOR))
                .date(selectFromElementValue(document, DATE_SELECTOR))
                .content(selectFromElementValue(document, CONTENT_SELECTOR))
                .author(selectFromElementValue(document, AUTHOR_SELECTOR))
                .authorCountry(selectFromElementValue(document, AUTHOR_COUNTRY_SELECTOR))
                .build();

        return PictureEntity.builder()
                .photoFileUri(selectFromAttribute(document, PHOTO_FILE_URI_SELECTOR, "src"))
                .nextPageUri(selectFromAttribute(document, SEARCH_NEXT_PAGE_URI_SELECTOR, "href"))
                .pictureData(pictureData)
                .build();
    }

    private String selectFromElementValue(Document document, String query) {
        return Optional.of(document)
                .map(d -> d.select(query))
                .map(Elements::text)
                .orElse(null);
    }

    private String selectFromAttribute(Document document, String query, String attrName) {
        return Optional.ofNullable(document)
                .map(d -> d.select(query))
                .map(e -> e.attr(attrName))
                .orElse(null);
    }
}
