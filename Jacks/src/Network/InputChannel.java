package Network;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import Logic.ICallBack;
import Logic.Logic;


public class InputChannel implements Runnable {

	private Socket connection;
	private ICallBack callBack;
	private DataInputStream inPut;
	
	public InputChannel(Socket Connection,ICallBack CallBack) throws IOException {
		this.connection = Connection;
		this.callBack = CallBack;
		this.inPut = new DataInputStream(connection.getInputStream());
	}
	
	@Override
	public void run() {
	
		while (true)
		{
			try
			{
				
				String Input = inPut.readUTF();
				callBack.CallBack(Input,this);
				Logic.sleep(1000);
				
			}
			catch (Exception e)
			{
				
			}
		}
	}
}
