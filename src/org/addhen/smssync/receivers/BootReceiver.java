
package org.addhen.smssync.receivers;

import org.addhen.smssync.Prefrences;
import org.addhen.smssync.services.AutoSyncService;
import org.addhen.smssync.services.ScheduleServices;
import org.addhen.smssync.services.SmsSyncServices;
import org.addhen.smssync.util.ServicesConstants;
import org.addhen.smssync.util.Util;

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
