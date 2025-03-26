package ai;

import enums.Player;
import model.Board;
import model.GameEvaluator;
import model.Move;

/**
 * 类 AlphaBetaPruning 在极大极小算法的基础上实现了Alpha-Beta剪枝优化。
 */
public class AlphaBetaPruning {
    private final GameEvaluator evaluator;

    /**
     * 构造方法，初始化评估函数
     */
    public AlphaBetaPruning() {
        this.evaluator = new GameEvaluator();
    }

    /**
     * 极大极小算法结合Alpha-Beta剪枝
     *
     * @param board   当前棋盘状态
     * @param depth   搜索深度
     * @param alpha   Alpha值
     * @param beta    Beta值
     * @param maximizing 是否是最大化层
     * @return 返回包含最佳走法和评估分数的对象
     */
    public MinimaxResult minimaxWithAlphaBeta(Board board, int depth, int alpha, int beta, boolean maximizing) {
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
                        int eval = minimaxWithAlphaBeta(board, depth - 1, alpha, beta, false).score;
                        board.removePiece(row, col);
                        if (eval > maxEval) {
                            maxEval = eval;
                            bestMove = new Move(row, col);
                        }
                        alpha = Math.max(alpha, eval);
                        //当前节点的beta值小于alpha值时，剪枝
                        if (beta <= alpha) {
                            break; // Alpha-Beta剪枝
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
                        int eval = minimaxWithAlphaBeta(board, depth - 1, alpha, beta, true).score;
                        board.removePiece(row, col);
                        if (eval < minEval) {
                            minEval = eval;
                            bestMove = new Move(row, col);
                        }
                        beta = Math.min(beta, eval);
                        if (beta <= alpha) {
                            break; // Alpha-Beta剪枝
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



