import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.SwingConstants;

public class Client {

	private static JFrame frmClient;
	private static JTextField msgClient;
	static Socket s;
	static DataInputStream din;
	static DataOutputStream dout;
	static JTextArea msgArea;
	public static String uName = "";
	private JTextField txtClientServer;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args){
		
		uName = JOptionPane.showInputDialog(frmClient,"Enter Username: ");
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client window = new Client();
					frmClient.setTitle(uName);
					Client.frmClient.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		try {
			s = new Socket("127.0.0.1", 1431);
			din = new DataInputStream(s.getInputStream());
			dout = new DataOutputStream(s.getOutputStream());
			
			
			String msgin="";
			while(!msgin.contentEquals("end")) 
			{
				msgin = din.readUTF();
				msgArea.setText(msgArea.getText().trim()+"\n" + Server.formatter.format(new Date()) + " " +"Server: " +msgin);
			}
			s.close();
			
			
			
			
		}catch (Exception ec) {
			
		}
		
		
	}

	/**
	 * Create the application.
	 */
	public Client() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmClient = new JFrame();
		frmClient.setTitle("Client");
		frmClient.setBounds(100, 100, 400, 480);
		frmClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmClient.getContentPane().setLayout(null);
		
		msgArea = new JTextArea();
		msgArea.setFont(new Font("Arial", Font.PLAIN, 14));
		msgArea.setTabSize(10);
		msgArea.setForeground(Color.BLACK);
		msgArea.setBackground(Color.WHITE);
		msgArea.setBounds(10, 70, 364, 306);
		frmClient.getContentPane().add(msgArea);
		
		msgClient = new JTextField();
		msgClient.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
				try 
				{
				String msgout ="";
				msgout = msgClient.getText().trim();
				dout.writeUTF(msgout);
				msgArea.append("\n"+ Server.formatter.format(new Date()) + " " + uName + ": "+ msgout);
				msgClient.setText("");
				}catch (Exception ecc) 
				{
				}
				}
			}
		});
		msgClient.setBounds(10, 387, 237, 38);
		frmClient.getContentPane().add(msgClient);
		msgClient.setColumns(10);
		
		
		JButton sendBtn = new JButton("SEND");
		sendBtn.setForeground(Color.BLACK);
		sendBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
		sendBtn.setBackground(SystemColor.textHighlight);
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try 
				{
				String msgout ="";
				msgout = msgClient.getText().trim();
				dout.writeUTF(msgout);
				msgArea.append("\n"+ Server.formatter.format(new Date()) + " " + uName + ": "+ msgout);
				msgClient.setText("");
				}catch (Exception ecc) 
				{
					
				}
			}
		});
		sendBtn.setBounds(257, 387, 117, 38);
		frmClient.getContentPane().add(sendBtn);
		
		txtClientServer = new JTextField();
		txtClientServer.setEditable(false);
		txtClientServer.setForeground(Color.DARK_GRAY);
		txtClientServer.setHorizontalAlignment(SwingConstants.CENTER);
		txtClientServer.setFont(new Font("Arial", Font.BOLD, 30));
		txtClientServer.setText("CLIENT");
		txtClientServer.setBounds(10, 11, 364, 48);
		frmClient.getContentPane().add(txtClientServer);
		txtClientServer.setColumns(10);
	}

	
	public static void stop()
		{
		frmClient.setVisible(false);
		}	
}

