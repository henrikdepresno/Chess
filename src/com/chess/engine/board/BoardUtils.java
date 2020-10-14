package com.chess.engine.board;

// We will use this class for helpful constants and static methods.
public class BoardUtils {

    // Boolean arrays for each column/row used when we need to see if a pieces legal move is out of bounds, i.e. edge cases
    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);       
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);

    public static final boolean[] EIGHTH_RANK = initRow(0);
    public static final boolean[] SEVENTH_RANK = initRow(8);
    public static final boolean[] SIXTH_RANK = initRow(16);
    public static final boolean[] FIFTH_RANK = initRow(24);
    public static final boolean[] FOURTH_RANK = initRow(32);
    public static final boolean[] THIRD_RANK = initRow(40);
    public static final boolean[] SECOND_RANK = initRow(48);
    public static final boolean[] FIRST_RANK = initRow(56);

    // The number of squares on a standard chess board
    public static final int NUM_SQUARES = 64;
    public static final int NUM_SQUARES_PER_ROW = 8;

    private BoardUtils(){
        throw new RuntimeException("You cannot instantiate this class");
    }

    // For each column passed in, initialize them from false to true
    private static boolean[] initColumn(int columnNumber){
        final boolean[] column = new boolean[NUM_SQUARES];
        do{
            column[columnNumber] = true;
            columnNumber += NUM_SQUARES_PER_ROW;
        } while(columnNumber < NUM_SQUARES);
        return column;
    }

    // For a given row number passed, initialize it from false to true.
    private static boolean[] initRow(int rowNumber){
        final boolean[] row = new boolean[NUM_SQUARES];
        do{
           row[rowNumber] = true;
           rowNumber++;
        } while(rowNumber % NUM_SQUARES_PER_ROW != 0);
        return row;
    }

    // Check to see if the coordinate is out of bounds.
    public static boolean isValidSquareCoordinate(final int coordinate) {
        return coordinate >= 0 && coordinate < 64;
    }
}
