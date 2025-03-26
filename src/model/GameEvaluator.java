package model;

import enums.Player;

public class GameEvaluator {

    /**
     * 评估当前棋盘状态的价值
     *
     * @param board 当前棋盘状态
     * @return 评估分数，正值表示玩家一有利，负值表示玩家二有利
     */
    public int evaluate(Board board) {
        int scorePlayerOne = evaluateLines(board, Player.HUMAN_PLAYER);
        int scorePlayerTwo = evaluateLines(board, Player.COMPUTER_PLAYER);
        return scorePlayerOne - scorePlayerTwo;
    }

    /**
     * 评估某玩家的所有可能线（水平、垂直、对角线）的得分
     *
     * @param board 棋盘状态
     * @param player 玩家标识符
     * @return 该玩家的总得分
     */
    private int evaluateLines(Board board, Player player) {
        int totalScore = 0;
        int size = board.getSize();

        // 水平方向
        for (int row = 0; row < size; row++) {
            for (int col = 0; col <= size - 5; col++) {
                totalScore += evaluateLine(board, row, col, 0, 1, player);
            }
        }

        // 垂直方向
        for (int col = 0; col < size; col++) {
            for (int row = 0; row <= size - 5; row++) {
                totalScore += evaluateLine(board, row, col, 1, 0, player);
            }
        }

        // 左上到右下的对角线方向
        for (int row = 0; row <= size - 5; row++) {
            for (int col = 0; col <= size - 5; col++) {
                totalScore += evaluateLine(board, row, col, 1, 1, player);
            }
        }

        // 右上到左下的对角线方向
        for (int row = 4; row < size; row++) {
            for (int col = 0; col <= size - 5; col++) {
                totalScore += evaluateLine(board, row, col, -1, 1, player);
            }
        }

        return totalScore;
    }

    /**
     * 评估一条线的得分
     *
     * @param board 棋盘状态
     * @param startRow 起始行坐标
     * @param startCol 起始列坐标
     * @param deltaRow 行方向增量
     * @param deltaCol 列方向增量
     * @param player 玩家标识符
     * @return 该线的得分
     */
    private int evaluateLine(Board board, int startRow, int startCol, int deltaRow, int deltaCol, Player player) {
        int count = 0;
        int emptyCount = 0;

        for (int i = 0; i < 5; i++) {
            int row = startRow + i * deltaRow;
            int col = startCol + i * deltaCol;
            Player currentPiece = board.getPiece(row, col);
            if (currentPiece == player) {
                count++;
            } else if (currentPiece == null) {
                emptyCount++;
            } else {
                return 0; // 如果这条线上有对方的棋子，则得分为0
            }
        }

        // 根据连珠的数量和空位的数量给予不同的权重
        if (count == 5) {
            return 100000; // 连成五子
        } else if (count == 4 && emptyCount == 1) {
            return 1000; // 四子一线且有一个空位
        } else if (count == 3 && emptyCount == 2) {
            return 100; // 三子一线且有两个空位
        } else if (count == 2 && emptyCount == 3) {
            return 10; // 两子一线且有三个空位
        } else if (count == 1 && emptyCount == 4) {
            return 1; // 一子一线且有四个空位
        }

        return 0;
    }
}



