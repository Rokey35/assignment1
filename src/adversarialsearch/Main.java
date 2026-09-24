package adversarialsearch;
import java.util.Vector;

public class Main {
	public static void main(String[] args) {
		System.out.println("Hello World");
		Game g=new Game();
		//g.test();
		System.out.println(g.b.toString());
		
		g.b.turn = 1;
		g.b.execute("block");
		g.b.execute("right");
		System.out.println(g.b.toString());
		Vector<String> movesAgent0 = g.b.legalMoves(1);
		for (String move : movesAgent0) {
		    System.out.println(move);
		}
		
		
		
	}
}
