package groovyMVC.controllers;

import groovyMVC.utils.SpringPropertyUtils;
import groovyMVC.validation.ValidationResult;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class AccountController {

	@RequestMapping(value = "/account/list")
	public String list(Model model, @ModelAttribute("AttributeName") final String message ){

		List<Account> accounts = accountService.findAll();
		model.addAttribute("accounts", accounts);
		return "account/list";
	}

	@RequestMapping(value = "/account/create")
	public String create(Model model){

		model.addAttribute("account", new Account());
		return "account/create";
	}

	@RequestMapping(value = "/account/show/{id}")
	public String show(Model model, @PathVariable("id") Integer id){

		model.addAttribute("account", accountService.findOne(id));
		return "account/show";
	}

	@RequestMapping(value = "/account/edit/{id}")
	public String edit(Model model, @PathVariable("id") Integer id){

		model.addAttribute("account", accountService.findOne(id));
		return "account/edit";
	}

	@RequestMapping(value = "/account/save")
	public String save(Model model, Account account, RedirectAttributes redirectAttributes){

		ValidationResult result = accountValidator.validate(account);

		if (result.hasErrors()) {
			model.addAttribute("errors", result.getErrors());
			model.addAttribute("account", account);
			return "account/create";
		}

		accountService.save(account);

		redirectAttributes.addFlashAttribute("message", SpringPropertyUtils.getProperty("message.account.created"));

		return "account/list";
	}

	@RequestMapping(value = "/account/update/{id}")
	public String update(Model model, Account account, @PathVariable("id") Integer id, RedirectAttributes redirectAttributes){

		ValidationResult result = accountValidator.validate(account);

		if (result.hasErrors()) {
			model.addAttribute("errors", result.getErrors());
			model.addAttribute("account", account);
			return "account/edit";
		}

		Account currentAccount = accountService.findOne(id);
		//TODO: Loop through model and populate setters

		accountService.save(account);

		redirectAttributes.addFlashAttribute("message", SpringPropertyUtils.getProperty("message.account.updated"));

		return "account/list";
	}



}
