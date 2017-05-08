
public class Instruction{
	private String operation;
	private String op1;
	private String op2;
	private int immediate;

	public Instruction(String op1, int value){
		this.operation = "LOAD";
		this.op1 = op1;
		this.op2 = null;
		this.immediate = value;
	}

	public Instruction(String operation, String op1, String op2){
		this.operation = operation;
		this.op1 = op1;
		this.op2 = op2;
		this.immediate = 100;
	}

	public String getOperation(){
		return this.operation;
	}

	public String getOp1(){
		return this.op1;
	}

	public String getOp2(){
		return this.op2;
	}

	public int getImmediate(){
		return this.immediate;
	}

	public Result performOperation(int value1, int value2){
		if(this.operation.equals("LOAD")){
			return new Result(this.op1, this.immediate);
		}else if(this.operation.equals("CMP")){
			return new Result(value1, value2);
		}else{
			return new Result(this, value1, value2);
		}
	}
}