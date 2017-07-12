# wordent-distance
Wordnet-based concept similarity measure service.

The service is a simple implementation of the Wu & Palmer similarity measure, as described e.g., in: 
http://blog.thedigitalgroup.com/sagarg/2015/06/10/words-similarityrelatedness-using-wupalmer-algorithm/

"The Wu & Palmer calculates relatedness by considering the depths of the two synsets in the WordNet taxonomies, along with the depth of the LCS (Least Common Subsumer).

The formula is score = 2 * depth (lcs) / (depth (s1) + depth (s2))." [ibid]

The service needs to be accompanied by a specially transformed wordnet RDF db, included in this repo. This needs to be unpacked in the main directory. 



