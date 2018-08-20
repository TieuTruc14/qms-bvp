
app.controller('qmsCtrl',['$scope','$http','$filter','$window','$timeout','$q'
    ,function ($scope,$http,$filter,$window,$timeout,$q) {
        $scope.name="";
        $scope.dataLoaded=false;
        $scope.page=page;
        $http.get(preUrl+"/admin/category/object-type/list",{params:{name:$scope.name}})
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.page=response.data;
                    $scope.page.pageCount=getPageCount($scope.page);
                    $scope.page.pageList=getPageList($scope.page);
                }
            });

        $scope.search=function () {
            $scope.page=page;
            $http.get(preUrl+"/admin/category/object-type/list",{params:{name:$scope.name}})
                .then(function (response) {
                    if(response!=null && response!='undefined' && response.status==200){
                        $scope.page=response.data;
                        $scope.page.pageCount=getPageCount($scope.page);
                        $scope.page.pageList=getPageList($scope.page);
                    }
                });
        };

        /*----------------------------------------------------------------------------------*/

        $scope.loadPage=function (pageNumber) {
            if(pageNumber>=1){
                $http.get(preUrl+"/admin/category/object-type/list", {params: {p:pageNumber,name:$scope.name}})
                    .then(function (response) {
                        $scope.page=response.data;
                        $scope.page.pageList=getPageList($scope.page);
                        $scope.page.pageCount=getPageCount($scope.page);
                    });
            }
        };

        $scope.itemDeleteId=0;
        $scope.deleteItem=function (id) {
            $scope.itemDeleteId=0;
            if(id>0){
                $scope.itemDeleteId=id;
            }
        }



    }]);