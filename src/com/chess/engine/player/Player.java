package com.chess.engine.player;

import com.chess.engine.Color;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class Player {

    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    private final boolean isInCheck;

    Player(final Board board,
           final Collection<Move> legalMoves,
           final Collection<Move> opponentMoves){
                                  
        this.board = board;
        this.legalMoves = legalMoves;
        this.playerKing = establishKing();
        this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
    }

    public King getPlayerKing(){
        return this.playerKing;
    }

    public Collection<Move> getLegalMoves(){
        return this.legalMoves;
    }

    // Get all the attacks currently on the King. If there are attacks on the King available, our King is in check.
    private static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> moves) {
        final List<Move> attackMoves = new ArrayList<>();
        for(final Move move: moves){
            if(piecePosition == move.getDestinationCoordinate()){
                attackMoves.add(move);
            }
        }
        return Collections.unmodifiableList(attackMoves);
    }

    // To ensure that the player is in a legal "State", we will have to check if the King is
    // established in the active pieces on the current board.
    private King establishKing() {
        for(final Piece piece: getActivePieces()){
            if(piece.getPieceType().isKing()){
                return (King) piece;
            }
        }
        throw new RuntimeException("Not a valid board!");
    }

    // A method to check if the move is in our list of legalmoves.
    public boolean isMoveLegal(final Move move){
        return this.legalMoves.contains(move);
    }

    // When the players King is checked and needs to move the King to escape.
    public boolean isInCheck(){
        return this.isInCheck;
    }

    // When the players King is already checked, but can't move anywhere that isn't checked either.
    public boolean isInCheckMate(){
        return this.isInCheck && !hasEscapeMoves();
    }

    // Current players King is not checked, but doesn't have any escape moves/no moves to make.
    // The players only moves put the King in check.
    public boolean isInStaleMate(){
        return !this.isInCheck && !hasEscapeMoves();
    }

    public boolean isCastled(){
        return false;
    }

    // If there are any moves to make, return true, when that move is done. Otherwise return false;
    protected boolean hasEscapeMoves(){
        for(final Move move: this.legalMoves){
            final MoveTransition transition = makeMove(move);
            if(transition.getMoveStatus().isDone()){
                return true;
            }
        }
        return false;
    }


    /* When making a legal move, we are constructing the new board layout/structure and need to pass all the values across.
     * If the move is illegal, pass the SAME board back and say its illegal move
     * Create the new board and check: are there any attacks on the players king that will leave it in check?
     * Then we shouldn't allow the player to make the move.
     * Otherwise return the new transition board.
     */
    public MoveTransition makeMove(final Move move){
        if(!isMoveLegal(move)) {
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
        }
        final Board transitionBoard = move.execute();
        final Collection<Move> kingAttacks = Player.calculateAttacksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
                                                                           transitionBoard.currentPlayer().getLegalMoves());
        if(!kingAttacks.isEmpty()){
            return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }
        return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
    }

    // Get the pieces for a white or black player polymorphically
    public abstract Collection<Piece> getActivePieces();

    // Get the color for a player
    public abstract Color getColor();

    // Get the player itself
    public abstract Player getOpponent(); 
}
