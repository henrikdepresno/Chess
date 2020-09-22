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

    // Fixed set of destination coordinates for our King. A King is not a sliding piece,
    // so these coordinates offsets can always be applied with no further iterations to check paths.
    private final static int[] POTENTIAL_MOVE_COORDINATES = {-9, -8, -7, -1, 1, 7, 8, 9};

    public King(Color pieceColor, int piecePosition) {
        super(piecePosition, pieceColor);
    }

    @Override
    public Collection<Move> calcLegalMove(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        // We need to loop through all potential moves this time and apply the offset to each position
        // and check its validity. The Knight has a similar implementation.
        for(final int currentCoordinateOffset: POTENTIAL_MOVE_COORDINATES){

            // For each coordinate offset defined above in this class, apply the offset to our current coordinate position.
            final int potentialDestinationCoordinate = this.piecePosition + currentCoordinateOffset;

            // Check if the new coordinate is valid, if valid, check exclusion and get the square on the coordinate.
            // Then check if the square is occupied or not, and add that Move to our list of legal Moves
            if(BoardUtils.isValidSquareCoordinate(potentialDestinationCoordinate)){

                // Check for our edge cases, if true, break out of the for loop, and go to next potential coordinate.
                if(isFirstColumnWithExclusions(this.piecePosition, currentCoordinateOffset) ||
                        isEighthColumnWithExclusions(this.piecePosition, currentCoordinateOffset)){
                    continue;
                }
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

    // toString for testing
    public String toString(){
        return PieceType.KING.toString();
    }

    // Exclusions for the King
    private static boolean isFirstColumnWithExclusions(final int currentPosition, final int coordinateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (coordinateOffset == -1 || coordinateOffset == -9 ||
                coordinateOffset == 7);
    }

    // Exclusions for the King
    private static boolean isEighthColumnWithExclusions(final int currentPosition, final int coordinateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (coordinateOffset == -7 || coordinateOffset == 1 ||
                coordinateOffset == 9);
    }
}
