# WordNet-distance
WordNet-based concept similarity measure service.

The service is a simple implementation of the Wu & Palmer similarity measure, as described e.g., in: 
http://blog.thedigitalgroup.com/sagarg/2015/06/10/words-similarityrelatedness-using-wupalmer-algorithm/

"*The Wu & Palmer calculates relatedness by considering the depths of the two synsets in the WordNet taxonomies, along with the depth of the LCS (Least Common Subsumer).*

**The formula is score = 2 * depth (lcs) / (depth (s1) + depth (s2)).**"
[ibid]

Wordnet-distance uses a specially tailored wordnet RDF graph, which is supplied within this repository (**wordnet-distance.ttl.zip**). The zip file must be unpacked in the main service's directory. 

A full example of setting up and using the wordnet-distance is included in the **Test** class. 

Example "pretty-print" output:
------------------------------

Observe how the sense of the same word changes depending on the choice of the other word it is compared to. In this implementation, the service searches for the most similar meaning, hence implicitly disambiguating the words towards the closest possible context that both words share.

```
Maximum similarity between "school" and "university" is... 0.875 for:
	concept 1: 	[school] (http://wordnet-rdf.princeton.edu/wn31/108293641-n)
	concept 2: 	[university] (http://wordnet-rdf.princeton.edu/wn31/108303490-n)
	lcs concept:	[educational institution] (http://wordnet-rdf.princeton.edu/wn31/108293263-n)
	(search time: PT4.97S)

Maximum similarity between "university" and "company" is... 0.8 for:
	concept 1: 	[university] (http://wordnet-rdf.princeton.edu/wn31/108303490-n)
	concept 2: 	[company] (http://wordnet-rdf.princeton.edu/wn31/108074934-n)
	lcs concept:	[institution, establishment] (http://wordnet-rdf.princeton.edu/wn31/108070328-n)
	(search time: PT0.952S)

Maximum similarity between "company" and "business" is... 0.7692307692307693 for:
	concept 1: 	[troupe, company] (http://wordnet-rdf.princeton.edu/wn31/108203951-n)
	concept 2: 	[business, business organisation, concern, business organization, business concern] (http://wordnet-rdf.princeton.edu/wn31/108077878-n)
	lcs concept:	[organisation, organization] (http://wordnet-rdf.princeton.edu/wn31/108024893-n)
	(search time: PT4.491S)

Maximum similarity between "business" and "market" is... 0.8571428571428571 for:
	concept 1: 	[line of work, occupation, business, line, job] (http://wordnet-rdf.princeton.edu/wn31/100583425-n)
	concept 2: 	[marketplace, market, market place] (http://wordnet-rdf.princeton.edu/wn31/101099197-n)
	lcs concept:	[activity] (http://wordnet-rdf.princeton.edu/wn31/100408356-n)
	(search time: PT4.761S)

Maximum similarity between "company" and "friendship" is... 0.9230769230769231 for:
	concept 1: 	[fellowship, society, companionship, company] (http://wordnet-rdf.princeton.edu/wn31/113952621-n)
	concept 2: 	[friendship, friendly relationship] (http://wordnet-rdf.princeton.edu/wn31/113954178-n)
	lcs concept:	[friendship, friendly relationship] (http://wordnet-rdf.princeton.edu/wn31/113954178-n)
	(search time: PT3.873S)

Maximum similarity between "friendship" and "love" is... 0.6153846153846154 for:
	concept 1: 	[friendship, friendly relationship] (http://wordnet-rdf.princeton.edu/wn31/113954178-n)
	concept 2: 	[love] (http://wordnet-rdf.princeton.edu/wn31/107558676-n)
	lcs concept:	[state] (http://wordnet-rdf.princeton.edu/wn31/100024900-n)
	(search time: PT0.742S)
```


