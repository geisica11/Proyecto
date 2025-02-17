
package Controller;

import View.Login;

public class Main {
    
    public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            Login vista = new Login();
            new Controller(vista);
            vista.setVisible(true);
        }
    });
}
    
}
