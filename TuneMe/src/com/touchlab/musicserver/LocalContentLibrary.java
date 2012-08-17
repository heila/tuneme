package com.touchlab.musicserver;

import java.util.ArrayList;

public class LocalContentLibrary {
	
	static LocalContentLibrary instance = null;
	static public synchronized LocalContentLibrary getInstance() {
		if (instance == null) {
			instance = new LocalContentLibrary();
		}
		return instance;
	}
	
	private LocalContentLibrary() {
	}
	
	int artworkIds[] = {R.drawable.antsmarching, R.drawable.fstopblues, R.drawable.prelude, R.drawable.suicideblond, R.drawable.theregoesthefear, R.drawable.whilewereyoung, R.drawable.wreckingball};
	
	public String metaData[][] = { {"Ants Marching", "Dave Matthews Band", "Under The Table And Dreaming", "antsmarching.mp3", "antsmarching.mp3"},
			                 {"F Stop Blues", "Jack Johnson", "Bushfire Fairytales", "fstopblues.mp3", "fstopblues.mp3"},
			                 {"Prelude", "Bonobo", "Black Sands","prelude.mp3", "prelude.mp3"},
			                 {"Suice Blonde", "INXS", "Thre Greatest Hits", "suicideblond.mp3", "suicideblond.mp3"},
			                 {"There Goes The Fear", "Doves", "The Last Broadcast", "theregoesthefear.mp3", "theregoesthefear.mp3"},
			                 {"While We're Young", "April March" ,"Paris in April", "whilewereyoung.mp3", "whilewereyoung.mp3"},
			                 {"Wrecking Ball", "Bruce Springsteen", "Wrecking Ball", "wreckingball.mp3", "wreckingball.mp3"}
						  };
	
	public ArrayList<LocalContentItem> getContents() {
		ArrayList<LocalContentItem> list = new ArrayList<LocalContentItem>();
		
		int index = 0;
		for (String[] data : metaData) {
			list.add(new LocalContentItem(data[1],data[2],data[0],data[3],data[4],artworkIds[index++]));
		}
		return list;		
	}

}
