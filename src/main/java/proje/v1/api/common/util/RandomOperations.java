package proje.v1.api.common.util;

import java.util.Random;

public class RandomOperations {

    private final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private Random random = new Random();

    public String generateRandomString(byte size){
        StringBuilder stringBuilder = new StringBuilder();
        for(int count = 0 ; count < size ; count++){
            stringBuilder.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        return stringBuilder.toString();
    }
}
