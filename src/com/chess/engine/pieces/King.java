package com.chess.engine.pieces;

import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Square;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.chess.engine.board.Move.*;

public class King extends Piece{

    private final static int[] POTENTIAL_MOVE_COORDINATES = {-9, -8, -7, -1, 1, 7, 8, 9};

    King(int piecePosition, Color pieceColor) {
        super(piecePosition, pieceColor);
    }

    @Override
    public Collection<Move> calcLegalMove(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCoordinateOffset: POTENTIAL_MOVE_COORDINATES){
            final int potentialDestinationCoordinate = this.piecePosition + currentCoordinateOffset;

            // Check that we don't go outside our board
            if(BoardUtils.isValidSquareCoordinate(potentialDestinationCoordinate)){
                final Square potentialDestinationSquare = board.getSquare(potentialDestinationCoordinate);

                if(!potentialDestinationSquare.isSquareOccupied()){
                    legalMoves.add(new NormalMove(board, this, potentialDestinationCoordinate));
                } else{
                    
                }
            }
        }

        return Collections.unmodifiableList(legalMoves);
    }
}
