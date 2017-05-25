import java.awt.*;
import java.util.*;
import java.applet.*;
import java.awt.event.*;
import javax.swing.*;

/*<applet code="JavaProject" width=700 height=700></applet>*/

public class JavaProject extends Applet  {
	  
	 
	 public Insets in() {
	return new Insets(20, 20, 20, 20);
    }


    GraphCanvas gc = new GraphCanvas(this);						
    Options op = new Options(this);   
    Documentation doc = new Documentation();

    public void init() {
	setLayout(new BorderLayout(20, 20));
	add("Center", gc);
	add("North", doc);
	add("East", op);
    }

   
    public void lock() {
	gc.lock();
	op.lock();
    } 

    public void unlock() {
	gc.unlock();
	op.unlock();
    } 

    public static void main(String arg[]){
        JavaProject g = new JavaProject();
		Frame f = new Frame("Advanced Java Project");
        g.init();
        f.add("Center", g);
        f.show();
    }
}


class Options extends Panel {
    Button b1 = new Button("clear");
  
	Button b4 = new Button("find mutual");
	Button b5 = new Button("display node locations");
	Button b6 = new Button("Chat");
    
	JavaProject parent;   
    boolean Locked=false;
  
    Options(JavaProject myparent) {
	parent = myparent;
	setLayout(new GridLayout(6, 1, 0, 10));
	add(b1);
	
	add(b4);
	add(b5);
	add(b6);
    }

    public boolean action(Event evt, Object arg) {
	if (evt.target instanceof Button) {
		
	  if (((String)arg).equals("clear")) { 
	     parent.gc.clear();
	     parent.doc.doctext.showline("cleared all items");
	  }
	 
	  if(((String)arg).equals("find mutual")){
		  if (!Locked)
			  parent.gc.find_mutual();
		  else parent.doc.doctext.showline("locked");
	  }
	  if(((String)arg).equals("display node locations")){
		  if (!Locked)
			  parent.gc.display_node_locations();
		  else parent.doc.doctext.showline("locked");
	  }
	  if(((String)arg).equals("Chat")){
		  if (!Locked)
			  parent.gc.node_chat();
		  else parent.doc.doctext.showline("locked");
	  }
	}                   
	return true; 
    }
    
    public void lock() {
	Locked=true;
    }

    public void unlock() {
	Locked=false;
    } 
}    

class GraphCanvas extends Canvas implements Runnable{
    final int maxnodes = 20;
    final int m = maxnodes+1;
    final int node_size = 26;
    final int node_radius = 13;
 
    Point node[] = new Point[m];          
    int weight[][] = new int[m][m];     
    Point arrow[][] = new Point[m][m];  
    Point startp[][] = new Point[m][m]; 
    Point endp[][] = new Point[m][m];   
    float dir_x[][] = new float[m][m];  
    float dir_y[][] = new float[m][m];  
    
    Color colornode[] = new Color[m];
   
    int numnodes=0;      
    int emptyspots=0;    
    int startgraph=0;    
    int hitnode;         
    int node1, node2;    

    Point thispoint=new Point(0,0); 
    Point oldpoint=new Point(0, 0); 
    
    boolean newarrow = false;
    boolean movearrow = false;
    boolean movestart = false;
    boolean deletenode = false;
    boolean movenode = false;
   
    boolean clicked = false;
	String msg=" ADVANCED JAVA PROJECT";
	
    Font roman= new Font("TimesRoman", Font.BOLD, 12);
    Font helvetica= new Font("Arial", Font.BOLD, 15);
    FontMetrics fmetrics = getFontMetrics(roman);
    int h = (int)fmetrics.getHeight()/3;

    private Image osi;
    private Graphics osg;
    private Dimension oss;

	Thread t;   

    boolean Locked = false;

    JavaProject parent;

    GraphCanvas(JavaProject myparent) {
	parent = myparent;
	setBackground(Color.white);
    }

	public void node_chat(){
		homepage hg = new homepage(node);
		display_node_locations();
		hg.display(names);
		
	}
	public void run(){
		char ch;
		while(true){
			try{repaint();
			Thread.sleep(500);
			ch=msg.charAt(0);
			msg=msg.substring(1,msg.length());
			msg+=ch;
			
			}
			catch(InterruptedException e){
				
			}
		}
	}
	
	private String getCharForNumber(int i) {
		return i > 0 && i < 27 ? String.valueOf((char)(i + 64)) : null;
	}
	
