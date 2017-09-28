package fr.enlight.spellgenerator

import android.support.test.InstrumentationRegistry
import com.google.gson.GsonBuilder
import fr.enlight.spellgenerator.spells.Spellbook
import org.junit.Test
import java.io.InputStreamReader
import java.util.*

/**
 *
 */
class SpellFilesGenerator {

    private val gson = GsonBuilder().create()

    private val spellbookOrderList = listOf(
            "Luz",
            "Oscuridad",
            "Creación",
            "Destrucción",
            "Fuego",
            "Agua",
            "Aire",
            "Tierra",
            "Esencia",
            "Ilusión",
            "Nigromancia",
            "Libre Acceso",
            "CAOS",
            "GUERRA",
            "LITERAE",
            "MUERTE",
            "MUSICAL",
            "NOBLEZA",
            "PAZ",
            "PECADO",
            "CONOCIMIENTO",
            "SANGRE",
            "SUEÑOS",
            "TIEMPO",
            "UMBRAL",
            "VACÍO"
    )

    private val oppositePathMap = mapOf(
            "Luz" to "Oscuridad",
            "Oscuridad" to "Luz",
            "Creación" to "Destrucción",
            "Destrucción" to "Creación",
            "Fuego" to "Agua",
            "Agua" to "Fuego",
            "Aire" to "Tierra",
            "Tierra" to "Aire",
            "Esencia" to "Ilusión",
            "Ilusión" to "Esencia",
            "Nigromancia" to "El resto de las vías de magia."
    )

    private val descriptionMap = mapOf(
            "Luz" to "Esta vía es la que permite a los hechiceros controlar uno de los dos elementos superiores, la luz pura. Es la que controla las emociones positivas del hombre, como el amor, la tranquilidad o el placer. También domina el conocimiento y la detección. Su poder ofensivo y defensivo está muy equilibrado.",
            "Oscuridad" to "La oscuridad no controla la ausencia de luz, sino el puro poder de las tinieblas. Domina todas las emociones negativas como el miedo, la ira y el odio. Es también el poder de los secretos, entre los que se incluyen los conjuros de ocultación y ofuscación. Al igual que la Luz, su poder está muy equilibrado.",
            "Creación" to "El poder de la creación es la capacidad mágica de componer y alterar la realidad que rodea a los hechiceros. Sus conjuros engloban el cambio, la curación y la creación de cosas. Es una magia marcadamente defensiva.",
            "Destrucción" to "Esta vía permite utilizar el poder del flujo de almas para destruir la propia realidad. Sus conjuros afectan tanto al mundo material como al espiritual. Su poder es por naturaleza muy ofensivo.",
            "Fuego" to "La primera de las cuatro vías elementales menores tiene el control sobre las altas temperaturas. Sus conjuros crean calor, fuego y arrasan grandes cantidades de terreno. También reúne conjuros de sacrificio, que permiten consumir ciertos aspectos de un individuo para potenciar a cambio otros. Es una vía muy ofensiva.",
            "Agua" to "El elemento del agua recoge dentro de sí muchos conceptos distintos. Es la vía que controla los líquidos y la pureza de las cosas. También representa el poder del hielo y el de las bajas temperaturas. Su poder es muy equilibrado ofensiva y defensivamente.",
            "Aire" to "La Tierra es el poder del planeta, la piedra y los minerales. Es la magia más material de todas y controla las leyes de la física, como la gravedad y el magnetismo. Su influencia sobre los espíritus y lo inmaterial es muy limitada. Los conjuros de piedra recogen la magia relacionada con la resistencia, la dureza y la lentitud.",
            "Tierra" to "Es la vía de lo etéreo y lo incorpóreo. Recoge los conjuros de velocidad y movimiento. Con su poder se puede alterar el espacio y transportar cuerpos de un lugar a otro. También es la vía que controla el clima y la electricidad.",
            "Esencia" to "La Esencia controla la vida y las almas. Esta vía tiene el poder sobre los espíritus y los seres naturales que pertenecen al mundo. Es también la vía de la naturaleza y los conjuros de origen druídicos.",
            "Ilusión" to "El poder de esta vía permite al hechicero controlar el engaño y la ilusión. Sus conjuros pueden alterar la realidad, y con ella la percepción de las personas.",
            "Nigromancia" to "La nigromancia es una perversión de la magia como tal. Mientras que el resto de vías se alimenta de las energías de la red de almas, la nigromancia las arranca de allí. Sus conjuros destruyen la esencia de la vida, estancando y pervirtiendo las almas que utiliza. Su poder permite devolver la vida a los muertos, destruir las almas y drenar la vida y la esencia de otros seres."
    )

    private val secondaryPaths = listOf(
            "CAOS",
            "GUERRA",
            "LITERAE",
            "MUERTE",
            "MUSICAL",
            "NOBLEZA",
            "PAZ",
            "PECADO",
            "CONOCIMIENTO",
            "SANGRE",
            "SUEÑOS",
            "TIEMPO",
            "UMBRAL",
            "VACÍO"
    )

    @Test
    fun generate_files_on_device(){
        val coreExxetLines = InputStreamReader(InstrumentationRegistry.getContext().assets.open("core_exxet_spells_spanish.csv"))
                .readLines()

        val arcanaExxetLines = InputStreamReader(InstrumentationRegistry.getContext().assets.open("arcana_exxet_spells_spanish.csv"))
                .readLines()

        val descriptor = SpanishSpellFileDescriptor()
        val primarySpellParser = SpellFileParser(descriptor)
        val secondarySpellParser = SpellFileParser(descriptor, true)

        val primarySpellbooks = primarySpellParser.parseFileLines(coreExxetLines)
        val secondarySpellbooks = secondarySpellParser.parseFileLines(arcanaExxetLines)

        primarySpellbooks.forEach{
            it.key.secondaryBookAccessibles = secondaryPaths
            it.key.oppositeBook = oppositePathMap[it.key.bookName]
            it.key.description  = descriptionMap[it.key.bookName]
        }

        val allSpellbooks = primarySpellbooks + secondarySpellbooks

        val spellbooks: List<Spellbook> = allSpellbooks.entries.map {
            it.key.bookId = spellbookOrderList.indexOf(it.key.bookName) + 1
            it.key.bookName = it.key.bookName.toLowerCase().capitalize()
            it.value.mapIndexed { index, spell -> spell.bookId = index + 1 }
            it.key.spells = it.value
            it.key
        }

        Collections.sort(spellbooks, { first, second -> first.bookId - second.bookId})


        // Spellbooks files
        spellbooks.forEach {
            val content = gson.toJson(it)
            writeInFileDir(InstrumentationRegistry.getContext(), "spellbooks", "spellbook_${it.bookId}.json", content)
        }

        // Spellbook index
        spellbooks.forEach { it.spells = null }
        val spellbookIndex = gson.toJson(spellbooks)
        writeInFileDir(InstrumentationRegistry.getContext(), "spellbooks", "spellbook_index.json", spellbookIndex)
    }
}