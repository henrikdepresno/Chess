package com.chess.engine.pieces;

import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;

public abstract class Piece {

    // All pieces require the current position on the board, the color and if it's made it's first move or not.
    protected final PieceType pieceType;
    protected final int piecePosition;
    protected final Color pieceColor;
    protected final boolean isFirstMove;
    private final int cachedHashCode;

    Piece(final PieceType pieceType,
          final int piecePosition,
          final Color pieceColor){
        this.pieceType = pieceType;
        this.piecePosition = piecePosition;
        this.pieceColor = pieceColor;
        this.isFirstMove = false;
        this.cachedHashCode = computeHashCode();
    }

    private int computeHashCode(){
        int result = pieceType.hashCode();
        result = 31 * result + pieceColor.hashCode();
        result = 31 * result + piecePosition;
        result = 31 * result + (isFirstMove ? 1 : 0);
        return result;
    }

    @Override
    public boolean equals(final Object other){
        if(this == other) return true;
        if(!(other instanceof Piece)) return false;

        final Piece otherPiece = (Piece) other;
        return pieceType == otherPiece.getPieceType() && piecePosition == otherPiece.getPiecePosition() &&
               pieceColor == otherPiece.getPieceColor() && isFirstMove == otherPiece.isFirstMove();
    }

    @Override
    public int hashCode(){
        return this.cachedHashCode;
    }

    // Getters
    public Color getPieceColor(){
        return this.pieceColor;
    }
    public int getPiecePosition(){ return this.piecePosition; }
    public boolean isFirstMove(){
        return this.isFirstMove;
    }
    public PieceType getPieceType(){
        return this.pieceType;
    }

    // Passes in a board object that will be used to calculate legal moves for a given Piece,
    // ie. Knight, Queen, Pawn.
    public abstract Collection<Move> calcLegalMove(final Board board);

    // Our Piece is immutable, so we have to return a new version of it to place on the new board.
    public abstract Piece movePiece(Move move);

    // Nested enum class used for our toString testing print method.
    public enum PieceType{
        ROOK("R") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        KNIGHT("N") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        BISHOP("B") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        QUEEN("Q") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        KING("K") {
            @Override
            public boolean isKing() {
                return true;
            }
        },
        PAWN("P") {
            @Override
            public boolean isKing() {
                return false;
            }
        };

        private String pieceName;

        PieceType(final String pieceName){
            this.pieceName = pieceName;
        }

        // To prevent us from casting.
        public abstract boolean isKing();

        @Override
        public String toString() {
            return this.pieceName;
        }
    }
}
