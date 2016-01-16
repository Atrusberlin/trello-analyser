package de.dranke.trello.analyser.security;

import org.slf4j.Logger;
import spark.Request;
import spark.Session;
import spark.Spark;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static org.slf4j.LoggerFactory.getLogger;
import static spark.Spark.get;
import static spark.SparkBase.secure;

public class SecurityManager {

  public static final Logger LOGGER = getLogger(SecurityManager.class);

  public static final String APP_PATH = "/trello/analyse";
  public static final String callbackURL = "https://" +
                                           System.getProperty("server.domain") + ":" +
                                           System.getProperty("server.port") + APP_PATH + "/createSession";

  private static final SecurityManager INSTANCE = new SecurityManager();

  public static SecurityManager getInstance() {
    return INSTANCE;
  }

  private SecurityManager() {
  }

  public void configureHttps(File keystore, String keystorePW) {
    secure(keystore.getAbsolutePath(), keystorePW, null, null);
  }

  public void configureSecurity() {
    enableSecurityChain();

    get(APP_PATH + "/login", (req, res) -> {
      final TrelloLogin trelloLogin = new TrelloLogin();
      LOGGER.debug("start authorization");
      trelloLogin.startAuthorization(getApplicationKey(), System.getProperty("trello.secret"), callbackURL);
      final Session session = req.session(true);
      session.raw().setAttribute("trelloLogin", trelloLogin);
      res.redirect(trelloLogin.getAuthFlowURL());
      return "";
    });

    get(APP_PATH + "/createSession", (req, res) -> {
      LOGGER.debug("Verify authorization");
      final String oauthVerifier = req.queryParams("oauth_verifier");
      final Login trelloLogin = getLogin(req);
      if (trelloLogin == null) {
        LOGGER.error("session not found");
        return "Session not found";
      }
      trelloLogin.createtClientSession(oauthVerifier);
      res.redirect(APP_PATH + "/boards");
      return "Login ok";
    });
  }

  public Login getLogin(Request req) {
    return (TrelloLogin) req.session().raw().getAttribute("trelloLogin");
  }

  public String getApplicationKey() {
    return System.getProperty("trello.key");
  }

  private void enableSecurityChain() {
    Spark.before("*", (request, response) -> {
      final String pathInfo = request.pathInfo();
      if (request.session().isNew() && !pathInfo.contains("login") && !pathInfo.contains("favicon")) {
        response.cookie("originLocation", pathInfo);
        response.redirect(APP_PATH + "/login");
      }
    });
  }

  private String decode(String value) {
    try {
      return URLDecoder.decode(value, "UTF-8").toString();
    }
    catch (UnsupportedEncodingException e) {
      LOGGER.error(e.getMessage());
      return null;
    }
  }
}