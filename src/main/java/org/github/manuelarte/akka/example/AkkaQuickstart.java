package org.github.manuelarte.akka.example;

import akka.actor.typed.ActorSystem;

import java.io.IOException;

public class AkkaQuickstart {
  public static void main(String[] args) {
    final ActorSystem<CaptainMain.SendOrder> captainMain = ActorSystem.create(CaptainMain.create(), "jamesTKick");
    captainMain.tell(new CaptainMain.SendOrder("run"));
    try {
      System.out.println(">>> Press ENTER to exit <<<");
      System.in.read();
    } catch (final IOException ignored) {
    } finally {
      captainMain.terminate();
    }
  }
}
