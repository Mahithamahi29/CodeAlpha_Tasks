import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Student {
    String name;
    int marks;

    Student(String name, int marks) {
        this.name = name;
        this.marks = marks;
    }
}

public class StudentGradeTrackerGUI extends JFrame implements ActionListener {

    JTextField nameField, marksField;
    JTextArea displayArea;
    JButton addButton, reportButton, clearButton;

    ArrayList<Student> students = new ArrayList<>();

    public StudentGradeTrackerGUI() {
        setTitle("Student Grade Tracker");
        setSize(400, 400);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Labels and text fields
        add(new JLabel("Student Name:"));
        nameField = new JTextField(15);
        add(nameField);

        add(new JLabel("Marks:"));
        marksField = new JTextField(5);
        add(marksField);

        // Buttons
        addButton = new JButton("Add Student");
        reportButton = new JButton("Show Report");
        clearButton = new JButton("Clear");

        add(addButton);
        add(reportButton);
        add(clearButton);

        // Text area
        displayArea = new JTextArea(15, 30);
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea));

        // Button actions
        addButton.addActionListener(this);
        reportButton.addActionListener(this);
        clearButton.addActionListener(this);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == addButton) {
            String name = nameField.getText();
            int marks;

            try {
                marks = Integer.parseInt(marksField.getText());
                students.add(new Student(name, marks));
                displayArea.setText("Student added successfully!\n");
                nameField.setText("");
                marksField.setText("");
            } catch (Exception ex) {
                displayArea.setText("Please enter valid marks!\n");
            }
        }

        if (e.getSource() == reportButton) {
            if (students.isEmpty()) {
                displayArea.setText("No student data available.\n");
                return;
            }

            int total = 0;
            int highest = students.get(0).marks;
            int lowest = students.get(0).marks;

            displayArea.setText("----- STUDENT REPORT -----\n");
            displayArea.append("Name\tMarks\n");

            for (Student s : students) {
                displayArea.append(s.name + "\t" + s.marks + "\n");
                total += s.marks;
                if (s.marks > highest) highest = s.marks;
                if (s.marks < lowest) lowest = s.marks;
            }

            double average = (double) total / students.size();

            displayArea.append("\nTotal Students: " + students.size());
            displayArea.append("\nAverage Marks: " + average);
            displayArea.append("\nHighest Marks: " + highest);
            displayArea.append("\nLowest Marks: " + lowest);
        }

        if (e.getSource() == clearButton) {
            displayArea.setText("");
        }
    }

    public static void main(String[] args) {
        new StudentGradeTrackerGUI();
    }
}
