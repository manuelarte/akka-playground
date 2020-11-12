package org.github.manuelarte.akka.example;

import akka.actor.typed.ActorRef;

public interface ArmyMessage {

    @lombok.Data
    class Order implements ArmyMessage {
        public final String command;
        public final ActorRef<ArmyMessage> from;
        public final ActorRef<ArmyMessage> sendOrderTo;
    }

    interface SoldierResponse extends ArmyMessage {
    }

    @lombok.Data
    class OrderFinished implements SoldierResponse {
        public final String command;
        public final ActorRef<ArmyMessage> from;
    }

}
