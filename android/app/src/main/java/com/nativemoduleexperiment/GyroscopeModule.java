package com.nativemoduleexperiment;

/*
    Import the native modules we want to use
 */
import android.content.Context;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

/*
    Import the React Native bridge classes
 */
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.HashMap;
import java.util.Map;


public class GyroscopeModule extends ReactContextBaseJavaModule {

    private static final String GRAVITY_SENSOR_CHANGED = "GRAVITY_SENSOR_CHANGED";
    private static final String GRAVITY_SENSOR_EVENT_KEY = "GRAVITY_SENSOR_EVENT_KEY";

    private final SensorManager sensorManager;
    private final Sensor gravitySensor;
    private SensorEventListener listener;
    private ReactApplicationContext reactContext;

    public GyroscopeModule(ReactApplicationContext reactContext) {
        super(reactContext);
        sensorManager = (SensorManager) reactContext.getSystemService(Context.SENSOR_SERVICE);
        gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "Gyroscope";
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put(GRAVITY_SENSOR_EVENT_KEY, GRAVITY_SENSOR_CHANGED);
        return constants;
    }

    @ReactMethod
    public void start() {
        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                WritableMap map = Arguments.createMap();
                float forceX = event.values[0];
                float forceY = event.values[1];
                float forceZ = event.values[2];
                map.putDouble("forceX", forceX);
                map.putDouble("forceY", forceY);
                map.putDouble("forceZ", forceZ);
                reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(GRAVITY_SENSOR_CHANGED, map);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        sensorManager.registerListener(listener, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @ReactMethod
    public void stop() {
        if (listener != null) {
            sensorManager.unregisterListener(listener);
        }
    }

}
