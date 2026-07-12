package com.directi.training.codesmells.refactored.pieces;

import com.directi.training.codesmells.refactored.Color;
import com.directi.training.codesmells.refactored.Position;
import com.directi.training.codesmells.refactored.chess.MoveUtil;

public class Queen extends Piece
{
    public Queen(Color color)
    {
        super(color);
    }

    // Fixed Switch-Case Code Smell by moving the queen movement logic to the Queen class.
    @Override
    public boolean isValidMove(Position from, Position to)
    {
        return MoveUtil.isStraightLineMove(from, to);
    }

    @Override
    public String toString()
    {
        return "q";
    }
}