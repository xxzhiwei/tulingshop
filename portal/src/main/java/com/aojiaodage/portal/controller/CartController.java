package com.aojiaodage.portal.controller;

import com.aojiaodage.common.util.R;
import com.aojiaodage.portal.dto.CartItemForm;
import com.aojiaodage.portal.entity.Cart;
import com.aojiaodage.portal.service.OmsCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    OmsCartService cartService;

    @GetMapping("/detail")
    public R<?> getDetail() {
        Cart cart = cartService.get();
        return R.ok(cart);
    }

    @PostMapping("/item/add")
    public R<?> add(@RequestBody CartItemForm form) {
        cartService.add(form);
        return R.ok();
    }

    @PostMapping("/item/updateCount")
    public R<?> updateCount(@RequestBody CartItemForm form) {
        cartService.updateCount(form);
        return R.ok();
    }

    @PostMapping("/item/remove")
    public R<?> remove(@RequestBody CartItemForm form) {
        cartService.remove(form);
        return R.ok();
    }

    @PostMapping("/clear")
    public R<?> clear() {
        cartService.clear();
        return R.ok();
    }
}
