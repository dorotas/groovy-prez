// Using MetaMethod - We don’t have to know a method name at coding time. We can get it as input and invoke the method dynamically.
str = "hello"
methodName = 'toUpperCase'

methodOfInterest = str.metaClass.getMetaMethod(methodName)

println methodOfInterest.invoke(str)

print "Does String respond to toUpperCase()? "
println String.metaClass.respondsTo(str, 'toUpperCase') ? 'yes' : 'no'

print "Does String respond to compareTo(String)? "
println String.metaClass.respondsTo(str, 'compareTo', "test") ? 'yes' : 'no'

print "Does String respond to toUpperCase(int)? "
println String.metaClass.respondsTo(str, 'toUpperCase', 5) ? 'yes' : 'no'

def printInfo(obj) { 
//Assume user entered these values from standard input
usrRequestedProperty = 'bytes'
usrRequestedMethod = 'toUpperCase'

println obj[usrRequestedProperty]
//or
println obj."$usrRequestedProperty"

println obj."$usrRequestedMethod"()
//or
println obj.invokeMethod(usrRequestedMethod, null)}

printInfo('hello')

println "Properties of 'hello' are:"
'hello'.properties.each{ println it }