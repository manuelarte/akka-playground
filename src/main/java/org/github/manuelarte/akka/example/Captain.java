package org.github.manuelarte.akka.example;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class Captain extends AbstractBehavior<ArmyMessage> {

  public static Behavior<ArmyMessage> create() {
    return Behaviors.setup(Captain::new);
  }

  private Captain(ActorContext<ArmyMessage> context) {
    super(context);
  }

  @Override
  public Receive<ArmyMessage> createReceive() {
    return newReceiveBuilder().onMessage(ArmyMessage.class, this::onOrder).build();
  }

  private Behavior<ArmyMessage> onOrder(final ArmyMessage message) {
    if (message instanceof ArmyMessage.Order) {
      final ArmyMessage.Order command = (ArmyMessage.Order) message;
      getContext().getLog().info("Captain {}: Solider {}!", getContext().getSelf().path().name(), command.command);
      command.sendOrderTo.tell(command);
    }

    if (message instanceof ArmyMessage.OrderFinished) {
      final ArmyMessage.OrderFinished order = (ArmyMessage.OrderFinished) message;
      getContext().getLog().info("Captain {}: Well done solider!", getContext().getSelf().path().name());
    }
    return this;
  }
}

