import java.util.*;
import java.io.*;
public class Main{
	public static void getRegisters(Instruction i,HashMap<String,Integer> registers){
		if(i.operand1.matches("^-?\\d+$") == false){
			registers.put(i.operand1,0);
		}
	}

	public static void printRegisters(HashMap<String,Integer> registers){
		Set regs = registers.keySet();
		System.out.println("Registers: ");
		for(Iterator i = regs.iterator(); i.hasNext(); ){
			String curr = (String)i.next();
			System.out.println(curr + ": " + registers.get(curr));
		}
	}

	public static void main(String[] args){
		int total_clock_cycles;
		BufferedReader br;
		FileReader fr;
		
		LinkedList<Instruction> instructions;
		HashMap<String,Integer> registers = new HashMap<String,Integer>();
		HashMap<String,Integer> special_registers= new HashMap<String,Integer>();
		
		Parser parser = new Parser(args[0]);
		
		instructions = (LinkedList<Instruction>) parser.getInstructions();
		parser.setDependencies();

		total_clock_cycles = 5 + instructions.size() - 1;
		
		//initialize special registers
		special_registers.put("MAR",null);
		special_registers.put("MBR",null);
		special_registers.put("PC",0);
		special_registers.put("OF",null);
		special_registers.put("ZF",null);
		special_registers.put("NF",null);

		//initialize storage registers
		for(int i=1; i<33; i++){
			String temp = "R" + Integer.toString(i);
			registers.put(temp, 100);
		}

		Scheduler pipeline = new Scheduler(instructions, parser.getDependencies(), registers, special_registers);

		pipeline.start();
		System.out.println("****SUMMARY****");
		parser.printDependencyTable();
		System.out.println("");
		System.out.println("Total Clock Cycles:\t" + (pipeline.clock_cycle-1));
		
	}
}
