package com.dp.workManager.controllers;

import java.security.Principal;
import java.text.DateFormatSymbols;
import java.util.Calendar;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dp.workManager.services.CalendarService;
import com.dp.workManager.services.UserService;

@Controller
public class HomeController {
	private final UserService userService;
	private final CalendarService calendarService;
	
	public HomeController(UserService userService, CalendarService calendarService) {
		this.userService = userService;
		this.calendarService = calendarService;
	}
	@RequestMapping(value={"/"})
	public String goHome() {
		return "redirect:/home";
	}
	@RequestMapping("/home")
	public String home(Principal principal, Model model, HttpSession session) {
		Calendar calendar = Calendar.getInstance();
		Calendar today = new Calendar.Builder().setCalendarType("iso8601").setDate(calendar.get(1), calendar.get(2), calendar.get(5)).build();
		if(session.getAttribute("calendar")!=null) {
			calendar = (Calendar) session.getAttribute("calendar");
		}
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();
		session.setAttribute("calendar",calendar);
		model.addAttribute("today", today);
		model.addAttribute("layout", calendarService.getLayout(calendar));
		model.addAttribute("calendar", calendar);
		model.addAttribute("month", months[calendar.get(2)]);
		model.addAttribute("currentUser", userService.findByEmail(principal.getName()));
		return "home.jsp";
	}
	@RequestMapping("/previous")
	public String previousMonth(HttpSession session) {
		Calendar calendar = (Calendar) session.getAttribute("calendar");
		calendar.add(2, -1);
		return "redirect:/home";
	}
	@RequestMapping("/next")
	public String nextMonth(HttpSession session) {
		Calendar calendar = (Calendar) session.getAttribute("calendar");
		calendar.add(2, 1);
		return "redirect:/home";
	}
}
