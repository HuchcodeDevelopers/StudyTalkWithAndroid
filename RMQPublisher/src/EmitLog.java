import java.util.Calendar;
import java.util.ResourceBundle;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


public class EmitLog {

	private static final String EXCHANGE_NAME = ResourceBundle.getBundle("access").getString("EXCHANGE_NAME");

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		HuchcodeRMQConnectFactory.setParamForConnectionFactory(factory);

		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		
		String[] strings = new String[5];
		
		strings[0] = "[Notice]";
		strings[1] = "Hi Everyone!";
		strings[2] = "The time is ";
		strings[3] = DateFormatUtils.format(Calendar.getInstance(), "yyyyMMddHHmmss");
		strings[4] = "\n I love you so much!!! 사랑 우정 믿음!! ";
		
		String message = null;
		for (int k = 0; k < 5; k++ ) {
			message = getMessage(strings, k);
			
			channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");
		}
		
		channel.close();
		connection.close();
	}
  
	private static String getMessage(String[] strings, int k){
		if (strings.length < 1)
			    return "info: Hello World!";
		return joinStrings(strings, " ", k);
	}
  
	private static String joinStrings(String[] strings, String delimiter, int k) {
		int length = strings.length;
		if (length == 0) return "";
		StringBuilder words = new StringBuilder(strings[0]);
	    words.append(delimiter).append(k);
		for (int i = 1; i < length; i++) {
		    words.append(delimiter).append(strings[i]);
		}
		return words.toString();
	}
}
