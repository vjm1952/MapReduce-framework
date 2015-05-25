

import java.io.Serializable;

public class Message implements Serializable {
	public int command;
	public Object data;
	public Message(int command,Object data) {
		super();
		this.data = data;
		this.command=command;
	}
	
	
}
