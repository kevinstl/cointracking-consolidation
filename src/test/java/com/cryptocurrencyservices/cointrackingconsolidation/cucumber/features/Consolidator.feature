@ALL_TESTS @CONSOLIDATOR
Feature: Consolidator


    Scenario: Consolidate poloniex transactions into common contracking.info csv format
        Given I have access to the Poloniex Consolidator Service
        When I use the Poloniex Consolidator Service to consoliate
        Then I see the poloniex transactions consolidated into a csv file


