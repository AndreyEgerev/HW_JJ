package org.jj.seminar2;

import java.lang.reflect.Field;
import java.time.*;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class RandomDateProcessor {
    public static void RandomDate (Object object) {
        for (Field declaredField : object.getClass().getDeclaredFields()) {
            if (declaredField.isAnnotationPresent(RandomDate.class)) {
                RandomDate randomDate = declaredField.getAnnotation(RandomDate.class);
                long min = randomDate.min();
                long max = randomDate.max();
                long dateUnixTS = ThreadLocalRandom.current().nextLong(min, max);
                System.out.println(dateUnixTS);
                declaredField.setAccessible(true);
                try {
                    if (declaredField.getType().equals(Date.class)) {
                        declaredField.set(object, new Date(dateUnixTS));
                    } else if (declaredField.getType().equals(Instant.class)){
                        declaredField.set(object, Instant.ofEpochMilli(dateUnixTS));
                    } else if (declaredField.getType().equals(LocalDate.class)) {
                        declaredField.set(object,
                                LocalDate.ofInstant(Instant.ofEpochMilli(dateUnixTS), ZoneId.systemDefault()));
                    }
                    System.out.println(declaredField.get(object));
                } catch (IllegalAccessException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }
}
