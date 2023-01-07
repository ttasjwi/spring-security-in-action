package com.ttasjwi.ssia.product.adapter.input.web;

import com.ttasjwi.ssia.product.adapter.input.web.dto.ProductDto;
import com.ttasjwi.ssia.product.application.usecases.ProductUseCase;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MainPageController {

    private final ProductUseCase productUsecase;

    @GetMapping("/main")
    public String main(Authentication a, Model model) {

        String memberName = a.getName();
        var products = productUsecase.findAll()
                .stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());

        model.addAttribute("memberName", memberName);
        model.addAttribute("products", products);

        return "main";
    }
}
