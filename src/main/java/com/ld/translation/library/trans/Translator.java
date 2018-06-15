package com.ld.translation.library.trans;

public interface Translator {
	public String trans(LANG from, LANG targ, String query) throws Exception;
}
