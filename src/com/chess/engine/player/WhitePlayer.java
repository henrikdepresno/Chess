package com.chess.engine.player;

import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.KingSideCastleMove;
import com.chess.engine.board.Move.QueenSideCastleMove;
import com.chess.engine.board.Square;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

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
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals, final Collection<Move> opponentLegals) {
        final List<Move> kingCastles = new ArrayList<>();
        if(this.playerKing.isFirstMove() && !this.isInCheck()){
            // White king side castle
            if(!this.board.getSquare(61).isSquareOccupied() &&
               !this.board.getSquare(62).isSquareOccupied()){
                final Square rookSquare = this.board.getSquare(63);
                if(rookSquare.isSquareOccupied() && rookSquare.getPiece().isFirstMove()){
                    if(Player.calculateAttacksOnTile(61, opponentLegals).isEmpty() &&
                       Player.calculateAttacksOnTile(62, opponentLegals).isEmpty() &&
                       rookSquare.getPiece().getPieceType().isRook()){
                    }
                    kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 62, (Rook)rookSquare.getPiece(), rookSquare.getSquareCoordinate(), 61));
                }
            }
            //White queen side castle
            if(!this.board.getSquare(59).isSquareOccupied() &&
               !this.board.getSquare(58).isSquareOccupied() &&
               !this.board.getSquare(57).isSquareOccupied()){
                final Square rookSquare = this.board.getSquare(56);
                if(rookSquare.isSquareOccupied() && rookSquare.getPiece().isFirstMove() &&
                   Player.calculateAttacksOnTile(58, opponentLegals).isEmpty() &&
                   Player.calculateAttacksOnTile(59, opponentLegals).isEmpty() &&
                   rookSquare.getPiece().getPieceType().isRook()){
                    kingCastles.add(new QueenSideCastleMove(this.board, this.playerKing, 58, (Rook)rookSquare.getPiece(), rookSquare.getSquareCoordinate(), 59));
                }
            }
        }
        return Collections.unmodifiableList(kingCastles);
    }
}