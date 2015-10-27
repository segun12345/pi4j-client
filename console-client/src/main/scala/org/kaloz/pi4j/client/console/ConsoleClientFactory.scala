package org.kaloz.pi4j.client.console

import akka.actor.ActorSystem
import com.typesafe.scalalogging.StrictLogging
import org.jnativehook.GlobalScreen
import org.kaloz.pi4j.client.actor.{LocalInputPinStateChangedListenerActor, InMemoryClientActor}
import org.kaloz.pi4j.client.factory.ClientFactory

class ConsoleClientFactory extends ClientFactory with StrictLogging {

  logger.info("Initialised...")

  private val system = ActorSystem("console-actor-system")
  private val consoleClientActor = system.actorOf(InMemoryClientActor.props(ConsoleInputPinStateChangeListenerActor.factory), "consoleClientActor")

  system.actorOf(LocalInputPinStateChangedListenerActor.props, "pinStateChangeListenerActor")

  val gpio = new ConsoleGpio(consoleClientActor)
  val gpioUtil = new ConsoleGpioUtil(consoleClientActor)
  val gpioInterrupt = new ConsoleGpioInterrupt(consoleClientActor)

  def shutdown(): Unit = {
    GlobalScreen.unregisterNativeHook()
    system.terminate()
  }

}
