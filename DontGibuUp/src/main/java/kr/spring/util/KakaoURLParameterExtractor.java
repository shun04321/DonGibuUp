package kr.spring.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KakaoURLParameterExtractor {
    public static String extractRcode(String url) {
        String rcode = null;
        Pattern pattern = Pattern.compile("rcode=([A-Za-z0-9]{8})");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            rcode = matcher.group(1);
        }
        return rcode;
    }
}
