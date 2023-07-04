package gsalaun.diverse.address.geography.countries

import gsalaun.diverse.address.geography.CityWithRelatedInformation
import gsalaun.diverse.address.geography.Country
import gsalaun.diverse.address.geography.StateProvinceArea
import gsalaun.diverse.persons.Continent

// TODO Maybe use Json instead of internal values
object UnitedStatesOfAmerica {

    val cities: List<CityWithRelatedInformation> =
        listOf(
            CityWithRelatedInformation(
                "New York",
                StateProvinceArea.NEW_YORK,
                Country.USA,
                Continent.NORTH_AMERICA,
                "1001#",
            ),
            CityWithRelatedInformation(
                "Los Angeles",
                StateProvinceArea.CALIFORNIA,
                Country.USA,
                Continent.NORTH_AMERICA,
                "900##",
            ),
            CityWithRelatedInformation(
                "Denver",
                StateProvinceArea.COLORADO,
                Country.USA,
                Continent.NORTH_AMERICA,
                "8020#",
            ),
            CityWithRelatedInformation(
                "Chicago",
                StateProvinceArea.ILLINOIS,
                Country.USA,
                Continent.NORTH_AMERICA,
                "606##",
            ),
            CityWithRelatedInformation(
                "Seattle",
                StateProvinceArea.WASHINGTON,
                Country.USA,
                Continent.NORTH_AMERICA,
                "981##",
            ),
            CityWithRelatedInformation(
                "Washington",
                StateProvinceArea.DC,
                Country.USA,
                Continent.NORTH_AMERICA,
                "2000#",
            ),
            CityWithRelatedInformation(
                "Miami",
                StateProvinceArea.FLORIDA,
                Country.USA,
                Continent.NORTH_AMERICA,
                "3314#",
            ),
            CityWithRelatedInformation(
                "Las Vegas",
                StateProvinceArea.NEVADA,
                Country.USA,
                Continent.NORTH_AMERICA,
                "891##",
            ),
            CityWithRelatedInformation(
                "New Orleans",
                StateProvinceArea.LOUISIANA,
                Country.USA,
                Continent.NORTH_AMERICA,
                "701##",
            ),
            CityWithRelatedInformation(
                "Houston",
                StateProvinceArea.TEXAS,
                Country.USA,
                Continent.NORTH_AMERICA,
                "770##",
            ),
            CityWithRelatedInformation(
                "Milwaukee",
                StateProvinceArea.WISCONSIN,
                Country.USA,
                Continent.NORTH_AMERICA,
                "5320#",
            ),
            CityWithRelatedInformation(
                "Philadelphia",
                StateProvinceArea.PENNSYLVANIA,
                Country.USA,
                Continent.NORTH_AMERICA,
                "191##",
            ),
            CityWithRelatedInformation(
                "Phoenix",
                StateProvinceArea.ARIZONA,
                Country.USA,
                Continent.NORTH_AMERICA,
                "850##",
            ),
        )

    val streetNames: List<String> =
        listOf(
            "1st Avenue",
            "2nd Avenue",
            "3th Avenue",
            "4th Avenue",
            "5th Avenue",
            "6th Avenue",
            "7th Avenue",
            "8th Avenue",
            "14th Street",
            "West 28th Street",
            "West 36th Street",
            "Canal Street",
            "Lafayette Avenue",
            "Nevins Street",
            "Francisco Street",
            "Wilshire Boulevard",
            "West Century Boulevard",
            "Ocean Avenue",
            "Stout Street",
            "Grant Street",
            "Amphitheatre Parkway Mountain View",
            "North State Street",
            "North Wabash Avenue",
            "Boren Avenue",
            "South King Street",
            "K Street NW",
            "15th Street North West",
            "17th Street North West",
            "Collins Avenue",
            "Harding Avenue",
            "Giles Street",
            "Saint Rose Parkway",
            "South Boulevard",
            "Poydras St",
            "Magnolia St",
            "Philip St",
            "Martin Luther King Jr Blvd",
            "Loyola Avenue",
            "Bourbon Street",
            "West Loop South",
            "Fannin Street",
            "Main Street",
            "West Kilbourn Avenue",
            "East Wisconsin Avenue",
            "South Howell Avenue",
            "Market Street",
            "Tinicum Boulevard",
            "E Van Buren St",
            "North 44th Street",
            "West Willetta Street",
        )
}
