package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.model.Category;
import com.assignments.ecomerce.model.OrderDetail;
import com.assignments.ecomerce.model.Orders;
import com.assignments.ecomerce.model.User;
import com.assignments.ecomerce.service.CategoryService;
import com.assignments.ecomerce.service.OrderDetailService;
import com.assignments.ecomerce.service.OrderService;
import com.assignments.ecomerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class AccountController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/account/{pageNo}")
    public String showAccountUser(@PathVariable("pageNo") int pageNo, Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        User user = userService.findByEmailUser(principal.getName());
        Page<Orders> listOrder = orderService.pageOrdersById(pageNo, user.getId());
        List<String> formattedPrices = new ArrayList<>();
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.getDefault());
        decimalFormatSymbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,###", decimalFormatSymbols);

        for (Orders orders : listOrder) {
            String formattedPrice = decimalFormat.format(orders.getTotal());
            formattedPrices.add(formattedPrice);
        }
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categories", categories);
        model.addAttribute("formattedPrices", formattedPrices);
        model.addAttribute("size", listOrder.getSize());
        model.addAttribute("listOrder", listOrder);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listOrder.getTotalPages());
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("userDetail", user);
        return "accountUser";
    }

    @GetMapping("/EditAccount/{pageNo}/{userId}")
    public String editAccountUser(@PathVariable("pageNo") int pageNo,
                                  @PathVariable("userId") Integer userId, Model model, Principal principal) {
        User user = userService.getById(userId);
        List<Category> categories = categoryService.getAllCategory();

        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("categories", categories);
        model.addAttribute("userDetail", user);
        return "editAccountUser";
    }

    @GetMapping("/OrderDetailByUser/{pageNo}/{orderId}")
    public String OrderDetailByUser(@PathVariable("pageNo") int pageNo,
                               @PathVariable("orderId") Integer orderId, Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categories", categories);
        List<Orders> listOrder = new ArrayList<>();
        Orders order = orderService.getOrderById(orderId);
        if (order != null) {
            listOrder.add(order);
        }

        List<OrderDetail> listOrderDetail = orderDetailService.findAllByOrderId(orderId);
        model.addAttribute("order", order);
        model.addAttribute("listOrderDetail", listOrderDetail);
        model.addAttribute("userDetails", userDetails);
        return "orderDetailUser";
    }

    @PostMapping("/EditAccount/{pageNo}/{userId}")
    public String editAccountUser(@PathVariable("pageNo") int pageNo,
                                  @PathVariable("userId") Integer userId, @ModelAttribute("userDetail")
                                  User user, RedirectAttributes attributes) {
        try {
            userService.update(user);
            attributes.addFlashAttribute("success", "Update successfully");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to update");
        }
        return "redirect:/EditAccount/" + pageNo + "/" + userId;
    }
}
