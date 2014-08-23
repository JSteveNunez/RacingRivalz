package com.artoos.racing.listener;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.text.SpannableStringBuilder;
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
            Log.e(TAG, "QuizListenerService failed to connect to GoogleApiClient.");
            return;
        }

        for (DataEvent event : events) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                DataItem dataItem = event.getDataItem();
                DataMap dataMap = DataMapItem.fromDataItem(dataItem).getDataMap();
                if (dataMap.getBoolean(QUESTION_WAS_ANSWERED)
                        || dataMap.getBoolean(QUESTION_WAS_DELETED)) {
                    // Ignore the change in data; it is used in MainActivity to update
                    // the question's status (i.e. was the answer right or wrong or left blank).
                    continue;
                }
                String question = dataMap.getString(QUESTION);
                int questionIndex = dataMap.getInt(QUESTION_INDEX);
                int questionNum = questionIndex + 1;
                String[] answers = dataMap.getStringArray(ANSWERS);
                int correctAnswerIndex = dataMap.getInt(CORRECT_ANSWER_INDEX);
                Intent deleteOperation = new Intent(this, DeleteQuestionService.class);
                deleteOperation.setData(dataItem.getUri());
                PendingIntent deleteIntent = PendingIntent.getService(this, 0,
                        deleteOperation, PendingIntent.FLAG_UPDATE_CURRENT);
                // First page of notification contains question as Big Text.
                Notification.BigTextStyle bigTextStyle = new Notification.BigTextStyle()
                        .setBigContentTitle(getString(R.string.question, questionNum))
                        .bigText(question);
                Notification.Builder builder = new Notification.Builder(this)
                        .setStyle(bigTextStyle)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setLocalOnly(true)
                        .setDeleteIntent(deleteIntent);

                // Add answers as actions.
                Notification.WearableExtender wearableOptions = new Notification.WearableExtender();
                for (int i = 0; i < answers.length; i++) {
                    Notification answerPage = new Notification.Builder(this)
                            .setContentTitle(question)
                            .setContentText(answers[i])
                            .extend(new Notification.WearableExtender()
                                    .setContentAction(i))
                            .build();

                    boolean correct = (i == correctAnswerIndex);
                    Intent updateOperation = new Intent(this, UpdateQuestionService.class);
                    // Give each intent a unique action.
                    updateOperation.setAction("question_" + questionIndex + "_answer_" + i);
                    updateOperation.setData(dataItem.getUri());
                    updateOperation.putExtra(UpdateQuestionService.EXTRA_QUESTION_INDEX,
                            questionIndex);
                    updateOperation.putExtra(UpdateQuestionService.EXTRA_QUESTION_CORRECT, correct);
                    PendingIntent updateIntent = PendingIntent.getService(this, 0, updateOperation,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    Notification.Action action = new Notification.Action.Builder(
                            questionNumToDrawableId.get(i), null, updateIntent)
                            .build();
                    wearableOptions.addAction(action).addPage(answerPage);
                }
                builder.extend(wearableOptions);
                Notification notification = builder.build();
                ((NotificationManager) getSystemService(NOTIFICATION_SERVICE))
                        .notify(questionIndex, notification);
            } else if (event.getType() == DataEvent.TYPE_DELETED) {
                Uri uri = event.getDataItem().getUri();
                // URI's are of the form "/question/0", "/question/1" etc.
                // We use the question index as the notification id.
                int notificationId = Integer.parseInt(uri.getLastPathSegment());
                ((NotificationManager) getSystemService(NOTIFICATION_SERVICE))
                        .cancel(notificationId);
            }
            // Delete the quiz report, if it exists.
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE))
                    .cancel(QUIZ_REPORT_NOTIF_ID);
        }
        googleApiClient.disconnect();
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        String path = messageEvent.getPath();
        if (path.equals(QUIZ_EXITED_PATH)) {
            // Remove any lingering question notifications.
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE)).cancelAll();
        }
        if (path.equals(QUIZ_ENDED_PATH) || path.equals(QUIZ_EXITED_PATH)) {
            // Quiz ended - display overall results.
            DataMap dataMap = DataMap.fromByteArray(messageEvent.getData());
            int numCorrect = dataMap.getInt(NUM_CORRECT);
            int numIncorrect = dataMap.getInt(NUM_INCORRECT);
            int numSkipped = dataMap.getInt(NUM_SKIPPED);

            Notification.Builder builder = new Notification.Builder(this)
                    .setContentTitle(getString(R.string.quiz_report))
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setLocalOnly(true);
            SpannableStringBuilder quizReportText = new SpannableStringBuilder();
            appendColored(quizReportText, String.valueOf(numCorrect), R.color.dark_green);
            quizReportText.append(" " + getString(R.string.correct) + "\n");
            appendColored(quizReportText, String.valueOf(numIncorrect), R.color.dark_red);
            quizReportText.append(" " + getString(R.string.incorrect) + "\n");
            appendColored(quizReportText, String.valueOf(numSkipped), R.color.dark_yellow);
            quizReportText.append(" " + getString(R.string.skipped) + "\n");

            builder.setContentText(quizReportText);
            if (!path.equals(QUIZ_EXITED_PATH)) {
                // Don't add reset option if user exited quiz (there might not be a quiz to reset!).
                builder.addAction(R.drawable.ic_launcher,
                        getString(R.string.reset_quiz), getResetQuizPendingIntent());
            }
            ((NotificationManager) getSystemService(NOTIFICATION_SERVICE))
                    .notify(QUIZ_REPORT_NOTIF_ID, builder.build());
        }
    }
}
