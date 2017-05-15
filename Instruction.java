import java.util.*;

public class Instruction{
	String operation;
	String operand1;
	String operand2;
	Result result;
	int address;

	public Instruction(String operation, String op1, String op2, int address){
		this.operation = operation;
		this.operand1 = op1;
		this.operand2 = op2;
		this.address = address;
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

	public void fetch(HashMap<String,Integer> s_registers, int clock_cycle){
		//System.out.println("Fetching " + this.operation + " " + this.operand1 + " " + this.operand2 + " at address: " + this.address);
		// System.out.println("Clock cycle: " + clock_cycle);		
		System.out.print(this.operation + " " + this.operand1 + " " + this.operand2);
		for(int i=1; i<clock_cycle; i++){
			System.out.print("\t");
		}
		System.out.print("\tF\n");
		//System.out.println("Fetching " + this.operation + " " + this.operand1 + " " + this.operand2 + " at address: " + this.address);
		// s_registers.put("PC", s_registers.get("PC") + 1);
		s_registers.put("PC", s_registers.get("PC") + 1);
	}

	public void decode(HashMap<String,Integer> s_registers, int clock_cycle){
		// System.out.println("Decoding...");		
		System.out.print(this.operation + " " + this.operand1 + " " + this.operand2 );
		for(int i=1; i<clock_cycle; i++){
			System.out.print("\t");
		}
		System.out.print("F\tD\n");
		// System.out.print("Fetching [" + this.operation + " " + this.operand1 + " " + this.operand2 + "]\t");		
		// System.out.print("Decoding [" + this.operation + " " + this.operand1 + " " + this.operand2 + "]\t\n");
		s_registers.put("MAR", Integer.parseInt(this.operand1.substring(1)));
	}
	
	public void execute(HashMap<String,Integer> registers, int clock_cycle){
		// System.out.println("Executing...");
		System.out.print(this.operation + " " + this.operand1 + " " + this.operand2);
		for(int i=1; i<clock_cycle-1; i++){
			System.out.print("\t");
		}
		System.out.print("F\tD\tE\n");
		// System.out.print("Fetching [" + this.operation + " " + this.operand1 + " " + this.operand2 + "]\t");		
		// System.out.print("Decoding [" + this.operation + " " + this.operand1 + " " + this.operand2 + "]\t");
		// System.out.print("Executing [" + this.operation + " " + this.operand1 + " " + this.operand2 + "]\t\n");
		if(this.operation.equals("LOAD")){
			this.result = new Result(this.operand1, Integer.parseInt(this.operand2));
		}else if(this.operation.equals("CMP")){
			this.result = new Result(this.operand1, registers.get(this.operand1), registers.get(this.operand2));
		}else{
			this.result = new Result(this, registers.get(this.operand1), registers.get(this.operand2));
		}
	}

	public void memory(HashMap<String,Integer> s_registers, int clock_cycle){
		// System.out.println("Memory Accessing...");
		System.out.print(this.operation + " " + this.operand1 + " " + this.operand2);
		for(int i=1; i<clock_cycle-2; i++){
			System.out.print("\t");
		}
		System.out.print("F\tD\tE\tM\n");
		// System.out.print("Fetching [" + this.operation + " " + this.operand1 + " " + this.operand2 + "]\t");		
		// System.out.print("Decoding [" + this.operation + " " + this.operand1 + " " + this.operand2 + "]\t");
		// System.out.print("Executing [" + this.operation + " " + this.operand1 + " " + this.operand2 + "]\t");
		// System.out.print("Memory Accessing [" + this.operation + " " + this.operand1 + " " + this.operand2 + "]\t\n");
		s_registers.put("MBR", this.result.result);
	}

	public void writeResult(HashMap<String,Integer> registers, HashMap<String,Integer> s_registers){		
		// System.out.println("Writing Result...");
		System.out.print(this.operation + " " + this.operand1 + " " + this.operand2);
		System.out.print("\tF\tD\tE\tM\tW\n");
		// System.out.print("Fetching [" + this.operation + " " + this.operand1 + " " + this.operand2 + "]\t");		
		// System.out.print("Decoding [" + this.operation + " " + this.operand1 + " " + this.operand2 + "]\t");
		// System.out.print("Executing [" + this.operation + " " + this.operand1 + " " + this.operand2 + "]\t");
		// System.out.print("Memory Accessing [" + this.operation + " " + this.operand1 + " " + this.operand2 + "]\t");
		//  System.out.print("Writing Result [" + this.operation + " " + this.operand1 + " " + this.operand2 + "]\t\n");
		registers.put(this.result.registerStored, s_registers.get("MBR"));
		s_registers.put("OF", this.result.valueOF);
		s_registers.put("NF", this.result.valueNF);
		s_registers.put("ZF", this.result.valueZF);

	}

	// public void stalling(){
	// 	// System.out.println("Decoding...");
	// 	System.out.print("Stalling...\t");
	// }
}
