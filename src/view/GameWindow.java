package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;

/**
 * 类 GameWindow 是五子棋游戏的主窗口。
 */
public class GameWindow extends JFrame {
    private final ChessBoardView chessBoardView;

    /**
     * 构造方法，初始化游戏窗口
     */
    public GameWindow() {
        setTitle("五子棋游戏");
        setSize(600, 650); // 设置窗口大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 居中显示窗口

        GameController gameController = new GameController();

        chessBoardView = new ChessBoardView(gameController);

        // 使用 BorderLayout 布局管理器
        setLayout(new BorderLayout());
        add(chessBoardView, BorderLayout.CENTER);
    }

    /**
     * 主方法，程序入口
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameWindow window = new GameWindow();
            window.setVisible(true);
        });
    }
}



