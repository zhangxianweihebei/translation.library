package com.ld.translation.library.trans.impl;

import com.alibaba.fastjson.JSONObject;
import com.ld.translation.library.http.HttpParams;
import com.ld.translation.library.http.HttpPostParams;
import com.ld.translation.library.trans.AbstractOnlineTranslator;
import com.ld.translation.library.trans.LANG;
import com.ld.translation.library.trans.annotation.TranslatorComponent;

@TranslatorComponent(id = "jinshan")
final public class JinshanTranslator extends AbstractOnlineTranslator {
	
	public JinshanTranslator(){
		langMap.put(LANG.EN, "en");
		langMap.put(LANG.ZH, "zh");
	}
	
	@Override
	protected String getResponse(LANG from, LANG targ, String query) throws Exception{
		HttpParams params = new HttpPostParams()
				.put("f", langMap.get(from))
				.put("t", langMap.get(targ))
				.put("w", query);
		
		return params.send2String("http://fy.iciba.com/ajax.php?a=fy");
	}
	
	
	@Override
	protected String parseString(String jsonString){
		JSONObject jsonObject = JSONObject.parseObject(jsonString);
		String result = jsonObject.getJSONObject("content").getString("out");
		return result;
	}
}
