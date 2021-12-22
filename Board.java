public class Board {
    Square[][] board;
    private int dimension;

    /**
     * This constructor isntantiates the board, which is the initial
     * set up of pieces in standard checkers on an 8 x 8 board.
     * 
     * @param dim The dimension of the board, usually 8 but can be changed
     *            for testing
     */
    public Board(int dim) {
        dimension = dim;
        board = new Square[dimension][dimension];
        PlayerSide cur_side = PlayerSide.COMPUTER;

        for(int i = 0; i < dimension; i++) {

            if(i > 2)
                cur_side = PlayerSide.HUMAN;

            for(int j = 0; j < dimension; j++) {

                if((i == 0 || i == 2 || i == 6) && j % 2 != 0) {
                    
                    board[i][j] = new Square(i, j, new Piece(cur_side, i, j));
                    
                } else if ((i == 1 || i == 5 || i == 7) && j % 2 == 0) {
                
                    board[i][j] = new Square(i, j, new Piece(cur_side, i, j));
                    
                } else {
                    board[i][j] = new Square(i, j);
                }


            }
        }

    }
    
    /**
     * This method displays the board in the console.
     */
    public void display() {
        for(int i =0; i < dimension; i++){
           
            for(int j =0; j < dimension; j++){
                System.out.print("----");
            }
            System.out.println("-");
            
            for(int j =0; j < dimension; j++){
                
                
                if(board[i][j].getPiece() != null){
                  System.out.print("| " + board[i][j].getPiece().getSymbol() + " ");
                }else{
                  System.out.print("|   ");   
                }

            }
            System.out.println("|");
        }
        for(int i =0; i < dimension; i++){       
            System.out.print("----");
        }
        System.out.print("-");
    }

}
    
