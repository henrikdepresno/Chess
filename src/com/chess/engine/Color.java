package com.chess.engine;

import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.Player;
import com.chess.engine.player.WhitePlayer;

// Enum class for directionality of pieces and getting their color.
// White pawns for example can only move north.
// Black pawns can only move south.
public enum Color {
    WHITE{
        @Override
        public int getDirection(){
            return -1;
        }
        public boolean isWhite(){ return true; }
        public boolean isBlack(){ return false; }

        @Override
        public Player choosePlayer(final WhitePlayer whitePlayer,
                                   final BlackPlayer blackPlayer) {
            return whitePlayer;
        }
    },
    BLACK{
        @Override
        public int getDirection(){
            return 1;
        }
        public boolean isWhite(){ return false; }
        public boolean isBlack(){ return true; }

        @Override
        public Player choosePlayer(final WhitePlayer whitePlayer,
                                   final BlackPlayer blackPlayer) {
            return blackPlayer;
        }
    };

    public abstract int getDirection();
    public abstract boolean isWhite();
    public abstract boolean isBlack();

    public abstract Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer);
}
