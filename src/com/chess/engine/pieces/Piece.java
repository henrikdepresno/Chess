package com.chess.engine.pieces;

import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;

public abstract class Piece {

    // All pieces require the current position on the board, the color and if it's made it's first move or not.
    protected final int piecePosition;
    protected final Color pieceColor;
    protected final boolean isFirstMove;

    Piece(final int piecePosition, final Color pieceColor){
        this.piecePosition = piecePosition;
        this.pieceColor = pieceColor;
        this.isFirstMove = false;
    }

    // Getters
    public Color getPieceColor(){
        return this.pieceColor;
    }

    public int getPiecePosition(){ return this.piecePosition; }

    public boolean isFirstMove(){
        return this.isFirstMove;
    }

    // Passes in a board object that will be used to calculate legal moves for a given Piece,
    // ie. Knight, Queen, Pawn.
    public abstract Collection<Move> calcLegalMove(final Board board);

    // Nested enum class used for our toString testing print method.
    public enum PieceType{
        ROOK("R"),
        KNIGHT("N"),
        BISHOP("B"),
        QUEEN("Q"),
        KING("K"),
        PAWN("P");

        private String pieceName;

        PieceType(final String pieceName){
            this.pieceName = pieceName;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }
    }
}
