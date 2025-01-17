package pl.piomin.services.account.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pl.piomin.services.account.model.Account;
import pl.piomin.services.account.repository.AccountRepository;

@RestController
@RequestMapping("account")
public class AccountController {

	@Autowired
	AccountRepository repository;

	@PostMapping
	public Account add(@RequestBody Account account) {
		return repository.add(account);
	}

	@PutMapping
	public Account update(@RequestBody Account account) {
		return repository.update(account);
	}

	@PutMapping("/withdraw/{id}/{amount}")
	public Account withdraw(@PathVariable("id") Long id, @PathVariable("amount") int amount) {
		Account account = repository.findById(id);
		account.setBalance(account.getBalance() - amount);
		return repository.update(account);
	}

	@GetMapping("/{id}")
	public Account findById(@PathVariable("id") Long id) {
		return repository.findById(id);
	}

	@GetMapping("/customer/{customerId}")
	public List<Account> findByCustomerId(@PathVariable("customerId") Long customerId) {
		return repository.findByCustomer(customerId);
	}

	@PostMapping("/ids")
	public List<Account> find(@RequestBody List<Long> ids) {
		return repository.find(ids);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		repository.delete(id);
	}

}
