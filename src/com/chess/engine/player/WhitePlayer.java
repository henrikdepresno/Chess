package com.chess.engine.player;

import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;

import java.util.Collection;

public class WhitePlayer extends Player{
    public WhitePlayer(final Board board,
                       final Collection<Move> whiteLegalMoves,
                       final Collection<Move> blackLegalMoves) {
        super(board, whiteLegalMoves, blackLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public Color getColor(){
        return Color.WHITE;
    }

    @Override
    public Player getOpponent(){
        return this.board.blackPlayer();
    }
}
