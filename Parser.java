import java.util.*;
import java.io.*;

public class Parser{
	private LinkedList<Instruction> instructions;
	private LinkedList<Dependency> dependencies;
	public Parser(String path){
		this.instructions = new LinkedList<Instruction>();
		this.dependencies = new LinkedList<Dependency>();
		this.parse(path);
	}

	public void parse(String path){
		try{
			BufferedReader br = new BufferedReader(new FileReader(path));
			String inText;
			int lineCount = 0;

			while((inText = br.readLine()) != null){
				inText.trim();
				lineCount += 1;
				inText = inText.replaceAll(",", "");
				StringTokenizer st = new StringTokenizer(inText);
				while(st.hasMoreTokens()){
					String operation = st.nextToken();
					String op1 = st.nextToken();
					String op2 = st.nextToken();

					if(!operation.equals("LOAD") && !operation.equals("ADD") && !operation.equals("SUB") && !operation.equals("CMP")){
						System.out.println("Syntax error: Invalid operation at line " + lineCount);
						System.exit(1);
					}else if(operation.equals("LOAD") && !op2.matches("^-?\\d+$")){
						System.out.println("Syntax error: Invalid LOAD operation format at line " + lineCount);
						System.exit(1);
					}else if(!op1.matches("^R[1-9]|[1-2][0-9]|[3][1-2]$")){
						System.out.println("Syntax error: Invalid register name at line " + lineCount);
						System.exit(1);
					}else if(!operation.equals("LOAD") && !op2.matches("^R[1-9]|[1-2][0-9]|[3][1-2]$")){
						System.out.println("Syntax error: Invalid register name at line " + lineCount);
						System.exit(1);
					}

					System.out.println(operation+"\t"+op1+"\t"+op2);

					Instruction inst = new Instruction(operation, op1, op2, lineCount);
					this.instructions.add(inst);
									
				}
			}
			br.close();
		}catch(IOException e){
			System.out.println(e.toString());
		}
	}

	public LinkedList<Instruction> getInstructions(){
		return this.instructions;
	}

	public void getDependencies(){
		int addressDependentOn;
		int addressOfDependent;
		String register;
		int pos;
		for(int i = 0; i < this.instructions.size(); i++){
			if((i-1) == -1){

			}else{
				for(int j = 0; j < i; j++){
					if(instructions.get(i).getOp1().equals(instructions.get(j).getOp1())){//"WAW"
						addressDependentOn = instructions.get(j).getAddress();
						addressOfDependent = instructions.get(i).getAddress();
						register = instructions.get(i).getOp1();
						this.dependencies.add(new Dependency(addressDependentOn,addressOfDependent,register,"WAW"));
					}
					if(instructions.get(i).getOp1().equals(instructions.get(j).getOp2())){ //"WAR"
						addressDependentOn = instructions.get(j).getAddress();
						addressOfDependent = instructions.get(i).getAddress();
						register = instructions.get(i).getOp1();
						this.dependencies.add(new Dependency(addressDependentOn,addressOfDependent,register,"WAR"));	
					}

					if(instructions.get(i).getOp2().equals(instructions.get(j).getOp1())){ //"RAW"
						addressDependentOn = instructions.get(j).getAddress();
						addressOfDependent = instructions.get(i).getAddress();
						register = instructions.get(i).getOp2();
						this.dependencies.add(new Dependency(addressDependentOn,addressOfDependent,register,"RAW"));	
					}		
				}
				
			}
		}
	}

	public void printDependencies(){
		System.out.println("Dependencies:");
		for(int i = 0; i < this.dependencies.size(); i++){
			System.out.println("Instruction at address: " + this.dependencies.get(i).addressOfDependent + " is dependent on instruction at address: " + this.dependencies.get(i).addressDependentOn
				+"\nRegister: " + this.dependencies.get(i).register
				+"\nDependency Type: " + this.dependencies.get(i).dependencyType);
		}
	}
}