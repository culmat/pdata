package com.github.culmat.pdata.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * A thin wrapper around IputStream to handle large (binary) data efficiently.
 */
public interface PData {

	URI getId() throws IOException;

	/**
	 * @return the size in bytes or -1 if unknown.
	 */
	long getSize() throws IOException;

	/**
	 * @return last modification or -1 if unknown.
	 */
	long getLastModified() throws IOException;

	MimeType getType() throws IOException;

	InputStream getData() throws IOException;

	boolean exists() throws IOException;

	/**
	 * @return if false, then the setters, delete and copyFrom will fail
	 */
	boolean isWriteable() throws IOException;

	/**
	 * Sets the data, for indirect implementations also writes the meta data.
	 * Guarantees persistence.
	 * 
	 * @param data
	 * @return the new length
	 * @throws IOException
	 * @see PDataIndirect
	 */
	long setData(InputStream in) throws IOException;

	/**
	 * Copies the modification time, MIME type and data.
	 * 
	 * @param data
	 * @return see <code>setData</code>
	 * @throws IOException
	 */
	long copyFrom(PData data) throws IOException;

	void delete() throws IOException;

	/**
	 * Sets the last modified time.
	 * 
	 * @param lastModified
	 */
	void setLastModified(long lastModified) throws IOException;

	/**
	 * <code>setData</code> should throw an IOException if a (positive) size was
	 * set and the return value of setData would be unequal to this size.
	 * 
	 * @param size
	 */
	void setSize(long size) throws IOException;

	/**
	 * Sets the MIME type time. Does <b>not</b> guarantee persistence.
	 * 
	 * @param type
	 */
	void setType(MimeType type) throws IOException;

}
