package com.directi.training.codesmells.refactored.pieces;

import com.directi.training.codesmells.refactored.Color;
import com.directi.training.codesmells.refactored.Position;

public abstract class Piece
{
    public Color _color;

    public Piece(Color color)
    {
        _color = color;
    }

    public Color getColor()
    {
        return _color;
    }

    // Fixed Switch-Case Code Smell through polymorphism. The type field has also been removed.
    public abstract boolean isValidMove(Position from, Position to);
}