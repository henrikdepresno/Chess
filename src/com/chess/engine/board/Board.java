package com.chess.engine.board;

import com.chess.engine.Color;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    //We are choosing a List since it can be Immutable, unlike arrays.
    private final List<Square> gameBoard;


    private Board(Builder builder){
        this.gameBoard = createGameBoard(builder);
    }
                                              
    public Square getSquare(final int squareCoordinate){
        return null;
    }

    private static List<Square> createGameBoard(final Builder builder){
        final Square[] squares = new Square[BoardUtils.NUM_SQUARES];
        for(int i = 0; i < BoardUtils.NUM_SQUARES; i++){
            squares[i] = Square.createSquare(i, builder.boardConfig.get(i));
        }
        return ImmutableList.copyOf(squares);
    }

    public static Board createStandardBoard(){
        
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

        public Board Build(){
            return new Board(this);
        }
    }

}
