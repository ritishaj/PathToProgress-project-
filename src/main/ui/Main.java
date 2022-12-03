package ui;

import java.io.FileNotFoundException;

// initiates the GUI program
public class Main {
    public static void main(String[] args) {
        try {
            new PathToProgressUI();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }

    }
}
