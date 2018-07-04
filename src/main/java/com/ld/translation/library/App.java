package com.ld.translation.library;

import com.ld.translation.library.trans.LANG;
import com.ld.translation.library.trans.factory.TFactory;
import com.ld.translation.library.trans.factory.TranslatorFactory;

/**
 * 翻译测试
 * 可用的
 * 金山
 * 谷歌
 * 欧米
 * @author Administrator
 *
 */
public class App {
	public static void main( String[] args) throws Exception{
		TFactory factory = new TranslatorFactory();
		String origin = "你好";
		if(args.length > 0) {
			origin=args[0];
		}
		System.out.println("金山 : " + factory.get("jinshan").trans(LANG.ZH, LANG.EN, origin));
		System.out.println("谷歌 : " + factory.get("google").trans(LANG.ZH, LANG.EN, origin));
		System.out.println("欧米 : " + factory.get("omi").trans(LANG.ZH, LANG.EN, origin));
	}
}
