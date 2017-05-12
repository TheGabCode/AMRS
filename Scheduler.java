import java.util.LinkedList;
import java.util.HashMap;

import java.util.Scanner;
public class Scheduler{
	Scanner sc = new Scanner(System.in);
	LinkedList<Instruction> instructions = new LinkedList<Instruction>();
	HashMap<String, Instruction> queue = new HashMap<String, Instruction>();
	int c = 0;
	int total_clock_cycles;
	int clock_cycle;
	HashMap<String,Integer> registers;
	HashMap<String,Integer> special_registers;

	public Scheduler(LinkedList<Instruction> instructions, HashMap<String,Integer> registers, HashMap<String,Integer> special_registers){
		this.instructions = (LinkedList<Instruction>)instructions.clone();
		this.registers = (HashMap<String,Integer>)registers.clone();
		this.special_registers = (HashMap<String,Integer>)special_registers.clone();
		clock_cycle = 0;
		total_clock_cycles = 5 + (instructions.size() - 1);
	}


	public void start(){
		initQueue();
		while(c < total_clock_cycles){
			this.clock_cycle += 1;
			System.out.println("clock_cycle " + clock_cycle);

			if(instructions.size()!=0)	{
				this.queue.put("F", instructions.pop());
				this.queue.get("F").fetch(special_registers);
			}

			if(this.queue.get("D") != null)		this.queue.get("D").decode(special_registers);
			if(this.queue.get("E") != null)		this.queue.get("E").execute(registers);
			if(this.queue.get("M") != null)		this.queue.get("M").memory(special_registers);
			if(this.queue.get("W") != null)		this.queue.get("W").writeResult(registers, special_registers);
			
			this.queue.put("W", this.queue.get("M"));
			this.queue.put("M", this.queue.get("E"));
			this.queue.put("E", this.queue.get("D"));
			this.queue.put("D", this.queue.get("F"));

			printResult(registers, special_registers);

			sc.nextLine();
			c++;
		}
	}

	public void initQueue(){
		this.queue.put("F",null);
		this.queue.put("D",null);
		this.queue.put("E",null);
		this.queue.put("M",null);
		this.queue.put("W",null);
	}

	public void printResult(HashMap<String,Integer> registers, HashMap<String,Integer> s_registers){
		for(int i=1; i<17; i++){
			String temp = "R" + Integer.toString(i);
			String temp2 = "R" + Integer.toString(16+i);
			System.out.print("R"+i+": "+registers.get(temp)+"\t");
			System.out.print("R"+Integer.toString(16+i)+": "+registers.get(temp2)+"\n");
		}
		System.out.println("");
		System.out.println("PC: " + s_registers.get("PC") + "\t" + "OF: " + s_registers.get("OF"));
		System.out.println("MBR: " + s_registers.get("MBR") + "\t" + "NF: " + s_registers.get("NF"));
		System.out.println("MAR: " + s_registers.get("MAR") + "\t" + "ZF: " + s_registers.get("ZF"));
	}

	public HashMap<String,Integer> getRegisters(){	
		return this.registers;
	}

	public HashMap<String,Integer> getSpecialRegisters(){
		return this.special_registers;
	}
}