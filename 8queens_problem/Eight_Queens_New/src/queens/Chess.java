package queens;

import java.util.*;

public class Chess {

	public static final int BOARD_TRIES = 20000;
	public static final int RANDOM_TRIES = 500;

	private ArrayList<Board> boards;
	private Board board;
	
	public Chess() {
		
		boards = new ArrayList<>();
	}
	
	public static void main(String[] args) {

		Chess chess = new Chess();

		for (int i = 0; i < BOARD_TRIES; ++i) {
			Board board = new Board(Board.FILL);
			chess.set(board);			
			
			if (chess.algo())
				chess.add(board);
		}
		
		chess.printBoards();
		chess.printTotal();
		System.out.println("The algorithm finds rotational symmetry, but NOT mirror symmetry -- thus 24 solutions");
	}
	
	public void set(Board board)
	{
		this.board = board;
	}
	
	public boolean add(Board board)
	{
		if (boards.contains(board))
			return false;
		
		boards.add(board);
		return true;
	}
	
	public void printBoards()
	{
		for (Board board : boards) {
			board.print();
			System.out.println();
		}
	
	}
	
	public void printTotal() {
		System.out.println("Total: " + boards.size());
	}	
	
	/* this is the implementation of the pseudo-code that we wrote */
	public boolean algo() {

		boolean success = true;
	
		Random random = new Random();
		
		for (int col = 0; col < Board.WIDTH; ++col){
			int count = 0;
			while (count++ < RANDOM_TRIES) {
				int row = random.nextInt(Board.HEIGHT);
				//System.out.println("[" + count + "] " + row);
				if (!board.canSee(row, col)) {
					board.place(row, col);
					break;
				}
			}
			
			if (count >= RANDOM_TRIES)
				success = false;
			
			//print();
			//System.out.println();
		}
		
		return success;
	}
	
	private void place(int row, int col) {
		board.place(row, col);
	}
	
	private void print() {
		board.print();
	}
}


// tiles[row][col]
// the top left array position
// tiles[0][0]
// tiles[6][2]