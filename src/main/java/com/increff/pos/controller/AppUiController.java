package com.increff.pos.controller;

import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppUiController extends AbstractUiController {

	@Autowired
	private OrderService service;
	@RequestMapping(value = "/ui/home")
	public ModelAndView home() {
		return mav("home.html");
	}

	@RequestMapping(value = "/ui/employee")
	public ModelAndView employee() {
		return mav("employee.html");
	}

	@RequestMapping(value = "/ui/admin")
	public ModelAndView admin() {
		return mav("user.html");
	}

	@RequestMapping(value = "/ui/brand")
	public ModelAndView brand() {
		return mav("brand.html");
	}

	@RequestMapping(value = "/ui/product")
	public ModelAndView product() {
		return mav("product.html");
	}

	@RequestMapping(value = "/ui/inventory")
	public ModelAndView inventory() {
		return mav("inventory.html");
	}

	@RequestMapping(value = "/ui/inventoryReport")
	public ModelAndView inventoryReport() {
		return mav("inventoryReport.html");
	}

	@RequestMapping(value = "/ui/order")
	public ModelAndView order() { return mav("order.html"); }

	@RequestMapping(value = "/ui/order-item/{id}")
	public ModelAndView orderItem(@PathVariable Integer id) throws ApiException {
		if(service.get(id).getStatus()==false) {
			return mav("orderItem.html",id);
		}
		else{
			return mav("order.html");
		}
	}

	@RequestMapping(value = "/ui/dailyReport")
	public ModelAndView dailyReport() {
		return mav("dailyReport.html");
	}

	@RequestMapping(value = "/ui/salesReport")
	public ModelAndView salesReport() { return mav("salesReport.html"); }

}
