package com.example.rmqchatfanout;

import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rmqchatfanout.MessageConsumer.OnReceiveMessageHandler;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ChatAppActivity extends Activity {
	
	
	private MessageConsumer mConsumer;
	private TextView mOutput;
	private String QUEUE_NAME="huchcodechat0";
	private String message = "";
	private String name = "";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		Resources res = getResources();
		String RMQ_HOSTNAME = res.getString( R.string.RMQ_HOSTNAME );	
		String EXCHANGE_NAME = res.getString( R.string.EXCHANGE_NAME );
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
		Toast.makeText(ChatAppActivity.this, "RabbitMQ Chat Service!", Toast.LENGTH_LONG).show();
		final EditText etv1 = (EditText) findViewById(R.id.out3);
		etv1.setOnKeyListener(new OnKeyListener() {
	 
		public boolean onKey(View v, int keyCode, KeyEvent event) {
				// If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
						(keyCode == KeyEvent.KEYCODE_ENTER)) {
					// Perform action on key press
					name = etv1.getText().toString();
					etv1.setText("");
					etv1.setVisibility(View.GONE);
					return true;
				}
			return false;
			}
		});
		
	 
		final EditText etv = (EditText) findViewById(R.id.out2);
		etv.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
						(keyCode == KeyEvent.KEYCODE_ENTER)) {
					// Perform action on key press
					message = name + ": " + etv.getText().toString();
					new SendMessage().execute(message);
					etv.setText("");
					return true;
				}
				return false;
			}
		});
		
		
		//The output TextView we'll use to display messages
		mOutput = (TextView) findViewById(R.id.output);
	 
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


	private class SendMessage extends AsyncTask<String, Void, Void >{
			 
		@Override
		protected Void doInBackground(String... Message) {
			
			Resources res = getResources();
			String RMQ_HOSTNAME = res.getString( R.string.RMQ_HOSTNAME );	
			String EXCHANGE_NAME = res.getString( R.string.EXCHANGE_NAME );
				
			try {
			 
				ConnectionFactory factory = new ConnectionFactory();

			 
				//my internet connection is a bit restrictive so I have use an external server
				// which has RabbitMQ installed on it. So I use "setUsername" and "setPassword"
				factory.setHost(RMQ_HOSTNAME);
				factory.setPort(5672);
				factory.setVirtualHost("/huchcodeTestingMQ");
	            factory.setUsername("huchcode");
	            factory.setPassword("kitiwit7");				
			
				Connection connection = factory.newConnection();
				Channel channel = connection.createChannel();
				channel.exchangeDeclare(EXCHANGE_NAME, "fanout"); 

				String tempstr="";
				for(int i=0;i<Message.length;i++) tempstr+=Message[i];
			 
				channel.basicPublish(EXCHANGE_NAME,QUEUE_NAME, null, tempstr.getBytes());

				channel.close();
				connection.close();
			 
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			// TODO Auto-generated method stub
			 
			 
			return null;
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
