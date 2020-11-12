package org.github.manuelarte.akka.example;

public interface Soldier {

    String getName();

    @lombok.Data
    class Slow implements Soldier {
        private final String name;
    }
}
