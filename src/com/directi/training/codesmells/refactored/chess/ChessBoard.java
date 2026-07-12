package com.directi.training.codesmells.refactored.chess;

import com.directi.training.codesmells.refactored.Color;
import com.directi.training.codesmells.refactored.Direction;
import com.directi.training.codesmells.refactored.Position;
import com.directi.training.codesmells.refactored.pieces.*;

public class ChessBoard
{
    // Defines the board size in one place and removes repeated magic numbers.
    private static final int BOARD_SIZE = 8;
    private final Cell[][] _board;
    private boolean _kingDead; // Fixed Indecent Exposure Code Smell by making the field private.
    //Dead code
    //Unused player fields
    //public Player player1, player2;

    public ChessBoard()
    {
        _board = new Cell[BOARD_SIZE][BOARD_SIZE];
        initBoard();
        resetBoard();
    }

    private void initBoard()
    {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                Color color = ((row + column) % 2 == 0)
                        ? Color.WHITE
                        : Color.BLACK;

                _board[row][column] = new Cell(color);
            }
        }
    }

    // Removes the Long Method from GameEngine.
    // Fixed Feature Envy Code Smell by moving board setup to ChessBoard.
    public void resetBoard()
    {
        placePieces(Color.WHITE);
        placePieces(Color.BLACK);

        _kingDead = false;
    }

    // Uses the same setup process for both white and black pieces.
    // This removes duplicated setup logic.
    private void placePieces(Color color)
    {
        int pawnsRow;
        int otherPiecesRow;

        if (color == Color.WHITE) {
            // White pawns are placed two rows before the end of the board.
            pawnsRow = BOARD_SIZE - 2;

            // White major pieces are placed on the last row.
            otherPiecesRow = BOARD_SIZE - 1;
        } else {
            pawnsRow = 1;
            otherPiecesRow = 0;
        }

        placeOtherPieces(otherPiecesRow, color);
        placePawns(pawnsRow, color);
    }

    // Places all pawns of the given color on the specified row.
    private void placePawns(int row, Color color)
    {
        for (int column = 0; column < BOARD_SIZE; column++) {
            _board[row][column].setPiece(
                    new Pawn(color)
            );
        }
    }

    // Fixed Collapse Hierarchy Code Smell by creating
    // Rook, Knight and Bishop directly.
    private void placeOtherPieces(int row, Color color)
    {
        for (int column = 0; column < BOARD_SIZE; column++) {
            Piece piece = null;

            if (column == 0 || column == BOARD_SIZE - 1) {
                piece = new Rook(color);
            } else if (column == 1 || column == BOARD_SIZE - 2) {
                piece = new Knight(color);
            } else if (column == 2 || column == BOARD_SIZE - 3) {
                piece = new Bishop(color);
            } else if (column == 3) {
                piece = new King(color);
            } else if (column == 4) {
                piece = new Queen(color);
            }

            _board[row][column].setPiece(piece);
        }
    }


    //Dead code
//    public Cell[][] getBoard()
//    {
//        return _board;
//    }

    private boolean isPositionOutOfBounds(Position position)
    {
        return position.getRow() < 0
                || position.getRow() >= BOARD_SIZE
                || position.getColumn() < 0
                || position.getColumn() >= BOARD_SIZE;
    }

    public boolean isEmpty(Position position)
    {
        return isPositionOutOfBounds(position)
                || getCell(position).isEmpty();
    }

    private Cell getCell(Position position)
    {
        return _board[position.getRow()][position.getColumn()];
    }

    //Solve Duplicate Code Within Class
    public Piece getPiece(Position position)
    {
        // Reuses isEmpty() instead of repeating the same validation logic.
        return isEmpty(position)
                ? null
                : getCell(position).getPiece();
    }
