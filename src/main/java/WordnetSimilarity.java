import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.tdb.TDBFactory;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by szymon on 12/07/2017.
 */
public class WordnetSimilarity {

    private static Model modelWNT;
    private static final String graph = "wordnet";
    private static final String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "PREFIX xsd:<http://www.w3.org/2001/XMLSchema#>\n" +
            "\n" +
            "SELECT ?w1 ?w2 ?lcs (GROUP_CONCAT(?w1Lab;separator=\"|\") as ?context1) (GROUP_CONCAT(?w2Lab;separator=\"|\") as ?context2) (GROUP_CONCAT(?lab;separator=\"|\") as ?lcsName) ?score\n" +
            "WHERE \n" +
            "{?w1 rdfs:label ?l1 .\n" +
            "?w1 rdfs:label ?w1Lab .\n" +
            "FILTER (str(?l1)=\"%s\") .\n" +
            "?w2 rdfs:label ?l2 .\n" +
            "?w2 rdfs:label ?w2Lab .\n" +
            "FILTER (str(?l2)=\"%s\") .\n" +
            "?o <dist_from> ?w1 . \n" +
            "?o <dist_to> ?lcs . \n" +
            "?o <dist> ?d1 .\n" +
            "?p <dist_from> ?w2 . \n" +
            "?p <dist_to> ?lcs . \n" +
            "?p <dist> ?d2 .\n" +
            "?lcs <depth> ?lcsd .\n" +
            "?lcs rdfs:label ?lab . \n" +
            "BIND(xsd:double(2*?lcsd)/xsd:double(2*?lcsd+?d1+?d2) as ?score)} GROUP BY ?w1 ?w2 ?lcs ?score ORDER BY desc(?score) LIMIT 1";

    private WordnetSimilarity() {

    }

    public WordnetSimilarity(String dbDir) {
        Dataset dataset = TDBFactory.createDataset(dbDir);
        modelWNT = dataset.getNamedModel(graph);
    }

    public WordnetSimilarity(String dbDir, String wordnetFileName) {
        //default dbDir = "./tdb"
        //default wordnetFileName = "wordnet-distance.ttl"

        Dataset dataset = TDBFactory.createDataset(dbDir);
        modelWNT = RDFDataMgr.loadModel(wordnetFileName);
        dataset.addNamedModel(graph, modelWNT);
    }

    public static Result wordnetSimilarity(String word1, String word2) {

        Query query = QueryFactory.create(String.format(queryString, word1, word2));

        QueryExecution qexec = QueryExecutionFactory.create(query, modelWNT);
            ResultSet results = qexec.execSelect();
            if (results.hasNext()) {
                QuerySolution soln = results.nextSolution();
                if (soln.contains("?score")) {
                    Result result = new Result();
                    result.score = soln.get("?score").asLiteral().getDouble();
                    result.justificiation = new HashSet<>(Arrays.asList(soln.get("?lcsName").toString().split("\\|")));
                    result.context1 = new HashSet<>(Arrays.asList(soln.get("?context1").toString().split("\\|")));
                    result.context2 = new HashSet<>(Arrays.asList(soln.get("?context2").toString().split("\\|")));
                    result.concept1 = soln.get("?w1").toString();
                    result.concept2 = soln.get("?w2").toString();
                    result.lcsConcept = soln.get("?lcs").toString();
                    return result; } else {
                    Result result = new Result();
                    result.score = 0.0;
                    return result;
                }
            } else {
                Result result = new Result();
                result.score = 0.0;
                return result;
            }
    }
}