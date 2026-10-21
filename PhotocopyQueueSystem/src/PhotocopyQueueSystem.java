import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class PhotocopyQueueSystem extends JFrame {

    // Represents one photocopying request
    static class CopyRequest {
        String studentName;
        int pages;

        CopyRequest(String studentName, int pages) {
            this.studentName = studentName;
            this.pages = pages;
        }

        @Override
        public String toString() {
            return studentName + " - " + pages + " pages";
        }
    }

    // Queue data structure
    private final Queue<CopyRequest> requestQueue = new LinkedList<>();

    private final JTextField nameField = new JTextField(15);
    private final JTextField pagesField = new JTextField(15);
    private final JTextArea queueDisplay = new JTextArea(12, 35);
    private final JLabel statusLabel = new JLabel("Waiting requests: 0");

    public PhotocopyQueueSystem() {
        setTitle("University Photocopying Queue Management System");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Application heading
        JLabel heading = new JLabel(
                "UNIVERSITY PHOTOCOPYING QUEUE SYSTEM",
                SwingConstants.CENTER
        );
        heading.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(heading, BorderLayout.NORTH);

        // Input fields
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        inputPanel.add(new JLabel("Student Name:"));
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Number of Pages:"));
        inputPanel.add(pagesField);

        // Buttons
        JButton addButton = new JButton("Add Request");
        JButton processButton = new JButton("Process Next");
        JButton viewButton = new JButton("View Queue");
        JButton clearButton = new JButton("Clear Fields");

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        buttonPanel.add(addButton);
        buttonPanel.add(processButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(clearButton);

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.add(inputPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.CENTER);

        // Queue display
        queueDisplay.setEditable(false);
        queueDisplay.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(queueDisplay);
        scrollPane.setBorder(
                BorderFactory.createTitledBorder("Photocopying Requests")
        );

        // Bottom status
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(statusLabel, BorderLayout.WEST);
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));

        centerPanel.add(topPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(heading, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Add request button
        addButton.addActionListener(e -> addRequest());

        // Process next request button
        processButton.addActionListener(e -> processRequest());

        // View queue button
        viewButton.addActionListener(e -> displayQueue());

        // Clear input fields button
        clearButton.addActionListener(e -> {
            nameField.setText("");
            pagesField.setText("");
            nameField.requestFocus();
        });

        setContentPane(mainPanel);

        displayQueue();
    }

    private void addRequest() {
        String name = nameField.getText().trim();
        String pagesText = pagesField.getText().trim();

        if (name.isEmpty() || pagesText.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the student's name and number of pages."
            );
            return;
        }
try {
int pages = Integer.parseInt(pagesText);

            if (pages <= 0) {
        JOptionPane.showMessageDialog(
                        this,
                                "The number of pages must be greater than zero."
);
                return;
                        }

CopyRequest request = new CopyRequest(name, pages);

// ENQUEUE: Add request to the rear
            requestQueue.offer(request);

            JOptionPane.showMessageDialog(
                    this,
                            "Request added successfully for " + name + "."
);

            nameField.setText("");
            pagesField.setText("");

displayQueue();

        } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(
                    this,
                            "Please enter a valid whole number for the pages."
);
        }
                }

private void processRequest() {

    // DEQUEUE: Remove request from the front
    CopyRequest request = requestQueue.poll();

    if (request == null) {
        JOptionPane.showMessageDialog(
                this,
                "There are no requests waiting to be processed."
        );
        return;
    }

    JOptionPane.showMessageDialog(
            this,
            "Processing photocopying request:\n\n" +
                    "Student: " + request.studentName +
                    "\nPages: " + request.pages
    );

    displayQueue();
}

private void displayQueue() {
    StringBuilder display = new StringBuilder();

    if (requestQueue.isEmpty()) {
        display.append("No photocopying requests are waiting.\n");
    } else {
        display.append("Requests are processed in FIFO order:\n\n");

        int position = 1;

        for (CopyRequest request : requestQueue) {
            display.append(position++)
                    .append(". ")
                    .append(request)
                    .append("\n");
        }

        CopyRequest next = requestQueue.peek();

        display.append("\nNext student to be served: ")
                .append(next.studentName);
    }

    queueDisplay.setText(display.toString());
    statusLabel.setText(
            "Waiting requests: " + requestQueue.size()
    );
}

public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        PhotocopyQueueSystem app = new PhotocopyQueueSystem();
        app.setVisible(true);
    });
}
}