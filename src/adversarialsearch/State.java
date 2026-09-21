package adversarialsearch;
import java.io.File;                  
import java.io.FileNotFoundException; 
import java.util.Scanner;   
import java.util.Vector;
public class State {

	char[][] board;
	int[] agentX;
	int[] agentY;
	int[] score;
	int turn;
	int food;
	Vector<String> moves;

	public State() {
	        board = new char[0][0];
	        agentX = new int[2];
	        agentY = new int[2];
	        score = new int[2];
	        moves = new Vector<String>();
	}

	 public void read(String file) {
		 File boardFile = new File(file);
		  try (Scanner readBoard = new Scanner(boardFile)) {
			    int rows = readBoard.nextInt();
	            int cols = readBoard.nextInt();
	            readBoard.nextLine(); 	            
	            this.board = new char[rows][cols];
	            
	            
	            this.food = 0;
	            this.score[0] = 0;
	            this.score[1] = 0;
	            this.turn = 0;
	            int r = 0;
	            while (readBoard.hasNextLine()) {
	            	String line = readBoard.nextLine();
	            	
	            	for (int c = 0; c < line.length(); c++) {
	                    char symbol = line.charAt(c);

	                    if (symbol == 'A') {
	                        agentX[0] = c;
	                        agentY[0] = rows - r -1;
	                        this.board[r][c] = ' '; 
	                    } else if (symbol == 'B') {
	                        agentX[1] = c;
	                        agentY[1] = rows - r - 1;
	                        this.board[r][c] = ' ';
	                    } else if (symbol == '*') {
	                        this.food++;
	                        this.board[r][c] = '*';
	                    } else {
	                        this.board[r][c] = symbol;
	                    }
	                }
	                r++;
	            
	            }
	            
	            System.out.println(agentX[1]);
	            System.out.println(agentY[1]);
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		 
	 }
	public String toString() {
    	String result = "";
		result = result + board.length

    	for (int row = 0; row < board.length; row++) {
        	for (int col = 0; col < board[row].length; col++) {
            	int y = board.length - row - 1;

            	if (agentX[0] == col && agentY[0] == y) {
                	result= result + "A";
            	} else if (agentX[1] == col && agentY[1] == y) {
                	result = result + "B";
            	} else {
                	result = result + board[row][col]);
            	}
        	}
        	result = result + "\n";
    	}

    	return result;
	}
	 
}
