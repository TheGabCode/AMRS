import java.lang.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class CM132{
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


		//gawin yung computation
		
	}
}