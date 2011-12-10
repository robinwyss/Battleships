package com.jostrobin.battleships.common.util;

import java.util.Comparator;

public class ParticipantComparator implements Comparator<Long>
{
	private Long currentParticipant;

	public ParticipantComparator(Long currentParticipant)
	{
		this.currentParticipant = currentParticipant;
	}

	@Override
	public int compare(Long o1, Long o2)
	{
		if(o1 == currentParticipant){
			return -1;
		}
		if(o2 == currentParticipant) {
			return 1;
		}
		return o1.compareTo(o2);
	}

}
