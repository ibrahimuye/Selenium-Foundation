package com.nordstrom.automation.selenium.interfaces;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.internal.WrapsDriver;

public interface WrapsContext extends WrapsDriver {
	
	/**
	 * Get the underlying search context for this object
	 * 
	 * @return object search context
	 */
	public SearchContext getWrappedContext();
	
	/**
	 * Refresh the underlying search context for this object
	 * 
	 * @param expiration expiration time of context chain
	 * @return object search context
	 */
	public SearchContext refreshContext(Long expiration);
	
	/**
	 * Determine when the underlying search context for this object was acquired
	 * 
	 * @return search context acquisition time (from {@link System#currentTimeMillis()})
	 */
	public Long acquiredAt();
	
}
