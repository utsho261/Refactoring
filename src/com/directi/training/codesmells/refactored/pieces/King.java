package com.directi.training.codesmells.refactored.pieces;

import com.directi.training.codesmells.refactored.Color;
import com.directi.training.codesmells.refactored.Position;

public class King extends Piece
{
    public King(Color color)
    {
        super(color);
    }

    // Fixed Switch-Case Code Smell by moving the king movement logic to the King class.
    @Override
    public boolean isValidMove(Position from, Position to)
    {
        return Math.abs(from.getRow() - to.getRow()) == 1
                && Math.abs(from.getColumn() - to.getColumn()) == 1;
    }

    @Override
    public String toString()
    {
        return "K";
    }
}