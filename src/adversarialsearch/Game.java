package adversarialsearch;

import java.util.Vector;

public class Game {
	State b;
	int nodesVisited;
	public Game() {
		b=new State();
		b.read("data/board.txt");
	}

	
	
	public State minimax(State s, int forAgent, int maxDepth, int depth) {
		if (s.isLeaf() || depth >= maxDepth) {
	        return s;
	    }
		boolean findMax = s.turn == forAgent;
	    State bestState = null;
	    
	    
	    double bestValue;
	    
	    if (findMax) {
	        bestValue = -2.0;
	    } else {
	        bestValue = 2.0;
	    }
	    
	    for (String action : s.legalMoves()) {
	        State child = s.copy();
	        child.execute(action);

	        State resultState = minimax(child, forAgent, maxDepth, depth + 1);
	        double resultValue = resultState.value(forAgent);

	        if (findMax) {
	            if (bestState == null || resultValue > bestValue) {
	                bestValue = resultValue;
	                bestState = resultState;
	            }
	        } else {
	            if (bestState == null || resultValue < bestValue) {
	                bestValue = resultValue;
	                bestState = resultState;
	            }
	        }
	    }
	    return bestState;
	    
	   
		
	}
	
	public State alfabeta(State s, int forAgent, int maxDepth, int depth, double alfa, double beta) {
		nodesVisited++;
		if (s.isLeaf() || depth >= maxDepth) {
	        return s;
	    }
		boolean maximizing = (s.turn == forAgent);
	    State bestState = null;
	    
	    
	    double bestValue;
	    
	    if (maximizing) {
	        bestValue = -2.0;
	    } else {
	        bestValue = 2.0;
	    }
	    
	    for (String action : s.legalMoves()) {
	        State child = s.copy();
	        child.execute(action);
	        State resultState = alfabeta(child, forAgent, maxDepth, depth + 1, alfa, beta);
	        double resultValue = resultState.value(forAgent);

	        if (maximizing) {
	            if (resultValue > bestValue) {
	                bestValue = resultValue;
	                bestState = resultState;
	            }
	            alfa = Math.max(alfa, bestValue);
	        } else {
	            if (resultValue < bestValue) {
	                bestValue = resultValue;
	                bestState = resultState;
	            }
	            beta = Math.min(beta, bestValue);
	        }
	        if (alfa >= beta) {
	            break;
	        }
	    }
	    return bestState;
	    
	   
		
	}
	
	public void test() {
		
		while (!b.isLeaf()){
			System.out.println(b.toString());
			System.out.println("Legal moves for agent with turn:"+b.legalMoves());
			b.execute(b.legalMoves().get((int)(Math.random()*b.legalMoves().size())));
		}
	}
	
}
