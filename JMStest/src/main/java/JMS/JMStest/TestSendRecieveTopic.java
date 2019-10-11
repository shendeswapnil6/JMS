package JMS.JMStest;

import java.util.Hashtable;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TestSendRecieveTopic {

	public static void main(String[] args) throws NamingException, JMSException, InterruptedException {
		// TODO Auto-generated method stub

		try{
				Hashtable<String, String> prop = new Hashtable<String, String>();
		       prop.put(Context.PROVIDER_URL, "tcp://localhost:61617");
		       prop.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.artemis.jndi.ActiveMQInitialContextFactory");
//		       prop.put(Context)
//		       prop.put(Context.SECURITY_PRINCIPAL,"user2");
//		       prop.put(Context.SECURITY_CREDENTIALS, "Swap@2019");
		       Context context = new InitialContext(prop);
		           // Step 2. perform a lookup on the topic
		           

		           // Step 3. perform a lookup on the Connection Factory
		           ConnectionFactory cf = (ConnectionFactory) context.lookup("ConnectionFactory");

		           // Step 4. Create a JMS Connection
		           Connection connection = cf.createConnection("user2", "Swap@2019");

		           // Step 5. Create a JMS Session
		           Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		           Topic topic = (Topic) context.lookup("dynamicTopics/Topic");
		           
		           // Step 6. Create a Message Producer
		           MessageProducer producer = session.createProducer(topic);

		           // Step 7. Create a JMS Message Consumer
		           MessageConsumer messageConsumer1 = session.createConsumer(topic);

		           // Step 8. Create a JMS Message Consumer
		           MessageConsumer messageConsumer2 = session.createConsumer(topic);

		           // Step 9. Create a Text Message
		        /*   TextMessage message = session.createTextMessage("This is a text message9090997887878");

		           System.out.println("Sent message: " + message.getText());

		           // Step 10. Send the Message
		           producer.send(message);*/

		           // Step 11. Start the Connection
		           connection.start();

		           // Step 12. Receive the message
		           TextMessage messageReceived = (TextMessage) messageConsumer1.receive();

		           System.out.println("Consumer 1 Received message: " + messageReceived.getText());

		           // Step 13. Receive the message
		           messageReceived = (TextMessage) messageConsumer2.receive();

		           System.out.println("Consumer 2 Received message: " + messageReceived.getText());
		   
		           
		    }finally {
				
			}
	}
	
	public static void sendMessage(QueueSession queueSession, Queue queue) throws JMSException, InterruptedException{
		
		int num = 909090;
		TextMessage message = null;
		 QueueSender queueSender = queueSession.createSender(queue);
		 queueSender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		 for(int i = 6; i>=0; i--){
			
		long s = System.currentTimeMillis();	
		 message = queueSession.createTextMessage(Long.toString(s));
		 System.out.println("sent: " + message.getText());	
		 Thread.sleep(2000);
		 }
		queueSender.send(message);
//		System.out.println("sent: " + message.getText());	
	}
	
	
	/**
	 * This method is to test different combinations 
	 * @param queueSession
	 * @param queue
	 * @param queue2
	 * @throws JMSException
	 */
	public static void recieveMessage(QueueSession queueSession, Queue queue, Queue queue2) throws JMSException{
		QueueReceiver recv = queueSession.createReceiver(queue);
		TextMessage msg = (TextMessage) recv.receive();
		System.out.println("message from 1st recieved >>"+msg.getText());  
		
		QueueReceiver recv2 = queueSession.createReceiver(queue2);
		TextMessage msg2 = (TextMessage) recv.receive();
		System.out.println("message from 2nd recieved >>"+msg2.getText());  
		
	}
}


