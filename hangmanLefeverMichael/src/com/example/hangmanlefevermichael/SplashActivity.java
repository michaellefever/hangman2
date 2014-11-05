package com.example.hangmanlefevermichael;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SplashActivity extends Activity {

	private String theme;
	private InputStream iStream;
	private String xml;
	private DocumentBuilderFactory dbFactory;
	private DocumentBuilder builder;
	private Document wordDoc;
	private NodeList wordList;
	private NodeList childList;
	private List<String> words;
	protected boolean active = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		Intent prevIntent = getIntent();
		theme = prevIntent.getExtras().getString("theme");
		if (theme.equals("animal")){
			xml = "Animals.xml";
		} else {
			xml = "Fruits.xml";
		}
		
		final Intent intent = new Intent(this, MainActivity.class);
		
		 Thread splashScreen = new Thread(){
	            @Override
	            public void run(){                    
	                    try {
							iStream = getApplicationContext().getAssets().open(xml);
							dbFactory = DocumentBuilderFactory.newInstance();
		                    builder = dbFactory.newDocumentBuilder();
		                    wordDoc = builder.parse(iStream);
		                    wordList = wordDoc.getElementsByTagName("word");
		                    
		                    String word = null;
		                    words = new ArrayList<String>();
		                    
		                    iStream.close();
		                    
		                    for(int i = 0; i < wordList.getLength(); i++){
		                        childList = wordList.item(i).getChildNodes();
		                        word = childList.item(1).getFirstChild().getTextContent();
		                        if (word!=null){
		                        	words.add(word);
		                        }
		                        if (!active){
		                        	break;
		                        }
		                    }
		                    
		                    active = false;
		                    intent.putStringArrayListExtra("words", (ArrayList<String>) words);
		                    startActivity(intent);
		                    
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SAXException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParserConfigurationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                
	            }
		 };
		 splashScreen.start();
	}
}
