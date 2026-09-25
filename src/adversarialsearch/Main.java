package adversarialsearch;
import java.util.Vector;

public class Main {
	public static void main(String[] args) {
		System.out.println("Hello World");
		Game g=new Game();
		//g.test();
		//System.out.println(g.b.toString());
		//g.b.turn = 1;
		///g.b.execute("block");
		//g.b.turn = 0;
		//g.b.execute("block");
		//System.out.println(g.b.value(1));
		//g.b.turn = 1;
		//g.b.execute("block");
		//g.b.execute("right");
		//System.out.println(g.b.isLeaf());
		//Vector<String> movesAgent0 = g.b.legalMoves(1);
		//for (String move : movesAgent0) {
		    //System.out.println(move);
		//}
		
		//State copyState = g.b.copy();
		//copyState.execute("down");
		//System.out.println(copyState.toString());
		//System.out.println(g.b.value(0));
		g.b.turn = 1;
		for (int depth = 7; depth <= 30; depth++) {
			State initialState = g.b.copy();
			initialState.turn = 1;
	        int firstToMove = initialState.turn;
	        State bestState = g.minimax(initialState, firstToMove, depth, 0);

	        System.out.println("best state when depth " + depth);
	        System.out.println(bestState.value(firstToMove));
	        System.out.println(bestState.toString());
	        System.out.println("Moves path: " + bestState.moves);
			
		}
		
		
		
		
		
	}
}
