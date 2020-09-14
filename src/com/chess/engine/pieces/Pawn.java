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

    private final static int[] POTENTIAL_MOVE_COORDINATES = {8, 16, 7, 9};

    Pawn(final int piecePosition, final Color pieceColor) {
        super(piecePosition, pieceColor);
    }

    @Override
    public Collection<Move> calcLegalMove(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCoordinateOffset: POTENTIAL_MOVE_COORDINATES){
            final int potentialDestinationCoordinate = this.piecePosition + (this.getPieceColor().getDirection()*currentCoordinateOffset);
            if(isFirstColumnWithExclusions(potentialDestinationCoordinate, currentCoordinateOffset, this.pieceColor) ||
                    isEighthColumnWithExclusions(potentialDestinationCoordinate, currentCoordinateOffset, this.pieceColor)){
                break;
            }
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
            } else if(currentCoordinateOffset == 7){

            } else if(currentCoordinateOffset == 9){

            }
        }

        return Collections.unmodifiableList(legalMoves);
    }

    private static boolean isFirstColumnWithExclusions(final int currentPosition, final int coordinateOffset, final Color color){
        return(BoardUtils.FIRST_COLUMN[currentPosition] && coordinateOffset == 7 && color.isBlack() ||
               BoardUtils.FIRST_COLUMN[currentPosition] && coordinateOffset == 9 && color.isWhite());
    }

    private static boolean isEighthColumnWithExclusions(final int currentPosition, final int coordinateOffset, final Color color){
        return(BoardUtils.EIGHTH_COLUMN[currentPosition] && coordinateOffset == 7 && color.isWhite() ||
               BoardUtils.EIGHTH_COLUMN[currentPosition] && coordinateOffset == 9 && color.isBlack());
    }
}
