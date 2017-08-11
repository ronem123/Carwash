package com.ronem.carwash.onesignal;

/**
 * Created by ram on 8/12/17.
 */

import android.content.Context;

import com.onesignal.OneSignal;

import org.json.JSONObject;

/**
 * Created by ronem on 5/19/17.
 */

public class OneSignalOpenHandler implements OneSignal.NotificationOpenedHandler {
    private Context context;

    public OneSignalOpenHandler(Context context) {
        this.context = context;
    }

    @Override
    public void notificationOpened(String message, JSONObject additionalData, boolean isActive) {
//        try {
//
//            /**
//             * Only show the result if the user is logged in
//             * other wise do not show the notification to the free user
//             */
//            if (RegisteredUserData.getUserData() != null) {
//                if (additionalData != null) {
//
//                    Log.d("Notification", "Full additionalData:\n" + additionalData.toString());
//
//                    if (additionalData.has("id") && additionalData.has("title")) {
//                        int id = additionalData.getInt("id");
//                        String title = additionalData.getString("title");
//
//                        NotificationUtils notificationUtils = new NotificationUtils(context);
//
//                        Intent intent = new Intent(context, MovieDetail.class);
//                        intent.putExtra(StaticStorage.MOVIE_ID, id);
//                        notificationUtils.showNotificationMessage(1, title, message, "", intent, "");
//                    }
//                }
//            }
//        } catch (Throwable t) {
//            t.printStackTrace();
//        }
    }
}
