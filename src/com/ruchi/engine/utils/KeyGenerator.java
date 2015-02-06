package com.ruchi.engine.utils;

import java.util.concurrent.atomic.AtomicLong;

public class KeyGenerator {
	private static final AtomicLong LAST_TIME_MS;
	static{
		LAST_TIME_MS = new AtomicLong();
	}
	
	public static String uniqueCurrentTimeMS() {
	    long now = System.currentTimeMillis();
	    while(true) {
	        long lastTime = LAST_TIME_MS.get();
	        if (lastTime >= now)
	            now = lastTime+1;
	        if (LAST_TIME_MS.compareAndSet(lastTime, now))
	            return Long.toString(now);
	    }
	}

}
