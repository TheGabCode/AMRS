import java.util.LinkedList;
import java.util.HashMap;

import java.util.Scanner;
public class Scheduler{
	Scanner sc = new Scanner(System.in);
	LinkedList<Instruction> instructions = new LinkedList<Instruction>();
	LinkedList<Dependency> dependencies = new LinkedList<Dependency>();
	LinkedList<Instruction> readyQueue = new LinkedList<Instruction>();
	LinkedList<Integer> stall = new LinkedList<Integer>();
	HashMap<String, Instruction> queue = new HashMap<String, Instruction>();
	HashMap<String,Integer> registers;
	HashMap<String,Integer> special_registers;
	int clock_cycle;
	

	public Scheduler(LinkedList<Instruction> instructions, LinkedList<Dependency> dependencies, HashMap<String,Integer> registers, HashMap<String,Integer> special_registers){
		this.instructions = (LinkedList<Instruction>)instructions.clone();
		this.dependencies = (LinkedList<Dependency>) dependencies.clone();
		this.registers = (HashMap<String,Integer>)registers.clone();
		this.special_registers = (HashMap<String,Integer>)special_registers.clone();

		clock_cycle = 0;
		initQueue();
	}


	public void start(){
		while(this.clock_cycle-1 < instructions.size() || readyQueue.size() != 0 || queue.get("W") != null || queue.get("W") != null || queue.get("M") != null || queue.get("E") != null || queue.get("D") != null){
			this.clock_cycle += 1;

			if(this.clock_cycle-1 < instructions.size())	this.queue.put("F", instructions.get(this.clock_cycle-1));

			if(this.queue.get("W") != null)		this.queue.get("W").writeResult(registers, special_registers);
			if(this.queue.get("M") != null)		this.queue.get("M").memory(special_registers);
			if(this.queue.get("E") != null)		this.queue.get("E").execute(registers);
			if(this.queue.get("D") != null)		this.queue.get("D").decode(special_registers);
			if(this.queue.get("F") != null)		this.queue.get("F").fetch(special_registers);

			//stall the dependent instruction
			if(this.queue.get("F") != null && checkDependency()){
				this.readyQueue.add(this.queue.get("F"));
				this.stall.add(this.clock_cycle);
				this.queue.put("F", null);
			}

			//check if the dependent instruction is ready to execute
			if(this.stall.size() != 0 && this.clock_cycle == this.stall.getFirst()+3){
				this.queue.put("F", this.readyQueue.pop());
				this.stall.pop();
			}
			System.out.println("");
			printTable();
			System.out.println("");
			
			for(int i=0; i<readyQueue.size(); i++){
				this.readyQueue.get(i).stall();	
			}
				
			

			this.queue.put("W", this.queue.get("M"));
			this.queue.put("M", this.queue.get("E"));
			this.queue.put("E", this.queue.get("D"));
			this.queue.put("D", this.queue.get("F"));
			this.queue.put("F", null);

			
			printResult(registers, special_registers);

			sc.nextLine();
		}
	}

	public void initQueue(){
		this.queue.put("F",null);
		this.queue.put("D",null);
		this.queue.put("E",null);
		this.queue.put("M",null);
		this.queue.put("W",null);
	}

	public void printTable(){
		int longestInstruction = -1;
		System.out.println("Clock Cycle #" + this.clock_cycle);
		System.out.println("----Pipeline Table----");
		for(int i=0; i<instructions.size(); i++){
			int len = instructions.get(i).getOperation().length() + instructions.get(i).getOp1().length() + instructions.get(i).getOp2().length() + 3; 
			if(len > longestInstruction){
				longestInstruction = len;
			}
		}

		for(int i=-1; i<instructions.size(); i++){
			for(int j=-1; j<this.clock_cycle; j++){
				if(i==-1){
					if(j==-1){
						for(int k=0; k<longestInstruction; k++){
							System.out.print(" ");
						}	
					}else{
						System.out.print(" " + (j+1) + " ");	
					}
				}else{
					if(j==-1){
						System.out.print(instructions.get(i).getOperation() + " " + instructions.get(i).getOp1() + " " + instructions.get(i).getOp2() + " ");
					}else{
						for(int k=0; k<instructions.get(i).getAddress()-1; k++){
							System.out.print("   ");
						}
						for(int k=0; k<instructions.get(i).states.size(); k++){
							System.out.print(" " + instructions.get(i).states.get(k) + " ");
						}
						break;
					}
					
				}
			}

			System.out.println("");
		}
	}

	public boolean checkIfInReadyQueue(int address){
		for(int i=0; i<readyQueue.size(); i++){
			if(address == readyQueue.get(i).getAddress()){
				return true;
			}
		}
		return false;
	}

	public void printResult(HashMap<String,Integer> registers, HashMap<String,Integer> s_registers){
		System.out.println("----Registers----");		
		for(int i=1; i<17; i++){
			String temp = "R" + Integer.toString(i);
			String temp2 = "R" + Integer.toString(16+i);
			if(i<10){

				System.out.print("R0"+i+": "+registers.get(temp)+"\t\t");
			}else{
				System.out.print("R"+i+": "+registers.get(temp)+"\t\t");
			}
			System.out.print("R"+Integer.toString(16+i)+": "+registers.get(temp2)+"\n");
		}

		System.out.println("\n----Special Registers----");
		System.out.println("PC:\t" + s_registers.get("PC") + "\n" + "OF:\t" + s_registers.get("OF"));
		System.out.println("MBR:\t" + s_registers.get("MBR") + "\n" + "NF:\t" + s_registers.get("NF"));
		System.out.println("MAR:\t" + s_registers.get("MAR") + "\n" + "ZF:\t" + s_registers.get("ZF"));
	}

	public HashMap<String,Integer> getRegisters(){	
		return this.registers;
	}

	public HashMap<String,Integer> getSpecialRegisters(){
		return this.special_registers;
	}

	public boolean checkDependency(){
		int dependentAddress = this.queue.get("F").getAddress();
		for(int i=0; i<this.dependencies.size(); i++){
			if(dependentAddress == this.dependencies.get(i).addressOfDependent){
				if (this.clock_cycle < (this.dependencies.get(i).addressDependentOn + 5)) return true;
				else{
					for(int j=0; j<this.readyQueue.size(); j++){
						if(this.dependencies.get(i).addressDependentOn == readyQueue.get(j).address)	return true;
					}
				}
			}
		}
		return false;
	}
}