	public String[] names = new String[m];
	
	public void display_node_locations(){
		Point a;
		String location, name;
		for(int i = 0; i<node.length; i++){
			if(node[i]!=null){
				name = getCharForNumber(i+1);
				name = name.toLowerCase();
				names[i] = name;
				location = node[i].toString();
				
				System.out.println("\n "+name+" = "+location+"\n");
			}
		}
	}
	
	
	public void find_mutual(){
		Scanner sc=new Scanner(System.in);
		 try{int a = sc.nextInt();
		int b = sc.nextInt();
		
		
		System.out.println("adjacent nodes for node0");
		int node11[]=new int[10];
		for(int i=0;i<weight[a].length;i++){
			if(weight[a][i]==1)
				node11[i]=i;
		}
		for(int i=0;i<node11.length;i++){
			if(node11[i]!=0){
				System.out.println(node11[i]);
			}
		}
		
		
		System.out.println("adjacent nodes for node1");
		int node22[]=new int[10];
		for(int j=0;j<weight[b].length;j++){
			if(weight[b][j]==1)
				node22[j]=j;
		}
		
		for(int i=0;i<node22.length;i++){
			if(node22[i]!=0){
				System.out.println(node22[i]);
			}
			
		}
		for(int i=0;i<node11.length;i++){
			for(int j=0;j<node22.length;j++){
				if(node11[i]!=0 && node22[j]!=0){
					if(node11[i]==node22[j]){
						System.out.println("mutual friends are:");
						System.out.println(node11[i]);}
					
				}
					
			}	
		}
		}
		catch(InputMismatchException e){
			System.out.println(e);
		}
	}
    
	public void lock() {
	Locked=true;
    }

    public void unlock() {
	Locked=false;
    }

    public void start() {
    }

    public void init() {
		t=new Thread(this);
		t.start();
	for (int i=0;i<maxnodes;i++) {
	  colornode[i]=Color.blue;
	}
	colornode[startgraph]=Color.blue;
	setBackground(Color.white); 
     setForeground(Color.white);
    }

    public void clear() {
	startgraph=0;
	numnodes=0;
	emptyspots=0;
	init();
	for(int i=0; i<maxnodes; i++) {
	  node[i] = null;
	  for (int j=0; j<maxnodes;j++)
	      weight[i][j]=0;
	}
	parent.unlock();
	repaint();
    }

    

	@Override
    public boolean mouseDown(Event evt, int x, int y) {
	
	if (Locked)
	    parent.doc.doctext.showline("locked");
	else {
	  clicked = true;
	  if (evt.shiftDown()) {
	     if (nodehit(x, y, node_size)) {
	        oldpoint = node[hitnode];
	        node1 = hitnode;
	        movenode=true;
	     }
	  }
	  else if (evt.controlDown()) {
	     if (nodehit(x, y, node_size)) {
	        node1 = hitnode;
	        if (startgraph==node1) {
	           movestart=true;
	           thispoint = new Point(x,y);
                   colornode[startgraph]=Color.blue;
	        }
	        else
	           deletenode= true;
	     }
	  }
	  else if (nodehit(x, y, node_size)) {
	     if (!newarrow) {
	        newarrow = true;
	        thispoint = new Point(x, y);
	        node1 = hitnode;
	     }
	   }
	   else if ( !nodehit(x, y, 1) && !arrowhit(x, y, 1) )  {
	      if (emptyspots==0) {
	         if (numnodes < maxnodes)
	            node[numnodes++]=new Point(x, y);
	         else  parent.doc.doctext.showline("maxnodes");
	      }
	      else {
	         int i;
	         for (i=0;i<numnodes;i++)
	            if (node[i].x==-100) break;
	         node[i]=new Point(x, y);
	         emptyspots--;
	      }
	   }
	   else
	      parent.doc.doctext.showline("toclose");
	   repaint();
	}
	return true;
    }

    public boolean mouseDrag(Event evt, int x, int y) {
	if ( (!Locked) && clicked ) {
	   if (movenode) {
	      node[node1]=new Point(x, y);
	      for (int i=0;i<numnodes;i++) {
	         if (weight[i][node1]>0) {
	            arrowupdate(i, node1, weight[i][node1]);
	         }
	         if (weight[node1][i]>0) {
	            arrowupdate(node1, i, weight[node1][i]);
	         }
	      }
	      repaint();
	   }
	   else if (movestart || newarrow) {
	      thispoint = new Point(x, y);
	      repaint();
	   }
	}
	return true;
    }


