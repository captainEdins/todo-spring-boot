package com.todo.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.todo.demo.model.Todo;
import com.todo.demo.service.TodoService;


@Controller
public class TodoController {

	@Autowired
	private TodoService service;
	
	@RequestMapping("/")
	public String viewHomePage(Model model) {
		List<Todo> listTodo = service.listAll();
		model.addAttribute("listTodo",listTodo);
		//findTopByOrderByIdDesc()
		return "Index";
	}
	
	@RequestMapping("/new")
	public String showNewProductForm(Model model) {
		Todo todo = new Todo();
		model.addAttribute("todo", todo);
		return "new_todo";
	}

	@RequestMapping(value = "/save" , method = RequestMethod.POST)
	public String saveProduct(@Valid @ModelAttribute("todo") Todo todo,BindingResult result) {
		if (result.hasErrors()) {
			return "new_todo";	
		}else {
			service.save(todo);
			return "redirect:/";
		}
	}

	@RequestMapping("/edit/{id}")
	public ModelAndView showEditProductForm(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("edit_todo");
		Todo todo = service.get(id);
		mav.addObject("todo", todo);
		return mav;
	}

	@RequestMapping("/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") int id) {
		service.delete(id);
		return "redirect:/";
	}
}
