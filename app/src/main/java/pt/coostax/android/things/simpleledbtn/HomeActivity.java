/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pt.coostax.android.things.simpleledbtn;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.google.android.things.contrib.driver.button.Button;
import com.google.android.things.contrib.driver.button.ButtonInputDriver;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;

/**
 * Turns a LED on and off when pressing a button
 *
 * Modified to use a single state push button
 *
 * @author Paulo Costa
 *
 */
public class HomeActivity extends Activity {
    private static final String TAG = HomeActivity.class.getSimpleName();
    private static final String BTN_GPIO_PIN = "BCM21";
    private static final String LED_GPIO_PIN = "BCM6";

    //Use GPIO for LED
    private Gpio mLedGpio;
    //Use Driver Library for button
    private ButtonInputDriver mButtonInputDriver;
    private boolean ledState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Starting SimpleButton Activity");

        PeripheralManagerService pioService = new PeripheralManagerService();
        ledState = false;

        try{
            Log.i(TAG, "Configuring GPIO");
            //set led PIN and direction
            mLedGpio = pioService.openGpio(LED_GPIO_PIN);
            mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            //configure button, set event to space key
            Log.i(TAG, "Configuring button driver");
            mButtonInputDriver = new ButtonInputDriver(
                    BTN_GPIO_PIN,
                    Button.LogicState.PRESSED_WHEN_LOW,
                    KeyEvent.KEYCODE_SPACE
            );
            mButtonInputDriver.register();
        } catch (IOException e) {
            Log.e(TAG,"ERROR configuring GPIO pin", e);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        //turn LED off
        setLedValue(false);
        //Close driver and unregister - button
        if (mButtonInputDriver != null) {
            mButtonInputDriver.unregister();
            try {
                mButtonInputDriver.close();
            } catch (IOException e) {
                Log.e(TAG, "Error closing Button driver", e);
            } finally {
                mButtonInputDriver = null;
            }
            mButtonInputDriver = null;
        }
        if (mLedGpio != null) {
            try {
                mLedGpio.close();
            } catch (IOException e) {
                Log.e(TAG, "Error closing LED GPIO", e);
            } finally{
                mLedGpio = null;
            }
            mLedGpio = null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_SPACE) {
            if(ledState){
                // LED is on, turn off
                setLedValue(false);
            }else {
                // LED is off Turn on
                setLedValue(true);
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * Sets LED value
     * @param val value to set on led
     */
    private void setLedValue(boolean val){
        try {
            mLedGpio.setValue(val);
            ledState = val;
        } catch (IOException e) {
            Log.e(TAG,"Unable to set LED value",e);
        }
    }
}
