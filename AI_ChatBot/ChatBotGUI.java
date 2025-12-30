import javax.swing.*;
import java.awt.*;

public class ChatBotGUI extends JFrame {

    private JTextArea chatArea;
    private JTextField inputField;
    private ChatBot bot;

    public ChatBotGUI() {
        bot = new ChatBot();

        setTitle("AI Chatbot");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatArea = new JTextArea();
        chatArea.setEditable(false);

        inputField = new JTextField();
        JButton sendButton = new JButton("Send");

        JScrollPane scrollPane = new JScrollPane(chatArea);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());

        setVisible(true);
    }

    private void sendMessage() {
        String userText = inputField.getText();
        chatArea.append("You: " + userText + "\n");

        String response = bot.getResponse(userText);
        chatArea.append("Bot: " + response + "\n\n");

        inputField.setText("");
    }

    public static void main(String[] args) {
        new ChatBotGUI();
    }
}
