// injecting using Categories
class StringUtil {
	def static toSSN(self) { // social security number
		if (self.size() == 9) {
			"${self[0..2]}-${self[3..4]}-${self[5..8]}"
		}
	}
}

@Category(String)
class StringUtilAnnotated {
	def toSSN() { // social security number
		if (size() == 9) {
			"${this[0..2]}-${this[3..4]}-${this[5..8]}"
		}
	}
}

// use can take more than one category
use(StringUtil) {
	println "123456789".toSSN()
	println new StringBuilder("987654321").toSSN()
}

use(StringUtilAnnotated) {
	println "123456789".toSSN()
}

try {
	println "123456789".toSSN()
} catch(MissingMethodException ex) {
	println "Exception outside the use: " + ex.message
}

// intercepting existing methods using categories
class Helper {
	def static toString(String self) {
		def method = self.metaClass.methods.find { it.name == 'toString' }
		'!!' + method.invoke(self, null) + '!!'
	}
}

use(Helper) {
	println 'hello'.toString()
}


// injecting using ExpandoMetaClass (MetaClass)

// using closure and introduced that into Integer's MetaClass - delegate refers to targer object - Integer
Integer.metaClass.daysFromNow = { ->
	Calendar today = Calendar.instance 
	today.add(Calendar.DAY_OF_MONTH, delegate)
	today.time
}

println 5.daysFromNow()

// to use without paretheses we need to set up a property instead of method
Integer.metaClass.getDaysFromNow = { ->
	Calendar today = Calendar.instance 
	today.add(Calendar.DAY_OF_MONTH, delegate)
	today.time
}

println 5.daysFromNow

// injecting static methods
Integer.metaClass.'static'.isEven = { val -> val % 2 == 0 }

println "Is 2 even? " + Integer.isEven(2) 

// injecting new or overriding existing constructor
Integer.metaClass.constructor << { Calendar calendar ->
	new Integer(calendar.get(Calendar.DAY_OF_YEAR))
}

println new Integer(Calendar.instance)

Integer.metaClass.constructor = { int val ->
	print 'Awesome Integer: '
	// From within the constructor override, we can still call the original implementation using reflection.
	constructor = Integer.class.getConstructor(Integer.TYPE)
	constructor.newInstance(val)
}

println new Integer(7)
println new Integer(Calendar.instance)

// Expando Meta Class DSL 
Integer.metaClass { 
	daysFromNow = { ->
		Calendar today = Calendar.instance
		today.add(Calendar.DAY_OF_MONTH, delegate)
		today.time
	}

	getDaysFromNow = { ->
		Calendar today = Calendar.instance
		today.add(Calendar.DAY_OF_MONTH, delegate)
		today.time
	}

	'static' { 
		isEven = { val -> val % 2 == 0 }
	}

	constructor = { Calendar calendar ->
		new Integer(calendar.get(Calendar.DAY_OF_YEAR))
	}

	constructor = { int val -> 
		print 'Awesome Integer: '
		constructor = Integer.class.getConstructor(Integer.TYPE)
		constructor.newInstance(val)
	}
}

println 5.daysFromNow()
println 5.daysFromNow
println "Is 2 even? " + Integer.isEven(2)
println new Integer(7)
println new Integer(Calendar.instance)

// Injecting methods into Specific Instances
class Person {
	def play() { println 'playing ... '}
}

def emc = new ExpandoMetaClass(Person)
emc.sing = { -> 'oh baby baby ... ' }
emc.initialize()

def jack = new Person()
def paul = new Person()

jack.metaClass = emc // setting the instance of MetaClass to inject sing
println jack.sing()

jack.metaClass = null // to remove all added methods, all predefined methods are still available

try {
	jack.play()
	jack.sing()
} catch (ex) {
	println ex
}

// simply setting methods on the instance's metaClass property
jack.metaClass.sing = { ->
	'oh baby baby ... '
}

println jack.sing()

jack.metaClass = null // to remove all added methods, all predefined methods are still available

try {
	jack.play()
	jack.sing()
} catch (ex) {
	println ex
}
