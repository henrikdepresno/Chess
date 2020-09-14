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

    private final static int[] POTENTIAL_MOVE_COORDINATES = {8, 16};

    Pawn(final int piecePosition, final Color pieceColor) {
        super(piecePosition, pieceColor);
    }

    @Override
    public Collection<Move> calcLegalMove(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCoordinateOffset: POTENTIAL_MOVE_COORDINATES){
            final int potentialDestinationCoordinate = this.piecePosition + (this.getPieceColor().getDirection()*currentCoordinateOffset);

            if(!BoardUtils.isValidSquareCoordinate(potentialDestinationCoordinate)){
                continue;
            }

            if(potentialDestinationCoordinate == 8 && !board.getSquare(potentialDestinationCoordinate).isSquareOccupied()){
                //TODO: more work to do here
                legalMoves.add(new NormalMove(board, this, potentialDestinationCoordinate));
            } else if(currentCoordinateOffset == 16 && this.isFirstMove()
                                                    && (BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceColor().isBlack())
                                                    || (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceColor().isWhite())){
                final int behindPotentialDestinationCoordinate = this.piecePosition + (this.getPieceColor().getDirection()*8);
                if(!board.getSquare(behindPotentialDestinationCoordinate).isSquareOccupied() &&
                   !board.getSquare(potentialDestinationCoordinate).isSquareOccupied()){
                    legalMoves.add(new NormalMove(board, this, potentialDestinationCoordinate));
                }
            }
        }

        return Collections.unmodifiableList(legalMoves);
    }
}
