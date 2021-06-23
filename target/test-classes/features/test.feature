Feature: Test google map

  Background: User is on landing Page
    Given I navigate to the google maps page
    And The map has been loaded completely
    And I change the language to English


  Scenario Outline: Search a place with name
    When I search "<place>" on map
    Then I validate the "<place>" exists on map
    And I verify information buttons

    Examples:
      | place     |
      | Frankfurt |


  Scenario Outline: Search a place with coordinates
    When I search "<coordinates>" on map
    And I verify information buttons

    Examples:
      | coordinates                         |
      | 52.5162841525877, 13.37770064613413 |


  Scenario Outline: Search route for a destination
    When I search "<place>" on map
    And I click on directions
    When I choose the journey mode as "<mode>"
    And I set starting point as "<startingPoint>"
    Then I verify the journey details exist
    And I verify the journey mode - "<mode>"
    And I capture the journey time

    Examples:
      | startingPoint   | place  | mode    |
      | Airport Hamburg | Berlin | Driving |
      | Noida           | Delhi  | Walking |


  Scenario Outline: Search route and reverse places
    When I search "<place>" on map
    And I click on directions
    When I choose the journey mode as "<mode>"
    And I set starting point as "<startingPoint>"
    And I press the reverse button
    Then I verify places "<place>" and "<startingPoint>" are reversed

    Examples:
      | startingPoint | place         | mode    |
      | Moscow        | St Petersburg | Driving |


  Scenario Outline: Add destinations in route
    When I search "<place>" on map
    And I click on directions
    When I choose the journey mode as "<mode>"
    And I set starting point as "<startingPoint>"
    And I add below destinations
      | Key          | Value                   |
      | Destination2 | Brandenburg Gate Berlin |
      | Destination3 | Wooga Berlin            |
    Then I verify the journey details exist
    And I verify the journey mode - "<mode>"
    And I capture the journey time

    Examples:
      | startingPoint | place   | mode    |
      | Leipzig       | Hamburg | Driving |


  Scenario Outline: Perform actions on map canvas
    When I search "<place>" on map
    And I click on Directions from here
    Then I verify that "<place>" is in destination field
    When I navigate back
    And I click on Directions to here
    Then I verify that "<place>" is in origin field
    When I click on zoom out
    And I click on zoom in

    Examples:
      | place   |
      | Chicago |


  Scenario Outline: Search for an invalid place
    When I search "<place>" on map
    And I verify the "<place>" is invalid

    Examples:
      | place      |
      | NotValid99 |


  Scenario Outline: Search for an invalid route
    When I search "<place>" on map
    And I click on directions
    And I set starting point as "<startingPoint>"
    And I verify the route is invalid
    And I verify that all modes are disabled

    Examples:
      | startingPoint | place    |
      | Berlin        | Maldives |


  Scenario Outline: Search invalid place and verify suggestion
    When I search "<invalidPlace>" on map
    Then I validate "<suggestedPlace>" is suggested instead of "<invalidPlace>"
    And I validate the "<suggestedPlace>" exists on map

    Examples:
      | invalidPlace | suggestedPlace |
      | Frankggrt    | Frankfurt      |
