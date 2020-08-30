package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;

//Creating an abstract class to allow for polymorphism to check if tile is occupied or not.
// Will return if occupied and the piece itself, if any.
public abstract class Square {

    //protected for subclass access only
    //final for immutability 
    protected final int squareCoordinate;

    //creating an immutable map for the chessboard
    private static final Map<Integer, EmptySquare> EMPTY_SQUARES_CACHE = createAllSquares();

    private static Map<Integer, EmptySquare> createAllSquares() {
        final Map<Integer, EmptySquare> emptySquareMap = new HashMap<>();

        for(int i = 0; i < 64; i++){
            emptySquareMap.put(i, new EmptySquare(i));
        }

        //enforcing immutability by returning a non-changeable map, thanks google/josh
        return ImmutableMap.copyOf(emptySquareMap);
    }

    //creating a static factory method to enable user to create occupied square
    public static Square createSquare(final int squareCoordinate, final Piece piece){
        return piece != null ? new OccupiedSquare(squareCoordinate, piece) : EMPTY_SQUARES_CACHE.get(squareCoordinate);
    }

    private Square(int squareCoordinate){
        this.squareCoordinate = squareCoordinate;
    }

    public abstract boolean isSquareOccupied();

    public abstract Piece getPiece();


    //This concrete class is instantiated and used when we want to create an empty square with no piece
    //Static for readability and to avoid inheritance
    public static final class EmptySquare extends Square{

        private EmptySquare(final int squareCoordinate){
            super(squareCoordinate);
        }

        @Override
        public boolean isSquareOccupied(){
            return false;
        }

        @Override
        public Piece getPiece(){
            return null;
        }

    }

    //This concrete class is instantiated and used when we want to get an occupied square and the respective piece
    //Static for readability and to avoid inheritance
    public static final class OccupiedSquare extends Square{

        //private to force use of getPiece()
        //final for immutability
        private final Piece pieceOnSquare;

        private OccupiedSquare(final int squareCoordinate, final Piece pieceOnSquare){
            super(squareCoordinate);
            this.pieceOnSquare = pieceOnSquare;
        }

        @Override
        public boolean isSquareOccupied(){
            return true;
        }

        @Override
        public Piece getPiece(){
            return this.pieceOnSquare;
        }
    }
}
