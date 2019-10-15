package JMS.JMStest;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.fusesource.stomp.jms.StompJmsConnectionFactory;
import org.fusesource.stomp.jms.StompJmsMessageConsumer;
import org.fusesource.stomp.jms.message.StompJmsMessage;

public class StompAcceptor {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		final String END_OF_FRAME = "\u0000";
		Socket socket = new Socket("localhost", 61613);

		// Step 2. Send a CONNECT frame to connect to the server
		String connectFrame = "CONNECT\n" + "accept-version:1.2\n" + "host:localhost\n" + "request-id:1\n" + "\n"
				+ END_OF_FRAME;
		sendFrame(socket, connectFrame);

		String response = receiveFrame(socket);
		System.out.println("response: " + response);

		// Step 3. Send a SEND frame (a Stomp message) to the
		// jms.queue.exampleQueue address with a text body
		String text = "Stomp new message @@@++%%%nnnnnnnnn+++@@ !";
		String message = "SEND\n" + "destination:/Area\n" + "destination-type:ANYCAST\n" + "\n" + text + END_OF_FRAME;
		sendFrame(socket, message);
		System.out.println("Sent Stomp message: " + text);

		// Disconnect from the server
		String disconnectFrame = "DISCONNECT\n" + "\n" + END_OF_FRAME;
		sendFrame(socket, disconnectFrame);

		// Step 5. Slose the TCP socket
		socket.close();

		StompJmsConnectionFactory factory = new StompJmsConnectionFactory();
		// factory.setDisconnectTimeout(50000);
		factory.setBrokerURI("tcp://localhost:61613");
		factory.setUsername("admin");
		factory.setPassword("Swap@2019");
		// StompJmsQueue sjsm = (StompJmsQueue)
		// factory.createQueueConnection("StompQ123");
		/*
		 * Connection connection = factory.createConnection(); Session session =
		 * connection.createSession(false, Session.AUTO_ACKNOWLEDGE); Queue
		 * queue = session.createQueue("Lane");
		 */// session.

		QueueConnection qConn = factory.createQueueConnection("user2", "Swap@2019");
		QueueSession qSess = qConn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		qConn.start();
		Queue queue = qSess.createQueue("Area");
		// MessageProducer producer = session.createProducer(queue);

		/*
		 * // System.out.println("Waiting 10 seconds"); Thread.sleep(10000); //
		 * increase this and it will fail System.out.println("waited");
		 */
//		MessageConsumer consumer = qSess.createConsumer(queue);
		
		StompJmsMessageConsumer consumer1 = (StompJmsMessageConsumer) qSess.createConsumer(queue);

//		TextMessage message1 = (TextMessage) consumer.receive();
		
		StompJmsMessage message1  = (StompJmsMessage) consumer1.receive();

		System.out.println("The content of the message is " + ((TextMessage) message1).getText());

	/*	if (!message1.getText().equals("Hello")) {
			throw new IllegalStateException("the content of the message was different than expected!");
		}
*/
		// connection.close();

	}

	private static void sendFrame(Socket socket, String data) throws Exception {
		byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
		OutputStream outputStream = socket.getOutputStream();
		for (int i = 0; i < bytes.length; i++) {
			outputStream.write(bytes[i]);
		}
		outputStream.flush();
	}

	private static String receiveFrame(Socket socket) throws Exception {
		InputStream inputStream = socket.getInputStream();
		byte[] buffer = new byte[1024];
		int size = inputStream.read(buffer);

		byte[] data = new byte[size];
		System.arraycopy(buffer, 0, data, 0, size);

		String frame = new String(data, StandardCharsets.UTF_8);
		return frame;
	}

	/*
	 * public String decrypt(String encryptedString) { String
	 * decryptedText=null;
	 * 
	 * final String UNICODE_FORMAT = "UTF8"; final String
	 * DESEDE_ENCRYPTION_SCHEME = "DESede"; KeySpec ks; SecretKeyFactory skf;
	 * Cipher cipher = null; byte[] arrayBytes; String myEncryptionKey; String
	 * myEncryptionScheme; SecretKey key = null; try {
	 * cipher.init(Cipher.DECRYPT_MODE, key); byte[] encryptedText =
	 * Base64.decodeBase64(encryptedString); byte[] plainText =
	 * cipher.doFinal(encryptedText); decryptedText= new String(plainText);
	 * System.out.println(" "+ decryptedText); } catch (Exception e) {
	 * e.printStackTrace(); } return decryptedText; }
	 */

}
