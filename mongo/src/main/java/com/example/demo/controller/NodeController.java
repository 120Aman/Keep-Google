package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.Node;
import com.example.demo.repository.NodeRepository;
import com.example.demo.service.SequenceGeneratorService;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@RestController
public class NodeController {

	@Autowired
	private NodeRepository nodeRepository;
	@Autowired
	private SequenceGeneratorService service;
	
	@Autowired
	MongoTemplate mongo;

	@GetMapping("/nodes")
	public List<Node> getAllNodes() {
		return nodeRepository.findAll();
	}

	@GetMapping("/bin")
	public  List<Node> viewDeletedNode() {
		Query query = new Query();
		query.addCriteria(Criteria.where("DeleteStatus").is(true));
		List<Node> nodes = mongo.find(query, Node.class);
		return nodes;
	}

	@GetMapping("/keep")
	public List<Node> viewStoredNode() {
		Query query = new Query();
		query.addCriteria(Criteria.where("DeleteStatus").is(false));
		List<Node> nodes = mongo.find(query, Node.class);
		return nodes;
	}

	@PostMapping("/create/node")
	public String createNode(@Validated @RequestBody Node details) {
		Node nodes = new Node();
		if(details.getId()!=null){
			return "Node can't be created: You must not provide ID in RequestBody.";
		}
		else if(details.isDeleteStatus()){
			return "Node can't be created: You can not create a node with deleteStatus = true.";
		}
        else if(details.isPinned()){
			return "Node can't be created: You can not create a node with pinned = true.";
		}
        else if(details.getTitle().length()>30){
	       return "Node can't be created: Title can have maximum 30 characters.";
        }
		else {
		nodes.setId(service.getSequenceNumber(Node.SEQUENCE_NAME));
		nodes.setTitle(details.getTitle());
		nodes.setNote(details.getNote());
		nodes.setDeleteStatus(false);
		nodes.setPinned(false);
		nodeRepository.save(nodes);
		return "Node created";
		}
	}

	@PutMapping("/edit/node/{id}")
	public String editNode(@PathVariable(value = "id") Long nodeId,
			@Validated @RequestBody Node details) {
		Node node = nodeRepository.getById(nodeId);
		if(node==null)
		{
			return "Node with this ID doesn't exist.";
		}
		else
		{
		node.setNote(details.getNote());
		node.setTitle(details.getTitle());
		nodeRepository.save(node);
		return "Node Edited.";
		}
	}

	@PutMapping("/delete/node/{id}")
	public String deleteNode(@PathVariable(value = "id") Long nodeId) {
		Node node=nodeRepository.getById(nodeId);
		if(node==null)
		{
			return "Node with this ID doesn't exist.";
		}
		else if(node.isDeleteStatus())
		{
			return "Node is already present in the bin.";
		}
		else
		{
			node.setDeleteStatus(true);
			nodeRepository.save(node);
			return "Node deleted.";
		}
	}

	@PutMapping("/pin/node/{id}")
	public String pinNode(@PathVariable(value = "id") Long nodeId) {
		Node node=nodeRepository.getById(nodeId);
		if(node==null)
		{
			return "Node with this ID doesn't exist.";
		}
		else if(node.isPinned())
		{
			return "Node is already pinned.";
		}
		else
		{
			node.setPinned(true);
			nodeRepository.save(node);
			return "Node pinned.";
		}
	}

	@PutMapping("/restore/node/{id}")
	public String restoreNode(@PathVariable(value = "id") Long nodeId) {
		Node node=nodeRepository.getById(nodeId);
		if(node==null)
		{
			return "Node with this ID doesn't exist.";
		}
		else if(!node.isDeleteStatus())
		{
			return "Node is not present in bin.";
		}
		else
		{
			node.setDeleteStatus(false);
			nodeRepository.save(node);
			return "Node restored.";
		}
	}

	@PutMapping("/unpin/node/{id}")
	public String unpinNode(@PathVariable(value = "id") Long nodeId) {
		Node node=nodeRepository.getById(nodeId);
		if(node==null)
		{
			return "Node with this ID doesn't exist.";
		}
		else if(!node.isPinned())
		{
			return "Node is already unpinned.";
		}
		else
		{
			node.setPinned(false);
			nodeRepository.save(node);
			return "Node unpinned.";
		}
	}
}
