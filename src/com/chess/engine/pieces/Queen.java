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

public class Queen extends Piece{

    // A queen has the union of potential moves of a rook and a bishop.
    // It is also a sliding piece which means we need to check all possible paths.
    private final static int[] POTENTIAL_MOVE_VECTOR_COORDINATES = {-9, -8, -7, -1, 1, 7, 8, 9};

    public Queen(Color pieceColor, int piecePosition) {
        super(PieceType.QUEEN, piecePosition, pieceColor);
    }

    @Override
    public Collection<Move> calcLegalMove(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        //Similar logic here as we had with our Rook
        // We need to loop through all potential moves this time and apply the offset to each position
        // and check its validity. The Rook and Bishop will have very similar implementation since they are sliding pieces.
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
                        legalMoves.add(new Move.NormalMove(board, this, potentialDestinationCoordinate));
                    } else{
                        final Piece pieceAtDestination = potentialDestinationSquare.getPiece();
                        final Color pieceColor = pieceAtDestination.getPieceColor();
                        if(this.pieceColor != pieceColor){
                            legalMoves.add(new Move.AttackingMove(board, this, potentialDestinationCoordinate, pieceAtDestination));
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
        return new Queen(move.getMovedPiece().getPieceColor(), move.getDestinationCoordinate());
    }

    // toString for early testing
    @Override
    public String toString(){
        return PieceType.QUEEN.toString();
    }

    // The exclusions or edge cases for a Queen on the first or leftmost column.
    private static boolean isFirstColumnWithExclusions(final int currentPosition, final int coordinateOffset){
        return BoardUtils.FIRST_COLUMN[currentPosition] && (coordinateOffset == -1 || coordinateOffset == -9 || coordinateOffset == 7);
    }

    // The exclusions or edge cases for a Queen on the last or rightmost column.
    private static boolean isEighthColumnWithExclusions(final int currentPosition, final int coordinateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (coordinateOffset == 1 || coordinateOffset == -7 || coordinateOffset == 9);
    }
}
