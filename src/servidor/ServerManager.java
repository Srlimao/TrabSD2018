package servidor;


import java.awt.Desktop;
import java.awt.event.*;
import java.net.URI;

import javax.swing.*;

public class ServerManager {
	private static HttpFileServer server;
	
	private static void createAndShowGUI() {
        //Create and set up the window.
		server = new HttpFileServer();
		JFrame frame = new JFrame("Soccer SPB");
  		frame.setVisible(true);
		frame.setSize(300,150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		frame.add(panel);
		JLabel label;
		JButton siteLink = new JButton("Abrir P�gina");
		
		try {
			HttpFileServer.Start();
			label = new JLabel("Servidor "+ server.toString() +" Online");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			label = new JLabel(e.getMessage());
			label.setHorizontalAlignment(SwingConstants.CENTER); // set the horizontal alignement on the x axis !
			label.setVerticalAlignment(SwingConstants.CENTER);
		}
		siteLink.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	
				try {
					URI uri = new URI("http://"+server.toString()+"/getAvailabeYears");
					Desktop.getDesktop().browse(uri);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }
		});
		
		
		panel.add(label);
		panel.add(siteLink);
		
    }
	
	static class Action1 implements ActionListener {        
		  public void actionPerformed (ActionEvent e) {     
		    try {
		    	HttpFileServer.Start();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}     
		  }
		}   
		static class Action2 implements ActionListener {        
		  public void actionPerformed (ActionEvent e) {     
		    try {
		    	
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		  }
		} 

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
                
            }
        });
	}

}
