package Logic;

import Network.NodeConnection;


public class ConnectedNodeProcessor implements IProcessor {
	private ICallBack callBack;
	private ISender   sender;
	
	public ConnectedNodeProcessor() {}
	public ConnectedNodeProcessor(ICallBack callBack,ISender Sender)
	{
		this.callBack = callBack;
		this.sender = Sender;
	}
	
	@Override
	public void Process (String Data)
	{
		Process(Data,this.callBack,this.sender);
	}
	
	@Override
	public void Process(String Data, ICallBack callBack,ISender Sender) {
		String Flag = Data.substring(0, 5);
		String Msg = Data.substring(5);
		
		if(Flag.equals("C_SHD"))
		{
			System.out.println("Shake Hands Successfull");
			System.out.println("Requesting Name");
			Sender.Send("S_RNM");
			
		}
		else if (Flag.equals("C_RNM"))
		{
			((NodeConnection)Sender).setPlayerName(Msg);
			System.out.println("PlayerName Recived : " + Msg);
			callBack.CallBack(Flag,Sender);
		}
		else if (Flag.equals("C_CHT"))
		{
			callBack.CallBack(Data, Sender);
			System.out.println("Chat Msg Recived " +Msg );
		}
		else if (Flag.equals("C_CPD"))
		{
			callBack.CallBack(Data, Sender);
			System.out.println("Card Recived " +Msg );
		}
		
		else if (Flag.equals("C_GMA"))
		{
			callBack.CallBack(Data, Sender);
		}
		else if (Flag.equals("C_SGM"))
		{
			callBack.CallBack(Data, Sender);
		}
		
		
		
		
	}

}
