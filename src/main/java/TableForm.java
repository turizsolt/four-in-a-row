package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class TableForm extends JFrame {
    private static final String MESSAGE_INVALID_DROP = "Invalid drop.";
    private static final String MESSAGE_PLAYER_ONE_WON = "Red WON!";
    private static final String MESSAGE_PLAYER_TWO_WON = "Blue WON!";
    private static final String MESSAGE_DRAW = "No winner!";
    private static final Color COLOR_PLAYER_ONE = Color.RED;
    private static final Color COLOR_PLAYER_TWO = Color.BLUE;

    private int tableWidth;
    private int tableHeight;
    private JButton[][] buttons;
    private Table table;

    TableForm(int _tableWidth, int _tableHeight) {
        tableWidth = _tableWidth;
        tableHeight = _tableHeight;
        table = new Table(tableWidth, tableHeight);
        initGui();
    }

    private void initGui() {
        Container pane = setGridLayout();
        createButtons(pane);
        setDefaultFormSettings();
    }

    private void setDefaultFormSettings() {
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
    }

    private Container setGridLayout() {
        Container pane = getContentPane();
        GridLayout gl = new GridLayout(0, tableWidth);
        pane.setLayout(gl);
        return pane;
    }

    private void createButtons(Container pane) {
        buttons = new JButton[tableHeight][tableWidth];

        for(int i = tableHeight -1; i > -1 ; i-- ) {
            for(int j = 0; j < tableWidth; j++ ) {
                buttons[i][j] = new JButton(i+":"+j);
                buttons[i][j].addActionListener(createActionListenerForButton(this, j));
                pane.add(buttons[i][j]);
            }
        }
    }

    private ActionListener createActionListenerForButton(JFrame form, int column) {
        return (ActionEvent e) -> {
            if(table.drop(column)) {
                showLastDrop();
                checkResult(form);
            } else {
                showMessage(form, MESSAGE_INVALID_DROP);
            }
        };
    }

    private void showMessage(JFrame form, String message) {
        JOptionPane.showMessageDialog(form, message);
    }

    private void checkResult(JFrame form) {
        int result = table.getResult();
        if(result > 0) {
            String[] messages = new String[] {
                null,
                MESSAGE_PLAYER_ONE_WON,
                MESSAGE_PLAYER_TWO_WON,
                MESSAGE_DRAW
            };

            showMessage(form, messages[result]);
            disableAllButtons();
        }
    }

    private void showLastDrop() {
        DropData lastDrop = table.getLastDrop();
        buttons[lastDrop.getX()][lastDrop.getY()].setBackground(
                lastDrop.getColor() == 1
                ? COLOR_PLAYER_ONE
                : COLOR_PLAYER_TWO
        );
    }

    private void disableAllButtons() {
        for(int i = tableHeight -1; i > -1 ; i-- ) {
            for(int j = 0; j < tableWidth; j++ ) {
                buttons[i][j].setEnabled(false);
            }
        }
    }
}
