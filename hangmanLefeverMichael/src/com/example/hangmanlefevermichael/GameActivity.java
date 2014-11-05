package com.example.hangmanlefevermichael;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.BufferType;

public class GameActivity extends Activity {

	private String theme;
	private ArrayList<Boolean> curAnswer = new ArrayList<Boolean>();
	private int curMan = 0;
	private String key;
	private Intent intent = getIntent();

	 
	 private ArrayList<String> fr = new ArrayList<String>();
	 private ArrayList<String> an = new ArrayList<String>();
	 
	 private Random random = new Random();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		fr.add("apple");fr.add("pear");fr.add("pineapple");fr.add("strawberry");fr.add("cherry");
		fr.add("horse");fr.add("giraffe");fr.add("elephant");fr.add("zebra");fr.add("bird");fr.add("shark");fr.add("monkey");
		//theme = intent.getStringExtra("theme");
		theme = "animals and fruit";

		TextView textTheme = (TextView) findViewById(R.id.theme);
		textTheme.setText(theme);

		selectWord();

		TextView answer = (TextView) findViewById(R.id.answer);
		answer.setText(getCurAnswer());

		checkResult();
	}

	private void selectWord() {
		/*if (intent.getExtras().getString("theme").equals("animal")){
			key = an.get(random.nextInt(an.size()));
		} else {*/
			key = fr.get(random.nextInt(fr.size()));
		//}
		
		curAnswer = new ArrayList<Boolean>();
		for (int i = 0; i < key.length(); i++) {
			curAnswer.add(false);
		}
		HashSet<Character> letterSet = new HashSet<Character>();
		for(int i=0;i<key.length();++i){
			letterSet.add(key.charAt(i));
		}
		int numOfBlanks = 2;
		int numOfLetters = letterSet.size();
		int numOfShow = 0;
		if(numOfLetters > numOfBlanks){
			curMan = 0;
			numOfShow = numOfLetters - numOfBlanks;
		}
		else if(numOfLetters < numOfBlanks){
			curMan = numOfBlanks - numOfLetters ; 
			numOfShow = 0;
		}
		
		for(int i=0;i<numOfShow;++i){
			int itemIndex = random.nextInt(letterSet.size());
			int j = 0;
			for(Character c : letterSet)
			{
			    if (j == itemIndex){
			        inputLetter(c);
			        letterSet.remove(c);
			        break;
			    }
			    ++j;
			}
		}		

	}

	private void inputLetter(char c) {
		boolean isContain = false;
		for (int i = 0; i < key.length(); ++i) {
			char ans = key.charAt(i);
			if (c == ans) {
				isContain = true;
				curAnswer.set(i, true);
			}
		}
		if (curMan > 0 && isContain) {
			curMan--;
		}
		disableLetter(c);
	}

	private void disableLetter(char c) {
		char C = Character.toUpperCase(c);
		String buttonID = "button" + C;
		int resID = getResources().getIdentifier(buttonID, "id",
				"com.example.hangmanlefevermichael");
		Button b = (Button) findViewById(resID);
		b.setEnabled(false);
	}

	private String getCurAnswer() {
		String result = new String();
		for (int i = 0; i < curAnswer.size(); ++i) {
			if (curAnswer.get(i)) {
				result += (key.charAt(i) + " ");
			} else {
				result += "_ ";
			}
		}
		Log.d("test", result);

		return result;
	}

	private void checkResult() {
		boolean isComplete = true;
		for (boolean b : curAnswer) {
			if (!b) {
				isComplete = false;
				break;
			}
		}

		ImageView imageHanging = (ImageView) findViewById(R.id.imageHanging);
		TextView textFill = (TextView) findViewById(R.id.answer);

		if (isComplete) {
			imageHanging.setImageResource(R.drawable.hanggood);
			for (int i = 0; i < 26; i++) {
				char c = (char) ('a' + i);
				disableLetter(c);
			}
			textFill.setText(getCurAnswer());
			return;
		}

		// not complete
		if (curMan < 8) {
			textFill.setText(getCurAnswer());
		}
		switch (curMan) {
		case 0:
			imageHanging.setImageResource(R.drawable.hang1);
			break;
		case 1:
			imageHanging.setImageResource(R.drawable.hang1);
			break;
		case 2:
			imageHanging.setImageResource(R.drawable.hang2);
			break;
		case 3:
			imageHanging.setImageResource(R.drawable.hang3);
			break;
		case 4:
			imageHanging.setImageResource(R.drawable.hang4);
			break;
		case 5:
			imageHanging.setImageResource(R.drawable.hang5);
			break;
		case 6:
			imageHanging.setImageResource(R.drawable.hang6);
			break;
		case 7:
			imageHanging.setImageResource(R.drawable.hang7);
			break;
		case 8:
			imageHanging.setImageResource(R.drawable.hang8);
			for (int i = 0; i < 26; i++) {
				char c = (char) ('a' + i);
				disableLetter(c);
			} // game over
			String rightAnswer = new String("");
			for (int i = 0; i < curAnswer.size(); ++i) {
				rightAnswer += key.charAt(i) + " ";
			}
			SpannableString text = new SpannableString(rightAnswer);

			for (int i = 0; i < curAnswer.size(); ++i) {
				if (!curAnswer.get(i)) {
					text.setSpan(new ForegroundColorSpan(Color.GRAY), 2 * i,
							2 * i + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
				}
			}
			textFill.setText(text, BufferType.SPANNABLE);
			break;
		}
	}

	public void clickLetter(View view) {
		curMan++;
		switch (view.getId()) {
		case R.id.buttonA:
			inputLetter('a');
			break;
		case R.id.buttonB:
			inputLetter('b');
			break;
		case R.id.buttonC:
			inputLetter('c');
			break;
		case R.id.buttonD:
			inputLetter('d');
			break;
		case R.id.buttonE:
			inputLetter('e');
			break;
		case R.id.buttonF:
			inputLetter('f');
			break;
		case R.id.buttonG:
			inputLetter('g');
			break;
		case R.id.buttonH:
			inputLetter('h');
			break;
		case R.id.buttonI:
			inputLetter('i');
			break;
		case R.id.buttonJ:
			inputLetter('j');
			break;
		case R.id.buttonK:
			inputLetter('k');
			break;
		case R.id.buttonL:
			inputLetter('l');
			break;
		case R.id.buttonM:
			inputLetter('m');
			break;
		case R.id.buttonN:
			inputLetter('n');
			break;
		case R.id.buttonO:
			inputLetter('o');
			break;
		case R.id.buttonP:
			inputLetter('p');
			break;
		case R.id.buttonQ:
			inputLetter('q');
			break;
		case R.id.buttonR:
			inputLetter('r');
			break;
		case R.id.buttonS:
			inputLetter('s');
			break;
		case R.id.buttonT:
			inputLetter('t');
			break;
		case R.id.buttonU:
			inputLetter('u');
			break;
		case R.id.buttonV:
			inputLetter('v');
			break;
		case R.id.buttonW:
			inputLetter('w');
			break;
		case R.id.buttonX:
			inputLetter('x');
			break;
		case R.id.buttonY:
			inputLetter('y');
			break;
		case R.id.buttonZ:
			inputLetter('z');
			break;
		}

		checkResult();
	}

	public void next(View view){
		Intent intent = new Intent(this, GameActivity.class);
		startActivity(intent);
	}
}
