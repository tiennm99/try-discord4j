package com.miti99;

import discord4j.core.DiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

public class Main {

  public static void main(String[] args) {
    var token = System.getenv("DISCORD4J_TOKEN"); // Replace with your own token
    var client = DiscordClient.create(token);
    var login =
        client.withGateway(
            gateway -> {
              var printOnLogin =
                  gateway
                      .on(
                          ReadyEvent.class,
                          event ->
                              Mono.fromRunnable(
                                  () -> {
                                    var self = event.getSelf();
                                    System.out.printf(
                                        "Logged in as %s#%s%n",
                                        self.getUsername(), self.getDiscriminator());
                                  }))
                      .then();

              var handlePingCommand =
                  gateway
                      .on(
                          MessageCreateEvent.class,
                          event -> {
                            var message = event.getMessage();

                            if (message.getContent().equalsIgnoreCase("!ping")) {
                              return message
                                  .getChannel()
                                  .flatMap(channel -> channel.createMessage("pong!"));
                            }

                            return Mono.empty();
                          })
                      .then();

              return printOnLogin.and(handlePingCommand);
            });
    login.block();
  }
}
