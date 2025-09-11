package JTressette.view;


import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;

@SuppressWarnings("deprecation")
public class View extends JFrame implements Observer {
    
    public View() {
        super("JTressette");
    }

    @Override
    public void update(Observable o, Object arg) {
        
    }
}