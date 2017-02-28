var ddd=[".jpg",".png"];
var testImg=angular.module("PrinterApp",["baneFileOper"])
	.controller('testCtr',function($scope,$http,baneFileFactory){
			$scope.aa=ddd;
			$scope.data={"a":"1","b":"2"}
			$scope.onSelect=function(files,error,dataForm){
				console.log(dataForm[0]);
				baneFileFactory.uploadDataForm("rest/files/upload",dataForm[0]).then(function(response){
					alert(response.data);
				});
			}
			
			$scope.submit=function(){

				}
			
		});
	
	angular.module("baneFileOper",[])
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
				var fd=new DataForm();
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
		}
	}])
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
										var expansion=fName.substring(fName.lastIndexOf('.'),fName.length).toLowerCase();;
										if(types.indexOf(expansion)>-1){
											if(scope.maxSize&&(scope.maxSize<fileList[i].size)){
												errorItems.push({"file":fName,"errorMsg":"文件不能大于"+scope.maxSize});
											}else {
												files.push(fileList[i]);
												}
										}else {
											errorItems.push({"file":fName,"errorMsg":"文件格式只能是"+types.join(',')});
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