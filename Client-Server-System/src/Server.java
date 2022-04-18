import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JLabel;

public class Server {

	private static JFrame frmServer;
	private static JTextField msgServer;
	static ServerSocket cs;
	static Socket s;
	static DataInputStream din;
	static DataOutputStream dout;
	static JTextArea msgArea;
	public static SimpleDateFormat formatter = new SimpleDateFormat("[hh:mm a]");
	private JTextField txtClientServer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		String name = Client.uName;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server window = new Server();
					Server.frmServer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	String msgin = "";
	
	try 
	{
		cs = new ServerSocket(1431);
		s = cs.accept();
		
		din = new DataInputStream(s.getInputStream());
		dout = new DataOutputStream(s.getOutputStream());

		
		
		while(!msgin.contentEquals("end")) 
		{
			
			msgin = din.readUTF();
			msgArea.setText(msgArea.getText().trim() + "\n" + formatter.format(new Date()) + " " + name + ": " +msgin);
		}
		cs.close();
		s.close();
		
	}
	catch (Exception e)
	{
		
	}
	}
	//main
	

	/**
	 * Create the application.
	 */
	public Server() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmServer = new JFrame();
		frmServer.setTitle("Server");
		frmServer.setBounds(100, 100, 400, 480);
		frmServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmServer.getContentPane().setLayout(null);
		
		msgArea = new JTextArea();
		msgArea.setFont(new Font("Arial", Font.PLAIN, 14));
		msgArea.setForeground(Color.BLACK);
		msgArea.setLineWrap(true);
		msgArea.setWrapStyleWord(true);
		msgArea.setEditable(false);
		msgArea.setBackground(Color.WHITE);
		msgArea.setBounds(10, 70, 364, 311);
		frmServer.getContentPane().add(msgArea);
		
		msgServer = new JTextField();
		msgServer.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) 
				{
					try 
					{
					String msgout = "";
					msgout = msgServer.getText().trim();
					dout.writeUTF(msgout);
					msgArea.append("\n" + formatter.format(new Date()) + " " +"Server: "+ msgout);
					msgServer.setText("");
					}catch(Exception es) 
					{
						
					}
				}
			}
		});
		msgServer.setBounds(10, 392, 241, 38);
		frmServer.getContentPane().add(msgServer);
		msgServer.setColumns(10);
		
		JButton sendBtn = new JButton("SEND");
		sendBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
		sendBtn.setForeground(SystemColor.textText);
		sendBtn.setBackground(SystemColor.textHighlight);
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try 
				{
				String msgout = "";
				msgout = msgServer.getText().trim();
				dout.writeUTF(msgout);
				msgArea.append("\n" + formatter.format(new Date()) + " " +"Server: "+ msgout);
				msgServer.setText("");
				}catch(Exception es) 
				{
					
				}
			}
		});
		sendBtn.setBounds(261, 392, 113, 38);
		frmServer.getContentPane().add(sendBtn);
		
		txtClientServer = new JTextField();
		txtClientServer.setEditable(false);
		txtClientServer.setForeground(Color.DARK_GRAY);
		txtClientServer.setHorizontalAlignment(SwingConstants.CENTER);
		txtClientServer.setFont(new Font("Arial", Font.BOLD, 30));
		txtClientServer.setText("SERVER");
		txtClientServer.setBounds(10, 11, 364, 48);
		frmServer.getContentPane().add(txtClientServer);
		txtClientServer.setColumns(10);
	}
	
	public static void stop() {
		Server.frmServer.setVisible(false);
	}
	}


