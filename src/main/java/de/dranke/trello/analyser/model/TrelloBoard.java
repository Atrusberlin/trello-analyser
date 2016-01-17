package de.dranke.trello.analyser.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrelloBoard {

  private final String id;
  private String title;
  private List<TrelloList> lists;

  public TrelloBoard(String id) {
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

  public List<TrelloList> getLists() {
    return lists;
  }

  public void setLists(List<TrelloList> lists) {
    this.lists = lists;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (!(o instanceof TrelloBoard)) { return false; }

    TrelloBoard that = (TrelloBoard) o;

    if (!id.equals(that.id)) { return false; }

    return true;
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
