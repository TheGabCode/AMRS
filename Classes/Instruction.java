public class Intstruction{
	ArrayList<String> arguments = new ArrayList<String>();
	HashMap<String,Integer> variables = new HashMap<String,Integer>();

	public Instruction(String[] args){
		for(int i = 0; i < args.length; i++){
			arguments.add(args[i]);
		}
	}

	public void classify(){
		String instr = this.arguments.get(0).toUpperCase();
		if(instr.equals("LOAD")){
			load();
		}
		else if(instr.equals("ADD")){
			add();
		}
		else if(instr.equals("SUB")){
			sub();
		}
		else if(instr.equals("CMP")){
			cmp();
		}


	}


	public void load(){
		int value = Integer.parseInt(arguments.get(2));
		this.variables.put(arguments.get(1),value);
	}



	public void add(){	
	}



	public void sub(){
		
	}



	public void cmp(){
		
	}


public static boolean isInteger(String str) {
    if (str == null) {
        return false;
    }
    int length = str.length();
    if (length == 0) {
        return false;
    }
    int i = 0;
    if (str.charAt(0) == '-') {
        if (length == 1) {
            return false;
        }
        i = 1;
    }
    for (; i < length; i++) {
        char c = str.charAt(i);
        if (c < '0' || c > '9') {
            return false;
        }
    }
    return true;
}





}