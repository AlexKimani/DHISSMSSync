package org.dhis.smssync.util;

import android.os.Environment;

public class DhisConstants {
	
	public static final String PIPED_REGEX = "\\w+#\\w+#((\\w+\\|)*(\\w+))";
	public static final String PAIR_REGEX = "\\w+\\s+(((\\w+\\.\\w+=\\w+|\\w+=\\w+),\\s*)*((\\w+\\.\\w+=\\w+|\\w+=\\w+)){1})";
	
	public static final String EXTERNAL_STORAGE = Environment.getExternalStorageDirectory() + "";
	public static final String EXPORT_DIRECTORY = EXTERNAL_STORAGE + "/DHIS_EXPORT/";
	public static final String DATASET_DIRECTORY = EXTERNAL_STORAGE + "/Android/data/" + "org.dhis.smssync" + "/mappingfiles/";
	
	public static final String DATASET_FILE = "dataSets.xml";
	public static final String DATASET_PATH = "/api/" + DATASET_FILE;
	
	public static final String POST_PATH = "/api/dataValueSets/";
	public static final String PHONE_EXTENSION = "?phone=";
}
