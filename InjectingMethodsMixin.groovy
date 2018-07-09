class Friend {
	def listen() {
		"$name is listening as a friend"
	}
}

class Student {
	def trick() {
		"$name does a trick"
	}
}

class Teacher {
	def teach() {
		"$name is teaching"
	}

	def listen() {
		"$name is listening your answer"
	}
}

// mixin by annotation
// @Mixin(Friend)
@Mixin([Friend, Teacher])
class Person {
	String firstName
	String lastName
	String getName() { "$firstName $lastName"}
}

john = new Person(firstName: 'John', lastName: 'Smith')
println john.listen()

// mixin by calling method
class Dog {
	String name
}

Dog.mixin Friend

buddy = new Dog(name: "Buddy")
println buddy.listen()

// mixin for instance
class Cat {
	String name
}

red = new Cat(name: "Red")
red.metaClass.mixin Friend, Student

println red.listen()
println red.trick()