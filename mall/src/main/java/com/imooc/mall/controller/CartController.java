package com.imooc.mall.controller;

import com.imooc.mall.consts.MallConst;
import com.imooc.mall.form.CartAddForm;
import com.imooc.mall.form.CartUpdateForm;
import com.imooc.mall.pojo.User;
import com.imooc.mall.service.ICartService;
import com.imooc.mall.vo.CartVo;
import com.imooc.mall.vo.ResponseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created by 廖师兄
 * Updated by hsoluo 2021年3月5日
 */
@RestController
@RequestMapping("/carts")
@Slf4j
@Api(tags = "购物车")
public class CartController {

    @Autowired
    private ICartService cartService;

    @ApiOperation(value = "获取购物车列表")
//    @GetMapping("/carts")
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public ResponseVo<CartVo> list(@ApiParam(hidden = true) HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.list(user.getId());
    }

    @ApiOperation(value = "增加购物车")
//    @PostMapping("/carts")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseVo<CartVo> add(@Valid @RequestBody CartAddForm cartAddForm,
                                  @ApiParam(hidden = true) HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.add(user.getId(), cartAddForm);
    }

    @ApiOperation(value = "更新购物车商品")
    @PutMapping("/carts/{productId}")
    public ResponseVo<CartVo> update(@PathVariable Integer productId,
                                     @Valid @RequestBody CartUpdateForm form,
                                     @ApiParam(hidden = true) HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.update(user.getId(), productId, form);
    }

    @DeleteMapping("/carts/{productId}")
    @ApiOperation(value = "删除购物车商品")
    public ResponseVo<CartVo> delete(@PathVariable Integer productId,
                                     @ApiParam(hidden = true) HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.delete(user.getId(), productId);
    }

    @PutMapping("/carts/selectAll")
    @ApiOperation(value = "购物车全选")
    public ResponseVo<CartVo> selectAll(@ApiParam(hidden = true) HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.selectAll(user.getId());
    }

    @PutMapping("/carts/unSelectAll")
    @ApiOperation(value = "取消购物车全选")
    public ResponseVo<CartVo> unSelectAll(@ApiParam(hidden = true) HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.unSelectAll(user.getId());
    }

    @GetMapping("/carts/products/sum")
    @ApiOperation(value = "计算购物车总价")
    public ResponseVo<Integer> sum(@ApiParam(hidden = true) HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.sum(user.getId());
    }
}
