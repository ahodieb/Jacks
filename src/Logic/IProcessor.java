package Logic;


public interface IProcessor {
	public void Process(String Data,ICallBack callBack,ISender Sender);
	public void Process(String Data);
	
}
