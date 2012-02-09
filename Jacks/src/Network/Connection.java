package Network;

import java.io.IOException;
import java.net.Socket;

import Logic.ICallBack;
import Logic.IProcessor;
import Logic.ISender;


public class Connection implements  ICallBack,ISender {
	
	protected ICallBack callBack;
	protected Socket connection;
	protected InputChannel inPutChannel;
	protected OutputChannel outPutChannel;	
	protected IProcessor Processor;
	
	public Connection(){}
	public Connection(String IP,int Port,ICallBack CallBack,IProcessor Processor) throws IOException {
		this.callBack = CallBack;
		
		this.connection = new Socket(IP, Port);
		this.outPutChannel = new OutputChannel(this.connection);
		this.inPutChannel = new InputChannel(this.connection, this);
		
		Thread outputThread = new Thread(outPutChannel);
		Thread inputThread   = new Thread(inPutChannel);
		
		this.Processor = Processor;
		
		outputThread.start();
		inputThread.start();
		
		
	}


	public void Send(String Data)
	{
		this.outPutChannel.Send(Data);
	}
	

	@Override
	public void CallBack(String Data,Object Caller) {
		Processor.Process(Data, this.callBack,this);
	}
}
