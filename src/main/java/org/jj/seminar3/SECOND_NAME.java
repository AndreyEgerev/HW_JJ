package org.jj.seminar3;

import java.util.concurrent.ThreadLocalRandom;

public enum SECOND_NAME {
    Tayer,
    Air,
    Sidorov,
    Ivanov,
    Petrov;

    public static String getRandomName() {
        SECOND_NAME[] names = SECOND_NAME.values();
        return names[ThreadLocalRandom.current().nextInt(names.length)].name();
    }
}
