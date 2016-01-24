package de.dranke.trello.analyser.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrelloCard {

  private String id;
  private String title;
  private List<TrelloAction> actions = new ArrayList<>();
  private Date lastModified;
  private List<TrelloLabel> labels = new ArrayList<>();

  public TrelloCard(String id) {
    this.id = id;
  }

  public TrelloCard() {
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setActions(List<TrelloAction> actions) {
    this.actions = actions;
  }

  public void setLabels(List<TrelloLabel> labels) {
    this.labels = labels;
  }

  public List<TrelloLabel> getLabels() {
    return labels;
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

  public List<TrelloAction> getActions() {
    return actions;
  }

  public void addActions(Collection<TrelloAction> actions) {
    this.actions.addAll(actions);
  }

  public Date getLastModified() {
    return lastModified;
  }

  public void setLastModified(Date lastModified) {
    this.lastModified = lastModified;
  }

  public void addLabels(List<TrelloLabel> labels) {
    this.labels.addAll(labels);
  }
}