//     Dead code
//    public String getPlayerName(Position position)
//    {
//        if (isPositionOutOfBounds(position))
//            return null;
//        Color color = getCell(position).getPiece().getColor();
//        if (color == player1.getColor()) {
//            return player1.getName();
//        } else {
//            return player2.getName();
//        }
//    }
//
//    private void printMove(Position from, Position to)
//    {
//        System.out.println(getPlayerName(from) + " moved " + getPiece(from) + " from " + from + " to " + to);
//        if (getPiece(from).getColor() != getPiece(to).getColor()) {
//            System.out.println("And has captured " + getPiece(to) + " of " + getPlayerName(to));
//        }
//    }

    /*
     * Fixed Long Parameter List Code Smell by passing Position objects
     * instead of passing row and column values separately.
     */
    public boolean isValidMove(Position from, Position to)
    {
        return !from.equals(to)
                && !isPositionOutOfBounds(from)
                && !isPositionOutOfBounds(to)
                && !isEmpty(from)
                && (
                isEmpty(to)
                        || getPiece(from).getColor()
                        != getPiece(to).getColor()
        )
                && getPiece(from).isValidMove(from, to)
                && hasNoPieceInPath(from, to)
                && (
                !(getPiece(from) instanceof Pawn)
                        || isValidPawnMove(from, to)
        );
    }

    private boolean hasNoPieceInPath(Position from, Position to)
    {
        if (getPiece(from) instanceof Knight)
            return true;

        // Reuses the shared movement calculation from MoveUtil.
        if (!MoveUtil.isStraightLineMove(from, to))
            return false;

        Direction direction = new Direction(
                cappedCompare(to.getRow(), from.getRow()),
                cappedCompare(to.getColumn(), from.getColumn())
        );

        // Position now handles its own translation behavior.
        Position currentPosition =
                from.translatedPosition(direction);

        while (!currentPosition.equals(to)) {
            if (!isEmpty(currentPosition))
                return false;

            currentPosition =
                    currentPosition.translatedPosition(direction);
        }

        return true;
    }

    private int cappedCompare(int x, int y)
    {
        return Math.max(-1, Math.min(1, Integer.compare(x, y)));
    }



    /*
     * Fixed Long Parameter List Code Smell by passing Position objects
     * instead of four separate integer parameters.
     */
    public void movePiece(Position from, Position to)
    {
        updateIsKingDead(to);

        if (!getCell(to).isEmpty()) {
            getCell(to).removePiece();
        }

        getCell(to).setPiece(getPiece(from));
        getCell(from).removePiece();
    }

    /*
     * Fixed Long Parameter List Code Smell by passing the Position object
     * instead of passing row and column separately.
     */
    private void updateIsKingDead(Position positionBeingMovedTo)
    {
        if (getPiece(positionBeingMovedTo) instanceof King) {
            _kingDead = true;
        }
    }


    private boolean isValidPawnMove(Position from, Position to)
    {
        assert getPiece(from) instanceof Pawn;
        Pawn pawn = (Pawn)getPiece(from);
        Color pawnColor = pawn.getColor();
        int forwardRow = from.getRow() + ((pawnColor == Color.BLACK) ? 1 : -1);
        Position forwardLeft = new Position(forwardRow, from.getColumn() + (pawnColor == Color.WHITE ? -1 : 1));
        Position forwardRight = new Position(forwardRow, from.getColumn() + (pawnColor == Color.WHITE ? 1 : -1));

        boolean opponentPieceAtForwardLeft = !isEmpty(forwardLeft) && getPiece(forwardLeft).getColor() != pawnColor;
        boolean opponentPieceAtForwardRight = !isEmpty(forwardRight) && getPiece(forwardRight).getColor() != pawnColor;
        boolean atInitialPosition = from.getRow() == ((pawnColor == Color.BLACK) ? 1 : 6);

        return pawn.isValidMoveGivenContext(from, to, atInitialPosition, opponentPieceAtForwardLeft, opponentPieceAtForwardRight);
    }

    public boolean isKingDead()
    {
        return _kingDead;
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder(" ");
        for (int column = 0; column < BOARD_SIZE; column++) {
            stringBuilder.append("  ")
                    .append(column + 1)
                    .append("  ");
        }
        stringBuilder.append("\n");

        for (int row = 0; row < BOARD_SIZE; row++) {
            stringBuilder.append(row + 1);
            for (int column = 0; column < BOARD_SIZE; column++) {
                stringBuilder.append(" ")
                        .append(_board[row][column])
                        .append(" ");
            }
            stringBuilder.append("\n\n");
        }
        return stringBuilder.toString();
    }
}
