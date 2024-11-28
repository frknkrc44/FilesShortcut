package org.blinksd.filesshortcut;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

public class ShortcutActivity extends Activity {
    private static final String GOOGLE_DOCUMENTSUI_PKG = "com.google.android.documentsui";
    private static final String AOSP_DOCUMENTSUI_PKG = "com.android.documentsui";
    private static final String FILES_ACTIVITY = "com.android.documentsui.files.FilesActivity";

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // it calls less functions, don't touch
        super.onRestart();

        Intent launchIntent = new Intent();
        try {
            // check for Google DocumentsUI
            getPackageManager().getPackageInfo(GOOGLE_DOCUMENTSUI_PKG, 0);
            launchIntent.setComponent(new ComponentName(GOOGLE_DOCUMENTSUI_PKG, FILES_ACTIVITY));
        } catch (Throwable ignore) {
            try {
                // check for AOSP DocumentsUI
                getPackageManager().getPackageInfo(AOSP_DOCUMENTSUI_PKG, 0);
                launchIntent.setComponent(new ComponentName(AOSP_DOCUMENTSUI_PKG, FILES_ACTIVITY));
            } catch (Throwable ignored) {
                // if nothing found, just finish
                finish();
                return;
            }
        }

        // start the FilesActivity and finish this activity
        launchIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(launchIntent);
        finish();
    }
}
