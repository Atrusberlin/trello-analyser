package de.dranke.trello.analyser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.julienvey.trello.impl.TrelloImpl;
import com.julienvey.trello.impl.http.AsyncTrelloHttpClient;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import de.dranke.trello.analyser.business.BoardLoader;
import de.dranke.trello.analyser.business.CardLoader;
import de.dranke.trello.analyser.model.TrelloCard;
import de.dranke.trello.analyser.security.SecurityManager;
import org.slf4j.Logger;

import java.io.File;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static spark.Spark.get;
import static spark.SparkBase.externalStaticFileLocation;

/**
 * Created by Daniel Ranke on 12.01.2016.
 */
public class Application {

  public static final Logger LOGGER = getLogger(Application.class);
  public static final String APP_PATH = "/trello/analyse";

  public static void main(String[] args) {
    final ObjectMapper objectMapper = new ObjectMapper();

    String www = new File("www").getAbsolutePath();
    LOGGER.info("Read static files from: {}", www);
    externalStaticFileLocation(www);

    final SecurityManager securityManager = SecurityManager.getInstance();
    securityManager.configureHttps(new File(System.getProperty("keystore.file")), System.getProperty("keystore.pw"));
    securityManager.configureSecurity();

    get(APP_PATH + "/boards", (req, res) -> {
//      final RestTemplateHttpClient httpClient = new RestTemplateHttpClient();
      res.type("application/json; charset=utf-8");
      final TrelloImpl trello = new TrelloImpl(securityManager.getApplicationKey(), securityManager.getLogin(req).getAccessToken().getToken(), new AsyncTrelloHttpClient());
      final BoardLoader boardLoader = new BoardLoader(trello);
      return objectMapper.writeValueAsString(boardLoader.load());
    });

    get(APP_PATH + "/lists/:listId/cards", (req, res) -> {
      res.type("application/json; charset=utf-8");
      final String listId = req.params(":listId");
      final AsyncHttpClientConfig.Builder builder = new AsyncHttpClientConfig.Builder();
      final AsyncHttpClientConfig config = builder.setMaxConnections(100).setIOThreadMultiplier(20).build();
      final TrelloImpl trello = new TrelloImpl(securityManager.getApplicationKey(), securityManager.getLogin(req).getAccessToken().getToken(), new AsyncTrelloHttpClient(new AsyncHttpClient(config)));
      final CardLoader cardLoader = new CardLoader(trello);
      final List<TrelloCard> cards = cardLoader.allCards(listId);
      return objectMapper.writeValueAsString(cards);
    });
  }
}
