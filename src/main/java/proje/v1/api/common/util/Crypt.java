package proje.v1.api.common.util;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class Crypt {

    public static String hashWithSha256(String originalString){
        return Hashing.sha256().hashString(originalString, StandardCharsets.UTF_8).toString();
    }

}
