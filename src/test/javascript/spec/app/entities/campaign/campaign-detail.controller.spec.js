'use strict';

describe('Controller Tests', function() {

    describe('Campaign Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCampaign, MockCompany, MockBeacon, MockApplication;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCampaign = jasmine.createSpy('MockCampaign');
            MockCompany = jasmine.createSpy('MockCompany');
            MockBeacon = jasmine.createSpy('MockBeacon');
            MockApplication = jasmine.createSpy('MockApplication');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Campaign': MockCampaign,
                'Company': MockCompany,
                'Beacon': MockBeacon,
                'Application': MockApplication
            };
            createController = function() {
                $injector.get('$controller')("CampaignDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'mappingBugApp:campaignUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
