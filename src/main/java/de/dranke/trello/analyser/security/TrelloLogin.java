package de.dranke.trello.analyser.security;

import org.glassfish.jersey.client.oauth1.AccessToken;
import org.glassfish.jersey.client.oauth1.ConsumerCredentials;
import org.glassfish.jersey.client.oauth1.OAuth1AuthorizationFlow;
import org.glassfish.jersey.client.oauth1.OAuth1ClientSupport;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

class TrelloLogin implements Login {

  private ConsumerCredentials consumerCredentials;
  private OAuth1AuthorizationFlow authFlow;
  private String authFlowURL;
  private AccessToken accessToken;
  private Client client;

  @Override
  public void startAuthorization(String authKey, String authSecret, String callbackURL) {
    this.consumerCredentials = new ConsumerCredentials(authKey, authSecret);
    this.authFlow = OAuth1ClientSupport.builder(consumerCredentials)
        .authorizationFlow(
            "https://trello.com/1/OAuthGetRequestToken",
            "https://trello.com/1/OAuthGetAccessToken",
            "https://trello.com/1/OAuthAuthorizeToken?name=TrelloAnalyser&expiration=1hour&scope=read")
        .callbackUri(callbackURL)
        .build();

    this.authFlowURL = authFlow.start();
  }

  @Override
  public void createtClientSession(String oauthVerifier) {
    accessToken = this.authFlow.finish(oauthVerifier);
    client = ClientBuilder.newBuilder()
        .register(this.authFlow.getOAuth1Feature())
        .build();
  }

  public ConsumerCredentials getConsumerCredentials() {
    return consumerCredentials;
  }

  @Override
  public String getAuthFlowURL() {
    return authFlowURL;
  }

  @Override
  public AccessToken getAccessToken() {
    return accessToken;
  }
}
