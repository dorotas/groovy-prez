// doesn't require final parameter
def pickEven (n, block) {
	for(int i = 2; i <= n; i += 2) {
		block(i)
	}
}
pickEven(10, { println it })

total = 0 
pickEven(10) {total += it}
println "Sum of even numbers from 1 to 10 is ${total}"

// passing parameter to Closures
def tellFortune(closure) {
	closure new Date("06/08/2018"), "Your day is filled with ceremony"
}

tellFortune() { date, fortune -> println "Fortune for ${date} is '${fortune}'"}

def iterate(n, closure) {
	1.upto(n) {
		println "Initerate with value ${it}"
		closure(it)
	}
}

println "Calling iterate"
total = 0
iterate(4) {
	total += it
	println "In closure total so far is ${total}"
}
println "Done"

// this, owener, delegate
def examiningClosure(closure) {
	closure()
}

examiningClosure() {
	println "In First Closure: "
	println "class is " + getClass().name
	println "this is " + this + ", super: " + this.getClass().superclass.name
	println "owner is " + owner + ", super: " + owner.getClass().superclass.name
	println "delegate is " + delegate + ", super: " + delegate.getClass().superclass.name

	examiningClosure() { 
		println "In Closure within the FirstClosure: "
		println "class is " + getClass().name
		println "this is " + this + ", super: " + this.getClass().superclass.name
		println "owner is " + owner + ", super: " + owner.getClass().superclass.name
		println "delegate is " + delegate + ", super: " + delegate.getClass().superclass.name

		examiningClosure() { 
			println "In Closure within the SecondClosure: "
			println "class is " + getClass().name
			println "this is " + this + ", super: " + this.getClass().superclass.name
			println "owner is " + owner + ", super: " + owner.getClass().superclass.name
			println "delegate is " + delegate + ", super: " + delegate.getClass().superclass.name
		}
	}
}

println ""
println "METHOD ROUTING"
println ""

class Handler {
	def f1() { println "f1 of Handler called..." }
	def f2() { println "f2 of Handler called..." }
}

class Example {
	def f1() { println "f1 of Example called..." }
	def f2() { println "f2 of Example called..." }
	def foo(closure) {
		println "Example delegate is " + closure.delegate
		closure.delegate = new Handler()
		println "Example delegate after change is " + closure.delegate
		closure()
	}
}

def f1() { println "Script f1 this is " + this; println "f1 of Script called..." }

Example ex = new Example()
ex.foo {
	println "THIS " + this
	f1()
	f2()
}

// trampoline and tail recursion
def factorial
factorial = { int number, BigInteger theFactorial ->
	number == 1 ? 
		theFactorial : factorial(number-1, number * theFactorial)
}

// println "factorial of 300 is ${factorial(5000,1)}" - StackOverflowError

def factorialTR
factorialTR = { int number, BigInteger theFactorial = 1 ->
	number == 1 ? 
		theFactorial : factorialTR.trampoline(number-1, number * theFactorial)
}.trampoline()

println "factorialTR of 5000 is ${factorialTR(5000)}"

def fib
fib = {int n, BigInteger a = 0, BigInteger b = 1 ->
	if(n == 0) a
	else fib n-1, b, a+b 
}

println fib(300) 







