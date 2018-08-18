/**
 * Created by Admin on 8/13/2018.
 */
app.controller('qmsCtrl',['$scope','$http','$filter','$window','$timeout','$q'
    ,function ($scope,$http,$filter,$window,$timeout,$q) {
        $scope.name="";
        $scope.dataLoaded=false;
        $scope.items="";
        $http.get(preUrl+"/admin/management/generality/list-door")
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.items=response.data;
                }
            });

        $scope.reload=function () {
            $http.get(preUrl+"/admin/management/generality/list-door")
                .then(function (response) {
                    if(response!=null && response!='undefined' && response.status==200){
                        $scope.items=response.data;
                    }
                });
        }
    }]);