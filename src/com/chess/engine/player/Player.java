package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;

import java.util.Collection;

public abstract class Player {

    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    //protected final Collection<Move> opponentMoves;

    Player(final Board board,
           final Collection<Move> legalMoves,
           final Collection<Move> opponentMoves){
                                  
        this.board = board;
        this.legalMoves = legalMoves;
        //this.opponentMoves = opponentMoves;
        this.playerKing = establishKing(); 
    }

    // To ensure that the player is in a legal "State", we will have to check if the King is
    // established in the active pieces on the current board.
    private King establishKing() {
        for(final Piece piece: getActivePieces()){
            if(piece.getPieceType().isKing()){
                return (King) piece;
            }
        }
        throw new RuntimeException("Not a valid board!");
    }

    // Get the pieces for a white or black player.
    public abstract Collection<Piece> getActivePieces();
}
