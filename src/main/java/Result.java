import java.util.HashSet;
import java.util.Set;

/**
 * Created by szymon on 12/07/2017.
 */
public class Result {
    public Double score;
    public Set<String> leastCommonSubsumer;
    public String leastCommonSubsumerUri;
    public Set<String> conceptOne;
    public String conceptOneUri;
    public Set<String> conceptTwo;
    public String conceptTwoUri;

    public Result() {
        score = 0.0;
        leastCommonSubsumer = new HashSet<>();
        conceptOne = new HashSet<>();
        conceptTwo = new HashSet<>();
        leastCommonSubsumerUri = "";
        conceptOneUri = "";
        conceptTwoUri = "";
    }

}
