public class Result{
	int result;
	int valueOF;
	int valueNF;
	int valueZF;
	String registerStored;

	//for load instruction
	public Result(String register, int immediate){
		this.result = (immediate > 99) ? 99 : ((immediate < -99) ? -99 : immediate);
		this.registerStored = register;
		this.valueOF = (this.result > 99 || this.result < -99) ? 1 : 0;
	}

	//for cmp instruction
	public Result(String register, int op1, int op2){
		this.registerStored = register;
		this.result = op1 - op2;
		this.valueZF = result == 0 ? 1 : 0;
		this.valueNF = result < 0 ? 1 : 0;
	}

	//for add & sub instruction
	public Result(Instruction inst, int op1, int op2){
		int res = inst.getOperation().equals("ADD") ? (op1+op2) : (op1-op2);
		this.valueOF = (res > 99 || res < -99) ? 1 : 0;
		
		if(res > 99){
			this.result = 99;
		}else if(res < -99){
			this.result = -99;
		}else{
			this.result = res;
		}

		this.registerStored = inst.getOp1();
	}
}