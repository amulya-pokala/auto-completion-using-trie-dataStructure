package wasdev.sample.servlet;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
class TrieNode {
    TrieNode[] arr;
    boolean isEnd;
    // Initialize your data structure here.
    public TrieNode() {
        this.arr = new TrieNode[26];
        for(int i=0;i<26;i++)
        {
        	this.arr[i]=null;
        }
    }
 
}
 
 class trie1 {
    private TrieNode root;
    char[] words;
    
    int count,k;
	//words=new String[1000];
	String[] w;
    public trie1() throws IOException {
    	//String[] words;
    	words=new char[100];
    	count=0;
    	k=0;
    	w=new String[109576];
    	root = new TrieNode();
    	try{
    		InputStream fis = new FileInputStream("dict.txt");
    		String line;
    	    InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
    	    BufferedReader br = new BufferedReader(isr);
    		
    	     while ((line = br.readLine()) != null) {
    	        // Do your thing with line
    	    	 insert(line);
    	    	 
    	    }
    		}
    	     catch(FileNotFoundException e)
    	     {
    	    	 
    	     }
    }
 
    // Inserts a word into the trie.
    public void insert(String word) {
   //     System.out.println("insert "+word);
    	TrieNode p = root;
        for(int i=0; i<word.length(); i++){
            char c = word.charAt(i);
            int index = c-'a';
            if(p.arr[index]==null){
                TrieNode temp = new TrieNode();
                p.arr[index]=temp;
                p = temp;
            }else{
                p=p.arr[index];
            }
        }
        p.isEnd=true;
    }
 
    // Returns if the word is in the trie.
    public boolean search(String word) {
        TrieNode p = searchNode(word);
        if(p==null){
            return false;
        }else{
            if(p.isEnd)
                return true;
        }
 
        return false;
    }
 
    
    public boolean startsWith(String prefix) {
        TrieNode p = searchNode(prefix);
        if(p==null){
            return false;
        }else{
            return true;
        }
    }
 
    public TrieNode searchNode(String s){
        TrieNode p = root;
        for(int i=0; i<s.length(); i++){
            char c= s.charAt(i);
            int index = c-'a';
            if(p.arr[index]!=null){
                p = p.arr[index];
            }else{
                return null;
            }
        }
 
        if(p==root)
            return null;
 
        return p;
    }
    public void printWords(TrieNode t, String s)
    {
    	if(t==null) return;
    	if(t.isEnd==true)
    	{
    		//System.out.printf("K IS : %d, WORDS is %s, S is %s\n", k, words, s);
    		if(k>=0)
    		w[count]=s+(String.valueOf(words)).substring(0, k);
    		count++;
    	}
    	for(int i=0;i<26;i++)
		{
    		if(t.arr[i]!=null)
    		{
    			words[k]=(char)('a'+i);
    			k++;
    			words[k]='\0';
    			printWords(t.arr[i],s);
    			
    			
    		}
		}
    	k--;
    }
    public int wordsWithSamePrefix(String s)
    {
    	//w=new String[109575];
    
    	TrieNode p=root;
    	for(int i=0;i<s.length();i++)
    	{
    		char c=s.charAt(i);
    		int index=c-'a';
    		if(p.arr[index]!=null)
    			{
    				p=p.arr[index];
    			}
    		else return 0;
    	}
    	printWords(p,s);
    	return 1;
    }
 }

 public class Trie2 {
		
	 			static trie1 i;		 
	 			public static void main(String[] args) throws Exception {
				 	
			        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			        JFrame frame = new JFrame();
			        frame.setTitle("AUTO COMPLETION");
			        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			        frame.setBounds(200, 200, 500, 400);

			       JTextField txtInput = new JTextField();
			        String y=txtInput.getText();
			         i =new trie1();
			        i.wordsWithSamePrefix(y);
			        ArrayList<String> items = new ArrayList<String>();
			        for(int j=0;j<(i.w).length;j++)
			        {
			        	String item = i.w[j];
			        	items.add(item);
			        }
			        setupAutoComplete(txtInput, items);
			        txtInput.setColumns(30);
			        frame.getContentPane().setLayout(new FlowLayout());
			        frame.getContentPane().add(txtInput, BorderLayout.NORTH);
			        frame.setVisible(true);
			    }

			    private static boolean isAdjusting(JComboBox cbInput) {
			        if (cbInput.getClientProperty("is_adjusting") instanceof Boolean) {
			            return (Boolean) cbInput.getClientProperty("is_adjusting");
			        }
			        return false;
			    }

			    private static void setAdjusting(JComboBox cbInput, boolean adjusting) {
			        cbInput.putClientProperty("is_adjusting", adjusting);
			    }

			    public static void setupAutoComplete(final JTextField txtInput, final ArrayList<String> items) {
			        final DefaultComboBoxModel model = new DefaultComboBoxModel();
			        final JComboBox cbInput = new JComboBox(model) {
			            public Dimension getPreferredSize() {
			                return new Dimension(super.getPreferredSize().width, 0);
			            }
			        };
			        setAdjusting(cbInput, false);
			        for (String item : items) {
			            model.addElement(item);
			        }
			        cbInput.setSelectedItem(null);
			        cbInput.addActionListener(new ActionListener() {
			            @Override
			            public void actionPerformed(ActionEvent e) {
			                if (!isAdjusting(cbInput)) {
			                    if (cbInput.getSelectedItem() != null) {
			                        txtInput.setText(cbInput.getSelectedItem().toString());
			                    }
			                }
			            }
			        });

			        txtInput.addKeyListener(new KeyAdapter() {

			            @Override
			            public void keyPressed(KeyEvent e) {
			                setAdjusting(cbInput, true);
			                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			                    if (cbInput.isPopupVisible()) {
			                        e.setKeyCode(KeyEvent.VK_ENTER);
			                    }
			                }
			                if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
			                    e.setSource(cbInput);
			                    cbInput.dispatchEvent(e);
			                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			                        txtInput.setText(cbInput.getSelectedItem().toString());
			                        cbInput.setPopupVisible(false);
			                    }
			                }
			                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			                    cbInput.setPopupVisible(false);
			                }
			                setAdjusting(cbInput, false);
			            }
			        });
			        txtInput.getDocument().addDocumentListener(new DocumentListener() {
			            public void insertUpdate(DocumentEvent e) {
			                try {
								updateList();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
			            }

			            public void removeUpdate(DocumentEvent e) {
			                try {
								updateList();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
			            }

			            public void changedUpdate(DocumentEvent e) {
			                try {
								updateList();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
			            }

			            private void updateList() throws IOException  {
			                setAdjusting(cbInput, true);
			                model.removeAllElements();
			                String input = txtInput.getText();
			               
			                i=new trie1();
			                int g=(i).wordsWithSamePrefix(input);
			                if(g!=0) 	
			                	if(!input.isEmpty())
			                	{
			                		for(String item: (i).w)
			                		{
			                			model.addElement(item);
			                		}
			                	}
			                
			                
			              
			                cbInput.setPopupVisible(model.getSize() > 0);
			                setAdjusting(cbInput, false);
			            }
			        });
			        txtInput.setLayout(new BorderLayout());
			        txtInput.add(cbInput, BorderLayout.SOUTH);
			    }
			}
 

		
		
