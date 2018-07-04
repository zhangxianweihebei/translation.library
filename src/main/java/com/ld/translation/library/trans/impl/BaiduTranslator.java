package com.ld.translation.library.trans.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ld.translation.library.http.HttpGetParams;
import com.ld.translation.library.http.HttpParams;
import com.ld.translation.library.trans.AbstractOnlineTranslator;
import com.ld.translation.library.trans.LANG;
import com.ld.translation.library.trans.annotation.TranslatorComponent;

@TranslatorComponent(id = "baidu")
final public class BaiduTranslator extends AbstractOnlineTranslator {

	public BaiduTranslator(){
		langMap.put(LANG.EN, "en");
		langMap.put(LANG.ZH, "zh");
	}
	
	@Override
	public String getResponse(LANG from, LANG targ, String query) throws Exception{
		HttpParams params = new HttpGetParams();
				params.put("from", langMap.get(from));
				params.put("to", langMap.get(targ));
				params.put("query", query);
				params.put("transtype", "translang");
				params.put("simple_means_flag", "3");
		
		return params.send2String("http://fanyi.baidu.com/v2transapi");
	}
	
	@Override
	protected String parseString(String jsonString){
		JSONObject jsonObject = JSONObject.parseObject(jsonString);
		JSONArray segments = jsonObject.getJSONObject("trans_result").getJSONArray("data");
		StringBuilder result = new StringBuilder();
		
		for(int i=0; i<segments.size(); i++){
			result.append(i==0?"":"\n");
			result.append(segments.getJSONObject(i).getString("dst"));
		}
		return new String(result);
	}
}
