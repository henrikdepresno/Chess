package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

public abstract class Move {

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

    public static final class NormalMove extends Move{
        public NormalMove(final Board board,
                   final Piece movedPiece,
                   final int destinationCoordinate){
            super(board, movedPiece, destinationCoordinate);
        }

    }

    public static final class AttackingMove extends Move{

        final Piece attackedPiece;

        public AttackingMove(final Board board,
                      final Piece piece,                
                      final int destinationCoordinate,
                      final Piece attackedPiece){
            super(board, piece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }

    }
}
