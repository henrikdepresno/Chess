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

    // The Bishop is a sliding piece, so all the coordinates in the list are potentially valid for each row/column.
    // Which means we need to check all possible paths.
    private final static int[] POTENTIAL_MOVE_VECTOR_COORDINATES = {-9, -7, 7, 9};

    public Bishop(Color pieceColor, int piecePosition) {
        super(piecePosition, pieceColor);
    }

    @Override
    public Collection<Move> calcLegalMove(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        // The logic here is that we don't have a constant number of moves, since it's a sliding piece.
        // We need to loop through all potential moves this time and apply the offset to each position
        // and check its validity. The Queen and Rook will have very similar implementation since they are sliding pieces.
        for(final int currentCoordinateOffset: POTENTIAL_MOVE_VECTOR_COORDINATES){
            int potentialDestinationCoordinate = this.piecePosition;

            // While the current position we are on is valid, check for exclusions and apply the offset.
            while(BoardUtils.isValidSquareCoordinate(potentialDestinationCoordinate)){

                // Check for our edge cases, if true, break out of the while, and go to next potential coordinate.
                if(isFirstColumnWithExclusions(potentialDestinationCoordinate, currentCoordinateOffset) ||
                        isEighthColumnWithExclusions(potentialDestinationCoordinate, currentCoordinateOffset)){
                    break;
                }
                potentialDestinationCoordinate += currentCoordinateOffset;

                // Check again if the new coordinate is valid, if valid, get the square on the coordinate.
                // Then check if the square is occupied or not, and add that Move to our list of legal Moves
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

    // toString for early testing
    @Override
    public String toString(){
        return PieceType.BISHOP.toString();
    }

    // The exclusions or edge cases for a Bishop on the first or leftmost column.
    private static boolean isFirstColumnWithExclusions(final int currentPosition, final int coordinateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (coordinateOffset == -9 || coordinateOffset == 7);
    }

    // The exclusions or edge cases for a Bishop on the last or rightmost column.
    private static boolean isEighthColumnWithExclusions(final int currentPosition, final int coordinateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (coordinateOffset == -7 || coordinateOffset == 9);
    }
}
