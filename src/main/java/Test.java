import java.time.Duration;
import java.time.Instant;

/**
 * Created by szymon on 12/07/2017.
 */
public class Test {
    static WordnetSimilarity wntsim;

    public static void main(String[] args) {

        //if jena tdb has been already created at ./tdb
        String directory = "./tdb";
        wntsim = new WordnetSimilarity(directory);

        //if jena tdb has NOT been yet created
        // String directory = "./tdb";
        // String wordnetFileName = "wordnet-distance.ttl";
        // wntsim = new WordnetSimilarity(directory, wordnetFileName);

        prettyQuery("chair", "leader");
        prettyQuery("universe", "galaxy");
        prettyQuery("future", "past");
        prettyQuery("hair dryer", "petrol station");
        prettyQuery("picture", "view");
        prettyQuery("horse", "person");

    }

    public static void prettyQuery(String word1, String word2) {
        System.out.print("\nMaximum similarity between \"" + word1 + "\" and \"" + word2 + "\" is... ");
        Instant start = Instant.now();
        Result result = wntsim.wordnetSimilarity(word1, word2);
        System.out.println(result.score + " for:");
        System.out.println("\tconcept 1: \t" + result.context1 + " ("+result.concept1+")");
        System.out.println("\tconcept 2: \t" + result.context2 + " ("+result.concept2+")");
        System.out.println("\tlcs concept:" + result.justificiation + " ("+result.lcsConcept+")");
        Instant end = Instant.now();
        System.out.println("\t(search time: " + Duration.between(start,end)+ ")");
    }
}
