package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Logic.ICallBack;

public class SidePanel extends JPanel implements KeyListener {

	private JTextField txtSend; 
	private JTextArea  chatArea;
	private ICallBack callBack;
	private JScrollPane chatScroll;
	private JList playersList;
	private DefaultListModel playersNames;
	
	public SidePanel(ICallBack CallBack) {
	
		this.callBack = CallBack;
		this.setLayout(new BorderLayout(5,5));
		
		this.setPreferredSize(new Dimension(180, 400));
		this.txtSend = new JTextField();
		this.txtSend.addKeyListener(this);
		this.txtSend.setPreferredSize(new Dimension(180,30));
		this.txtSend.setBackground(Color.darkGray);
		this.txtSend.setForeground(Color.orange);
		this.txtSend.setBorder(null);
		
		this.chatArea = new JTextArea();
		this.chatArea.setEditable(false);
		//this.chatArea.setPreferredSize(new Dimension(200,400));
		this.chatArea.setBackground(Color.darkGray);
		this.chatArea.setForeground(Color.orange);
		this.chatArea.setLineWrap(true);
		this.chatScroll = new JScrollPane(chatArea);
		this.chatScroll.setBorder(null);
		this.chatScroll.getVerticalScrollBar().setBackground(Color.DARK_GRAY);
		this.playersNames  = new DefaultListModel();
		this.playersList = new JList(playersNames);
		this.playersList.setPreferredSize(new Dimension(200, 80));
		this.playersList.setBorder(null);
		this.playersList.setBackground(Color.DARK_GRAY);
		this.playersList.setForeground(Color.orange);
		this.playersList.setSelectionForeground(Color.DARK_GRAY);
		this.playersList.setSelectionBackground(Color.orange);
		
		
		this.add(chatScroll,BorderLayout.CENTER);
		this.add(playersList,BorderLayout.NORTH);
		this.add(txtSend,BorderLayout.SOUTH);
		
	}

	public void AppendToChat(String Str)
	{
		this.chatArea.append(Str + "\n");
		this.chatArea.setCaretPosition(chatArea.getText().length());
	}
	
	public void AddPlayer(String Name , int indx)
	{System.out.println("Adding Players" + Name);
		//this.playersNames.insertElementAt(Name, indx);
		this.playersNames.addElement(Name);
		this.repaint();
	}
	
	public void SelectPlayer(int indx)
	{
		this.playersList.setSelectedIndex(indx);
		this.repaint();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == e.VK_ENTER)
		{
			this.callBack.CallBack("C_CHT" + txtSend.getText(), this);
			txtSend.setText("");
			
		}
		
	}
	public JList getPlayersList() {
		return playersList;
	}
	public void keyReleased(KeyEvent e) {};
	public void keyTyped(KeyEvent e) {};
}
