package de.dranke.trello.analyser.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.julienvey.trello.domain.Card;
import com.julienvey.trello.domain.TList;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class AnalysedCardsTest {

  private AnalysedCards analysedCards;

  @BeforeMethod
  public void setUp() throws Exception {
    analysedCards = new AnalysedCards();
  }

  @Test
  public void testGetLabelsForYear() throws Exception {

    final Card card = new Card();
    card.addLabels("Berlin");
//    card.setDateLastActivity(LocalDateTime.now().);

    analysedCards.add(card);

//    assertTrue(analysedCards.getLabelsForYear());
  }

  @Test
  public void count() throws IOException {
    // given
    final ObjectMapper mapper = new ObjectMapper();
    final InputStream resourceAsStream = AnalysedCards.class.getResourceAsStream("List.json");
    final TList tList = mapper.readValue(resourceAsStream, TList.class);

    // when
    final long count = analysedCards.count(tList);

    // then
    System.out.println("Count: " + count);
  }

  @Test
  public void countCards() throws IOException {
    // given
    final ObjectMapper mapper = new ObjectMapper();
    final InputStream resourceAsStream = AnalysedCards.class.getResourceAsStream("Cards.json");
    final TrelloCard[] cards = mapper.readValue(resourceAsStream, TrelloCard[].class);

    // when
    final long count = analysedCards.count(Arrays.asList(cards));

    // then
    System.out.println("Count: " + count);
  }

}