package com.github.culmat.pdata.file;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Set;

import com.github.culmat.pdata.api.MimeType;
import com.github.culmat.pdata.api.PData;
import com.github.culmat.pdata.api.PTree;
import com.github.culmat.pdata.util.Copy;

public class PDataFile implements PData, PTree {

	private File file;
	private MimeType type;

	public PDataFile(File file) {
		this.file = file;
		type = MimeType.forExtension(getExtension(file.getAbsolutePath()));
	}
	
	public PDataFile(String path) {
		this(new File(path));
	}
	
	public PDataFile(URI uri) {
		this(new File(uri));
	}
	

	@Override
	public URI getParent() {
		File parent = file.getParentFile();
		return parent == null ? null : parent.toURI();
	}

	@Override
	public Set<URI> list() {
		return stream(file.listFiles()).map(File::toURI).collect(toSet());
	}

	@Override
	public URI getId() throws IOException {
		return file.toURI();
	}

	@Override
	public long getSize() throws IOException {
		return file.length();
	}

	@Override
	public long getLastModified() throws IOException {
		return file.lastModified();
	}

	@Override
	public MimeType getType() throws IOException {
		return type;
	}

	@Override
	public InputStream getData() throws IOException {
		return new FileInputStream(file);
	}

	@Override
	public boolean exists() throws IOException {
		return file.exists();
	}

	@Override
	public boolean isWriteable() throws IOException {
		return file.canWrite();
	}

	@Override
	public long setData(InputStream in) throws IOException {
		return Copy.copy(in, new FileOutputStream(file));
	}

	public long copyFrom(PData data) throws IOException {
		return setData(data.getData());
	}

	protected boolean unEqual(MimeType a, MimeType b) {
		return a != null && b != null && !a.equals(b);
	}

	@Override
	public void delete() throws IOException {
		file.delete();
	}

	@Override
	public void setLastModified(long lastModified) throws IOException {
	}

	@Override
	public void setSize(long size) throws IOException {
	}

	@Override
	public void setType(MimeType type) throws IOException {
	}

	public final static String getExtension(String path) {
        String ext = null;
        int i = path.lastIndexOf('.');

        if (i > 0 &&  i < path.length() - 1) {
            ext = path.substring(i+1);
        }
        return ext;
    }

	public String getName() {
		return file.getName();
	}
	
}
