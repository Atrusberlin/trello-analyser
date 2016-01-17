package de.dranke.trello.analyser.model;

import com.julienvey.trello.domain.Card;

import java.time.Year;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

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
}
