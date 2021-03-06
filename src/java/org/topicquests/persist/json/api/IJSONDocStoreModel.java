/*
 * Copyright 2013, TopicQuests
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.topicquests.persist.json.api;

import org.json.simple.JSONObject;
import org.topicquests.common.api.IResult;
import org.topicquests.persist.json.JSONDocStoreEnvironment;
import org.elasticsearch.index.query.QueryBuilder;

/**
 * @author park
 *
 */
public interface IJSONDocStoreModel {
	
	IResult init(JSONDocStoreEnvironment env);

	/**
	 * <p>Store a document based on <code>jsonString</code>.</p>
	 * <p>If <code>checkVersion</code> is <code>true</code>, then
	 * the value of the property {@link IJSONDocStoreOntology$VERSION_PROPERTY}, if it exists
	 * in an already-stored document, is compared with that value, if it exists in <code>jsonString</code>.
	 * If the comparison fails, then the store process is aborted and an OptimisticLockException error message
	 * is returned.</p>
	 * @param id
	 * @param index
	 * @param type
	 * @param jsonString
	 * @param checkVersion
	 * @return
	 */
	IResult putDocument(String id, String index, String type, String jsonString, boolean checkVersion);
	
	IResult putDocument(String id, String index, String type, JSONObject document, boolean checkVersion);
	
	/**
	 * Return a JSON String representing the document
	 * @param index
	 * @param type
	 * @param documentId
	 * @return 
	 */
	IResult getDocument(String index, String type, String documentId);
	
	/**
	 * Will return a Boolean based on existence of a document answering to <code>documentId</code>
	 * @param index
	 * @param type
	 * @param documentId
	 * @return
	 */
	IResult documentExists(String index, String type, String documentId);
	
	/**
	 * Find a specific document according to a <code>key/value</code> pair
	 * @param index
	 * @param key
	 * @param value
	 * @param types
	 * @return a <code>List<String></code> or <code>null</code> inside {@link IResult}
	 */
	IResult getDocumentByProperty(String index, String key, String value, String... types);
	
	/**
	 * Works only on keys (fields) which are set to <em>not-analyzed</em>
	 * in the file <code>mappings.json</code>
	 * @param index
	 * @param key
	 * @param wildcardQuery
	 * @param start TODO
	 * @param count TODO
	 * @param types
	 * @return a <code>List<String></code> or <code>null</code> inside {@link IResult}
	 */
	IResult listDocumentsByWildcardPropertyValue(String index, String key, String wildcardQuery, int start, int count, String...types);
	
	IResult listDocumentsByKeywordProperty(String index, String key, String query, int start, int count, String...types);
	
	/**
	 * <p>List all documents according to a <code>key/value</code> pair</p>
	 * <p>This can be run recursively to walk down a tree structure</p>
	 * @param index
	 * @param key
	 * @param value
	 * @param start
	 * @param count
	 * @param types
	 * @return
	 */
	IResult listDocumentsByProperty(String index, String key, String value, int start, int count, String... types);

	/**
	 * Return a Long count of documents in the given <code>index</code>
	 * @param indices
	 * @return
	 */
	IResult countDocuments(String... indices);
	
	/**
	 * Remove a document identified by <code>documentId</code>
	 * @param index
	 * @param type
	 * @param documentId
	 * @return
	 */
	IResult removeDocument(String index, String type, String documentId);
	
	/**
	 * <p> Process a JSON-String query</p>
	 * <p>Note: <code>queryString</code> must be in appropriate JSON query form</p>
	 * @param index
	 * @param queryString
	 * @param start 
	 * @param count -1 means all
	 * @param types
	 * @see http://exploringelasticsearch.com/book/searching-data/the-query-dsl-and-the-search-api.html
	 * @return empty List or List of JSON String documents
	 */
	IResult runQuery(String index, String queryString, int start, int count, String... types);

	IResult runQuery(String index, QueryBuilder qb, int start, int count, String... types);

	/**
	 * Required behavior when system is shut down.
	 */
	void shutDown();
}
