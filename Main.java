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
		LinkedList<Instruction> instructions = new LinkedList<Instruction>();
		HashMap<String,Integer> registers = new HashMap<String,Integer>();
		ArrayList<ArrayList<Instruction>> instruction_set = new ArrayList<ArrayList<Instruction>>();
		ArrayList<Instruction> instructions_list = new ArrayList<Instruction>();
		try{
			fr = new FileReader(args[0]);
			br = new BufferedReader(fr);

			String line;
			String[] tokens;
			while((line = br.readLine()) != null){
				tokens = line.split("\\s+");
				instructions.add(new Instruction(tokens[0].toUpperCase(),tokens[1],tokens[2],program_counter));
				program_counter++;
			}

		}catch(IOException e){
			e.printStackTrace();
		}

		total_clock_cycles = 5 + instructions.size() - 1;

		for(int i = 0; i < instructions.size(); i++){
			getRegisters(instructions.get(i),registers);
		}

		Scheduler pipeline = new Scheduler(instructions,registers);

		pipeline.start();

	/*	for(int i = 0; i < instructions.size(); i++){
			instructions.get(i).start(registers);
		}
*/


/*		//printRegisters(registers);
		for(int i = 0; i < instructions.size(); i++){ //arraylist<arraylist<instruction>>()
			instruction_set.add(instructions_list);
			System.out.println("BBB"+instruction_set.get(i).size());			
		}
*/
	
/*		for(int i = 0; i < instructions.size(); i++){

		}

*/


	//	System.out.println("BBB"+instruction_set.get(1).size());
	/*for(int i = 0; i < instruction_set.size();i++){
			for(int j = 0; j < instruction_set.get(i).size();j++){
				System.out.println("Clock cycle" + (i+1));
				System.out.println(j);
			}
		}*/
		
	/*	int stage = 0;
		for(int i = 0; i < instruction_set.size(); i++){
			for(int j = 0; j < instruction_set.get(stage).size(); j++){
				System.out.println("AAAA"+instruction_set.get(stage).size());
				if(stage == 0){
					System.out.println("Clock Cycle: " + 1);
					instruction_set.get(stage).get(j).state = "F";
				}else if (stage == 1) {
					System.out.println("Clock Cycle: " + 2);
					instruction_set.get(stage-1).get(j).state = "D";
					instruction_set.get(stage-1).get(j).decode(registers);
					instruction_set.get(stage).get(j).fetch();
				}else if (stage == 2){
					instruction_set.get(stage).get(j).state = "E";
				}else if(stage == 3){
					instruction_set.get(stage).get(j).state = "M";
				}else if(stage == 4){
					instruction_set.get(stage).get(j).state = "W";
				}
			}
			stage++;
		}
*/


	}



}