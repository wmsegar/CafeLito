
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

    $scope.findCoffeeShopNearestToMe = function () {
        window.navigator.geolocation.getCurrentPosition(function (position) {
            CoffeeShopLocator.get({latitude: position.coords.latitude, longitude: position.coords.longitude},
                function (foundCoffeeShop) {
                    $scope.nearestCoffeeShop = foundCoffeeShop;
                    LocalCoffeeShop.setShop(foundCoffeeShop);
                });
        });
    };
    $scope.findCoffeeShopNearestToMe();
});

coffeeApp.controller('OrderController', function ($scope, $resource) {
    $scope.types = [
        {name: 'Americano', family: 'Coffee'},
        {name: 'Latte', family: 'Coffee'},
        {name: 'Tea', family: 'that other drink'},
        {name: 'Cappuccino', family: 'Coffee'}
    ];

    $scope.sizes = ['Small', 'Medium', 'Large'];

    $scope.messages = [];

    $scope.giveMeCoffee = function () {
        $scope.drink.coffeeShopId = 1;
        var CoffeeOrder = $resource('/service/coffeeshop/order/');
        CoffeeOrder.save($scope.drink, function (order) {
            $scope.messages.push({type: 'success', msg: 'Order Sent!'})
        });
    };
});