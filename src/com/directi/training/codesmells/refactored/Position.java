package com.directi.training.codesmells.refactored;

public class Position
{
    private final int _row;
    private final int _column;

    public Position(int row, int column)
    {
        _row = row;
        _column = column;
    }

    public int getRow()
    {
        return _row;
    }

    public int getColumn()
    {
        return _column;
    }

    // Fixed Feature Envy Code Smell by moving translation behavior to Position.
    public Position translatedPosition(Direction direction)
    {
        return new Position(
                _row + direction.getRowOffset(),
                _column + direction.getColumnOffset()
        );
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null || !(obj instanceof Position))
            return false;

        Position otherPosition = (Position) obj;

        return this == obj
                || (_row == otherPosition.getRow()
                && _column == otherPosition.getColumn());
    }

}