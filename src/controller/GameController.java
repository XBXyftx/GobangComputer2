package controller;


import ai.AlphaBetaPruning;
import enums.Player;
import model.Board;
import model.Move;

/**
 * 类 GameController 负责处理五子棋游戏的逻辑和控制。
 * 包括处理用户输入、更新棋盘状态、判断游戏结束等。
 */
public class GameController {
    public static final int MAX_MIN_DEPTH = 3;
    private final Board board;
    private Player currentPlayer;
    private boolean gameOver;
    private final AlphaBetaPruning alphaBetaPruning;

    /**
     * 构造方法，初始化游戏控制器
     */
    public GameController() {
        this.board = Board.getInstance();
        this.currentPlayer = Player.HUMAN_PLAYER;
        this.gameOver = false;
        this.alphaBetaPruning = new AlphaBetaPruning();
    }

    /**
     * 处理玩家的走法
     *
     * @param move 玩家的走法
     * @return 如果游戏结束返回 true，否则返回 false
     */
    public boolean makeMove(Move move) {
        if (gameOver) {
            throw new IllegalStateException("游戏已经结束");
        }
        int row = move.getRow();
        int col = move.getCol();

        // 检查位置是否有效且为空
        if (board.getPiece(row, col) != null) {
            throw new IllegalArgumentException("该位置已经被占");
        }

        // 放置棋子
        board.placePiece(row, col, currentPlayer);

        // 检查游戏是否结束
        if (board.isGameOver(row, col)) {
            gameOver = true;
            return true;
        }

        // 切换当前玩家
        currentPlayer = currentPlayer.opposite();
        return false;
    }

    /**
     * 获取当前玩家
     *
     * @return 当前玩家
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * 获取棋盘实例
     *
     * @return 棋盘实例
     */
    public Board getBoard() {
        return board;
    }

    /**
     * 判断游戏是否结束
     *
     * @return 如果游戏结束返回 true，否则返回 false
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * 计算机下棋逻辑，使用极大极小算法和Alpha-Beta剪枝
     */
    public void computerMove() {
        AlphaBetaPruning.MinimaxResult result = alphaBetaPruning.minimaxWithAlphaBeta(board, MAX_MIN_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        Move bestMove = result.move;
        if (bestMove != null) {
            makeMove(bestMove);
        } else {
            // 如果没有找到最佳走法，随机选择一个空位
            for (int row = 0; row < board.getSize(); row++) {
                for (int col = 0; col < board.getSize(); col++) {
                    if (board.getPiece(row, col) == null) {
                        Move move = new Move(row, col);
                        makeMove(move);
                        return;
                    }
                }
            }
        }
    }
}



