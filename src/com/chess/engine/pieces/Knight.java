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

public class Knight extends Piece{

    // Fixed set of destination coordinates for our Knight. A Knight is not a sliding piece,
    // so these coordinates offsets can always be applied with no further iterations to check paths.
    private final static int[] POTENTIAL_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};

    public Knight(final Color pieceColor, final int piecePosition){
        super(piecePosition, pieceColor);
    }

    @Override
    public Collection<Move> calcLegalMove(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        /*
        * We need to loop through our POTENTIAL_MOVE_COORDINATES to figure out if:
        * A: is it on the board? not out of bounds
        * B: is the square empty? how do we move there?
        * C: is the square occupied by enemy? How do we knock out the enemy piece?
        */                                                             
        for(final int currentCoordinateOffset: POTENTIAL_MOVE_COORDINATES){

            // For each coordinate offset defined above in this class, apply the offset to our current coordinate position.
            final int potentialDestinationCoordinate = this.piecePosition + currentCoordinateOffset;

            // Check if the new coordinate is valid, if valid, check exclusion and get the square on the coordinate.
            // Then check if the square is occupied or not, and add that Move to our list of legal Moves
            if(BoardUtils.isValidSquareCoordinate(potentialDestinationCoordinate) /*if the square is not outside the range*/){

                // Check for our edge cases, if true, break out of the for loop, and go to next potential coordinate.
                if(isFirstColumnWithExclusions(this.piecePosition, currentCoordinateOffset) ||
                        isSecondColumnWithExclusions(this.piecePosition, currentCoordinateOffset) ||
                        isSeventhColumnWithExclusions(this.piecePosition, currentCoordinateOffset) ||
                        isEighthColumnWithExclusions(this.piecePosition, currentCoordinateOffset)){
                    continue;
                }

                final Square potentialDestinationSquare = board.getSquare(potentialDestinationCoordinate);
                //If the square is empty, make a move
                if(potentialDestinationSquare == null){
                    legalMoves.add(new NormalMove(board, this, potentialDestinationCoordinate));
                } else{ //If the square is occupied by enemy, make another move
                    final Piece pieceAtDestination = potentialDestinationSquare.getPiece();
                    final Color pieceColor = pieceAtDestination.getPieceColor();
                    //Enemy piece
                    if(this.pieceColor != pieceColor){
                        legalMoves.add(new AttackingMove(board, this, potentialDestinationCoordinate, pieceAtDestination));
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    // toString for testing
    @Override
    public String toString(){
        return PieceType.KNIGHT.toString();
    }

    // If the Knight is on the first column, it will have certain edge cases that need to be addressed.
    // We are checking if: Is it in the 1st,2nd,7th,8th column? AND is the offset one that will make it move unlawfully?
    private static boolean isFirstColumnWithExclusions(final int currentPosition, final int coordinateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (coordinateOffset == -17 || coordinateOffset == -10 ||
                coordinateOffset == 6 || coordinateOffset == 15);
    }

    private static boolean isSecondColumnWithExclusions(final int currentPosition, final int coordinateOffset){
        return BoardUtils.SECOND_COLUMN[currentPosition] && (coordinateOffset == -10 || coordinateOffset == 6);
    }
                                                        
    private static boolean isSeventhColumnWithExclusions(final int currentPosition, final int coordinateOffset){
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && (coordinateOffset == -6 || coordinateOffset == 10);
    }

    private static boolean isEighthColumnWithExclusions(final int currentPosition, final int coordinateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (coordinateOffset == -15 || coordinateOffset == -6 ||
                coordinateOffset == 10 || coordinateOffset == 17);
    }


}
