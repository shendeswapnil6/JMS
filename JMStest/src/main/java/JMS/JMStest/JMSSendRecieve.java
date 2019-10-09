package JMS.JMStest;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class JMSSendRecieve {

	public static void main(String[] args) throws JMSException {
		// TODO Auto-generated method stub
		
			 ConnectionFactory connFact = new ActiveMQConnectionFactory("tcp://localhost:61616");
			 //Destination dest = ActiveMQDestination.fromPrefixedName("queue://");
			 
			 
			 Connection conn = connFact.createConnection("admin", "Swap@2019");
//		     Connection conn = connFact.createConnection();
			   try {
				
		     	
		     	Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		     	Destination dest = session.createQueue("JMS_TEST_QUEUE");
		 /*    	MessageProducer producer = session.createProducer(dest);
		     	String msg = "Hello this is test message for AMQ";
		     	TextMessage msg1 = session.createTextMessage(msg);
		     	producer.send(msg1);
		     	System.out.println("\n Message sent to the server");
		     	*/
			     
		     	//Code for consuming message from server
		     	MessageConsumer consumer = session.createConsumer(dest);
		     	System.out.println("Recieving message ");
//		     	consumer = MessageListener.
		     	
		     	TextMessage recvdMsg =  (TextMessage) consumer.receive();
		     	System.out.println("***** \n Message recieved from sever ");		     	
		     	System.out.println("\n >>"+ recvdMsg);
			      } 
			    catch (JMSException ex) {
			      // handle exception (details omitted)
			    	System.out.println("\n **** Error encountered ");
			    	ex.printStackTrace();
			   }
			   finally {
			         conn.close();
			    	  
			      }
				}
			}

