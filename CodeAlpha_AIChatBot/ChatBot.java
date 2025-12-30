import java.io.*;
import java.util.*;

public class ChatBot {

    private Map<String, String> knowledgeBase = new HashMap<>();

    // Load FAQ training data
    public ChatBot() {
        loadFAQ();
    }

    private void loadFAQ() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("faq.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=");
                knowledgeBase.put(parts[0].toLowerCase(), parts[1]);
            }
            br.close();
        } catch (Exception e) {
            System.out.println("FAQ file not found!");
        }
    }

    // NLP Processing + Response
    public String getResponse(String userInput) {
        userInput = preprocess(userInput);

        for (String key : knowledgeBase.keySet()) {
            if (userInput.contains(key)) {
                return knowledgeBase.get(key);
            }
        }
        return "Sorry, I don't understand. Can you rephrase?";
    }

    // NLP Preprocessing
    private String preprocess(String input) {
        input = input.toLowerCase();
        input = input.replaceAll("[^a-zA-Z ]", "");
        return input.trim();
    }
}
