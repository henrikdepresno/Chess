package com.chess.engine.pieces;

import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Square;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece{

    //Fixed set of destination coordinates for our Knight
    private final static int[] POTENTIAL_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};

    Knight(final int piecePosition, final Color pieceColor){
        super(piecePosition, pieceColor);
    }

    @Override
    public List<Move> calcLegalMove(Board board) {

        int potentialDestinationCoordinate;
        final List<Move> legalMoves = new ArrayList<>();

        /*
        We need to loop through our POTENTIAL_MOVE_COORDINATES to figure out if:
        A: is it on the board? not out of bounds
        B: is the square empty? how do we move there?
        C: is the square occupied by enemy? How do we knock out the enemy piece?
        */
        for(final int currentCoordinate: POTENTIAL_MOVE_COORDINATES){
            //apply offset to current coordinate
            potentialDestinationCoordinate = this.piecePosition + currentCoordinate;
            if(BoardUtils.isValidSquareCoordinate(potentialDestinationCoordinate) /*if the square is not outside the range*/){
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
