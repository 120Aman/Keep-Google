package com.example.demo.controller;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.example.demo.model.Node;
import com.example.demo.repository.NodeRepository;
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

	@BeforeEach
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(nodeController1).build();
	}

	@Test
	public void testGetAllNodes() throws Exception {
		Node mockNode1 = new Node();
		mockNode1.setId(1L);
		mockNode1.setTitle("klsqwd");
		mockNode1.setNote("sdfc");
		mockNode1.setDeleteStatus(true);
		mockNode1.setPinned(false);

		Node mockNode2 = new Node();
		mockNode2.setId(2L);
		mockNode2.setTitle("acaac");
		mockNode2.setNote("sdc");
		mockNode2.setDeleteStatus(true);
		mockNode2.setPinned(false);

		Node mockNode3 = new Node();
		mockNode3.setId(3L);
		mockNode3.setTitle("klsqwdsfw");
		mockNode3.setNote("sdfcsfcw");
		mockNode3.setDeleteStatus(false);
		mockNode3.setPinned(false);

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
		Node mockNode1 = new Node();
		mockNode1.setId(1L);
		mockNode1.setTitle("klsqwd");
		mockNode1.setNote("sdfc");
		mockNode1.setDeleteStatus(true);
		mockNode1.setPinned(false);
		nodeRepository.save(mockNode1);
		Node mockNode2 = new Node();
		mockNode2.setId(2L);
		mockNode2.setTitle("acaac");
		mockNode2.setNote("sdc");
		mockNode2.setDeleteStatus(true);
		mockNode2.setPinned(false);
		nodeRepository.save(mockNode2);
		Node mockNode3 = new Node();
		mockNode3.setId(3L);
		mockNode3.setTitle("klsqwdsfw");
		mockNode3.setNote("sdfcsfcw");
		mockNode3.setDeleteStatus(false);
		mockNode3.setPinned(false);
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
		Node mockNode1 = new Node();
		mockNode1.setId(1L);
		mockNode1.setTitle("klsqwd");
		mockNode1.setNote("sdfc");
		mockNode1.setDeleteStatus(true);
		mockNode1.setPinned(false);
		nodeRepository.save(mockNode1);
		Node mockNode2 = new Node();
		mockNode2.setId(2L);
		mockNode2.setTitle("acaac");
		mockNode2.setNote("sdc");
		mockNode2.setDeleteStatus(false);
		mockNode2.setPinned(false);
		nodeRepository.save(mockNode2);
		Node mockNode3 = new Node();
		mockNode3.setId(3L);
		mockNode3.setTitle("klsqwdsfw");
		mockNode3.setNote("sdfcsfcw");
		mockNode3.setDeleteStatus(false);
		mockNode3.setPinned(false);
		nodeRepository.save(mockNode3);
		List<Node> NodeList = new ArrayList<>();
		NodeList.add(mockNode1);
		NodeList.add(mockNode2);
		NodeList.add(mockNode3);
		List<Node> NodeList1 = new ArrayList<>();
		for (Node i : NodeList) {
			if (!(i.isDeleteStatus())) {
				NodeList1.add(i);
			}
		}
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

	}

	@Test 
	public void testEditNode() throws Exception {
	 
		String URI = "/edit/node/11";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = "Node with this ID doesn't exist.";
		String outputInJson = result.getResponse().getContentAsString();
		assertEquals(expectedJson, outputInJson);
	  
	  }

	@Test
	public void testDeleteNode() throws Exception {

		Node mockNode = new Node();
		mockNode.setId(1L);
		mockNode.setTitle("acaac");
		mockNode.setNote("sdc");
		mockNode.setDeleteStatus(false);
		mockNode.setPinned(false);
		Mockito.when(nodeRepository.getById(1L)).thenReturn(mockNode);

		String URI2 = "/delete/node/1";
		RequestBuilder requestBuilder2 = MockMvcRequestBuilders.put(URI2);

		MvcResult result2 = mockMvc.perform(requestBuilder2).andReturn();
		String expectedJson2 = "Node deleted.";
		String outputInJson2 = result2.getResponse().getContentAsString();
		assertEquals(expectedJson2, outputInJson2);

		Node mockNode2 = new Node();
		mockNode2.setId(2L);
		mockNode2.setTitle("acaac");
		mockNode2.setNote("sdc");
		mockNode2.setDeleteStatus(true);
		mockNode2.setPinned(false);
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

		Node mockNode = new Node();
		mockNode.setId(1L);
		mockNode.setTitle("acaac");
		mockNode.setNote("sdc");
		mockNode.setDeleteStatus(false);
		mockNode.setPinned(false);
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

		Node mockNode2 = new Node();
		mockNode2.setId(2L);
		mockNode2.setTitle("acaac");
		mockNode2.setNote("sdc");
		mockNode2.setDeleteStatus(false);
		mockNode2.setPinned(true);
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

		Node mockNode = new Node();
		mockNode.setId(1L);
		mockNode.setTitle("acaac");
		mockNode.setNote("sdc");
		mockNode.setDeleteStatus(true);
		mockNode.setPinned(false);
		Mockito.when(nodeRepository.getById(1L)).thenReturn(mockNode);

		String URI2 = "/restore/node/1";
		RequestBuilder requestBuilder2 = MockMvcRequestBuilders.put(URI2);

		MvcResult result2 = mockMvc.perform(requestBuilder2).andReturn();
		String expectedJson2 = "Node restored.";
		String outputInJson2 = result2.getResponse().getContentAsString();
		assertEquals(expectedJson2, outputInJson2);

		Node mockNode2 = new Node();
		mockNode2.setId(2L);
		mockNode2.setTitle("acaac");
		mockNode2.setNote("sdc");
		mockNode2.setDeleteStatus(false);
		mockNode2.setPinned(false);
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

		Node mockNode = new Node();
		mockNode.setId(1L);
		mockNode.setTitle("acgyvuc");
		mockNode.setNote("sdc");
		mockNode.setDeleteStatus(true);
		mockNode.setPinned(true);
		Mockito.when(nodeRepository.getById(1L)).thenReturn(mockNode);

		String URI2 = "/unpin/node/1";
		RequestBuilder requestBuilder2 = MockMvcRequestBuilders.put(URI2);

		MvcResult result2 = mockMvc.perform(requestBuilder2).andReturn();
		String expectedJson2 = "Node unpinned.";
		String outputInJson2 = result2.getResponse().getContentAsString();
		assertEquals(expectedJson2, outputInJson2);

		Node mockNode2 = new Node();
		mockNode2.setId(2L);
		mockNode2.setTitle("acaac");
		mockNode2.setNote("sdc");
		mockNode2.setDeleteStatus(false);
		mockNode2.setPinned(false);
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
