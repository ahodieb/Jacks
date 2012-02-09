package Logic;

import javax.swing.JOptionPane;


public class ClientProcessor implements IProcessor {
	
	private ICallBack callBack;
	private ISender   sender;
	
	public ClientProcessor() {}
	public ClientProcessor(ICallBack callBack,ISender Sender)
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

		String Flag = Data.substring(0,5);
		String Msg = Data.substring(5);
		
		if (Flag.equals("S_SHD"))
		{
			Sender.Send("C_SHD");
		}
		else if(Flag.equals("S_RNM"))
		{
			callBack.CallBack(Flag, Sender);
		}
		
		else if(Flag.equals("S_SIN"))
		{
			System.out.println("MY Index " +Msg );
			callBack.CallBack(Data, Sender);
		}
		
		else if(Flag.equals("S_PCN"))
		{
			System.out.println("Player Connected : " + Msg);
			callBack.CallBack(Data, Sender);
		}
		
		else if(Flag.equals("S_PLS"))
		{
			String[] Players = Msg.split("-");
			for(int i =0;i<Players.length;i++)System.out.println(Players[i]);
			callBack.CallBack(Data, Sender);
		}
		
		
		else if (Flag.equals("S_TBC"))
		{
			callBack.CallBack("S_TBC", this);
			System.out.println("Table Complete\nStarting Game");
		}
		

		else if (Flag.equals("S_CUP"))
		{
			callBack.CallBack(Data, this);
			System.out.println("Player Changed" + Msg);
		}
		
		
		
		else if(Flag.equals("S_CLS"))
		{
			callBack.CallBack(Data, Sender);
		}
		
		else if (Flag.equals("S_STH"))
		{
			String Modes = "";
			while(Modes.length() ==0)
			{
				Modes = JOptionPane.showInputDialog("Enter Game Modes Spearated by ',' \n\b 1-Jacks\n2-King Of Hearts\n3-Queens\n4-Diamonds\n5-Suns");
			}
			
			if (Modes.length() == 1)Modes += ",";
			Sender.Send("C_SGM" + Modes);
		}
		
		else if (Flag.equals("S_SGM"))
		{
			callBack.CallBack(Data, Sender);
			System.out.println("Checking Game Modes" + Msg);
		}
		
		
		else if (Flag.equals("S_CHT"))
		{
			callBack.CallBack(Data, Sender);
		}
		else if (Flag.equals("S_CPD"))
		{
			callBack.CallBack(Data, Sender);
			System.out.println("Card Recived" + Msg);
		}
		
		else if (Flag.equals("S_STS"))
		{
			callBack.CallBack(Data, Sender);
			System.out.println("Table Suite Recived" + Msg);
		}
		
		else if (Flag.equals("S_PWN"))
		{
			callBack.CallBack(Data, Sender);
			System.out.println("Player Won " + Msg);
		}
		
		else if (Flag.equals("S_ROE"))
		{
			callBack.CallBack(Data, Sender);
			System.out.println("Round Ended" + Msg);
		}
		
		else if (Flag.equals("S_GME"))
		{
			callBack.CallBack(Data, Sender);
			System.out.println("Game Ended" + Msg);
		}
		
		
		
		
	}

}
