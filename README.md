# WordNet-distance
WordNet-based concept similarity measure rest API service.

The service is a simple implementation of the Wu & Palmer similarity measure, as described e.g., in: 
http://blog.thedigitalgroup.com/sagarg/2015/06/10/words-similarityrelatedness-using-wupalmer-algorithm/

"*The Wu & Palmer calculates relatedness by considering the depths of the two synsets in the WordNet taxonomies, along with the depth of the LCS (Least Common Subsumer).*

**The formula is score = 2 * depth (lcs) / (depth (s1) + depth (s2)).**"
[ibid]

Wordnet-distance is an in-memory service built on top of Jena and its embedded Lucene library, and uses a specially tailored wordnet RDF graph, which is supplied within this repository (**wordnet-distance.ttl.zip**). The zip file must be unpacked in the main service's directory. 

Examples:
------------------------------

Observe how the sense of the same word changes depending on the choice of the other word it is compared to. In this implementation, the service searches for the most similar meaning, hence implicitly disambiguating the words towards the closest possible context that both words share.

```
// "guy" vs. "dick"
http://localhost:4567?word1=dick&word2=asshole&details=true

{
  "score": 0.6086956521739131,
  "leastCommonSubsumer": ["someone","individual","soul","person","somebody","mortal"],
  "leastCommonSubsumerUri": "http://wordnet-rdf.princeton.edu/wn31/100007846-n",
  "conceptOne": ["cat","hombre","sod","guy","bozo"],
  "conceptOneUri": "http://wordnet-rdf.princeton.edu/wn31/110172934-n"
  "conceptTwon": ["dick","gumshoe","hawkshaw"],
  "conceptTwoUri": "http://wordnet-rdf.princeton.edu/wn31/110031439-n",
}

// "dick" vs. "cock"
http://localhost:4567?word1=dick&word2=cock&details=true

{
  "score": 1,
  "leastCommonSubsumer": ["prick","dick","pecker","cock","tool","shaft","putz"],
  "leastCommonSubsumerUri": "http://wordnet-rdf.princeton.edu/wn31/105534354-n",
  "conceptOne": ["prick","dick","pecker","cock","tool","shaft","putz"],
  "conceptOneUri": "http://wordnet-rdf.princeton.edu/wn31/105534354-n",
  "conceptTwo": ["prick","dick","pecker","cock","tool","shaft","putz"],
  "conceptTwoUri": "http://wordnet-rdf.princeton.edu/wn31/105534354-n"
}

// "cock" vs. "chick"
http://localhost:4567?word1=cock&word2=chick&details=true

{
  "score": 0.9285714285714286,
  "leastCommonSubsumer": ["chicken","Gallus gallus"],
  "leastCommonSubsumerUri": "http://wordnet-rdf.princeton.edu/wn31/101794266-n",
  "conceptOne": ["rooster","cock"],
  "conceptOneUri": "http://wordnet-rdf.princeton.edu/wn31/101794799-n",
  "conceptTwo": ["biddy","chick"],
  "conceptTwoUri": "http://wordnet-rdf.princeton.edu/wn31/101794683-n"
}

// "chick" vs. "girl"
http://localhost:4567?word1=chick&word2=girl&details=true

{
  "score": 0.9523809523809523,
  "leastCommonSubsumer": ["young lady","young woman","missy","girl","fille","miss"],
  "leastCommonSubsumerUri": "http://wordnet-rdf.princeton.edu/wn31/110149362-n",
  "conceptOne": ["doll","skirt","bird","wench","dame","chick"],
  "conceptOneUri": "http://wordnet-rdf.princeton.edu/wn31/110008583-n",
  "conceptTwo": ["young lady","young woman","missy","girl","fille","miss"],
  "conceptTwoUri": "http://wordnet-rdf.princeton.edu/wn31/110149362-n"
}
```
...right, really disturbing, but it's the language, simply encoded by WordNet. 

