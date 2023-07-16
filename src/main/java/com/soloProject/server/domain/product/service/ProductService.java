package com.soloProject.server.domain.product.service;

import com.soloProject.server.domain.balance.entity.Balance;
import com.soloProject.server.domain.member.entity.Member;
import com.soloProject.server.domain.member.service.MemberService;
import com.soloProject.server.domain.product.dto.ProductDto;
import com.soloProject.server.domain.product.entity.Product;
import com.soloProject.server.domain.product.repository.ProductRepository;
import com.soloProject.server.global.exception.BusinessLogicException;
import com.soloProject.server.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    MemberService memberService;
    Balance balance;

    public Product createProduct (ProductDto.Post productPostDto) {
        Product product = new Product();
        product.setProductId(productPostDto.getProductId());
        product.setName(productPostDto.getName());
        product.setBuyPrice(productPostDto.getBuyPrice());
        product.setSellPrice(productPostDto.getSellPrice());
        product.setQuantity(productPostDto.getQuantity());

        productRepository.save(product);
        return product;
    }

    public void sellProduct(int memberId, int productId, int quantity) {
        Product verifiedProduct = findVerifiedProduct(productId);

        int money = verifiedProduct.getSellPrice() * quantity;

        memberService.updateResult(memberId, money);
        balance.updateBalance(money);
    }

    public void buyProduct(int memberId, int productId, int quantity) {
        Product verifiedProduct = findVerifiedProduct(productId);

        int money = verifiedProduct.getBuyPrice() * quantity;

        memberService.updateResult(memberId, money);
        balance.updateBalance(money);
    }


    public Product findVerifiedProduct(long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Product findProduct = optionalProduct.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return findProduct;
    }
}
