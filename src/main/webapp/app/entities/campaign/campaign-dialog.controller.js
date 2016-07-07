(function() {
    'use strict';

    angular
        .module('mappingBugApp')
        .controller('CampaignDialogController', CampaignDialogController);

    CampaignDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Campaign', 'Company', 'Beacon', 'Application'];

    function CampaignDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Campaign, Company, Beacon, Application) {
        var vm = this;

        vm.campaign = entity;
        vm.clear = clear;
        vm.save = save;
        vm.companies = Company.query();
        vm.beacons = Beacon.query();
        vm.applications = Application.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.campaign.id !== null) {
                Campaign.update(vm.campaign, onSaveSuccess, onSaveError);
            } else {
                Campaign.save(vm.campaign, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mappingBugApp:campaignUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
