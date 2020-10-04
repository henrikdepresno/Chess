package com.chess.engine.player;

import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Square;
import com.chess.engine.pieces.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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

    @Override
    protected Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentLegals) {
        final List<Move> kingCastles = new ArrayList<>();
        if(this.playerKing.isFirstMove() && !this.isInCheck()){
            if(!this.board.getSquare(61).isSquareOccupied() &&
               !this.board.getSquare(62).isSquareOccupied()){
                final Square rookSquare = this.board.getSquare(63);
                if(rookSquare.isSquareOccupied() && rookSquare.getPiece().isFirstMove()){
                    //TODO: add a castle move.
                    kingCastles.add(null);
                }
            }
        }
        return Collections.unmodifiableList(kingCastles);
    }
}
