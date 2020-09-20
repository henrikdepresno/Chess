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

public class Bishop extends Piece{

    private final static int[] POTENTIAL_MOVE_VECTOR_COORDINATES = {-9, -7, 7, 9};

    public Bishop(Color pieceColor, int piecePosition) {
        super(piecePosition, pieceColor);
    }

    @Override
    public Collection<Move> calcLegalMove(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        //Similar logic here as we had with our Knight, except we don't have a constant number of moves.
        //We need to loop through all potential moves this time and apply the offset to each position
        //and check its validity.
        for(final int currentCoordinateOffset: POTENTIAL_MOVE_VECTOR_COORDINATES){
            int potentialDestinationCoordinate = this.piecePosition;
            while(BoardUtils.isValidSquareCoordinate(potentialDestinationCoordinate)){
                if(isFirstColumnWithExclusions(potentialDestinationCoordinate, currentCoordinateOffset) ||
                        isEighthColumnWithExclusions(potentialDestinationCoordinate, currentCoordinateOffset)){
                    break;
                }
                potentialDestinationCoordinate += currentCoordinateOffset;
                if(BoardUtils.isValidSquareCoordinate(potentialDestinationCoordinate)){
                    final Square potentialDestinationSquare = board.getSquare(potentialDestinationCoordinate);
                    if(potentialDestinationSquare == null){
                        legalMoves.add(new NormalMove(board, this, potentialDestinationCoordinate));
                    } else{
                        final Piece pieceAtDestination = potentialDestinationSquare.getPiece();
                        final Color pieceColor = pieceAtDestination.getPieceColor();
                        if(this.pieceColor != pieceColor){
                            legalMoves.add(new AttackingMove(board, this, potentialDestinationCoordinate, pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    @Override
    public String toString(){
        return PieceType.BISHOP.toString();
    }

    private static boolean isFirstColumnWithExclusions(final int currentPosition, final int coordinateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (coordinateOffset == -9 || coordinateOffset == 7);
    }

    private static boolean isEighthColumnWithExclusions(final int currentPosition, final int coordinateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (coordinateOffset == -7 || coordinateOffset == 9);
    }
}
