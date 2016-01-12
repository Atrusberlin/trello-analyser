package de.dranke.trello.analyser;

import org.glassfish.jersey.client.oauth1.ConsumerCredentials;
import org.glassfish.jersey.client.oauth1.OAuth1AuthorizationFlow;
import org.glassfish.jersey.client.oauth1.OAuth1ClientSupport;
import spark.Spark;

/**
 * Created by Daniel Ranke on 12.01.2016.
 */
public class Application {

    public static final String callbackURL = "/createSession";
    private static OAuth1AuthorizationFlow flow;

    public static void main(String[] args) {

        Spark.get("/login", (req, res) -> {
            flow = login();
            res.redirect(flow.start());
            return "";
        });

    }

    public static OAuth1AuthorizationFlow login() {
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

//    public void createtClientSession(String oauthVerifier) {
//        accessToken = this.authFlow.finish(oauthVerifier);
//        client = ClientBuilder.newBuilder()
//                .register(this.authFlow.getOAuth1Feature())
//                .build();
//    }
}
