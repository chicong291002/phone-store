package com.assignments.ecomerce.controller;

import com.assignments.ecomerce.model.Category;
import com.assignments.ecomerce.model.Coupon;
import com.assignments.ecomerce.model.Product;
import com.assignments.ecomerce.service.CategoryService;
import com.assignments.ecomerce.service.CouponService;
import com.assignments.ecomerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class CouponController {

    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CouponService couponService;
    @GetMapping("/coupon/{pageNo}")
    public String getAllCoupons(@PathVariable("pageNo") int pageNo, Model model) {
        Page<Coupon> listCoupon = couponService.pageCoupon(pageNo);
        model.addAttribute("listCoupon", listCoupon);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listCoupon.getTotalPages());
        model.addAttribute("couponNew", new Coupon());

        return "coupon";
    }

    @GetMapping("/couponCustomer/{pageNo}")
    public String getAllCouponCustomer(@PathVariable("pageNo") int pageNo,Model model,Principal principal) {

        List<Product> listProducts = productService.getAllProducts();
        List<Category> categories = categoryService.getAllCategory();
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        Page<Coupon> listCoupon = couponService.pageCoupon(pageNo);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listCoupon.getTotalPages());
        model.addAttribute("listCoupon", listCoupon);
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("categories", categories);
        model.addAttribute("user", userDetails);
        return "couponCustomer";
    }

    @GetMapping("/detailCoupon/{id}")
    public String getDetailCouponCustomer(@PathVariable("id") Integer id,Model model,Principal principal) {
        List<Product> listProducts = productService.getAllProducts();
        List<Category> categories = categoryService.getAllCategory();
        Coupon coupon = couponService.findById(id);
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        model.addAttribute("coupon", coupon);
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("categories", categories);
        return "detailCoupon";
    }

    @PostMapping("/add-coupon")
    public String add(@ModelAttribute("couponNew") Coupon coupon, Model model, RedirectAttributes attributes) {
        try {
            couponService.save(coupon);
            model.addAttribute("couponNew", coupon);
            attributes.addFlashAttribute("success", "Added successfully");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            attributes.addFlashAttribute("failed", "Duplicate name of category, please check again!");
        } catch (Exception e2) {
            e2.printStackTrace();
            attributes.addFlashAttribute("failed", "Error Server");
        }
        return "redirect:/coupon/0";
    }

    @RequestMapping(value = "/findByIdCoupon/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Coupon findById(@PathVariable("id") Integer id){
        return couponService.findById(id);
    }


    @GetMapping("/update-coupon")
    public String update(Coupon coupon, RedirectAttributes attributes) {
        try {
            couponService.update(coupon);
            attributes.addFlashAttribute("success", "Updated successfully");
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to update because duplicate name");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Error server");
        }
        return "redirect:/coupon/0";
    }

    @RequestMapping(value = "/delete-coupon", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String delete(Integer id, RedirectAttributes attributes) {
        try {
            couponService.deleteById(id);
            attributes.addFlashAttribute("success", "Deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to deleted");
        }
        return "redirect:/coupon/0";
    }

    @RequestMapping(value = "/enable-coupon", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enabledProduct(Integer id, RedirectAttributes redirectAttributes, Principal principal) {
        try {
            couponService.enableById(id);
            redirectAttributes.addFlashAttribute("success", "Enabled successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Enabled failed!");
        }
        return "redirect:/coupon/0";
    }

    @GetMapping("/search-coupon/{pageNo}")
    public String searchCoupon(@PathVariable("pageNo") int pageNo,
                                 @RequestParam("keyword") String keyword,
                                 Model model, Principal principal) {
        Page<Coupon> listCoupon = couponService.searchCoupon(pageNo, keyword);
        model.addAttribute("size", listCoupon.getSize());
        model.addAttribute("listCoupon", listCoupon);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", listCoupon.getTotalPages());
        model.addAttribute("couponNew", new Coupon());
        return "coupon";
    }
}
