package com.github.svyaz.airlinersbot.adapter.htmlselector;

import com.github.svyaz.airlinersbot.adapter.exeption.PictureNotFoundException;
import com.github.svyaz.airlinersbot.app.domain.Picture;
import com.github.svyaz.airlinersbot.adapter.exeption.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

@Component
public class HtmlSelectorBean implements HtmlSelector {

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
    public String getTopPicturePageUri(String html) {
        return Optional.ofNullable(Jsoup.parse(html)
                        .select(TOP_PHOTO_LINK_SELECTOR)
                        .first())
                .map(e -> e.attr("href"))
                .orElseThrow(() -> new ParseException("Top photo link not found"));
    }

    @Override
    public Picture getPicture(String html) {
        var document = Jsoup.parse(html);

        var photoPageUri = selectFromAttribute(document, PHOTO_PAGE_URI_SELECTOR, "content");

        return Picture.builder()
                .id(getPictureId(photoPageUri))
                .photoFileUri(selectFromAttribute(document, PHOTO_FILE_URI_SELECTOR, "src"))
                .nextPageUri(selectFromAttribute(document, SEARCH_NEXT_PAGE_URI_SELECTOR, "href"))
                .photoPageUri(photoPageUri)
                .airline(selectFromElementValue(document, AIRLINE_SELECTOR))
                .aircraft(selectFromElementValue(document, AIRCRAFT_SELECTOR))
                .registration(selectFromElementValue(document, REGISTRATION_SELECTOR))
                .location(selectFromElementValue(document, LOCATION_SELECTOR))
                .date(selectFromElementValue(document, DATE_SELECTOR))
                .content(selectFromElementValue(document, CONTENT_SELECTOR))
                .author(selectFromElementValue(document, AUTHOR_SELECTOR))
                .authorCountry(selectFromElementValue(document, AUTHOR_COUNTRY_SELECTOR))
                .updateTime(LocalDateTime.now())
                .build();
    }

    @Override
    public String getFirstSearchResultUri(String html) {
        return Optional.ofNullable(Jsoup.parse(html)
                        .select(SEARCH_FIRST_PHOTO_LINK_SELECTOR)
                        .first())
                .map(e -> e.attr("href"))
                .orElseThrow(() -> new PictureNotFoundException("Pictures by keywords not found"));
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
                .filter(Predicate.not(String::isEmpty))
                .orElse(null);
    }

    private Long getPictureId(String raw) {
        var pattern = Pattern.compile("\\d+$");
        var matcher = pattern.matcher(raw);

        return matcher.find() ?
                Long.parseLong(matcher.group(0)) :
                null;
    }

}
