package junit;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.hamcrest.Matchers.is;

import io.openshift.booster.service.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FreelancerJPAResource.class)
public class Freelancer4JFreelancersTestCases {

	MockMvc mockMvc;
	
	@Autowired
	FreelancerJPAResource freelancerJPAResource;
	
	@MockBean
	Freelancer freelancer;
	
	@MockBean
	FreelancerRepository freelancerRepository;	

    
    private List<Freelancer> freelancerList;	// List of samples freelancers
    private Freelancer oneFreelancer;			// One sample of freelancer
    private Freelancer emptyFreelancer;			// Empty freelancer object
    
	
    @Before
    public void setup() throws Exception {
    	this.mockMvc = standaloneSetup(this.freelancerJPAResource).build();
		
    	Freelancer fl01 = new Freelancer(11L, "Amy", "Lee", "al@hotmail.com");
    	Freelancer fl02 = new Freelancer(12L, "Billy", "Sze", "bs@gmail.com");
    	Freelancer fl03 = new Freelancer(13L, "Chris", "Wong", "cw@yahoo.com");
   	
    	freelancerList = new ArrayList<>();
    	freelancerList.add(fl01);
    	freelancerList.add(fl02);
    	freelancerList.add(fl03);
    	oneFreelancer = fl02;
    	emptyFreelancer = null;
    }
    
	@Test
	public void Case09AllFreelancers() throws Exception {
		when(freelancerJPAResource.retrieveAllUsers()).thenReturn(freelancerList);
		mockMvc.perform(get("/freelancers").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].email", is("al@hotmail.com")))
			.andExpect(jsonPath("$[1].email", is("bs@gmail.com")))
			.andExpect(jsonPath("$[2].email", is("cw@yahoo.com")));
	}
	
	@Test
	public void Case10OneFreelancer() throws Exception {
		when(freelancerJPAResource.retreiveFreelancer("12")).thenReturn(oneFreelancer);
		mockMvc.perform(get("/freelancers/12").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("email", is("bs@gmail.com")));
	}
	
	@Test
	public void Case11FreelancerNotFound() throws Exception {
		when(freelancerJPAResource.retreiveFreelancer("99")).thenReturn(emptyFreelancer);
		mockMvc.perform(get("/freelancers/99").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(""));
	}
	
}
