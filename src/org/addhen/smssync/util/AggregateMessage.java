package org.addhen.smssync.util;

import java.util.HashMap;
import java.util.Map;

 public abstract class AggregateMessage {
	// TODO Auto-generated constructor stub - setters/getters
	public   String formId;
	public   String periodText;
	public	 Map<String, String> dataValues = new HashMap<String, String>();
	protected String body;
	
	/**
	 * 
	 */
	public AggregateMessage() {
	}
	/**
	 * 
	 * @param formId
	 * @param periodText
	 * @param dataValues
	 */
	public AggregateMessage(String formId, String periodText,Map<String,String>  dataValues) {
		this.formId = formId;
		this.periodText = periodText;
		this.dataValues = dataValues;
	}
	
	public AggregateMessage(String body) {
		this.body = body;
	}
	/**
	 * 
	 */
	public abstract boolean parse();	
	public abstract String getXMLString();
}
