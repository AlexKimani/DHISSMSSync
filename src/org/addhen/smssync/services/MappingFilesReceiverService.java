package org.addhen.smssync.services;

import org.addhen.smssync.R;
import org.addhen.smssync.util.Util;

import android.content.Intent;
import android.util.Log;

public class MappingFilesReceiverService extends SmsSyncServices {
	
	private static String CLASS_TAG = MappingFilesReceiverService.class.getSimpleName();

    public MappingFilesReceiverService() {
		super(CLASS_TAG);
	}


	@Override
	protected void executeTask(Intent intent) {
		Log.i(CLASS_TAG, "executeTask(); get mapping files");
		
		if(intent != null){
			Util.showToast(MappingFilesReceiverService.this, R.string.downloading_mapping_files);
			Util.getDhisMappingFiles(MappingFilesReceiverService.this);
		}
	
	}

}
