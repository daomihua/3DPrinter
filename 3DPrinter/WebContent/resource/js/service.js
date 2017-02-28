angular.module('PrinterApp')
.factory('AdminService', ['$resource', function($resource){

	var RESTurl='rest/admins';
	    return $resource('',{},{
	    	'loginAdmin':{ method:'POST', url:RESTurl+'/login' },
	    	'queryAdmin':{ method:'GET',params:{key:'@key'}, url:RESTurl+'/admin/:key' },
	    	'queryAdmins':{ method:'GET', url:RESTurl },
	    	'queryAthorities':{ method:'GET', params:{key:'@key'},url:RESTurl+'/authorities/:key' },
	    	'addAdmin':{ method:'POST', url:RESTurl+'/add'},
	    	'deleteAdmins':{ method:'DELETE', params:{key:'@key'}, url:RESTurl+'/delete/:key'},
	     	'updateAdmin':{ method:'POST', url:RESTurl+'/update'},
		   	'queryDesigners':{ method:'GET', url:RESTurl+'/designer'},
		   	'logout':{method:'GET', url:RESTurl+'/logout'},
		   	'getTongJi':{method:'GET', url:RESTurl+'/tongji'}
	    });

	//   TEST
//	return $resource('',{},{
//		'loginAdmin':{ method:'POST', url:RESTurl+'/login' },
//	 	'queryAdmin':{ method:'GET',params:{key:'@key'}, url:RESTurl+'/admin?adID=:key' },
//		'queryAdmins':{ method:'POST', url:'resource/js/admin.json' },
//		'queryAthorities':{ method:'GET', url:'resource/js/authority.json' },
//		'deleteAdmins':{ method:'DELETE', params:{key:'@key'}, url:RESTurl+'/delete?adName=:key'},
//		'updateAdmin':{ method:'POST', url:RESTurl+'/update'},
//		'logout':{ method:'GET',url:RESTurl+'/logout'}
//	});


}]);

angular.module('PrinterApp')
.factory('RoleService', ['$resource', function($resource){

	var RESTurl='rest/roles';
	    return $resource('',{},{
	     'queryRoles':{ method:'GET', url:RESTurl },
	     'queryRole':{ method:'GET', params: { key: '@key' }, url:RESTurl+'/role/:key' },
	     'addRole':{ method:'POST', url:RESTurl+'/add'},
	     'updateRole':{ method:'POST', url:RESTurl+'/update'},
	     'deleteRoles':{ method:'DELETE', params: { key: '@key' }, url:RESTurl+'/delete/:key' },
	     'deleteAuthority':{ method:'GET', url:RESTurl+'/delete/authority/:key'},
	     'distribute':{ method:'POST', url:RESTurl+'/distribute'}
	    });

	// TEST URL
	
//	return $resource('',{},{
//		'queryRoles':{ method:'GET', url:'resource/js/role.json' },
//		'queryRole':{ method:'GET', params: { key: '@key' }, url:'resource/js/role.json' },
//		'addRole':{ method:'POST', url:RESTurl+'/addrole'},
//		'updateRole':{ method:'POST', url:RESTurl+'/updaterole'},
//		'deleteRoles':{ method:'DELETE', params: { key: '@key' }, url:RESTurl+'/delete?roleid=:key' },
//		'distribute':{ method:'POST', url:RESTurl+'/distribute'}
//	});

}]);

angular.module('PrinterApp')
.factory('CaseService', ['$resource', function($resource){

	var RESTurl='rest/cases';
//	   return $resource('',{},{
//	   	'queryRoles':{ method:'GET', url:RESTurl },
//	    'queryRole':{ method:'GET', params: { key: '@key' }, url:RESTurl+'/role?roleID=:key' },
//	   	'addRole':{ method:'POST', url:RESTurl+'/addrole'},
//	    'updateRole':{ method:'POST', url:RESTurl+'/updaterole'},
//	    'deleteRoles':{ method:'DELETE', params: { key: '@key' }, url:RESTurl+'/delete?roleID=:key' },
//	    'distribute':{ method:'POST', url:RESTurl+'/distribute'}
//	   });

	// TEST URL
	
	return $resource('',{},{
		'queryAllCases':{ method:'GET', url:RESTurl },
		'queryCases':{ method:'GET',params:{key: '@key'}, url:RESTurl+'/category/:key' },
		'queryCase':{ method:'GET', params: { key: '@key' }, url:RESTurl+'/case/:key' },
		'queryACase':{ method:'GET', params: { key: '@key' }, url:RESTurl+'/admin/case/:key' },
		'queryCaseList':{ method:'GET', url:RESTurl+'/admin' },
		'auditCase':{ method:'GET', params: { key: '@key' }, url:RESTurl+'/audit/:key' },
		'queryCaseByCategory':{ method:'GET', url:RESTurl+'/category' },
		'addCase':{ method:'POST', url:RESTurl+'/add'},
		'deleteCase':{ method:'DELETE', params: { key: '@key' }, url:RESTurl+'/delete/:key' },
		'getCategory':{ method:'GET', url:'resource/js/category.json' }
	});

}]);

