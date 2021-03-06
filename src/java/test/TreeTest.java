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

import java.util.ArrayList;
import java.util.List;
import java.io.*;

import org.topicquests.common.api.IResult;
import org.topicquests.common.api.ITopicQuestsOntology;
import org.topicquests.persist.json.JSONDocStoreEnvironment;
import org.topicquests.persist.json.api.IJSONDocStoreModel;
import org.topicquests.persist.json.api.ITreeHandler;

/**
 * @author park
 *
 */
public class TreeTest {
	private JSONDocStoreEnvironment environment;
	private IJSONDocStoreModel model;
	private ITreeHandler handler;
	private final String
		INDEX = "topics", //BuildComplexKnowledgeBase.INDEX,
		TYPE = "core", //BuildComplexKnowledgeBase.TYPE,
		ROOT = ITopicQuestsOntology.TYPE_TYPE; //BuildComplexKnowledgeBase.ROOTID;

	/**
	 * 
	 */
	public TreeTest() {
		System.out.println("Starting tree test");
		environment = new JSONDocStoreEnvironment();
		model = environment.getModel();
		handler = environment.getTreeHandler();
		List<String>props = new ArrayList<String>();
		props.add(ITopicQuestsOntology.SUBCLASS_OF_PROPERTY_TYPE);
		props.add(ITopicQuestsOntology.INSTANCE_OF_PROPERTY_TYPE);
		try {
			IResult rx = null;
			File f = new File("Typology"+System.currentTimeMillis()+".json");
			FileOutputStream fos = new FileOutputStream(f);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			Writer out = new PrintWriter(bos);
			String identityKey = ITopicQuestsOntology.LOCATOR_PROPERTY;
			System.out.println("JUST STARTING");
			rx = handler.writeTypologyTree(ROOT, identityKey, props, out, INDEX, TYPE);
			out.close();
			out.flush();
			if (rx.hasError())
				System.out.println("ERR1 "+rx.getErrorString());
			handler.clearVisitedList();
			f = new File("FullTree"+System.currentTimeMillis()+".json");
			fos = new FileOutputStream(f);
			bos = new BufferedOutputStream(fos);
			out = new PrintWriter(bos);
			rx = handler.writeTree(ROOT, identityKey, props, true, out, INDEX, TYPE);
			out.close();
			out.flush();
			if (rx.hasError())
				System.out.println("ERR2 "+rx.getErrorString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Did");
		environment.shutDown();
	}

}
