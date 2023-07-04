package gsalaun.diverse

import gsalaun.diverse.utils.ChessLevel
import gsalaun.diverse.utils.ChessPlayerWithPublicConstructor
import gsalaun.diverse.utils.Country
import gsalaun.diverse.utils.Ingredient
import gsalaun.diverse.utils.NotTrivialToInstantiatePerson
import gsalaun.diverse.utils.PlayerWithProtectedConstructor
import gsalaun.diverse.utils.PropertyOfAllTypes
import gsalaun.diverse.utils.SelfReferencingTypeWithACollectionOfItself
import gsalaun.diverse.utils.TypeWithPrivateEmptyConstructorOnly
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDateTime

@ExtendWith(AllTestsFixtures::class)
class TypeFuzzerShould {

    @Test
    fun `be able to fuzz a type aggregating a bunch of various type of properties`() {
        val fuzzer = Fuzzer(140481483)

        val instance = fuzzer.generateInstanceOf<PropertyOfAllTypes>()

        assertThat(instance?.name).isNotEmpty()
        assertThat(instance?.age).isInstanceOf(Integer::class.java).isNotZero()
        assertThat(instance?.gender).isNotNull()
        assertThat(instance?.favoriteNumbers).hasSize(2)
        assertThat(instance?.birthday).isNotNull()
    }

    @Test
    fun `be able to fuzz enum values`() {
        var fuzzer = Fuzzer(1277808677)
        val ingredient = fuzzer.generateEnum<Ingredient>()

        assertThat(ingredient).isEqualTo(Ingredient.PEPPER)

        fuzzer = Fuzzer(805686996)
        val otherIngredient = fuzzer.generateEnum<Ingredient>()

        assertThat(otherIngredient).isEqualTo(Ingredient.FLOUR)
    }

    @Test
    fun `be able to fuzz a type with a protected constructor`() {
        val fuzzer = Fuzzer(23984398)

        val player = fuzzer.generateInstanceOf<PlayerWithProtectedConstructor>()

        assertThat(player?.lastName).isNotEmpty()
        assertThat(player?.firstName).isNotEmpty()
        assertThat(player?.age).isInstanceOf(Integer::class.java).isNotEqualTo(0)
    }

    @Test
    fun `be able to fuzz a type with getters only and some public constructors with a protected base class constructor involved`() {
        val fuzzer = Fuzzer(953064492);

        val player = fuzzer.generateInstanceOf<ChessPlayerWithPublicConstructor>()

        assertThat(player?.lastName).isNotEmpty()
        assertThat(player?.firstName).isNotEmpty()
        assertThat(player?.chessLevel).isEqualTo(ChessLevel.PRO)
        assertThat(player?.age).isInstanceOf(Integer::class.java).isNotEqualTo(0)
    }

    @Test
    fun `be able to fuzz the properties of a type even when the constructor is empty as soon as they have a public or a private setter`() {
        val fuzzer = Fuzzer(345766738)

        val instance = fuzzer.generateInstanceOf<TypeWithPrivateEmptyConstructorOnly>()

        assertThat(instance?.modifiableSecret).isNotEmpty() // because we Fuzz properties with public setter
        assertThat(instance?.favoriteNumer).isInstanceOf(Integer::class.java)
            .isNotEqualTo(0) // because we fuzz properties with private setter
    }

    @Test
    fun `not be able to fuzz the setterLess properties of a type when the constructor is empty`() {
        val fuzzer = Fuzzer(345766738)

        val instance = fuzzer.generateInstanceOf<TypeWithPrivateEmptyConstructorOnly>()

        assertThat(instance?.consultableSecret).isNull() // because we don't Fuzz properties with no setter
        assertThat(instance?.name).isNull() // because we don't fuzz properties with no setter
    }

    @Test
    fun `be able to fuzz an enumerable with 5 elements when fuzzing a type containing an enumerable of something`() {
        val fuzzer = Fuzzer(787978813)
        val player = fuzzer.generateInstanceOf<ChessPlayerWithPublicConstructor>()

        val aggregatedFavOpponent = player?.favoriteOpponent
        assertThat(aggregatedFavOpponent).isNotNull()
        assertThat(aggregatedFavOpponent?.lastName).isNotEmpty()
        assertThat(aggregatedFavOpponent?.firstName).isNotEmpty()
        assertThat(aggregatedFavOpponent?.firstName).isNotEmpty()
        assertThat(aggregatedFavOpponent?.currentClub?.name).isEqualTo("Alexis")
        assertThat(aggregatedFavOpponent?.currentClub?.country).isEqualTo(Country.UKRAINE)

        assertThat(player?.formerClubs).hasSize(4);
    }

    @Test
    fun `be able to generate instance of Int`() {
        val fuzzer = Fuzzer()

        val result = fuzzer.generateInstanceOf<Int>()
        assertThat(result).isInstanceOf(Integer::class.java).isNotZero()
    }

    @Test
    fun `be able to generate instance of long`() {
        val fuzzer = Fuzzer(1128739598)

        val result = fuzzer.generateInstanceOf<Long>()
        assertThat(result).isInstanceOf(java.lang.Long::class.java).isNotZero()
    }

    @Test
    fun `be able to generate instance of double`() {
        val fuzzer = Fuzzer()

        val result = fuzzer.generateInstanceOf<Double>()
        assertThat(result).isInstanceOf(java.lang.Double::class.java).isNotZero()
    }

    @Test
    fun `be able to generate instance of list of Int`() {
        val fuzzer = Fuzzer(1824499564)

        val result = fuzzer.generateInstanceOf<List<Int>>()
        assertThat(result).hasSize(3)
    }

    @Test
    fun `be able to generate instance of set of String`() {
        val fuzzer = Fuzzer(1283043917)

        val result = fuzzer.generateInstanceOf<List<Double>>()
        assertThat(result).hasSize(2)
    }

    @Test
    fun `be able to generate instance of map of int,String`() {
        val fuzzer = Fuzzer(584196225)

        val result = fuzzer.generateInstanceOf<Map<Int, String>>()
        assertThat(result).hasSize(2)
    }

    @Test
    fun `be able to generate instance of LocalDateTime`() {
        val fuzzer = Fuzzer()

        val result = fuzzer.generateInstanceOf<LocalDateTime>()

        assertThat(result).isNotNull().isNotEqualTo(LocalDateTime.now())
    }

    @Test
    fun `be able to generate instance of bool`() {
        val fuzzer = Fuzzer()

        val result = fuzzer.generateInstanceOf<Boolean>()
        assertThat(result).isInstanceOf(java.lang.Boolean::class.java)
    }

    @Test
    fun `be able to generate instance of enum`() {
        val fuzzer = Fuzzer()

        val result = fuzzer.generateEnum<Country>()
        assertThat(result).isInstanceOf(Country::class.java)
    }

    @Test
    @Disabled("Because it timeouts JUnit for the moment due to excessive recursion. Time to find another solution than recursion.")
    fun `be able to fuzz self referencing types aggregating a collection of the same type`() {
        val fuzzer = Fuzzer()

        val result = fuzzer.generateInstanceOf<SelfReferencingTypeWithACollectionOfItself>()

        assertThat(result?.friends).hasSize(5)
    }

    @Test
    fun `be able to generate instance of object with constructor throwing Exception`(){
        val fuzzer = Fuzzer(842964934)

        val result = fuzzer.generateInstanceOf<NotTrivialToInstantiatePerson>()

        assertThat(result?.name).isEqualTo("Rebecca")
        assertThat(result?.surname).isEqualTo("Byeol")
        assertThat(result?.age).isNull()

    }
}