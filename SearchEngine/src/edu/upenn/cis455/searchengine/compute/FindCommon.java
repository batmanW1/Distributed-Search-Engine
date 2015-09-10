package edu.upenn.cis455.searchengine.compute;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.math3.util.Pair;

public class FindCommon {
	private static final int WINDOW = 20;
	ArrayList<Pair<String, String[]>> positionList = new ArrayList<Pair<String, String[]>>();

	public FindCommon(ArrayList<Pair<String, String[]>> positionList) {
		this.positionList = positionList;
	}

	public boolean find() {
		for (int i = 0; i < positionList.size(); i++) {
			for (int j = i + 1; j < positionList.size(); j++) {
				if (!computeDist(positionList.get(i).getSecond(), positionList
						.get(j).getSecond())) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean computeDist(String[] list1, String[] list2) {
		int i = 0;
		int j = 0;
		while (i < list1.length && j < list2.length) {
			int num1 = Integer.parseInt(list1[i]);
			int num2 = Integer.parseInt(list2[j]);

			if (Math.abs(num1 - num2) <= WINDOW && num1 != -100 && num2 != -100) {
				return true;
			}
			if (num1 < num2) {
				i++;
			} else {
				j++;
			}

		}
		return false;
	}

}
