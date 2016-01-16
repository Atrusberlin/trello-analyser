package de.dranke.trello.analyser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.julienvey.trello.domain.Member;
import com.julienvey.trello.impl.TrelloImpl;
import com.julienvey.trello.impl.http.ApacheHttpClient;
import de.dranke.trello.analyser.security.SecurityManager;
import org.slf4j.Logger;

import java.io.File;

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

    String www = new File("www").getAbsolutePath();
    LOGGER.info("Read static files from: {}", www);
    externalStaticFileLocation(www);

    final SecurityManager securityManager = SecurityManager.getInstance();
    securityManager.configureHttps(new File(System.getProperty("keystore.file")), System.getProperty("keystore.pw"));
    securityManager.configureSecurity();

    get(APP_PATH + "/boards", (req, res) -> {
//      final RestTemplateHttpClient httpClient = new RestTemplateHttpClient();
      res.type("application/json");
      final ObjectMapper objectMapper = new ObjectMapper();
      final TrelloImpl trello = new TrelloImpl(securityManager.getApplicationKey(), securityManager.getLogin(req).getAccessToken().getToken(), new ApacheHttpClient());
      final Member me = trello.getMemberInformation("me");
      return objectMapper.writeValueAsString(me);
    });
  }
}
