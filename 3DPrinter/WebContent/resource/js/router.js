angular.module('PrinterApp')
.config(function($stateProvider,$urlRouterProvider,$locationProvider){
	$urlRouterProvider.otherwise('/index/home');//  /admin/main  /index/home  /access/signin
	$stateProvider
		.state('admin',{
			abstract: true,
            url: '/admin',
            templateUrl: 'admin/admin.html'
		})
		.state('admin.main',{
			url:'/main',
			templateUrl:'admin/main.html',
			resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("resource/js/controllers/role.js");
	            }]
	        } 
		})
		/*添加角色*/
		.state('admin.addrole',{
			url:'/addrole',
			templateUrl:'admin/addRole.html',
			controller:'addRoleCtrl',
			resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("resource/js/controllers/role.js");
	            }]
	        } 
		})
		/*管理角色（列表）*/
		.state('admin.deleterole',{
			url:'/deleterole',
			templateUrl:'admin/deleteRole.html',
			controller:'deleteRoleCtrl',
	        resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("ngGrid").then(
	                    function(){
	                        return $ocLazyLoad.load("resource/js/controllers/role.js");
	                    }
	                );
	            }]
	        }
		})
		/*修改角色*/
		.state('admin.editrole',{
			url:'/editrole/:roleid',
			templateUrl:'admin/editRole.html',
			controller:'editRoleCtrl',
			resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("resource/js/controllers/role.js");
	            }]
	        } 
		})
		/*添加管理员*/
		.state('admin.addadmin',{
			url:'/addadmin',
			templateUrl:'admin/addAdmin.html',
			controller:'addAdminCtrl',
			resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("resource/js/controllers/admin.js");
	            }]
	        } 
		})
		/*删除管理员*/
		.state('admin.deleteadmin',{
			url:'/deleteadmin',
			templateUrl:'admin/deleteAdmin.html',
			controller:'deleteAdminCtrl',
	        resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("ngGrid").then(
	                    function(){
	                        return $ocLazyLoad.load("resource/js/controllers/admin.js");
	                    }
	                );
	            }]
	        }
		})
		/*修改管理员信息*/
		.state('admin.editeadmin',{
			url:'/editeadmin/:adID',
			templateUrl:'admin/editeAdmin.html',
			controller:'editeAdminCtrl',
			resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("resource/js/controllers/admin.js");
	            }]
	        } 
		})
		/*修改个人信息*/
		.state('admin.editself',{
			url:'/editeself',
			templateUrl:'admin/self.html',
			controller:'SelfCtrl',
			resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("resource/js/controllers/editeSelf.js");
	            }]
	        } 
		})
		/*编辑案例*/
		.state('admin.caseeditor',{
			url:'/editor',
			templateUrl:'admin/caseEditor.html',
			controller:'EditorCtrl',
	        resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("resource/js/controllers/editor.js");
	            }]
	        } 
		})
		/*管理、审核案例*/
		.state('admin.deletecase',{
			url:'/case',
			templateUrl:'admin/deleteCase.html',
			controller:'CaseCtrl',
	        resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("ngGrid").then(
	                    function(){
	                        return $ocLazyLoad.load("resource/js/controllers/case.js");
	                    }
	                );
	            }]
	        }
		})
		/*查看审核案例内容*/
		.state('admin.c-content',{
			url:'/case/:caseID',
			templateUrl:'admin/case-content.html',
			controller:'ContentCtrl',
	        resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	               return $ocLazyLoad.load("resource/js/controllers/case.js");
	            }]
	        }
		})
		/*代价工商查看生产订单列表*/
		.state('admin.order',{
			url:'/order',
			templateUrl:'admin/order.html',
			controller:'OrderCtrl',
	        resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("ngGrid").then(
	                    function(){
	                        return $ocLazyLoad.load("resource/js/controllers/ad-order.js");
	                    }
	                );
	            }]
	        }
		})
		/*设计师查看设计订单列表*/
		.state('admin.designorder',{
			url:'/design/order1',
			templateUrl:'admin/u-order1.html',
			controller:'DesignOrderCtrl',
	        resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("ngGrid").then(
	                    function(){
	                        return $ocLazyLoad.load("resource/js/controllers/ad-order.js");
	                    }
	                );
	            }]
	        }
		})
		/*设计师查看设计订单详情*/
		.state('admin.dorderdetail',{
			url:'/design/orderdetail/:orderid',
			templateUrl:'admin/u-order2.html',
			controller:'DOrderDetailCtrl',
	        resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("ngGrid").then(
	                    function(){
	                        return $ocLazyLoad.load("resource/js/controllers/ad-order.js");
	                    }
	                );
	            }]
	        }
		})
		/*代价工商、设计师查看生产订单详情*/
		.state('admin.orderdetail',{
			url:'/order/detail/:orderID',
			templateUrl:'admin/detail.html',
			controller:'DetailCtrl',
	        resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                 return $ocLazyLoad.load("resource/js/controllers/ad-order.js");
	            }]
	        }
		})
		/*设计师查看生产订单列表（查看自己的）*/
		.state('admin.designer',{
			url:'/designer',
			templateUrl:'admin/designer.html',
			controller:'DesignCtrl',
	        resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("ngGrid").then(
	                    function(){
	                        return $ocLazyLoad.load("resource/js/controllers/designer.js");
	                    }
	                );
	            }]
	        }
		})
		/*设计师操作生产订单（确认、修改、生产）*/
		.state('admin.d-detail',{
			url:'/designer/detail/:orderID',
			templateUrl:'admin/c-detail.html',
			controller:'DdetailCtrl',
	        resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                 return $ocLazyLoad.load("resource/js/controllers/designer.js");
	            }]
	        }
		})
		/*代价工商查看统计*/
		.state('admin.tongji',{
			url:'/tongji',
			templateUrl:'admin/tongji.html',
			controller:'TongjiCtrl',
	        resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                 return $ocLazyLoad.load("resource/js/controllers/admin.js");
	            }]
	        }
		})
		/*管理员查看反馈列表*/
		.state('admin.feedback1',{
			url:'/feedback',
			templateUrl:'admin/feedback1.html',
			controller:'FBListCtrl',
	        resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("ngGrid").then(
	                    function(){
	                        return $ocLazyLoad.load("resource/js/controllers/feedback.js");
	                    }
	                );
	            }]
	        }
		})
		/*管理员处理反馈*/
		.state('admin.feedback2',{
			url:'/feedback/:id',
			templateUrl:'admin/feedback2.html',
			controller:'FBDetailCtrl',
	        resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                   return $ocLazyLoad.load("resource/js/controllers/feedback.js");
	            }]
	        }
		})
		//------------登录、注册相关-----------//
		.state('access',{
			url: '/access',
            template: '<div ui-view></div>'
            // templateUrl: 'admin/login.html'
		})
		.state('access.signin',{
			url:'/signin',
            templateUrl: 'admin/login.html',
            controller:'LoginController'
		})
		.state('access.usersignin',{
			url:'/user/signin',
            templateUrl: 'user/user-signin.html',
            controller:'SigninCtrl'
		})
		.state('access.register',{
			url:'/user/register',
			templateUrl:'user/register.html',
			controller:'RegisterCtrl',
			resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("resource/js/controllers/user.js");
	            }]
	        } 
		})
		/*-----------------  user  ----------------*/
		.state('index',{
			abstract: true,
            url: '/index',
            templateUrl: 'user/user.html',
            resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("resource/css/style.css");
	            }]
	        }
		})
		.state('index.home',{
			url:'/home',
			templateUrl:'user/home.html'
		})
		.state('index.intro',{
			url:'/intro',
			templateUrl:'user/intro.html'
		})
		.state('index.case',{
			url:'/case?id&name',
			templateUrl:'user/case.html',
			controller:'UserCaseCtrl',
			resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("resource/js/controllers/userctrl/case.js");
	            }]
	        } 
		})
		.state('index.content',{
			url:'/content/:caseID',
			templateUrl:'user/case-content.html',
			controller:'CaseContentCtrl',
			resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("resource/js/controllers/userctrl/case.js");
	            }]
	        } 
		})
		.state('index.library',{
			url:'/library',
			templateUrl:'user/library.html',
			controller:'LibraryCtrl',
			resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("resource/js/controllers/user.js");
	            }]
	        } 
		})
		.state('index.designer',{
			url:'/designer',
			templateUrl:'user/designer.html',
			controller:'DeginerCtrl',
			resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("resource/js/controllers/userctrl/designer.js");
	            }]
	        } 
		})
		.state('index.userdesign',{
			url:'/user/design/:designer',
			templateUrl:'user/u-design.html',
			controller:'UserDesignCtrl',
			resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("resource/js/controllers/userctrl/designer.js");
	            }]
	        } 
		})
		.state('index.onedesigner',{
			url:'/designer/:adID',
			templateUrl:'user/onedesigner.html',
			controller:'OneDeginerCtrl',
			resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("resource/js/controllers/userctrl/designer.js");
	            }]
	        } 
		})
		.state('index.register',{
			url:'/register',
			templateUrl:'user/register.html',
			controller:'RegisterCtrl',
			resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("resource/js/controllers/user.js");
	            }]
	        } 
		})
		.state('index.order',{
			url:'/order',
			controller:'OrderCtrl',
			templateUrl:'user/order1.html',
			resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("resource/js/controllers/order.js");
	            }]
	        } 
		})
		.state('index.designorder',{
			url:'/design/order',
			controller:'UserDOrderCtrl',
			templateUrl:'user/order2.html',
			resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("resource/js/controllers/order.js");
	            }]
	        } 
		})
		.state('index.detail',{
			url:'/detail/:orderID',
			templateUrl:'user/detail.html',
			controller:'OrderDetailCtrl',
			resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("resource/js/controllers/detail.js");
	            }]
	        } 
		})
		.state('index.dodetail',{
			url:'/design/detail/:orderid',
			templateUrl:'user/detail2.html',
			controller:'DOrderDetailCtrl',
			resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("resource/js/controllers/detail.js");
	            }]
	        } 
		})
		.state('index.print',{
			url:'/print',
			controller:'PrintCtrl',
			templateUrl:'user/print.html',
			resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("resource/js/controllers/cart.js");
	            }]
	        } 
		})
		.state('index.personal',{
			url:'/personal',
			controller:'PersonCtrl',
			templateUrl:'user/personal.html',
			resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("resource/js/controllers/user.js");
	            }]
	        } 
		})
		.state('index.cart',{
			url:'/cart',
			controller:'CartCtrl',
			templateUrl:'user/cart.html',
			resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("resource/js/controllers/cart.js");
	            }]
	        } 
		})
		.state('index.deliver',{
			url:'/deliver',
			controller:'OrderCtrl1',
			templateUrl:'user/deliver.html',
			resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("resource/js/controllers/order.js");
	            }]
	        } 
		})
		.state('index.feedback',{
			url:'/feedback',
			controller:'FeedBackCtrl',
			templateUrl:'user/feedback.html',
			resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("resource/js/controllers/feedback.js");
	            }]
	        } 
		})
		.state('index.file',{
			url:'/process-file',
			controller:'ProcessFileCtrl',
			templateUrl:'user/process-file.html',
			resolve:{
	            deps:["$ocLazyLoad",function($ocLazyLoad){
	                return $ocLazyLoad.load("ngGrid").then(
	                    function(){
	                        return $ocLazyLoad.load("resource/js/controllers/user.js");
	                    }
	                );
	            }]
	        }
		});
		
});