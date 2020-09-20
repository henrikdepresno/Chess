package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//Creating an abstract class to allow for polymorphism to check if tile is occupied or not.
// Will return Piece if occupied
public abstract class Square {

    //protected for subclass access only
    //final for immutability 
    protected final int squareCoordinate;

    //creating an immutable map for the chessboard
    private static final Map<Integer, EmptySquare> EMPTY_SQUARES_CACHE = createAllSquares();

    private static Map<Integer, EmptySquare> createAllSquares() {
        final Map<Integer, EmptySquare> emptySquareMap = new HashMap<>();

        for(int i = 0; i < BoardUtils.NUM_SQUARES; i++){
            emptySquareMap.put(i, new EmptySquare(i));
        }

        //enforcing immutability by returning a non-changeable map
        return Collections.unmodifiableMap(emptySquareMap);
    }

    //creating a static factory method to enable user to create occupied square
    public static Square createSquare(final int squareCoordinate, final Piece piece){
        return piece != null ? new OccupiedSquare(squareCoordinate, piece) : EMPTY_SQUARES_CACHE.get(squareCoordinate);
    }

    private Square(final int squareCoordinate){
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
        public String toString() {
            return "-";
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
        public String toString(){
            return getPiece().getPieceColor().isBlack() ? getPiece().toString().toLowerCase() :
                   getPiece().toString();
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
