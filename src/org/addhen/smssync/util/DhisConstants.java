package org.addhen.smssync.util;

import android.os.Environment;

public class DhisConstants {
	
	public static final String PIPED_REGEX = "\\w+#\\w+#((\\w+\\|)*(\\w+))";
	public static final String PAIR_REGEX = "\\w+\\s+(((\\w+\\.\\w+=\\w+|\\w+=\\w+),\\s*)*((\\w+\\.\\w+=\\w+|\\w+=\\w+)){1})";

	public static final String DATASET_DIRECTORY_PATH = Environment.getExternalStorageDirectory() + "/Android/data/" + "org.addhen.smssync" + "/dhismappingfiles/";
	public static final String DATASET_FILE = "dataSets.xml";
	public static final String DATASET_FILE_URL = "http://apps.dhis2.org/demo/api/dataSets.xml";
	public static final String EXPORT_DIRECTORY_PATH = Environment.getExternalStorageDirectory() + "/dhisexport/";
}
