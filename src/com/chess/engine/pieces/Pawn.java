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

    // Fixed set of destination coordinates for our Pawn. A Pawn is not a sliding piece,
    // so these coordinates offsets can always be applied with no further iterations to check paths.
    private final static int[] POTENTIAL_MOVE_COORDINATES = {8, 16, 7, 9};

    public Pawn(final Color pieceColor, final int piecePosition) {
        super(PieceType.PAWN, piecePosition, pieceColor);
    }

    @Override
    public Collection<Move> calcLegalMove(final Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        // Like always, loop through our potential coords and add it to our current position.
        // The difference for a Pawn is the directionality for a particular color.
        // If White, check positive coords.
        // If Black, check the negative coords
        for(final int currentCoordinateOffset: POTENTIAL_MOVE_COORDINATES){
            final int potentialDestinationCoordinate = this.piecePosition + (this.getPieceColor().getDirection()*currentCoordinateOffset);

            // Check the range
            if(!BoardUtils.isValidSquareCoordinate(potentialDestinationCoordinate)){
                continue;
            }

            // Here we have a different exclusion design pattern. We should change this later to reflect the other pieces.
            if(potentialDestinationCoordinate == 8 && !board.getSquare(potentialDestinationCoordinate).isSquareOccupied()){
                //TODO: more work to do here
                legalMoves.add(new NormalMove(board, this, potentialDestinationCoordinate));
            } else if(currentCoordinateOffset == 16 && this.isFirstMove()
                                                    && (BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceColor().isBlack())
                                                    || (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceColor().isWhite())){
                final int behindPotentialDestinationCoordinate = this.piecePosition + (this.getPieceColor().getDirection()*8);
                if(board.getSquare(behindPotentialDestinationCoordinate) != null &&
                   !board.getSquare(potentialDestinationCoordinate).isSquareOccupied()){
                    legalMoves.add(new NormalMove(board, this, potentialDestinationCoordinate));
                }
            } else if(currentCoordinateOffset == 7 &&
                    !((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceColor.isWhite() ||
                      (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceColor.isBlack())))){
                if(board.getSquare(potentialDestinationCoordinate) != null){
                    final Piece pieceAtDestination = board.getSquare(potentialDestinationCoordinate).getPiece();
                    if(this.pieceColor != pieceAtDestination.getPieceColor()){
                        //TODO more work here!
                        legalMoves.add(new Move.AttackingMove(board, this, potentialDestinationCoordinate, pieceAtDestination));
                    }
                }
            } else if(currentCoordinateOffset == 9 &&
                    !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceColor.isWhite()) ||
                      (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceColor.isBlack()))){
                if(board.getSquare(potentialDestinationCoordinate) != null){
                    final Piece pieceAtDestination = board.getSquare(potentialDestinationCoordinate).getPiece();
                    if(this.pieceColor != pieceAtDestination.getPieceColor()){
                        //TODO more work here!
                        legalMoves.add(new Move.AttackingMove(board, this, potentialDestinationCoordinate, pieceAtDestination));
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    @Override
    public Piece movePiece(final Move move) {
        return new Pawn(move.getMovedPiece().getPieceColor(), move.getDestinationCoordinate());
    }


    @Override
    public String toString(){
        return PieceType.PAWN.toString();
    }

    /* TODO We could also use the below design strategy that follows the rules for our other piece exclusions.
    private static boolean isFirstColumnWithExclusions(final int currentPosition, final int coordinateOffset, final Color color){
        return(BoardUtils.FIRST_COLUMN[currentPosition] && coordinateOffset == 7 && color.isBlack() ||
               BoardUtils.FIRST_COLUMN[currentPosition] && coordinateOffset == 9 && color.isWhite());
    }

    private static boolean isEighthColumnWithExclusions(final int currentPosition, final int coordinateOffset, final Color color){
        return(BoardUtils.EIGHTH_COLUMN[currentPosition] && coordinateOffset == 7 && color.isWhite() ||
               BoardUtils.EIGHTH_COLUMN[currentPosition] && coordinateOffset == 9 && color.isBlack());
    }
    */
}
