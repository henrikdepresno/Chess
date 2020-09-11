package com.chess.engine.pieces;

import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.NormalMove;
import com.chess.engine.board.Square;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Pawn extends Piece{

    private final static int[] POTENTIAL_MOVE_COORDINATES = {8};

    Pawn(int piecePosition, Color pieceColor) {
        super(piecePosition, pieceColor);
    }

    @Override
    public Collection<Move> calcLegalMove(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCoordinateOffset: POTENTIAL_MOVE_COORDINATES){
            int potentialDestinationCoordinate = this.piecePosition + (this.getPieceColor().getDirection()*currentCoordinateOffset);

            if(!BoardUtils.isValidSquareCoordinate(potentialDestinationCoordinate)){
                continue;
            }

            if(potentialDestinationCoordinate == 8 && !board.getSquare(potentialDestinationCoordinate).isSquareOccupied()){
                //TODO: more work to do here
                legalMoves.add(new NormalMove(board, this, potentialDestinationCoordinate));
            }
        }

        return Collections.unmodifiableList(legalMoves);
    }
}
