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
						agentY[0] = r;
						this.board[r][c] = ' ';
					} else if (symbol == 'B') {
						agentX[1] = c;
						agentY[1] = r;
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
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

	}

	public String toString() {
		String result = "";
	

		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
		

				if (agentX[0] == col && agentY[0] == row) {
					result = result + "A";
				} else if (agentX[1] == col && agentY[1] == row) {
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

	public Vector<String> legalMoves(int agent) {
		Vector<String> legalmoves = new Vector<>();
		int x = agentX[agent];
		int row =agentY[agent];

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
		int row = agentY[turn];
		int col = agentX[turn];

		if ("up".equalsIgnoreCase(action)) {
			agentY[turn] -= 1;
			moves.add("up");
		} else if ("right".equalsIgnoreCase(action)) {
			agentX[turn] += 1;
			moves.add("right");
		} else if ("down".equalsIgnoreCase(action)) {
			agentY[turn] += 1;
			moves.add("down");
		} else if ("left".equalsIgnoreCase(action)) {
			agentX[turn] -= 1;
			moves.add("left");
		} else if ("eat".equalsIgnoreCase(action)) {
			food -= 1;
			score[turn] += 1;
			board[row][col] = ' ';
			moves.add("eat");
		} else if ("block".equalsIgnoreCase(action)) {
			board[row][col] = '#';
			moves.add("block");
		}

		this.turn = 1 - this.turn;
	}

	/*
	 * ( public boolean isLeaf() { if (food == 0) { return true; } if (legalMoves())
	 * { return true; } return false;
	 * 
	 * }
	 */
	public boolean isLeaf() {
		if (food == 0) {
			return true;
		}
		boolean agent1CantMove = legalMoves(1).isEmpty();
		boolean agent0CantMove = legalMoves(0).isEmpty();

		if (agent1CantMove || agent0CantMove) {
			return true;
		} else {
			return false;
		}
	}

	public State copy() {
		State newState = new State();

		newState.turn = this.turn;
		newState.food = this.food;
		newState.agentX = this.agentX.clone();
		newState.agentY = this.agentY.clone();
		newState.score = this.score.clone();

		newState.board = new char[this.board.length][];
		for (int i = 0; i < this.board.length; i++) {
			newState.board[i] = this.board[i].clone();
		}

		newState.moves = new Vector<String>(this.moves);

		return newState;
	}

	public double value(int agent) {
		if (!isLeaf()) {
	        return 0.0;
	    }

	    boolean agent0Stuck = legalMoves(0).isEmpty();
	    boolean agent1Stuck = legalMoves(1).isEmpty();


	    if (agent0Stuck && !agent1Stuck) {
	        return (agent == 0) ? -1.0 : 1.0;
	    }
	    if (agent1Stuck && !agent0Stuck) {
	        return (agent == 1) ? -1.0 : 1.0;
	    }


	    int enemy = 1 - agent;
	    if (score[agent] > score[enemy]) {
	        return 1.0;
	    } else if (score[agent] < score[enemy]) {
	        return -1.0;
	    } else {
	        return 0.0;
	    }
		
	}
}
