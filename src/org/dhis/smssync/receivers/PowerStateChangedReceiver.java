
package org.dhis.smssync.receivers;

import org.dhis.smssync.Prefrences;
import org.dhis.smssync.services.AutoSyncService;
import org.dhis.smssync.util.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * The manifest Receiver is used to detect changes in battery state. When the
 * system broadcasts a "Battery Low" warning we turn off we stop all enabled
 * services When the system broadcasts "Battery OK" to indicate the battery has
 * returned to an okay state, we start all enabled services
 */

public class PowerStateChangedReceiver extends BroadcastReceiver {

    private boolean batteryLow;

    private Intent smsSyncAutoSyncServiceIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        batteryLow = intent.getAction().equals(Intent.ACTION_BATTERY_LOW);
        // load current settings
        Prefrences.loadPreferences(context);
        if (batteryLow) {
            // is smssync enabled
            if (Prefrences.enabled) {

                // clear all notifications
                Util.clearNotify(context);

                // Stop the service that pushes pending messages
                if (Prefrences.enableAutoSync) {
                    smsSyncAutoSyncServiceIntent = new Intent(context, AutoSyncService.class);
                    context.stopService(smsSyncAutoSyncServiceIntent);
                }
            }
        }
    }
}
