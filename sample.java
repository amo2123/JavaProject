import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class sample {

    String      name     = "Chat Application - Advanced Java Project";
    JFrame      f    = new JFrame(name);
    JButton     sendMessage;
    JTextField  messageBox;
    JTextArea   chatBox;

    public static void main(String[] args) {
    }

    public void display(String name) {
		String node_name = name;
		username = node_name;
        JPanel mPanel = new JPanel();
        mPanel.setLayout(new BorderLayout());

        JPanel sPanel = new JPanel();
        sPanel.setBackground(Color.WHITE);
        sPanel.setLayout(new GridBagLayout());

        messageBox = new JTextField(30);
        messageBox.requestFocusInWindow();

        sendMessage = new JButton("Send Message");
        sendMessage.addActionListener(new sendMessageButtonListener());

        chatBox = new JTextArea();
        chatBox.setEditable(false);
        chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
        chatBox.setLineWrap(true);

        mPanel.add(new JScrollPane(chatBox), BorderLayout.CENTER);

        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.LINE_START;
        left.fill = GridBagConstraints.HORIZONTAL;
        left.weightx = 512.0D;
        left.weighty = 1.0D;

        GridBagConstraints right = new GridBagConstraints();
        right.insets = new Insets(0, 10, 0, 0);
        right.anchor = GridBagConstraints.LINE_END;
        right.fill = GridBagConstraints.NONE;
        right.weightx = 1.0D;
        right.weighty = 1.0D;

        sPanel.add(messageBox, left);
        sPanel.add(sendMessage, right);

        mPanel.add(BorderLayout.SOUTH, sPanel);

        f.add(mPanel);
        f.setSize(470, 300);
        f.setVisible(true);
    }

    class sendMessageButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (messageBox.getText().length() < 1) {
				
            } else if (messageBox.getText().equals(".clear")) {
                chatBox.setText("Cleared all messages\n");
                messageBox.setText("");
            } else {
                chatBox.append("<" + username + ">:  " + messageBox.getText()
                        + "\n");
                messageBox.setText("");
            }
            messageBox.requestFocusInWindow();
        }
    }

    String  username;
}
	