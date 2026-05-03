package com.github.svyaz.airlinersbot.adapter.htmlselector;

import com.github.svyaz.airlinersbot.adapter.exeption.ParseException;
import com.github.svyaz.airlinersbot.adapter.htmlselector.model.PowChallengeData;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

@Component
public class PowChallengeDataResolverBean implements PowChallengeDataResolver {

    @Override
    @SneakyThrows
    public String apply(PowChallengeData pcd) {
        int i = 0;
        var digest = MessageDigest.getInstance("SHA-256");
        var hexFormat = HexFormat.of();

        while (i++ < 100_000) {
            String input = "%s%s%d".formatted(pcd.getChallengeNonce(), pcd.getIssuedAt(), i);
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            String hexHash = hexFormat.formatHex(hash);

            if (hexHash.startsWith(pcd.getDifficultyChar().repeat(pcd.getDifficulty()))) {
                return "%s|%s|%d|%s|%s".formatted(
                        pcd.getChallengeNonce(),
                        pcd.getIssuedAt(),
                        i,
                        hexHash,
                        pcd.getChallengeHmac()
                );
            }
        }

        throw new ParseException("PoW solution not found");
    }

}
