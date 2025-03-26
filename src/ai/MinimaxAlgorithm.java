package ai;

import enums.Player;
import model.Board;
import model.GameEvaluator;
import model.Move;

/**
 * 类 MinimaxAlgorithm 实现了五子棋游戏的基本极大极小算法。
 */
public class MinimaxAlgorithm {
    private final GameEvaluator evaluator;

    /**
     * 构造方法，初始化评估函数
     */
    public MinimaxAlgorithm() {
        this.evaluator = new GameEvaluator();
    }

    /**
     * 极大极小算法
     *
     * @param board   当前棋盘状态
     * @param depth   搜索深度
     * @param maximizing 是否是最大化层
     * @return 返回包含最佳走法和评估分数的对象
     */
    public MinimaxResult minimax(Board board, int depth, boolean maximizing) {
        // 找到最后一步的位置
        Move lastMove = findLastMove(board);
        if (lastMove == null) {
            return new MinimaxResult(null, evaluator.evaluate(board));
        }
        int lastRow = lastMove.getRow();
        int lastCol = lastMove.getCol();

        if (depth == 0 || board.isGameOver(lastRow, lastCol)) {
            return new MinimaxResult(null, evaluator.evaluate(board));
        }

        if (maximizing) {
            int maxEval = Integer.MIN_VALUE;
            Move bestMove = null;
            for (int row = 0; row < board.getSize(); row++) {
                for (int col = 0; col < board.getSize(); col++) {
                    if (board.getPiece(row, col) == null) {
                        board.placePiece(row, col, Player.COMPUTER_PLAYER);
                        int eval = minimax(board, depth - 1, false).score;
                        board.removePiece(row, col);
                        if (eval > maxEval) {
                            maxEval = eval;
                            bestMove = new Move(row, col);
                        }
                    }
                }
            }
            return new MinimaxResult(bestMove, maxEval);
        } else {
            int minEval = Integer.MAX_VALUE;
            Move bestMove = null;
            for (int row = 0; row < board.getSize(); row++) {
                for (int col = 0; col < board.getSize(); col++) {
                    if (board.getPiece(row, col) == null) {
                        board.placePiece(row, col, Player.HUMAN_PLAYER);
                        int eval = minimax(board, depth - 1, true).score;
                        board.removePiece(row, col);
                        if (eval < minEval) {
                            minEval = eval;
                            bestMove = new Move(row, col);
                        }
                    }
                }
            }
            return new MinimaxResult(bestMove, minEval);
        }
    }

    /**
     * 查找最后一步的位置
     *
     * @param board 当前棋盘状态
     * @return 最后一步的位置，如果没有找到则返回 null
     */
    private Move findLastMove(Board board) {
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.getPiece(row, col) != null) {
                    return new Move(row, col);
                }
            }
        }
        return null;
    }

    /**
     * 内部类，用于存储最小极大算法的结果
     */
    public static class MinimaxResult {
        public Move move;
        public int score;

        public MinimaxResult(Move move, int score) {
            this.move = move;
            this.score = score;
        }
    }
}