    public boolean mouseUp(Event evt, int x, int y) {
	if ( (!Locked) && clicked ) {
	   if (movenode) {
	      node[node1]=new Point(0, 0);
	      if ( nodehit(x, y, 1) || (x<0) || (x>this.size().width) || 
					  (y<0) || (y>this.size().height) ) {
	         node[node1]=oldpoint;
	         parent.doc.doctext.showline("toclose");
	      }
	      else node[node1]=new Point(x, y);
	      for (int i=0;i<numnodes;i++) {
	         if (weight[i][node1]>0)
	            arrowupdate(i, node1, weight[i][node1]);
	         if (weight[node1][i]>0) 
	            arrowupdate(node1, i, weight[node1][i]);
	      }
	      movenode=false;
	   }
	   else if (deletenode) {
	      nodedelete();
	      deletenode=false;
	   }
	   else if (newarrow) {
	      newarrow = false;
	      if (nodehit(x, y, node_size)) {
	         node2=hitnode;
	         if (node1!=node2) {
	            arrowupdate(node1, node2, 1);
	            /*if (weight[node2][node1]>0) {
	               arrowupdate(node2, node1, weight[node2][node1]);
	            }*/
	         }
	         else System.out.println("abcde");
	      }
	   }
	   else if (movearrow) {
	      movearrow = false;
	   }
	   else if (movestart) {
	      if (nodehit(x, y, node_size))
	         startgraph=hitnode;
	      colornode[startgraph]=Color.blue;
	      movestart=false;
	   }
	   repaint();
	}
	return true;
    }

    public boolean nodehit(int x, int y, int dist) {
	for (int i=0; i<numnodes; i++)
	  if ( (x-node[i].x)*(x-node[i].x) + 
				(y-node[i].y)*(y-node[i].y) < dist*dist ) {
	     hitnode = i;
	     return true;
	  }
	return false;
    }

    public boolean arrowhit(int x, int y, int dist) {
	for (int i=0; i<numnodes; i++)
	  for (int j=0; j<numnodes; j++) {
	     if ( ( weight[i][j]>0 ) && 
			(Math.pow(x-arrow[i][j].x, 2) + 
			 Math.pow(y-arrow[i][j].y, 2) < Math.pow(dist, 2) ) ) {
	        node1 = i;
	        node2 = j;
	        return true;
	     }
	  }
	return false;
    }

    public void nodedelete() {
	node[node1]=new Point(-100, -100);
	for (int j=0;j<numnodes;j++) {
	   weight[node1][j]=0;
	   weight[j][node1]=0;
	}
	emptyspots++;
    }

    public void arrowupdate(int p1, int p2, int w) {
	int dx, dy;
	float l;
	weight[p1][p2]=w;

	dx = node[p2].x-node[p1].x;
	dy = node[p2].y-node[p1].y;

	l = (float)( Math.sqrt((float)(dx*dx + dy*dy)));
	dir_x[p1][p2]=dx/l;
	dir_y[p1][p2]=dy/l;

	if (weight[p2][p1]>0) {
	   startp[p1][p2] = new Point((int)(node[p1].x-5*dir_y[p1][p2]), 
				      (int)(node[p1].y+5*dir_x[p1][p2]));
	   endp[p1][p2] = new Point((int)(node[p2].x-5*dir_y[p1][p2]), 
				    (int)(node[p2].y+5*dir_x[p1][p2]));
	}
	else {
	   startp[p1][p2] = new Point(node[p1].x, node[p1].y);
	   endp[p1][p2] = new Point(node[p2].x, node[p2].y);
	}

	int diff_x = (int)(Math.abs(20*dir_x[p1][p2]));
	int diff_y = (int)(Math.abs(20*dir_y[p1][p2]));

	if (startp[p1][p2].x>endp[p1][p2].x) {
	   arrow[p1][p2] = new Point(endp[p1][p2].x + diff_x +
		(Math.abs(endp[p1][p2].x-startp[p1][p2].x) - 2*diff_x )*
			(100-w)/100 , 0);
	}
	else {
	   arrow[p1][p2] = new Point(startp[p1][p2].x + diff_x +
		(Math.abs(endp[p1][p2].x-startp[p1][p2].x) - 2*diff_x )*
			w/100, 0);
	}

	if (startp[p1][p2].y>endp[p1][p2].y) {
	   arrow[p1][p2].y=endp[p1][p2].y + diff_y +
		(Math.abs(endp[p1][p2].y-startp[p1][p2].y) - 2*diff_y )*
			(100-w)/100;
	}
	else {
	   arrow[p1][p2].y=startp[p1][p2].y + diff_y +
		(Math.abs(endp[p1][p2].y-startp[p1][p2].y) - 2*diff_y )*
			w/100;
	}
    }


