package com.ruchi.engine.sentiment;

import edu.stanford.nlp.ling.IndexedWord;

public class MyWord implements Comparable<MyWord> {
	private String tag;
	private int index;
	private String value;

	public MyWord(IndexedWord indexedWord) {
		this.tag = indexedWord.tag();
		this.value = indexedWord.value();
		this.index = indexedWord.index();
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int hashCode() {
		return index;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MyWord))
			return false;
		else {
			MyWord myWord = (MyWord) obj;
			return this.index == myWord.getIndex();
		}
	}

	@Override
	public String toString() {
		return value + "-" + index;
	}

	@Override
	public int compareTo(MyWord o) {
		if (this.index < o.index)
			return -1;
		else if (this.index > o.index)
			return 1;
		return 0;
	}
}
