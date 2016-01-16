package de.dranke.trello.analyser.security;

import org.glassfish.jersey.client.oauth1.AccessToken;

public interface Login {

  void startAuthorization(String authKey, String authSecret, String callbackURL);

  void createtClientSession(String oauthVerifier);

  String getAuthFlowURL();

  AccessToken getAccessToken();
}