    public String intToString(int i) {
	char c=(char)((int)'a'+i);
	return ""+c;
    }

    public final synchronized void update(Graphics g) {
	Dimension d=size();
	if ((osi == null) || (d.width != oss.width) ||
			(d.height != oss.height)) {
	    osi = createImage(d.width, d.height);
	    oss = d;
	    osg = osi.getGraphics();
	}
	osg.setColor(Color.white);
	osg.fillRect(0, 0, d.width, d.height);
	paint(osg);
	g.drawImage(osi, 0, 0, null);
    }

    public void drawarrow(Graphics g, int i, int j) {
	int x1, x2, x3, y1, y2, y3;

	// calculate arrowhead
	x1= (int)(arrow[i][j].x - 3*dir_x[i][j] + 6*dir_y[i][j]);
	x2= (int)(arrow[i][j].x - 3*dir_x[i][j] - 6*dir_y[i][j]);
	x3= (int)(arrow[i][j].x + 6*dir_x[i][j]);

	y1= (int)(arrow[i][j].y - 3*dir_y[i][j] - 6*dir_x[i][j]);
	y2= (int)(arrow[i][j].y - 3*dir_y[i][j] + 6*dir_x[i][j]);
	y3= (int)(arrow[i][j].y + 6*dir_y[i][j]);

	int arrowhead_x[] = { x1, x2, x3, x1 };
	int arrowhead_y[] = { y1, y2, y3, y1 };
	
	g.drawLine(startp[i][j].x, startp[i][j].y, endp[i][j].x, endp[i][j].y);
	g.fillPolygon(arrowhead_x, arrowhead_y, 4);

	int dx = (int)(Math.abs(7*dir_y[i][j]));
	int dy = (int)(Math.abs(7*dir_x[i][j]));
	String str = new String("" + weight[i][j]);
	g.setColor(Color.blue);
	if ((startp[i][j].x>endp[i][j].x) && (startp[i][j].y>=endp[i][j].y))
	  g.drawString( str, arrow[i][j].x + dx, arrow[i][j].y - dy);
	if ((startp[i][j].x>=endp[i][j].x) && (startp[i][j].y<endp[i][j].y))
	  g.drawString( str, arrow[i][j].x - fmetrics.stringWidth(str) - dx , 
			     arrow[i][j].y - dy);
	if ((startp[i][j].x<endp[i][j].x) && (startp[i][j].y<=endp[i][j].y))
	  g.drawString( str, arrow[i][j].x - fmetrics.stringWidth(str) , 
			     arrow[i][j].y + fmetrics.getHeight());
	if ((startp[i][j].x<=endp[i][j].x) && (startp[i][j].y>endp[i][j].y))
	    g.drawString( str, arrow[i][j].x + dx, 
			       arrow[i][j].y + fmetrics.getHeight() );
    }


    public void paint(Graphics g) {
    g.setFont(roman);
	g.setColor(Color.blue);

	if (newarrow)
	  g.drawLine(node[node1].x, node[node1].y, thispoint.x, thispoint.y);

	for (int i=0; i<numnodes; i++)
	  for (int j=0; j<numnodes; j++)
	     if (weight [i][j]>0) {
	        drawarrow(g, i, j);
	     }

	if (movearrow && weight[node1][node2]==0) {
	  drawarrow(g, node1, node2);
	  g.drawLine(startp[node1][node2].x, startp[node1][node2].y, 
		     endp[node1][node2].x, endp[node1][node2].y);
	}

	for (int i=0; i<numnodes; i++)
	  if (node[i].x>0) {
	     g.setColor(colornode[i]);
	     g.fillOval(node[i].x-node_radius, node[i].y-node_radius, 
			node_size, node_size);
	  }
	  g.drawLine(0,425,700,425);  
	g.drawString(msg,250,480);
	g.setColor(Color.blue);
	g.setFont(new Font("TimesRoman", Font.PLAIN, 18)); 

	g.setColor(Color.blue);
	if (movestart)
	  g.fillOval(thispoint.x-node_radius, thispoint.y-node_radius, 
			node_size, node_size);


	g.setColor(Color.blue);

	g.setFont(helvetica);
	for (int i=0; i<numnodes; i++)
	  if (node[i].x>0) {
	     g.setColor(Color.blue);
	     g.drawOval(node[i].x-node_radius, node[i].y-node_radius, 
			node_size, node_size);
	     g.setColor(Color.blue);
	     g.drawString(intToString(i), node[i].x-14, node[i].y-14);
	  }
    }
}

