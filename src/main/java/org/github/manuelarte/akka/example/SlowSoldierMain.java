package org.github.manuelarte.akka.example;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class SlowSoldierMain extends AbstractBehavior<ArmyMessage> {

    public static Behavior<ArmyMessage> create() {
        return Behaviors.setup(SlowSoldierMain::new);
    }

    private final Soldier soldier;

    private SlowSoldierMain(ActorContext<ArmyMessage> context) {
        super(context);
        this.soldier = new Soldier.Slow("Manuel");
    }

    @Override
    public Receive<ArmyMessage> createReceive() {
        return newReceiveBuilder().onMessage(ArmyMessage.class, this::onOrderReceived).build();
    }

    private Behavior<ArmyMessage> onOrderReceived(ArmyMessage message) {
        if (message instanceof ArmyMessage.Order) {
            final ArmyMessage.Order order = (ArmyMessage.Order) message;
            getContext().getLog().info("Solider {}: Yes sir!", soldier.getName());
            getContext().getLog().info("(Solider {} starts {})", soldier.getName(), order.command);

            onOrderFinished(order);
        }
        return this;
    }

    private Behavior<ArmyMessage> onOrderFinished(ArmyMessage.Order order) {
        getContext().getLog().info("(Solider {} finishes {})", soldier.getName(), order.command);
        order.from.tell(new ArmyMessage.OrderFinished(order.command, order.sendOrderTo));
        return this;
    }
}
