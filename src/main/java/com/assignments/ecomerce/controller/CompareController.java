package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.model.Category;
import com.assignments.ecomerce.model.Coupon;
import com.assignments.ecomerce.model.Product;
import com.assignments.ecomerce.model.User;
import com.assignments.ecomerce.service.CategoryService;
import com.assignments.ecomerce.service.CouponService;
import com.assignments.ecomerce.service.ProductService;
import com.assignments.ecomerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Controller
public class CompareController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CouponService couponService;

    @Autowired
    UserDetailsService userDetailsService;

    @GetMapping("/compare/{productId}/{pageNo}")
    public String compareProducts(@PathVariable("pageNo") int pageNo, @PathVariable("productId") Integer productId, Model model, Principal principal) {
        Product product = productService.getProductById(productId);
        User user = userService.findByEmail(principal.getName());
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Page<Coupon> listCoupon = couponService.pageCoupon(pageNo);
        List<Product> listProducts = productService.getAllProducts();
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("name", userDetails);
        model.addAttribute("userId", user.getId());
        model.addAttribute("product", product);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listCoupon.getTotalPages());
        model.addAttribute("listCoupon", listCoupon);
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("categories", categories);
        model.addAttribute("userDetails", userDetails);
        return "compareProductUser";
    }
}
