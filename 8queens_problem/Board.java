public class Board {

	public static final int WIDTH = 8;
	public static final int HEIGHT = 8;
	public static final String FILL = " . ";
	public static final String QUEEN = " Q ";
	
	private String[][] tiles;
	
	public Board(String fill) {
	
		tiles = new String[WIDTH][HEIGHT];
		for (int row = 0; row < HEIGHT; ++row)
			for (int col = 0; col < WIDTH; ++col)
				tiles[row][col] = fill;
	}
	
	public static void main(String[] args) {
		
		Board board = new Board(Board.FILL);
		board.place(1, 1);
		board.print();
	    System.out.println();
		Board turn = board.mirrorX();
		turn.print();
	}
	
	public int getCols()
	{
		return WIDTH;
	}
	
	public int getRows()
	{
		return HEIGHT;
	}
	
	public String get(int row, int col)
	{
		return tiles[row][col];
	}
	
	public void set(int row, int col, String tile) {
		
		tiles[row][col] = tile;
	}
	
	public void place(int row, int col) {

		tiles[row][col] = QUEEN;
	}
	
	public boolean canSee(int row, int col)
	{
		// go north
		for (int i = row; i >= 0; --i)
			if (tiles[i][col].equals(QUEEN))
				return true;
		
		// go south
		for (int i = row; i < 8; ++i)
			if (tiles[i][col].equals(QUEEN))
				return true;
		
		// go east
		for (int i = col; i < 8; ++i)
			if (tiles[row][i].equals(QUEEN))
				return true;

		// go west
		for (int i = col; i >= 0; --i)
			if (tiles[row][i].equals(QUEEN))
				return true;
			
        // go south east
		int r=row;
		int c=col;
		while (r>=0 && r<8 && c>=0 && c<8)
			if (tiles[r++][c++].equals(QUEEN))
				return true;			

		// go south west
		r=row;
		c=col;
		while (r>=0 && r<8 && c>=0 && c<8)
			if (tiles[r++][c--].equals(QUEEN))
				return true;			

		// go north west
		r=row;
		c=col;
		while (r>=0 && r<8 && c>=0 && c<8)
			if (tiles[r--][c--].equals(QUEEN))
				return true;	

		// go north east
		r=row;
		c=col;
		while (r>=0 && r<8 && c>=0 && c<8)
			if (tiles[r--][c++].equals(QUEEN))
				return true;	

		return false;
	}
	
	public Board turn() {

		Board board = new Board(FILL);
		/*
		for (int row = 0; row < HEIGHT; ++row)
			for (int col = 0; col < WIDTH; ++col)
				board.set(row, col, tiles[row][col]);
		*/
			
		for (int row = 0; row < HEIGHT; ++row)
			for (int col = 0; col < WIDTH; ++col)
				board.set(row, col, tiles[HEIGHT - col - 1][row]);
		
		return board;
	}
    
	public Board mirrorX() {
		
		Board board = new Board(FILL);

		for (int row = 0; row < HEIGHT; ++row)
			for (int col = 0; col < tiles[row].length; ++col)
				if (col < tiles[row].length / 2)
					board.set(row, tiles[row].length - 1 - col, tiles[row][col]);

		return board;
	}
	
	public Board mirrorY() {
		
		Board board = new Board(FILL);

		for (int col = 0; col < WIDTH; ++col)
			for (int row = 0; row < HEIGHT; ++row)
				if (row < HEIGHT / 2)
					board.set(HEIGHT - 1 - row, col, tiles[row][col]);

		return board;
	}
	
	public void print() {
		for (int row = 0; row < HEIGHT; ++row)
		{
			for (int col = 0; col < WIDTH; ++col)
				System.out.print(tiles[row][col]);
			System.out.println();
		}
	}
	
	/* This algorithm only looks for exact board matches
	@Override
	public boolean equals(Object object) {
		
		if (object instanceof Board) {
			
			Board board = (Board)object;
			
			for (int row = 0; row < HEIGHT; ++row)
				for (int col = 0; col < WIDTH; ++col)
					if (!tiles[row][col].equals(board.get(row, col)))
						return false;

			return true;
		}
		
		return false;
	}
	*/
	
	// this algorithm looks for symmetric board matches
	@Override
	public boolean equals(Object object) {
		
		if (object instanceof Board) {
			
			Board board = (Board)object;
			
			for (int i = 0; i < 4; ++i) {
				
				boolean success = true;
				
				for (int row = 0; row < HEIGHT; ++row)
					for (int col = 0; col < WIDTH; ++col)
						if (!tiles[row][col].equals(board.get(row, col)))
							success = false;
						
				if (success)
					return true;
		
				Board mirror = board.mirrorX();

				for (int j = 0; j < 2; ++j) {

					boolean success2 = true;
					for (int row = 0; row < HEIGHT; ++row)
						for (int col = 0; col < WIDTH; ++col)
							if (!tiles[row][col].equals(mirror.get(row, col)))
								success2 = false;
							
					if (success2) {
						System.out.println("MIRROR FOUND -- this never gets triggered because I think that we need diagonal mirrors instead");
						return true;
					}
							
					mirror = board.mirrorY();
				}

				board = board.turn();
			}
						
			return false;
		}
		
		return false;
	}
}