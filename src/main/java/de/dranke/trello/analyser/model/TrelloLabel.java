package de.dranke.trello.analyser.model;

public class TrelloLabel {

  private final String id;
  private String color;
  private String name;

  public TrelloLabel(String id) {
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
