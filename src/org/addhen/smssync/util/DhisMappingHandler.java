package org.addhen.smssync.util;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.os.Environment;

public class DhisMappingHandler {
	private static final String DATASET_DIRECTORY_PATH = Environment.getExternalStorageDirectory() + "/dhismappingfiles/";
	private static final String DATASET_FILE = "dataSets.xml";
	private static final String CLASS_TAG = DhisMappingHandler.class.getCanonicalName();

	protected Document datasetDoc;
	protected Document elementsDoc;
	protected String dataSetId = null;

	public DhisMappingHandler(String formId) {
		datasetDoc = initDoc(DATASET_DIRECTORY_PATH + DATASET_FILE);
		NodeList dataSetList = datasetDoc.getElementsByTagName("dataSet");

		int index = Integer.parseInt(formId)-1;
		if(index < 1 || index > dataSetList.getLength()) {
			dataSetId = null;
		} 

		Node node = dataSetList.item(index);
		Element elem = (Element) node;
		dataSetId  =  elem.getAttribute("id");

		elementsDoc = initDoc(DATASET_DIRECTORY_PATH + dataSetId);
	}

	private Document initDoc(String fileName) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Document doc = null;

		try {
			db = dbf.newDocumentBuilder();
			doc = db.parse(new File(fileName));
			doc.getDocumentElement().normalize();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return doc;

	}

	public String getDataSetId() {
		return dataSetId;
	}
	

	public String getPipedElementId(String element) {
		NodeList dataSetList = elementsDoc.getElementsByTagName("dataElement"); 
		
		int counter = 0;
		int elementNumber = Integer.parseInt(element);
		
		for (int i = 0; i < dataSetList.getLength(); i++) {
			Node elementNode = dataSetList.item(i);
			Element elementElem = (Element) elementNode;
			NodeList comboList = elementElem.getElementsByTagName("categoryOptionCombo");
			
			if(comboList.getLength() == 0) {
				counter++;
				if(elementNumber == counter) {
					return elementElem.getAttribute("id");
				}
			} else {
				for (int j = 0; j < comboList.getLength(); j++) {
					counter++;
					if(elementNumber == counter) {
						Node comboNode = comboList.item(j);
						Element comboElem = (Element) comboNode;
						return comboElem.getAttribute("id");
					}
				}
			}
		}
		
		return null;
	}
		
}



