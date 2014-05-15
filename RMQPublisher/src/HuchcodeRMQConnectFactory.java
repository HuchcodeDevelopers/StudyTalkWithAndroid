import java.util.Enumeration;
import java.util.ResourceBundle;

import com.rabbitmq.client.ConnectionFactory;


public class HuchcodeRMQConnectFactory {

	public static void setParamForConnectionFactory(ConnectionFactory factory) {
		  String host = null;
		  int port = 5672;
		  String virtualHost = null;
		  String username = null;
		  String password = null;
		  	  
		  ResourceBundle rb = ResourceBundle.getBundle("access");
		  Enumeration <String> keys = rb.getKeys();
		  while (keys.hasMoreElements()) {
			  String key = keys.nextElement();
			  String value = rb.getString(key);
			  switch (key) {
			  	case "Host" :
			  		host = value;
			  		break;
			  	case "Port" :
			  		port = Integer.parseInt(value);
			  		break;
			  	case "VirtualHost" :
			  		virtualHost = value;
			  		break;
			  	case "Username" :
			  		username = value;
			  		break;
			  	case "Password" :
			  		password = value;
			  		break;
			  	default :
			  		break;

			  }
		  }

	    factory.setHost(host);
	    factory.setPort(port);
	    factory.setVirtualHost(virtualHost);
	    factory.setUsername(username);
	    factory.setPassword(password);		
	}
	
}
