# wordent-distance
Wordnet-based concept similarity measure service.

The service is a simple implementation of the Wu & Palmer similarity measure, as described e.g., in: 
http://blog.thedigitalgroup.com/sagarg/2015/06/10/words-similarityrelatedness-using-wupalmer-algorithm/

"The Wu & Palmer calculates relatedness by considering the depths of the two synsets in the WordNet taxonomies, along with the depth of the LCS (Least Common Subsumer).

The formula is score = 2 * depth (lcs) / (depth (s1) + depth (s2))." [ibid]

The service needs to be accompanied by a specially transformed wordnet RDF db, that can be downloaded from:
https://www.dropbox.com/s/8umyuun3izuazp5/wordnet-distance.ttl.zip?dl=0
The zip needs to be unpacked in the main service directory. 

A full example of setting up and running the service is included in the Test class. 

Example "pretty-print" output:

Maximum similarity between "chair" and "leader" is... 0.8888888888888888 for:
	concept 1: 	[chairwoman, chairperson, chair, chairman, president] (http://wordnet-rdf.princeton.edu/wn31/110488547-n)
	concept 2: 	[leader] (http://wordnet-rdf.princeton.edu/wn31/109646208-n)
	lcs concept:[leader] (http://wordnet-rdf.princeton.edu/wn31/109646208-n)
	(search time: PT7.254S)

Maximum similarity between "universe" and "galaxy" is... 0.8 for:
	concept 1: 	[universe, population] (http://wordnet-rdf.princeton.edu/wn31/106035684-n)
	concept 2: 	[galaxy] (http://wordnet-rdf.princeton.edu/wn31/108287859-n)
	lcs concept:[accumulation, assemblage, aggregation, collection] (http://wordnet-rdf.princeton.edu/wn31/107968050-n)
	(search time: PT2.209S)

Maximum similarity between "future" and "past" is... 0.875 for:
	concept 1: 	[future, future tense] (http://wordnet-rdf.princeton.edu/wn31/106341394-n)
	concept 2: 	[past, past tense] (http://wordnet-rdf.princeton.edu/wn31/106341255-n)
	lcs concept:[tense] (http://wordnet-rdf.princeton.edu/wn31/106340499-n)
	(search time: PT2.989S)

Maximum similarity between "hair dryer" and "petrol station" is... 0.5555555555555556 for:
	concept 1: 	[blow dryer, hair dryer, blow drier, hand blower, hair drier] (http://wordnet-rdf.princeton.edu/wn31/103488399-n)
	concept 2: 	[filling station, gas station, gasoline station, petrol station] (http://wordnet-rdf.princeton.edu/wn31/103430002-n)
	lcs concept:[artefact, artifact] (http://wordnet-rdf.princeton.edu/wn31/100022119-n)
	(search time: PT1.096S)

Maximum similarity between "picture" and "view" is... 0.8888888888888888 for:
	concept 1: 	[painting, picture] (http://wordnet-rdf.princeton.edu/wn31/103882197-n)
	concept 2: 	[view, scene] (http://wordnet-rdf.princeton.edu/wn31/104151847-n)
	lcs concept:[graphic art] (http://wordnet-rdf.princeton.edu/wn31/103458753-n)
	(search time: PT9.535S)

Maximum similarity between "horse" and "person" is... 0.5454545454545454 for:
	concept 1: 	[Equus caballus, horse] (http://wordnet-rdf.princeton.edu/wn31/102377103-n)
	concept 2: 	[soul, mortal, individual, somebody, someone, person] (http://wordnet-rdf.princeton.edu/wn31/100007846-n)
	lcs concept:[organism, being] (http://wordnet-rdf.princeton.edu/wn31/100004475-n)
	(search time: PT2.647S)

