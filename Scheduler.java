import java.util.LinkedList;
import java.util.HashMap;
public class Scheduler{
	LinkedList<Instruction> instructions = new LinkedList<Instruction>();
	HashMap<String, Instruction> queue = new HashMap<String, Instruction>();
	int c = 0;
	int total_clock_cycles;
	int clock_cycle;
	HashMap<String,Integer> registers;
	public Scheduler(LinkedList<Instruction> instructions,HashMap<String,Integer> registers){
			this.instructions = (LinkedList<Instruction>)instructions.clone();
			this.registers = (HashMap<String,Integer>)registers.clone();
			clock_cycle = 0;
			total_clock_cycles = 3 + (instructions.size() - 1);
	}


	public void start(){
		initQueue();
		while(c < total_clock_cycles){
			this.clock_cycle += 1;
			System.out.println("clock_cycle " + clock_cycle);

			if(instructions.size()!=0)	{
				this.queue.put("F", instructions.pop());
				this.queue.get("F").fetch();

			}

			if(this.queue.get("D") != null)		this.queue.get("D").decode(registers);
			if(this.queue.get("E") != null)		this.queue.get("E").performOperation(registers);
			
		
			this.queue.put("E", this.queue.get("D"));
			this.queue.put("D", this.queue.get("F"));
			c++;
		}
	}

	public void initQueue(){
		this.queue.put("F",null);
		this.queue.put("D",null);
		this.queue.put("E",null);
	}



}