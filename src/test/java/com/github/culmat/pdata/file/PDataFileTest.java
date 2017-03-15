package com.github.culmat.pdata.file;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;

import com.github.culmat.pdata.api.PData;

public class PDataFileTest {

	@Test
	@Ignore
	public void test() throws Exception {
		Set<URI> list = new PDataFile(".").list();
		System.out.println(list);
		PDataFile child1 = new PDataFile(list.iterator().next());
		assertTrue(child1.exists());
		PData dst = new PDataFile(child1.getName()+"_copy");
		assertFalse(dst.exists());
		dst.copyFrom(child1);
		assertTrue(dst.exists());
	}

}
