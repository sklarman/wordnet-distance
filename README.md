# wordent-distance
Wordnet-based concept similarity measure service.

The service is a simple implementation of the Wu & Palmer similarity measure, as described e.g., in: 
http://blog.thedigitalgroup.com/sagarg/2015/06/10/words-similarityrelatedness-using-wupalmer-algorithm/

"*The Wu & Palmer calculates relatedness by considering the depths of the two synsets in the WordNet taxonomies, along with the depth of the LCS (Least Common Subsumer).*

**The formula is score = 2 * depth (lcs) / (depth (s1) + depth (s2)).**"
[ibid]

Wordnet-distance uses a specially tailored wordnet RDF graph, which is supplied within this repository (**wordnet-distance.ttl.zip**). The zip file must be unpacked in the main service's directory. 

A full example of setting up and using the wordnet-distance is included in the **Test** class. 

Example "pretty-print" output:
------------------------------


    Maximum similarity between "chair" and "leader" is... 0.8888888888888888 for:
    	concept 1: 	[chairwoman, chairperson, chair, chairman, president] (http://wordnet-rdf.princeton.edu/wn31/110488547-n)
    	concept 2: 	[leader] (http://wordnet-rdf.princeton.edu/wn31/109646208-n)
    	lcs concept:	[leader] (http://wordnet-rdf.princeton.edu/wn31/109646208-n)
    	(search time: PT7.254S)
    
    Maximum similarity between "universe" and "galaxy" is... 0.8 for:
    	concept 1: 	[universe, population] (http://wordnet-rdf.princeton.edu/wn31/106035684-n)
    	concept 2: 	[galaxy] (http://wordnet-rdf.princeton.edu/wn31/108287859-n)
    	lcs concept:	[accumulation, assemblage, aggregation, collection] (http://wordnet-rdf.princeton.edu/wn31/107968050-n)
    	(search time: PT2.209S)
	
    Maximum similarity between "horse" and "person" is... 0.5454545454545454 for: 
	concept 1: 	[Equus caballus, horse] (http://wordnet-rdf.princeton.edu/wn31/102377103-n) 
	concept 2: 	[soul, mortal, individual, somebody, someone, person] (http://wordnet-rdf.princeton.edu/wn31/100007846-n) 
	lcs concept:	[organism, being] (http://wordnet-rdf.princeton.edu/wn31/100004475-n) 
	(search time: PT2.647S)




