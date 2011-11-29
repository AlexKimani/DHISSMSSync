
package org.dhis.smssync.receivers;

import org.dhis.smssync.Prefrences;
import org.dhis.smssync.services.AutoSyncService;
import org.dhis.smssync.services.ScheduleServices;
import org.dhis.smssync.services.SmsSyncServices;
import org.dhis.smssync.util.ServicesConstants;
import org.dhis.smssync.util.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * This Receiver class listens for system boot. If smssync has been enabled run
 * the app.
 */
public class BootReceiver extends BroadcastReceiver {

    private boolean isConnected;

    @Override
    public void onReceive(Context context, Intent intent) {

        // load current settings
        Prefrences.loadPreferences(context);

        // is smssync enabled
        if (Prefrences.enabled) {

            // show notification
            Util.showNotification(context);

            // start pushing pending messages
            isConnected = Util.isConnected(context);

            // do we have data network?
            if (isConnected) {
                // Push any pending messages now that we have connectivity
                if (Prefrences.enableAutoSync) {
                    
                    SmsSyncServices.sendWakefulTask(context, AutoSyncService.class);
                    // start the scheduler for auto sync service
                    long interval = (Prefrences.autoTime * 60000);
                    new ScheduleServices(context, intent, AutoSyncScheduledReceiver.class,
                            interval, ServicesConstants.AUTO_SYNC_SCHEDULED_SERVICE_REQUEST_CODE, 0);
                }
            }
        }
    }
}
