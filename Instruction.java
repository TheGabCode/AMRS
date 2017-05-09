import java.util.*;
public class Instruction{
	String operation;
	String operand1;
	String operand2;
	int valueOF; //overflow
	int valueNF; //negative flag
	int valueZF; //zero flag
	String state = null;
	int address;
	int immediate;

	public Instruction(String operation,String op1, String op2, int address){
		this.operation = operation;
		this.operand1 = op1;
		this.operand2 = op2;
		this.address = address;
	}

	public void start(HashMap<String,Integer> registers){
		fetch();
		decode(registers);
		performOperation(registers);
//		execute(registers);
		printResult(registers);
	}

	public String getOperation(){
		return this.operation;
	}

	public String getOp1(){
		return this.operand1;
	}

	public void decode(HashMap<String,Integer> registers){
		System.out.println("Decoding...");
		try{
				Thread.sleep(1000);
			}catch(InterruptedException e){
				Thread.currentThread().interrupt();
				return;
		}
		System.out.println(this.operand1 + ": " + registers.get(this.operand1));
		if(this.operand2.matches("^-?\\d+$") == false){ //if also register
			System.out.println(this.operand2 + ": " + registers.get(this.operand2));
		}
		else{
			System.out.println("OP2: " + this.operand2);
		}
	}
	public void fetch(){
		System.out.println("Fetching " + this.operation + " " + this.operand1 + " " + this.operand2 + " at address: " + this.address);
		try{
			Thread.sleep(1000);
		}catch(InterruptedException e){
			Thread.currentThread().interrupt();
			return;
		}
	}

	public void printResult(HashMap<String,Integer> registers){
		System.out.print("Executing...");
		try{
			Thread.sleep(1000);
		}catch(InterruptedException e){
			Thread.currentThread().interrupt();
			return;
		} 
		//print();
		if(this.operation.equals("LOAD")){
			System.out.println("\n" + this.operand1 + ": " + registers.get(this.operand1) + "\n" +
								"OF: " + this.valueOF + "\n" +
								"NF: " + this.valueNF + "\n" +
								"ZF: " + this.valueZF);
		}
		else if(this.operation.equals("CMP")){
			System.out.println("\n" +
								"OF: " + this.valueOF + "\n" +
								"NF: " + this.valueNF + "\n" +
								"ZF: " + this.valueZF);


		}
		else{//if add or sub
			System.out.println("\n" + this.operand1 + ": " + registers.get(this.operand1) + "\n" +
								"OF: " + this.valueOF + "\n" +
								"NF: " + this.valueNF + "\n" +
								"ZF: " + this.valueZF);
		}
	}



	public void performOperation(HashMap<String,Integer> registers){
		int value;
		if(this.operation.equals("LOAD")){
			value = Integer.parseInt(this.operand2);
			registers.put(this.operand1,value); //assigns value to register where value is immediate
			this.valueOF = (value > 99 || value < -99) ? 1 : 0; //checks if overflow
			this.valueNF = 0;
			this.valueZF = 0;
		}
		else if(this.operation.equals("ADD")){
			int op1_value = registers.get(this.operand1); //gets value of register from hashmap of registers
			int op2_value = 0;
			if(this.operand2.matches("^-?\\d+$") == false){ //if operand2 is also a register
				op2_value = registers.get(this.operand2); //get value of operand2 from hashmap of registers
				value = op1_value + op2_value; //perform add and store to value
				registers.put(this.operand1,value); //assigns sum to first operand<dst>
				this.valueOF = (value > 99 || value < -99) ? 1 : 0; //checks if overflow
				this.valueNF = 0;
				this.valueZF = 0;
			}
			else{ //if operand2 is an integer;
				op2_value = Integer.parseInt(this.operand2); //if operand2 is an integer, just parse
				value = op1_value + op2_value; //perform addition
				registers.put(this.operand1,value); //assigngs sum to dst
				this.valueOF = (value > 99 || value < -99) ? 1 : 0; //check if overflow
				this.valueNF = 0;
				this.valueZF = 0;
			}
		}
		else if(this.operation.equals("SUB")){
			int op1_value = registers.get(this.operand1);
			int op2_value = 0;
			if(this.operand2.matches("^-?\\d+$") == false){ //if operand2 is also a register
				op2_value = registers.get(this.operand2);
				value = op1_value - op2_value;
				registers.put(this.operand1,value);
				this.valueOF = (value > 99 || value < -99) ? 1 : 0;
				this.valueNF = 0;
				this.valueZF = 0;
			}
			else{ //if operand2 is an integer;
				op2_value = Integer.parseInt(this.operand2);
				value = op1_value - op2_value;
				registers.put(this.operand1,value);
				this.valueOF = (value > 99 || value < -99) ? 1 : 0;
				this.valueNF = 0;
				this.valueZF = 0;
			}
		}
		else if(this.operation.equals("CMP")){
			int op1_value = registers.get(this.operand1);
			int op2_value = registers.get(this.operand2);
			value = op1_value - op2_value;
			this.valueZF = value == 0 ? 1 : 0;
			this.valueNF = value < 0 ? 1 : 0;
			this.valueOF = 0;
		}
		else{
			System.out.println("Syntax Error: Undefined instruction operation");
		}
		this.printResult(registers);
	}
}