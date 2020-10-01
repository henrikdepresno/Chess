package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

import static com.chess.engine.board.Board.*;

public abstract class Move {

    // To make a move, we need the board layout, the piece we want to move, and the destination.
    final Board board;
    final Piece movedPiece;
    final int destinationCoordinate;

    private Move(final Board board,
                 final Piece movedPiece,
                 final int destinationCoordinate){
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate; 
    }

    public int getDestinationCoordinate(){
        return this.destinationCoordinate;
    }
    public Piece getMovedPiece(){
        return this.movedPiece;
    }

    // We will create a new board. Remember, the board class is immutable so we won't mutate the existing board.
    public abstract Board execute();

    // If the move we wish to make is to an unoccupied square
    public static final class NormalMove extends Move{
        public NormalMove(final Board board,
                          final Piece movedPiece,
                          final int destinationCoordinate){
            super(board, movedPiece, destinationCoordinate);
        }

        // When making a move on a board, we are not going to mutate the board.
        // We will materialise a new board with the previous state + the new move.
        @Override
        public Board execute() {
            // Build a new board to return using Board builder.
            final Builder builder = new Builder();

            // Set the current players pieces on the board, except the moved piece.
            // TODO: Hashcode and equals for the Piece class. Currently just reference equality check.
            for(final Piece piece: this.board.currentPlayer().getActivePieces()){
                if(!this.movedPiece.equals(piece)){
                    builder.setPiece(piece);
                }
            }

            // Set the opponent pieces on the board
            for(final Piece piece: this.board.currentPlayer().getOpponent().getActivePieces()){
                builder.setPiece(piece);
            }
            
            // Move the moved Piece
            builder.setPiece(this.movedPiece.movePiece(this));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getColor());
            return builder.build();
        }

    }

    // If the move we want to make is to an occupied square of enemy color.
    public static final class AttackingMove extends Move{

        final Piece attackedPiece;

        public AttackingMove(final Board board,
                      final Piece piece,                
                      final int destinationCoordinate,
                      final Piece attackedPiece){
            super(board, piece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public Board execute() {
            return null;
        }
    }
}
