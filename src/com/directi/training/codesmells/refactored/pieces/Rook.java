package com.directi.training.codesmells.refactored.pieces;

import com.directi.training.codesmells.refactored.Color;
import com.directi.training.codesmells.refactored.Position;
import com.directi.training.codesmells.refactored.chess.MoveUtil;

// Fixed Collapse Hierarchy Code Smell by removing the unnecessary LeftRook and RightRook subclasses.
public class Rook extends Piece
{
    public Rook(Color color)
    {
        super(color);
    }

    // Fixed Switch-Case Code Smell by moving the rook movement logic to the Rook class.
    @Override
    public boolean isValidMove(Position from, Position to)
    {
        return MoveUtil.isHorizontalOrVerticalMove(from, to);
    }

    @Override
    public String toString()
    {
        return "r";
    }
}