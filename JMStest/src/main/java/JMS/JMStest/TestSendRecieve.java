package JMS.JMStest;

import java.util.Hashtable;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TestSendRecieve {

	public static void main(String[] args) throws NamingException, JMSException {
		// TODO Auto-generated method stub

		try{
		                                                                          
		       // lookup the queue object
		       Hashtable<String, String> prop = new Hashtable<String, String>();
		       prop.put(Context.PROVIDER_URL, "tcp://localhost:61616");
		       prop.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.artemis.jndi.ActiveMQInitialContextFactory");
//		       prop.put(Context., value)
		       Context context = new InitialContext(prop);
		       
		       // lookup the queue connection factory
		   /* QueueConnectionFactory connFactory = (QueueConnectionFactory) ctx.
		           lookup("tcp://localhost:61616/queue/");
		       
		       //ConnectFacory                                         */
		       QueueConnectionFactory connFactory = (QueueConnectionFactory) context.lookup("ConnectionFactory");
		     
		       // create a queue connection
		       QueueConnection queueConn = connFactory.createQueueConnection();
		                                                                          
		       // create a queue session
		       QueueSession queueSession = queueConn.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);
		       queueConn.start();
		                                                                           
		       Queue queue = (Queue) context.lookup("dynamicQueues/JMS_TEST_QUEUE");
		      
		       //Send message
//		       		sendMessage(queueSession, queue);
		       
		       //Coded to recieve the message:
		           recieveMessage(queueSession, queue);    
		           
		     // close the queue connection
		           queueConn.close();
		    }finally {
				
			}
	}
	
	public static void sendMessage(QueueSession queueSession, Queue queue) throws JMSException{
		
		 QueueSender queueSender = queueSession.createSender(queue);
		 queueSender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		TextMessage message = queueSession.createTextMessage("Test message 909080");
		queueSender.send(message);
		System.out.println("sent: " + message.getText());	
	}
	
	public static void recieveMessage(QueueSession queueSession, Queue queue) throws JMSException{
		QueueReceiver recv = queueSession.createReceiver(queue);
		TextMessage msg = (TextMessage) recv.receive();
		System.out.println("message recieved >>"+msg.getText());  
	}
}


