package com.chess.engine;

public enum Color {
    WHITE{
        @Override
        public int getDirection(){
            return -1;
        }
        public boolean isWhite(){ return true; }
        public boolean isBlack(){ return false; }
    },
    BLACK{
        @Override
        public int getDirection(){
            return 1;
        }
        public boolean isWhite(){ return false; }
        public boolean isBlack(){ return true; }
    };

    public abstract int getDirection();
    public abstract boolean isWhite();
    public abstract boolean isBlack();
}
