package com.ld.translation.library.trans.factory;

import com.ld.translation.library.trans.Translator;

public interface TFactory {
	Translator get(String id);
}
