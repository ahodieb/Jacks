package GUI;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import Logic.ClientProcessor;
import Logic.ICallBack;
import Logic.ISender;
import Logic.Logic;
import Network.Connection;

public class GameFrame extends JFrame implements ICallBack,ISender
{

	private static final long serialVersionUID = 1L;

	private GamePanel gPanel;
	private SidePanel sidePanel;
	private Connection connection;
	
	private String PlayerName;
	private int Index;
	private int CurrentPlayer = 0;
	private int tableSuite = -1;
	
	public int getTableSuite() {
		return tableSuite;
	}

	
    public int  getCurrentPlayer()
    {return this.CurrentPlayer;}
    public void setCurrentPlayer(int i)
    {this.CurrentPlayer = i;}
    public int getIndex()
    {return this.Index;}
    
    @Override
    public void Send(String Data) {
    	this.connection.Send(Data);    	
    }
    
	
	public GameFrame(String IP , int Port,String PlayerName) throws IOException {

		this.setBackground(Color.black);
		this.PlayerName = PlayerName;
		this.connection = new Connection(IP, Port, this,new ClientProcessor());
		
		this.setTitle("Jacks !! Online Mode ");
		this.setSize(900, 500);
				
		this.setLayout( new BorderLayout(5, 5) );
		
		this.gPanel = new GamePanel(this);
		gPanel.setBackground(Color.black);
		
		this.sidePanel = new SidePanel(this);
		this.sidePanel.setBackground(Color.black);
		
		this.add(sidePanel,BorderLayout.EAST);
		
		this.add(gPanel,BorderLayout.CENTER);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		System.out.println("Frame Done");
	}	
	
	public Connection getConnection() {
		return connection;
	}
	
	@Override
	public void CallBack(String Data, Object Caller) {
		
		String Flag = Data.substring(0,5);
		String Msg = Data.substring(5);
		
		if(Flag.equals("S_RNM"))
		{
			connection.Send("C_RNM" + this.PlayerName);
		}	
		
		if(Flag.equals("S_SIN"))
		{
			this.Index = Integer.parseInt(Msg);
			System.out.println("Index Set");
		}
		
		
		else if(Flag.equals("S_PCN"))
		{
			int indx = Integer.parseInt(Msg.split(",")[0]);
			String name =(Msg.split(",")[1]);
			this.sidePanel.AddPlayer(name, indx);
			
		}
		
		else if(Flag.equals("S_PLS"))
		{
			System.out.println("Player list Recived");
			
			String[] Players = Msg.split("-");
			for(int i =0;i<Players.length;i++)
			{
				try
				{
					String name =(Players[i].split(",")[1]);
					int indx = Integer.parseInt(Players[i].split(",")[0]);
					this.sidePanel.AddPlayer(name, indx);
				}
				catch(Exception ex)
				{
					
				}
			}
			this.sidePanel.AddPlayer(this.PlayerName,0);
			
			
		}
		else if(Flag.equals("S_TBC"))
		{
			//this.setVisible(true);
		}
		
		else if (Flag.equals("S_CUP"))
		{
			int Indx = Integer.parseInt(Msg);
			this.CurrentPlayer = Indx;
			this.sidePanel.SelectPlayer(Indx);
			
		}

		else if (Flag.equals("S_SGM"))
		{
			String[] Modes = Msg.split(",");
			ArrayList<Integer> modes = new ArrayList<Integer>();
			for(int i=0;i<Modes.length;i++)modes.add(new Integer(Integer.parseInt(Modes[i])));
			if(Logic.Dokk(this.gPanel.getCards(), modes))Send("C_GMA0");
			else Send("C_GMA1");
			
		}
		
		else if (Flag.equals("S_CLS"))
		{
			this.gPanel.ReDistributeCards(Logic.DeSerializeCards(Msg));
		}
		
		else if (Flag.equals("C_CHT"))
		{
			this.connection.Send("C_CHT" + this.PlayerName + ":" + Msg);
		}
		
		else if (Flag.equals("S_CHT"))
		{
			System.out.println("chat mssge recived");
			this.sidePanel.AppendToChat(Msg);
		}
		else if (Flag.equals("S_CPD"))
		{
			System.out.println("Proccessing Card" + Msg);
			int indx = Integer.parseInt(Msg.split(",")[0]);
			int value = Integer.parseInt(Msg.split(",")[1]);
			int suit = Integer.parseInt(Msg.split(",")[2]);			
			CardData cd = new CardData(value,suit);
			Card c = new Card(cd);
			c.setTableIndx(indx);
			this.gPanel.addCard(c);
			
			
		}
		else if (Flag.equals("S_STS"))
		{
			this.tableSuite = Integer.parseInt(Msg);
		}
		
		else if (Flag.equals("S_PWN"))
		{
			this.tableSuite = -1;
			int indx = Integer.parseInt(Msg.split(",")[0]);
			String Name = Msg.split(",")[1];
			int score = Integer.parseInt(Msg.split(",")[2]);
			this.gPanel.tablePanel.removeAll();
			this.gPanel.tablePanel.repaint();
			this.gPanel.tablePanel.revalidate();
			
			
			this.sidePanel.getPlayersList().setSelectedIndex(-1);
		if(indx == this.Index)
			JOptionPane.showMessageDialog(null,"You won "  + this.PlayerName  + "\nYour score : "  + score);
		else
			JOptionPane.showMessageDialog(null,   Name + " Won the trick\nNew score : "  + score);
		
		}
		else if (Flag.equals("S_ROE"))
		{	
			JOptionPane.showMessageDialog(null, "Round Finished\n\n" + Msg);
		
		}
		
		else if (Flag.equals("S_GME"))
		{
			JOptionPane.showMessageDialog(null, "Game Finished\n\n" + Msg);
			System.exit(0);
		}
		
	}
	
}