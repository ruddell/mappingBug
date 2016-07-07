(function() {
    'use strict';

    angular
        .module('mappingBugApp')
        .controller('BeaconDialogController', BeaconDialogController);

    BeaconDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Beacon', 'Company', 'Campaign'];

    function BeaconDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Beacon, Company, Campaign) {
        var vm = this;

        vm.beacon = entity;
        vm.clear = clear;
        vm.save = save;
        vm.companies = Company.query();
        vm.campaigns = Campaign.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.beacon.id !== null) {
                Beacon.update(vm.beacon, onSaveSuccess, onSaveError);
            } else {
                Beacon.save(vm.beacon, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mappingBugApp:beaconUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
