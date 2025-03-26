import view.GameWindow;

import javax.swing.*;

/**
 * 类 Main 是五子棋游戏的主类，用于启动应用程序。
 */
public class Main {
    /**
     * 主方法，程序入口
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 使用 SwingUtilities.invokeLater 确保在事件调度线程上创建和显示 GUI 组件
        SwingUtilities.invokeLater(() -> {
            GameWindow window = new GameWindow();
            window.setVisible(true);
        });
    }
}



