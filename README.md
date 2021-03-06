JSONDocStore
============

Document Store based on ElasticSearch and TinkerPop Blueprints Graph. Includes two central APIs, one to store documents as JSON blobs, and one which (experimentally speaking) implements the TinkerPop.com Blueprints GraphAPI. A third, highly experimental API (in the works) implements the full TopicQuests topic map API.

Status: *very-pre-alpha*<br/>
Latest edit: 20140218<br/>
## Background ##
For many of the SolrSherlock projects, particularly in text harvesting, there is a need for a "local" but scalable database with indexing capabilities. For that reason, an ElasticSearch platform was started. Along the way, a curiosity grew, whether the same platform could be used for other purposes, including:
- A topic map, similar to what we are doing with Solr
- A graph database similar to Titan, Neo4J, and other Blueprints-capable platforms.

Thus, this project includes experimental implementations. It runs all its dev tests except the blueprints vertex test, where it is presently not responding properly. It does build graphs with vertices and edges, and returns them.

## Update History ##
20140218 No code changes: added a UML image of the classes.

20140209 Cleaned up code for faster operation; now tested on a cluster (2 nodes), with some startup issues (unresolved at present) but otherwise running on a cluster.

20131129 Extended the Blueprints Graph API with IJSONGraph to permit creation of Vertex and Edge objects with properties.

20131126 changed code to improve boot reliability of Embedded system. Version 0.1.1
20131126 First GitHub commit

## ToDo ##
Lots<br/>
Mavenize the project<br/>
Create a full unit test suite

## License ##
Apache 2

