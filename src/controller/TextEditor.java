package controller;

import model.Character;
import org.apache.log4j.BasicConfigurator;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.UnknownHostException;

class TextEditor extends JFrame implements ActionListener {
    JTextArea textArea; // Text component
    JFrame frameEditor; // Frame
    String textCache = ""; // save last Text version
    // Constructor
    TextEditor() throws UnknownHostException {
    
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

            }
            public void keyPressed(KeyEvent e){ //Not used
                if (e.getExtendedKeyCode()!=8 && (java.lang.Character.isAlphabetic(e.getKeyChar()) || java.lang.Character.isWhitespace(e.getKeyChar()))) {
                    try {
                        Controller.insert(textArea.getCaretPosition(), e.getKeyChar());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    System.out.println("INSERT " + e.getKeyChar() + " on " + textArea.getCaretPosition());
                }
//                System.out.println(e.getExtendedKeyCode());
            }
            public void keyReleased(KeyEvent e){ // Not used
                if (e.getExtendedKeyCode()==8 && !textArea.getText().equals(textCache)) {
                    try {
                        Controller.delete(textArea.getCaretPosition());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    System.out.println("DEL " + textArea.getCaretPosition());
                }
                textCache = textArea.getText();
                System.out.println(textCache);
            }
		});
        
        frameEditor.add(textArea);
        frameEditor.setSize(500, 500);
        frameEditor.show();
    }

    public void insert(Character c, Integer idx) {
        textArea.insert(String.valueOf(c.getValue()), idx);
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
        else if (s.equals("Exit")) {
            frameEditor.setVisible(false);
        }
    }

    // Main class
    public static void main(String args[]){
    BasicConfigurator.configure();
        try {
            TextEditor editor = new TextEditor();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
