import java.lang.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CM132{
	static LinkedList<String> input2 = new LinkedList<String>();
	static LinkedList<Vector> instructions = new LinkedList<Vector>();
	HashMap<String, Integer> values = new HashMap<String, Integer>();
	static int overflowFlag = 0, zeroFlag = 0, negativeFlag = 0;

	public static void main(String []args){

		LinkedList operations = new LinkedList();
		LinkedList operand1 = new LinkedList();
		LinkedList operand2 = new LinkedList();

		System.out.println(args[0]);
		String input = args[0];
		try{
			BufferedReader br = new BufferedReader(new FileReader(input));
			String inText;

			while((inText = br.readLine()) != null){
					
					
				inText.trim();
				inText = inText.replaceAll(",", "");
				StringTokenizer st = new StringTokenizer(inText);
				while(st.hasMoreTokens()){
					//Object na tumatanggap ng array ng string
					operations.add(st.nextToken());
					operand1.add(st.nextToken());
					operand2.add(st.nextToken());
				}
			}

		}catch(IOException e){
			System.out.println(e.toString());
		}

		int population = operations.size();
		System.out.println("Instruction\tOperand1\tOperand2 ");
		for(int i=0; i<operations.size(); i++){
			System.out.println(operations.get(i)+ "\t\t" +operand1.get(i)+ "\t\t" +operand2.get(i));
		}

		//FILE READING
		String line = null;

		try{			
		 	BufferedReader br = new BufferedReader(new FileReader(args[0]));
		 	while((line = br.readLine()) != null){
				input2.add(line);
			}
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}

		//Adding to vector of instructions
		String s1;
		for(int i=0; i<input2.size(); i++){
			Vector<String> instvector = new Vector<String>();
			String data = input2.get(i);
				StringTokenizer tokenizer = new StringTokenizer(data);
					while(tokenizer.hasMoreTokens()){
						s1 = tokenizer.nextToken();
						s1 = s1.replaceAll(",", "");
						instvector.add(s1);
					}
			instructions.add(instvector);
		}

		//Printing
		for(Vector s : instructions){
			System.out.println(s);
		}

		CM132 mainflow = new CM132();

		mainflow.process();
	}//end of process

	//gawin yung computation
		
	public void process(){
		int a = 0, op = 0, line_count = 0; 

		for(int i=0; i<instructions.get(0).size(); i++){
			if(!values.containsKey((String)instructions.get(i).get(1))){
				values.put((String)instructions.get(i).get(1), 0);
			}
		}

		while(a<instructions.size()){
			line_count++;
			op = checkOperand1(a);
			if(op == 1){
				performLoad(a);
			}else if(op == 2){
				performAdd(a);
			}else if(op == 3){	
				performSub(a);
			}else if(op == 4){
				performCmp(a);
			}else{
				System.out.println("Syntax error at line number: " + line_count + " " + instructions.get(a));
			}
			a++;
		}
	}

	public int checkOperand1(int a){
		int op1 = 0;
		String operand1 = (String)instructions.get(a).get(0);
		String pattern1 = "LOAD", pattern2 = "ADD", pattern3 = "SUB", pattern4 = "CMP";

		Pattern p1 = Pattern.compile(pattern1);
		Pattern p2 = Pattern.compile(pattern2);
		Pattern p3 = Pattern.compile(pattern3);
		Pattern p4 = Pattern.compile(pattern4);

		Matcher match1 = p1.matcher(operand1);
		Matcher match2 = p2.matcher(operand1);
		Matcher match3 = p3.matcher(operand1);
		Matcher match4 = p4.matcher(operand1);

		if(match1.matches()){
			op1 = 1;
		}else if(match2.matches()){
			op1 = 2;
		}else if(match3.matches()){
			op1 = 3;
		}else if(match4.matches()){
			op1 = 4;
		}else{
			System.out.println("Syntax Error!");
		}
		
		return op1;
		
	}//end of checkOperand1

	public void performLoad(int a){
		System.out.println("LOAD");
		int checker = 0;
		//always integer agad yung operand2
		if(values.containsKey((String)instructions.get(a).get(1))){
			checker = Integer.valueOf(values.get((String)instructions.get(a).get(1)));
			if( checker > 9){
				overflowFlag = 1;
			}else{
				overflowFlag = 0;
			}
			values.put((String)instructions.get(a).get(1), Integer.valueOf((String)instructions.get(a).get(2)));
			
		}
		
		System.out.println("\tOF: " + overflowFlag);
		for (Map.Entry<String, Integer> entry : values.entrySet()) {
		    System.out.println("\t" + entry.getKey()+" : "+entry.getValue());
		}
	}//end of performLoad

	public void performAdd(int a){
		System.out.println("ADD");
		//check if operand 1 exists in hashmap
		//if yes, get key of that operand
		int firstOp = 0, secondOp = 0;

		if(values.containsKey((String)instructions.get(a).get(1)) && values.containsKey((String)instructions.get(a).get(2))){
			firstOp = Integer.valueOf(values.get((String)instructions.get(a).get(1)));
			secondOp = Integer.valueOf(values.get((String)instructions.get(a).get(2)));
			firstOp = firstOp + secondOp;
			values.put((String)instructions.get(a).get(1), firstOp);
			if(firstOp > 9){
				overflowFlag = 1;
			}else{
				overflowFlag = 0;
			}
		}

		System.out.println("\tOF: " + overflowFlag);
		for (Map.Entry<String, Integer> entry : values.entrySet()) {
		    System.out.println("\t" + entry.getKey()+" : "+entry.getValue());
		}

	}//end of performLoad

	public void performSub(int a){
		System.out.println("SUB");

		int firstOp = 0, secondOp = 0;

		if(values.containsKey((String)instructions.get(a).get(1)) && values.containsKey((String)instructions.get(a).get(2))){
			firstOp = Integer.valueOf(values.get((String)instructions.get(a).get(1)));
			secondOp = Integer.valueOf(values.get((String)instructions.get(a).get(2)));
			firstOp = firstOp - secondOp;
			values.put((String)instructions.get(a).get(1), firstOp);
			if(firstOp > 9){
				overflowFlag = 1;
			}else{
				overflowFlag = 0;
			}
		}

		System.out.println("\tOF: " + overflowFlag);
		for (Map.Entry<String, Integer> entry : values.entrySet()) {
		    System.out.println("\t" + entry.getKey()+" : "+entry.getValue());
		}
	}//end of performLoad

	public void performCmp(int a){
		System.out.println("CMP");

		int firstOp = 0, secondOp = 0, result = 0;

		if(values.containsKey((String)instructions.get(a).get(1)) && values.containsKey((String)instructions.get(a).get(2))){
			firstOp = Integer.valueOf(values.get((String)instructions.get(a).get(1)));
			secondOp = Integer.valueOf(values.get((String)instructions.get(a).get(2)));
			result = firstOp - secondOp;			
			if(result == 0 ){
				zeroFlag = 1;
			}else{
				zeroFlag = 0;
			}
			if(result < 0 ){
				negativeFlag = 1;
			}else{
				negativeFlag = 0;
			}
		}

		System.out.println("\tResult: " + result);
		System.out.println("\tZF: " + zeroFlag);
		System.out.println("\tNF: " + negativeFlag);

	}//end of performCmp
}