package com.github.culmat.pdata.api;

import java.net.URI;
import java.util.Set;

public interface PTree {
	
	 URI getParent();
	 
	 Set<URI> list();
	

}