package com.airensoft.skovp.sample.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.airensoft.skovp.sample.utils.common.UnitUtils;
import com.airensoft.skovp.sample.utils.ovpconnector.OMSConfig;
import com.airensoft.skovp.sample.utils.ovpconnector.OMSConnector;
import com.airensoft.skovp.sample.utils.ovpconnector.OMSConnectorResponse;
import com.airensoft.skovp.sample.utils.time.DateHelper;
import com.airensoft.skovp.sample.utils.time.DateUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

@Controller
public class SampleViewController
{
	@Autowired
	OMSConnectorResponse		omsResponder;
	@Autowired
	OMSConnector				omsConnector;
	
	/**
	 * 샘플웹페이지의 View Controller
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getMemberInfo(HttpServletRequest request, ModelMap model)
	{
		List<Object> result = new ArrayList<Object>();
		
		omsResponder = omsConnector.RequestContentList("video", null, null, null, null,null, null, null, null, null, null, true);
		JsonElement resultElement = omsResponder.getRootDataElement();
		JsonArray contentJsonArray = resultElement.getAsJsonObject().get("content").getAsJsonArray();
		
		for(int i =0 ; i<contentJsonArray.size(); i++)
		{
			Map<String, Object> map = new HashMap<String, Object>();
			JsonElement element = contentJsonArray.get(i);
			
			// 업로드 완료된 미디어
			String state = element.getAsJsonObject().get("state").getAsString();
			if(state.equals("cmplt"))
			{
				// 제목
				map.put("title", element.getAsJsonObject().get("title").getAsString());
				
				// job 프로파일과 맞은 트랜스코딩 미디어 파일을 찾는다. 없는경우 원본사용
				JsonArray trscdJsonArray = element.getAsJsonObject().get("extra").getAsJsonObject().get("transcodes").getAsJsonArray();
				for(int j = 0 ; j < trscdJsonArray.size(); j++)
				{
					JsonElement trscdElement = trscdJsonArray.get(j);
					// 업로드가 완료된 미디어만 View에 출력하도록 한다.
					String trscdState = trscdElement.getAsJsonObject().get("state").getAsString();
					if(trscdState.equals("cmplt"))
					{
						Integer jobProfileId = trscdElement.getAsJsonObject().get("job_profile_id").getAsInt();
						if(jobProfileId.equals(OMSConfig.getJobProfileId()) )
						{
							
							//재생시간
							long duration = trscdElement.getAsJsonObject().get("duration").getAsLong();
							map.put("duration", DateHelper.getHourString(duration) + ":" + DateHelper.getMinuteString(duration) + ":" + DateHelper.getSecondString(duration));
							
							// 컨텐츠 파일사이즈차례
							long file_size = trscdElement.getAsJsonObject().get("file_size").getAsLong();
							map.put("file_size",UnitUtils.humanReadableByteCount(file_size, false));
							
							// 미디어 수정시간
							int mod_date = trscdElement.getAsJsonObject().get("mod_date").getAsInt();
							map.put("mod_date", DateUtils.TimestamptToString(mod_date));
							
							// 미디어 ID
							Integer content_id = trscdElement.getAsJsonObject().get("content_id").getAsInt();
							map.put("content_id", content_id);
							
							// 섬네일 id
							int thumbnailMediaId = element.getAsJsonObject().get("extra").getAsJsonObject().get("thumbnails").getAsJsonArray().get(1).getAsJsonObject().get("content_id").getAsInt();
							omsConnector.clear();
							omsResponder = omsConnector.RequestPulbishDownloadContent(thumbnailMediaId);
							
							String thumb_url = omsResponder.getRootDataElement().getAsJsonObject().get("url").getAsString();
							map.put("thumb_url", thumb_url);
							
							result.add(map);
							break;
						}
					}
				}
			}
		}
		
		model.addAttribute("list", result);
		
		model.addAttribute("player_id", OMSConfig.getPlayerId());
		
		return "main/sample";
	}
	
	/**
	 * content_id를 이용하여 스트리밍URL을 생성하는 API
	 * OMS로 부터 스트리밍 URL을 받아온다.
	 * @param request
	 * @param content_id
	 * @return
	 */
	@RequestMapping(value = "content/player/{content_id}", method = RequestMethod.POST)
	@ResponseBody
	public String getContentStringUrl(@PathVariable("content_id") Integer content_id)
	{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 재생할 미디어 정보를 가져온다.
		//Integer content_id, Integer allow_count, Integer allow_start_Date, Integer allow_end_date, String protocol, boolean encryption
		omsResponder = omsConnector.RequestPulbishStreamingContent(content_id, 5, null, null, "rtmp", false);
		String streaming_url = omsResponder.getRootDataElement().getAsJsonObject().get("url").getAsString();
		System.out.println("\n" + streaming_url);
		
		map.put("streaming_url",streaming_url);
		
		return new Gson().toJson(map);
	}
}
