import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class homepage{

	static Point node[];						//IllegalAccessException-----Access to a class is denied
	
	homepage(){
	}
	
	homepage(Point[] nodelist){
		node = nodelist;
	}
	
	public static void main(String[] args) {
		//homepage hg=new homepage();
		//hg.display();
		//hg.display_node_locations();
	}
	
	public String[] names = new String[40];
	
	private String getCharForNumber(int i) {
		return i > 0 && i < 27 ? String.valueOf((char)(i + 64)) : null;
	}
	
	public void display_node_locations(){
		Point a;
		String location, name;
		for(int i = 0; i<node.length; i++){
			if(node[i]!=null){
				name = getCharForNumber(i+1);
				name = name.toLowerCase();
				names[i] = name;
				location = node[i].toString();
				System.out.println("\n "+name+" = "+names[i]+" = "+location+"\n");
			}
		}
		System.out.println("\n 123fdfsdgfdsgfdg\n");
		
	}
	
	public void display(String[] nameslist){						//IllegalArgumentException
	JFrame frame = new JFrame("A Simple GUI");
    frame.setVisible(true);
    //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500, 500);
    frame.setLocation(430, 100);

    JPanel panel = new JPanel();

    frame.add(panel);

    JLabel lbl = new JLabel("Select one of the possible choices and click OK");
    lbl.setVisible(true);

    panel.add(lbl);

	//JavaProject parent = new JavaProject();
	//GraphCanvas gc = new GraphCanvas(parent);
	
	//String[] choices = { "CHOICE 1","CHOICE 2", "CHOICE 3","CHOICE 4","CHOICE 5","CHOICE 6"};
	
	String[] choices = nameslist;
	
	
	
	//gc.display_names_homepage();
	
	//gc.display_node_locations();
	
	//String[] choices = gc.names;
	
	
    final JComboBox<String> cb = new JComboBox<String>(choices);

    cb.setVisible(true);
    panel.add(cb);
	
	String node_final;
	
	//String x = String.valueOf(cb.getSelectedItem());
	
	//System.out.println("\n"+x);

    JButton button = new JButton("Start Group Chatting!");
    panel.add(button);
	
	button.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
				String x = String.valueOf(cb.getSelectedItem());
                sample s= new sample();
				s.display(x);
            }
        });
		
}
}