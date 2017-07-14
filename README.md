# WordNet-distance
WordNet-based concept similarity measure (Rest API).

The service is a simple implementation of the Wu & Palmer similarity measure, as described e.g., in: 
http://blog.thedigitalgroup.com/sagarg/2015/06/10/words-similarityrelatedness-using-wupalmer-algorithm/

"*The Wu & Palmer calculates relatedness by considering the depths of the two synsets in the WordNet taxonomies, along with the depth of the LCS (Least Common Subsumer).*

**The formula is score = 2 * depth (lcs) / (depth (s1) + depth (s2)).**"

[ibid]

WordnNet-distance uses in-memory Jena triplestore with its embedded Lucene library. It requires a specifically structured WordNet RDF graph, which is supplied within this repository (**wordnet-distance.ttl.zip**). The zip file must be unpacked in the main service's directory. 

The service runs at http://localhost:4567 and requires the following query parameters:

* 'word1':	(String) first word
* 'word2':	(String) second word
* 'details':	(Boolean) additional details about the score


Examples:
------------------------------

Observe how the sense of the same word changes depending on the choice of the other word it is compared to. In this implementation, the service searches for the most similar meaning, thus implicitly disambiguating the words towards the closest possible context that both words share.

```
// "mind" vs. "consider"
http://localhost:4567?word1=mind&word2=consider&details=true

{
  "score": 0.6666666666666666,
  "leastCommonSubsumer": ["think about"],
  "leastCommonSubsumerUri": "http://wordnet-rdf.princeton.edu/wn31/200736371-v",
  "conceptOne": ["mind"],
  "conceptOneUri": "http://wordnet-rdf.princeton.edu/wn31/200726454-v",
  "conceptTwo": ["take","deal","consider","look at"],
  "conceptTwoUri": "http://wordnet-rdf.princeton.edu/wn31/200736077-v"
}

// "mind" vs. "philosopher"
http://localhost:4567?word1=mind&word2=philosopher&details=true

{
  "score": 0.8421052631578947,
  "leastCommonSubsumer": ["intellect","intellectual"],
  "leastCommonSubsumerUri": "http://wordnet-rdf.princeton.edu/wn31/109644715-n",
  "conceptOne": ["mind","creative thinker","thinker"],
  "conceptOneUri": "http://wordnet-rdf.princeton.edu/wn31/110727941-n",
  "conceptTwo": ["philosopher"],
  "conceptTwoUri": "http://wordnet-rdf.princeton.edu/wn31/110443334-n"
}

// "mind" vs. "watch"
http://localhost:4567?word1=mind&word2=watch&details=true

{
  "score": 0.6666666666666666,
  "leastCommonSubsumer": ["watch out","watch","look out"],
  "leastCommonSubsumerUri": "http://wordnet-rdf.princeton.edu/wn31/202156396-v",
  "conceptOne": ["beware","mind"],
  "conceptOneUri": "http://wordnet-rdf.princeton.edu/wn31/200726626-v",
  "conceptTwo": ["watch out","watch","look out"],
  "conceptTwoUri": "http://wordnet-rdf.princeton.edu/wn31/202156396-v"
}

// "watch" vs. "clock"
http://localhost:4567?word1=watch&word2=clock&details=true

{
  "score": 0.9090909090909091,
  "leastCommonSubsumer": ["horologe","timepiece","timekeeper"],
  "leastCommonSubsumerUri": "http://wordnet-rdf.princeton.edu/wn31/104445087-n",
  "conceptOne": ["ticker","watch"],
  "conceptOneUri": "http://wordnet-rdf.princeton.edu/wn31/104563183-n",
  "conceptTwo": ["clock"],
  "conceptTwoUri": "http://wordnet-rdf.princeton.edu/wn31/103050242-n"
}
```
