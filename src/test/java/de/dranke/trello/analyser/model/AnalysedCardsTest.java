package de.dranke.trello.analyser.model;

import com.julienvey.trello.domain.Card;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

import static org.testng.Assert.assertTrue;

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
    card.setDateLastActivity(LocalDateTime.now().);

    analysedCards.add(card);

    assertTrue(analysedCards.getLabelsForYear());
  }
}