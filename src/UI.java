import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.plaf.metal.*;
import javax.swing.text.*;

class TextEditor extends JFrame implements ActionListener {
    JTextArea textArea; // Text component
    JFrame frameEditor; // Frame
    String textCache; // save last Text version
    // Constructor
    TextEditor(){
        // Create a frame
        frameEditor = new JFrame("Text Editor");
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel"); // Set metl look and feel
            MetalLookAndFeel.setCurrentTheme(new OceanTheme()); // Set theme to ocean
        }
        catch (Exception e) {
        }
        
        textArea = new JTextArea(); // Text component
        textArea.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e){	
                textCache = textArea.getText()+e.getKeyChar();
				System.out.println(textCache);
			}
			public void keyPressed(KeyEvent e){
			}
			public void keyReleased(KeyEvent e){
		    }			
		});
        
        JMenuBar mb = new JMenuBar(); // Create a menubar
        JMenu m1 = new JMenu("File"); // Create amenu for menu

        // Create menu items
        JMenuItem mi1 = new JMenuItem("New");
        JMenuItem mi2 = new JMenuItem("Open");
        JMenuItem mi3 = new JMenuItem("Save");
        JMenuItem mi9 = new JMenuItem("Print");

        // Add action listener
        mi1.addActionListener(this);
        mi2.addActionListener(this);
        mi3.addActionListener(this);
        mi9.addActionListener(this);

        m1.add(mi1);
        m1.add(mi2);
        m1.add(mi3);
        m1.add(mi9);

        // Create amenu for menu
        JMenu m2 = new JMenu("Edit");

        // Create menu items
        JMenuItem mi4 = new JMenuItem("cut");
        JMenuItem mi5 = new JMenuItem("copy");
        JMenuItem mi6 = new JMenuItem("paste");

        // Add action listener
        mi4.addActionListener(this);
        mi5.addActionListener(this);
        mi6.addActionListener(this);

        m2.add(mi4);
        m2.add(mi5);
        m2.add(mi6);

        JMenuItem mc = new JMenuItem("close");

        mc.addActionListener(this);

        mb.add(m1);
        mb.add(m2);
        mb.add(mc);

        frameEditor.setJMenuBar(mb);
        frameEditor.add(textArea);
        frameEditor.setSize(500, 500);
        frameEditor.show();
    }

	private void actionCancelled(){
		JOptionPane.showMessageDialog(frameEditor, "Cancelled !!");
	}

    // If a button is pressed
    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();
		System.out.println(s);
        if (s.equals("cut")) {
            textArea.cut();
        }
        else if (s.equals("copy")) {
            textArea.copy();
        }
        else if (s.equals("paste")) {
            textArea.paste();
        }
        else if (s.equals("Save")) {
            // Create an object of JFileChooser class
            JFileChooser j = new JFileChooser("f:");

            // Invoke the showsSaveDialog function to show the save dialog
            int r = j.showSaveDialog(null);

            if (r == JFileChooser.APPROVE_OPTION) {

                // Set the label to the path of the selected directory
                File fi = new File(j.getSelectedFile().getAbsolutePath());

                try {
                    // Create a file writer
                    FileWriter wr = new FileWriter(fi, false);

                    // Create buffered writer to write
                    BufferedWriter w = new BufferedWriter(wr);

                    // Write
                    w.write(textArea.getText());

                    w.flush();
                    w.close();
                }
                catch (Exception evt) {
                    JOptionPane.showMessageDialog(frameEditor, evt.getMessage());
                }
            }
            // If the user cancelled the operation
            else
                actionCancelled();
        }
        else if (s.equals("Print")) {
            try {
                // print the file
                textArea.print();
            }
            catch (Exception evt) {
                JOptionPane.showMessageDialog(frameEditor, evt.getMessage());
            }
        }
        else if (s.equals("Open")) {
            // Create an object of JFileChooser class
            JFileChooser j = new JFileChooser("f:");

            // Invoke the showsOpenDialog function to show the save dialog
            int r = j.showOpenDialog(null);

            // If the user selects a file
            if (r == JFileChooser.APPROVE_OPTION) {
                // Set the label to the path of the selected directory
                File fi = new File(j.getSelectedFile().getAbsolutePath());

                try {
                    String s1 = "", sl = ""; // String
                    FileReader fr = new FileReader(fi); // File reader
                    BufferedReader br = new BufferedReader(fr); // Buffered reader
                    sl = br.readLine(); // Initilize sl

                    // Take the input from the file
                    while ((s1 = br.readLine()) != null) {
                        sl = sl + "\n" + s1;
                    }
                    textArea.setText(sl); // Set text from input
                }
                catch (Exception evt) {
                    JOptionPane.showMessageDialog(frameEditor, evt.getMessage());
                }
            }
            // If the user cancelled the operation
            else
                actionCancelled();
        }
        else if (s.equals("New")) {
            textArea.setText("");
        }
        else if (s.equals("close")) {
            frameEditor.setVisible(false);
        }
    }

    // Main class
    public static void main(String args[]){
        TextEditor editor = new TextEditor();
        

    }
}
