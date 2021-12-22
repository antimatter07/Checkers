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
    public Board(int dim, Piece[] computerPieces, Piece[] humanPieces) {
        dimension = dim;
        board = new Square[dimension][dimension];
        PlayerSide cur_side = PlayerSide.COMPUTER;
        int hum_index = 0;
        int comp_index = 0;

        for(int i = 0; i < dimension; i++) {

            if(i > 2)
                cur_side = PlayerSide.HUMAN;

            for(int j = 0; j < dimension; j++) {

                if((i == 0 || i == 2 || i == 6) && j % 2 != 0) {

                    if(cur_side == PlayerSide.COMPUTER) {
                        computerPieces[comp_index] = new Piece(cur_side, i, j);
                        board[i][j] = new Square(i, j, computerPieces[comp_index]);
                        comp_index++;
                    } else {
                        humanPieces[hum_index] = new Piece(cur_side, i, j);
                        board[i][j] = new Square(i, j, humanPieces[hum_index]);
                        hum_index++;
                    }
                    
                    
                    
                    
                } else if ((i == 1 || i == 5 || i == 7) && j % 2 == 0) {
                
                    if(cur_side == PlayerSide.COMPUTER) {
                        computerPieces[comp_index] = new Piece(cur_side, i, j);
                        board[i][j] = new Square(i, j, computerPieces[comp_index]);
                        comp_index++;
                    } else {
                        humanPieces[hum_index] = new Piece(cur_side, i, j);
                        board[i][j] = new Square(i, j, humanPieces[hum_index]);
                        hum_index++;
                    }
                    
                } else {
                    board[i][j] = new Square(i, j);
                }


            }
        }

    }

    public void standardMove(Piece cur_piece, Direction moveDirection) {

        board[cur_piece.getRow()][cur_piece.getCol()].removePiece();
        switch(moveDirection) {
            case UPRIGHT:
                cur_piece.setPos(cur_piece.getRow() - 1, cur_piece.getCol() + 1);
                break;
            case UPLEFT:
                cur_piece.setPos(cur_piece.getRow() - 1, cur_piece.getCol() - 1);
                break;
            case DOWNLEFT:
                cur_piece.setPos(cur_piece.getRow() + 1, cur_piece.getCol() - 1);
                break;
            case DOWNRIGHT:
                cur_piece.setPos(cur_piece.getRow() + 1, cur_piece.getCol() + 1);
                break;         

        }

        board[cur_piece.getRow()][cur_piece.getCol()].movePieceIn(cur_piece);

    }
    
    /**
     * This method displays the board in the console.
     */
    public void display() {
        for(int i = 0; i < dimension; i++){
           
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
    
