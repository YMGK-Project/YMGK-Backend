package proje.v1.api.base.util;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class Crypt {

    public static String hashWithSha256(String originalString){
        return Hashing.sha256().hashString(originalString, StandardCharsets.UTF_8).toString();
    }
}
