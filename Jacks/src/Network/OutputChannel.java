package Network;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import Logic.ISender;


public class OutputChannel implements Runnable,ISender {

	private boolean SendingFlag = false;
	private String Data = "";
	private Socket connection;
	private DataOutputStream outPut;
	
	public OutputChannel(Socket Connection) throws IOException {
		this.connection = Connection;
		outPut = new DataOutputStream(this.connection.getOutputStream());
	}
	
	public void Send(String data)
	{
		this.Data = data;
		this.SendingFlag = true;
	}
	
	@Override
	public void run() {
	
		while (true)
		{
			try
			{
				if(SendingFlag)
				{
					System.out.println("Sending : " + Data);
					outPut.writeUTF(Data);	
					outPut.flush();
					SendingFlag = false;
				}
				
				
				Thread.sleep(1000);
			}
			catch (Exception e)
			{
				
			}
		}
	}
}
