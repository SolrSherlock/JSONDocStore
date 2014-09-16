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
package test;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.topicquests.common.api.IResult;
import org.topicquests.persist.json.JSONDocStoreEnvironment;
import org.topicquests.persist.json.api.IJSONDocStoreModel;

/**
 * @author park
 *
 */
public class SecondBigQueryTest {
	private JSONDocStoreEnvironment environment;
	private IJSONDocStoreModel model;
	private final String
			ID	= "HumanType",
			ID2 = "AnimalType",
			ID3 = "BirdType",
			ID4 = "FooType",
			INDEX = "testindex",
			//cannot be empty
			TYPE 	= "foo";

	/**
	 * 
	 */
	public SecondBigQueryTest() {
		environment = new JSONDocStoreEnvironment();
		model = environment.getModel();
		JSONObject jo = new JSONObject();
		jo.put("id", ID);
		jo.put("subOf", ID2);
		IResult rx = model.putDocument(ID, INDEX, TYPE, jo, false);
		System.out.println("AAA "+rx.getErrorString());
		jo.clear();
		jo.put("id", ID2);
		model.putDocument(ID2, INDEX, TYPE, jo, false);
		System.out.println("BBB "+rx.getErrorString());
		jo.clear();
		jo.put("id", ID3);
		JSONArray array = new JSONArray();
		array.add(ID2);
		array.add(ID4);
		jo.put("subOf", array);
		model.putDocument(ID3, INDEX, TYPE, jo, false);
		System.out.println("CCC "+rx.getErrorString());
		String query = "{\"match\": {\"subOf\": \""+ID2+"\"}}";
		rx = model.runQuery(INDEX, query, 0, -1, TYPE);
		//AbstractBaseElasticSearchModel.runQuery- {"size": 12,"query": {"match": {"subOf": "AnimalType"}}}
		System.out.println(rx.hasError()+"  "+rx.getResultObject());
		if (rx.hasError())
			System.out.println("ERR "+rx.getErrorString());
		 rx = model.getDocument(INDEX, TYPE, ID3);
			System.out.println(rx.hasError()+"  "+rx.getResultObject());
		environment.shutDown();		
	}
//false  [{"id":"HumanType","subOf":"AnimalType"}, {"id":"BirdType","subOf":["AnimalType","FooType"]}]
//	false  {"id":"BirdType","subOf":["AnimalType","FooType"]}

}
