package com.ruchi.engine.demo;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;

import opennlp.tools.util.Span;

import com.ruchi.engine.foodextraction.OpenNLP;
import com.ruchi.engine.models.Sentence;
import com.ruchi.engine.sentiment.TypedDependencyEngine;


public class FrameIntro {

	private JFrame frame;
	String[] output;
	int priviousCount=0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameIntro window = new FrameIntro();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FrameIntro() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		int foodNumber=4;
		
		TypedDependencyEngine typedDependencyEngine =new TypedDependencyEngine();
		ArrayList<StarRater> starRaters=new ArrayList<StarRater>();
		ArrayList<JLabel> lblNewLabels=new ArrayList<JLabel>();
		StarRater[] starRaters1=new StarRater[5];
		JLabel[] lblNewLabels1= new  JLabel[5];
		OpenNLP openNlp=new OpenNLP();
		openNlp.loadModel();
		
		frame.setBounds(100, 100, 450, 752);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 11, 414, 89);
		frame.getContentPane().add(textArea);
		
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), "Food Rating", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 164, 414, 121);
		panel.setLayout(null);
		
		frame.getContentPane().add(panel);
		
		JButton btnSubmit = new JButton("Submit");
		
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sen=textArea.getText();
				
				String[] tokens=openNlp.getTokens(sen);
				Sentence sentence=new Sentence(sen);
				typedDependencyEngine.foodSentiment(sentence);
				Span nameSpaces[]=openNlp.getNames(tokens);
				output=Span.spansToStrings(nameSpaces, tokens);
				System.out.println(output.length);
				
				int i=0; 
				
				for(String s:output)
				{
					if(lblNewLabels1[i]==null){
						starRaters1[i] = new StarRater(5, 2, 1);
						starRaters1[i].setBounds(276, 11+i*25, 80, 16);
						
						lblNewLabels1[i] = new JLabel();
						lblNewLabels1[i].setText(s);
						lblNewLabels1[i].setBounds(64, 13+i*25, 131, 14);
						System.out.println("########");
						
						
					}	
					
					else
					{
						
						(lblNewLabels1[i]).setText(s);
						
						//panel.add( lblNewLabels.get(i));
						//panel.add(starRaters.get(i));
					}
					System.out.println(lblNewLabels1[i].getText());
					panel.add( lblNewLabels1[i]);
					panel.add(starRaters1[i]);
					panel.revalidate();
	                panel.repaint();
					System.out.println("************");
					i++;
					
				}
				
				if(priviousCount>output.length)
				{
					for(int m=output.length;m<priviousCount;m++)
						(lblNewLabels1[m]).setText("");
				}
				priviousCount= output.length;
				//new TestOpenCloud().initUI(sen);
			}
		});
		btnSubmit.setBounds(335, 111, 89, 23);
		frame.getContentPane().add(btnSubmit);
		
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), "Sentiment", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 469, 414, 60);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblSentimentValue = new JLabel("Food names");
		lblSentimentValue.setBounds(10, 24, 394, 14);
		panel_1.add(lblSentimentValue);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), "Food Prediction", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(10, 540, 414, 76);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(10, 627, 414, 76);
		frame.getContentPane().add(panel_3);
		panel_3.setLayout(null);
		panel_3.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), "Dictionary Chunker", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), "After Post Processing", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_4.setBounds(10, 398, 414, 60);
		frame.getContentPane().add(panel_4);
		
		JLabel lblFoodNames = new JLabel("Food Names");
		lblFoodNames.setBounds(10, 24, 394, 14);
		panel_4.add(lblFoodNames);
		
		JPanel panel_5 = new JPanel();
		panel_5.setLayout(null);
		panel_5.setBorder(new TitledBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), "NER-pridiction", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_5.setBounds(10, 327, 414, 60);
		frame.getContentPane().add(panel_5);
		
		JLabel lblFoodNames_1 = new JLabel("Food Names");
		lblFoodNames_1.setBounds(10, 24, 394, 14);
		panel_5.add(lblFoodNames_1);
		
		
		
		
		//panel.setLayout(null);
	}
}
