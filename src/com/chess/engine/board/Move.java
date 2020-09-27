package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

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

    // We will create a new board. Remember, the board class is immutable so we won't mutate the existing board.
    public abstract Board execute();

    // If the move we wish to make is to an unoccupied square
    public static final class NormalMove extends Move{
        public NormalMove(final Board board,
                   final Piece movedPiece,
                   final int destinationCoordinate){
            super(board, movedPiece, destinationCoordinate);
        }

        @Override
        public Board execute() {
            return null;
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
