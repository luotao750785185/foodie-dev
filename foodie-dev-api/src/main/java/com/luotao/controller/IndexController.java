package com.luotao.controller;

import com.luotao.enums.YesOrNo;
import com.luotao.pojo.Carousel;
import com.luotao.pojo.Category;
import com.luotao.pojo.vo.CategoryVO;
import com.luotao.pojo.vo.NewItemsVO;
import com.luotao.service.CarouselService;
import com.luotao.service.CategoryService;
import com.luotao.utils.IMOOCJSONResult;
import com.luotao.utils.JsonUtils;
import com.luotao.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : luo
 * @date : 2020/3/13 17:26
 */
@Api(value = "首页",tags ={"首页展示的相关接口"})
@RestController
@RequestMapping("index")
public class IndexController {
    @Autowired
    private CarouselService carouselService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation(value="获取首页轮播图列表",notes = "获取首页轮播图列表",httpMethod = "GET")
    @GetMapping("/carousel")
    public IMOOCJSONResult carousel(){
        String carouselStr=redisOperator.get("carousel");
        List<Carousel> list=new ArrayList<>();
        if (StringUtils.isBlank(carouselStr)) {
            list = carouselService.queryAll(YesOrNo.YES.type);
            redisOperator.set("carousel", JsonUtils.objectToJson(list));//value参数是字符串
        }else{
            list=JsonUtils.jsonToList(carouselStr,Carousel.class);
        }
        /**
         * 万一轮播图改变了，此缓存就需要重置，三种方法。
         * 1：在后台管理系统中主动删除此缓存的，以便自动重置。
         * 2：定时重置，比如每天凌晨三点重置。
         * 3：设置缓存过期时间。
         */

        return IMOOCJSONResult.ok(list);
    }

    /**
     * 打开首页加载大分类，主页中靠近大分类再加载它下面的子分类，子分类存在则不再加载
     * 这样提升首页展示速度，提升用户体验
     * @return
     */
    @ApiOperation(value="获取商品分类（一级分类）",notes = "获取商品分类（一级分类）",httpMethod = "GET")
    @GetMapping("/cats")
    public IMOOCJSONResult cats(){
        List<Category> list =new ArrayList<>();
        String catsStr=redisOperator.get("cats");
        if (StringUtils.isBlank(catsStr)) {
            list = categoryService.queryAllRootLevelCat();
            redisOperator.set("cats", JsonUtils.objectToJson(list));//value参数是字符串
        }else{
            list=JsonUtils.jsonToList(catsStr,Category.class);
        }
        return IMOOCJSONResult.ok(list);
    }

    /**
     * 主页中靠近大分类获取商品子分类，子分类存在则不再加载
     * @return
     */
    @ApiOperation(value="获取商品子分类",notes = "获取商品子分类",httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")//接收路径参数
    public IMOOCJSONResult subCat(
            @ApiParam(name="rootCatId",value = "一级分类id",required = true)//给前端解释参数
            @PathVariable Integer rootCatId){
        //为空不查询，避免一些爬虫和攻击。
        if (rootCatId == null) {
            return IMOOCJSONResult.errorMsg("子类不存在");
        }

        List<CategoryVO> list =new ArrayList<>();
        String subCatStr=redisOperator.get("subCat:"+rootCatId);//加分号是作为一个文件（subCat），里面存放subCat:rootCatId数据
        if (StringUtils.isBlank(subCatStr)) {
            list = categoryService.getSubCatList(rootCatId);
            redisOperator.set("subCat:"+rootCatId, JsonUtils.objectToJson(list));//value参数是字符串
        }else{
            list=JsonUtils.jsonToList(subCatStr,CategoryVO.class);
        }
        return IMOOCJSONResult.ok(list);
    }

    /**
     * 查询每个一级分类下的最新六条数据
     * @return
     */
    @ApiOperation(value="查询每个一级分类下的最新六条数据",notes = "查询每个一级分类下的最新六条数据",httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")//接收路径参数
    public IMOOCJSONResult sixNewItems(
            @ApiParam(name="rootCatId",value = "一级分类id",required = true)//给前端解释参数
            @PathVariable Integer rootCatId){
        //为空不查询，避免一些爬虫和攻击。
        if (rootCatId == null) {
            return IMOOCJSONResult.errorMsg("子类不存在");
        }
        List<NewItemsVO> list = categoryService.getSixNewItemsLazy(rootCatId);
        return IMOOCJSONResult.ok(list);
    }
}
