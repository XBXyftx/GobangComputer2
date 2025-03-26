package model;

import enums.Player;
import model.Board;

/**
 * 类 GameEvaluator 负责评估棋盘的状态。
 */
public class GameEvaluator {
    private static final int[][] DIRECTIONS = {{0, 1}, {1, 1}, {1, 0}, {1, -1}};

    /**
     * 评估棋盘状态的价值
     *
     * @param board 当前棋盘状态
     * @return 评估分数，正值表示人类玩家有利，负值表示计算机玩家有利
     */
    public int evaluate(Board board) {
        int humanScore = calculateScore(board, Player.HUMAN_PLAYER);
        int computerScore = calculateScore(board, Player.COMPUTER_PLAYER);

        return computerScore - humanScore;
    }

    /**
     * 计算指定玩家的得分
     *
     * @param board   当前棋盘状态
     * @param player  指定玩家
     * @return 玩家的得分
     */
    private int calculateScore(Board board, Player player) {
        int score = 0;

        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.getPiece(row, col) == player) {
                    for (int[] direction : DIRECTIONS) {
                        score += evaluateDirection(board, row, col, direction, player);
                    }
                }
            }
        }

        return score;
    }

    /**
     * 在指定方向上评估连珠的数量和威胁
     *
     * @param board       当前棋盘状态
     * @param startRow    起始行
     * @param startCol    起始列
     * @param direction   方向数组
     * @param player      指定玩家
     * @return 方向上的评分
     */
    private int evaluateDirection(Board board, int startRow, int startCol, int[] direction, Player player) {
        int count = 0;
        int emptyCount = 0;
        int blockedCount = 0;

        int row = startRow;
        int col = startCol;

        // 向一个方向检查
        while (row >= 0 && row < board.getSize() && col >= 0 && col < board.getSize()) {
            if (board.getPiece(row, col) == player) {
                count++;
            } else if (board.getPiece(row, col) == null) {
                emptyCount++;
            } else {
                blockedCount++;
                break;
            }
            row += direction[0];
            col += direction[1];
        }

        // 反向检查
        row = startRow - direction[0];
        col = startCol - direction[1];

        while (row >= 0 && row < board.getSize() && col >= 0 && col < board.getSize()) {
            if (board.getPiece(row, col) == player) {
                count++;
            } else if (board.getPiece(row, col) == null) {
                emptyCount++;
            } else {
                blockedCount++;
                break;
            }
            row -= direction[0];
            col -= direction[1];
        }

        // 根据连珠数量和空位数计算得分
        if (count == 5) {
            return 1000000; // 连成五子
        } else if (count == 4 && emptyCount >= 1) {
            return 10000; // 四子一空
        } else if (count == 3 && emptyCount >= 2) {
            return 1000; // 三子两空
        } else if (count == 2 && emptyCount >= 3) {
            return 100; // 二子三空
        } else if (count == 1 && emptyCount >= 4) {
            return 10; // 一子四空
        }

        return 0;
    }
}



