package com.chess.engine;

public enum Color {
    WHITE{
        @Override
        public int getDirection(){
            return -1;
        }
    },
    BLACK{
        @Override
        public int getDirection(){
            return 1;
        }
    };

    public abstract int getDirection();
}
