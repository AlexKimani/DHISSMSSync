package org.addhen.smssync.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.addhen.smssync.R;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.os.Environment;

/**
 * @author 
 *
 */
public abstract class DhisMappingHandler {
	private String dataSetId = null;

	protected Document datasetDoc;
	protected Document elementsDoc;

	//TODO create own method for this! (to return if success) 
	public DhisMappingHandler() {

	}

	public boolean init(String formId) {
		if((datasetDoc = initDoc(Util.DATASET_DIRECTORY_PATH + Util.DATASET_FILE)) == null) {
			return false;
		}

		NodeList dataSetList = datasetDoc.getElementsByTagName("dataSet");

		int index = Integer.parseInt(formId)-1;
		if(index < 1 || index > dataSetList.getLength()) {
			return false;
		} 

		Node node = dataSetList.item(index);
		Element elem = (Element) node;
		dataSetId  =  elem.getAttribute("id");

		if((elementsDoc = initDoc(Util.DATASET_DIRECTORY_PATH + dataSetId + ".xml")) == null) {
			return false;
		}
		return true;

	}

	public static ArrayList<String> getDatasetsUrls() {
		Document doc = initDoc(Util.DATASET_DIRECTORY_PATH + Util.DATASET_FILE);
		if(doc == null)
			return null;
		NodeList dataSetList = doc.getElementsByTagName("dataSet");
		if(dataSetList == null)
			return null;
		ArrayList<String> list = new ArrayList<String>();

		for (int i = 0; i < dataSetList.getLength(); i++) {
			Node setNode = dataSetList.item(i);
			Element elementElem = (Element) setNode;
			list.add(elementElem.getAttribute("href"));
		}

		return list;
	}

	public static String getDataSetId(String formId) {
		Document doc = initDoc(Util.DATASET_DIRECTORY_PATH + Util.DATASET_FILE);
		if(doc == null)
			return null;
		NodeList dataSetList = doc.getElementsByTagName("dataSet");
		if(dataSetList == null)
			return null;
		
		int setNumber = Integer.parseInt(formId)-1;

		Node setNode = dataSetList.item(setNumber);
		Element setElem = (Element) setNode;
		return setElem.getAttribute("id");
	}

	private static Document initDoc(String fileName) {
		if(!Util.checkExternalMediaMounted()) {
			return null;
		}
		
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
			return null;
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return doc;

	}

	public String getDataSetId() {
		return dataSetId;
	}

	public abstract String getElementId(String element);
	public abstract String getComboId(String element);
	public abstract boolean isCombo(String element);
}



class SerialMappingHandler extends DhisMappingHandler {

	public SerialMappingHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getElementId(String element) {
		NodeList dataSetList = elementsDoc.getElementsByTagName("dataElement"); 

		int counter = 0;
		int elementNumber = Integer.parseInt(element) + 1;

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
						return elementElem.getAttribute("id");
					}
				}
			}
		}

		return null;
	}

	@Override
	public String getComboId(String element) {
		NodeList dataSetList = elementsDoc.getElementsByTagName("dataElement"); 

		int counter = 0;
		int elementNumber = Integer.parseInt(element) + 1;

		for (int i = 0; i < dataSetList.getLength(); i++) {
			Node elementNode = dataSetList.item(i);
			Element elementElem = (Element) elementNode;
			NodeList comboList = elementElem.getElementsByTagName("categoryOptionCombo");

			if(comboList.getLength() == 0) {
				counter++;
				if(elementNumber == counter) {
					return null;
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


	@Override
	public boolean isCombo(String element) {
		NodeList dataSetList = elementsDoc.getElementsByTagName("dataElement"); 

		int counter = 0;
		int elementNumber = Integer.parseInt(element) + 1;

		for (int i = 0; i < dataSetList.getLength(); i++) {
			Node elementNode = dataSetList.item(i);
			Element elementElem = (Element) elementNode;
			NodeList comboList = elementElem.getElementsByTagName("categoryOptionCombo");

			if(comboList.getLength() == 0) {
				counter++;
				if(elementNumber == counter) {
					return false;
				}
			} else {
				for (int j = 0; j < comboList.getLength(); j++) {
					counter++;
					if(elementNumber == counter) {
						return true;
					}
				}
			}
		}

		return false;
	}
}

class IndexedMappingHandler extends DhisMappingHandler {

	public IndexedMappingHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getElementId(String element) {
		NodeList dataSetList = elementsDoc.getElementsByTagName("dataElement"); 

		int elementNumber;

		if(element.contains(".")) {
			elementNumber = Integer.parseInt(element.split(".")[0]);
		} else {
			elementNumber = Integer.parseInt(element);
		}
		elementNumber =  elementNumber -1;

		if(elementNumber < dataSetList.getLength() ) {
			Node elementNode = dataSetList.item(elementNumber);
			Element elementElem = (Element) elementNode;
			return elementElem.getAttribute("id");
		}

		return null;
	}

	@Override
	public String getComboId(String element) {
		if(element.contains(".")) {
			NodeList dataSetList = elementsDoc.getElementsByTagName("dataElement"); 

			int elementNumber = Integer.parseInt(element.split(".")[0]);
			int comboNumber = Integer.parseInt(element.split(".")[1]);
			elementNumber =  elementNumber -1;
			comboNumber =  comboNumber -1;

			if(elementNumber < dataSetList.getLength() ) {
				Node elementNode = dataSetList.item(elementNumber);
				Element elementElem = (Element) elementNode;
				NodeList comboList = elementElem.getElementsByTagName("categoryOptionCombo");
				if (comboNumber < comboList.getLength()) {
					Node comboNode = comboList.item(comboNumber);
					Element comboElem = (Element) comboNode;
					return comboElem.getAttribute("id");
				}
			}
		} else {
			return null;
		}

		return null;
	}

	@Override
	public boolean isCombo(String element) {
		if(element.contains(".")) {
			NodeList dataSetList = elementsDoc.getElementsByTagName("dataElement"); 

			int elementNumber = Integer.parseInt(element.split(".")[0]);
			int comboNumber = Integer.parseInt(element.split(".")[1]);
			elementNumber =  elementNumber -1;
			comboNumber =  comboNumber -1;

			if(elementNumber < dataSetList.getLength() ) {
				Node elementNode = dataSetList.item(elementNumber);
				Element elementElem = (Element) elementNode;
				NodeList comboList = elementElem.getElementsByTagName("categoryOptionCombo");
				if (comboNumber < comboList.getLength()) {
					return true;
				}
			}
		} else {
			return false;
		}

		return false;
	}
}
