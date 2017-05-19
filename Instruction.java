import java.util.*;

public class Instruction{
	String operation;
	String operand1;
	String operand2;
	Result result;
	int address;
	LinkedList<String> states;

	public Instruction(String operation, String op1, String op2, int address){
		this.operation = operation;
		this.operand1 = op1;
		this.operand2 = op2;
		this.address = address;
		this.states = new LinkedList<String>();
	}

	public String getOperation(){
		return this.operation;
	}

	public String getOp1(){
		return this.operand1;
	}

	public String getOp2(){
		return this.operand2;
	}

	public int getAddress(){
		return this.address;
	}

	public void fetch(HashMap<String,Integer> s_registers){		
		s_registers.put("PC", s_registers.get("PC") + 1);
		this.states.add(new String("F"));
	}

	public void decode(HashMap<String,Integer> s_registers){		
		s_registers.put("MAR", Integer.parseInt(this.operand1.substring(1)));
		this.states.add(new String("D"));
	}
	
	public void execute(HashMap<String,Integer> registers){
		if(this.operation.equals("LOAD")){
			this.result = new Result(this.operand1, Integer.parseInt(this.operand2));
		}else if(this.operation.equals("CMP")){
			this.result = new Result(this.operand1, registers.get(this.operand1), registers.get(this.operand2));
		}else{
			this.result = new Result(this, registers.get(this.operand1), registers.get(this.operand2));
		}
		this.states.add(new String("E"));
	}

	public void memory(HashMap<String,Integer> s_registers){
		s_registers.put("MBR", this.result.result);
		this.states.add(new String("M"));
	}

	public void writeResult(HashMap<String,Integer> registers, HashMap<String,Integer> s_registers){		
		registers.put(this.result.registerStored, s_registers.get("MBR"));
		s_registers.put("OF", this.result.valueOF);
		s_registers.put("NF", this.result.valueNF);
		s_registers.put("ZF", this.result.valueZF);
		this.states.add(new String("W"));
	}

	public void stall(){
		this.states.add(new String("S"));
	}
}
