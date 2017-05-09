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
		int program_counter = 0; //PC
		LinkedList<Instruction> instructions;
		HashMap<String,Integer> registers = new HashMap<String,Integer>();
		ArrayList<ArrayList<Instruction>> instruction_set = new ArrayList<ArrayList<Instruction>>();
		ArrayList<Instruction> instructions_list = new ArrayList<Instruction>();
		Parser parser = new Parser(args[0]);

		instructions = (LinkedList<Instruction>) parser.getInstructions();

		total_clock_cycles = 5 + instructions.size() - 1;

		for(int i = 0; i < instructions.size(); i++){
			getRegisters(instructions.get(i),registers);
		}

		Scheduler pipeline = new Scheduler(instructions,registers);

		pipeline.start();

	}



}