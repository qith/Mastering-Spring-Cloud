package pl.piomin.services.customer.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import pl.piomin.services.customer.model.Account;
import pl.piomin.services.customer.model.Customer;
import pl.piomin.services.customer.repository.CustomerRepository;

@RestController
@RequestMapping("/custom")
public class CustomerController {

	@Autowired
	RestTemplate template;
	@Autowired
	CustomerRepository repository;

	@PostMapping("/add")
	public Customer add(@RequestBody Customer customer) {
		return repository.add(customer);
	}

	@PutMapping("/update")
	public Customer update(@RequestBody Customer customer) {
		return repository.update(customer);
	}

	@GetMapping("/{id}")
	public Customer findById(@PathVariable("id") Long id) {
		return repository.findById(id);
	}

	@GetMapping("/withAccounts/{id}")
	public Customer findByIdWithAccounts(@PathVariable("id") Long id) {
		Account[] accounts = template.getForObject("http://account-service/account/customer/{customerId}", Account[].class, id);
		Customer c = repository.findById(id);
		c.setAccounts(Arrays.stream(accounts).collect(Collectors.toList()));
		return c;
	}

	@PostMapping("/ids")
	public List<Customer> find(@RequestBody List<Long> ids) {
		return repository.find(ids);
	}

	@DeleteMapping("/{id}")
	@ApiModelProperty(value = "id",example = "123")
	public void delete(@PathVariable("id") Long id) {
		repository.delete(id);
	}

}
