package com.soloProject.server.domain.product.service;

import com.soloProject.server.domain.balance.entity.Balance;
import com.soloProject.server.domain.balance.service.BalanceService;
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
    private final ProductRepository productRepository;
    private final MemberService memberService;
    private final BalanceService balanceService;

    public Product createProduct (ProductDto.Create productPostDto) {
        Product product = new Product();
        product.setProductId(productPostDto.getProductId());
        product.setName(productPostDto.getName());
        product.setBuyPrice(productPostDto.getBuyPrice());
        product.setSellPrice(productPostDto.getSellPrice());
        product.setQuantity(productPostDto.getQuantity());

        productRepository.save(product);
        return product;
    }

    public void sellProduct(long memberId, long productId, int quantity) {
        Product verifiedProduct = findVerifiedProduct(productId);

        int money = verifiedProduct.getSellPrice() * quantity;

        int curQuantity = verifiedProduct.getQuantity();

        verifiedProduct.setQuantity(curQuantity - quantity);
        memberService.updateResult(memberId, money);
        balanceService.updateBalance(money);
    }

    public void buyProduct(int memberId, int productId, int quantity) {
        Product verifiedProduct = findVerifiedProduct(productId);

        int money = verifiedProduct.getBuyPrice() * quantity;

        int curQuantity = verifiedProduct.getQuantity();

        verifiedProduct.setQuantity(curQuantity + quantity);

        memberService.updateResult(memberId, money);
        balanceService.updateBalance(money);
    }


    public Product findVerifiedProduct(long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Product findProduct = optionalProduct.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return findProduct;
    }
}
