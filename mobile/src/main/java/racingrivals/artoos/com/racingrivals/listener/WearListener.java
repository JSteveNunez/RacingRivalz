package racingrivals.artoos.com.racingrivals.listener;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.data.FreezableUtils;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by jstevenunez on 023, 8/23/2014.
 */
public class WearListener extends WearableListenerService {
    public static final String TAG = "com.artoos.RacingRival.WearListener";
    public static final int CONNECT_TIMEOUT_MS = 100;

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        final List<DataEvent> events = FreezableUtils.freezeIterable(dataEvents);
        dataEvents.close();

        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .build();

        ConnectionResult connectionResult = googleApiClient.blockingConnect(CONNECT_TIMEOUT_MS,
                TimeUnit.MILLISECONDS);
        if (!connectionResult.isSuccess()) {
            Log.e(TAG, "WearListener failed to connect to GoogleApiClient.");
            return;
        }

        for (DataEvent event : events) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                DataItem dataItem = event.getDataItem();
                DataMap dataMap = DataMapItem.fromDataItem(dataItem).getDataMap();
                double steps = dataMap.getDouble("steps");
                Intent intent = new Intent();
                intent.setAction("UPDATE_TV");
                intent.putExtra("steps", steps);
                sendBroadcast(intent);
            } else if (event.getType() == DataEvent.TYPE_DELETED) {

            }
        }
        googleApiClient.disconnect();
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        String path = messageEvent.getPath();
//        if (path.equals(QUIZ_EXITED_PATH)) {
//            // Remove any lingering question notifications.
//            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).cancelAll();
//        }
//        if (path.equals(QUIZ_ENDED_PATH) || path.equals(QUIZ_EXITED_PATH)) {
//            // Quiz ended - display overall results.
//            DataMap dataMap = DataMap.fromByteArray(messageEvent.getData());
//            int numCorrect = dataMap.getInt(NUM_CORRECT);
//            int numIncorrect = dataMap.getInt(NUM_INCORRECT);
//            int numSkipped = dataMap.getInt(NUM_SKIPPED);
//
//            Notification.Builder builder = new Notification.Builder(this)
//                    .setContentTitle(getString(R.string.quiz_report))
//                    .setSmallIcon(R.drawable.ic_launcher)
//                    .setLocalOnly(true);
//            SpannableStringBuilder quizReportText = new SpannableStringBuilder();
//            appendColored(quizReportText, String.valueOf(numCorrect), R.color.dark_green);
//            quizReportText.append(" " + getString(R.string.correct) + "\n");
//            appendColored(quizReportText, String.valueOf(numIncorrect), R.color.dark_red);
//            quizReportText.append(" " + getString(R.string.incorrect) + "\n");
//            appendColored(quizReportText, String.valueOf(numSkipped), R.color.dark_yellow);
//            quizReportText.append(" " + getString(R.string.skipped) + "\n");
//
//            builder.setContentText(quizReportText);
//            if (!path.equals(QUIZ_EXITED_PATH)) {
//                // Don't add reset option if user exited quiz (there might not be a quiz to reset!).
//                builder.addAction(R.drawable.ic_launcher,
//                        getString(R.string.reset_quiz), getResetQuizPendingIntent());
//            }
//            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE))
//                    .notify(QUIZ_REPORT_NOTIF_ID, builder.build());
//        }
    }
}
