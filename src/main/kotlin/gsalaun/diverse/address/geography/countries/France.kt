package gsalaun.diverse.address.geography.countries

import gsalaun.diverse.address.geography.CityWithRelatedInformation
import gsalaun.diverse.address.geography.Country
import gsalaun.diverse.address.geography.StateProvinceArea
import gsalaun.diverse.persons.Continent

// TODO Maybe use Json instead of internal values
object France {

    val cities: List<CityWithRelatedInformation> =
        listOf(
            CityWithRelatedInformation(
                "Paris",
                StateProvinceArea.ILE_DE_FRANCE,
                Country.FRANCE,
                Continent.EUROPE,
                "7501#",
            ),
            CityWithRelatedInformation(
                "Saint-Ouen",
                StateProvinceArea.ILE_DE_FRANCE,
                Country.FRANCE,
                Continent.EUROPE,
                "93400",
            ),
            CityWithRelatedInformation(
                "Saint-Denis",
                StateProvinceArea.ILE_DE_FRANCE,
                Country.FRANCE,
                Continent.EUROPE,
                "93200",
            ),
            CityWithRelatedInformation(
                "Versailles",
                StateProvinceArea.ILE_DE_FRANCE,
                Country.FRANCE,
                Continent.EUROPE,
                "78000",
            ),
            CityWithRelatedInformation(
                "La Courneuve",
                StateProvinceArea.ILE_DE_FRANCE,
                Country.FRANCE,
                Continent.EUROPE,
                "93120",
            ),
            CityWithRelatedInformation(
                "Quiberon",
                StateProvinceArea.BRETAGNE,
                Country.FRANCE,
                Continent.EUROPE,
                "56170",
            ),
            CityWithRelatedInformation(
                "Rennes",
                StateProvinceArea.BRETAGNE,
                Country.FRANCE,
                Continent.EUROPE,
                "35#00",
            ),
            CityWithRelatedInformation(
                "Brest",
                StateProvinceArea.BRETAGNE,
                Country.FRANCE,
                Continent.EUROPE,
                "29200",
            ),
            CityWithRelatedInformation(
                "Nantes",
                StateProvinceArea.PAYS_DE_LA_LOIRE,
                Country.FRANCE,
                Continent.EUROPE,
                "44###",
            ),
            CityWithRelatedInformation(
                "Bordeaux",
                StateProvinceArea.NOUVELLE_AQUITAINE,
                Country.FRANCE,
                Continent.EUROPE,
                "33#00",
            ),
            CityWithRelatedInformation(
                "Marseille",
                StateProvinceArea.PROVENCE_ALPES_COTE_D_AZUR,
                Country.FRANCE,
                Continent.EUROPE,
                "1301#",
            ),
            CityWithRelatedInformation(
                "Nice",
                StateProvinceArea.PROVENCE_ALPES_COTE_D_AZUR,
                Country.FRANCE,
                Continent.EUROPE,
                "06#00",
            ),
            CityWithRelatedInformation(
                "Lyon",
                StateProvinceArea.AUVERGNE_RHONE_ALPES,
                Country.FRANCE,
                Continent.EUROPE,
                "6900#",
            ),
            CityWithRelatedInformation(
                "Grenoble",
                StateProvinceArea.AUVERGNE_RHONE_ALPES,
                Country.FRANCE,
                Continent.EUROPE,
                "38###",
            ),
            CityWithRelatedInformation(
                "Montpellier",
                StateProvinceArea.OCCITANIE,
                Country.FRANCE,
                Continent.EUROPE,
                "340##",
            ),
            CityWithRelatedInformation(
                "Toulouse",
                StateProvinceArea.OCCITANIE,
                Country.FRANCE,
                Continent.EUROPE,
                "31###",
            ),
        )

    val streetNames: List<String> =
        listOf(
            "rue Anatole France", "rue des Martyrs", "bd Saint-Germain", "rue du Commandant Cartouche",
            "rue de la palissade", "rue de la Gare", "rue de la Poste", "fronton des épivents",
            "rue des Archives", "rue Tristan Tzara", "rue de l'évangile", "bd de la Somme",
            "rue des flots bleus", "rue de la résistance", "bd de la Mer", "rue Paul Doumer",
            "cours Saint-Louis", "rue Albert", "rue Condorcet", "cour du Médoc", "rue Camille Godard",
            "rue Frère", "promenade du Peyrou", "Cours Gambetta", "rue Oberkampf", "avenue de la liberté",
            "rue de Toiras", "avenue de la Croix du Capitaine", "avenue de Lodève", "bd des Arceaux",
            "place Bellecour", "place des fêtes", "rue Pablo Picasso", "rue Garibaldi", "quai Claude-Bernard",
            "quai des Orfèvres", "avenue Jean Jaurès", "rue Pasteur", "rue des Calanques", "rue de la Loge",
            "rue des Caisseries", "rue de la République", "avenue de la République", "rue Paradis",
            "rue du Parlement", "rue Jean Guéhenno", "bd Maréchal de Lattre de Tassigny", "rue Albert Camus",
        )
}
