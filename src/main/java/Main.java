import org.apache.jena.shared.NotFoundException;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import static spark.Spark.get;

/**
 * Created by szymon on 13/07/2017.
 */
public class Main {

    static final String dbFile = "wordnet-distance.ttl";
    static WordnetSimilarityService wntsim;
    static String PROBLEM_MESSAGE = "Failed to calculate similarity.";
    static String welcomeMessage =
        "****************\n" +
        "WordNet-distance\n" +
        "****************\n\n" +
        "URL: http://localhost:4567\n" +
        "Required query parameters:\n" +
        "\t'word1':\t(String) first word\n" +
        "\t'word2':\t(String) second word\n" +
        "\t'details':\t(Boolean) additional details about the score";

    public static void main(String[] args) {
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.OFF);

        System.out.println("Loading data...\n");

        wntsim = new WordnetSimilarityService(dbFile);

        System.out.println(welcomeMessage);

        get("/", (req, res) -> {
            String word1 = req.queryParams("word1");
            String word2 = req.queryParams("word2");
            Boolean details = Boolean.valueOf(req.queryParams("details"));

                try {
                    Result response = wntsim.similarity(word1, word2, details);
                    res.status(200);
                    res.type("application/json");
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.writeValueAsString(response);
                } catch (NotFoundException e) {

                    if (!e.getMessage().isEmpty()) {
                        res.status(404);
                        return e.getMessage();
                    } else {
                        res.status(204);
                        return PROBLEM_MESSAGE;
                    }
                     }
        });

    }

}
