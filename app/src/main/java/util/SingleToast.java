package util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Abdalla maged
 * on 01,March,2019
 */
    public enum SingleToast {
        INSTANCE;

        private Toast currentToast;
        private String currentMessage;

        public void show(Context context, String message, int duration) {
            if (message.equals(currentMessage)) {
                currentToast.cancel();
            }
            currentToast = Toast.makeText(context, message, duration);
            currentToast.show();

            currentMessage = message;
        }
    }
