package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.Node;
import com.example.demo.repository.NodeRepository;
import com.example.demo.service.SequenceGeneratorService;

@RestController
public class NodeController {

	@Autowired
	private NodeRepository nodeRepository;
	@Autowired
	private SequenceGeneratorService service;
	@Autowired
	MongoTemplate mongo;

	@GetMapping("/nodes")
	public List<Node> getAllEmployees() {
		return nodeRepository.findAll();
	}

	@GetMapping("/bin")
	public List<Node> viewDeletedNode() {
		Query query = new Query();
		query.addCriteria(Criteria.where("DeleteStatus").is(true));
		List<Node> nodes = mongo.find(query,Node.class);
		return nodes;
	}
	@GetMapping("/keep")
	public List<Node> viewStoredNode() {
		Query query = new Query();
		query.addCriteria(Criteria.where("DeleteStatus").is(false));
		List<Node> nodes = mongo.find(query,Node.class);
		return nodes;
	}

	@PostMapping("/create/node")
	public Node createNode(@Validated @RequestBody Node nodes) {
		nodes.setId(service.getSequenceNumber(Node.SEQUENCE_NAME));
		return nodeRepository.save(nodes);
	}

	@PutMapping("/edit/node/{id}")
	public String editNode(@PathVariable(value = "id") Long nodeId,
			@Validated @RequestBody Node details) {
		Node nodes = nodeRepository.getById(nodeId);
		if(nodes==null)
		{
			return "Node doesn't exist";
		}
		else
		{
		nodes.setNote(details.getNote());
		nodes.setTitle(details.getTitle());
		nodeRepository.save(nodes);
		return "Node Edited";
		}
	}

	@PutMapping("/delete/node/{id}")
	public String deleteNode(@PathVariable(value = "id") Long nodeId) {
		Node node=nodeRepository.getById(nodeId);
		if(node==null)
		{
			return "Node doesn't exist";
		}
		else if(node.isDeleteStatus())
		{
			return "Node already deleted";
		}
		else
		{
			node.setDeleteStatus(true);
			nodeRepository.save(node);
			return "Node deleted";
		}
	}

	@PutMapping("/pin/node/{id}")
	public String pinNode(@PathVariable(value = "id") Long nodeId) {
		Node node=nodeRepository.getById(nodeId);
		if(node==null)
		{
			return "Node doesn't exist";
		}
		else if(node.isPinned())
		{
			return "Node already deleted";
		}
		else
		{
			node.setDeleteStatus(true);
			nodeRepository.save(node);
			return "Node deleted";
		}
	}

	@PutMapping("/restore/node/{id}")
	public String restoreNode(@PathVariable(value = "id") Long nodeId) {
		Node node=nodeRepository.getById(nodeId);
		if(node==null)
		{
			return "Node doesn't exist";
		}
		else if(!node.isDeleteStatus())
		{
			return "Node already restored";
		}
		else
		{
			node.setDeleteStatus(false);
			nodeRepository.save(node);
			return "Node restored";
		}
	}

	@PutMapping("/unpin/node/{id}")
	public String unpinNode(@PathVariable(value = "id") Long nodeId) {
		Node node=nodeRepository.getById(nodeId);
		if(node==null)
		{
			return "Node doesn't exist";
		}
		else if(!node.isDeleteStatus())
		{
			return "Node already unpinned";
		}
		else
		{
			node.setDeleteStatus(true);
			nodeRepository.save(node);
			return "Node pinned";
		}
	}
}
