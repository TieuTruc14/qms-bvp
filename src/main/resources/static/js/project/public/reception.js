
app.controller('qmsCtrl',['$scope','$http','$filter','$window','$timeout','$q'
    ,function ($scope,$http,$filter,$window,$timeout,$q) {
        $(".hidden1").css({"display": "block"});
        $scope.dataLoaded=false;
        $scope.item="";
        $scope.areas="";
        $scope.priority=false;
        $scope.reception_type_value=0;
        // $scope.area={areaId:0};
        $scope.areaId=0;
        $scope.assurance=true;
        $scope.areaError="";
        $scope.messageStatus="";

        $http.get(preUrl+"/area/list")
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.areas=response.data;
                }
            });

        $scope.genNumber=function () {
            $scope.item="";
            $scope.preGetData();
            if(!$scope.genObject()){
                $scope.endGetData();
                return ;
            }
            $http.get(preUrl+"/reception/born-number",{params: {reception_type_value:$scope.reception_type_value,areaId:$scope.areaId}})
                .then(function (response) {
                    if(response!=null && response!='undefined' ){
                        if(response.status==200){
                            $scope.item=response.data;
                        }else if(response.status==204){
                            $scope.messageStatus="Không lấy được số tiếp đón!";
                        }
                    }
                    $scope.endGetData();
                },
                function(response){
                    $scope.messageStatus="Có lỗi xảy ra, hãy thử lại sau!";
                    $scope.endGetData();
                });
        };

        $scope.genObject=function () {
            if($scope.priority){
                if($scope.assurance){
                    $scope.reception_type_value=5;
                }else{
                    $scope.reception_type_value=6;
                }
            }else{
                if($scope.assurance){
                    $scope.reception_type_value=1;
                }else{
                    $scope.reception_type_value=2;
                }
            }
            if($scope.areaId==0){
                $scope.areaError="Cần chọn khu vực khám";
                return false;
            }
            return true;

        };
        
        $scope.selectArea=function (areaId) {
          $scope.areaId=areaId;
        };
        
        $scope.preGetData=function () {
            $scope.areaError="";
            $scope.messageStatus="";
            var myEl = angular.element( document.querySelector( '#layso' ) );
            myEl.addClass('disabled');
            // var el=angular.element("#layso")[0];
            // el.addClass("disabled");
        };
        $scope.endGetData=function () {
            var myEl = angular.element( document.querySelector( '#layso' ) );
            myEl.removeClass('disabled');
            // var el=angular.element("#layso")[0];
            // el.removeClass("disabled");
        }
    }]);