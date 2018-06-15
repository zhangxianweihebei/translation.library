package com.ld.translation.library.trans.factory;

import java.net.URISyntaxException;

import com.ld.translation.library.trans.Translator;
import com.ld.translation.library.trans.exception.DupIdException;

final public class TranslatorFactory extends AbstractTranslatorFactory{

	public TranslatorFactory() throws ClassNotFoundException, InstantiationException, IllegalAccessException, DupIdException, URISyntaxException {
		super();
	}

	@Override
	public Translator get(String id) {
		return translatorMap.get(id);
	}

}
