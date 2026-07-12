package com.directi.training.codesmells.refactored.chess;

import com.directi.training.codesmells.refactored.Position;

// Contains common movement calculations used by multiple classes.
public final class MoveUtil
{
    private MoveUtil()
    {
        // Prevent object creation because this class only contains utility methods.
    }

    public static boolean isDiagonalMove(
            Position from,
            Position to)
    {
        return Math.abs(
                from.getRow() - to.getRow()
        ) == Math.abs(
                from.getColumn() - to.getColumn()
        );
    }

    public static boolean isHorizontalOrVerticalMove(
            Position from,
            Position to)
    {
        return from.getRow() == to.getRow()
                || from.getColumn() == to.getColumn();
    }

    public static boolean isStraightLineMove(
            Position from,
            Position to)
    {
        return isDiagonalMove(from, to)
                || isHorizontalOrVerticalMove(from, to);
    }
}