package pl.piomin.services.boot.controller;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.piomin.services.boot.data.PersonRepository;
import pl.piomin.services.boot.model.Person;
import pl.piomin.services.boot.service.PersonCounterService;

@RestController
@RequestMapping("/person")
@Api(value = "/person", tags = "Operations about user")
public class PersonController {
	
	@Autowired
	private PersonRepository repository;
	@Autowired
	private PersonCounterService counterService;
	
	@GetMapping
	@ApiImplicitParam()
	public List<Person> findAll() {
		return repository.findAll();
	}

	@ApiOperation(value="根据用户id查询用户信息",notes="随边说点啥")
	@ApiImplicitParams({
			@ApiImplicitParam(name="id",value="用户ID",required=true,paramType="path"),
	})
	@GetMapping("/{id}")
	public Person findById(@RequestParam("id") String id) {
		return repository.findOne(id);
	}
	
	@PostMapping
	public Person add(@RequestBody Person p) {
		p = repository.save(p);
		counterService.countNewPersons();
		return p;
	}
	
	@DeleteMapping("/{id}")
	public void delete(@RequestParam("id") String id) {
		repository.delete(id);
		counterService.countDeletedPersons();
	}
	
	@PutMapping
	public void update(@RequestBody Person p) {
		repository.save(p);
	}

	@GetMapping("/lastname/{lastName}")
	public List<Person> findByLastName(@RequestParam("lastName") String lastName) {
		return repository.findByLastName(lastName);
	}
	
	@GetMapping("/age/{age}")
	public List<Person> findByAgeGreaterThan(@RequestParam("age") int age) {
		return repository.findByAgeGreaterThan(age);
	}
	
}
