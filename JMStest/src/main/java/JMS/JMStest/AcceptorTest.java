package JMS.JMStest;

import java.util.concurrent.TimeUnit;

import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;

public class AcceptorTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		/*//MQTT acceptor code
		MQTT mqtt = new MQTT();
		MqttClient mqttC = new MqttClient("tcp://localhost:1884", "Topic");
		MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        connOpts.setUserName("user2");
        connOpts.setPassword("Swap@2019".toCharArray());
		mqtt.setHost("tcp://localhost:1884");
		mqtt.setUserName("user2");
		mqtt.setPassword("Swap@2019");
		BlockingConnection connection = mqtt.blockingConnection();
		connection.connect();
		Topic Topics[] = {};
//		MqttAsyncClient asynch = new MqttAsyncClient("tcp:\\localhost:1883", "Topic12");
		
		 connection.publish("Topic", "Hellou MQTT343>>+++>>".getBytes(), QoS.AT_LEAST_ONCE, false);
		 connection.publish("Topic", "Hello iam am MQTT m---sg?>>>".getBytes(), QoS.AT_MOST_ONCE, false);
		 
//		System.out.println("\n\n mqtt.getWillTopic >> "+ mqtt.getWillTopic());
		System.out.println("Published \n ");
		
		
//		connection.subscribe((org.fusesource.mqtt.client.Topic[]) Topics);
//		mqttC.subscribe("Topic");
		
		MqttMessage m = new MqttMessage();
//		System.out.println("recicved message ***** "+ m.);
		Message message = (Message) connection.receive();
		System.out.println("Message recicved >> "+ ((org.fusesource.mqtt.client.Message) message).getTopic());
//		byte[] payload = ((org.fusesource.mqtt.client.Message) message).getPayload();
		// process the message then:
		((org.fusesource.mqtt.client.Message) message).ack();
		
		
		   // Subscribe to  fidelityAds topic
	      Topic[] topics = {new Topic(FIDELITY_ADS_TOPIC, QoS.AT_LEAST_ONCE)};
	      connection.subscribe(topics);
		
		
*/	      
	      System.out.println("Connecting to Artemis using MQTT");
	      MQTT mqtt = new MQTT();
	      mqtt.setHost("tcp://localhost:1883");
	      mqtt.setUserName("user2");
	      mqtt.setPassword("Swap@2019");
	      BlockingConnection connection = mqtt.blockingConnection();
	      connection.connect();
	      System.out.println("Connected to Artemis");

	      // Subscribe to topics
	      org.fusesource.mqtt.client.Topic[] topics = {new org.fusesource.mqtt.client.Topic("Topic", QoS.AT_LEAST_ONCE),  new org.fusesource.mqtt.client.Topic("Topic", QoS.EXACTLY_ONCE)};
	      connection.subscribe((org.fusesource.mqtt.client.Topic[]) topics);
	      System.out.println("Subscribed to topics.");

	      // Publish Messages
	      String payload1 = "This is a new message 1>>>???> 14 Oct";
	      String payload2 = "This is a new message 2 >>??>114 Oct";
	      String payload3 = "This is a new message 3??>>>?? 114 Oct";

	      connection.publish("Topic", payload1.getBytes(), QoS.AT_LEAST_ONCE, false);
	      connection.publish("Topic", payload2.getBytes(), QoS.AT_MOST_ONCE, false);
	      connection.publish("Topic", payload3.getBytes(), QoS.AT_MOST_ONCE, false);
	      System.out.println("Sent messages.");

	      org.fusesource.mqtt.client.Message message1 =  connection.receive(5, TimeUnit.SECONDS);
	      org.fusesource.mqtt.client.Message  message2 = connection.receive(5, TimeUnit.SECONDS);
	      org.fusesource.mqtt.client.Message  message3 =  connection.receive(5, TimeUnit.SECONDS);
	      System.out.println("Received messages.");

	      System.out.println(new String(( message1).getPayload()));
	      System.out.println(new String(( message2).getPayload()));
	      System.out.println(new String(( message3).getPayload()));
	   }
		
	}


