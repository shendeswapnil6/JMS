package JMS.JMStest;

import java.util.Hashtable;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TestSendRecieve {

	public static void main(String[] args) throws NamingException, JMSException, InterruptedException {
		// TODO Auto-generated method stub

		try{
		                                                                          
		       // lookup the queue object
		       Hashtable<String, String> prop = new Hashtable<String, String>();
		       prop.put(Context.PROVIDER_URL, "tcp://localhost:61617");
		       prop.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.artemis.jndi.ActiveMQInitialContextFactory");
//		       prop.put(Context)
//		       prop.put(Context.SECURITY_PRINCIPAL,"user2");
//		       prop.put(Context.SECURITY_CREDENTIALS, "Swap@2019");
		       Context context = new InitialContext(prop);
		       
		       //ConnectFacory                                         */
		       QueueConnectionFactory connFactory = (QueueConnectionFactory) context.lookup("ConnectionFactory");
//		       connFactory.createContext("user2", "Swap@2019");
		       // create a queue connection
		       QueueConnection queueConn = connFactory.createQueueConnection("user2", "Swap@2019");
		                                                                          
		       // create a queue session
		       QueueSession queueSession = queueConn.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);
		       queueConn.start();
		                                                                           
		       Queue queue = (Queue) context.lookup("dynamicQueues/brodcastQ");
		       Queue queue2 = (Queue) context.lookup("dynamicQueues/ExpiryQueue");
		      
		       //Send message
		       	//	sendMessage(queueSession, queue);
		       
		       //Coded to recieve the message:
		            recieveMessage(queueSession, queue, queue2);    
		           
		     // close the queue connection
		           queueConn.close();
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


