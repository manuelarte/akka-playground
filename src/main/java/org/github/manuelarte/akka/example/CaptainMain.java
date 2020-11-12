package org.github.manuelarte.akka.example;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.util.UUID;

public class CaptainMain extends AbstractBehavior<CaptainMain.SendOrder> {

    @lombok.AllArgsConstructor
    public static class SendOrder {
        public final String order;
    }

    private final ActorRef<ArmyMessage> captain;

    public static Behavior<SendOrder> create() {
        return Behaviors.setup(CaptainMain::new);
    }

    private CaptainMain(ActorContext<SendOrder> context) {
        super(context);
        captain = context.spawn(Captain.create(), context.getSelf().path().address().system());
    }

    @Override
    public Receive<SendOrder> createReceive() {
        return newReceiveBuilder().onMessage(SendOrder.class, this::onOrderReceived).build();
    }

    private Behavior<SendOrder> onOrderReceived(final SendOrder command) {
        ActorRef<ArmyMessage> soldierOrderReceiver = getContext().spawn(SlowSoldierMain.create(), UUID.randomUUID().toString());
        captain.tell(new ArmyMessage.Order(command.order, captain, soldierOrderReceiver));
        return this;
    }
}