class Documentation extends Panel {
    DocOptions docopt = new DocOptions(this);
    DocText doctext = new DocText();

    Documentation() {
	setLayout(new BorderLayout(10, 10));
	add("West", docopt);
	add("Center", doctext);
    }
}


class DocOptions extends Panel {
    Choice doc = new Choice();
    Documentation parent;   
   
    DocOptions(Documentation myparent) {
	setLayout(new GridLayout(2, 1, 5, 0));
	parent = myparent;
	add(new Label("DOCUMENTATION:"));
	doc.addItem("draw nodes");
	doc.addItem("remove nodes"); 
	doc.addItem("move nodes");
	doc.addItem("the startnode");
	doc.addItem("draw arrows");
	doc.addItem("remove arrows");
	doc.addItem("clear");
	
	add(doc);
    }
  
    public boolean action(Event evt, Object arg) {
        if (evt.target instanceof Choice) {
	    String str=new String(doc.getSelectedItem());
	    parent.doctext.showline(str);  
        }             
       	return true;
    }
}


class DocText extends TextArea {
    final String drawnodes = new String("DRAWING NODES:\n"+
	  "Draw a node by clicking the mouse.\n\n");
    final String rmvnodes = new String("REMOVE NODES:\n"+
	  "To remove a node press <ctrl> and click on the node.\n"+
	  "You can not remove the startnode.\n"+
	  "Select another startnode, then you can remove the node.\n\n");
    final String mvnodes = new String("MOVING NODES\n"+
	  "To move a node press <Shift>, click on the node,\nand drag it to"+
	  " its new position.\n\n");
    final String startnode = new String("STARTNODE:\n"+
	  "The startnode is blue"+ 
	  "The first node you draw on the screen will be the startnode.\n"+
	  "To select another startnode press <ctrl>, click on the startnode, "+
	  "and drag the mouse to another node.\n"+
	  "To delete the startnode, first select another startnode, and then"+
	  "remove the node the usual way.\n\n"); 
    final String drawarrows = new String("DRAWING ARROWS:\n"+
	  "To draw an arrow click mouse in a node,"+
	  "and drag it to another node.\n\n");
    final String rmvarrows = new String("REMOVE ARROWS:\n"+
	  "To remove an arrow, change its weight to 0.\n\n");
    final String clrreset = new String("<CLEAR> BUTTON: "+
	  "Remove the current graph from the screen.\n");
    
    final String toclose = new String("ERROR: "+
	  "This position is too close to another node/arrow.\n");
  
    final String maxnodes = new String("ERROR: "+ 
	  "Maximum number of nodes reached!\n\n");
    final String info = new String("DOCUMENTATION:\n"+
	  "You can scroll through the documentation or get documentation\n"+
	  "on a specific "); 
    final String locked = new String("ERROR: "+
	  "Keyboard/mouse locked for this action.\n"+
	  "Either press <NEXT STEP> or <RESET>.\n"); 
	
		final String doc = info + drawnodes + rmvnodes + mvnodes + 
		       startnode + drawarrows + rmvarrows + 											
		       clrreset ;
			   
	
    

    DocText() {
	super(5, 2);
	setText(doc);
    }
    
    public void showline(String str) {
	if (str.equals("draw nodes"))              setText(drawnodes);
	else if (str.equals("remove nodes"))       setText(rmvnodes);
	else if (str.equals("move nodes"))         setText(mvnodes);
	else if (str.equals("the startnode"))      setText(startnode);
	else if (str.equals("draw arrows"))        setText(drawarrows);
	else if (str.equals("remove arrows"))      setText(rmvarrows);
	else if (str.equals("clear"))      		   setText(clrreset);
   	else if (str.equals("toclose"))            setText(toclose);
	else if (str.equals("locked"))             setText(locked);
	else if (str.equals("maxnodes"))           setText(maxnodes);  
	else setText(str);
    }
}