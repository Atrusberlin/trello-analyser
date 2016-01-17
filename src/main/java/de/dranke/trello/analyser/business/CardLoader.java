package de.dranke.trello.analyser.business;

import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.Argument;
import com.julienvey.trello.domain.Card;
import com.julienvey.trello.domain.TList;

import java.util.List;

public class CardLoader {

  private final Trello client;

  public CardLoader(Trello client) {
    this.client = client;
  }

  //  cards=all&card_fields=name,labels
  public List<Card> allCards(String listId) {
    final TList list = client.getList(listId, new Argument("fields", "name"), new Argument("cards", "all"), new Argument("card_fields", "name,labels"));
    return list.getCards();
  }
}
