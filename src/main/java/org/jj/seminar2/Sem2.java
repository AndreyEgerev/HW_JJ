package org.jj.seminar2;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;


public class Sem2 {
    public static void main(String[] args) {

        MyDate date = new MyDate();
        RandomDateProcessor.RandomDate(date);
        System.out.println(date);
    }
    private static class MyDate {
        @RandomDate
        private Date date;
        @RandomDate
        private Instant instantDate;
        @RandomDate
        private LocalDate localDate;


        @Override
        public String toString() {
            return "MyDate" + "\n" +
                    "Date =         " + date + "\n" +
                    "Instant Date = " + instantDate + "\n" +
                    "Local Date =   " + localDate ;
        }
    }
}
