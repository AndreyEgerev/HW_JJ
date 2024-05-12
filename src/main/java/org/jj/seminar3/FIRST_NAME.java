package org.jj.seminar3;
import java.util.concurrent.ThreadLocalRandom;

public enum FIRST_NAME {
    Tom,
    Jane,
    Andrey,
    Ivan,
    Fedor;

    public static String getRandomName() {
        FIRST_NAME[] names = FIRST_NAME.values();
        return names[ThreadLocalRandom.current().nextInt(names.length)].name();
    }
}
