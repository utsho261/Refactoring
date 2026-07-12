package com.directi.training.codesmells.refactored.pieces;

import com.directi.training.codesmells.refactored.Color;
import com.directi.training.codesmells.refactored.Position;
import com.directi.training.codesmells.refactored.chess.MoveUtil;

// Fixed Collapse Hierarchy Code Smell by removing the unnecessary LeftBishop and RightBishop subclasses.
public class Bishop extends Piece
{
    public Bishop(Color color)
    {
        super(color);
    }

    // Fixed Switch-Case Code Smell by moving the bishop movement logic to the Bishop class.
    @Override
    public boolean isValidMove(Position from, Position to)
    {
        return MoveUtil.isDiagonalMove(from, to);
    }

    @Override
    public String toString()
    {
        return "b";
    }
}