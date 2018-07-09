// Intercepting Methods Using GroovyInterceptable

class Car implements GroovyInterceptable {
	def check() { System.out.println "check called ... " }

	def start() { System.out.println "start called ... " }

	def drive() { System.out.println "drive called ... " }

	def invokeMethod(String name, args) {
		System.out.print("Call to $name intercepted ... ")
		if (name != 'check') {
			System.out.print("running filter ... ")
			Car.metaClass.getMetaMethod('check').invoke(this, null)
		}

		def validMethod = Car.metaClass.getMetaMethod(name, args)

		if (validMethod) {
			validMethod.invoke(this, args)
		} else {
			Car.metaClass.invokeMethod(this, name, args)
		}
	}
}

Car car = new Car()
car.start()
car.drive()
car.check()

try {
	car.speed()
} catch(Exception ex) {
	println "Exception: " + ex
}

//If the method is not found, we simply route the request to the MetaClass. 
//This creates an opportunity for the method to be synthesized dynamically,
//If the method does not exist, MetaClass’s invokeMethod() will throw a MissingMethodException.

// Intercepting Methods Using MetaClass
class CarMeta {
	def check() { System.out.println "check called ... " }

	def start() { System.out.println "start called ... " }

	def drive() { System.out.println "drive called ... " }
}

CarMeta.metaClass.invokeMethod = { String name, args ->
	System.out.print("Call to $name intercepted ... ")

	if (name != 'check') {
		System.out.print("running filter ... ")
		CarMeta.metaClass.getMetaMethod('check').invoke(delegate, null)
	}

	def validMethod = CarMeta.metaClass.getMetaMethod(name, args)

	if (validMethod) {
		validMethod.invoke(delegate, args)
	} else {
		CarMeta.metaClass.invokeMissingMethod(delegate, name, args)
	}
}


CarMeta carMeta = new CarMeta()
carMeta.start()
carMeta.drive()
carMeta.check()

try {
	carMeta.speed()
} catch(Exception ex) {
	println "Exception: " + ex
}


