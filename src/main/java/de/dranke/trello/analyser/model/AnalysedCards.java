package de.dranke.trello.analyser.model;

import com.julienvey.trello.domain.Card;
import com.julienvey.trello.domain.TList;

import java.time.Instant;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AnalysedCards {

  private Map<Year, Counter> countYear = new HashMap<>();
  private Map<YearMonth, Counter> countMonth = new HashMap<>();
  private Map<Year, Map<Integer, Counter>> countWeek = new HashMap<>();

  private class Counter {

    public final TrelloLabel withoutLabel = new TrelloLabel("none");
    private Map<TrelloLabel, Integer> labelCount;
  }

  public void add(Card card) {

  }

  public Map<TrelloLabel, Integer> getLabelsForYear(Year year) {
    final Counter counter = countYear.get(year);
    return counter.labelCount;
  }

  public long count(TList list) {
    return list.getCards().stream()
//        .filter((card -> convert(card.getDateLastActivity()).getYear() == 2015))
        .count();
  }

  public long count(Collection<TrelloCard> cards) {
    cards.stream()
        .forEach(card -> card.getActions().stream()
            .filter(a -> a.getListAfter() != null && "Fertig".equals(a.getListAfter().getTitle()))
            .sorted((d1, d2) -> d2.getDate().compareTo(d1.getDate()))
            .findFirst()
            .ifPresent(a -> card.setLastModified(a.getDate())));
    return cards.stream()
        .filter(card -> convert(card.getLastModified()).getYear() == 2015)
        .filter(card -> {
          Set<String> colors = card.getLabels().stream().map(l -> l.getColor()).collect(Collectors.toSet());
//          if ( !colors.contains("blue") && !colors.contains("yellow") && !colors.isEmpty()) {
//            System.out.print("Colors" + colors);
//            return true;
//          }
//          return false;
          return
              (colors.contains("yellow") || (colors.contains("red")) && colors.size() == 1) || colors.isEmpty()
//              colors.size() == 1 &&
//              colors.contains("red")
              ;
        })
        .count();
  }

  private static ZonedDateTime convert(Date date) {
    Instant instant = Instant.ofEpochMilli(date.getTime());
    return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
  }
}
