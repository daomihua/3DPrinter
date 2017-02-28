angular.module('PrinterApp')
.controller('AppCtrl',['$scope','$rootScope',
function($scope,$rootScope){
	var user =sessionStorage.getItem("User");
	var admin = sessionStorage.getItem("Admin");
	if(user!=null && user!=undefined){
		$rootScope.cartNum=sessionStorage.getItem("Carts");
		$rootScope.isLogin=true;
	}else{
		$rootScope.isLogin=false;
	}

	if(admin!=null && admin!=undefined){
		$rootScope.currentAdmin=JSON.parse(admin);
		$rootScope.isAdLogin=true;
	}else{
		$rootScope.isAdLogin=false;
	}
	console.log($rootScope.isLogin);
 
}]);

angular.module('PrinterApp')
.controller('HeadCtrl',['$scope','$state','$rootScope','AdminService',
function($scope,$state,$rootScope,AdminService){
	
	$scope.logout=function(){
		var check=confirm('确定要退出吗？');
		if(check){
			(new AdminService()).$logout().then(function(resp){
				if(resp.state){
					sessionStorage.removeItem('Admin');
					$rootScope.currentAdmin=null;
					$rootScope.isAdLogin=false;
					$state.go('access.signin');
				}
			});
		}
	};

}]);


angular.module('PrinterApp')
.controller('AuthorityController', ['$scope','$rootScope','AdminService', 
function($scope,$rootScope,AdminService){

	var role_id=$rootScope.currentAdmin.roleID;
	console.log(role_id);
	(new AdminService()).$queryAthorities({key:role_id})
		.then(function(resp){
			if(resp.state){
				$scope.items = resp.data;				
			}else{
				console.log('get data error');
			}	
		});
}]);

angular.module('PrinterApp')
.controller('LoginController', ['$scope','$state','$rootScope','AdminService', 
	function($scope,$state,$rootScope, AdminService){

		$scope.checkAdmin=function(){
			if(!$scope.check1 && !$scope.check2){
				return (new AdminService({
					'adName':$scope.name,
					'adPassword':$scope.password
				})).$loginAdmin().then(function(resp){
					console.log(resp.state);
					alert(resp.msg);
					if(resp.state){
						$rootScope.currentAdmin=resp.data;
						sessionStorage.setItem("Admin",JSON.stringify(resp.data));
						$state.go('admin.main');
					}
				});
			}
		};

		$scope.checkNameNull=function(){
			if($scope.name==null)
				$scope.check1=true;
			else
				$scope.check1=false;
		};

		$scope.checkPwdNull=function(){
			if($scope.password==null)
				$scope.check2=true;
			else
				$scope.check2=false;
		};

	}
]);

/*---------------------------------- 用户登录 -----------------------------*/
angular.module('PrinterApp')
.controller('SigninCtrl', ['$scope','$rootScope','$state', 'UserService','CartService',
function($scope,$rootScope,$state,UserService,CartService){
	var carts =sessionStorage.getItem("Carts");
	var user = sessionStorage.getItem("User");
	if(user!=null && user!=undefined){
		var u=JSON.parse(user);
		$rootScope.currentUser=u;
		console.log($scope.username);
		$rootScope.isLogin=true;
	}else{
		$rootScope.isLogin=false;
	}
	if(carts!=null && carts!=undefined){
		$rootScope.cartNum=carts;
	}
	//登录
	$scope.login=function(){
		if(!$scope.show1 && !$scope.show2){
			(new UserService({
				'userName':$scope.userName,
				'password':$scope.password
			})).$loginUser().then(function(resp){
				alert(resp.msg);
				if(resp.state){
					$rootScope.isLogin=resp.state; 
					$rootScope.currentUser=resp.data;
					sessionStorage.setItem("User",JSON.stringify(resp.data));
					(new CartService()).$queryCartNum().then(function(resp){
						sessionStorage.setItem('Carts',resp.data);
					});
					$rootScope.cartNum=sessionStorage.getItem("Carts");
					$rootScope.back();
				}
			});
		}
	};
	
	$scope.check1=false;
	$scope.check2=false;
	//验证输入
	$scope.checkNameNull=function(){
		if($scope.userName==null)
			$scope.check1=true;
		else
			$scope.check1=false;
	};

	$scope.checkPwdNull=function(){
		if($scope.password==null)
			$scope.check2=true;
		else
			$scope.check2=false;
	};
	
}]);

