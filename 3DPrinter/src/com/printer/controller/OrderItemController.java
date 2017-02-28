package com.printer.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.printer.entities.Cart;
import com.printer.entities.OrderItem;
import com.printer.entities.User;
import com.printer.service.CartService;
import com.printer.service.FileUpload;
import com.printer.service.OrderItemService;

@Controller  
@RequestMapping("/orderitem")
public class OrderItemController {
	
	@Autowired
	private OrderItemService itemService;
	@Autowired
	private CartService cartService;
	@Autowired
	private FileUpload fileAction;
	
	//-----------------------------生成新详细订单-------------------------//
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getCarts(@RequestBody OrderItem item,HttpSession session){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		
		int flag=itemService.addOrderItem(item);
		User u = (User)session.getAttribute("User");
		Cart cart = new Cart();
		cart.setModelID(item.getModelID());
		cart.setUserID(u.getUserID());
		
		if(flag>0){
			cartService.deleteCart(cart);
			modelMap.put("state", true);
			modelMap.put("msg", "订单详情添加成功");
		}else{
			modelMap.put("state", false);
			modelMap.put("msg", "订单详情添加出错");
		}
		return modelMap;
	}
	
	//-----------------------------生成新详细订单-------------------------//
	@RequestMapping(value="/price",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> changePrice(@RequestBody OrderItem item){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		double price=item.getPrice();
		int mid=item.getModelID();
		String oid=item.getOrderID();
		int flag=itemService.updateOrderItem(price, mid, oid);
		if(flag>0){
			modelMap.put("state", true);
			modelMap.put("msg", "订单详情报价成功");
		}else{
			modelMap.put("state", false);
			modelMap.put("msg", "订单详情报价出错");
		}
		return modelMap;
	}
	
	//-----------------------------设计师修改详细订单-------------------------//
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateItem(@RequestBody OrderItem item){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		System.out.println(item.getQuantity());
		int flag=itemService.updateItem(item);	
		if(flag>0){
			modelMap.put("state", true);
			modelMap.put("msg", "订单详情修改成功");
		}else{
			modelMap.put("state", false);
			modelMap.put("msg", "订单详情修改失败");
		}
		return modelMap;
	}
	
	//-----------------------------获取设计师成品列表-------------------------//
	@RequestMapping(value="/products/{adID}",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getItems(@PathVariable("adID")int adID){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("satte", true);
		modelMap.put("data", itemService.queryItems(adID));
		return modelMap;
	}
	
	//----------------------- 上传设计师成品图片 -----------------------------------//
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addItemImg(@RequestParam("itemImg") CommonsMultipartFile file,
			HttpSession session){  
		String folder="images/items/";
		String path=session.getServletContext().getRealPath("/")+"\\files"+"\\images"+"\\items\\";
		return fileAction.uploadFile(file, path,folder);
	}
	
	//----------------------- 上传设计师成品标题 -----------------------------------//
	@RequestMapping(value="/uploaditem",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addItemTitle(@RequestBody OrderItem item){  
		Map<String,Object> modelMap = new HashMap<String,Object>();
		int flag=itemService.uploadOrderItem(item);	
		if(flag>0){
			modelMap.put("state", true);
			modelMap.put("msg", "订单详情修改成功");
		}else{
			modelMap.put("state", false);
			modelMap.put("msg", "订单详情修改失败");
		}
		return modelMap;
	}
}
