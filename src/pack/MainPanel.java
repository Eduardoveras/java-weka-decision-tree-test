package pack;

import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import javax.swing.JList;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainPanel extends JPanel {


	private static final String ARFF_FILE_PATH = "iris.arff";

	public MainPanel() throws Exception {

		
		
		ArffLoader arffLoader = new ArffLoader();
		File datasetFile = new File(ARFF_FILE_PATH);
		arffLoader.setFile(datasetFile);
		Instances dataInstances = arffLoader.getDataSet();

		dataInstances.setClassIndex(dataInstances.numAttributes()-1);
		NaiveBayes nb = new NaiveBayes();
		nb.buildClassifier(dataInstances);
		System.out.println(nb.getCapabilities().toString());
		setLayout(new MigLayout("", "[1px][741px]", "[1px][][409px]"));
		
		SMO svm = new SMO();
		svm.buildClassifier(dataInstances);
		System.out.println(svm.getCapabilities().toString());
		
		
		String[] options = new String[4];
		options[0] = "-C"; options[1] = "0.11";
		options[2] = "-M"; options[3] = "3";
		J48 tree = new J48();
		tree.setOptions(options);
		tree.buildClassifier(dataInstances);
		System.out.println(tree.getCapabilities().toString());
		System.out.println(tree.graph());





		setMinimumSize(new Dimension(1000, 700));
		setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JScrollPane scrollPane = new JScrollPane();
		DefaultListModel<String> model = new DefaultListModel<>();
		JList<String> list = new JList<>( model );
		
		scrollPane.setViewportView(list);

		for(Instance inst : dataInstances){
			model.addElement("Instance:" + inst);
			// System.out.println("Instance:" + inst);
		}
		JTextPane lblToBeFilled = new JTextPane();
		lblToBeFilled.setText(nb.getCapabilities().toString());
		lblToBeFilled.setText(lblToBeFilled.getText() + svm.getCapabilities().toString());

		add(lblToBeFilled, "cell 1 1,grow");
		add(scrollPane, "cell 1 2,grow");
		
		JButton btnDoSomething = new JButton("View Decision Tree");
		btnDoSomething.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					showResultTree(tree);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		scrollPane.setRowHeaderView(btnDoSomething);
		

		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public void showResultTree(J48 cls) throws Exception
	{
		 final javax.swing.JFrame jf = 
			       new javax.swing.JFrame("Weka Classifier Tree Visualizer: J48");
			     jf.setSize(500,400);
			     jf.getContentPane().setLayout(new BorderLayout());
			     TreeVisualizer tv = new TreeVisualizer(null,
			         cls.graph(),
			         new PlaceNode2());
			     jf.getContentPane().add(tv, BorderLayout.CENTER);
			     jf.addWindowListener(new java.awt.event.WindowAdapter() {
			       public void windowClosing(java.awt.event.WindowEvent e) {
			         jf.dispose();
			       }
			     });
			 
			     jf.setVisible(true);
			     tv.fitToScreen();
	}
}
