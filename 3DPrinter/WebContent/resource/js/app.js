angular.module('PrinterApp',['ngResource','ngCookies','ngAnimate','ui.router','textAngular','oc.lazyLoad'])
	.config(['$httpProvider', function ($httpProvider) {
        $httpProvider.defaults.headers.patch = {
            'Content-Type': 'application/json;charset=utf-8'
        }
    }]);

angular.module('PrinterApp')
.run(function($rootScope, $state, $stateParams){
    $rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams;
    $rootScope.isLogin = false;
    $rootScope.$on("$stateChangeSuccess",  function(event, toState, toParams, fromState, fromParams) {
                // to be used for back button //won't work when page is reloaded.
                $rootScope.previousState_name = fromState.name;
                $rootScope.previousState_params = fromParams;
            });
    $rootScope.back = function() {//实现返回的函数
        $state.go($rootScope.previousState_name,$rootScope.previousState_params);
    };
});

var app=angular.module('PrinterApp');
app.config(
    [        '$controllerProvider', '$compileProvider', '$filterProvider', '$provide',
    function ($controllerProvider,   $compileProvider,   $filterProvider,   $provide) {
        
        // lazy controller, directive and service
        app.controller = $controllerProvider.register;
        app.directive  = $compileProvider.directive;
        app.filter     = $filterProvider.register;
        app.factory    = $provide.factory;
        app.service    = $provide.service;
        app.constant   = $provide.constant;
        app.value      = $provide.value;
    }
  ]);

angular.module('PrinterApp')
.constant("Modules_Config",[
    
])
.config(["$ocLazyLoadProvider","Modules_Config",
function routeFn($ocLazyLoadProvider,Modules_Config){
    $ocLazyLoadProvider.config({
        debug:false,
        events:true,
        modules:[
	        {
		        name:"ngGrid",
		        module:true,
		        files:[
		            "resource/framework/ng-grid/ng-grid.min.css",
		            "resource/framework/ng-grid/ng-grid.min.js"
		        ]
		    },
            {
                name:"textAngular",
                module:true,
                files:[
                    "resource/framework/textAngular/textAngular-rangy.min.js",
                    "resource/framework/textAngular/textAngular-sanitize.min.js",
                    "resource/framework/textAngular/textAngular.min.js"
                ]
            }
        ]
    });
}]);


