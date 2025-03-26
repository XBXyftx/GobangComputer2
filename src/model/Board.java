package model;

import enums.Player;

public class Board {
    /**
     * 棋盘大小，默认为15x15
     */
    private static final int SIZE = 15;
    /**
     * 二维数组表示的棋盘状态
     * null 表示空位，PLAYER_ONE 表示玩家一，PLAYER_TWO 表示计算机
     */
    private Player[][] board; // 棋盘状态

    private static Board instance;

    private Board() {
        this.board = new Player[SIZE][SIZE];
        initializeBoard();
    }

    /**
     * 获取单例实例
     *
     * @return 单例实例
     */
    public static Board getInstance() {
        if (instance == null) {
            instance = new Board();
        }
        return instance;
    }

    /**
     * 初始化棋盘，所有位置设为空位（null）
     */
    private void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = null;
            }
        }
    }

    /**
     * 获取棋盘大小
     *
     * @return 棋盘大小
     */
    public int getSize() {
        return SIZE;
    }

    /**
     * 获取指定位置的状态
     *
     * @param row 行坐标
     * @param col 列坐标
     * @return 该位置的状态（null: 空位, PLAYER_ONE: 玩家一, PLAYER_TWO: 计算机）
     */
    public Player getPiece(int row, int col) {
        if (row >= 0 && row < SIZE && col >= 0 && col < SIZE) {
            return board[row][col];
        } else {
            throw new IllegalArgumentException("Invalid position");
        }
    }

    /**
     * 在指定位置放置棋子
     *
     * @param row   行坐标
     * @param col   列坐标
     * @param piece 棋子类型（PLAYER_ONE 或 PLAYER_TWO）
     */
    public void placePiece(int row, int col, Player piece) {
        if (row >= 0 && row < SIZE && col >= 0 && col < SIZE) {
            if (board[row][col] == null) {
                board[row][col] = piece;
            } else {
                throw new IllegalArgumentException("该位置已经被占");
            }
        } else {
            throw new IllegalArgumentException("这个位置不存在");
        }
    }

    /**
     * 移除指定位置的棋子
     *
     * @param row 行坐标
     * @param col 列坐标
     */
    public void removePiece(int row, int col) {
        if (row >= 0 && row < SIZE && col >= 0 && col < SIZE) {
            board[row][col] = null;
        } else {
            throw new IllegalArgumentException("这个位置不存在");
        }
    }

    /**
     * 检查游戏是否结束
     *
     * @param lastRow 最后一步的行坐标
     * @param lastCol 最后一步的列坐标
     * @return 如果游戏结束返回true，否则返回false
     */
    public boolean isGameOver(int lastRow, int lastCol) {
        Player currentPlayer = board[lastRow][lastCol];
        return checkWin(lastRow, lastCol, currentPlayer);
    }

    /**
     * 检查当前玩家是否获胜
     *
     * @param row         当前行坐标
     * @param col         当前列坐标
     * @param currentPlayer 当前玩家
     * @return 如果当前玩家获胜返回true，否则返回false
     */
    private boolean checkWin(int row, int col, Player currentPlayer) {
        // 检查水平方向是否有五个连珠
        if (checkDirection(row, col, currentPlayer, 0, 1))
            return true;
        // 检查垂直方向是否有五个连珠
        if (checkDirection(row, col, currentPlayer, 1, 0))
            return true;
        // 检查左上到右下的对角线方向是否有五个连珠
        if (checkDirection(row, col, currentPlayer, 1, 1))
            return true;
        // 检查右上到左下的对角线方向是否有五个连珠
        return checkDirection(row, col, currentPlayer, 1, -1);
    }

    /**
     * 检查指定方向是否有五个连珠
     *
     * @param row         当前行坐标
     * @param col         当前列坐标
     * @param currentPlayer 当前玩家
     * @param deltaRow    行方向增量
     * @param deltaCol    列方向增量
     * @return 如果在指定方向上有五个连珠返回true，否则返回false
     */
    private boolean checkDirection(int row, int col, Player currentPlayer, int deltaRow, int deltaCol) {
        int count = 1; // 包括当前位置的一个棋子
        // 向一个方向检查
        for (int i = 1; i < 5; i++) {
            int newRow = row + i * deltaRow;
            int newCol = col + i * deltaCol;
            if (isValidPosition(newRow, newCol) && board[newRow][newCol] == currentPlayer) {
                count++;
            } else {
                break;
            }
        }
        // 向相反方向检查
        for (int i = 1; i < 5; i++) {
            int newRow = row - i * deltaRow;
            int newCol = col - i * deltaCol;
            if (isValidPosition(newRow, newCol) && board[newRow][newCol] == currentPlayer) {
                count++;
            } else {
                break;
            }
        }
        return count >= 5;
    }

    /**
     * 检查位置是否有效
     *
     * @param row 行坐标
     * @param col 列坐标
     * @return 如果位置有效返回true，否则返回false
     */
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }

    /**
     * 打印棋盘状态（仅用于调试）
     * 真正的棋盘由UI组件渲染，此方法仅用于控制台调试
     */
    public void printBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == null) {
                    System.out.print("0 ");
                } else if (board[i][j] == Player.HUMAN_PLAYER) {
                    System.out.print("1 ");
                } else {
                    System.out.print("-1 ");
                }
            }
            System.out.println();
        }
    }
}



