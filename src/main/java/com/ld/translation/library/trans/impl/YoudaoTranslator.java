package com.ld.translation.library.trans.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ld.translation.library.http.HttpGetParams;
import com.ld.translation.library.http.HttpParams;
import com.ld.translation.library.trans.AbstractOnlineTranslator;
import com.ld.translation.library.trans.LANG;
import com.ld.translation.library.trans.annotation.TranslatorComponent;

@TranslatorComponent(id = "youdao")
final public class YoudaoTranslator extends AbstractOnlineTranslator {

	public YoudaoTranslator(){
		langMap.put(LANG.EN, "EN");
		langMap.put(LANG.ZH, "ZH_CN");
	}
	
	@Override
	protected String getResponse(LANG from, LANG targ, String query) throws Exception{
		HttpParams params = new HttpGetParams();
				params.put("type", langMap.get(from)+"2"+langMap.get(targ));
				params.put("i", query);
				params.put("doctype", "json");
				params.put("xmlVersion", "1.8");
				params.put("keyfrom", "fanyi.web");
				params.put("ue", "UTF-8");
				params.put("action", "FY_BY_CLICKBUTTON");
				params.put("typoResult", "true");
		
		return params.send2String("http://fanyi.youdao.com/translate?smartresult=dict&smartresult=rule&smartresult=ugc&sessionFrom=https://www.baidu.com/link");
	}
	
	@Override
	protected String parseString(String jsonString){
		StringBuilder result = new StringBuilder();
		JSONObject jsonObject = JSONObject.parseObject(jsonString);
		JSONArray segments = jsonObject.getJSONArray("translateResult");
		
		for(int i=0; i<segments.size(); i++){
			result.append(i==0 ? "" : "\r\n");
			JSONArray parts = jsonObject.getJSONArray("translateResult").getJSONArray(i);
			for(int j=0; j<parts.size(); j++){
				result.append(parts.getJSONObject(j).getString("tgt"));
			}
		}
		
		return new String(result);
	}
}
