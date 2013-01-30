package Network;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import GUI.CardData;
import Logic.ConnectedNodeProcessor;
import Logic.ICallBack;
import Logic.Logic;


public class Server implements Runnable , ICallBack{
	
	public ServerSocket serverSocket;
	private boolean listinegFlag = true;
	private ArrayList<NodeConnection> connectedNodes;
	
	
	private CardData[] tableCards;
	private int cardCount = 0;
	private int tableSuite = -1;
	private int currentPlayer = 0;
	private int tablehead = 0;
	private int rounds = 0;
	private int tricksWon = 0;
	private int modesResponses = 0;
	private int queencount = 0;
	private int kingcount = 0;
	private int diamondcount =0;
	
	private ArrayList<Integer> gameModes;//1-jacks,2-king of hearts,3-queens,4-diamond,5-suns
	
	public Server(int listeningPort) throws IOException {
		
		this.serverSocket = new ServerSocket(listeningPort);
		this.connectedNodes = new  ArrayList<NodeConnection>();
		this.tableCards = new CardData[4];
		this.gameModes = new ArrayList<Integer>();
		
	}
	
	public void Listen(boolean listen)
	{
		this.listinegFlag = listen;
	}
	
	public void sendToAll(String Data)
	{
		for (NodeConnection player : connectedNodes) {
			player.Send(Data);
		}
	}
	private String getPlayersString()
	{
		String result = "";
		
		for(NodeConnection player:connectedNodes)
		{
			result += player.getPlayerName() + "   " + player.getScore() + "\n";
		}
		
		return result;
	}
	private int  getWinner()
	{
		int indx = 0;
		int max = connectedNodes.get(0).getScore();
		for(int i = 1 ; i<connectedNodes.size();i++)
		{
			if(max < connectedNodes.get(i).getScore())
			{
				max = connectedNodes.get(i).getScore();
				indx = i;
			}
		}
		
		return indx;
	}
	
	@Override
	public void run() {
		while (true)
		{
			if(this.listinegFlag)
			{

				System.out.println("Waiting for players");
				try {
					
					Socket client = serverSocket.accept();
					NodeConnection player = new NodeConnection(client,this,new ConnectedNodeProcessor());
					player.shakeHands();
				} 
				catch (Exception e) {e.printStackTrace();}
			}
			else
			{
				Thread.currentThread().stop();
			}
		}
		
	}

