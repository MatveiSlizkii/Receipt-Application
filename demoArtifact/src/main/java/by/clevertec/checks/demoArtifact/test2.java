package by.clevertec.checks.demoArtifact;

import java.util.UUID;

public class test2 {
    public static void main(String[] args) {
        String str = "123";
        int a = 0;
        UUID uuid = null;

        try {
            a = Integer.parseInt(str);
        } catch (NumberFormatException e){
            try {
                uuid = UUID.fromString(str);
            } catch (IllegalArgumentException il){

            }
        }

    }
}
