package io.openshift.booster.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FreelancerJPAResource {
	
	@Autowired
	private FreelancerRepository freelancerRepository;
	
	@GetMapping("/freelancers")
	public List<Freelancer> retrieveAllUsers() {
		List<Freelancer> freelancers = new ArrayList<>();
		freelancerRepository.findAll()
		.forEach(freelancers::add);
		return freelancers;
	}
	
	@GetMapping("/freelancers/{id}")
	public Freelancer retreiveFreelancer(@PathVariable String id) {
		Long numId = Long.parseLong(id);
		return freelancerRepository.findOne(numId);
	}
	
}
