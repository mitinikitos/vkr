package com.example.vkr;

import com.example.vkr.config.TestBeanConfig;
import com.example.vkr.config.TestConfig;
import com.example.vkr.util.View;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;


//@RunWith(SpringRunner.class)
@SpringBootTest(classes = {VkrApplication.class})//, TestConfig.class, TestBeanConfig.class})
@ContextConfiguration(classes = {TestConfig.class, TestBeanConfig.class})
//@Import({TestConfig.class, TestBeanConfig.class})
@ActiveProfiles("test")
public abstract class AbstractTest {

	@Autowired
	ObjectMapper objectMapper;

	protected void setUp() throws Exception {}
	protected void reset() throws Exception {}

	protected String mapToJson(Object obj) throws JsonProcessingException {

//		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper
				.writerWithView(View.UI.class)
				.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper
				.readerWithView(View.UI.class)
				.readValue(json, clazz);
	}
}
