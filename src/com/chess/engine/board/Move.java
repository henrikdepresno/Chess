package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

import static com.chess.engine.board.Board.*;

public abstract class Move {

    // To make a move, we need the board layout, the piece we want to move, and the destination.
    final Board board;
    final Piece movedPiece;
    final int destinationCoordinate;
    public static final Move NULL_MOVE = new NullMove();

    private Move(final Board board,
                 final Piece movedPiece,
                 final int destinationCoordinate){
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate; 
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + this.destinationCoordinate;
        result = prime * result + this.movedPiece.hashCode();
        return result;
    }

    @Override
    public boolean equals(final Object other){
        if(this == other) return true;
        if(!(other instanceof Move)) return false;

        final Move otherMove = (Move) other;
        return getDestinationCoordinate() == otherMove.getDestinationCoordinate() &&
               getMovedPiece().equals(otherMove.getMovedPiece());
    }
                                              
    public int getCurrentCoordinate(){
        return  this.movedPiece.getPiecePosition();
    }
    public int getDestinationCoordinate(){
        return this.destinationCoordinate;
    }
    public Piece getMovedPiece(){
        return this.movedPiece;
    }

    public boolean isAttack(){
        return false;
    }

    public boolean isCastlingMove(){
        return false;
    }

    public Piece getAttackedPiece(){
        return null;
    }

    // When making a move on a board, we are not going to mutate the board.
    // We will materialise a new board with the previous state + the new move.
    public Board execute() {
        // Build a new board to return using Board builder.
        final Builder builder = new Builder();

        // Set the current players pieces on the board, except the moved piece.
        // TODO: Hashcode and equals for the Piece class. Currently just reference equality check.
        for(final Piece piece: this.board.currentPlayer().getActivePieces()){
            if(!this.movedPiece.equals(piece)){
                builder.setPiece(piece);
            }
        }

        // Set the opponent pieces on the board
        for(final Piece piece: this.board.currentPlayer().getOpponent().getActivePieces()){
            builder.setPiece(piece);
        }

        // Move the moved Piece
        builder.setPiece(this.movedPiece.movePiece(this));
        builder.setMoveMaker(this.board.currentPlayer().getOpponent().getColor());
        return builder.build();
    }

    // If the move we wish to make is to an unoccupied square
    public static final class NormalMove extends Move{
        public NormalMove(final Board board,
                          final Piece movedPiece,
                          final int destinationCoordinate){
            super(board, movedPiece, destinationCoordinate);
        }
     }

    // If the move we want to make is to an occupied square of enemy color.
    public static class AttackingMove extends Move{

        final Piece attackedPiece;

        public AttackingMove(final Board board,
                      final Piece piece,                
                      final int destinationCoordinate,
                      final Piece attackedPiece){
            super(board, piece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public Board execute() {
            return null;
        }
    }

    public static final class PawnMove extends Move{
        public PawnMove(final Board board,
                        final Piece movedPiece,
                        final int destinationCoordinate){
            super(board, movedPiece, destinationCoordinate);
        }
    }

    public static class PawnAttackMove extends AttackingMove{
        public PawnAttackMove(final Board board,
                              final Piece movedPiece,
                              final int destinationCoordinate,
                              final Piece attackedPiece){
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }
    }

    public static final class PawnEnPassantAttackMove extends PawnAttackMove{
        public PawnEnPassantAttackMove(final Board board,
                                       final Piece movedPiece,
                                       final int destinationCoordinate,
                                       final Piece attackedPiece){
            super(board, movedPiece, destinationCoordinate, attackedPiece);
        }
    }

    public static final class PawnJump extends Move{
        public PawnJump(final Board board,
                        final Piece movedPiece,
                        final int destinationCoordinate){
            super(board, movedPiece, destinationCoordinate);
        }
    }

    static abstract class CastleMove extends Move{
        public CastleMove(final Board board,
                          final Piece movedPiece,
                          final int destinationCoordinate){
            super(board, movedPiece, destinationCoordinate);
        }
    }

    public static final class KingSideCastleMove extends CastleMove{
        public KingSideCastleMove(final Board board,
                                  final Piece movedPiece,
                                  final int destinationCoordinate){
            super(board, movedPiece, destinationCoordinate);
        }
    }

    public static final class QueenSideCastleMove extends CastleMove{
        public QueenSideCastleMove(final Board board,
                                   final Piece movedPiece,
                                   final int destinationCoordinate){
            super(board, movedPiece, destinationCoordinate);
        }
    }

    public static final class NullMove extends Move{
        public NullMove(){
            super(null, null, -1);
        }

        @Override
        public Board execute(){
            throw new RuntimeException("Cannot execute the null move!");
        }
    }

    public static class MoveFactory{
        private MoveFactory(){
            throw new RuntimeException("Not instantiable!");
        }

        public static Move createMove(final Board board,
                                      final int currentCoordinate,
                                      final int destinationCoordinate){
            for(final Move move: board.getAllLegalMoves()){
                if(move.getCurrentCoordinate() == currentCoordinate &&
                   move.getDestinationCoordinate() == destinationCoordinate){
                    return move;
                }
            }
            return NULL_MOVE;
        }
    }
}
