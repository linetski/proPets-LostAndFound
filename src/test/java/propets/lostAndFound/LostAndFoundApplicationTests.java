package propets.lostAndFound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import propets.model.ImmagaResponse;
import propets.model.ImmagaTagWithConfidence;

@SpringBootTest
class LostAndFoundApplicationTests {
	
	String json = "{\"result\":{\"tags\":[{\"confidence\":100,\"tag\":{\"en\":\"macaw\"}},{\"confidence\":100,\"tag\":{\"en\":\"parrot\"}},{\"confidence\":100,\"tag\":{\"en\":\"bird\"}},{\"confidence\":65.9520416259766,\"tag\":{\"en\":\"beak\"}},{\"confidence\":55.3943786621094,\"tag\":{\"en\":\"wildlife\"}},{\"confidence\":53.6005363464355,\"tag\":{\"en\":\"animal\"}},{\"confidence\":51.0465240478516,\"tag\":{\"en\":\"feather\"}},{\"confidence\":44.4009132385254,\"tag\":{\"en\":\"tropical\"}},{\"confidence\":42.1621742248535,\"tag\":{\"en\":\"wing\"}},{\"confidence\":39.3801765441895,\"tag\":{\"en\":\"eye\"}},{\"confidence\":39.2761535644531,\"tag\":{\"en\":\"wild\"}},{\"confidence\":39.0030174255371,\"tag\":{\"en\":\"feathers\"}},{\"confidence\":35.1900482177734,\"tag\":{\"en\":\"yellow\"}},{\"confidence\":31.9514770507812,\"tag\":{\"en\":\"zoo\"}},{\"confidence\":30.8869171142578,\"tag\":{\"en\":\"colorful\"}},{\"confidence\":28.5435466766357,\"tag\":{\"en\":\"pet\"}},{\"confidence\":22.699047088623,\"tag\":{\"en\":\"head\"}},{\"confidence\":22.6724262237549,\"tag\":{\"en\":\"avian\"}},{\"confidence\":21.9152297973633,\"tag\":{\"en\":\"exotic\"}},{\"confidence\":21.3889999389648,\"tag\":{\"en\":\"portrait\"}},{\"confidence\":21.1693553924561,\"tag\":{\"en\":\"close\"}},{\"confidence\":19.3372364044189,\"tag\":{\"en\":\"fauna\"}},{\"confidence\":19.2157344818115,\"tag\":{\"en\":\"wings\"}},{\"confidence\":18.6421737670898,\"tag\":{\"en\":\"bright\"}},{\"confidence\":18.4062671661377,\"tag\":{\"en\":\"color\"}},{\"confidence\":17.8560314178467,\"tag\":{\"en\":\"parakeet\"}},{\"confidence\":17.7935600280762,\"tag\":{\"en\":\"perch\"}},{\"confidence\":17.7417125701904,\"tag\":{\"en\":\"plumage\"}},{\"confidence\":17.5441761016846,\"tag\":{\"en\":\"birds\"}},{\"confidence\":15.5923538208008,\"tag\":{\"en\":\"jungle\"}},{\"confidence\":14.8293170928955,\"tag\":{\"en\":\"forest\"}},{\"confidence\":14.7641725540161,\"tag\":{\"en\":\"natural\"}},{\"confidence\":14.3058404922485,\"tag\":{\"en\":\"bill\"}},{\"confidence\":13.7154169082642,\"tag\":{\"en\":\"branch\"}},{\"confidence\":13.7017822265625,\"tag\":{\"en\":\"hunter\"}},{\"confidence\":13.0833282470703,\"tag\":{\"en\":\"fly\"}},{\"confidence\":12.9076089859009,\"tag\":{\"en\":\"parrots\"}},{\"confidence\":12.6312847137451,\"tag\":{\"en\":\"predator\"}},{\"confidence\":12.3357210159302,\"tag\":{\"en\":\"tree\"}},{\"confidence\":12.1477966308594,\"tag\":{\"en\":\"closeup\"}},{\"confidence\":12.040657043457,\"tag\":{\"en\":\"black\"}},{\"confidence\":11.8679943084717,\"tag\":{\"en\":\"hawk\"}},{\"confidence\":11.79869556427,\"tag\":{\"en\":\"eagle\"}},{\"confidence\":11.7806034088135,\"tag\":{\"en\":\"species\"}},{\"confidence\":11.7633438110352,\"tag\":{\"en\":\"life\"}},{\"confidence\":11.5553779602051,\"tag\":{\"en\":\"pets\"}},{\"confidence\":11.5150918960571,\"tag\":{\"en\":\"cute\"}},{\"confidence\":11.2123031616211,\"tag\":{\"en\":\"looking\"}},{\"confidence\":10.9278497695923,\"tag\":{\"en\":\"macaws\"}},{\"confidence\":10.7741451263428,\"tag\":{\"en\":\"tropics\"}},{\"confidence\":10.7301549911499,\"tag\":{\"en\":\"park\"}},{\"confidence\":10.2280883789062,\"tag\":{\"en\":\"animals\"}},{\"confidence\":9.98186492919922,\"tag\":{\"en\":\"orange\"}},{\"confidence\":9.85871696472168,\"tag\":{\"en\":\"prey\"}},{\"confidence\":9.47923183441162,\"tag\":{\"en\":\"multi\"}},{\"confidence\":9.41910076141357,\"tag\":{\"en\":\"rainbow\"}},{\"confidence\":8.907958984375,\"tag\":{\"en\":\"perched\"}},{\"confidence\":8.82864952087402,\"tag\":{\"en\":\"endangered\"}},{\"confidence\":8.70457458496094,\"tag\":{\"en\":\"standing\"}},{\"confidence\":8.42059993743896,\"tag\":{\"en\":\"pretty\"}},{\"confidence\":8.39922618865967,\"tag\":{\"en\":\"vivid\"}},{\"confidence\":8.15724945068359,\"tag\":{\"en\":\"domestic\"}},{\"confidence\":7.87109661102295,\"tag\":{\"en\":\"bald\"}},{\"confidence\":7.78220415115356,\"tag\":{\"en\":\"staring\"}},{\"confidence\":7.74479198455811,\"tag\":{\"en\":\"sitting\"}},{\"confidence\":7.58638858795166,\"tag\":{\"en\":\"conservation\"}},{\"confidence\":7.07927846908569,\"tag\":{\"en\":\"colors\"}}]},\"status\":{\"text\":\"\",\"type\":\"success\"}}";

	@Test
	void contextLoads() {
	}
	
	@Test
	void deserializationTest() {
		try {
			ObjectMapper om = new ObjectMapper();
			om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			ImmagaResponse immagaResponse = om.readerFor(ImmagaResponse.class).readValue(json);
			for (ImmagaTagWithConfidence immagaTagWithConfidence : immagaResponse.getResult().getTags()) {
				if (immagaTagWithConfidence.getConfidence()>20) {
					immagaTagWithConfidence.getTag().getEn();
					System.out.println(immagaTagWithConfidence.getTag().getEn());
				}
			}

			String s = "4";
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
