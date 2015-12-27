"use strict";

/******************************************************************************************

Expenses controller

******************************************************************************************/

var app = angular.module("expenses.controller", []);

app.config(function(RestangularProvider) {
	var deployedAt = window.location.origin;
 	var newBaseUrl = deployedAt + "/api";
 	RestangularProvider.setBaseUrl(newBaseUrl);	
});

app.controller("ctrlExpenses", ["$rootScope", "$scope", "Restangular", function ExpensesCtrl($rootScope, $scope, $Restangular) {
	// Update the headings
	$rootScope.mainTitle = "Expenses";
	$rootScope.mainHeading = "Expenses";
	
	// Update the tab sections
	$rootScope.selectTabSection("expenses", 0);
	
	$scope.dateOptions = {
		changeMonth: true,
		changeYear: true,
		dateFormat: "dd/mm/yy"
	};

	var loadExpenses = function() {
		// Retrieve a list of expenses via REST
		$Restangular.one("expenses").getList().then(function(expenses) {
			$scope.expenses = expenses;
		});
	}

	$scope.saveExpense = function() {
		if ($scope.expensesform.$valid) {
			// Post the expense via REST
			$Restangular.one("expenses").post(null, $scope.newExpense).then(function() {
				// Reload new expenses list
				loadExpenses();
			});
		}
	};

	$scope.clearExpense = function() {
		$scope.newExpense = {};
	};

	$scope.calculateVAT = function() {
		if($scope.newExpense.amount) {
			$scope.newExpense.vat = $scope.newExpense.amount * 0.20;
		} else {
			$scope.newExpense.vat = 0.00;
		}
		
		$scope.newExpense.vat = $scope.newExpense.vat.toFixed(2);
	};
	
	// Initialise scope variables
	loadExpenses();
	$scope.clearExpense();
}]);
