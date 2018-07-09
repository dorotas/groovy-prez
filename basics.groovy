// loops
0.upto(2) { println "$it" }
3.times { println "$it" }
0.step(10, 3) { i -> println i }

// execute
println "git help".execute().text 
"mkdir dorota".execute()

// usefull operators
def foo(str) {
	str?.reverse()
} 
println foo("evil")
println foo(null)

// this withing static method
class Wizard {
	def static learn(trick, acition) {
		acition()
		this
	}
}

Wizard.learn("alohomora", { println "Openned" }).learn("lumos", { println "Light on" })

// optional parameters
def log(x, base = 10) {
	Math.log(x) / Math.log(base)
}

println log(1024)
println log(1024, 10)
println log(1024, 2)

def task(name, String[] details) {
	println "$name - $details"
}

task 'Call', '123-123-1234'
task 'Call', '123-123-1234', '456-456-4567'
task 'Check Mail'

// multiple assignments
def(String cat,String mouse) = ['Tom', 'Jerry', 'Spike', 'Tyke']
println "$cat and $mouse"

def(first, second, third) = ['Tom', 'Jerry']
println "$first, $second, and $third"


// implementing interfaces 
// button.addActionListener({ JOptionPane.showMessageDialog(frame, "You clicked!") } as ActionListener)

// in Java we need to implement all seven methods of MouseListener and MouseMotionListener
// displayMouseLocation = { positionLabel.setText("$it.x, $it.y") }
// frame.addMouseListener(displayMouseLocation as MouseListener)
// frame.addMouseMotionListener(displayMouseLocation as MouseMotionListener)


// @Canonical
import groovy.transform.*

@Canonical(excludes="lastName, age")
class Person {
	String firstName
	String lastName
	int age
}
def sara = new Person(firstName: "Sara", lastName: "Walker", age: 49)
println sara

// @Lazy
class Heavy {
	def size = 10
	Heavy() { println "Creating Heavy with $size" }
}
class AsNeeded {
	def value
	@Lazy Heavy heavy1 = new Heavy()
	@Lazy Heavy heavy2 = { new Heavy(size: value) }()
	AsNeeded() { println "Created AsNeeded" }
}
def asNeeded = new AsNeeded(value: 1000)
println asNeeded.heavy1.size
println asNeeded.heavy1.size
println asNeeded.heavy2.size

// Equals
str1 = 'hello'
str2 = str1
str3 = new String('hello')
str4 = 'Hello'
println "str1 == str2: ${str1 == str2}"
println "str1 == str3: ${str1 == str3}"
println "str1 == str4: ${str1 == str4}"
println "str1.is(str2): ${str1.is(str2)}"
println "str1.is(str3): ${str1.is(str3)}"
println "str1.is(str4): ${str1.is(str4)}"


// sometimes semicolons are required
class Semi { 
	def val = 3; // ; is required here
	{
		println "Instance Initializer called ..."
	}
}
new Semi()

public void giveRaise(Employee employee) {
    employee.raise(new BigDecimal(10000.00));
}

// multimethods magic
class Employee {

    public void raise(Number amount) {
        System.out.println("Employee got raise");
    }
}

class Executive extends Employee {

    public void raise(Number amount) {
        System.out.println("Executive got raise");
    }

    public void raise(BigDecimal amount) {
        System.out.println("Executive got outlandish raise");
    }
}

giveRaise new Employee()
giveRaise new Executive()


// static type checking
@groovy.transform.TypeChecked // compile-time verification (This preventsus from using any metaprogramming capabilities.)
def shout(String str) {
	println "Printing in uppercase"
	println str.toUpperCase()
	println "Printing again in uppercase"
	// println str.toUppercase() // typo in method
}
try { 
	shout('hello')
} catch(ex) {
	println"Failed..."
}

// static compilation
@groovy.transform.CompileStatic
def shout1(String str) {
	println str.toUpperCase()
}












