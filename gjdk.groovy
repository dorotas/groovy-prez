str = 'hello'

println str
println str.dump()

lst = [1,2]
lst.with {
	add(3)
	add(4)
	println size()
	println contains(2)
	println "this is ${this}"
	println "owner is ${owner}"
	println "delegate is ${delegate}"
}

class Car {
	int miles, fuelLevel
	def ride() { println "Riding" }
	def ride(int miles) { println "Riding $miles miles"}
	def ride(int miles, int fuelLevel) { println "Riding $miles miles with $fuelLevel fuel"}
}

car = new Car(miles: 100, fuelLevel: 25)

// accessing properties indirectly
properties = car.properties

properties.each { name, value -> 
	println "$name = ${car[name]}"
}

// invoking methids indirectly 
car.invokeMethod("ride", null)
car.invokeMethod("ride", 100) 
car.invokeMethod("ride", [100, 10] as Object[])

println ""

// working with XMLs
// DOMBuilder
document = groovy.xml.DOMBuilder.parse(new FileReader('languages.xml'))

rootElement = document.documentElement

use(groovy.xml.dom.DOMCategory) {
	println "Languages and authors (DOMBuilder)"
	languages = rootElement.language
	languages.each {
		println "${it.'@name'} authored by ${it.author[0].text()}"
	}
	def languagesByAuthorDOMCategory = { authorName ->
		languages.findAll { it.author[0].text() == authorName }.collect {
			it.'@name' }.join(', ')
	}
	println "Languages by Wirth: " + languagesByAuthorDOMCategory('Wirth')
}

println ""

// XMLParser
languages = new XmlParser().parse('languages.xml')
println "Languages and authors (XMLParser)"
languages.each { 
	println "${it.@name} authored by ${it.author[0].text()}"
}
def languagesByAuthorXMLParser = { authorName ->
	languages.findAll { it.author[0].text() == authorName }.collect {
		it.@name }.join(', ')
}

println "Languages by Wirth: " + languagesByAuthorXMLParser('Wirth')

println ""

// XMLSlurper 
languages = new XmlSlurper().parse('languages.xml')
println "Languages and authors (XMLSlurper)"
languages.language.each {
	println "${it.@name} authored by ${it.author[0].text()}"
}

def languagesByAuthorXMLSlurper = { authorName ->
	languages.language.findAll { it.author[0].text() == authorName }.collect {
		it.@name }.join(', ')
}

println "Languages by Wirth: " + languagesByAuthorXMLSlurper('Wirth')

println ""

// XMLSlurper with namespaces
languages = new XmlSlurper().parse('languagesWithNS.xml').declareNamespace(human: 'Natural', computer: 'Computer')

println "Languages: "
println languages.language.collect { it.@name }.join(', ')

println "Natural languages: "
println languages.'human:language'.collect { it.@name }.join(', ')

println "Computer Languages: "
println languages.'computer:language'.collect { it.@name }.join(', ')
