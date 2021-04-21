package com.example.demo.controller;

import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.example.demo.model.Node;
import com.example.demo.repository.NodeRepository;
import com.example.demo.service.SequenceGeneratorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(PowerMockRunner.class)
@AutoConfigureMockMvc
public class NodeControllerTest {

	@InjectMocks
	private Node node = new Node();

	@InjectMocks
	NodeController nodeController1 = new NodeController();

	NodeRepository nodeRepository = Mockito.mock(NodeRepository.class);

	@Autowired
	private MockMvc mockMvc;

	MongoTemplate mongo = Mockito.mock(MongoTemplate.class);

	SequenceGeneratorService service = Mockito.mock(SequenceGeneratorService.class);

	@BeforeEach
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(nodeController1).build();
	}

	@Test
	public void testGetAllNodes() throws Exception {
		Node mockNode1 = new Node(1L, "wdxer", "ervrwe", true, false);
		Node mockNode2 = new Node(2L, "4ref", "rg4fr4", true, false);
		Node mockNode3 = new Node(3L, "derccer", "rvcre", false, false);

		List<Node> NodeList = new ArrayList<>();
		NodeList.add(mockNode1);
		NodeList.add(mockNode2);
		NodeList.add(mockNode3);
		when(nodeController1.getAllNodes()).thenReturn(NodeList);
		String URI = "/nodes";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(NodeList);
		String outputInJson = result.getResponse().getContentAsString();
		System.out.println(expectedJson);
		System.out.println(outputInJson);
		assertEquals(expectedJson, outputInJson);
	}

	@Test
	public void testViewDeletedNode() throws Exception {
		Node mockNode1 = new Node(1L, "fetf", "rg54gt", true, false);
		nodeRepository.save(mockNode1);
		Node mockNode2 = new Node(2L, "d4fr", "rgf4c", true, false);
		nodeRepository.save(mockNode2);
		Node mockNode3 = new Node(3L, "rf5e", "tgvre", false, false);
		nodeRepository.save(mockNode3);

		List<Node> NodeList = new ArrayList<>();
		NodeList.add(mockNode1);
		NodeList.add(mockNode2);
		NodeList.add(mockNode3);
		List<Node> NodeList1 = new ArrayList<>();
		for (Node i : NodeList)
			if (i.isDeleteStatus())
				NodeList1.add(i);
		when(nodeController1.viewDeletedNode()).thenReturn(NodeList1);
		String URI = "/bin";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(NodeList1);
		String outputInJson = result.getResponse().getContentAsString();
		assertEquals(expectedJson, outputInJson);
	}

	@Test
	public void testViewStoredNode() throws Exception {
		Node mockNode1 = new Node(1L, "wd43", "fc4e2f", true, false);
		nodeRepository.save(mockNode1);
		Node mockNode2 = new Node(2L, "ewwf43", "rf34", false, false);
		nodeRepository.save(mockNode2);
		Node mockNode3 = new Node(3L, "ed43f", "v43h6b", false, false);
		nodeRepository.save(mockNode3);

		List<Node> NodeList = new ArrayList<>();
		NodeList.add(mockNode1);
		NodeList.add(mockNode2);
		NodeList.add(mockNode3);
		List<Node> NodeList1 = new ArrayList<>();
		for (Node i : NodeList)
			if (!(i.isDeleteStatus()))
				NodeList1.add(i);
		when(nodeController1.viewStoredNode()).thenReturn(NodeList1);
		String expectedJson = this.mapToJson(NodeList1);
		String URI = "/keep";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String outputInJson = result.getResponse().getContentAsString();
		assertEquals(expectedJson, outputInJson);
	}

	@Test
	public void testCreateNode() throws Exception {
		assertEquals("Node can't be created: You must not provide ID in RequestBody.",
				nodeController1.createNode(new Node(1L, "wde3", "d23", false, false)));
		assertEquals("Node can't be created: You can not create a node with deleteStatus = true.",
				nodeController1.createNode(new Node("wde3", "d23", true, false)));
		assertEquals("Node can't be created: You can not create a node with pinned = true.",
				nodeController1.createNode(new Node("wde3", "d23", false, true)));
		assertEquals("Node can't be created: Title can have maximum 30 characters.",
				nodeController1.createNode(new Node("wde3re wde3re wde3re wde3re wde3re", "d23", false, false)));
		Node mockNode = new Node("wde3re wde3re", "d23");
		Mockito.when(nodeController1.createNode(mockNode)).thenReturn("Node created");
		assertEquals("Node created",
				nodeController1.createNode(new Node("wde3re wde3r", "d23", false, false)));
		assertEquals("Node created",
				nodeController1.createNode(new Node("wde3re wde3r", "d23", false)));

	}

	
	 /* @Test public void testEditNode() throws Exception {
	  Node mockNode= new Node();
	  String URI = "/edit/node/11";
	  RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI);
	  
	  MvcResult result = mockMvc.perform(requestBuilder).andReturn();
	  String expectedJson = "Node with this ID doesn't exist."; 
	  String outputInJson = result.getResponse().getContentAsString(); 
	  assertEquals(expectedJson,outputInJson);
	  
	 }*/
	  

	@Test
	public void testDeleteNode() throws Exception {
		Node mockNode = new Node(1L, "wdrc", "rcf45", false, false);
		Mockito.when(nodeRepository.getById(1L)).thenReturn(mockNode);

		String URI2 = "/delete/node/1";
		RequestBuilder requestBuilder2 = MockMvcRequestBuilders.put(URI2);
		MvcResult result2 = mockMvc.perform(requestBuilder2).andReturn();
		String expectedJson2 = "Node deleted.";
		String outputInJson2 = result2.getResponse().getContentAsString();
		assertEquals(expectedJson2, outputInJson2);

		Node mockNode2 = new Node(2L, "tcv", "ytuy", true, false);
		Mockito.when(nodeRepository.getById(2L)).thenReturn(mockNode2);

		String URI1 = "/delete/node/2";
		RequestBuilder requestBuilder1 = MockMvcRequestBuilders.put(URI1);
		MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();
		String expectedJson1 = "Node is already present in the bin.";
		String outputInJson1 = result1.getResponse().getContentAsString();
		assertEquals(expectedJson1, outputInJson1);

		String URI = "/delete/node/11";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = "Node with this ID doesn't exist.";
		String outputInJson = result.getResponse().getContentAsString();
		assertEquals(expectedJson, outputInJson);
	}

	@Test
	public void testPinNode() throws Exception {
		Node mockNode = new Node(1L, "ed4", "sxgrc", false, false);
		Mockito.when(nodeRepository.getById(1L)).thenReturn(mockNode);

		String URI2 = "/pin/node/1";
		RequestBuilder requestBuilder2 = MockMvcRequestBuilders.put(URI2);
		MvcResult result2 = mockMvc.perform(requestBuilder2).andReturn();
		String expectedJson2 = "Node pinned.";
		String outputInJson2 = result2.getResponse().getContentAsString();
		assertEquals(expectedJson2, outputInJson2);

		String URI = "/pin/node/10";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = "Node with this ID doesn't exist.";
		String outputInJson = result.getResponse().getContentAsString();
		assertEquals(expectedJson, outputInJson);

		Node mockNode2 = new Node(2L, "ef4e", "regt", false, true);
		Mockito.when(nodeRepository.getById(2L)).thenReturn(mockNode2);

		String URI1 = "/pin/node/2";
		RequestBuilder requestBuilder1 = MockMvcRequestBuilders.put(URI1);
		MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();
		String expectedJson1 = "Node is already pinned.";
		String outputInJson1 = result1.getResponse().getContentAsString();
		assertEquals(expectedJson1, outputInJson1);
	}

	@Test
	public void testRestoreNode() throws Exception {
		Node mockNode = new Node(1L, "extrc", "trcyuyj", true, false);
		Mockito.when(nodeRepository.getById(1L)).thenReturn(mockNode);

		String URI2 = "/restore/node/1";
		RequestBuilder requestBuilder2 = MockMvcRequestBuilders.put(URI2);
		MvcResult result2 = mockMvc.perform(requestBuilder2).andReturn();
		String expectedJson2 = "Node restored.";
		String outputInJson2 = result2.getResponse().getContentAsString();
		assertEquals(expectedJson2, outputInJson2);

		Node mockNode2 = new Node(2L, "chtjvyh", "xthc", false, false);
		Mockito.when(nodeRepository.getById(2L)).thenReturn(mockNode2);

		String URI1 = "/restore/node/2";
		RequestBuilder requestBuilder1 = MockMvcRequestBuilders.put(URI1);
		MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();
		String expectedJson1 = "Node is not present in bin.";
		String outputInJson1 = result1.getResponse().getContentAsString();
		assertEquals(expectedJson1, outputInJson1);

		String URI = "/unpin/node/10";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = "Node with this ID doesn't exist.";
		String outputInJson = result.getResponse().getContentAsString();
		assertEquals(expectedJson, outputInJson);
	}

	@Test
	public void testUnpinNode() throws Exception {
		Node mockNode = new Node(1L, "yrtfyj", "rzeyetj", true, true);
		Mockito.when(nodeRepository.getById(1L)).thenReturn(mockNode);

		String URI2 = "/unpin/node/1";
		RequestBuilder requestBuilder2 = MockMvcRequestBuilders.put(URI2);
		MvcResult result2 = mockMvc.perform(requestBuilder2).andReturn();
		String expectedJson2 = "Node unpinned.";
		String outputInJson2 = result2.getResponse().getContentAsString();
		assertEquals(expectedJson2, outputInJson2);

		Node mockNode2 = new Node(2L, "thcjg", "yctjhm", false, false);
		Mockito.when(nodeRepository.getById(2L)).thenReturn(mockNode2);

		String URI1 = "/unpin/node/2";
		RequestBuilder requestBuilder1 = MockMvcRequestBuilders.put(URI1);
		MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();
		String expectedJson1 = "Node is already unpinned.";
		String outputInJson1 = result1.getResponse().getContentAsString();
		assertEquals(expectedJson1, outputInJson1);

		String URI = "/unpin/node/10";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = "Node with this ID doesn't exist.";
		String outputInJson = result.getResponse().getContentAsString();
		assertEquals(expectedJson, outputInJson);
	}

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

}
