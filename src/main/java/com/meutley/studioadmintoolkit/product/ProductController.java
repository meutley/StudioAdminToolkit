package com.meutley.studioadmintoolkit.product;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;

    public ProductController(
        ProductService productService
    ) {
        this.productService = productService;
    }
    
    @GetMapping(value = {"", "/index"})
    public String index(Model model) {
        List<ProductDto> products = this.productService.getAll();
        model.addAttribute("products", products);
        return "product/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        if (!model.containsAttribute("product")) {
            model.addAttribute("product", new ProductDto());
        }
        return "product/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute ProductDto product, final BindingResult bindingResult,
        final RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.product", bindingResult);
            redirectAttributes.addFlashAttribute("product", product);
            return "redirect:/product/create";
        }

        this.productService.create(product);
        return "redirect:/product";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        if (!model.containsAttribute("product")) {
            model.addAttribute("product", this.productService.getById(id));
        }
        return "product/edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(@Valid @ModelAttribute ProductDto product, final BindingResult bindingResult,
        final RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.product", bindingResult);
            redirectAttributes.addFlashAttribute("product", product);
            redirectAttributes.addAttribute("id", product.getId());
            return "redirect:/product/{id}/edit";
        }

        this.productService.edit(product);
        return "redirect:/product";
    }

}