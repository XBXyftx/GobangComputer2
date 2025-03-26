package view;

import controller.GameController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 类 ButtonPanel 提供了一些控制游戏的按钮，如重置游戏。
 */
public class ButtonPanel extends JPanel {
    private final GameController gameController;

    /**
     * 构造方法，初始化按钮面板
     *
     * @param gameController 游戏控制器
     */
    public ButtonPanel(GameController gameController) {
        this.gameController = gameController;
        initializeUI();
    }

    /**
     * 初始化用户界面组件
     */
    private void initializeUI() {
        setLayout(new FlowLayout());

        // 创建重置游戏按钮
        JButton resetButton = new JButton("重置游戏");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.resetGame();
                JOptionPane.showMessageDialog(ButtonPanel.this, "游戏已重置", "提示", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // 添加按钮到面板
        add(resetButton);
    }
}



