package de.dranke.trello.analyser.model;

import com.julienvey.trello.domain.Label;

public class TrelloLabel {

  public static TrelloLabel create(Label label) {
    final TrelloLabel trelloLabel = new TrelloLabel(null);
    trelloLabel.setName(label.getName());
    trelloLabel.setColor(label.getColor());
    return trelloLabel;
  }

  public TrelloLabel() {
  }

  private String id;
  private String color;
  private String name;

  public TrelloLabel(String id) {
    this.id = id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (!(o instanceof TrelloLabel)) { return false; }

    TrelloLabel that = (TrelloLabel) o;

    if (!id.equals(that.id)) { return false; }

    return true;
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
