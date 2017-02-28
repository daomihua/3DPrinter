package com.printer.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.printer.entities.DOrder;
import com.printer.entities.Model;
import com.printer.entities.Order;
import com.printer.entities.User;
import com.printer.service.FileUpload;
import com.printer.service.OrderService;

@Controller  
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private FileUpload fileAction;
	
	//-----------------------------生成新订单-------------------------//
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addOrder(@RequestBody Order order){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		Timestamp time = new Timestamp(System.currentTimeMillis());
		//生成订单号
		DateFormat df=new SimpleDateFormat("yyyyMMddHHmmss");
		String orderid=df.format(time);
		order.setOrderID(orderid);
		order.setOrderState("待确认");
		order.setOrderDate(time);
		
		int flag=orderService.addOrder(order);
		
		if(flag>0){
			modelMap.put("state", true);
			modelMap.put("orderid",orderid);
			modelMap.put("msg", "订单添加成功");
		}else{
			modelMap.put("state", false);
			modelMap.put("orderid",null);
			modelMap.put("msg", "订单添加出错");
		}
		return modelMap;
	}
	
	//-----------------------------代价工商根据状态获取订单-------------------------//
	@RequestMapping(value="/ad/{state}",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getOrders(@PathVariable("state") String state){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		switch(state){
		case "0":
			modelMap.put("data", orderService.queryAdOrders("已取消"));
			break;
		case "1":
			modelMap.put("data", orderService.queryAdOrders("待确认"));
			break;
		case "2":
			modelMap.put("data", orderService.queryAdOrders("待报价"));
			break;
		case "3":
			modelMap.put("data", orderService.queryAdOrders("待付款"));
			break;
		case "4":
			modelMap.put("data", orderService.queryAdOrders("待生产"));
			break;
		case "5":
			modelMap.put("data", orderService.queryAdOrders("待发货"));
			break;
		case "7":
			modelMap.put("data", orderService.queryAdOrders("已签收"));
			break;
		default:
			modelMap.put("data", orderService.queryAdOrders("全部"));
			break;
		}
		modelMap.put("state", true);
//		modelMap.put("orderNum", orderService.queryOrderNum());
		return modelMap;
	}
	
	//-----------------------------用户根据状态获取订单-------------------------//
	@RequestMapping(value="/{state}",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getAdOrders(@PathVariable("state") int state,HttpSession session){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		User u=new User();
		u=(User)session.getAttribute("User");
		String username=u.getUserName();
		switch(state){
		case 0:
			modelMap.put("data", orderService.queryOrders("待确认",username));
			break;
		case 1:
			modelMap.put("data", orderService.queryOrders("待付款",username));
			break;
		case 2:
			modelMap.put("data", orderService.queryOrders("待生产",username));
			break;
		case 3:
			modelMap.put("data", orderService.queryOrders("待发货",username));
			break;
		case 4:
			modelMap.put("data", orderService.queryOrders("已签收",username));
			break;
		default:
			modelMap.put("data", null);
			break;
		}
		modelMap.put("state", true);
//		modelMap.put("orderNum", orderService.queryOrderNum());
		return modelMap;
	}

	
	//--------------------------- 获取一个订单和详情 ---------------------------//
	@RequestMapping(value="/order/{id}",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getOrder(@PathVariable("id") String id){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("state", true);
		modelMap.put("data",orderService.queryOrder(id));
		modelMap.put("msg", "获取订单");
		return modelMap;
	}
	
	//-----------------------------用户、设计师、代价工商修改订单状态-------------------------//
	@RequestMapping(value="/state/{state}/orderID/{orderID}",method=RequestMethod.PATCH)
	@ResponseBody
	public Map<String,Object> updateOrders(@PathVariable("state") int state,@PathVariable("orderID") String id){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		System.out.println(state+"--"+id);
		Timestamp t = new Timestamp(System.currentTimeMillis());
		int result=0;
		switch(state){
		case 0:
			result=orderService.changeOrderSate(id, "已取消", t);
			break;
		case 1:
			result = orderService.changeOrderSate(id, "待确认", t);
			break;
		case 2:
			result = orderService.changeOrderSate(id, "待报价", t);
			break;
		case 3:
			result = orderService.changeOrderSate(id, "待付款", t);
			break;
		case 4:
			result = orderService.changeOrderSate(id, "待生产", t);
			break;
		case 5:
			result = orderService.changeOrderSate(id, "待发货", t);
			break;
		case 6:
			result = orderService.changeOrderSate(id, "待收货", t);
			break;
		case 7:
			result = orderService.changeOrderSate(id, "已签收", t);
			break;
		default:
			result = 0;
			break;
		}
		if(result>0){
			modelMap.put("state", true);
			modelMap.put("msg", "订单状态修改成功！");
		}else{
			modelMap.put("state", false);
			modelMap.put("msg", "订单状态修改失败！");
		}
		return modelMap;
	}
	
	//-----------------------------代价工商修改订单状态、报价-------------------------//
	@RequestMapping(value="order/orderID/{oid}/price/{price}",method=RequestMethod.PATCH)
	@ResponseBody
	public Map<String,Object> putOrderPrice(@PathVariable("oid") String oid,@PathVariable("price") double price){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		int flag=orderService.offerOrder(price, oid);
		if(flag>0){
			modelMap.put("state", true);
			modelMap.put("msg", "更新订单总价成功");
		}else{
			modelMap.put("state", false);
			modelMap.put("msg", "更新订单总价失败");
		}
		return modelMap;
	}
	
	//-----------------------------设计师根据状态获取订单列表-------------------------//
	@RequestMapping(value="/designer/{adID}/state/{state}",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> designerOrder(@PathVariable("adID") int adID,@PathVariable("state") int state){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("state", true);
		modelMap.put("data", orderService.queryOrderByAD(adID, state));
		modelMap.put("msg", "获取设计师订单");
		return modelMap;
	}
	
	//-----------------------------设计师修改订单-------------------------//
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateOrder(@RequestBody Order order){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		int flag=orderService.updateOrder(order);
		if(flag>0){
			modelMap.put("state", true);
			modelMap.put("msg", "修改订单信息成功");
		}else{
			modelMap.put("state", false);
			modelMap.put("msg", "修改订单信息失败");
		}
		return modelMap;
	}
	
//---------------------------------- 设计订单 ------------------------------//
//---------------------------------- 设计订单 ------------------------------//
	
	//------------------- 添加设计订单 -------------------------//
	@RequestMapping(value="/design/addorder",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addDOrder(@RequestBody DOrder order){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		Timestamp time = new Timestamp(System.currentTimeMillis());
		//生成订单号
		DateFormat df=new SimpleDateFormat("yyyyMMddHHmmss");
		String orderid=df.format(time);
		order.setDOrderID(orderid);
		order.setState("待确认");
		order.setDOrderDate(time);
		
		int r=orderService.addDOrder(order);
		if(r>0){
			modelMap.put("state", true);
			modelMap.put("msg", "设计订单添加成功");
		}else{
			modelMap.put("state", false);
			modelMap.put("msg", "设计订单添加出错");
		}
		return modelMap;
	}
	
	//------------------- 设计订单报价 -------------------------//
	@RequestMapping(value="/design/quote",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> quoteDOrder(@RequestBody DOrder order){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		Timestamp time = new Timestamp(System.currentTimeMillis());
		order.setQuoteDate(time);
		order.setState("待付款");
		int r=orderService.quoteDOrder(order);
		if(r>0){
			modelMap.put("state", true);
			modelMap.put("msg", "设计订单报价成功");
		}else{
			modelMap.put("state", false);
			modelMap.put("msg", "设计订单报价出错");
		}
		return modelMap;
	}
	
	//------------------- 设计师返回设计文件 -------------------------//
	@RequestMapping(value="/design/back",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> backDOrder(@RequestBody DOrder order){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		Timestamp time = new Timestamp(System.currentTimeMillis());
		order.setDeliverDate(time);
		order.setState("已设计");
		int r=orderService.backDesignFile(order);
		if(r>0){
			modelMap.put("state", true);
			modelMap.put("msg", "设计文件返回成功");
		}else{
			modelMap.put("state", false);
			modelMap.put("msg", "设计文件返回出错");
		}
		return modelMap;
	}
	
	//------------------- 设计师确认订单 -------------------------//
	@RequestMapping(value="/design/check/{orderid}",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> checkDOrder(@PathVariable("orderid") String orderid){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		Timestamp time = new Timestamp(System.currentTimeMillis());
		DOrder order=new DOrder();
		order.setDOrderID(orderid);
		order.setCheckDate(time);
		order.setState("待报价");
		int r=orderService.checkDOrder(order);
		if(r>0){
			modelMap.put("state", true);
			modelMap.put("msg", "订单确认成功");
		}else{
			modelMap.put("state", false);
			modelMap.put("msg", "订单确认失败");
		}
		return modelMap;
	}
	
	//------------------- 设计师修改需求 -------------------------//
	@RequestMapping(value="/design/update",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateDOrder1(@RequestBody DOrder order){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		int r=orderService.updateDOrder(order);
		if(r>0){
			modelMap.put("state", true);
			modelMap.put("msg", "订单需求修改成功");
		}else{
			modelMap.put("state", false);
			modelMap.put("msg", "订单需求修改失败");
		}
		return modelMap;
	}
	
	//------------------- 用户取消、付款订单 -------------------------//
	@RequestMapping(value="/design/userupdate",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateDOrder(@RequestBody DOrder order){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		//订单状态由前端返回
		Timestamp time = new Timestamp(System.currentTimeMillis());
		order.setCancelDate(time);
		order.setPayDate(time);
		int r=orderService.updateOrder(order);
		if(r>0){
			modelMap.put("state", true);
			modelMap.put("msg", "订单处理成功");
		}else{
			modelMap.put("state", false);
			modelMap.put("msg", "订单处理失败");
		}
		return modelMap;
	}
	
	//------------------- 代价工商、设计师、用户查看设计订单 -------------------------//
	@RequestMapping(value="/design/orders",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryDOrders(@RequestBody DOrder order){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		System.out.println(order.getDesigner());
		modelMap.put("state", true);
		modelMap.put("data",orderService.queryDOrders(order));
		modelMap.put("msg", "订单获取成功");
		return modelMap;
	}
	
	
	
	//------------------- 根据订单编号查看订单 -------------------------//
	@RequestMapping(value="/design/order/{orderid}",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> queryDOrder(@PathVariable("orderid") String orderid){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("data",orderService.queryDOrder(orderid));
		modelMap.put("state", true);
		modelMap.put("msg", "订单获取成功");
		return modelMap;
	}
	
	//------------------- 上传设计订单的需求文档 -------------------------//
	@RequestMapping(value="/design/demand",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> uploadDemand(@RequestParam("demandfile") CommonsMultipartFile file,
			HttpSession session){
		String folder="demandfile/";
		String path=session.getServletContext().getRealPath("/")+"\\files"+"\\demandfile\\";
		return fileAction.uploadZipFile(file, path,folder);
	}	
	
	//------------------- 上传设计订单的设计文档 -------------------------//
	@RequestMapping(value="/design/file",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> uploadDesign(@RequestParam("designfile") CommonsMultipartFile file,
			HttpSession session){
		String folder="designfile/";
		String path=session.getServletContext().getRealPath("/")+"\\files"+"\\designfile\\";
		return fileAction.uploadZipFile(file, path,folder);
	}
	
	//----------------------下载设计文件--------------------------------//
	@RequestMapping(value="/design/download",method=RequestMethod.POST)
	public ModelAndView  downloadModel(@RequestBody DOrder order,
			HttpServletRequest request, HttpServletResponse response) throws Exception{  
		String storeName = "";
        String realName = "";
        String ctxPath="";
        
        BufferedInputStream bis = null;  
        BufferedOutputStream bos = null;  
		
        if(order.getDemandFile()==null && order.getDesignFile()!=null){
        	storeName = order.getDesignFile();
            realName = order.getDesignFile(); 
            ctxPath = request.getSession().getServletContext()  
                    .getRealPath("/")  
                    + "\\files"+"\\designfile"+"\\";
        }else if(order.getDesignFile()==null && order.getDemandFile()!=null){
        	storeName = order.getDemandFile();
            realName = order.getDemandFile(); 
            ctxPath = request.getSession().getServletContext()  
                    .getRealPath("/")  
                    + "\\files"+"\\demandfile"+"\\";
        }
        String downLoadPath = ctxPath + storeName;
        
		request.setCharacterEncoding("UTF-8"); 

         File file= new File(downLoadPath);  
         long fileLength=file.length();

        response.setContentType("multipart/form-data"); 
        response.setHeader("Content-disposition", "attachment; filename="  
                + new String(realName.getBytes("utf-8"), "ISO8859-1"));  
        response.setHeader("Content-Length", String.valueOf(fileLength));  
  
        bis = new BufferedInputStream(new FileInputStream(downLoadPath));  
        bos = new BufferedOutputStream(response.getOutputStream());  
        byte[] buff = new byte[2048];  
        int bytesRead;  
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
            bos.write(buff, 0, bytesRead);  
        }  
        bis.close();
        bos.flush();
        bos.close(); 

        return null;
	}
	
	


}
