import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Server extends JFrame
{
	private  JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection;
	
	//constructor
	public Server()
	{
		super("Instant Messenger");
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent event){
					sendMessage(event.getActionCommand());
					userText.setText("");
				}
			}
		);
		add(userText, BorderLayout.NORTH);
		chatWindow = new JTextArea();
		add(new JScrollPane(chatWindow));
		setSize(300, 150);
		setVisible(true);
		chatWindow.setEditable(false);
	}
	
	//set up and run server
	public void startRunning()
	{
		try{
			server = new ServerSocket(6789, 100);
			while(true){
				try{
					//connect and have conversation
					waitForConnection();
					setupStreams();
					whileChatting();
				}catch(EOFException eofException){
					showMessage("\n Server ended the conenction");
				}finally{
					closeSnS();
				}
			}
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	//wait for connection, then display connection info
	private void waitForConnection() throws IOException
	{
		showMessage(" Waiting for someone to connect...\n");
		connection = server.accept();
		showMessage(connection.getInetAddress().getHostName() + " has connected\n");
	}
	
	//get stream to send and receive data
	private void setupStreams() throws IOException
	{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\n Streams are setup \n");
	}
	
	//during the chat conversation
	private void whileChatting() throws IOException
	{
		String message = " You are now connected\n";
		sendMessage(message);
		ableToType(true);
		do{
			//have conversation
			try{
				waitForConnection();
				message = (String) input.readObject();
				showMessage("\n" + message);
			}catch(ClassNotFoundException classNotFoundException){
				showMessage("\n CAN'T DISPLAY TEXT");
			}
			catch(EOFException eofException){
				showMessage("\n Server ended the conenction");
			}
		}while(!message.equals("CLIENT - END"));
	}
	
	//close streams and sockets
	private void closeSnS()
	{
		showMessage("\n Closing connection... \n");
		ableToType(false);
		try{
			output.close();
			input.close();
			connection.close();
			waitForConnection();
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	//sends message to client
	private void sendMessage(String message)
	{
		try{
			output.writeObject("SERVER - " + message);
			output.flush();
			showMessage("\nServer - " + message);
		}catch(IOException ioException){
			chatWindow.append("\n MESSAGE FAILED TO SEND");
		}
	}
	
	//updates chatWindow
	private void showMessage(final String text)
	{
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					chatWindow.append(text);
				}
			}
		);
	}
	
	//allows user to type
	private void ableToType(final boolean tof)
	{
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					userText.setEditable(tof);
				}
			}
		);
	}
	
}