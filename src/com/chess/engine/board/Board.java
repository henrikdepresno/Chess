package com.chess.engine.board;

import com.chess.engine.Color;
import com.chess.engine.pieces.*;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;
import com.google.common.collect.ImmutableList;

import java.util.*;

public class Board {

    // We are choosing a List of squares since it can be Immutable, unlike arrays.
    private final List<Square> gameBoard;

    // We will then have our collection of pieces. This will be used to store all our pieces on the board.
    private final Collection<Piece> blackPieces;
    private final Collection<Piece> whitePieces;

    // Creating our player objects, we will relate these with a common superclas
    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;

    private final Player currentPlayer;

    // Using Builder pattern for the constructor
    private Board(final Builder builder){
        this.gameBoard = createGameBoard(builder);
        this.blackPieces = calculateActivePieces(this.gameBoard, Color.BLACK);
        this.whitePieces = calculateActivePieces(this.gameBoard, Color.WHITE);

        // Storing a collection of all legal moves for all pieces when game starts.
        final Collection<Move> blackLegalMoves = calculateLegalMoves(this.blackPieces);
        final Collection<Move> whiteLegalMoves = calculateLegalMoves(this.whitePieces);

        // Constructing the players with their own moves and the counterpart moves to determine legal castle moves
        this.whitePlayer = new WhitePlayer(this, whiteLegalMoves, blackLegalMoves);
        this.blackPlayer = new BlackPlayer(this, whiteLegalMoves, blackLegalMoves);
        this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.whitePlayer, this.blackPlayer);
    }

    // For all given Pieces, regardless of color, return all the legalmoves on the current board.
    private Collection<Move> calculateLegalMoves(final Collection<Piece> pieces){
        final List<Move> legalMoves = new ArrayList<>();
        for(final Piece piece: pieces){
           legalMoves.addAll(piece.calcLegalMove(this));
        }
        return Collections.unmodifiableList(legalMoves);
    }

    // Getting the coordinate of a square
    public Square getSquare(final int squareCoordinate){
        //TODO: ERROR HERE
        return gameBoard.get(squareCoordinate);
    }

    public Collection<Piece> getBlackPieces(){
        return this.blackPieces;
    }

    public Collection<Piece> getWhitePieces(){
        return this.whitePieces;
    }

    public Player whitePlayer(){
        return whitePlayer;
    }

    public Player blackPlayer(){
        return blackPlayer;
    }

    public Player currentPlayer(){
        return this.currentPlayer;
    }

    // For each occupied square on the board, get the activePieces.
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

    // Initialize the chess board for the first time.
    private static List<Square> createGameBoard(final Builder builder){
        final Square[] squares = new Square[BoardUtils.NUM_SQUARES];
        for(int i = 0; i < BoardUtils.NUM_SQUARES; i++){
            squares[i] = Square.createSquare(i, builder.boardConfig.get(i));
        }
        return ImmutableList.copyOf(squares);
    }

    // Initializing all the pieces on the baord and creating them in a given squareCoordinate.
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

    public Iterable<Move> getAllLegalMoves() {
        List<Move> allLegalMoves = new ArrayList<>();
        allLegalMoves.addAll(this.whitePlayer.getLegalMoves());
        allLegalMoves.addAll(this.blackPlayer.getLegalMoves());
        return Collections.unmodifiableList(allLegalMoves);
    }

    // Builder constructor
    public static class Builder{

        Map<Integer, Piece> boardConfig;
        Color nextMoveMaker;
        Pawn enPassantPawn;

        public Builder() {
            this.boardConfig = new HashMap<>();
        }

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

        public void setEnPassantPawn(Pawn enPassantPawn) {
            this.enPassantPawn = enPassantPawn;
        }
    }

    // Used for early testing, will print out a board in the terminal
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for(int i = 0; i < BoardUtils.NUM_SQUARES; i++){
            final String squareText = this.gameBoard.get(i).toString();
            sb.append(String.format("%3s", squareText));
            if((i+1) % BoardUtils.NUM_SQUARES_PER_ROW == 0){
                sb.append("\n");
            }
        }
        return sb.toString();
    }

}
