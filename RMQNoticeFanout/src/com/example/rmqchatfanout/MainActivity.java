package com.example.rmqchatfanout;

import java.io.UnsupportedEncodingException;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rmqchatfanout.MessageConsumer.OnReceiveMessageHandler;

public class MainActivity extends ActionBarActivity {
	
    private MessageConsumer mConsumer;
    private TextView mOutput;
    
	@Override
	protected void onCreate(Bundle savedInstanceState)  {
		
		Resources res = getResources();
		String RMQ_HOSTNAME = res.getString( R.string.RMQ_HOSTNAME );
		String EXCHANGE_NAME = res.getString( R.string.EXCHANGE_NAME );
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
        //The output TextView we'll use to display messages
        mOutput =  (TextView) findViewById(R.id.output);
 
        //Create the consumer
        mConsumer = new MessageConsumer(RMQ_HOSTNAME,
        		EXCHANGE_NAME,
                "fanout");
 
        //Connect to broker
        mConsumer.connectToRabbitMQ();
 
        //register for messages
        mConsumer.setOnReceiveMessageHandler(new OnReceiveMessageHandler(){
 
            public void onReceiveMessage(byte[] message) {
                String text = "";
                try {
                    text = new String(message, "UTF8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                mOutput.append("\n"+text);
            }
        });
 
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	
    @Override
    protected void onResume() {
        super.onPause();
        mConsumer.connectToRabbitMQ();
    }
 
    @Override
    protected void onPause() {
        super.onPause();
        mConsumer.dispose();
    }

    
}
