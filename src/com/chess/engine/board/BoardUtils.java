package com.chess.engine.board;

//We will use this class for helpful constants and static methods.
public class BoardUtils {

    private BoardUtils(){
        throw new RuntimeException("You cannot instatiate this class");
    }

    public static boolean isValidSquareCoordinate(int coordinate) {
        return coordinate >= 0 && coordinate < 64;
    }
}