angular.module('PrinterApp')
.factory('UserService', ['$resource', function($resource){

	var RESTurl='rest/users';
	  return $resource('',{},{
	  	'loginUser':{ method:'POST',url:RESTurl+'/login'},
	  	'queryUsers':{ method:'GET', url:RESTurl },
	   	'queryUser':{ method:'GET', params: { key: '@key' }, url:RESTurl+'/user/:key' },
	  	'addUser':{ method:'POST', url:RESTurl+'/add'},
	   	'updateUser':{ method:'POST', url:RESTurl+'/update'},
	   	'deleteUser':{ method:'DELETE', params: { key: '@key' }, url:RESTurl+'/delete/:key' }
	  });

}]);

angular.module('PrinterApp')
.factory('ModelService',['$resource',function($resource){
	var RESTurl='rest/models';
	return $resource('',{},{
		'queryModels':{ method:'GET', url:RESTurl},
		'queryMaterial':{ method:'GET', url:RESTurl+'/material'},
		'deleteModel':{ method:'DELETE', url:RESTurl+'/delete/:key'},
		'updateModel':{ method:'POST', url:RESTurl+'/update'},
		'downloadModel':{ method:'GET', params: { key: '@key' },url:RESTurl+'/download/:key'},
		'queryModelsByCategory':{ method:'GET',params: { key: '@key' },url:RESTurl+'/category/:key'},
		'queryLibModels':{ method:'GET', url:RESTurl+'/library'},
		'shareModels':{ method:'POST', url:RESTurl+'/share'}
	});
}]);

angular.module('PrinterApp')
.factory('CartService',['$resource',function($resource){
	var RESTurl='rest/carts';
	return $resource('',{},{
		'queryCarts':{ method:'GET', url:RESTurl},
		'queryCartNum':{ method:'GET', url:RESTurl+'/count'},
		'deleteCart':{ method:'DELETE', url:RESTurl+'/delete/:key'},
		'updateCart':{ method:'POST', url:RESTurl+'/update'},
		'addCart':{ method:'POST', url:RESTurl+'/add'}
	});
}]);


angular.module('PrinterApp')
.factory('OrderService', ['$resource', function($resource){

	var RESTurl='rest/orders';
	  return $resource('',{},{
	  	'addOrder':{ method:'POST', url:RESTurl+'/add'},
	  	'queryOrdersByState':{ method:'GET', params: { key: '@key' }, url:RESTurl+'/:key'},
	  	'queryOrdersByStateAD':{ method:'GET', params: { key: '@key' }, url:RESTurl+'/ad/:key'},
	  	'queryOrder':{ method:'GET', params: { key: '@key' }, url:RESTurl+'/order/:key'},
	  	'updateOrder':{ method:'PATCH',params: { key1: '@key1',key2: '@key2' }, url:RESTurl+'/state/:key1/orderID/:key2'},
	  	'offerOrder':{ method:'PATCH',params: { key1: '@key1',key2: '@key2' },url:RESTurl+'/order/orderID/:key1/price/:key2'},
	  	'designerOrder':{ method:'GET',params:{key1:'@key1',key2:'@key2'},url:RESTurl+'/designer/:key1/state/:key2'},
	  	'changeOrder':{method:'POST',url:RESTurl+'/update'},
	  	/*设计订单*/
	  	'addDesignOrder':{ method:'POST', url:RESTurl+'/design/addorder'},
	  	'queryDesignOrders':{ method:'POST', url:RESTurl+'/design/orders'},
	  	'queryDOrder':{ method:'GET', params:{key:'@key'},url:RESTurl+'/design/order/:key'},
	  	'updateDOrder':{ method:'POST', url:RESTurl+'/design/update'},
	  	'checkDOrder':{ method:'GET', params:{key:'@key'}, url:RESTurl+'/design/check/:key'},
	  	'backDOrder':{ method:'POST',  url:RESTurl+'/design/back'},
	  	'checkDOderPrice':{ method:'POST',  url:RESTurl+'/design/quote'},
	  	'userUpdateOrder':{ method:'POST',  url:RESTurl+'/design/userupdate'}
	  });

}]);

angular.module('PrinterApp')
.factory('OrderItemService', ['$resource', function($resource){

	var RESTurl='rest/orderitem';
	  return $resource('',{},{
	  	'addItem':{ method:'POST', url:RESTurl+'/add'},
	  	'putPrice':{method:'POST', url:RESTurl+'/price'},
	  	'updateItem':{ method:'POST',url:RESTurl+'/update'},
	  	'uploadItem':{ method:'POST',url:RESTurl+'/uploaditem'},
	  	'getItems':{method:'GET',params:{key:'@key'},url:RESTurl+'/products/:key'}
	  });

}]);

angular.module('PrinterApp')
.factory('FeedBackService', ['$resource', function($resource){

	var RESTurl='rest/feedbacks';
	  return $resource('',{},{
	  	'queryFeedBack':{ method:'GET',params:{key:'@key'}, url:RESTurl+'/user/:key'},
	  	'addFeedBack':{method:'POST', url:RESTurl+'/user/add'},
	  	'updateFeedBack':{ method:'POST',url:RESTurl+'/admin/update'},
	  	'queryAllFeedBack':{ method:'GET',url:RESTurl+'/admin'},
	  	'queryOneFeedBack':{ method:'GET',params:{key:'@key'},url:RESTurl+'/:key'}
	  });

}]);
