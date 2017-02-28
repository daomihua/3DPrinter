angular.module('PrinterApp')
.directive("authority", function() {
    return {
        restrict: 'E',
        replace:true,
		    transclude:true,
        template:'<li ng-transclude></li>',
        link: function(scope, element, attr) {
		        element.click(function() {
		            if (!element.hasClass('active')) {
		            	element.siblings().removeClass('active')
		                element.addClass('active');
		            }
		        });
		    }
    }
});

angular.module('PrinterApp')
.directive("login", function() {
    return {
        restrict: 'A',
        replace:true,
		    transclude:true,
    		template:'<a href="" ng-transclude></a>',
            link: function(scope, element, attr) {
    		        element.click(function() {
    				console.log("aaa");
    				$("#login-modal").modal("toggle");
    		    	});
    			}
        }
});

angular.module('PrinterApp'). 
  directive('tabs', function() { 
    return { 
      restrict: 'E', 
      transclude: true, 
      scope: {}, 
      controller:function($scope,OrderService) { 
        var panes = $scope.panes = []; 
  
        $scope.select = function(pane) { 
          angular.forEach(panes, function(pane) { 
            pane.selected = false; 
          }); 
          pane.selected = true; 
        } 

        this.addPane = function(pane) { 
          if (panes.length == 0) $scope.select(pane); 
          panes.push(pane); 
        } 
      }, 
      template: 
        '<div class="tabbable">' + 
          '<ul class="nav nav-tabs">' + 
            '<li ng-repeat="pane in panes" ng-class="{active:pane.selected}">'+ 
              '<a href="" ng-click="select(pane)">{{pane.title}}</a>' + 
            '</li>' + 
          '</ul>' + 
          '<div class="tab-content" ng-transclude></div>' + 
        '</div>', 
      replace: true 
    }; 
  }). 
  directive('pane', function() { 
    return { 
      require: '^tabs', 
      restrict: 'E', 
      transclude: true, 
      scope: { title: '@' }, 
      link: function(scope, element, attrs, tabsCtrl) { 
        tabsCtrl.addPane(scope); 
      }, 
      template: 
        '<div class="tab-pane" ng-class="{active: selected}" ng-transclude> </div>', 
      replace: true 
    }; 
  });

angular.module('PrinterApp'). 
directive('showUploadModel', function() { 
  return {
      restrict:'A',
      transclude: true,
      scope:false,
      link: function(scope, element, attr) {
    	    scope.mtitle=null;   
            element.click(function() {
              $("#upload-modal").modal("toggle");
            });
      } 
  }
});

angular.module('PrinterApp'). 
directive('showImage', function() { 
  return {
      restrict:'A',
      transclude: true,
      scope:false,
      link: function(scope, element, attr) {
            element.click(function() {
              $("#img-modal").modal("toggle");
            });
      } 
  };
});

angular.module('PrinterApp'). 
directive('showLibImage', function() { 
  return {
      restrict:'A',
      transclude: true,
      scope:false,
      link: function(scope, element, attr) {
            element.click(function() {
              $("#img-modal").modal("toggle");
            });
      } 
  };
});

angular.module('PrinterApp'). 
directive('printWord', function() { 
  return {
      restrict:'A',
      transclude: true,
      scope:false,
      link: function(scope, element, attr) {
            
            element.click(function(){
            	scope.html=$("#tongjitable").html();
            	console.log(scope.html);
            	});
            
      } 
  };
});
