package view;

import controller.GameController;
import enums.Player;
import model.Board;
import model.Move;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 类 ChessBoardView 负责显示五子棋的棋盘，并处理用户的点击事件。
 */
public class ChessBoardView extends JPanel {
    private static final int CELL_SIZE = 40; // 每个格子的大小
    private static final int BOARD_SIZE = 15; // 棋盘大小
    private final Board board;
    private final GameController gameController;

    /**
     * 构造方法，初始化棋盘视图
     *
     * @param gameController 游戏控制器
     */
    public ChessBoardView(GameController gameController) {
        this.board = gameController.getBoard();
        this.gameController = gameController;
        setPreferredSize(new Dimension(BOARD_SIZE * CELL_SIZE, BOARD_SIZE * CELL_SIZE));
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (gameController.isGameOver()) {
                    JOptionPane.showMessageDialog(ChessBoardView.this, "游戏已经结束", "提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                int col = e.getX() / CELL_SIZE;
                int row = e.getY() / CELL_SIZE;

                try {
                    Move move = new Move(row, col);
                    boolean isGameOver = gameController.makeMove(move);
                    repaint();

                    if (isGameOver) {
                        JOptionPane.showMessageDialog(ChessBoardView.this, "玩家 " + gameController.getCurrentPlayer().opposite() + " 获胜！", "游戏结束", JOptionPane.INFORMATION_MESSAGE);
                        gameController.resetGame();
                        repaint();
                    } else if (gameController.getCurrentPlayer() == Player.COMPUTER_PLAYER) {
                        // 计算机下棋逻辑
                        gameController.computerMove();
                        repaint();
                    }
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(ChessBoardView.this, ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    /**
     * 绘制棋盘和棋子
     *
     * @param g 图形上下文
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        drawPieces(g);
    }

    /**
     * 绘制棋盘网格
     *
     * @param g 图形上下文
     */
    private void drawGrid(Graphics g) {
        g.setColor(Color.BLACK);
        for (int i = 0; i <= BOARD_SIZE; i++) {
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, BOARD_SIZE * CELL_SIZE);
            g.drawLine(0, i * CELL_SIZE, BOARD_SIZE * CELL_SIZE, i * CELL_SIZE);
        }
    }

    /**
     * 绘制棋子
     *
     * @param g 图形上下文
     */
    private void drawPieces(Graphics g) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Player piece = board.getPiece(row, col);
                if (piece != null) {
                    int x = col * CELL_SIZE + CELL_SIZE / 2;
                    int y = row * CELL_SIZE + CELL_SIZE / 2;
                    int radius = CELL_SIZE / 2 - 5;
                    g.setColor(piece == Player.HUMAN_PLAYER ? Color.BLACK : Color.RED);
                    g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
                }
            }
        }
    }
}



