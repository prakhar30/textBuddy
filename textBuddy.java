import java.util.LinkedList;
import java.util.Scanner;
import java.util.Arrays;

import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;



public class textBuddy {
	public static LinkedList<String> textElements=new LinkedList<String>();
	public static Scanner sc=new Scanner(System.in);
	private static final int MIN_ARRAY_LENGTH=1;
	private static final int LENGTH_OF_ADD=4;
	private static final int LENGTH_OF_DELETE=7;
	private static final int LENGTH_OF_SEARCH=7;

	private static final String MESSAGE_WELCOME="Welcome to textBuddy. %1$s is ready for you.\nType help for available commands.";
	private static final String MESSAGE_INVALID="invalid";
	private static final String MESSAGE_COMMAND="Command: ";
	private static final String MESSAGE_ADDED="Added to %1$s : %2$s";
	private static final String MESSAGE_CLEARED="All content deleted from %1$s";
	private static final String MESSAGE_DELETED="deleted from %1$s : %2$s";
	private static final String MESSAGE_SORTED="file is now sorted alphabetically";
	private static final String MESSAGE_SEARCHED="The file contains the following lines with the word : %1$s";
	private static final String MESSAGE_EXIT="Thank you";
	private static final String MESSAGE_ERROR="Error writing to file %1$s";
	private static final String MESSAGE_CANNOT_DELETE="Cannot delete because the element doesn't exist in file %1$s";
	private static final String MESSAGE_CANNOT_DISPLAY="cannot display: the file is empty";
	private static final String MESSAGE_CANNOT_SORT="cannot sort as the file is empty";
	private static final String MESSAGE_CANNOT_SEARCH="cannot search the element in the file";
	private static final String MESSAGE_HELP="Commands Available\n1. add\n2. display\n3. sort\n4. clear(deletes the contents of the file)\n5. delete(index)\n6. search(string to be searched)\n7. exit";

	private static String FILE_NAME= "abc.txt";

	public static void main(String[] args)throws IOException{

		    if(args.length!=MIN_ARRAY_LENGTH){
			System.out.println(MESSAGE_INVALID);
			return;
		}
		FILE_NAME=args[0];
		File f1 = new File(FILE_NAME);
		if(f1.exists()){
			FileReader fin = new FileReader(FILE_NAME);
			Scanner src = new Scanner(fin);
			src.useDelimiter(", *");
			while(src.hasNext()){
				textElements.add(src.next());
			}
		}
		System.out.println(String.format(MESSAGE_WELCOME,FILE_NAME));
		while(true){
			String command=readUserCommand();
			if(command.equalsIgnoreCase("exit")){
				String result=exit();
				System.out.println(result);
				break;
			}
			else {
			String result=evaluateCommands(command);
	        	System.out.println(result);
			}
		}
    }

	public static String evaluateCommands(String command){
			if(command.equalsIgnoreCase("display")){
				return display();
			}
			else if(command.startsWith("add")){
				return add(command.substring(LENGTH_OF_ADD));
			}
			else if(command.equalsIgnoreCase("clear")){
				return clear();
			}
			else if(command.startsWith("delete")){
				return delete(command.substring(LENGTH_OF_DELETE));
			}
			else if(command.startsWith("sort")){
				return sort();
			}
			else if(command.startsWith("search")){
		    return search(command.substring(LENGTH_OF_SEARCH));
			}
			else if(command.equalsIgnoreCase("exit")){
				return exit();
			}
			else if(command.equalsIgnoreCase("help")){
				return help();
			}
			else{
				return MESSAGE_INVALID;
			}
	}

	public static String help(){
		return MESSAGE_HELP;
	}


	public static String readUserCommand(){
		System.out.print(MESSAGE_COMMAND);
		String command=sc.nextLine();
		command=command.trim();
		return command;
	}

	public static String add(String nextSentence){
		textElements.add(nextSentence);
		return String.format(MESSAGE_ADDED,FILE_NAME,nextSentence);
	}

	public static String display(){
		String result="";
		if (textElements.size()==0)
			return MESSAGE_CANNOT_DISPLAY;
		for (int i =0; i<textElements.size();i++){
			result += (i+1+". "+textElements.get(i));
			if (i!=textElements.size()-1){
				result +="\n";
			}
		}
		return result;
	}

	public static String clear(){
		textElements.clear();
		return String.format(MESSAGE_CLEARED,FILE_NAME);
	}

	public static String delete(String indexString){
		int index=Integer.parseInt(indexString);
		index--;
		if(index>=textElements.size()||index<0){
			return String.format(MESSAGE_CANNOT_DELETE,FILE_NAME);
		}
		else{
			String textDeleted=textElements.remove(index);
		    return String.format(MESSAGE_DELETED,FILE_NAME,textDeleted );
		}
	}
    
	public static String sort(){
		if(textElements.size()==0)
			return MESSAGE_CANNOT_SORT;
		String [] tempStorage = textElements.toArray(new String[textElements.size()]);
        Arrays.sort(tempStorage);
        textElements.clear();
        for(int i=0;i<tempStorage.length;i++){
        	textElements.add(i,tempStorage[i]);
        	}
        return MESSAGE_SORTED;
	}

	public static String search (String findElement){
		String result=String.format(MESSAGE_SEARCHED, findElement)+"\n";
		int counter=1;
		if(textElements.size()==0)
			return MESSAGE_CANNOT_SEARCH;
		for(int i=0;i<textElements.size();i++){
			String tempStr=textElements.get(i);
			if(tempStr.contains(findElement)){
				result+=(counter++ + ". "+tempStr+"\n");
				}
			}
		if(counter==1)
			return MESSAGE_CANNOT_SEARCH;
		else
			return result;
	}

	public static String exit(){
		createTextFile();
		return MESSAGE_EXIT;
    }

	public static void createTextFile(){
		try {

			FileWriter fileWriter =new FileWriter(FILE_NAME);

			BufferedWriter bufferedWriter =new BufferedWriter(fileWriter);

			for(int i=0;i<textElements.size();i++){
				bufferedWriter.append((String)textElements.get(i));
				bufferedWriter.write(',');
			}
			bufferedWriter.close();
		}
		catch(IOException ex) {
			System.out.println(String.format(MESSAGE_ERROR,FILE_NAME));
		}
	}
}
