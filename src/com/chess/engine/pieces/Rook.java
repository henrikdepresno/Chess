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

public class Rook extends Piece{

    // We define the potential moves as constants in an array. If our board has 0-63 squares,
    // ordered from left -> right, top -> bottom, we can easily define the legal coordinates of our Rook.
    // The Rook is however a sliding piece, so all the coordinates on the list are potentially valid for each row/column.
    private final static int[] POTENTIAL_MOVE_VECTOR_COORDINATES = {-1, -8, 1, 8};

    public Rook(final Color pieceColor, final int piecePosition){
        super(PieceType.ROOK, piecePosition, pieceColor);
    }

    @Override
    public Collection<Move> calcLegalMove(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        // The logic here is that we don't have a constant number of moves, since it's a sliding piece.
        // We need to loop through all potential moves this time and apply the offset to each position
        // and check its validity. The Queen and Bishop will have very similar implementation since they are sliding pieces.
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

    @Override
    public Piece movePiece(final Move move) {
        return new Rook(move.getMovedPiece().getPieceColor(), move.getDestinationCoordinate());
    }

    // toString for early testing
    @Override
    public String toString(){
        return PieceType.ROOK.toString();
    }

    // Our exclusions/edge cases. A rook can't go to the left if it's on the leftmost column.
    // It would mean it would jump to the rightmost column on the above row. Not a valid move in chess
    private static boolean isFirstColumnWithExclusions(final int currentPosition, final int coordinateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (coordinateOffset == -1);
    }

    // Similarly, a rook cannot go to the right if we are on the rightmost column.
    // It would mean it would jump to the leftmost column on the below row. Not a valid move in chess
    private static boolean isEighthColumnWithExclusions(final int currentPosition, final int coordinateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (coordinateOffset == 1);
    }
}
