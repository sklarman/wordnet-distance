import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.shared.NotFoundException;
import org.apache.jena.vocabulary.RDFS;
import org.apache.lucene.store.*;
import org.apache.jena.query.text.* ;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by szymon on 12/07/2017.
 */
public class WordnetSimilarityService {

    static Dataset ds;

    static final String uriSearchQuery =
         "PREFIX text: <http://jena.apache.org/text#>\n" +
         "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
         "SELECT (GROUP_CONCAT(CONCAT(\"<\",str(?s),\">\");separator=\" \") as ?uris)\n" +
         "WHERE { " +
                "?s text:query (rdfs:label \"%s\") . " +
                "?s rdfs:label ?l . FILTER(str(?l)=\"%s\")" +
                 "}";

    static final String mainQuery =
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "PREFIX xsd:<http://www.w3.org/2001/XMLSchema#>\n" +
            "\n" +
            "SELECT ?w1 ?w2 ?lcs ?score\n" +
            "WHERE {" +
                "VALUES ?w1 { %s } \n" +
                "VALUES ?w2 { %s }\n" +
                "?o <dist_from> ?w1 . \n" +
                "?o <dist_to> ?lcs . \n" +
                "?o <dist> ?d1 .\n" +
                "?p <dist_from> ?w2 . \n" +
                "?p <dist_to> ?lcs . \n" +
                "?p <dist> ?d2 .\n" +
                "?lcs <depth> ?lcsd .\n" +
                "BIND(xsd:double(2*?lcsd)/xsd:double(2*?lcsd+?d1+?d2) as ?score)" +
                "} ORDER BY desc(?score) LIMIT 1 ";

    static final String labelQuery =
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "SELECT (GROUP_CONCAT(str(?l);separator=\",\") as ?labs) " +
            "WHERE {<%s> rdfs:label ?l. }";


    public WordnetSimilarityService(String dbFile) {

        Dataset dataset = DatasetFactory.create() ;
        EntityDefinition entDef = new EntityDefinition("uri", "text", RDFS.label.asNode());
        Directory dir =  new RAMDirectory();
        ds = TextDatasetFactory.createLucene(dataset, dir, new TextIndexConfig(entDef)) ;
        ds.begin(ReadWrite.WRITE) ;
        Model m = ds.getDefaultModel();
        RDFDataMgr.read(m, dbFile) ;
        ds.commit() ;
        ds.end();
        ds.begin(ReadWrite.READ) ;
    }

    public static Result similarity(String word1, String word2, Boolean details) throws Exception {

        Result result = new Result();

        String uris1;
        String uris2;

        try {uris1 = getUris(word1);} catch (Exception e)
        {
            throw new NotFoundException("Word \""+word1+"\" not found.");
        }

        try {uris2 = getUris(word2);} catch (Exception e)
        {
            throw new NotFoundException("Word \""+word2+" not found.");
        }

        Query query = QueryFactory.create(String.format(mainQuery, uris1, uris2));
        QueryExecution qexec = QueryExecutionFactory.create(query, ds);

        ResultSet results = qexec.execSelect();

        if (!results.hasNext()) return result;

        QuerySolution soln = results.next();
        result.score = soln.get("?score").asLiteral().getDouble();

        if (details) {
            result.conceptOneUri = soln.get("?w1").toString();
            result.conceptTwoUri = soln.get("?w2").toString();
            result.leastCommonSubsumerUri = soln.get("?lcs").toString();
            result.leastCommonSubsumer.addAll(getLabels(result.leastCommonSubsumerUri));
            result.conceptOne.addAll(getLabels(result.conceptOneUri));
            result.conceptTwo.addAll(getLabels(result.conceptTwoUri));
        }

        return result;
    }


    public static String getUris(String word) throws Exception {

        Query uriQuery = QueryFactory.create(String.format(uriSearchQuery, word, word));
        QueryExecution qexec = QueryExecutionFactory.create(uriQuery, ds);
        ResultSet results = qexec.execSelect();
        if (results.hasNext()) {
            QuerySolution soln = results.nextSolution();
            if (!soln.get("?uris").asLiteral().toString().isEmpty())
                return soln.get("?uris").asLiteral().toString();
        }
        System.out.println("Word: \"" + word + "\" not found.");
        throw new Exception();
    }


    public static Set<String> getLabels(String uri)  {

        Query uriQuery = QueryFactory.create(String.format(labelQuery, uri));
        QueryExecution qexec = QueryExecutionFactory.create(uriQuery, ds);
        ResultSet results = qexec.execSelect();
        QuerySolution soln = results.nextSolution();
        String[] labels = soln.get("?labs").toString().split(",");
        Set<String> output = new HashSet<>();
        output.addAll(new ArrayList<>(Arrays.asList(labels)));

        return output;
    }
}