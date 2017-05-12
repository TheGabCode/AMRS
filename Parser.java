import java.util.*;
import java.io.*;

public class Parser{
	private LinkedList<Instruction> instructions;

	public Parser(String path){
		this.instructions = new LinkedList<Instruction>();
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
}