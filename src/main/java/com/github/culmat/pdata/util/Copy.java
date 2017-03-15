package com.github.culmat.pdata.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class Copy {
	/**
	 * Copy stream.
	 * 
	 * @param src
	 *            the src
	 * @param dst
	 *            the dst
	 * @return
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static long copy(final InputStream src, final OutputStream dst) throws IOException {
		ReadableByteChannel inputChannel = Channels.newChannel(src);
		WritableByteChannel outputChannel = Channels.newChannel(dst);
		// copy the channels
		long ret = -1;
		// closing the channels
		try {
			ret = copy(inputChannel, outputChannel);
		} finally {
			inputChannel.close();
			outputChannel.close();
		}
		return ret;
	}

	/**
	 * Copy.
	 * 
	 * @param src
	 *            the src
	 * @param dest
	 *            the dest
	 * @return
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static long copy(final ReadableByteChannel src, final WritableByteChannel dest) throws IOException {
		if (src instanceof FileChannel) {
			FileChannel fc = (FileChannel) src;
			return fc.transferTo(0, fc.size(), dest);
		} else if (dest instanceof FileChannel) {
			FileChannel fc = (FileChannel) dest;
			return fc.transferFrom(src, 0, Long.MAX_VALUE);
		}
		final ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
		int read = src.read(buffer);
		long totalRead = 0;
		while (read != -1) {
			totalRead += read;
			// prepare the buffer to be drained
			buffer.flip();
			// write to the channel, may block
			dest.write(buffer);
			// If partial transfer, shift remainder down
			// If buffer is empty, same as doing clear()
			buffer.compact();
			read = src.read(buffer);
		}
		// EOF will leave buffer in fill state
		buffer.flip();
		// make sure the buffer is fully drained.
		while (buffer.hasRemaining()) {
			dest.write(buffer);
		}
		return totalRead;
	}
	
}
