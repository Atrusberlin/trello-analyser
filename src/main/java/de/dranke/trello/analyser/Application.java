package de.dranke.trello.analyser;

import org.glassfish.jersey.client.oauth1.AccessToken;
import org.glassfish.jersey.client.oauth1.ConsumerCredentials;
import org.glassfish.jersey.client.oauth1.OAuth1AuthorizationFlow;
import org.glassfish.jersey.client.oauth1.OAuth1ClientSupport;
import org.slf4j.Logger;
import spark.Spark;

import java.io.File;

import static org.slf4j.LoggerFactory.getLogger;
import static spark.SparkBase.externalStaticFileLocation;

/**
 * Created by Daniel Ranke on 12.01.2016.
 */
public class Application {

    private static OAuth1AuthorizationFlow flow;
    public static final Logger LOGGER = getLogger("Application");
    public static final String APP_PATH = "/trello/analyse";
    public static final String callbackURL = "http://" +
            System.getProperty("server.domain") + ":" +
            System.getProperty("server.port") + APP_PATH + "/createSession";

    public static void main(String[] args) {

        String www = new File("www").getAbsolutePath();
        LOGGER.info("Read static files from: {}", www);
        externalStaticFileLocation(www);

        Spark.get(APP_PATH + "/login", (req, res) -> {
            flow = createFlow();
            res.redirect(flow.start());
            return "";
        });

        Spark.get(APP_PATH + "/createSession", (req, res) -> {
            return createtClientSession(req.queryParams("oauth_verifier")).getToken();
        });

    }

    public static OAuth1AuthorizationFlow createFlow() {
        ConsumerCredentials consumerCredentials = new ConsumerCredentials(System.getProperty("trello.key"), System.getProperty("trello.secret"));
        OAuth1AuthorizationFlow flow = OAuth1ClientSupport.builder(consumerCredentials)
                .authorizationFlow(
                        "https://trello.com/1/OAuthGetRequestToken",
                        "https://trello.com/1/OAuthGetAccessToken",
                        "https://trello.com/1/OAuthAuthorizeToken?name=TrelloAnalyser&expiration=1hour&scope=read")
                .callbackUri(callbackURL)
                .build();
        return flow;
    }

    private static AccessToken createtClientSession(String oauthVerifier) {
        AccessToken accessToken = flow.finish(oauthVerifier);
//        client = ClientBuilder.newBuilder()
//                .register(this.authFlow.getOAuth1Feature())
//                .build();
        return accessToken;
    }
}
