import java.util.Scanner;

public class SudokuGame {
    int[] board[];
    int boardSize;
    int sqrtBoardSize;
    int missingVals;

    SudokuGame(int boardSize, int missingVals) {
        this.boardSize = boardSize;
        this.missingVals = missingVals;

        Double sqrtBS = Math.sqrt(boardSize);
        sqrtBoardSize = sqrtBS.intValue();

        board = new int[boardSize][boardSize];
    }

    public void fillValues() {
        fillDiagonal();

        fillRemaining(0, sqrtBoardSize);

        removeDigits();
    }

    void fillDiagonal() {
        for (int i = 0; i < boardSize; i = i + sqrtBoardSize)
            fillBoard(i, i);
    }

    boolean boardUnfilled(int rowStart, int colStart, int num) {
        for (int i = 0; i < sqrtBoardSize; i++)
            for (int j = 0; j < sqrtBoardSize; j++)
                if (board[rowStart + i][colStart + j] == num)
                    return false;

        return true;
    }

    void fillBoard(int row, int col) {
        int num;
        for (int i = 0; i < sqrtBoardSize; i++) {
            for (int j = 0; j < sqrtBoardSize; j++) {
                do {
                    num = randomGenerator(boardSize);
                } while (!boardUnfilled(row, col, num));

                board[row + i][col + j] = num;
            }
        }
    }

    int randomGenerator(int num) {
        return (int) Math.floor((Math.random() * num + 1));
    }

    boolean CheckIfSafe(int i, int j, int num) {
        return (rowUnfilled(i, num) && colUnfilled(j, num)
                && boardUnfilled(i - i % sqrtBoardSize, j - j % sqrtBoardSize, num));
    }

    boolean rowUnfilled(int i, int num) {
        for (int j = 0; j < boardSize; j++)
            if (board[i][j] == num)
                return false;
        return true;
    }

    boolean colUnfilled(int j, int num) {
        for (int i = 0; i < boardSize; i++)
            if (board[i][j] == num)
                return false;
        return true;
    }

    boolean fillRemaining(int i, int j) {
        if (j >= boardSize && i < boardSize - 1) {
            i = i + 1;
            j = 0;
        }
        if (i >= boardSize && j >= boardSize)
            return true;

        if (i < sqrtBoardSize) {
            if (j < sqrtBoardSize)
                j = sqrtBoardSize;
        } else if (i < boardSize - sqrtBoardSize) {
            if (j == (int) (i / sqrtBoardSize) * sqrtBoardSize)
                j = j + sqrtBoardSize;
        } else {
            if (j == boardSize - sqrtBoardSize) {
                i = i + 1;
                j = 0;
                if (i >= boardSize)
                    return true;
            }
        }

        for (int num = 1; num <= boardSize; num++) {
            if (CheckIfSafe(i, j, num)) {
                board[i][j] = num;
                if (fillRemaining(i, j + 1))
                    return true;

                board[i][j] = 0;
            }
        }
        return false;
    }

    public void removeDigits() {
        int temp = missingVals;
        while (temp != 0) {
            int cellId = randomGenerator(boardSize * boardSize);

            int i = (cellId / boardSize);
            int j = cellId % 9;
            if (j != 0)
                j = j - 1;

            if (board[i][j] != 0) {
                temp--;
                board[i][j] = 0;
            }
        }
    }

    public void printSudoku() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++)
                System.out.print(board[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nSize of your Sudoku board: ");
        int boardSize = sc.nextInt();
        System.out.print("Number of missing values: ");
        int missingVals = sc.nextInt();

        System.out.println("\nLET'S PLAY SUDOKU!");
        SudokuGame sudoku = new SudokuGame(boardSize, missingVals);
        sudoku.fillValues();
        sudoku.printSudoku();
        sc.close();
    }
}