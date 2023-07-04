package gsalaun.diverse.address.geography.countries

import gsalaun.diverse.address.geography.CityWithRelatedInformation
import gsalaun.diverse.address.geography.Country
import gsalaun.diverse.address.geography.StateProvinceArea
import gsalaun.diverse.persons.Continent

// TODO Maybe use Json instead of internal values
object China {

    val cities: List<CityWithRelatedInformation> =
        listOf(
            CityWithRelatedInformation(
                "Wuhan",
                StateProvinceArea.HUBEI,
                Country.CHINA,
                Continent.ASIA,
                "4300##",
            ),
            CityWithRelatedInformation(
                "Beijing",
                StateProvinceArea.CHINESE_MUNICIPAL,
                Country.CHINA,
                Continent.ASIA,
                "1000##",
            ),
            CityWithRelatedInformation(
                "Shanghai",
                StateProvinceArea.CHINESE_MUNICIPAL,
                Country.CHINA,
                Continent.ASIA,
                "2000##",
            ),
            CityWithRelatedInformation(
                "Tianjin",
                StateProvinceArea.CHINESE_MUNICIPAL,
                Country.CHINA,
                Continent.ASIA,
                "300###",
            ),
            CityWithRelatedInformation(
                "Chongqing",
                StateProvinceArea.CHINESE_MUNICIPAL,
                Country.CHINA,
                Continent.ASIA,
                "4000##",
            ),
            CityWithRelatedInformation(
                "Danzhou",
                StateProvinceArea.HAINAN,
                Country.CHINA,
                Continent.ASIA,
                "5717##",
            ),
            CityWithRelatedInformation(
                "Kunming",
                StateProvinceArea.YUNNAN,
                Country.CHINA,
                Continent.ASIA,
                "650###",
            ),
            CityWithRelatedInformation(
                "Lijiang",
                StateProvinceArea.YUNNAN,
                Country.CHINA,
                Continent.ASIA,
                "6741##",
            ),
            CityWithRelatedInformation(
                "Shangri-La",
                StateProvinceArea.YUNNAN,
                Country.CHINA,
                Continent.ASIA,
                "674400",
            ),
        )

    val streetNames: List<String> =
        listOf(
            "Shuangshiduan, Xinhua Street, Dayan Street",
            "Zhonghe Rd,Shuhe Ancient Town",
            "Xiangjiang Road, Gucheng District",
            "Guangbi Alley Guangyi Street",
            "DongSanHuan Middle Road, Chao-Yang",
            "Jinyu Hutong Wangfujing, Dongcheng",
            "North Dongsanhua Road, Chao-Yang",
            "Yong An Dong Li, Jian Guo Men Wai Avenue, Chao-Yang",
            "Hong Hua Qiao, Wuhua District",
            "Qianxing Road, Xishan District, Xishan District",
            "Zhong Shan San Lu, Yu Zhong",
            "Changjiang Binjiang Road，Yuzhong District, Yu Zhong",
            "Chicika Street, Jiantang Town",
            "Biesailang, the ancient city of dukezong",
            "Binjiang Ave, Hanyang District",
            "Zhongshan Avenue, Qiaokou District, Hankou (CapitaMall Wusheng Road), Qiaokou District",
            "Guosheng Road",
            "E Man Town",
            "Chifeng Road, Intersection of Nanjing Road and Harbin Road, Heping District",
            "Phoenix Shopping Mall, East Haihe Road, Hebei",
            "Zhong Shan Dong Yi Road, Huangpu",
            "Middle Yan'an Road, Jing'an",
        )
}
