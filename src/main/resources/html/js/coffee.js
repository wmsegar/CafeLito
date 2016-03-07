
var coffeeApp = angular.module('coffeeApp', ['ngResource', 'ui.bootstrap']);

coffeeApp.service('LocalCoffeeShop', function () {
    var localCoffeeShop;

    this.setShop = function (shop) {
        localCoffeeShop = shop;
    };

    this.getShop = function () {
        return localCoffeeShop;
    }
});

coffeeApp.controller('CoffeeShopController', function ($scope, $window, $resource, LocalCoffeeShop) {
    var CoffeeShopLocator = $resource('/service/coffeeshop/nearest/:latitude/:longitude',
        {latitude: '@latitude', longitude: '@longitude'}, {});

    function success(position){
        CoffeeShopLocator.get({latitude: position.coords.latitude, longitude: position.coords.longitude},
            function (foundCoffeeShop) {
                $scope.nearestCoffeeShop = foundCoffeeShop;
                LocalCoffeeShop.setShop(foundCoffeeShop);
            });
    }

    function error(err){
        alert('Error: ' + err.message + ' Please enable location services for this site to Order Coffee');
        //LocalCoffeeShop.getShop().openStreetMapId = 1677786338;
    }

    $scope.findCoffeeShopNearestToMe = function () {
        window.navigator.geolocation.getCurrentPosition(success, error);
    };
    $scope.findCoffeeShopNearestToMe();
});

coffeeApp.controller('OrderController', function ($scope, $resource, LocalCoffeeShop) {
    $scope.types = [
        {name: 'Americano', family: 'Coffee'},
        {name: 'Latte', family: 'Coffee'},
        {name: 'Tea', family: 'that other drink'},
        {name: 'Cappuccino', family: 'Coffee'}
    ];

    $scope.sizes = ['Small', 'Medium', 'Large'];

    $scope.messages = [];

    $scope.giveMeCoffee = function () {
        $scope.drink.coffeeShopId = LocalCoffeeShop.getShop().openStreetMapId;
        var CoffeeOrder = $resource('/service/coffeeshop/order/');
        CoffeeOrder.save($scope.drink, function (order) {
            $scope.messages.push({type: 'success', msg: 'Order Sent!'})
        });
    };
});