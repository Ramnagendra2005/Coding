import java.awt.*;
import java.awt.event.*;

public class BasicAWTExample {
    public static void main(String[] args) {
        // Create a frame
        Frame frame = new Frame("AWT Example");

        // Create a button
        Button button = new Button("Click Me");

        // Add a click event listener to the button
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Print message to console when the button is clicked
                System.out.println("Button clicked!");
            }
        });

        // Set the layout of the frame to null (absolute positioning)
        frame.setLayout(new FlowLayout());

        // Add the button to the frame
        frame.add(button);

        // Set frame size
        frame.setSize(300, 200);

        // Make the frame visible
        frame.setVisible(true);

        // Close the frame when the user closes the window
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }
}
