//package com.demo.shopdemo.shopoperation.controller;
//
//import com.demo.shopdemo.shopoperation.service.IShopService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.servlet.ModelAndView;
//
///**
// * 商品管理
// *
// * @author: Nanzhou
// * @version: v0.0.1
// * @Date: 2023/11/5
// */
//@Controller
//public class ShopOperationController {
//    @Autowired
//    private IShopService shopService;
//    @GetMapping("/shopList")
//    public ModelAndView shopList() {
//        return new ModelAndView("shopoperation/shopList");
//    }
//    @GetMapping("/shopModify")
//    public ModelAndView shopModify() {
//        return new ModelAndView("shopoperation/shopModify");
//    }
//    @GetMapping("/shopDown")
//    public ModelAndView shopDelete() {
//        return new ModelAndView("shopoperation/shopDelete");
//    }
//    @GetMapping("/shopAdd")
//    public ModelAndView shopAdd() {
//        return new ModelAndView("shopoperation/shopAdd");
//    }
//    @GetMapping("/shopDelete")
//    public ModelAndView shopDelete(Integer id) {
//        return new ModelAndView("shopoperation/shopDelete");
//    }
//}
