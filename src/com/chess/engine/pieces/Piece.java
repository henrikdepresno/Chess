package com.chess.engine.pieces;

import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;

public abstract class Piece {
    //coordinate and color on the board
    protected final int piecePosition;
    protected final Color pieceColor;

    Piece(final int piecePosition, final Color pieceColor){
        this.piecePosition = piecePosition;
        this.pieceColor = pieceColor;
    }

    public Color getPieceColor(){
        return this.pieceColor;
    }

    //Passes in the board object and extending classes will implement calculate legal moves for a given Piece,
    // ie. Knight, Queen, Pawn.
    public abstract Collection<Move> calcLegalMove(final Board board);
}
