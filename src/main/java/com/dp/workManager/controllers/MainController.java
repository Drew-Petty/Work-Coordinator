package com.dp.workManager.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dp.workManager.models.Crew;
import com.dp.workManager.models.Document;
import com.dp.workManager.models.Job;
import com.dp.workManager.models.User;
import com.dp.workManager.services.CrewService;
import com.dp.workManager.services.DocumentService;
import com.dp.workManager.services.JobService;
import com.dp.workManager.services.UserService;

@Controller
public class MainController {
	private final UserService userservice;
	private final DocumentService documentService;
	private final CrewService crewService;
	private final JobService jobService;
	public MainController(UserService userservice, DocumentService documentService, CrewService crewService,
			JobService jobService) {
		this.userservice = userservice;
		this.documentService = documentService;
		this.crewService = crewService;
		this.jobService = jobService;
	}
	@RequestMapping("/users")
	public String viewUsers(Principal principal, Model model) {
		return viewByPage(1, model, principal);
	}
	@GetMapping("/users/page/{pageNum}")
	public String viewByPage(@Valid @PathVariable("pageNum")int pageNum, Model model, Principal principal) {
		Page<User> page = userservice.listByPage(pageNum);
		List<User> listUsers = page.getContent();
		long startCount = (pageNum-1)* UserService.USERS_PER_PAGE+1;
		long endCount = startCount+ UserService.USERS_PER_PAGE-1;
		if(endCount>page.getTotalElements()) {
			endCount=page.getTotalElements();
		}
		User currentuser = userservice.findByEmail(principal.getName());
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("currentUser", currentuser);
		model.addAttribute("admin", userservice.isAdmin(currentuser));
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("users",listUsers);
		return "users.jsp";
	}
	@RequestMapping(value="/addUser", method = RequestMethod.POST)
	public String addUser(@Valid @ModelAttribute("user")User user, BindingResult result, RedirectAttributes redirect) {
		if(result.hasErrors()) {
			redirect.addFlashAttribute("error", "There was an error when adding a user");
			return "redirect:/users";
		}
		userservice.saveWithRole(user);
		return "redirect:/users";
	}
	@RequestMapping("/user/{userId}")
	public String viewUser(@PathVariable("userId")Long userId, Model model, Principal principal) {
		User currentUser = userservice.findByEmail(principal.getName());
		User user = userservice.findUserById(userId);
		model.addAttribute("user", user);
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("admin", userservice.isAdmin(currentUser));
		model.addAttribute("profile", userservice.isProfile(user, currentUser));
		return "user.jsp";
	}
	@RequestMapping(value="/file/user/{userId}", method = RequestMethod.POST)
	public String userUploadFile(@PathVariable("userId")Long userId, @RequestParam("file")MultipartFile file) {
		User user = userservice.findUserById(userId);
		if(documentService.isNothing(file)) {
			return "redirect:/user/"+userId;
		}
		Document document = documentService.saveDocument(file);
		documentService.addUser(document, user);
		return "redirect:/user/"+userId;
	}
	@RequestMapping(value="/document/{documentId}", method = RequestMethod.POST )
	public ResponseEntity<Resource> download(@PathVariable("documentId")Long documentId){
		Document document = documentService.findDocumentById(documentId);
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(document.getType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getName()+"\"")
				.body(new ByteArrayResource(document.getData()));
	}
	@RequestMapping(value="/delete/{documentId}/user/{userId}", method = RequestMethod.DELETE)
	public String deleteUserDocument(@PathVariable("documentId")Long documentId, @PathVariable("userId")Long userId) {
		documentService.deleteDocumentById(documentId);
		return "redirect:/user/"+userId;
	}
	@RequestMapping("/crews")
	public String viewCrews(Principal principal, Model model) {
		return crewsByPage(1, model, principal);
	}
	@RequestMapping("/crews/page/{pageNum}")
	public String crewsByPage(@PathVariable("pageNum")int pageNum, Model model, Principal principal) {
		Page<Crew> page = crewService.listByPage(pageNum);
		List<Crew> listCrews = page.getContent();
		long startCount = (pageNum-1)*CrewService.CREWS_PER_PAGE+1;
		long endCount = startCount + CrewService.CREWS_PER_PAGE-1;
		if(endCount>page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		Crew crew = new Crew();
		User currentUser = userservice.findByEmail(principal.getName());
		model.addAttribute("crewless", userservice.usersWithNoCrew());
		model.addAttribute("crew", crew);
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("admin", userservice.isAdmin(currentUser));
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("crews", listCrews);
		return "crews.jsp";
	}
	@RequestMapping(value = "/addCrew", method = RequestMethod.POST)
	public String addCrew(@RequestParam("name")String name, @RequestParam("color")String color, @RequestParam("members")List<Long> memberIds) {
		if(name.isBlank()) {
			return "redirect:/crews";
		}
		Crew crew = new Crew(name, color);
		crewService.saveCrew(crew);
		for(Long id: memberIds) {
			User user = userservice.findUserById(id);
			user.setCrew(crew);
			userservice.saveUser(user);
		}
		return "redirect:/crews";
	}
	@RequestMapping(value = "/delete/crew/{crewId}", method = RequestMethod.DELETE)
	public String deleteCrew(@PathVariable("crewId")Long crewId) {
		Crew crew = crewService.findCrewById(crewId);
		List<User> users = crew.getUsers();
		for(User user:users) {
			user.setCrew(null);
			user.setMyCrew(null);
			userservice.saveUser(user);
		}
		List<Job>jobs = crew.getJobs();
		for(Job job:jobs) {
			job.setCrew(null);
			jobService.saveJob(job);
		}
		crewService.deleteCrewById(crewId);
		return "redirect:/crews";
	}
	@RequestMapping("/crew/{crewId}")
	public String viewCrew(@PathVariable("crewId")Long crewId, Model model, Principal principal) {
		User currentUser = userservice.findByEmail(principal.getName());
		model.addAttribute("crewless", userservice.usersWithNoCrew());
		model.addAttribute("crew", crewService.findCrewById(crewId));
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("admin", userservice.isAdmin(currentUser));
		return "crew.jsp";
	}
	@RequestMapping(value = "/remove/{userId}/crew/{crewId}", method = RequestMethod.POST)
	public String removeFromCrew(@PathVariable("userId")Long userId, @PathVariable("crewId")Long crewId) {
		User user = userservice.findUserById(userId);
		user.setCrew(null);
		user.setMyCrew(null);
		userservice.saveUser(user);
		return "redirect:/crew/"+crewId;
	}
	@RequestMapping(value = "/update/crew/{crewId}", method = RequestMethod.POST)
	public String updateCrew(@PathVariable("crewId")Long crewId,@RequestParam("name")String name, @RequestParam("color")String color, @RequestParam("members")List<Long> memberIds, @RequestParam("lead")Optional<Long> leadId) {
		Crew crew = crewService.findCrewById(crewId);
		if(name.length()>2) {
			crew.setName(name);
		}
		crew.setColor(color);
		
		if(leadId.isPresent()) {
			User lead = userservice.findUserById(leadId.get());
			crew.setLead(lead);
		}
		crewService.saveCrew(crew);
		for(Long id:memberIds) {
			User user = userservice.findUserById(id);
			if (user!=null) {
				user.setCrew(crew);
				userservice.saveUser(user);				
			}
		}
		return "redirect:/crew/"+crewId;
	}
	@RequestMapping("/jobs")
	public String viewJobs(Principal principal, Model model) {
		return jobsByPage(1, model, principal);
	}
	@RequestMapping("/jobs/page/{pageNum}")
	public String jobsByPage(@PathVariable("pageNum")int pageNum, Model model, Principal principal) {
		Page<Job> page = jobService.listByPage(pageNum);
		List<Job> listJobs = page.getContent();
		long startCount = (pageNum-1)*JobService.JOBS_PER_PAGE+1;
		long endCount = startCount + JobService.JOBS_PER_PAGE-1;
		if(endCount>page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		Job job = new Job();
		User currentUser = userservice.findByEmail(principal.getName());
		model.addAttribute("job", job);
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("admin", userservice.isAdmin(currentUser));
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("jobs", listJobs);
		return "jobs.jsp";
	}
	@RequestMapping(value="/addJob", method = RequestMethod.POST)
	public String addJob(@Valid @ModelAttribute("job")Job job, BindingResult result, RedirectAttributes redirect, Model model, Principal principal) {
		if(result.hasErrors()) {
			List<ObjectError> allErrors = result.getAllErrors();
			List<String> errors = new ArrayList<String>();
			for(ObjectError objectError:allErrors) {
				errors.add(objectError.getDefaultMessage());
			}
			model.addAttribute("errors", errors);
			return jobsByPage(1, model, principal);
		}
		jobService.saveJob(job);
		return "redirect:/jobs";
	}
	@RequestMapping("/job/{jobId}")
	public String viewJob(@PathVariable("jobId")Long jobId, Model model, Principal principal) {
		Job job = jobService.findJobById(jobId);
		User currentUser = userservice.findByEmail(principal.getName());
		model.addAttribute("availableCrews", jobService.crewsWithoutConflict(job));
		model.addAttribute("job", job);
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("admin", userservice.isAdmin(currentUser));
		return "job.jsp";
	}
	@RequestMapping(value = "/file/job/{jobId}", method = RequestMethod.POST)
	public String jobUploadFile(@PathVariable("jobId")Long jobId, @RequestParam("file")MultipartFile file) {
		Job job = jobService.findJobById(jobId);
		if(documentService.isNothing(file)) {
			return "redirect:/job/"+jobId;
		}
		Document document = documentService.saveDocument(file);
		documentService.addJob(document, job);
		return "redirect:/job/"+jobId;
	}
	@RequestMapping(value="/delete/{documentId}/job/{jobId}", method = RequestMethod.DELETE)
	public String deleteJobDocument(@PathVariable("documentId")Long documentId,@PathVariable("jobId")Long jobId) {
		documentService.deleteDocumentById(documentId);
		return "redirect:/job/"+jobId;
	}
	@RequestMapping(value = "/update/job/{jobId}", method = RequestMethod.POST)
	public String updateJob(@Valid @ModelAttribute("job")Job job, BindingResult result, RedirectAttributes redirect, @PathVariable("jobId")Long jobId, Model model, Principal principal) {
		if (result.hasErrors()) {
			System.out.println("has errors");
			List<ObjectError> allErrors = result.getAllErrors();
			List<String> errors = new ArrayList<String>();
			for(ObjectError objectError:allErrors) {
				errors.add(objectError.getDefaultMessage());
			}
			model.addAttribute("errors", errors);
			return viewJob(jobId, model, principal);
		}
		Job targetJob = jobService.findJobById(jobId);
		targetJob.setName(job.getName());
		targetJob.setLocation(job.getLocation());
		targetJob.setStartDate(job.getStartDate());
		targetJob.setEndDate(job.getEndDate());
		targetJob.setCrew(null);
		jobService.saveJob(targetJob);
		return "redirect:/job/"+jobId;
	}
	@RequestMapping(value="/delete/job/{jobId}", method = RequestMethod.DELETE)
	public String deleteJob(@PathVariable("jobId")Long jobId) {
		Job job = jobService.findJobById(jobId);
		List<Document> documentList = job.getDocuments();
		for(Document document: documentList) {
			documentService.deleteDocumentById(document.getId());
		}
		jobService.deleteByJobId(jobId);
		return "redirect:/jobs";
	}
	@RequestMapping(value="/assignCrew/job/{jobId}", method = RequestMethod.POST)
	public String assignCrew(@PathVariable("jobId")Long jobId, @RequestParam("crew")Long crewId) {
		Job job = jobService.findJobById(jobId);
		Crew crew = crewService.findCrewById(crewId);
		job.setCrew(crew);
		jobService.saveJob(job);
		return "redirect:/job/"+jobId;
	}
}
