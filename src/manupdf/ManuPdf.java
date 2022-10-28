package manupdf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

class ManuPdf {

	File primo;
	File secondo;
	Path path;
	JFileChooser file;
	JMenuItem open, save, reset, merge, split;
	public static void main(String ...strings) throws IOException {
		
		ManuPdf application = new ManuPdf();

		
//	       //Schedule a job for the event-dispatching thread:
//        //creating and showing this application's GUI.
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                createAndShowGUI();
//            }
//        });
		
		application.createAndShowGUI();
	}
	
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("TopLevelDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create the menu bar.  Make it have a green background.
        JMenuBar greenMenuBar = new JMenuBar();
        greenMenuBar.setOpaque(true);
        greenMenuBar.setBackground(new Color(154, 165, 127));
        greenMenuBar.setPreferredSize(new Dimension(200, 20));

        file = new JFileChooser();
        JMenu menu = new JMenu("File");
        open = new JMenuItem("Open...");
        open.addActionListener(new SelectPdf());
        menu.add(open);
        save = new JMenuItem("Save...");
        save.addActionListener(new SelectPdf());
        menu.add(save);
        merge = new JMenuItem("Merge");
        merge.addActionListener(new SelectPdf());
        menu.add(merge);
        split = new JMenuItem("Split");
        split.addActionListener(new SelectPdf());
        menu.add(split);
        reset = new JMenuItem("--reset--");
        reset.addActionListener(new SelectPdf());
        menu.add(reset);
        greenMenuBar.add(menu);
        //Create a yellow label to put in the content pane.
        JLabel yellowLabel = new JLabel();
        yellowLabel.setOpaque(true);
        yellowLabel.setBackground(new Color(248, 213, 131));
        yellowLabel.setPreferredSize(new Dimension(200, 180));

        //Set the menu bar and add the label to the content pane.
        frame.setJMenuBar(greenMenuBar);
        frame.getContentPane().add(yellowLabel, BorderLayout.CENTER);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    private class SelectPdf implements ActionListener {
    	Component parent;
    	
//    	public SelectPdf(Component pa) {
//    		parent = pa;
//    	}
		/**
		 *
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			parent = (Component)e.getSource();
			if(e.getSource() == open) {
				
				int returnValue = file.showOpenDialog(parent);
				if(returnValue == JFileChooser.APPROVE_OPTION) {
					if(primo == null) {
						primo = file.getSelectedFile();
						path = primo.toPath().getParent();
					} else if (secondo == null) {
						secondo = file.getSelectedFile();
						
						
					}
					e.getActionCommand();
				}
			} else if(e.getSource() == save) {
				int returnValue = file.showSaveDialog(parent);
				if(returnValue == JFileChooser.APPROVE_OPTION) {
					File out = file.getSelectedFile();
					//create a pdf doc
					PDDocument doc1;
					PDDocument doc2;
					PDDocument docOut = new PDDocument();
					try {
						doc1 = PDDocument.load(primo);
						doc2 = PDDocument.load(secondo);
						
						docOut.addPage(doc1.getPage(1));
						docOut.addPage(doc2.getPage(0));
						docOut.save(out);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} else if(e.getSource() == reset) {
				primo = null;
				secondo = null;
			} else if(e.getSource() == split) {
				Splitter splitter = new Splitter();
				try {
					long start = System.currentTimeMillis();
					List<PDDocument> s = splitter.split(PDDocument.load(primo));
					long end = System.currentTimeMillis();
					System.out.println("took_: " + (end - start));
					if(s != null) {
						for(PDDocument d: s) {
							
						}
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
		}
    	
    }
}
