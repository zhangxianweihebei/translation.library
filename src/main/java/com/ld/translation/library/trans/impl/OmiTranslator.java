package com.ld.translation.library.trans.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ld.translation.library.http.HttpGetParams;
import com.ld.translation.library.http.HttpParams;
import com.ld.translation.library.trans.AbstractOnlineTranslator;
import com.ld.translation.library.trans.LANG;
import com.ld.translation.library.trans.annotation.TranslatorComponent;

@TranslatorComponent(id = "omi")
final public class OmiTranslator extends AbstractOnlineTranslator {

	public OmiTranslator(){
		langMap.put(LANG.EN, "e");
		langMap.put(LANG.ZH, "c");
	}
	
	@Override
	public String getResponse(LANG from, LANG targ, String query) throws Exception{
		
		HttpParams params = new HttpGetParams();
		params.put("languageType", langMap.get(from)+"2"+langMap.get(targ));
		params.put("userDbName", "");
		params.put("sentsToTrans", query);
		
		return params.send2String("http://www.alifanyi1688.com/transSents.do");
	}
	
	@Override
	protected String parseString(String jsonString){
		
		JSONObject jsonObject = JSONObject.parseObject(jsonString);
		JSONArray segments = jsonObject.getJSONArray("sentsResults").getJSONArray(1);
		StringBuilder result = new StringBuilder();
		
		for(int i=0; i<segments.size(); i++){
			result.append(segments.getString(i));
		}
		return result.toString();
	}
}
