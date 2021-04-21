package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Candidate;
import com.example.demo.model.Date;
import com.example.demo.model.Mentors;
import com.example.demo.model.Request;
import com.example.demo.repository.CandidateRepository;
import com.example.demo.repository.MentorsRepository;


@RestController
public class SDPMentorFeedbackApiController {
	@Autowired
	private CandidateRepository candidateRepository;
	@Autowired
	private MentorsRepository mentorsRepository;
	
	@GetMapping("/")
	public List<Candidate> getAllNodes() {
		return candidateRepository.findAll();
	}

	@PostMapping("/Candidates/post")
	public Candidate createCandidate(@Validated @RequestBody Candidate candidate) {
		return candidateRepository.save(candidate);
	}

	@PostMapping("/Mentors/register")
	public String registerMentors(@Validated @RequestBody Mentors newMentors) {
		Mentors mentors = new Mentors();
		List<Mentors> mentorsList = mentorsRepository.findAll();
		for (Mentors mentors1 : mentorsList) {
			if (mentors1.getUsername().equals(newMentors.getUsername())) {
				return "username_ALREADY_EXISTS";
			} else if (mentors1.getId().equals(newMentors.getId())) {
				return "id_ALREADY_EXISTS";
			}
		}
		mentors.setFullName(newMentors.getFullName());
		mentors.setId(newMentors.getId());
		mentors.setUsername(newMentors.getUsername());
		mentors.setPassword(newMentors.getPassword());
		mentors.setLoggedIn(false);
		mentorsRepository.save(newMentors);
		return "Successfully Registered.";
	}

	@PutMapping("/Mentors/login")
	public String loginMentors(@Validated @RequestBody Mentors newMentors) {
		String UserName = newMentors.getUsername();
		String s = newMentors.getPassword();
		Mentors mentors = mentorsRepository.getByusername(UserName);
		String u = mentors.getPassword();
		if (u.equals(s)) {
			mentors.setLoggedIn(true);
			mentorsRepository.save(mentors);
			return "Logged in.";
		} else {
			return "Please provide valid details.";
		}
	}
	@PutMapping("/Mentors/logout/{username}")
	public String logoutMentors(@PathVariable(value="username") String userName) {
		Mentors mentor = mentorsRepository.getByusername(userName);
		if(mentor!=null) {
			mentor.setLoggedIn(false);
			mentorsRepository.save(mentor);
			return "Logged out.";
		} else {
			return "Username Doesn't Exist.";
		}
	}

	@PutMapping("/get/candidate/topics/details/{username}")
	public String getTopicDetails(@PathVariable(value="username") String userName,@Validated @RequestBody Request request) {
		Mentors mentor=mentorsRepository.getByusername(userName);
		if(mentor!=null) {
		if(mentor.isLoggedIn())
		{
		Candidate candidate1 = candidateRepository.getById(request.getId());
		String date1 = request.getDate();
		if (candidate1 != null) {
			List<Date> dateList = candidate1.getDates();
			for (Date date2 : dateList) {
				if (date2.getDate().equals(date1)) {
					return "CandidateStatus:"+date2.isCandidateStatus()+" , "+date2.getTopics().toString();
				}
			}
		}
			return "Invalid Id.";
		}
return "Please Login First.";
	}
		return "Username Doesn't Exist.";
}
}
