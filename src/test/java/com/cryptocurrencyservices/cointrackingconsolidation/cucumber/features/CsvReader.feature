@ALL_TESTS @CSV_READER @WIP
Feature: CSV Reader


    Scenario: A poloniex csv file and an object type is given to the CsvReader and an object record is returned
        Given I have a "SOURCE_POLONIEX_CSV_FILE_NAME" csv file and an object type
        When I use the CSV Reader Service to read the csv into an object record
        Then I see the expected values exist in the object record


    Scenario: A bittrex csv file and an object type is given to the CsvReader and an object record is returned
        Given I have a "SOURCE_BITTREX_CSV_FILE_NAME" csv file and an object type
        When I use the CSV Reader Service to read the csv into an object record
        Then I see the expected values exist in the object record


