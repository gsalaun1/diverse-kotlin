package gsalaun.diverse.address.geography

enum class StateProvinceArea(val value: String) {

    // France -------------------------------------

    ILE_DE_FRANCE("Ile de France"),
    BRETAGNE("Bretagne"),

    AUVERGNE_RHONE_ALPES("Auvergne-Rhône-Alpes"),

    OCCITANIE("Occitanie"),

    PAYS_DE_LA_LOIRE("Pays de la Loire"),

    NOUVELLE_AQUITAINE("Nouvelle Aquitaine"),

    PROVENCE_ALPES_COTE_D_AZUR("Provence-Alpes-Côte d'Azur"),

    // China --------------------------------------

    CHINESE_AUTONOMOUS("Chinese Autonomous"),

    CHINESE_MUNICIPAL("Chinese Municipal"),

    YUNNAN("Yunnan"),

    HUBEI("Hubei"),

    HAINAN("Hainan"),

    // USA ----------------------------------------

    CALIFORNIA("California"),

    COLORADO("Colorado"),

    ILLINOIS("Illinois"),

    WISCONSIN("Wisconsin"),

    WASHINGTON("Washington"),

    DC("District Of Columbia"),

    FLORIDA("Florida"),

    NEVADA("Nevada"),

    LOUISIANA("Louisiana"),

    TEXAS("Texas"),

    PENNSYLVANIA("Pennsylvania"),

    ARIZONA("Arizona"),

    // India --------------------------------------

    MAHARASHTRA("Maharashtra"),

    DELHI("Delhi"),

    KARNATAKA("Karnataka"),

    // Africa --------------------------------------

    BANGUI("Bangui"),

    DAKAR("Dakar"),

    // US ------------------------------------------

    NEW_YORK("New-York"),

    // England -------------------------------------

    LONDON("London");

    override fun toString() = value


}
