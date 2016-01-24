package de.dranke.trello.analyser.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrelloAction {

  private String id;
  private Date date;
  private TrelloList listAfter;

  public TrelloAction(String id, Date date) {
    this.id = id;
    this.date = date;
  }

  public TrelloAction() {
  }

  public void setListAfter(TrelloList listAfter) {
    this.listAfter = listAfter;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public TrelloList getListAfter() {
    return listAfter;
  }

  public String getId() {
    return id;
  }

  public Date getDate() {
    return date;
  }
}
