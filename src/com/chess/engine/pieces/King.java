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

    public King(Color pieceColor, int piecePosition) {
        super(piecePosition, pieceColor);
    }

    @Override
    public Collection<Move> calcLegalMove(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCoordinateOffset: POTENTIAL_MOVE_COORDINATES){
            final int potentialDestinationCoordinate = this.piecePosition + currentCoordinateOffset;

            if(isFirstColumnWithExclusions(this.piecePosition, currentCoordinateOffset) ||
               isEighthColumnWithExclusions(this.piecePosition, currentCoordinateOffset)){
                continue;
            }
            
            // Check that we don't go outside our board
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
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    public String toString(){
        return PieceType.KING.toString();
    }

    private static boolean isFirstColumnWithExclusions(final int currentPosition, final int coordinateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (coordinateOffset == -1 || coordinateOffset == -9 ||
                coordinateOffset == 7);
    }

    private static boolean isEighthColumnWithExclusions(final int currentPosition, final int coordinateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (coordinateOffset == -7 || coordinateOffset == 1 ||
                coordinateOffset == 9);
    }
}
