package org.dhis.smssync.services;

import org.dhis.smssync.util.ServicesConstants;
import org.dhis.smssync.util.Util;

import android.content.Intent;
import android.util.Log;

public class MappingFilesReceiverService extends SmsSyncServices {
	
	private static String CLASS_TAG = MappingFilesReceiverService.class.getSimpleName();
	
	private Intent mappingStatusIntent; // holds the status of the sync and sends it to

    public MappingFilesReceiverService() {	
		super(CLASS_TAG);
		mappingStatusIntent = new Intent(ServicesConstants.MAPPING_DOWNLOAD_ACTION);
	}


	@Override
	protected void executeTask(Intent intent) {
		Log.i(CLASS_TAG, "executeTask(); get mapping files");
		
		if(intent != null){
			int status = Util.getDhisMappingFiles(MappingFilesReceiverService.this);
			mappingStatusIntent.putExtra("mappingstatus", status);
            sendBroadcast(mappingStatusIntent);
		}
	
	}

}