angular.module('PrinterApp')
.controller('UserLoginCtrl', ['$scope','$rootScope','$state', 'UserService','CartService',
function($scope,$rootScope,$state,UserService,CartService){

	$scope.login=function(){
		if(!$scope.show1 && !$scope.show2){
			(new UserService({
				'userName':$scope.userName,
				'password':$scope.password
			})).$loginUser().then(function(resp){
				alert(resp.msg);
				if(resp.state){
					$rootScope.isLogin=resp.state;
					$rootScope.currentUser=resp.data;
					sessionStorage.setItem("User",JSON.stringify(resp.data));
					(new CartService()).$queryCartNum().then(function(resp){
						sessionStorage.setItem('Carts',resp.data);
					});
					$rootScope.cartNum=sessionStorage.getItem("Carts");
					$state.go("index.home");
				}
			});
		}
	};
	
}]);


////上传文件的控制器

//angular.module("PrinterApp")
//.controller('addSImgCtrl',function($scope,$http,baneFileFactory){
//		$scope.imgType=type;
//		$scope.data={"a":"1","b":"2"}
//		$scope.onSelect=function(files,error,dataForm){
//			console.log(dataForm[0]);
//			baneFileFactory.uploadDataForm("rest/cases/upload",dataForm[0]).then(function(resp){
//				alert(resp.data.msg);
//				$scope.c.caseImg=resp.data.url;
//			});
//		}
//		
//});


	
angular.module("PrinterApp")
	.factory('baneFileFactory',['$http',function($http){
		return {
			uploadDataForm:function(uploadUrl,formData){
				return $http.post(uploadUrl,formData,{
							withCredentials: true,
							headers: {'Content-Type': undefined },
							transformRequest: angular.identity
						}).then(function(response){
							return response;
						});
			},
			uoloadFiles:function(uploadUrl,fileName,file,uploadData){
					var fd = new FormData();
					fd.append(fileName, file);
					if(uploadData){
						for(var dataKey in uploadData){
							fd.append(dataKey, uploadData[dataKey]);
						}
					}
				return $http.post(uploadUrl,dataForm,{
							withCredentials: true,
							headers: {'Content-Type': undefined },
							transformRequest: angular.identity
						}).then(function(response){
							return response;
						});
			}
		};
	}]);
angular.module("PrinterApp")
	.directive('baneFileSelect',['$parse','$timeout','$http',function($parse,$timeout,$http){
		return {
			restrict:'EA',
			scope:{
				fileTypes:'=?',
				maxSize:'=?',
				uploadData:'=?',
				createForm:'=?',
				baneFileSelect:'&'
				},
			link:function(scope,element,attrs,controller){
				var fn = $parse(attrs['baneFileSelect']);
				element.bind('change',function(evt){
					var errorItems=[];
					var dataForm=[];
					var types=scope.fileTypes;
					var files = [], fileList, i;
					fileList = evt.__files_ || evt.target.files;
					if(fileList!=null){
							for (i = 0; i < fileList.length; i++){
									if((types&&types.length>0)){
										var fName=fileList[i].name;
										var expansion=fName.substring(fName.lastIndexOf('.'),fName.length).toLowerCase();
										console.log(expansion);
										console.log(types);
										if(types.indexOf(expansion)>-1){
											if(scope.maxSize&&(scope.maxSize<fileList[i].size)){
												errorItems.push({"file":fName,"errorMsg":"文件不能大于"+scope.maxSize});
											}else {
												files.push(fileList[i]);
												}
										}else {
											errorItems.push({"file":fName,"errorMsg":"文件格式只能是"+types.join(',')});
											alert("文件格式只能是"+types.join(','));
										}
									}else {
											files.push(fileList[i]);											
										}
								}	
							if(scope.createForm){
								angular.forEach(files,function(fvalue,fkey){
									var fd = new FormData();
									fd.append(attrs.name, files[fkey]);
									if(scope.uploadData){
										for(var dataKey in scope.uploadData){
											fd.append(dataKey, scope.uploadData[dataKey]);
										}
									}

									dataForm.push(fd);
								});	
							}												
					}
					element.val("");
					if(scope.baneFileSelect){
						scope.baneFileSelect({$files : files,$error:errorItems,$dataForm:dataForm});
					}

				});
			}
		}
	}]);