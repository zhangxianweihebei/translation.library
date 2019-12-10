package com.ld.translation.library.trans.factory;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ld.translation.library.trans.AbstractOnlineTranslator;
import com.ld.translation.library.trans.Translator;
import com.ld.translation.library.trans.annotation.TranslatorComponent;
import com.ld.translation.library.trans.exception.DupIdException;
import com.ld.translation.library.trans.impl.BaiduTranslator;
import com.ld.translation.library.trans.impl.GoogleTranslator;
import com.ld.translation.library.trans.impl.JinshanTranslator;
import com.ld.translation.library.trans.impl.OmiTranslator;
import com.ld.translation.library.trans.impl.TencentTranslator;
import com.ld.translation.library.trans.impl.YoudaoTranslator;

public abstract class AbstractTranslatorFactory implements TFactory {
	
	protected Map<String, Translator> translatorMap = new HashMap<String, Translator>();
	private List<String> workPackages = new ArrayList<String>();

	private List<Class<? extends AbstractOnlineTranslator>> translatorClasses = Arrays.asList(
			BaiduTranslator.class,
			GoogleTranslator.class,
			JinshanTranslator.class,
			TencentTranslator.class,
			OmiTranslator.class,
			YoudaoTranslator.class);
			
	public AbstractTranslatorFactory() throws ClassNotFoundException, InstantiationException, IllegalAccessException, DupIdException, URISyntaxException{
		initSystemTranslator();
		initUserTranslator();
	}
	
	public AbstractTranslatorFactory(String[] workPackages) throws ClassNotFoundException, InstantiationException, IllegalAccessException, DupIdException, URISyntaxException{
		this();
	}
	
	public AbstractTranslatorFactory(String workPackage) throws ClassNotFoundException, InstantiationException, IllegalAccessException, DupIdException, URISyntaxException{
		this(new String[]{workPackage});
	}
	
	private void initSystemTranslator() throws ClassNotFoundException, InstantiationException, IllegalAccessException, DupIdException, URISyntaxException{
		for(Class<?> translatorClass : translatorClasses){
			TranslatorComponent component = translatorClass.getAnnotation(TranslatorComponent.class);
			if(component != null){
				String id = component.id();
				if(translatorMap.containsKey(id)){
					throw new DupIdException("Id duplication exception");
				}else{
					translatorMap.put(component.id(), (Translator) translatorClass.newInstance());	
				}
			}
		}
	}
	
	private void initUserTranslator() throws ClassNotFoundException, InstantiationException, IllegalAccessException, DupIdException, URISyntaxException{
		for(String workPackage : workPackages){
			List<String> workClassesName = getClassNameByPackage(workPackage);
			for(String workClassName : workClassesName){
				Class<?> workClass = Class.forName(workClassName);
				TranslatorComponent component = workClass.getAnnotation(TranslatorComponent.class);
				if(component != null){
					String id = component.id();
					if(translatorMap.containsKey(id)){
						throw new DupIdException("Id duplication exception");
					}else{
						translatorMap.put(component.id(), (Translator) workClass.newInstance());	
					}
				}
			}
		}
	}
	
	private List<String> getClassNameByPackage(String packageName) throws URISyntaxException{
		List<String> classesName = new ArrayList<String>();
		ClassLoader loader = getClass().getClassLoader();
		URL url = loader.getResource(packageName.replace(".", "/"));

		File packageDir = new File(new URI(url.getPath()).getPath());
		for(File classFile : packageDir.listFiles()){
			String classNickName = classFile.getName();
			classNickName = classNickName.substring(0, classNickName.indexOf('.'));
			classesName.add(packageName+"."+classNickName);
		}
		return classesName;
	}
}
