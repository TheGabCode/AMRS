

public class Register{
	private String name;
	private int value;

	public Register(String name, int value){
		this.name = name;
		this.value = value;
	}

	public String getName(){
		return this.name;
	}

	public int getValue(){
		return this.value;
	}
}