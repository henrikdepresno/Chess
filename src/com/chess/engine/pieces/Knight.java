package com.chess.engine.pieces;

import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Square;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece{

    private final static int[] POTENTIAL_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};

    Knight(final int piecePosition, final Color pieceColor){
        super(piecePosition, pieceColor);
    }

    @Override
    public List<Move> calcLegalMove(Board board) {

        int potentialDestinationCoordinate;
        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCoordinate: POTENTIAL_MOVE_COORDINATES){
              potentialDestinationCoordinate = this.piecePosition + currentCoordinate;
              if(true /*if the tile is valid, not outside the range*/){
                  final Square potentialDestinationSquare = board.getSquare(potentialDestinationCoordinate);
                  if(!potentialDestinationSquare.isSquareOccupied()){
                      legalMoves.add(new Move());
                  } else{
                      final Piece pieceAtDestination = potentialDestinationSquare.getPiece();
                      final Color pieceColor = pieceAtDestination.getPieceColor();
                      //Enemy piece
                      if(this.pieceColor != pieceColor){
                          legalMoves.add(new Move());
                      }
                  }
              }
        }
        return ImmutableList.copyOf(legalMoves);
    }
}
