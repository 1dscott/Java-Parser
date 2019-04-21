/*
a parser using java
*/


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Parser
{	
	public static void main(String[] args)
	{
	
	//read input from the keyboard
	Scanner scan = new Scanner(System.in);
	System.out.println("Please enter your input followed by a $");	
	String input = scan.nextLine();
	
	//split the users input into tokens and store them into String array
	String[]tokens = input.split("(?=\\s+)|(?<=\\s+)|(?=\\=)|(?<=\\=)|(?=\\;)|(?<=\\;)|(?=\\,)|(?<=\\,)|(?=\\+)|(?<=\\+)|(?=\\-)|(?<=\\-)|(?=\\*)|(?<=\\*)|(?=\\/)|(?<=\\/)|(?=\\()|(?<=\\()|(?=\\))|(?<=\\))|(?=\\&)|(?<=\\&)|(?=\\$)|(?<=\\$)");
	
	 //Grammer Rules
	 //1. E -> E + T
	 //2. E -> T
	 //3. T -> T * F
	 //4. T -> F
	 //5. F -> (E)
	 //6. F -> id
	
    //Create table 
	String[][] tableAction = new String[][] {
		//0      1    2    3    4    5     6    7    8
		//id     +    *    (    )    $     E    T    F
	/*0*/{"s5", "-", "-", "s4", "-", "-", "1", "2", "3"},
	/*1*/{"-", "s6", "-", "-", "-", "acc", "-", "-", "-"},
	/*2*/{"-", "r2", "s7", "-", "r2", "r2", "-", "-", "-"},
	/*3*/{"-", "r4", "r4", "-", "r4", "r4", "-", "-", "-"},
	/*4*/{"s5", "-", "-", "s4", "-", "-", "8", "2", "3"},
	/*5*/{"-", "r6", "r6", "-", "r6", "r6", "-", "-", "-"},
	/*6*/{"s5", "-", "-", "s4", "-", "-", "-", "9", "3"},
	/*7*/{"s5", "-", "-", "s4", "-", "-", "-", "-", "10"},
	/*8*/{"-", "s6", "-", "-", "s11", "-", "-", "-", "-"},
	/*9*/{"-", "r1", "s7", "-", "r1", "r1", "-", "-", "-"},
	/*10*/{"-", "r3", "r3", "-", "r3", "r3", "-", "-", "-"},
	/*11*/{"-", "r5", "r5", "-", "r5", "r5", "-", "-", "-"},
	};

	//declare all vars
	int columnNum = 0;
	int row = 0;
	String column = "";
	String columnReduce = "";
	String state = "";
	Stack st = new Stack();
    st.push(0);
    System.out.println("stack: " + st);
	
    //loop as long as there is a token
	for(int n=0; n <tokens.length; n++)
	{
		//Save current token
		String currentToken = tokens[n];
		
		//put current token on stack
		st.push(currentToken);
		System.out.println("stack: " + st);
		
		//take off latest stack items for row and column
		column = (String) st.pop();
	    row = (int) st.pop();
		
	    //determine what column 
		if(column.equals("id"))
		{
			columnNum = 0;
		}
		else if (column.equals("+")) 
		{
			columnNum = 1;
		}
		else if (column.equals("*"))
		{
			columnNum = 2;
			
		}
		else if (column.equals("(")) 
		{
			columnNum = 3;
			
		}
		else if (column.equals(")"))
		{
			columnNum = 4;
		}
		else if(column.equals("$"))
		{
			System.out.println("Final Parsing Symbol $ Accepted, Parsing Successful");
			break;
		}
		else if (column.equals("E")) 
		{
			columnNum = 6;
		}
		else if (column.equals("T")) 
		{
			columnNum = 7;
		}
		else if (column.equals("F")) 
		{
			columnNum = 8;
		}
		else
		{
			System.out.println("Unknown Token Entered Parsing Terminated");
			break;
		}	    
		
		//Create string State
		state = tableAction[row][columnNum];	
		
		//while we are in reduce state 
		while(state.equals("r1") || state.equals("r2") || state.equals("r3") || state.equals("r4") || state.equals("r5") || state.equals("r6"))
		{
			
			System.out.println("stack: " + st);	
		
			if(column.equals("id"))
			{
				columnNum = 0;
			}
			else if (column.equals("+")) 
			{
				columnNum = 1;
			}
			else if (column.equals("*"))
			{
				columnNum = 2;
				
			}
			else if (column.equals("(")) 
			{
				columnNum = 3;
				
			}
			else if (column.equals(")"))
			{
				columnNum = 4;
			}
			else if(column.equals("$"))
			{
				System.out.println("Final Parsing Symbol $ Accepted, Parsing Successful");
				break;
			}
			else if (column.equals("E")) 
			{
				columnNum = 6;
			}
			else if (column.equals("T")) 
			{
				columnNum = 7;
			}
			else if (column.equals("F")) 
			{
				columnNum = 8;
			}
			else
			{
				System.out.println("Unknown Token Entered Parsing Terminated");
				break;
			}		    

			//Create string State
			state = tableAction[row][columnNum];	
			
			//if we get reduce
			if(state.equals("r1"))
			{
				column = (String) st.pop();
				row = (int) st.pop();
				column = "E";
				st.push(row);
				st.push(column);
				int reduceNum = Integer.valueOf(tableAction[row][6]);
				st.push(reduceNum);
				st.push(currentToken);
				System.out.println("stack: " + st);
				column = (String) st.pop();
			    row = (int) st.pop();
			    
			}
			else if(state.equals("r2"))
			{
				column = (String) st.pop();
				row = (int) st.pop();
				column = "E";
				st.push(row);
				st.push(column);
				int reduceNum = Integer.valueOf(tableAction[row][6]);
				st.push(reduceNum);
				st.push(currentToken);	
				System.out.println("stack: " + st);
				column = (String) st.pop();
			    row = (int) st.pop();			   
						
			}
			else if(state.equals("r3"))
			{
				column = (String) st.pop();
				row = (int) st.pop();			
				column = "T";
				st.push(row);
				st.push(column);
				int reduceNum = Integer.valueOf(tableAction[row][7]);
				st.push(reduceNum);	
				st.push(currentToken);
				System.out.println("stack: " + st);
				column = (String) st.pop();
			    row = (int) st.pop();
		
			}
			else if(state.equals("r4"))
			{
				column = (String) st.pop();
				row = (int) st.pop();		
				column = "T";
				st.push(row);
				st.push(column);
				int reduceNum = Integer.valueOf(tableAction[row][7]);
				st.push(reduceNum);	
				st.push(currentToken);
				System.out.println("stack: " + st);
				column = (String) st.pop();
			    row = (int) st.pop();
			}
			else if(state.equals("r5"))
			{
				column = (String) st.pop();
				row = (int) st.pop();
				column = "F";
				st.push(row);
				st.push(column);
				int reduceNum = Integer.valueOf(tableAction[row][8]);
				st.push(reduceNum);
				st.push(currentToken);
				System.out.println("stack: " + st);
				column = (String) st.pop();
			    row = (int) st.pop();
			
			}
			else if(state.equals("r6"))
			{
				column = (String) st.pop();
				row = (int) st.pop();
				column = "F";
				st.push(row);
				st.push(column);
				int reduceNum = Integer.valueOf(tableAction[row][8]);
				st.push(reduceNum);
				st.push(currentToken);
				System.out.println("stack: " + st);
				column = (String) st.pop();
			    row = (int) st.pop();
			}
			else if(state.equals("-"))
			{
				System.out.println("Error Invalid State, Parse Terminated");
				break;
			}
		}
		
		//if we get a state
		if(state.equals("s4"))
		{
			st.push(row);
			st.push(column);
			st.push(4);	
			System.out.println("stack: " + st);
		}
		else if(state.equals("s5"))
		{
			st.push(row);
			st.push(column);
			st.push(5);	
			System.out.println("stack: " + st);
		}
		else if(state.equals("s6"))
		{
			st.push(row);
			st.push(column);
			st.push(6);	
			System.out.println("stack: " + st);
		}
		else if(state.equals("s7"))
		{
			st.push(row);
			st.push(column);
			st.push(7);	
			System.out.println("stack: " + st);
		}
		else if(state.equals("s11"))
		{
			st.push(row);
			st.push(column);
			st.push(11);	
			System.out.println("stack: " + st);
		}
		else if(state.equals("-"))
		{
			System.out.println("Error Invalid State, Parse Terminated");
			break;
		}
		else
		{
			System.out.println("Unknown Token Entered Parsing Terminated");
			break;
		}
	}
}
}
