package com.directi.training.codesmells.refactored.pieces;

import com.directi.training.codesmells.refactored.Color;
import com.directi.training.codesmells.refactored.Position;

// Fixed Collapse Hierarchy Code Smell by removing the unnecessary LeftKnight and RightKnight subclasses.
public class Knight extends Piece
{
    public Knight(Color color)
    {
        super(color);
    }

    // Fixed Switch-Case Code Smell by moving the knight movement logic to the Knight class.
    @Override
    public boolean isValidMove(Position from, Position to)
    {
        int columnDiff = Math.abs(to.getColumn() - from.getColumn());
        int rowDiff = Math.abs(to.getRow() - from.getRow());

        return (columnDiff == 2 && rowDiff == 1)
                || (columnDiff == 1 && rowDiff == 2);
    }

    @Override
    public String toString()
    {
        return "k";
    }
}