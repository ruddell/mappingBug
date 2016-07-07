'use strict';

describe('Controller Tests', function() {

    describe('Beacon Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockBeacon, MockCompany, MockCampaign;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockBeacon = jasmine.createSpy('MockBeacon');
            MockCompany = jasmine.createSpy('MockCompany');
            MockCampaign = jasmine.createSpy('MockCampaign');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Beacon': MockBeacon,
                'Company': MockCompany,
                'Campaign': MockCampaign
            };
            createController = function() {
                $injector.get('$controller')("BeaconDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'mappingBugApp:beaconUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
