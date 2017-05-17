package net.braingang.perkyblue;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public final String LOG_TAG = getClass().getName();

    public enum LED {GREEN_LED, RED_LED, YELLOW_LED};

    private boolean _discoveryActive = false;
    private ArrayList<BluetoothDevice> _deviceList = new ArrayList<BluetoothDevice>();
    private ArrayList<String> _nameList = new ArrayList<String>();

    private ListView _deviceListView;

    private BtArrayAdapter _btArrayAdapter;

    private BluetoothAdapter _bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice temp = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (_deviceList.contains(temp)) {
                    Log.i(LOG_TAG, "skipping:" + temp.getName() + ":" + temp.getAddress());
                } else {
                    _deviceList.add(temp);
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(intent.getAction())) {
                _discoveryActive = true;
                _deviceList.clear();
                Log.d(LOG_TAG, "discovery start");
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(intent.getAction())) {
                Log.d(LOG_TAG, "discovery finish:" + _deviceList.size());
                _discoveryActive = false;

                _btArrayAdapter = new BtArrayAdapter(getBaseContext(), _deviceList);
                _deviceListView.setAdapter(_btArrayAdapter);

                _deviceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> adapter, View view, int ndx1, long ndx2) {
                        Log.i(LOG_TAG, "hit hit hit:" + ndx1 + ":" + ndx2);
//                        String selectedFromList =(String) (lv.getItemAtPosition(myItemInt));
                    }
                });
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonDiscoveryCancel).setOnClickListener(this);
        findViewById(R.id.buttonDiscoveryStart).setOnClickListener(this);
        findViewById(R.id.buttonToggleGreenLed).setOnClickListener(this);
        findViewById(R.id.buttonToggleRedLed).setOnClickListener(this);
        findViewById(R.id.buttonToggleYellowLed).setOnClickListener(this);

        _deviceListView = (ListView) findViewById(R.id.lvBtDevice);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonDiscoveryCancel:
                discoveryStop();
                break;
            case R.id.buttonDiscoveryStart:
                discoveryStart();
                break;
            case R.id.buttonToggleGreenLed:
                toggleLed(LED.GREEN_LED);
                break;
            case R.id.buttonToggleRedLed:
                toggleLed(LED.RED_LED);
                break;
            case R.id.buttonToggleYellowLed:
                toggleLed(LED.YELLOW_LED);
                break;
            default:
                Log.i(LOG_TAG, "unknown click event");
        }
    }

    private void discoveryStart() {
        Log.d(LOG_TAG, "discoveryStart");

        if (_bluetoothAdapter == null) {
            Log.i(LOG_TAG, "unable to run w/null BT adapter");
            return;
        }

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(receiver, filter);

        _bluetoothAdapter.startDiscovery();
    }

    private void discoveryStop() {
        Log.d(LOG_TAG, "discoveryStop");

        if (_bluetoothAdapter == null) {
            Log.i(LOG_TAG, "unable to run w/null BT adapter");
            return;
        }
    }

    private void toggleLed(LED target) {
        switch(target) {
            case GREEN_LED:
                break;
            case RED_LED:
                break;
            case YELLOW_LED:
                break;
        }
    }
}