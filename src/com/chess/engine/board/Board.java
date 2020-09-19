package com.chess.engine.board;

import com.chess.engine.Color;
import com.chess.engine.pieces.*;
import com.google.common.collect.ImmutableList;

import java.util.*;

public class Board {

    //We are choosing a List since it can be Immutable, unlike arrays.
    private final List<Square> gameBoard;
    private final Collection<Piece> blackPieces;
    private final Collection<Piece> whitePieces;


    private Board(Builder builder){
        this.gameBoard = createGameBoard(builder);
        this.blackPieces = calculateActivePieces(this.gameBoard, Color.BLACK);
        this.whitePieces = calculateActivePieces(this.gameBoard, Color.WHITE);
    }
                                              
    public Square getSquare(final int squareCoordinate){
        return null;
    }

    private static Collection<Piece> calculateActivePieces(final List<Square> board, final Color color){

        final List<Piece> activePieces = new ArrayList<>();
        for(final Square square: board){
            if(square.isSquareOccupied()){
                final Piece piece = square.getPiece();
                if(piece.getPieceColor() == color){
                    activePieces.add(piece);
                }
            }
        }
        return Collections.unmodifiableList(activePieces);
    }

    private static List<Square> createGameBoard(final Builder builder){
        final Square[] squares = new Square[BoardUtils.NUM_SQUARES];
        for(int i = 0; i < BoardUtils.NUM_SQUARES; i++){
            squares[i] = Square.createSquare(i, builder.boardConfig.get(i));
        }
        return ImmutableList.copyOf(squares);
    }

    public static Board createStandardBoard(){
        final Builder builder = new Builder();

        // Black starting positions
        builder.setPiece(new Rook(Color.BLACK, 0));
        builder.setPiece(new Knight(Color.BLACK, 1));
        builder.setPiece(new Bishop(Color.BLACK, 2));
        builder.setPiece(new Queen(Color.BLACK, 3));
        builder.setPiece(new King(Color.BLACK, 4));
        builder.setPiece(new Bishop(Color.BLACK, 5));
        builder.setPiece(new Knight(Color.BLACK, 6));
        builder.setPiece(new Rook(Color.BLACK, 7));
        builder.setPiece(new Pawn(Color.BLACK, 8));
        builder.setPiece(new Pawn(Color.BLACK, 9));
        builder.setPiece(new Pawn(Color.BLACK, 10));
        builder.setPiece(new Pawn(Color.BLACK, 11));
        builder.setPiece(new Pawn(Color.BLACK, 12));
        builder.setPiece(new Pawn(Color.BLACK, 13));
        builder.setPiece(new Pawn(Color.BLACK, 14));
        builder.setPiece(new Pawn(Color.BLACK, 15));

        // White starting positions
        builder.setPiece(new Pawn(Color.WHITE, 48));
        builder.setPiece(new Pawn(Color.WHITE, 49));
        builder.setPiece(new Pawn(Color.WHITE, 50));
        builder.setPiece(new Pawn(Color.WHITE, 51));
        builder.setPiece(new Pawn(Color.WHITE, 52));
        builder.setPiece(new Pawn(Color.WHITE, 53));
        builder.setPiece(new Pawn(Color.WHITE, 54));
        builder.setPiece(new Pawn(Color.WHITE, 55));
        builder.setPiece(new Rook(Color.WHITE, 56));
        builder.setPiece(new Knight(Color.WHITE, 57));
        builder.setPiece(new Bishop(Color.WHITE, 58));
        builder.setPiece(new Queen(Color.WHITE, 59));
        builder.setPiece(new King(Color.WHITE, 60));
        builder.setPiece(new Bishop(Color.WHITE, 61));
        builder.setPiece(new Knight(Color.WHITE, 62));
        builder.setPiece(new Rook(Color.WHITE, 63));

        // Making sure that White will be the one to move first
        builder.setMoveMaker(Color.WHITE);

        return builder.build();
    }

    public static class Builder{

        Map<Integer, Piece> boardConfig;
        Color nextMoveMaker;

        public Builder setPiece(Piece piece){
            this.boardConfig.put(piece.getPiecePosition(), piece);
            return this;
        }

        public Builder setMoveMaker(final Color nextMoveMaker){
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }

        public Board build(){
            return new Board(this);
        }
    }

}
