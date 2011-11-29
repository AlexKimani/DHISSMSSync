package org.dhis.smssync.util;

import java.util.HashMap;
import java.util.Map;

 public abstract class AggregateMessage {
	protected   String formId;
	protected   String periodText;
	protected	Map<String, String> dataValues = new HashMap<String, String>();
	protected 	String body;
	
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
	 * @return the formId
	 */
	public String getFormId() {
		return formId;
	}
	/**
	 * @return the periodText
	 */
	public String getPeriodText() {
		return periodText;
	}
	/**
	 * @return the dataValues
	 */
	public Map<String, String> getDataValues() {
		return dataValues;
	}
	/**
	 * 
	 */
	public abstract boolean parse();	
	public abstract String getXMLString();	
}
