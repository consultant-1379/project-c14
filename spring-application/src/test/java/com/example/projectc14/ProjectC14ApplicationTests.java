package com.example.projectc14;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProjectC14ApplicationTests {

	@Value( "${resources.storeFolderPath}" )
	private String exportDirectory;

	@Test
	void applicationStartTest() {
		ProjectC14Application.main(new String[] {});
		assertThat(exportDirectory, is(not(emptyString())));
	}

	//Tests From Templates class
	@Test
	void checkTemplatesCreatesSuccessfully() {
		Map<Integer, Template> testMap = new ProjectC14Application().templates();
		assertEquals(9, testMap.size());
	}

	@Test
	void checkTemplatesInitQuestionReturnsCorrectly() {
		Map<Integer, Template> testMap = new ProjectC14Application().templates();
		String result = testMap.get(0).getInitQuestion();
		assertEquals("Do you have a collaborative culture (e.g. big but not specific/highly detailed goals with no fixed delivery dates)?", result);
	}

	@Test
	void checkTemplatesHeaderReturnsCorrectly() {
		Map<Integer, Template> testMap = new ProjectC14Application().templates();
		List<String> result = testMap.get(0).getHeader();
		assertEquals("Individualist", result.get(0));
	}

	@Test
	void checkTemplatesQuestionsReturnsCorrectly() {
		Map<Integer, Template> testMap = new ProjectC14Application().templates();
		List<String> result = testMap.get(0).getQuestions();
		assertEquals("Project managers coordinate between all the different teams working on the same project, and the teams have highly specialised responsibilities.", result.get(0));
	}

	@Test
	void checkTemplatesQuestionNameReturnsCorrectly() {
		Map<Integer, Template> testMap = new ProjectC14Application().templates();
		String result = testMap.get(0).getQuestionName();
		assertEquals("Culture", result);
	}

	//Tests for Response class
	@Test
	void checkIfResponse1SetsAndGetsCorrectly() {
		Response response = new Response();
		response.setResponse1(0);
		response.setResponse2(0);
		response.setResponse3(0);
		response.setResponse4(0);

		assertEquals(0,response.getResponse1());
		assertEquals(0,response.getResponse2());
		assertEquals(0,response.getResponse3());
		assertEquals(0,response.getResponse4());
	}

	//Tests for ResponseStore class
	@Test
	void checkResponseStoreCreatesMap() {
		ResponseStore responseStore = new ResponseStore();
		Map<Integer, List<Integer>> testMap = responseStore.getResponses();
		assertEquals(9, testMap.size());
	}

	@Test
	void checkResponseStoreSetsQNumToZero() {
		ResponseStore responseStore = new ResponseStore();
		assertEquals(0, responseStore.getQuestionNumber());
	}

	@Test
	void checkResponseStoreIncreasesQNum() {
		ResponseStore responseStore = new ResponseStore();
		responseStore.increaseQuestionNumber();
		assertEquals(1, responseStore.getQuestionNumber());
	}

	@Test
	void checkResponseStoreSetsResponses() {
		ResponseStore responseStore = new ResponseStore();
		List<Integer> testList = Arrays.asList(1,0,1,0);
		responseStore.setResponse(2, 1, 0, 1, 0);
		Assertions.assertArrayEquals(testList.toArray(), responseStore.getResponses().get(2).toArray());
	}

	//Tests for ResponseHandler class
	@Test
	void checkListOfZeroReturnsCorrect() {
		ResponseHandler handler = new ResponseHandler();
		double result = handler.calculateResult(Arrays.asList(0,0,0,0));
		assertEquals(2.5, result);
	}

	@Test
	void checkListOfOnesReturnsCorrect() {
		ResponseHandler handler = new ResponseHandler();
		double result = handler.calculateResult(Arrays.asList(1,1,1,1));
		assertEquals(2.5, result);
	}

	@Test
	void checkFirstValueIsOneReturnsCorrect() {
		ResponseHandler handler = new ResponseHandler();
		double result = handler.calculateResult(Arrays.asList(1,0,0,0));
		assertEquals(2, result);
	}

	@Test
	void checkSecondValueIsOneReturnsCorrect() {
		ResponseHandler handler = new ResponseHandler();
		double result = handler.calculateResult(Arrays.asList(0,1,0,0));
		assertEquals(3, result);
	}

	@Test
	void checkFirstAndSecondValueIsOneReturnsCorrect() {
		ResponseHandler handler = new ResponseHandler();
		double result = handler.calculateResult(Arrays.asList(1,1,0,0));
		assertEquals(2.5, result);
	}

	@Test
	void checkFirstAndLAstValueIsOneReturnsCorrect() {
		ResponseHandler handler = new ResponseHandler();
		double result = handler.calculateResult(Arrays.asList(1,0,0,1));
		assertEquals(2.5, result);
	}

	@Test
	void checkMapIsCorrectlyProcessedByHandler() {
		ResponseHandler handler = new ResponseHandler();
		ResponseStore store = new ResponseStore();
		store.setResponse(1, 0, 0, 1, 0);
		store.setResponse(7, 0, 0, 0, 1);
		handler.addResults(store.getResponses());
		assertEquals("[2.5, 2.0, 2.5, 2.5, 2.5, 2.5, 2.5, 3.0, 2.5]",handler.getResultMap().toString());
		assertEquals("2.5,2.0,2.5,2.5,2.5,2.5,2.5,3.0,2.5",handler.toString());
	}

	//Tests for FileHandler class
	@Test
	void checkIfFileNameIsProduced() throws IOException {
		FileHandler handler = new FileHandler();
		String result = handler.generateFileID();
		assertThat(result, is(not(emptyString())));
	}

	/*@Test
	void checkIfFileNameIsProducedWhenSavingFile() throws IOException {
		FileHandler handler = new FileHandler();
		String result = handler.saveGraph("1,1,1,2.3,1.5,2,3.3,1.8,2.2", exportDirectory);
		assertThat(result, is(not(emptyString())));
	}

	@Test
	void checkIfListIsReturnedWhenLoadingFile() throws IOException {
		FileHandler handler = new FileHandler();
		List<Double> result = handler.loadGraph("test", exportDirectory);
		assertEquals("[2.5, 2.0, 2.5, 2.5, 2.5, 2.5, 2.5, 3.0, 2.5]", result.toString());
	}*/

	//Tests for AppController
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ResponseStore store;

	@Test
	void checkResponseStoreCreatedOnStartUp() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk())
				.andExpect(model().attribute("myStore", is(notNullValue())));
	}

	@Test
	void checkInitQuestions() throws Exception {
		when(store.getQuestionNumber()).thenReturn(0);
		mockMvc.perform(get("/initQuestion")).andExpect(status().isOk())
				.andExpect(model().attribute("QuestionName","Culture"))
				.andExpect(model().attribute("Header",Arrays.asList("Individualist", "Predictive", "Iterative", "Collaborative", "Experimental")))
				.andExpect(model().attribute("Questions","Do you have a collaborative culture (e.g. big but not specific/highly detailed goals with no fixed delivery dates)?"));
	}

	@Test
	void checkQuestions() throws Exception {
		when(store.getQuestionNumber()).thenReturn(0);
		mockMvc.perform(get("/followUpQuestion")).andExpect(status().isOk())
				.andExpect(model().attribute("QuestionName","Culture"))
				.andExpect(model().attribute("Header",Arrays.asList("Individualist", "Predictive", "Iterative", "Collaborative", "Experimental")))
				.andExpect(model().attribute("Questions",Arrays.asList("Project managers coordinate between all the different teams working on the same project, and the teams have highly specialised responsibilities.",
						"Our development teams focus on achieving small, defined objectives quickly and then moving immediately to the next one.",
						"A lot of up-front planning goes into documenting each step of a project before it even begins.",
						"Each team contain a mix of members whose different areas of expertise cover the full spectrum of skills needed for crafting a releasable increment.")));
	}

	//Tests for GraphController
	@MockBean
	private FileHandler fileHandler;

	@Test
	void checkGraphWorks() throws Exception {
		when(fileHandler.saveGraph(Mockito.any(), Mockito.any())).thenReturn("123456");
		mockMvc.perform(get("/graph")).andExpect(status().isOk())
				.andExpect(model().attribute("GraphId","123456"))
				.andExpect(model().attribute("LongValues",Arrays.asList(2.5,2.5,2.5,2.5,2.5,2.5,2.5,2.5,2.5)));
	}

	@Test
	void checkLoadGraphWorks() throws Exception {
		when(fileHandler.loadGraph(Mockito.any(), Mockito.any())).thenReturn(Arrays.asList(2.5,2.5,2.5,2.5,2.5,2.5,2.5,2.5,2.5));
		mockMvc.perform(get("/loadGraph").param("graphID", "123456")).andExpect(status().isOk())
				.andExpect(model().attribute("GraphId","123456"))
				.andExpect(model().attribute("LongValues",Arrays.asList(2.5,2.5,2.5,2.5,2.5,2.5,2.5,2.5,2.5)));
	}

}