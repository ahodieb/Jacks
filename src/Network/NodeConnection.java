package Network;
import java.io.IOException;
import java.net.Socket;
import Logic.ICallBack;
import Logic.IProcessor;

public class NodeConnection extends Connection {
	private String playerName;
	private int playerIndex;
	private int Score =0;
	
	
	
	public NodeConnection(Socket connection,ICallBack CallBack,IProcessor Processor) throws IOException {
		this.callBack = CallBack;
		this.connection = connection;
		this.outPutChannel = new OutputChannel(this.connection);
		this.inPutChannel = new InputChannel(this.connection, this);
		
		Thread outputThread = new Thread(outPutChannel);
		Thread inputThread   = new Thread(inPutChannel);
		
		this.Processor = Processor;
		
		outputThread.start();
		inputThread.start();
		
		
	}
	
	public void shakeHands()
	{
		this.outPutChannel.Send("S_SHD");
		System.out.println("Shake Hands Invoked");
	}

	
	public String getPlayerName() {
		return playerName;
	}
	
	public int getPlayerIndex() {
		return playerIndex;
	}
	
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	public void Send (String Data)
	{
		this.outPutChannel.Send(Data);
	}
	
	public void inputRecived(String Input)
	{
		String Flag = Input.substring(0, 5);
		String Data = Input.substring(5);
		
		
		if(Flag.equals("C_SHD"))
		{
			System.out.println("Shake Hands Successfull");
			System.out.println("Requesting Name");
			this.outPutChannel.Send("S_RNM");
			
		}
		else if (Flag.equals("C_RNM"))
		{
			this.playerName = Data;
			System.out.println("PlayerName Recived : " + Data);
			callBack.CallBack(Flag,this);
		}
		
		
		
	}

	public void AddScore(int Score)
	{
		this.Score += Score;
	}
	
	public int getScore()
	{
		return this.Score;
	}
	
	@Override
	public void CallBack(String Data, Object Caller) {
		Processor.Process(Data,this.callBack,this);
		
	}
}
