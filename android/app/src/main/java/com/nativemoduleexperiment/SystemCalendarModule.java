package com.nativemoduleexperiment;

import android.database.Cursor;
import android.provider.CalendarContract;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nilswloka on 16.12.16.
 */

public class SystemCalendarModule extends ReactContextBaseJavaModule {

    public SystemCalendarModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "SystemCalendar";
    }

    @ReactMethod
    public void getAllCalendars(Promise promise) {
        String[] projection = new String[] {
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME
        };
        Cursor cursor = getReactApplicationContext().getContentResolver().query(
                CalendarContract.Calendars.CONTENT_URI,
                projection,
                null,
                new String[]{},
                null
        );
        List<String> calendars = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                calendars.add(cursor.getString(0));
            }
            promise.resolve(calendars);
        } else {
            promise.reject("NOCALS", "No calendars found");
        }

    }


}
