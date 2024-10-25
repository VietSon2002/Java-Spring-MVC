package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductController {

    private final ProductService productService;
    private final UploadService uploadService;

    public ProductController(ProductService productService, UploadService uploadService) {
        this.productService = productService;
        this.uploadService = uploadService;
    }

    @GetMapping("/admin/product")
    public String getProductList(Model model) {
        List<Product> products = this.productService.getAllProduct();
        model.addAttribute("products", products);
        return "admin/product/show";
    }

    @RequestMapping("/admin/product/{id}")
    public String getProductDetailPage(Model model, @PathVariable long id) {
        Product product = this.productService.getProductById(id);// {id} va id phai giong nhau
        model.addAttribute("product", product); // truyen qua view
        model.addAttribute("id", id);
        return "admin/product/detail";
    }

    @GetMapping("/admin/product/create")
    public String getProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "/admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String createProductPage(
            @ModelAttribute("newProduct") @Valid Product moimoi, BindingResult result,
            @RequestParam("anhFile") MultipartFile file, Model model) {
        if (result.hasErrors()) {

            return "admin/product/create";
        }
        String product = this.uploadService.handleSaveUploadFile(file, "product");
        moimoi.setImage(product);
        this.productService.handleSaveProduct(moimoi);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteProduct(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("newProduct", new Product());
        return "/admin/product/product-delete";
    }

    @PostMapping("/admin/product/delete")
    public String postDeleteProduct(Model model, @ModelAttribute("newProduct") Product moimoi) {
        this.productService.deleteProduct(moimoi.getId());
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/update/{id}")
    public String getUpdateProductPage(Model model, @PathVariable long id) {
        Product currentProduct = this.productService.getProductById(id);
        model.addAttribute("newProduct", currentProduct);
        return "admin/product/product-update";
    }

    @PostMapping("/admin/product/update")
    public String postUpdateConten(Model model, @ModelAttribute("newProduct") @Valid Product moimoi,
            BindingResult result, @RequestParam("anhFile") MultipartFile file) {

        // validate
        if (result.hasErrors()) {
            return "admin/product/update";
        }

        Product currentProduct = this.productService.getProductById(moimoi.getId());
        if (currentProduct != null) {

            if (!file.isEmpty()) {
                String img = this.uploadService.handleSaveUploadFile(file, "product");
                currentProduct.setImage(img);
            }

            currentProduct.setName(moimoi.getName());
            currentProduct.setPrice(moimoi.getPrice());
            currentProduct.setDetailDesc(moimoi.getDetailDesc());
            currentProduct.setShortDesc(moimoi.getShortDesc());
            currentProduct.setQuantity(moimoi.getQuantity());
            currentProduct.setFactory(moimoi.getFactory());
            currentProduct.setTarget(moimoi.getTarget());
            this.productService.handleSaveProduct(currentProduct);
        }
        return "redirect:/admin/product";
    }
}