	@Override
	public void CallBack(String Data , Object Caller) {
		// TODO Auto-generated method stub
		String Flag = Data.substring(0, 5);
		String Msg = Data.substring(5);
		
		if(Flag.equals("C_RNM"))
		{
			NodeConnection p = ((NodeConnection)Caller);
			p.Send("S_SIN" + connectedNodes.size());
			Logic.sleep(1000);
			String PLS = "";
			
			for (NodeConnection Player : connectedNodes) {
				Player.Send("S_PCN" + connectedNodes.size() + "," + p.getPlayerName());
				PLS += Player.getPlayerIndex() +","+  Player.getPlayerName() + "-";
			}
			p.setPlayerIndex(connectedNodes.size());
			this.connectedNodes.add(p);
			if(connectedNodes.size() != 0)
				p.Send("S_PLS" + PLS);
			
			if(connectedNodes.size() == 4)
			{
				Logic.sleep(1000);
				sendToAll("S_TBC");
				this.listinegFlag=false;
				Logic.sleep(2000);
				ArrayList<ArrayList<CardData>> Cards = Logic.generatePlayersCardSets();
				
				for(int i =0 ;i<4; i++)
				{
					ArrayList<CardData> clst = Cards.get(i);
					String strCLst = Logic.SerializeCards(clst);
					connectedNodes.get(i).Send("S_CLS" + strCLst);
				}

				
				Logic.sleep(1000);
					
				connectedNodes.get(tablehead).Send("S_STH");
				Logic.sleep(1000);
				
				this.gameModes.add(new Integer(5));
				
			}
			
			
			
		}
		else if(Flag.equals("C_SGM"))
		{
			String[] modes = Msg.split(",");			
			modesResponses = 0;
			gameModes.clear();
			
			for(int i =0;i<modes.length ;i++)
			{
				this.gameModes.add(new Integer(Integer.parseInt(modes[i])));
			}
			
			sendToAll("S_SGM" + Msg);
			Logic.sleep(1000);
		}
		
		else if(Flag.equals("C_GMA"))
		{
			if(Msg.equals("0"))
			{
				ArrayList<ArrayList<CardData>> Cards = Logic.generatePlayersCardSets();
				
				for(int i =0 ;i<4; i++)
				{
					ArrayList<CardData> clst = Cards.get(i);
					String strCLst = Logic.SerializeCards(clst);
					connectedNodes.get(i).Send("S_CLS" + strCLst);
				}

				
				Logic.sleep(1000);
				modesResponses = 0;
				gameModes.clear();
				connectedNodes.get(this.tablehead).Send("S_STH");
				Logic.sleep(1000);
			}
			else
			{
				modesResponses++;
				if(modesResponses == 4)
				{
					this.currentPlayer = this.tablehead;
					sendToAll("S_CUP" + this.tablehead);
					
				}
			}
			
		}
		
		

		else if (Flag.equals("C_CHT"))
		{
			sendToAll("S_CHT" + Msg);
			Logic.sleep(1000);
		}
		else if (Flag.equals("C_CPD"))
		{
			System.out.println("Card Recived");
			
			int indx = ((NodeConnection)Caller).getPlayerIndex();
			int value = Integer.parseInt(Msg.split(",")[0]);
			int suit = Integer.parseInt(Msg.split(",")[1]);
			
			tableCards[indx] = new CardData(value,suit);
			if(cardCount == 0)
			{
				tableSuite = suit;
				sendToAll("S_STS" + suit);
				Logic.sleep(1000);
				
			}
			sendToAll("S_CPD" + indx + "," + Msg);
			Logic.sleep(3000);
			cardCount++;
		
			if(cardCount == 4)
			{
				int winner = Logic.HandWinner(tableSuite, tableCards);
				
				boolean stop = true;
				
				if(gameModes.contains(new Integer(5)))//Suns Mode
				{
					connectedNodes.get(winner).AddScore(-15);
					stop = false;
				}
				
				if(gameModes.contains(new Integer(4)))//Diamonds Mode
				{
					for(int i =0;i<tableCards.length;i++)
					{
						if(tableCards[i].getSuit() == 4)
						{
							connectedNodes.get(winner).AddScore(-10);
							diamondcount ++;
						}
					}
					stop = stop && (diamondcount == 13);
				}
				
				if(gameModes.contains(new Integer(3)))//Queens Mode
				{
					for(int i =0;i<tableCards.length;i++)
					{
						if(tableCards[i].getValue() == 12)
						{
							connectedNodes.get(winner).AddScore(-25);
							queencount ++;
						}
					}
					stop = stop && (queencount == 4);
				}
				
				if(gameModes.contains(new Integer(2)))//King Heart Mode
				{
					for(int i =0;i<tableCards.length;i++)
					{
						if(tableCards[i].getSuit() == 2 && tableCards[i].getValue() == 13 )
						{
							connectedNodes.get(winner).AddScore(-75);
							kingcount ++;
						}
					}
					stop = stop && (kingcount == 1);
				}
				
				
				
				
				
				int Score = connectedNodes.get(winner).getScore();
				sendToAll("S_PWN"+winner +","+ connectedNodes.get(winner).getPlayerName() +"," + Score);
				Logic.sleep(1000);
				
				this.currentPlayer = winner;
				this.cardCount = 0;
				this.tricksWon++;
				System.out.println(tricksWon);
				
				if(stop) tricksWon =13;
				
				if(this.tricksWon == 13) // change to 13 after testing 
				{
					tricksWon = 0;
					rounds += this.gameModes.size();
					this.gameModes.clear();
					diamondcount = 0;
					queencount = 0;
					kingcount = 0;
				
				
				if(this.rounds == 4) // return to 5 after testing
				{
					sendToAll("S_ROE" +getPlayersString());
					Logic.sleep(1000);
					this.rounds = 0;
					this.tablehead ++;
				}
				
				if(this.tablehead == 4)// return to 5 after testing dont forget !
				{
					sendToAll("S_GME" + "Winner is : " + connectedNodes.get(getWinner()).getPlayerName() + "\n\n" + getPlayersString());
					Logic.sleep(1000);
					System.exit(0);
				}else
				{
					ArrayList<ArrayList<CardData>> Cards = Logic.generatePlayersCardSets();
				
					for(int i =0 ;i<4; i++)
					{
						ArrayList<CardData> clst = Cards.get(i);
						String strCLst = Logic.SerializeCards(clst);
						connectedNodes.get(i).Send("S_CLS" + strCLst);
					}

				
					Logic.sleep(1000);
					
					connectedNodes.get(tablehead).Send("S_STH");
					Logic.sleep(1000);
				}
				
				}
				

			}
			else
			{
				if(currentPlayer == 3)
					currentPlayer = 0;
				else 
					currentPlayer ++;				
			}
			
			sendToAll("S_CUP" + currentPlayer);
		}
	}
	}
