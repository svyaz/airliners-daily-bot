package com.github.svyaz.airlinersbot.adapter.htmlselector.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PowChallengeData {

    @JsonProperty(value = "challenge_nonce")
    private String challengeNonce;

    @JsonProperty(value = "challenge_hmac")
    private String challengeHmac;

    @JsonProperty(value = "difficulty")
    private Integer difficulty;

    @JsonProperty(value = "difficulty_char")
    private String difficultyChar;

    @JsonProperty(value = "issued_at")
    private String issuedAt;

    @JsonProperty(value = "cookie_duration")
    private String cookieDuration;

}
