package tutorial.cs5551.com.homeapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;

/**
 * Helper to manage scheduling the reminder alarm
 */
public class AlarmScheduler {

   /* */

    /**
     * Schedule a reminder alarm at the specified time for the given task.
     *
     * @param context   Local application or activity context
     * @param alarmTime Alarm start time
     */
   /*@param uri Uri referencing the task in the content provider*/
   /* public static void scheduleAlarm(Context context, long alarmTime, Uri reminderTask) {
        //Schedule the alarm. Will update an existing item for the same task.
        AlarmManager manager = AlarmManagerProvider.getAlarmManager(context);

        PendingIntent operation =
                ReminderAlarmService.getReminderPendingIntent(context, reminderTask);

        manager.setExact(AlarmManager.RTC, alarmTime, operation);
    }*/
    public static void scheduleAlarm(Context context, long alarmTime) {
        //Schedule the alarm. Will update an existing item for the same task.
        AlarmManager manager = AlarmManagerProvider.getAlarmManager(context);

        PendingIntent operation =
                ReminderAlarmService.getReminderPendingIntent(context);

        manager.setExact(AlarmManager.RTC, alarmTime, operation);
    }
}
