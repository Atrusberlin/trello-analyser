package de.dranke.trello.analyser.business;

import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.Action;
import com.julienvey.trello.domain.Argument;
import com.julienvey.trello.domain.TList;
import de.dranke.trello.analyser.model.TrelloAction;
import de.dranke.trello.analyser.model.TrelloCard;
import de.dranke.trello.analyser.model.TrelloLabel;
import de.dranke.trello.analyser.model.TrelloList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class CardLoader {

  private final Trello client;

  private static final Logger log = LoggerFactory.getLogger(CardLoader.class);

  public CardLoader(Trello client) {
    this.client = client;
  }

  //  cards=all&card_fields=name,labels
  public List<TrelloCard> allCards(String listId) {
    final TList list = client.getList(listId, new Argument("fields", "name"), new Argument("cards", "all"), new Argument("card_fields", "dateLastActivity,labels,name"));
    final List<TrelloCard> cards = list.getCards().parallelStream().map((source) -> {
      final TrelloCard target = new TrelloCard(source.getId());
      target.setTitle(source.getName());
      target.setLastModified(source.getDateLastActivity());
      target.addLabels(source.getLabels().stream().map(TrelloLabel::create).collect(Collectors.toList()));
      log.debug("load actions from Card ({} - {})", source.getName(), source.getId());
//      try {
//        source.setInternalTrello(client);
//        target.addActions(source.getActions().stream()
//                              .map((a) -> {
//                                TrelloAction action = null;
//                                try {
//                                  action = new TrelloAction(a.getId(), a.getDate());
//                                  action.setListAfter(mapAfter(a));
//                                }
//                                catch (Exception e) {
//                                  log.error("error at action {} {}", a.getId(), e.getMessage());
//                                }
//                                return action;
//                              }).collect(Collectors.toList()));
//      }
//      catch (Exception e) {
//        log.error("error at action {} {}", source.getId(), e.getMessage());
//      }
      return target;
    }).collect(Collectors.toList());
    cards.forEach((card) -> {
      card.addActions(client.getCardActions(card.getId())
                          .parallelStream()
                          .map((source) -> {
                            TrelloAction action = null;
                            try {
                              action = new TrelloAction(source.getId(), source.getDate());
                              action.setListAfter(mapAfter(source));
                            }
                            catch (Exception e) {
                              log.error("error at action {} {}", source.getId(), e.getMessage());
                            }
                            return action;
                          })
                          .collect(Collectors.toList()));
      log.debug("load actions from Card ({} - {})", card.getTitle(), card.getId());
    });
    return cards;
  }

  private TrelloList mapAfter(Action source) {
    final Action.Data data = source.getData();
    TrelloList target = null;
    if (data != null && data.getListAfter() != null) {
      target = new TrelloList(data.getListAfter().getId());
      target.setTitle(data.getListAfter().getName());
    }
    return target;
  }
}
