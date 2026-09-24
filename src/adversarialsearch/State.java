package adversarialsearch;
import java.io.File;                  
import java.io.FileNotFoundException; 
import java.util.Scanner;   
import java.util.Vector;
import java.util.HashSet;
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
		result = result + board.length;

    	for (int row = 0; row < board.length; row++) {
        	for (int col = 0; col < board[row].length; col++) {
            	int y = board.length - row - 1;

            	if (agentX[0] == col && agentY[0] == y) {
                	result= result + "A";
            	} else if (agentX[1] == col && agentY[1] == y) {
                	result = result + "B";
            	} else {
                	result = result + board[row][col];
            	}
        	}
        	result = result + "\n";
    	}

    	return result;
	}
	 public State(State other) {
		 board = new char[other.board.length][];
		 for (int i = 0; i < other.board.length; i++) {
			 board[i] = other.board[i].clone();
		 }
		 agentX = other.agentX.clone();
		 agentY = other.agentY.clone();
		 score = other.score.clone();
		 moves = new Vector<>(other.moves);
	}
	public State copy() {
		State state1 = new State(this);
		
	}	
	public Vector<String> legalMoves(int agent) {
		Vector<String> legalmoves = new Vector<>();
		int x = agentX[agent];
		int row = board.length -1 - agentY[agent];

		if (row > 0 && board[row - 1][x] != '#') {
			legalmoves.add("up");
	    }
	    if (x + 1 < board[row].length && board[row][x + 1] != '#') {
	        legalmoves.add("right");
	    }
	    if (row + 1 < board.length && board[row + 1][x] != '#') {
	        legalmoves.add("down");
	    }
	    if (x > 0 && board[row][x - 1] != '#') {
	        legalmoves.add("left");
	    }
	    if (board[row][x] == '*') {
	        legalmoves.add("eat");
	    }
	    if (board[row][x] == ' ') {
	        legalmoves.add("block");
	    }

    	return legalmoves;
	}
	public Vector<String> legalMoves() {
    	return legalMoves(turn);
	}	
	public void execute(String action) {
		if (action == "up") {
			agentY[turn] += 1;
			moves.add("up");
		}
		if (action == "right") {
			agentX[turn] += 1;
			moves.add("right");
		}
		if (action == "down") {
			agentY[turn] -= 1;
			moves.add("down");
		}
		if (action == "left") {
			agentX[turn] -= 1;
			moves.add("left");
		}
		if (action == "eat") {
			food -= 1;
			score[turn] += 1;
			board[agentX[turn]][agentY[turn]] = ' ';
			moves.add("eat");
		}
		if (action == "block") {
			board[agentX[turn]][agentY[turn]] = '#';
			System.out.println(board[agentX[turn]][agentY[turn]]);
			moves.add("block");
		}

	
		int row = board.length - 1 - agentY[turn];
	    int col = agentX[turn];
	
	    if ("eat".equals(action)) {
	        food -= 1;
	        score[turn] += 1;
	        board[row][col] = ' ';
	        moves.add("eat");
	    } else if ("block".equals(action)) {
	        board[row][col] = '#';
	        moves.add("block");
	    }
	}
	
	/*(
	public boolean isLeaf() {
		if (food == 0) {
			return true;
		}
		if (legalMoves()) {
			return true;
		}	
		return false;
		
	}
	*/
	
}
