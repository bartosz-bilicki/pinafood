package com.byoutline.pinafood.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.byoutline.pinafood.PinAFoodApp;
import com.byoutline.pinafood.events.PinAddedEvent;
import com.byoutline.pinafood.R;
import com.byoutline.pinafood.fragments.AddPinFragment;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import hugo.weaving.DebugLog;
import timber.log.Timber;


public class AddPinActivity extends Activity {

    @Inject
    Bus bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pin);
        PinAFoodApp.doDaggerInject(this);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new AddPinFragment())
                    .commit();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    @DebugLog
    @Subscribe
    public void pinAdded(PinAddedEvent event) {
        Timber.d("pin added");
        setResult(RESULT_OK);
        finish();
    }
}
