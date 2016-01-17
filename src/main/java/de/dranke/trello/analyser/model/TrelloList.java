package de.dranke.trello.analyser.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrelloList {

  private final String id;
  private String title;

  public TrelloList(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (!(o instanceof TrelloList)) { return false; }

    TrelloList that = (TrelloList) o;

    if (!id.equals(that.id)) { return false; }

    return true;
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
