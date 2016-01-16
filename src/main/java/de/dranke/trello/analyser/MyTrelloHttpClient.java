package de.dranke.trello.analyser;

import com.julienvey.trello.TrelloHttpClient;

import java.net.URI;

public class MyTrelloHttpClient implements TrelloHttpClient {

  @Override
  public <T> T get(String url, Class<T> objectClass, String... params) {
    return null;
  }

  @Override
  public <T> T postForObject(String url, T object, Class<T> objectClass, String... params) {
    return null;
  }

  @Override
  public URI postForLocation(String url, Object object, String... params) {
    return null;
  }

  @Override
  public <T> T putForObject(String url, T object, Class<T> objectClass, String... params) {
    return null;
  }
}
